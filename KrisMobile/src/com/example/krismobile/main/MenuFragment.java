package com.example.krismobile.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krismobile.R;

public class MenuFragment extends Fragment {

	public MenuFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_menu, container,
				false);
		
		rootView.setBackgroundResource(R.drawable.blank_background2);
		
		return rootView;
	}
}