package com.wirednest.apps.hairstyle;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.orm.SugarContext;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App extends Application {

    Permission[] permissions = new Permission[] {
            Permission.USER_PHOTOS,
            Permission.EMAIL,
            Permission.PUBLISH_ACTION
    };

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        Stetho.initializeWithDefaults(this);
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId("1258781384148572")
                .setNamespace("hairstyle-apps")
                .setPermissions(permissions)
                .build();
        SimpleFacebook.setConfiguration(configuration);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.wirednest.apps.hairstyle",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
