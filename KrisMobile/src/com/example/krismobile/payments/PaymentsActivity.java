package com.example.krismobile.payments;

import com.example.krismobile.R;
import com.example.krismobile.payments.adapters.PaymentsSectionPagerAdapter;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class PaymentsActivity extends Activity implements TabListener {


	PaymentsSectionPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	
	private int activePage = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payments);
		
		mSectionsPagerAdapter = new PaymentsSectionPagerAdapter(getFragmentManager(), this);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	getActionBar().setSelectedNavigationItem(position);
            }
        });
		
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			
			getActionBar().addTab(
					getActionBar().newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
		
		mViewPager.setCurrentItem(activePage);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.payments, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if(id == android.R.id.home){
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

}
