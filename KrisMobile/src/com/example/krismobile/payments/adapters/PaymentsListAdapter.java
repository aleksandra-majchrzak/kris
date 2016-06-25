package com.example.krismobile.payments.adapters;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.main.utilities.DateUtilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class PaymentsListAdapter extends BaseAdapter{
	
	private Context context;
	private List<Document> paymentsList;
	
	public PaymentsListAdapter(Context context, List<Document> paymentsList){
		
		this.context = context;
		this.paymentsList = paymentsList;
	}

	@Override
	public int getCount() {
		
		return paymentsList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return paymentsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0; //paymentsList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		Map<String, Object> props = this.paymentsList.get(position).getProperties();
			
		if(convertView == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.payment_row, null);
		}	

		TextView paymentNameTextView = (TextView) convertView.findViewById(R.id.payment_name_textView);
		paymentNameTextView.setText((String)props.get("PaymentName"));
			
		TextView paymentValueTextView = (TextView) convertView.findViewById(R.id.payment_value_textView);
		paymentValueTextView.setText(String.valueOf(props.get("Value")) +" "+ context.getResources().getString(R.string.PLN));
		
		Date paymentDate = new Date(Long.valueOf((String) props.get("PaymentDate")));
		
		TextView paymentDateTextView = (TextView) convertView.findViewById(R.id.payment_date_textView);
		paymentDateTextView.setText(DateUtilities.contvertDateToString(context, paymentDate));
		
		TextView paymentDescriptionTextView = (TextView) convertView.findViewById(R.id.payment_description_textView);
		paymentDescriptionTextView.setText((String)props.get("Description"));
		
		CheckBox paymentCheckBox = (CheckBox) convertView.findViewById(R.id.payment_checkBox);

		
		return convertView;
	}
	
	public void updateAdapter( List<Document> paymentsList){
		
		this.paymentsList.clear();
		this.paymentsList.addAll(paymentsList);
	}

}
