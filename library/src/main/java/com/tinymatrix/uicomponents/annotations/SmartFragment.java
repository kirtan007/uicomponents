package com.tinymatrix.uicomponents.annotations;

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
public @interface SmartFragment
{
    @LayoutRes int layoutResId();
    boolean enableEventBus() default false;
    boolean enableButterKnife() default true;
    boolean enableFragmentArgs() default false;
    ToolbarStyle toolbarStyle() default ToolbarStyle.NONE;
}
