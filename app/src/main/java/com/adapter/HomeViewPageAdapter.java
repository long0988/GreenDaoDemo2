package com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 首页viewpage适配器
 * 
 */
public class HomeViewPageAdapter extends FragmentPagerAdapter {
	
	private ArrayList<Fragment> mFragments;
	private String[] mTitles;
	
	public HomeViewPageAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public HomeViewPageAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
		super(fm);
		mFragments = fragments;
		mTitles=titles;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}
}
