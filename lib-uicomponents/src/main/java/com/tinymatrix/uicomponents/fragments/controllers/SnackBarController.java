package com.tinymatrix.uicomponents.fragments.controllers;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.activities.Activity;

/**
 * Created by systemx on 15/4/17.
 */

public class SnackBarController
{
    private Activity activity;
    public SnackBarController(Activity activity)
    {
        this.activity = activity;
    }
    /* Snackbar API */
    private Snackbar snackbar;

    public Snackbar getSnackBar(View view, CharSequence message)
    {
        Snackbar snackbar= Snackbar.make(view, message, Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback()
        {
            @Override
            public void onDismissed(Snackbar snackbar, int event)
            {
                super.onDismissed(snackbar, event);
            }

            @Override
            public void onShown(Snackbar snackbar)
            {
                super.onShown(snackbar);
            }
        });
        snackbar.setActionTextColor(ContextCompat.getColor(activity,android.R.color.white));
        return snackbar;
    }

    public void dismissSnackBar()
    {
        if(snackbar!=null && snackbar.isShown())
        {
            snackbar.dismiss();
            snackbar = null;
        }
    }
}
