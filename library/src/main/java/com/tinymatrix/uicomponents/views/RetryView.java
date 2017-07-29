package com.tinymatrix.uicomponents.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.tinymatrix.uicomponents.R;

/**
 * Created by systemx on 28/6/17.
 */

public class RetryView extends FrameLayout
{
    AppCompatTextView tvMessage;
    AppCompatButton btnRetry;
    AppCompatImageView ivIcon;

    public RetryView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public RetryView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RetryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_retry, this, true);
        ivIcon = (AppCompatImageView) findViewById(R.id.v_retry_iv_icon);
        tvMessage = (AppCompatTextView) findViewById(R.id.v_retry_tv_message);
        btnRetry = (AppCompatButton) findViewById(R.id.v_retry_btn_retry);
    }

    public void setIcon(@DrawableRes int drawableResId)
    {
        Drawable drawable = ContextCompat.getDrawable(getContext(),drawableResId);
        setIcon(drawable);
    }

    public void setIcon(Drawable drawable)
    {
        ivIcon.setBackgroundDrawable(drawable);
    }

    public void setMessage(String message)
    {
        tvMessage.setText(message);
    }
    public void setMessage(@StringRes int messageResId)
    {
        setMessage(getContext().getString(messageResId));
    }

    public void setRetryClickListener(OnClickListener clickListener)
    {
        btnRetry.setOnClickListener(clickListener);
    }
}
