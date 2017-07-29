package com.tinymatrix.uicomponents.fragments;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by systemx on 26/6/17.
 */

public interface IDrawer
{
     void openDrawer();
     void closeDrawer();
     View getView();
     ViewGroup getMenuView();
}
