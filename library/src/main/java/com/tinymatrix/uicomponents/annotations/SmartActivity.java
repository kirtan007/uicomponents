package com.tinymatrix.uicomponents.annotations;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.tinymatrix.uicomponents.models.ToolbarStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kirtan on 9/30/15.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SmartActivity
{
    @LayoutRes int layoutResId();

    @IdRes int fragmentContainerResId() default android.R.id.content;

    boolean enableEventBus() default false;

    boolean enableButterKnife() default true;

    ToolbarStyle toolbarStyle() default ToolbarStyle.NONE;

    boolean fullScreen() default false;

    boolean enableDrawer() default false;

}

