package com.tinymatrix.uicomponents.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.tinymatrix.uicomponents.fragments.ViewPagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SmartViewPagerAdapter extends FragmentPagerAdapter
{
    protected List<ViewPagerFragment> fragmentList = new ArrayList<>();
    private Map<Integer, String> fragmentTagMap;
    private FragmentManager fragmentManager;

    public SmartViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragmentTagMap = new HashMap<>();
        this.fragmentManager = fm;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return fragmentList.get(position).getTitle();
    }

    @Override
    public ViewPagerFragment getItem(int position)
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

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment)
        {
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            fragmentTagMap.put(position, tag);
        }
        return obj;
    }

    public Fragment getFragment(int position)
    {
        String tag = fragmentTagMap.get(position);
        if (tag == null)
        {
            return null;
        }
        return fragmentManager.findFragmentByTag(tag);
    }
}
