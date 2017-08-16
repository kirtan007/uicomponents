package com.tinymatrix.uicomponents.views;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.fragments.IProgressView;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by systemx on 25/6/17.
 */

public class ProgressView extends FrameLayout implements IProgressView
{
    private FrameLayout flDataProgress;
    private FrameLayout flNormalProgress;
    private RetryView vRetry;
    private ProgressBar pbData;
    private ProgressBar pbNormalProgress;

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
        pbData = (ProgressBar)findViewById(R.id.v_progress_pb_loading_data);
        pbNormalProgress= (ProgressBar)findViewById(R.id.v_progress_pb_normal);

        /*pbData.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(getPrimaryColor(getContext())),
                android.graphics.PorterDuff.Mode.SRC_IN);

        pbNormalProgress.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(getPrimaryColor(getContext())),
                android.graphics.PorterDuff.Mode.SRC_IN);*/

      /*  ViewCompat.setBackgroundTintList(pbData,ColorStateList.valueOf(getPrimaryColor(getContext())));
        ViewCompat.setBackgroundTintMode(pbData, PorterDuff.Mode.SRC_IN);

        ViewCompat.setBackgroundTintList(pbNormalProgress,ColorStateList.valueOf(getPrimaryColor(getContext())));
        ViewCompat.setBackgroundTintMode(pbNormalProgress, PorterDuff.Mode.SRC_IN);*/

        vRetry = (RetryView) findViewById(R.id.v_progress_v_retry);
        this.setVisibility(GONE);
    }

   /* private int getPrimaryColor(Context context)
    {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }*/

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
