package com.example.krismobile.contractors.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krismobile.R;

public class ModifyContractorDialogAdapter extends BaseAdapter {
	
	private Context context;
	public static final int POSITION_VIEW = 0;
	public static final int POSITION_EDIT = 1;
	public static final int POSITION_DELETE = 2;

	public ModifyContractorDialogAdapter(Context context) {
	    this.context = context;
	}

	public int getCount() {
	    return 3;
	}

	public Object getItem(int position) {
	    return null;
	}

	public long getItemId(int position) {
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	    if(convertView == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.contractor_modify_dialog_element, null);
		}	
	    
	    ImageView icon = (ImageView) convertView.findViewById(R.id.contractor_modify_imageView);
	    TextView description = (TextView) convertView.findViewById(R.id.contractor_modify_textView);

	   switch(position){
	   case POSITION_VIEW:
		   
		   icon.setBackgroundResource(android.R.drawable.ic_menu_view);
		   description.setText(R.string.action_view_contractor);
		   
		   break;
	   case POSITION_EDIT:
		   
		   icon.setBackgroundResource(android.R.drawable.ic_menu_edit);
		   description.setText(R.string.action_edit_contractor);
		   
		   break;
	   case POSITION_DELETE:
		   
		   icon.setBackgroundResource(android.R.drawable.ic_menu_delete);
		   description.setText(R.string.action_delete_contractor);
		   
		   break;
	   }

	    return convertView;
	}

	}