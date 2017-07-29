package com.tinymatrix.uicomponents.views;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.fragments.IProgressView;

/**
 * Created by systemx on 25/6/17.
 */

public class ProgressView extends FrameLayout implements IProgressView
{
    private FrameLayout flDataProgress;
    private FrameLayout flNormalProgress;
    private RetryView vRetry;

    public ProgressView(@NonNull Context context)
    {
        super(context);
        init(context);
    }

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context)
    {
        setLayoutTransition(new LayoutTransition());
        setClickable(true);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_progress, this, true);
        flDataProgress = (FrameLayout) findViewById(R.id.v_progress_fl_data_progress);
        flNormalProgress = (FrameLayout) findViewById(R.id.v_progress_fl_progress);
        vRetry = (RetryView) findViewById(R.id.v_progress_v_retry);
        this.setVisibility(GONE);
    }

    @Override
    public View getView()
    {
        return this;
    }

    @Override
    public void showProgress()
    {
        this.setVisibility(VISIBLE);
        flNormalProgress.setVisibility(VISIBLE);
        flDataProgress.setVisibility(GONE);
        vRetry.setVisibility(GONE);
        vRetry.setRetryClickListener(null);
    }

    @Override
    public void showRetry(String message, Drawable drawable, OnClickListener clickListener)
    {
        this.setVisibility(VISIBLE);
        flNormalProgress.setVisibility(GONE);
        flDataProgress.setVisibility(GONE);
        vRetry.setVisibility(VISIBLE);
        vRetry.setIcon(drawable);
        vRetry.setMessage(message);
        vRetry.setRetryClickListener(clickListener);
    }

    @Override
    public void showRetry(int messageStringResId, int drawableResId, OnClickListener clickListener)
    {
        String message = getContext().getString(messageStringResId);
        Drawable drawable = ContextCompat.getDrawable(getContext(),drawableResId);
        showRetry(message,drawable,clickListener);
    }

    @Override
    public void hideProgress()
    {
        this.setVisibility(GONE);
        hideAll();
        vRetry.setRetryClickListener(null);
    }

    @Override
    public void showDataProgress()
    {
        this.setVisibility(VISIBLE);
        flDataProgress.setVisibility(VISIBLE);
        flNormalProgress.setVisibility(GONE);
        vRetry.setVisibility(GONE);
        vRetry.setRetryClickListener(null);
    }

    public void hideAll()
    {
        flDataProgress.setVisibility(GONE);
        flNormalProgress.setVisibility(GONE);
        vRetry.setVisibility(GONE);
        vRetry.setRetryClickListener(null);
    }
}
