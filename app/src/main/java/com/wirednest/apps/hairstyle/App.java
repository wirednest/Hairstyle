package com.wirednest.apps.hairstyle;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orm.SugarContext;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        Stetho.initializeWithDefaults(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
