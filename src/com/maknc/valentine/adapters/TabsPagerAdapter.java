package com.maknc.valentine.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maknc.valentine.CatListFragment;
import com.maknc.valentine.StarListFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	Fragment mFragment = null;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		
		switch (position) {
		case 0:
			mFragment = new CatListFragment();
			break;
		case 1:
			mFragment = new StarListFragment();
			break;
		}
		return mFragment;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		// CHECK THIS !!! - or �java.lang.IllegalStateException: Can't change tag of fragment�
		return 2;
	}

}
