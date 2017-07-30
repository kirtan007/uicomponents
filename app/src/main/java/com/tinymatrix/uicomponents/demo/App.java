package com.tinymatrix.uicomponents.demo;

import android.app.Application;


import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by systemx on 11/6/17.
 */

public class App extends Application
{
    static App app;
    @Override
    public void onCreate()
    {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        app =this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
