package com.example.krismobile.payments;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.PaymentsManager;
import com.example.krismobile.documents.DocumentActivity;
import com.example.krismobile.documents.DocumentsActivity;
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.documents.positions.DocumentPositionDialog;
import com.example.krismobile.documents.positions.DocumentPositionObserver;
import com.example.krismobile.main.base.FragmentBase;
import com.example.krismobile.payments.adapters.PaymentsListAdapter;
import com.example.krismobile.payments.entities.Payment;


public class PaymentsFragment extends FragmentBase{
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public final static int TAB_DUE = 0;
	public final static int TAB_FINALIZED = 1;
	public final static int UPDATE_PAYMENTS = 3;
	private int currentTab;

	private ListView paymentsList;
	private PaymentsListAdapter duePaymentsAdapter;
	private PaymentsListAdapter settledPaymentsAdapter;
	
	public PaymentsFragment() {
	}
	
	public static PaymentsFragment newInstance(int sectionNumber){
		
		PaymentsFragment fragment = new PaymentsFragment();
		fragment.setCurrentTab(sectionNumber);
		
		Bundle args = new Bundle();
		
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		duePaymentsAdapter = new PaymentsListAdapter(this.context, PaymentsManager.getInstance().getDuePayments());
		settledPaymentsAdapter = new PaymentsListAdapter(this.context, PaymentsManager.getInstance().getFinalizedPayments());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_add_payment) {
			
			Payment payment = new Payment();
			
			Bundle paymentBundle = new Bundle();
			paymentBundle.putParcelable("payment", payment);
			paymentBundle.putBoolean("isNew", true);
			
			 DialogFragment newFragment = new PaymentDialog();
			 
			 newFragment.setTargetFragment(this, UPDATE_PAYMENTS);
			 newFragment.setArguments(paymentBundle);
		     newFragment.show(getFragmentManager(), "dialog");
		        
			return true;
			
		}
		else if(id == android.R.id.home){
	        NavUtils.navigateUpFromSameTask((Activity)context);
	        return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_payments,
				container, false);
		
		paymentsList = (ListView) rootView.findViewById(R.id.payments_listView);
		
		if(currentTab == TAB_DUE)
			paymentsList.setAdapter(duePaymentsAdapter);
		else
			paymentsList.setAdapter(settledPaymentsAdapter);
		 
		return rootView;
	}
	
	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}
	
	public void refreshAdapter(int adapterNumber){
		
		if(currentTab == TAB_DUE){
			
			duePaymentsAdapter.updateAdapter(PaymentsManager.getInstance().getDuePayments());
			duePaymentsAdapter.notifyDataSetChanged();
			
		} else{
			
			settledPaymentsAdapter.updateAdapter(PaymentsManager.getInstance().getFinalizedPayments());
			settledPaymentsAdapter.notifyDataSetChanged();
			
		}		
	}
}
