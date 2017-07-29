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
    public static Application context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;

    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext()
    {
        return context.getApplicationContext();
    }
}