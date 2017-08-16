package com.tinymatrix.uicomponents.fragments.controllers;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by admin on 6/22/16.
 */

public class KeyboardController
{
    private FragmentActivity fragmentActivity;

    public KeyboardController(FragmentActivity fragmentActivity)
    {
        this.fragmentActivity = fragmentActivity;
    }

    public void showKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public void hideKeyboard()
    {
        View view = fragmentActivity.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
