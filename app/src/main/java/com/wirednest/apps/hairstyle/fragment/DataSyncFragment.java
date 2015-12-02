package com.wirednest.apps.hairstyle.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rey.material.widget.ProgressView;
import com.wirednest.apps.hairstyle.MainActivity;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.db.Categories;
import com.wirednest.apps.hairstyle.db.Hairstyles;
import com.wirednest.apps.hairstyle.models.CategoriesObject;
import com.wirednest.apps.hairstyle.models.HairstylesObject;
import com.wirednest.apps.hairstyle.network.service.APIService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class DataSyncFragment extends Fragment {
    private static final String PREF = "Hairstyle";
    private static final String TAG = "hairstyle_";
    private static final int    MIN_DISK_CACHE_SIZE = 512 * 1024 * 102;
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
                                updateHairstyles();
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
                                //startActivity();
                            }

                            @Override
                            public void onNext(final List<HairstylesObject> hairstyles) {
                                i = 0;
                                final int hairstyleNum = hairstyles.size();

                                handler.post(new Runnable() {
                                    public void run() {
                                        syncStatus.setText("Updating hairstyle data");
                                    }
                                });
                                for (HairstylesObject data : hairstyles) {
                                    List<Hairstyles> hairstyleList = Hairstyles.find(Hairstyles.class,
                                            "ID_SERVER = ?", "" + data.getHairstyleId());
                                    if (hairstyleList.size() > 0) {
                                        Hairstyles hairstyle = hairstyleList.get(0);
                                        hairstyle.hairName = data.getHairstyleName();
                                        hairstyle.description = data.getHairsyleDescription();
                                        hairstyle.categories = Categories.findByServerId(data.getCategoryId());

//                                        sdcardTarget target = new sdcardTarget(hairstyle.getId()
//                                                + " " + data.getHairstyleName());
//                                        mBuilder.load(data.getImage())
//                                                .into(target);
                                        downloadImage download = new downloadImage(
                                                data.getImage(),
                                                hairstyle.getId()
                                                        + " " + data.getHairstyleName()
                                        );
                                        download.download();
                                        hairstyle.image = download.getFilename();
                                        hairstyle.save();
                                    } else {
                                        Hairstyles hairstyle = new Hairstyles();
                                        hairstyle.idServer = data.getHairstyleId();
                                        hairstyle.hairName = data.getHairstyleName();
                                        hairstyle.description = data.getHairsyleDescription();
                                        hairstyle.categories = Categories.findByServerId(data.getHairstyleId());
                                        hairstyle.save();
//                                        sdcardTarget target = new sdcardTarget(hairstyle.getId()
//                                                + " " + data.getHairstyleName());
//
//                                        mBuilder.load(data.getImage())
//                                                .into(target);
                                        downloadImage download = new downloadImage(
                                                data.getImage(),
                                                hairstyle.getId()
                                                + " " + data.getHairstyleName()
                                        );
                                        download.download();
                                        hairstyle.image = download.getFilename();
                                        hairstyle.save();
                                    }
                                }
                                startActivity();




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

    private class downloadImage{
        private String filename;
        private File imageDir;
        private File fileForImage;
        private String url;
        public downloadImage(String url,String filename) {
            this.url=url;
            this.filename = filename.replaceAll(" ", "_").toLowerCase() + ".png";
            File pictureFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File appDir = new File(pictureFileDir,"Hairstyle");

            if (!appDir.exists() && !appDir.mkdirs()) {
                Log.d("Log", "Can't create directory to save image.");

            }
            imageDir = new File(appDir,"hairstyles-data");
            if (!imageDir.exists() && !imageDir.mkdirs()) {
                Log.d("Log", "Can't create directory to save image.");
            }
            Log.d("Hairstyle_","Target acc "+this.filename);
            fileForImage = new File(imageDir + File.separator + this.filename);
        }
        public void download(){
            Log.d("Hairstyle_","Target start "+this.filename);
            InputStream sourceStream;
            //File cachedImage = ImageLoader.getInstance().getDiskCache().get(this.url);
            try {
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.loadImage(this.url, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        try {


                            if (!fileForImage.exists()) {
                                Log.d("Hairstyle_download",filename+" downloaded");
                                if(fileForImage.createNewFile()){
                                    FileOutputStream ostream = new FileOutputStream(fileForImage);
                                    loadedImage.compress(Bitmap.CompressFormat.PNG, 80, ostream);
                                    ostream.close();
                                    Log.d("Hairstyle_download", filename + " file created");
                                }else{
                                    Log.d("Hairstyle_download", filename + " file not created");
                                }


                            }else{
                                Log.d("Hairstyle_download",filename+" exist");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            } catch (Exception e) {
                Log.e("Hairstyle_","Target downloaded error "+e.getMessage());
                e.printStackTrace();
            }


        }
        public String getFilename(){
            return this.filename;
        }
    }



}
