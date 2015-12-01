package com.wirednest.apps.hairstyle.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.wirednest.apps.hairstyle.MainActivity;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.activity.SplashScreenActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FacebookConnectFragment extends Fragment {
    private static final String PREF = "Hairstyle";
    private static final String TAG = "hairstyle_";
    private Context ctx;
    private SimpleFacebook mSimpleFacebook;

    @Bind(R.id.login_button)
    Button login_button;

    public void setupLogin(){

        final OnLoginListener onLoginListener = new OnLoginListener() {

            @Override
            public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                Log.d(TAG,"Logged");
                // change the state of the button or do whatever you want
                SharedPreferences preference = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putBoolean("Initialize", true);
                editor.apply();
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"Cancel");
                // user canceled the dialog
            }

            @Override
            public void onFail(String reason) {
                Log.d(TAG,reason);
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onException(Throwable throwable) {
                Log.d(TAG,throwable.getMessage());
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                // exception from facebook
            }

        };
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mSimpleFacebook.login(onLoginListener);
            }
        });
    }

    public FacebookConnectFragment(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimpleFacebook = SimpleFacebook.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook_connect, container, false);
        ButterKnife.bind(this, view);
        setupLogin();
        return view;
    }
}
