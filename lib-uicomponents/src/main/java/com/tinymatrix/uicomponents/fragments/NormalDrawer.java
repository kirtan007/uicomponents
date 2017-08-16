package com.tinymatrix.uicomponents.fragments;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by systemx on 26/6/17.
 */

public class NormalDrawer implements IDrawer
{
    DrawerLayout drawerLayout;
    FrameLayout fragmentLayout;

    public NormalDrawer(ViewGroup viewGroup)
    {
        Context context = viewGroup.getContext();
        drawerLayout = new DrawerLayout(context);
        drawerLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        fragmentLayout = new FrameLayout(context);
        fragmentLayout.setBackgroundColor(0xFFFFFFFF);
        DrawerLayout.LayoutParams layoutParams = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.LEFT;
        fragmentLayout.setLayoutParams(layoutParams);

        drawerLayout.addView(viewGroup);
        drawerLayout.addView(fragmentLayout);
    }

    @Override
    public void openDrawer()
    {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void closeDrawer()
    {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public View getView()
    {
        return drawerLayout;
    }

    @Override
    public ViewGroup getMenuView()
    {
        return fragmentLayout;
    }
}
