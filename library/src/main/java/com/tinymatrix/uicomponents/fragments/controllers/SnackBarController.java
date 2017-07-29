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

    /* Success Snackbar */
    /*public void showSuccessSnackBar(CharSequence message)
    {
        snackbar = getSnackBar(activity.getCoordinatorLayout(), message);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.success_green));
        snackbar.show();
    }

    *//* Failure Snackbars *//*
    public void showFailureSnackBar(CharSequence message)
    {
        snackbar = getSnackBar(activity.getCoordinatorLayout(), message);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity,R.color.failure_red));
        snackbar.show();
    }
    public void showFailureSnackBar(@StringRes int messageStringResId, @StringRes int actionStringResId, View.OnClickListener onClickListener)
    {
        String message = activity.getString(messageStringResId);
        String actionString =activity.getString(actionStringResId);
        showFailureSnackBar(message,actionString,onClickListener);
    }

    public void showFailureSnackBar(CharSequence message,String actionText,View.OnClickListener onClickListener)
    {
        snackbar = getSnackBar(activity.getCoordinatorLayout(), message).setAction(actionText,onClickListener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity,R.color.failure_red));
        snackbar.show();
    }*/

    /* Simple Snackbar */
   /* public void showSnackbar(int messageStringResId)
    {
        showSnackbar(activity.getString(messageStringResId));
    }*/

    /*public void showSnackbar(CharSequence message)
    {
        snackbar = getSnackBar(activity.getCoordinatorLayout(), message);
        snackbar.show();
    }

    public void showSnackbar(CharSequence message, String actionName, View.OnClickListener onClickListener)
    {
        snackbar = getSnackBar(activity.getCoordinatorLayout(), message).setAction(actionName, onClickListener);
        snackbar.show();
    }
*/
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
        snackbar.setActionTextColor(ContextCompat.getColor(activity,R.color.white));
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
