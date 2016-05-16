package com.example.krismobile.payments;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.main.base.FragmentBase;
import com.example.krismobile.payments.adapters.PaymentsListAdapter;


public class PaymentsFragment extends FragmentBase{
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private ListView paymentsList;
	
	public PaymentsFragment() {
	}
	
public static PaymentsFragment newInstance(int sectionNumber){
		
	PaymentsFragment fragment = new PaymentsFragment();
		
		Bundle args = new Bundle();
		
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_payments,
				container, false);
		
		paymentsList = (ListView) rootView.findViewById(R.id.payments_listView);
		// musisz napisac managera do platnosci
		paymentsList.setAdapter(new PaymentsListAdapter(this.context, new ArrayList<Document>()));
		 
		return rootView;
	}
	
}
