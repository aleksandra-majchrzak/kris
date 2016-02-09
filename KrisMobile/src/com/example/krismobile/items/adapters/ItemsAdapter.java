package com.example.krismobile.items.adapters;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.items.entities.Price;

public class ItemsAdapter extends  BaseAdapter{
	
	private Context context;
	private ArrayList<Document> itemsList;
	
	public ItemsAdapter(Context context, ArrayList<Document> itemssList){
		
		this.context = context;
		this.itemsList = itemssList;
	}
	
	@Override
	public int getCount() {
		
		return this.itemsList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return this.itemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0; //this.contractorsList.get(position).get;
	}
	
	public int getIndexOf(String id) {
        for(int i = 0; i < this.itemsList.size(); i++) {
            if(this.itemsList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		Map<String, Object> props = this.itemsList.get(position).getProperties();
		
			
		if(convertView == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.item_row, null);
		}	
		
//		if(((Integer)(props.get("TypeId"))).intValue() == ItemsManager.BUYER_TYPE){
				
			//ustawienie obrazka dla sprzedawcy
				
//		} else{
			//ustawienie obrazka dla kupca
				
//		}
		
		Document priceDoc = ItemsManager.getInstance().getItemPrice((String)props.get("PriceId"));
		Price price = new Price(priceDoc);
			
		TextView itemCodeNameTextView = (TextView) convertView.findViewById(R.id.item_code_name_textView);
		itemCodeNameTextView.setText("[" + (String)props.get("Code") + "] " + (String)props.get("Name"));
			
	
		TextView itemNetPriceTextView = (TextView) convertView.findViewById(R.id.item_netPrice_textView);
		itemNetPriceTextView.setText(String.valueOf(price.getNetPrice()) + context.getString(R.string.net));
		
		TextView itemGrossPriceTextView = (TextView) convertView.findViewById(R.id.item_grossPrice_textView);
		itemGrossPriceTextView.setText(String.valueOf(price.getGrossPrice())+ context.getString(R.string.gross));

		
		return convertView;
		
	}
	
	@Override
	public void notifyDataSetChanged(){
		
		itemsList = ItemsManager.getInstance().getAllItems();
		
		super.notifyDataSetChanged();
	}

}
