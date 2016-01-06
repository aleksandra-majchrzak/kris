package com.example.krismobile.main.adapters;

import java.util.List;

import com.example.krismobile.R;
import com.example.krismobile.main.entities.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<MenuItem>{
	
	private Context context;
	private List<MenuItem> menuItems;

	public MenuAdapter(Context context, int resource, List<MenuItem> list) {
		super(context, resource, list);

		this.context = context;
		this.menuItems = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.menu_item, null);
		
			MenuItem currentItem = menuItems.get(position);
		
			ImageView icon = (ImageView) convertView.findViewById(R.id.menu_item_icon);
			icon.setImageResource(currentItem.getResourceId());
		
			TextView textView = (TextView) convertView.findViewById(R.id.menu_item_text);
			textView.setText(currentItem.getName());
//			textView.setCompoundDrawables(left, top, right, bottom)
			
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) convertView.getLayoutParams();
			if(params == null){
				params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 130);
			}
			else
				params.height = 130;
			
			convertView.setLayoutParams(params);
		}
		
		return convertView;
	}

}
