package com.wirednest.apps.hairstyle.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    private int chooseCamera = 0;
    @Bind(R.id.facebookButton)
    Button facebookButton;
    @Bind(R.id.settingImei)
    EditText settingImei;
    @Bind(R.id.settingAPIKey)
    EditText settingAPIKey;
    @Bind(R.id.cameraSpinner)
    Spinner cameraSpinner;
    @Bind(R.id.saveSettingButton)
    Button saveSettingButton;

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

        String[] items = new String[Camera.getNumberOfCameras()];
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int j = 0;
        for(int i = 0; i < items.length; i++){
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                items[i] = "Back Camera";
            }else if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                items[i] = "Front Camera";
            }else{
                j++;
                items[i] = "External Camera "+j;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cameraSpinner.setAdapter(adapter);
        cameraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chooseCamera = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chooseCamera = preference.getInt("cameraId",0);

        cameraSpinner.setSelection(chooseCamera);

        saveSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = preference.edit();
                editor.putString("APIKEY",settingAPIKey.getText().toString());
                editor.putInt("cameraId", chooseCamera);
                editor.apply();
                Toast.makeText(SettingActivity.this,
                        "Setting saved.",
                        Toast.LENGTH_SHORT).show();
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
