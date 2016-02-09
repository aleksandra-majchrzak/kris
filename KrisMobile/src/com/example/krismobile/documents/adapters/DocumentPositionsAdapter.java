package com.example.krismobile.documents.adapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.main.utilities.DateUtilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DocumentPositionsAdapter extends  BaseAdapter{

	private Context context;
	private ArrayList<DocumentPosition> documentPositionsList;
	
	public DocumentPositionsAdapter(Context context, ArrayList<DocumentPosition> documentsList){
		
		this.context = context;
		this.documentPositionsList = documentsList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.documentPositionsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.documentPositionsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getIndexOf(String id) {
        for(int i = 0; i < this.documentPositionsList.size(); i++) {
            if(this.documentPositionsList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		
		DocumentPosition position = this.documentPositionsList.get(index);
			
		if(convertView == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.document_position_row, null);
		}	
	
		// tu powinno byc tez ustawianie zdjecia przedmioty- musisz wykombinowac jaka inna grafike jesli nie ma zdjecia
		
		//jak zrobic jakiegoc joina zeby wyciagnac nazwe z id konhtrahenta ??  a moze w ogole powinnam to inaczej zrobic??
		TextView documentPositionCodeTextView = (TextView) convertView.findViewById(R.id.position_code_textView);
		documentPositionCodeTextView.setText("["+position.getItem().getCode()+"] "+ position.getItem().getName());
			
		TextView documentPositionQuantityTextView = (TextView) convertView.findViewById(R.id.position_quantity_textView);
		documentPositionQuantityTextView.setText(String.valueOf(position.getQuantity()));
		
		TextView documentPositionNetPriceTextView = (TextView) convertView.findViewById(R.id.position_price_net_textView);
		documentPositionNetPriceTextView
				.setText(String.valueOf(position.getItem().getPrice().getNetPrice()) + context.getString(R.string.net));
		
		TextView documentPositionGrossPriceTextView = (TextView) convertView.findViewById(R.id.position_price_gross_textView);
		documentPositionGrossPriceTextView
				.setText(String.valueOf(position.getItem().getPrice().getGrossPrice()) + context.getString(R.string.gross));
		
		
		// tu mosze jakos brac z pozycji
		
		TextView documentPositionNetValueTextView = (TextView) convertView.findViewById(R.id.position_value_net_textView);
		documentPositionNetValueTextView.setText(String.valueOf(position.getNetValue()) + context.getString(R.string.net));
		
		TextView documentPositionGrossValueTextView = (TextView) convertView.findViewById(R.id.position_value_gross_textView);
		documentPositionGrossValueTextView.setText(String.valueOf(position.getGrossValue()) + context.getString(R.string.gross));


		
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged(){
		
	//	documentPositionsList = DocumentsManager.getInstance().getAllKrisDocuments();
		
		super.notifyDataSetChanged();
	}

}
