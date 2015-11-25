package com.wirednest.apps.hairstyle.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;
import com.wirednest.apps.hairstyle.MainActivity;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.models.HairstylesObject;
import com.wirednest.apps.hairstyle.network.service.APIService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

public class DataSyncFragment extends Fragment {

    private Subscription subscription;
    private CompositeSubscription mCompositeSubscription;
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
        updateHairstyles();
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        new Thread(new Runnable() {
//            public void run() {
//
//                mProgressBar.setProgress(0f);
//                mProgressBar.start();
//                while (i < 100) {
//                    i += 1;
//                    // Update the progress bar and display the
//                    //current value in the text view
//                    handler.post(new Runnable() {
//                        public void run() {
//                            float p = i/100f;
//                            Log.d("Log_progress","progress "+p);
//                            mProgressBar.setProgress(p);
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        //Just to display the progress slowly
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Intent intent = new Intent(ctx, MainActivity.class);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        }).start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeSubscription = new CompositeSubscription();



//
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent intent = new Intent(ctx, MainActivity.class);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                // close this activity
//                //
//            }
//        }, 3000);
    }

    private void updateHairstyles(){
        APIService apiService = new APIService();
        Observable<List<HairstylesObject>> call = apiService.getHairstyles("123");
        mCompositeSubscription.add(call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<HairstylesObject>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(final List<HairstylesObject> hairstyles) {
                                i=0;
                                final int hairstyleNum = hairstyles.size();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.post(new Runnable() {
                                            public void run() {
                                                mProgressBar.setProgress(0f);
                                                mProgressBar.start();
                                            }
                                        });

                                        for (HairstylesObject data : hairstyles) {


                                            handler.post(new Runnable() {
                                                public void run() {
                                                    i+=1;
                                                    float p =  i / (float) hairstyleNum;
                                                    Log.d("Log_progress", "progress " + p + " : " + hairstyleNum);
                                                    mProgressBar.setProgress(p);
                                                }
                                            });

                                        }



                                    }
                                }).start();
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

}
