package com.tinymatrix.uicomponents.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.tinymatrix.uicomponents.fragments.IProgressView;

/**
 * Created by systemx on 26/6/17.
 */

public class CustomProgress extends FrameLayout implements IProgressView
{
    ProgressBar progressBar;

    public CustomProgress(@NonNull Context context)
    {
        super(context);
        init();
    }

    public CustomProgress(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomProgress(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminate(true);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(layoutParams);
        progressBar.setVisibility(GONE);
        addView(progressBar);
    }

    @Override
    public View getView()
    {
        return this;
    }

    @Override
    public void showProgress()
    {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void showRetry(String message, Drawable drawable, OnClickListener clickListener)
    {

    }

    @Override
    public void showRetry(int messageStringResId, int drawableResId, OnClickListener clickListener)
    {

    }

    @Override
    public void hideProgress()
    {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void showDataProgress()
    {
        progressBar.setVisibility(VISIBLE);
    }
}
