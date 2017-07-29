package com.tinymatrix.uicomponents.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import com.tinymatrix.uicomponents.R;

/**
 * Created by kirtan on 23/04/15.
 */
public class ToolbarUtils
{
    public static TextView getActionBarTextView(Toolbar toolbar)
    {
        TextView titleTextView = null;
        try
        {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
        }
        catch (Exception e)
        {
        }
        return titleTextView;
    }

    public static void setTitleFont(Context context, Toolbar toolbar, @DimenRes int fontSize)
    {
        TextView title = getActionBarTextView(toolbar);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(fontSize));
    }

    public static void setTitleFont(Context context, Toolbar toolbar, Typeface typeface, @DimenRes int fontSize)
    {
        TextView title = getActionBarTextView(toolbar);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(fontSize));
        title.setTypeface(typeface);
    }

    public static void reset(Toolbar toolbar, Context context)
    {
        toolbar.setTitle("");
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setLogo(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
        resetToolBarFlags(toolbar);
    }

    public static void setToolbarElevation(AppBarLayout appBarLayout,int elevation)
    {
        appBarLayout.setElevation(elevation);
    }

    public static void setBackButtonWithColor(@ColorRes int color, Toolbar toolbar)
    {
        Drawable upArrow = ResourcesCompat.getDrawable(toolbar.getResources(), R.drawable.ic_back_arrow, null);
        upArrow.setColorFilter(ContextCompat.getColor(toolbar.getContext(), color), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);

    }

    public static void enableScrollFlags(Toolbar toolbar)
    {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
    }

    public static void resetToolBarFlags(Toolbar toolbar)
    {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);
    }
}
