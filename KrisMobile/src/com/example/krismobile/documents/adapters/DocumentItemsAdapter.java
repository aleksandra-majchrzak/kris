package com.example.krismobile.documents.adapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.documents.DocumentActivity;
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.documents.positions.DocumentPositionsList;
import com.example.krismobile.main.utilities.DateUtilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DocumentItemsAdapter extends  BaseAdapter{
	
	private Context context;
	private ArrayList<Document> documentItemsList;
	private DocumentPositionsList positions;
	
	public DocumentItemsAdapter(Context context, ArrayList<Document> documentsList, DocumentPositionsList positions){
		
		this.context = context;
		this.documentItemsList = documentsList;
		this.positions = positions;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.documentItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.documentItemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getIndexOf(String id) {
        for(int i = 0; i < this.documentItemsList.size(); i++) {
            if(this.documentItemsList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
	
	@Override
	public boolean isEnabled(int position){
		if( ! ((DocumentActivity)context).getIsBeingEdited())
			return false;
		
		return true;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		Map<String, Object> props = this.documentItemsList.get(position).getProperties();
			
		if(convertView == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.document_item_row, null);
		}	


		// tu powinno byc tez ustawianie zdjecia przedmioty- musisz wykombinowac jaka inna grafike jesli nie ma zdjecia
		
		//jak zrobic jakiegoc joina zeby wyciagnac nazwe z id konhtrahenta ??  a moze w ogole powinnam to inaczej zrobic??
		TextView documentItemCodeTextView = (TextView) convertView.findViewById(R.id.document_item_code_textView);
		documentItemCodeTextView.setText("["+(String)props.get("Code")+"] "+(String)props.get("Name"));
			
		TextView documentItemStocksTextView = (TextView) convertView.findViewById(R.id.document_item_stocks_textView);
		// tu musze miec info o zasobach
		documentItemStocksTextView.setText((String)props.get("ContractorCode"));
		
		TextView documentItemPricesTextView = (TextView) convertView.findViewById(R.id.document_item_price_textView);
		documentItemPricesTextView.setText(String.valueOf(props.get("NetPrice")) + context.getString(R.string.net)
											+ "  "+String.valueOf(props.get("GrossPrice")) + context.getString(R.string.gross));
		
		
		// tu mosze jakos brac z pozycji
		DocumentPosition currentPosition = positions.getByItemId((String)props.get("_id"));
		if(currentPosition != null){
			
			((LinearLayout) convertView.findViewById(R.id.document_item_values_ll)).setVisibility(View.VISIBLE);
			
			TextView documentItemQuantityTextView = (TextView) convertView.findViewById(R.id.document_item_quantity_textView);
			documentItemQuantityTextView.setText(String.valueOf(currentPosition.getQuantity()));
		
			TextView documentItemValuesTextView = (TextView) convertView.findViewById(R.id.document_item_value_textView);
			documentItemValuesTextView.setText(String.valueOf(currentPosition.getNetValue()) + context.getString(R.string.net)
					+ "  "+String.valueOf(currentPosition.getGrossValue()) + context.getString(R.string.gross));
		}
		else{
			((LinearLayout) convertView.findViewById(R.id.document_item_values_ll)).setVisibility(View.GONE);
		}


		
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged(){
		
		// tu musze uwzglednic dodawanie posycji - miec dostep do lity pozycji
//		documentItemsList =  ItemsManager.getInstance().getAllItems(); // DocumentsManager.getInstance().getAllKrisDocuments();
		
		super.notifyDataSetChanged();
	}

}
