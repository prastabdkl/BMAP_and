package com.example.prastabdkl.bmap.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Prastab Dhakal on 12/16/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    int mNumOftabs;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragements(Fragment fragments, String titles)
    {
        this.fragments.add(fragments);
        this.tabTitles.add(titles);
    }
    public ViewPagerAdapter(FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.mNumOftabs = numOfTabs;
    }
    @Override
    public Fragment getItem(int position)
    {

        return fragments.get(position);
    }

    @Override
    public int getCount()
    {

        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles.get(position);
    }

}
