package com.example.krismobile.contractors;

import java.util.Map;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.R.layout;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.database.managers.ContractorsManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContractorsFragment extends Fragment {

	public ContractorsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contractors,
				container, false);
		
		Contractor contractor = new Contractor(1, "Cont1", 1, "address", "description", "NIP");
		
		ContractorsManager manager = new ContractorsManager();
		
		String documentId = manager.saveContractor(contractor);
		
		Document doc = manager.getContractor(documentId);
		
		TextView textView = (TextView) rootView.findViewById(R.id.contractorsTextView);
		
		Map<?, ?> properties = doc.getProperties();
		
		textView.setText(properties.get("contractorCode")+" "+ properties.get("contractorAddress"));
		
		return rootView;
	}
}