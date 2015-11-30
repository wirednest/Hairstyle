package com.wirednest.apps.hairstyle.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.wirednest.apps.hairstyle.MainActivity;
import com.wirednest.apps.hairstyle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstInstallFragment extends Fragment{
    private static final String PREF = "Hairstyle";
    private static final String TAG = "hairstyle_";

    private Context ctx;
    private SharedPreferences preference;
    @Bind(R.id.imei)
    TextView imeiText;
    @Bind(R.id.apikey)
    TextView apikey;
    @Bind(R.id.saveAPI)
    Button saveAPIButton;


    public FirstInstallFragment(Context ctx) {
        this.ctx = ctx;
    }

    @OnClick(R.id.saveAPI)
    public void saveAPI(){
        preference = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean("Initialize",true);
        editor.putString("APIKEY",apikey.getText().toString());
        editor.commit();

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_install, container, false);
        ButterKnife.bind(this, view);
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        imeiText.setText(telephonyManager.getDeviceId());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
