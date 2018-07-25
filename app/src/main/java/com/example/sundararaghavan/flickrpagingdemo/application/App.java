package com.example.sundararaghavan.flickrpagingdemo.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Sundararaghavan on 7/25/2018.
 */
public class App
        extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    int page = 1;
    /**
     * app level shared context without memory leak problem
     */
    @SuppressLint("StaticFieldLeak")
    private static Context instance;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
