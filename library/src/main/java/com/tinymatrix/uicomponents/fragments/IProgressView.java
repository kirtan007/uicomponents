package com.tinymatrix.uicomponents.fragments;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by systemx on 25/6/17.
 */

public interface IProgressView
{
    View getView();
    void showProgress();
    void showRetry(String message, Drawable drawable,View.OnClickListener clickListener);
    void showRetry(@StringRes int messageStringResId, @DrawableRes int drawableResId, View.OnClickListener clickListener);
    void hideProgress();
    void showDataProgress();
}
