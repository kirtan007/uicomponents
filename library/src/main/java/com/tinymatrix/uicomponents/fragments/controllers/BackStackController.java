package com.tinymatrix.uicomponents.fragments.controllers;

import android.support.v4.app.FragmentManager;

import com.tinymatrix.uicomponents.activities.Activity;

/**
 * Created by systemx on 10/6/17.
 */

public class BackStackController
{
    private Activity activity;
    public BackStackController(Activity activity)
    {
        this.activity = activity;
    }

    public void clearBackStack()
    {
        activity.disableExitAnimation = true;
        activity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        activity.disableExitAnimation = false;
    }

    public void noAnimPopBackStack()
    {
        activity.disableExitAnimation = true;
        activity.getSupportFragmentManager().popBackStackImmediate();
        activity.disableExitAnimation = false;
    }

    public void clearBackStackUpto(String removeFragmentUptoFragmentId)
    {
        activity.disableExitAnimation = true;
        activity.getSupportFragmentManager().popBackStackImmediate(removeFragmentUptoFragmentId, 0);
        activity.disableExitAnimation = false;
    }

    public void clearBackStackInclusive(String removeFragmentUptoFragmentId)
    {
        activity.disableExitAnimation = true;
        activity.getSupportFragmentManager().popBackStackImmediate(removeFragmentUptoFragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        activity.disableExitAnimation = false;
    }
}
