package com.example.krismobile.main.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

public class FragmentBase extends Fragment{
	protected Context context;
	
	@Override
	public void onAttach(Activity activity) {

	    super.onAttach(activity);
	    this.context=activity;
	}
	
	public Context getContext(){
		return this.context;
	}
}
