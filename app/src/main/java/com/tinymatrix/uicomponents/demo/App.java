package com.tinymatrix.uicomponents.demo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by systemx on 11/6/17.
 */

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
