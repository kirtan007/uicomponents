package com.tinymatrix.uicomponents.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tinymatrix.uicomponents.models.FullScreenType;

/**
 * Created by kirtan on 2/10/15.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SmartDialog
{
    int layoutResId() default -1;

    boolean cancellable() default true;

    FullScreenType fullScreenType() default FullScreenType.WITHOUT_TITLE;

    String title() default "";

    boolean enableButterKnife() default true;

    boolean enableEventBus() default false;
}
