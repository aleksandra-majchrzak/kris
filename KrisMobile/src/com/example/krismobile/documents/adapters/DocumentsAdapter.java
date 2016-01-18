package com.example.krismobile.documents.adapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.ContractorsManager;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.main.utilities.DateUtilities;

public class DocumentsAdapter extends  BaseAdapter{
	
	protected Context context;
	protected ArrayList<Document> documentsList;
	
	public DocumentsAdapter(Context context, ArrayList<Document> documentsList){
		
		this.context = context;
		this.documentsList = documentsList;
	}
	
	@Override
	public int getCount() {
		
		return this.documentsList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return this.documentsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0; //this.contractorsList.get(position).get;
	}
	
	public int getIndexOf(String id) {
        for(int i = 0; i < this.documentsList.size(); i++) {
            if(this.documentsList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		Map<String, Object> props = this.documentsList.get(position).getProperties();
			
		if(convertView == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.document_row, null);
		}	
/*		
		if(((Integer)(props.get("TypeId"))).intValue() == ContractorsManager.SELLER_TYPE){
				
			//ustawienie obrazka dla sprzedawcy
				
		} else{
			//ustawienie obrazka dla kupca
				
		}
*/			
		//jak zrobic jakiegoc joina zeby wyciagnac nazwe z id konhtrahenta ??  a moze w ogole powinnam to inaczej zrobic??
		TextView documentNumberTextView = (TextView) convertView.findViewById(R.id.document_number_textView);
		documentNumberTextView.setText((String)props.get("Number"));
			
		TextView documentContractorCodeTextView = (TextView) convertView.findViewById(R.id.document_contractor_textView);
		documentContractorCodeTextView.setText((String)props.get("ContractorCode"));
		
		Date documentDate = new Date((Long)props.get("DocumentDate"));
		
		TextView documentDateTextView = (TextView) convertView.findViewById(R.id.document_date_textView);
		documentDateTextView.setText(DateUtilities.contvertDateToString(context, documentDate));

		
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged(){
		
		documentsList = DocumentsManager.getInstance().getAllKrisDocuments();
		
		super.notifyDataSetChanged();
	}
}