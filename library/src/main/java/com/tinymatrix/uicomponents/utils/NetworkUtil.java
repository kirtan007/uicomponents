package com.tinymatrix.uicomponents.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 12/3/15.
 */
public class NetworkUtil
{
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isInternetAvailable()
    {
        boolean isInternetAvailable = false;
        try
        {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            if ((urlc.getResponseCode() == 200))
            {
                isInternetAvailable = true;
            }
        }
        catch (IOException e)
        {

        }
        return isInternetAvailable;
    }

    /*public void isInternetAvailable(BaseFragment.IInternetCheckListener internetCheckListener)
    {
        if(!isNetworkAvailable())
        {
            internetCheckListener.internetNotAvailable();
            return;
        }
        try
        {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            if ((urlc.getResponseCode() == 200))
            {
                internetCheckListener.internetAvailable();
            }
        }
        catch (IOException e)
        {

        }
    }*/
}
