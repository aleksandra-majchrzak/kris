package com.example.krismobile.documents.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.krismobile.R;
import com.example.krismobile.documents.DocumentFragment;

public class DocumentSectionsPagerAdapter extends FragmentPagerAdapter{
	
	private Context context;

	public DocumentSectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
/*
		if(position == POSITION_ITEMS)		// czy powinnam to robic jako osobne fragmenty czy tylko jako osobne widoki??
			return new DocumentFragment();
		else //if (position == POSITION_SETTINGS)
			return new SettingsFragment();
*/		
		return DocumentFragment.newInstance(position);
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.items);
		case 1:
			return context.getString(R.string.positions);
		case 2:
			return context.getString(R.string.header);
		}
		return null;
	}
}
