package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.sromku.simple.fb.SimpleFacebook;
import com.wirednest.apps.hairstyle.MainActivity;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.fragment.DataSyncFragment;

import android.os.Handler;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends FragmentActivity {

    public SimpleFacebook mSimpleFacebook;

    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sync);
        ButterKnife.bind(this);
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        if(fragmentContainer != null){
            if (savedInstanceState != null) {
                return;
            }
            DataSyncFragment dsFragment =  new DataSyncFragment(getBaseContext());
            dsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, dsFragment).commit();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }
}
