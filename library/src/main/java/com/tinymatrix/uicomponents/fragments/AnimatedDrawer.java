package com.tinymatrix.uicomponents.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lemonade.widgets.slidesidemenu.SlideSideMenuContentCardView;
import com.lemonade.widgets.slidesidemenu.SlideSideMenuTransitionLayout;

/**
 * Created by systemx on 26/6/17.
 */

public class AnimatedDrawer implements IDrawer
{
    private ViewGroup rootView;
    private SlideSideMenuTransitionLayout slideSideMenuTransitionLayout;
    private LinearLayout flMenuContent;

    public AnimatedDrawer(ViewGroup viewGroup)
    {
        Context context = viewGroup.getContext();
        this.rootView = viewGroup;
        slideSideMenuTransitionLayout = new SlideSideMenuTransitionLayout(context);
        slideSideMenuTransitionLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        SlideSideMenuContentCardView slideSideMenuContentCardView = new SlideSideMenuContentCardView(context);
        slideSideMenuContentCardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slideSideMenuContentCardView.addView(rootView);

        flMenuContent = new LinearLayout(context);
        LinearLayout.LayoutParams linearLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flMenuContent.setLayoutParams(linearLayoutParam);

        slideSideMenuTransitionLayout.setMenuLayout(flMenuContent);
        slideSideMenuTransitionLayout.setContentLayout(slideSideMenuContentCardView);
    }

    @Override
    public void openDrawer()
    {
        slideSideMenuTransitionLayout.openSideMenu();
    }

    @Override
    public void closeDrawer()
    {
        slideSideMenuTransitionLayout.closeSideMenu();
    }

    @Override
    public View getView()
    {
        return slideSideMenuTransitionLayout;
    }

    @Override
    public ViewGroup getMenuView()
    {
        return flMenuContent;
    }
}
