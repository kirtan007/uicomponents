package com.tinymatrix.uicomponents.demo;

import android.os.Bundle;
import android.view.ViewGroup;

import com.tinymatrix.uicomponents.activities.Activity;
import com.tinymatrix.uicomponents.annotations.SmartActivity;

/**
 * Created by systemx on 25/6/17.
 */

@SmartActivity(layoutResId = R.layout.a_fragment_test,fragmentContainerResId = R.id.a_fragment_test_main_container)
public class FragmentActivityTest extends Activity
{
    @Override
    public void setUpUI(Bundle savedInstanceState)
    {
        showFragment(new FragmentA());
    }

    @Override
    public void attachEventListeners()
    {

    }

    @Override
    public void setupToolbar(android.support.v7.widget.Toolbar toolbarObj, ViewGroup contentView)
    {

    }

    public void startFragmentB()
    {
        showFragment(new FragmentB());
    }
}
