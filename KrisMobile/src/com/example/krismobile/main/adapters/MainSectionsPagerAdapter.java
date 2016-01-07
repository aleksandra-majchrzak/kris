package com.example.krismobile.main.adapters;

import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.krismobile.R;
import com.example.krismobile.main.MenuFragment;
import com.example.krismobile.main.SettingsFragment;


public class MainSectionsPagerAdapter extends FragmentPagerAdapter {
	
	private Context context;
	
	private final int POSITION_MENU = 0;
	//private final int POSITION_SETTINGS  = 1;

	public MainSectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {

		if(position == POSITION_MENU)
			return new MenuFragment();
		else //if (position == POSITION_SETTINGS)
			return new SettingsFragment();
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return context.getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
}
