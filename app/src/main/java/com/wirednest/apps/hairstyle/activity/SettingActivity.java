package com.wirednest.apps.hairstyle.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.rey.material.widget.Button;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.wirednest.apps.hairstyle.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {
    private static final String PREF = "Hairstyle";
    private SimpleFacebook mFacebook;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Bind(R.id.facebookButton)
    Button facebookButton;
    @Bind(R.id.settingImei)
    EditText settingImei;
    @Bind(R.id.settingAPIKey)
    EditText settingAPIKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        preference = getSharedPreferences(PREF, Context.MODE_PRIVATE);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        settingImei.setText(telephonyManager.getDeviceId());
        settingImei.setEnabled(false);
        settingAPIKey.setText(preference.getString("APIKEY",""));

        mFacebook = SimpleFacebook.getInstance();
        changeFacebookButtonState(mFacebook.isLogin());
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButtonAction(mFacebook.isLogin());
            }
        });
    }
    private void facebookButtonAction(Boolean isLogged){
        if(isLogged){
            mFacebook.logout(new OnLogoutListener() {
                @Override
                public void onLogout() {
                    changeFacebookButtonState(false);
                }
            });
        }else{
            mFacebook.login(new OnLoginListener() {
                @Override
                public void onLogin(String s, List<Permission> list, List<Permission> list1) {
                    changeFacebookButtonState(true);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onException(Throwable throwable) {

                }

                @Override
                public void onFail(String s) {

                }
            });
        }
    }
    private void changeFacebookButtonState(Boolean isLogged){
        if(isLogged){
            facebookButton.setText("Logout");
        }else{
            facebookButton.setText("Log in with Facebook");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebook.onActivityResult(requestCode, resultCode, data);
    }
}
