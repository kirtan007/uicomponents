package com.tinymatrix.uicomponents.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Created by kirtan on 3/24/16.
 */
public class BaseApp extends MultiDexApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}