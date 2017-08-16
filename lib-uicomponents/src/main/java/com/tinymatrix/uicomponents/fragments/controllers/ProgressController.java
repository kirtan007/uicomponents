package com.tinymatrix.uicomponents.fragments.controllers;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;

import com.tinymatrix.uicomponents.fragments.IProgressView;

/**
 * Created by systemx on 17/6/17.
 */

public class ProgressController
{
    private IProgressView progressView;
    private ViewGroup progressContainerViewGroup;
    private int progressResId;
    private IProgressListener progressListener;

    public ProgressController(ViewGroup progressContainerViewGroup, @IdRes int progressResId)
    {
        this.progressContainerViewGroup = progressContainerViewGroup;
        this.progressResId = progressResId;
    }

    public interface IProgressListener
    {
        void onShow();
        void onHide();
    }

    public void setProgressListener(IProgressListener progressListener)
    {
        this.progressListener = progressListener;
    }

    public void setProgressView(IProgressView progressView)
    {
        /* Replace Progress View */
        IProgressView previousProgressView = (IProgressView) progressContainerViewGroup.findViewById(progressResId);
        if(previousProgressView!=null)
        {
            progressContainerViewGroup.removeView(previousProgressView.getView());
        }
        progressContainerViewGroup.addView(progressView.getView());
        this.progressView = progressView;
    }

    public void showDataProgress()
    {
        if(progressListener!=null)
        {
            progressListener.onShow();
        }
        this.progressView.showDataProgress();
    }

    public void showProgress()
    {
        if(progressListener!=null)
        {
            progressListener.onShow();
        }
        progressView.showProgress();
    }
    public void showRetry(String message, Drawable drawable, View.OnClickListener clickListener)
    {
        progressView.showRetry(message,drawable,clickListener);
    }
    public void showRetry(@StringRes  int messageStringResId, @DrawableRes int drawableResId, View.OnClickListener clickListener)
    {
        progressView.showRetry(messageStringResId,drawableResId,clickListener);
    }

    public void hideProgress()
    {
        if(progressListener!=null)
        {
            progressListener.onHide();
        }
        this.progressView.hideProgress();
    }
}
