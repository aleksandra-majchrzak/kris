package com.example.krismobile.contractors;

import java.util.Map;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.R.layout;
import com.example.krismobile.contractors.adapters.ContractorsAdapter;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.database.managers.ContractorsManager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ContractorsFragment extends Fragment {
	
	private Context context;
	private ListView contractorsListView;

	public ContractorsFragment() {
	}
	
	@Override
	public void onAttach(Activity activity) {

	    super.onAttach(activity);
	    this.context=activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_contractors,
				container, false);
		
		contractorsListView = (ListView)rootView.findViewById(R.id.contractors_listView);
		
//		Contractor contractor = new Contractor(1, "Cont1", 1, "address", "description", "NIP");
		
		ContractorsManager manager = new ContractorsManager();
/*		
		String documentId = manager.saveContractor(contractor);
		
		Document doc = manager.getContractor(documentId);
		
		TextView textView = (TextView) rootView.findViewById(R.id.contractorsTextView);
		
		Map<?, ?> properties = doc.getProperties();
		
		textView.setText(properties.get("contractorCode")+" "+ properties.get("contractorAddress"));
*/
		ContractorsAdapter adapter = new ContractorsAdapter(context, manager.getAllContractors());
		
		contractorsListView.setAdapter(adapter);
		
		return rootView;
	}
}