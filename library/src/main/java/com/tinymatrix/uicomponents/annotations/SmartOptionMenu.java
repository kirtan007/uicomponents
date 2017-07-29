package com.tinymatrix.uicomponents.annotations;

import android.support.annotation.MenuRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kirtan on 1/10/15.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SmartOptionMenu
{
    @MenuRes int menuResId();
}
