package com.tinymatrix.uicomponents.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.tinymatrix.uicomponents.R;


/**
 * Created by Kirtan on 4/22/2014.
 */
public class ActivityUtil
{
    public static void startExitAnimation(Activity activity)
    {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public static void startEnterAnimation(Activity activity)
    {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public static void startActivity(Context context, Class<?> classz)
    {
        Intent intent = new Intent(context, classz);
        context.startActivity(intent);
        ActivityUtil.startEnterAnimation((Activity) context);
    }

    public static void activateFullScreen(Activity activity)
    {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void activateFullScreenWithActionBar(Activity activity)
    {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}

