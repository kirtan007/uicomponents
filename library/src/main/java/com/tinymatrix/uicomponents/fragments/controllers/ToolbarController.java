package com.tinymatrix.uicomponents.fragments.controllers;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.activities.Activity;
import com.tinymatrix.uicomponents.models.ToolbarStyle;

/**
 * Created by systemx on 25/6/17.
 */

public class ToolbarController
{
    private Activity activity;
    public ToolbarController(Activity activity)
    {
        this.activity = activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public void setToolbarTitle(@StringRes int title)
    {
        this.activity.getToolbar().setTitle(activity.getString(title));
    }

    public void setToolbarTitle(String title)
    {
        this.activity.getToolbar().setTitle(title);
    }

    public void configureToolbar(ToolbarStyle toolbarStyle)
    {
        switch (toolbarStyle)
        {
            case NONE:
            {
                configureNoToolbar();
                break;
            }
            case NORMAL_TOOLBAR:
            {
                configureNonCollapsibleToolbar();
                break;
            }
            case COLLAPSIBLE_TOOLBAR:
            {
                configureCollapsibleToolbar();
                break;
            }
        }
    }

    private int getPrimaryColor(Context context)
    {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public void configureNoToolbar()
    {
        removeAppBar();
    }

    private void removeAppBar()
    {
        CoordinatorLayout coordinatorLayout =activity.getCoordinatorLayout();
        AppBarLayout appBarLayout = (AppBarLayout) coordinatorLayout.findViewById(R.id.a_activity_appbar);
        if (appBarLayout != null)
        {
            coordinatorLayout.removeView(appBarLayout);
        }
    }

    public void configureNonCollapsibleToolbar()
    {
        CoordinatorLayout coordinatorLayout =activity.getCoordinatorLayout();
        coordinatorLayout.setFitsSystemWindows(false);
        removeAppBar();

        /* Inflate Layout */
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup nonCollapsibleAppbar= (ViewGroup) layoutInflater.inflate(R.layout.v_non_collapsible_layout, coordinatorLayout, true);
        Toolbar toolbarObj = (Toolbar) nonCollapsibleAppbar.findViewById(R.id.a_activity_toolbar);
        toolbarObj.setTitle("");
        ViewGroup toolbarContent = (ViewGroup)nonCollapsibleAppbar.findViewById(R.id.a_activity_appbar_content);
        //RelativeLayout flContent = (RelativeLayout) collapsibleLayout.findViewById(R.id.v_non_collapsible_layout_fl_content);
        // AppBarLayout newAppBarLayout = (AppBarLayout) collapsibleLayout.findViewById(R.id.appbar);
        activity.setSupportActionBar(toolbarObj);
        activity.setupToolbar(toolbarObj, toolbarContent);
    }

    public void configureCollapsibleToolbar()
    {
        CoordinatorLayout coordinatorLayout =activity.getCoordinatorLayout();
        removeAppBar();

        /* Inflate Layout */
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View collapsibleLayout = layoutInflater.inflate(R.layout.v_collipsible_layout, coordinatorLayout, true);
        Toolbar toolbarObj = (Toolbar) collapsibleLayout.findViewById(R.id.a_activity_toolbar);
        ViewGroup toolbarContent = (ViewGroup)collapsibleLayout.findViewById(R.id.a_activity_appbar_content);

        toolbarObj.setTitle("");
        activity.setSupportActionBar(toolbarObj);
        activity.setupToolbar(toolbarObj,toolbarContent);
    }

    public void onPrepareOptionsMenu(Menu menu)
    {
        for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++)
        {
            menu.getItem(menuItemIndex).setEnabled(isToolbarEnabled);
        }
    }
    private boolean isToolbarEnabled=true;
    public void disableViews()
    {
        isToolbarEnabled = false;
    }

    public void enableViews()
    {
        isToolbarEnabled = true;
    }
}
