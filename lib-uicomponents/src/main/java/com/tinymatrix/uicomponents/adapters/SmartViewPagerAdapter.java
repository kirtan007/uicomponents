package com.tinymatrix.uicomponents.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.tinymatrix.uicomponents.fragments.ViewPagerFragment;

/**
 * Created by admin on 12/1/15.
 */
public class SmartViewPagerAdapter extends FragmentPagerAdapter
{
    protected List<ViewPagerFragment> fragmentList = new ArrayList<>();

    public SmartViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return fragmentList.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    public void addFragment(ViewPagerFragment fragment)
    {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragments(List<ViewPagerFragment> fragmentList)
    {
        this.fragmentList.addAll(fragmentList);
        notifyDataSetChanged();
    }

    public void clearAll()
    {
        fragmentList.clear();
        notifyDataSetChanged();
    }
}
