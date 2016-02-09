package com.example.krismobile.documents.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.krismobile.R;
import com.example.krismobile.documents.DocumentFragment;

public class DocumentSectionsPagerAdapter extends FragmentPagerAdapter{
	
	private Context context;
	private boolean isDocumentEditing = false;

	public DocumentSectionsPagerAdapter(FragmentManager fm, Context context, boolean isDocumentEditing) {
		super(fm);
		
		this.context = context;
		this.isDocumentEditing = isDocumentEditing;
	}

	@Override
	public Fragment getItem(int position) {
	
//		if(! isDocumentEditing)
//			position +=1;
		
		return DocumentFragment.newInstance(position);
	}

	@Override
	public int getCount() {
		
//		if(isDocumentEditing)
			return 3;
//		else
//			return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// Locale l = Locale.getDefault();
		
//		if(! isDocumentEditing)
//			position +=1;
		
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

	public boolean isDocumentEditing() {
		return isDocumentEditing;
	}

	public void setDocumentEditing(boolean isDocumentEditing) {
		this.isDocumentEditing = isDocumentEditing;
	}
}
