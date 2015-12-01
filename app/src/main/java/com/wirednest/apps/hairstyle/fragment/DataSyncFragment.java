package com.wirednest.apps.hairstyle.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wirednest.apps.hairstyle.MainActivity;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.db.Categories;
import com.wirednest.apps.hairstyle.db.Hairstyles;
import com.wirednest.apps.hairstyle.models.CategoriesObject;
import com.wirednest.apps.hairstyle.models.HairstylesObject;
import com.wirednest.apps.hairstyle.network.service.APIService;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

public class DataSyncFragment extends Fragment {
    private static final String PREF = "Hairstyle";
    private static final String TAG = "hairstyle_";

    private Subscription subscription;
    private CompositeSubscription mCompositeSubscription;
    private APIService apiService;
    private SharedPreferences preference;
    Context ctx;
    @Bind(R.id.progressbar)
    ProgressView mProgressBar;
    @Bind(R.id.syncStatus)
    TextView syncStatus;

    @SuppressLint("ValidFragment")
    public DataSyncFragment(Context context) {
        super();
        ctx = context;
    }


    CountDownTimer mCountDownTimer;
    int i=0;

    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onResume() {
        super.onResume();
        updateCategories();
        //getAPIData();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeSubscription = new CompositeSubscription();
        apiService = new APIService();

    }

    private void startActivity(){

        preference = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(preference.getBoolean("Initialize",false)){
            Intent intent = new Intent(ctx, MainActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();

        }else{
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            FirstInstallFragment dsFragment =  new FirstInstallFragment(ctx);
            dsFragment.setArguments(getActivity().getIntent().getExtras());
            ft.replace(R.id.fragment_container, dsFragment, "NewFragmentTag");
            ft.commit();
        }

    }

    private void getAPIData(){

        Observable<List<CategoriesObject>> call = apiService.getCategories("123");
        mCompositeSubscription.add(call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<CategoriesObject>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(List<CategoriesObject> categoriesObjects) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }


    private void updateCategories(){
        Observable<List<CategoriesObject>> call = apiService.getCategories("123");

        mCompositeSubscription.add(call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<CategoriesObject>>() {
                            @Override
                            public void onCompleted() {
                                //startActivity();

                            }

                            @Override
                            public void onError(Throwable e) {
                                startActivity();
                            }

                            @Override
                            public void onNext(final List<CategoriesObject> categories) {
                                i = 0;
                                final int hairstyleNum = categories.size();

                                handler.post(new Runnable() {
                                    public void run() {
                                        syncStatus.setText("Updating categories data");
                                        for (CategoriesObject data : categories) {
                                            List<Categories> categoryList = Categories.find(Categories.class,
                                                    "ID_SERVER = ?",""+data.getCategoryId());
                                            if(categoryList.size()>0){
                                                Categories category = categoryList.get(0);
                                                category.categoryName = data.getCategoryName();
                                                category.description = data.getCategoryDescription();
                                                category.save();
                                            }else{
                                                Categories category = new Categories(
                                                        data.getCategoryId(),
                                                        data.getCategoryName(),
                                                        data.getCategoryDescription(),
                                                        0);
                                                category.save();
                                            }
                                        }
                                        updateHairstyles();
                                    }
                                });


                            }
                        })
        );
    }

    private void updateHairstyles(){

        Observable<List<HairstylesObject>> call = apiService.getHairstyles("123");
        mCompositeSubscription.add(call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<HairstylesObject>>() {
                            @Override
                            public void onCompleted() {
                                //startActivity();

                            }

                            @Override
                            public void onError(Throwable e) {
                                startActivity();
                            }

                            @Override
                            public void onNext(final List<HairstylesObject> hairstyles) {
                                i = 0;
                                final int hairstyleNum = hairstyles.size();

                                handler.post(new Runnable() {
                                    public void run() {
                                        syncStatus.setText("Updating hairstyle data");
                                        for (HairstylesObject data : hairstyles) {
                                            List<Hairstyles> hairstyleList = Hairstyles.find(Hairstyles.class,
                                                    "ID_SERVER = ?", "" + data.getHairstyleId());
                                            if (hairstyleList.size() > 0) {
                                                Hairstyles hairstyle = hairstyleList.get(0);
                                                hairstyle.hairName = data.getHairstyleName();
                                                hairstyle.description = data.getHairsyleDescription();
                                                hairstyle.categories = Categories.findByServerId(data.getCategoryId());
                                                Log.d(TAG,"id:"+hairstyle.categories);
                                                sdcardTarget target = new sdcardTarget(hairstyle.getId()
                                                        + " " + data.getHairstyleName());
                                                Picasso.with(getContext())
                                                        .load(data.getImage())
                                                        .into(target);
                                                hairstyle.image = target.getFilename();
                                                hairstyle.save();
                                            } else {
                                                Hairstyles hairstyle = new Hairstyles();
                                                hairstyle.idServer = data.getHairstyleId();
                                                hairstyle.hairName = data.getHairstyleName();
                                                hairstyle.description = data.getHairsyleDescription();
                                                hairstyle.categories = Categories.findByServerId(data.getHairstyleId());
                                                hairstyle.save();
                                                sdcardTarget target = new sdcardTarget(hairstyle.getId()
                                                        + " " + data.getHairstyleName());
                                                Picasso.with(getContext())
                                                        .load(data.getImage())
                                                        .into(target);
                                                hairstyle.image = target.getFilename();
                                                hairstyle.save();
                                            }
                                        }
                                        startActivity();
                                    }
                                });



                            }
                        })
        );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_sync, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private class sdcardTarget implements Target{

        private String filename;
        public sdcardTarget(String filename) {
            this.filename = filename.replaceAll(" ", "_").toLowerCase() + ".png";
        }
        public String getFilename(){
            return this.filename;
        }
        @Override
            public void onPrepareLoad(Drawable arg0) {
                return;
            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {

                try {
                    File file = null;
                    final File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Hairstyle");
                    if (!appDir.exists() && !appDir.mkdirs()) {
                        Log.d("Log", "Can't create directory to save image.");
                        return;
                    }
                    File imageDir = new File(appDir,"hairstyles-data");
                    if (!imageDir.exists() && !imageDir.mkdirs()) {
                        Log.d("Log", "Can't create directory to save image.");
                        return;
                    }
                    File image = new File(imageDir + File.separator + filename );
                    if(!image.exists()){
                        image.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, ostream);
                        ostream.close();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Drawable arg0) {
                return;
            }


    }

}
