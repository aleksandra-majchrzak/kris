package com.example.krismobile.contractors.adapters;

import java.util.ArrayList;
import java.util.Map;

import com.couchbase.lite.Document;
import com.example.krismobile.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContractorsAdapter extends  BaseAdapter{
	
	private Context context;
	private ArrayList<Document> contractorsList;
	
	private final int SELLER_TYPE = 1;
	private final int BUYER_TYPE = 2;
	
	public ContractorsAdapter(Context context, ArrayList<Document> contractorsList){
		
		this.context = context;
		this.contractorsList = contractorsList;
	}
	
	@Override
	public int getCount() {
		
		return this.contractorsList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return this.contractorsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0; //this.contractorsList.get(position).get;
	}
	
	public int getIndexOf(String id) {
        for(int i = 0; i < this.contractorsList.size(); i++) {
            if(this.contractorsList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			
			Map<String, Object> props = this.contractorsList.get(position).getProperties();

			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.contractor_row, null);
			
			if(((Integer)(props.get("contractorTypeId"))).intValue() == SELLER_TYPE){
				
				//ustawienie obrazka dla sprzedawcy
				
			} else{
				//ustawienie obrazka dla kupca
				
			}
			
			TextView contractorCodeTextView = (TextView) convertView.findViewById(R.id.contractor_code_textView);
			contractorCodeTextView.setText((String)props.get("contractorCode"));
			
			TextView contractorAddressTextView = (TextView) convertView.findViewById(R.id.contractor_address_textView);
			contractorAddressTextView.setText((String)props.get("contractorAddress"));
			
		}
		
		return convertView;
	}

}
