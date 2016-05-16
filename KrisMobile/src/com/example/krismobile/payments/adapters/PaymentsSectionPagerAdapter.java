package com.example.krismobile.payments.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.krismobile.R;
import com.example.krismobile.payments.PaymentsFragment;

public class PaymentsSectionPagerAdapter extends FragmentPagerAdapter{
	
	private Context context;


	public PaymentsSectionPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		
		return PaymentsFragment.newInstance(position);
	}

	@Override
	public int getCount() {
			return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		
		switch (position) {
		case 0:
			return context.getString(R.string.all);
		case 1:
			return context.getString(R.string.due_payments);
		}
		return null;
	}

}