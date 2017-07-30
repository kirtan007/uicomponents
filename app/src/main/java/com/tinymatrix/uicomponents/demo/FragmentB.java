package com.tinymatrix.uicomponents.demo;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tinymatrix.uicomponents.annotations.SmartFragment;
import com.tinymatrix.uicomponents.demo.R;
import com.tinymatrix.uicomponents.fragments.Fragment;
import com.tinymatrix.uicomponents.models.ToolbarStyle;

/**
 * Created by systemx on 13/5/17.
 */

@SmartFragment(layoutResId = R.layout.f_b,toolbarStyle = ToolbarStyle.COLLAPSIBLE_TOOLBAR)
public class FragmentB extends Fragment
{
    @Override
    public void setUpUI(Bundle savedInstanceState)
    {
      //  Toolbar toolbar = ((Activity)getActivity()).getToolbar();
      //  toolbar.setTitle("Fragment B");
       Application application= App.app;
    }

    @Override
    public void attachEventListeners()
    {

    }

    @Override
    public void setupToolbar(Toolbar toolbar)
    {

    }
}
