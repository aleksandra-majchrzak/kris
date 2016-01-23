package com.example.krismobile.items;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krismobile.R;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.main.base.FragmentBase;
import com.example.krismobile.warehouses.stocks.ItemStocks;

public class ItemFragment extends FragmentBase {
	
	private Item item;
	
	private TextView itemTypeTextView;
	private TextView itemNetPriceTextView;
	private TextView itemGrossPriceTextView;
	private TextView itemSizeTextView;
	private TextView itemMaterialTextView;
	private Button itemStocksButton;
	private TextView itemDescriptionTextView;

	public ItemFragment() {
	}
/*	
	public static ItemFragment newInstance(int sectionNumber){
		
		ItemFragment fragment = new ItemFragment();
		
		Bundle args = new Bundle();
		
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		
		fragment.setArguments(args);
		
		return fragment;
	}
*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle args = getArguments();
		
		//czy to nie powinno byc gdziej indziej?
		this.item = ((ItemActivity) context).getItem();		
		
		View rootView = null;
		
		rootView = inflater.inflate(R.layout.fragment_item, container, false);
			
		loadControls(rootView);
		
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_edit_item) {
		
			
			Intent intent = new Intent(context, ModifyItemActivity.class);
			
			Bundle args = new Bundle();
			args.putString("itemId", this.item.getId());
			intent.putExtras(args);
			
			startActivityForResult(intent,ItemsActivity.REQUEST_REFRESH);
			
			
			return true;
		}
		else if(id == R.id.action_delete_item){
			
			deleteItem();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	/*
		if(requestCode == ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR){
			if(resultCode == ContractorsActivity.RESULT_OK){

				contractor = ((ContractorActivity)context).reloadContractor();
				fillControls();	
			}
		}
*/
	}
		
	private void loadControls(View rootView){
		
		itemTypeTextView = (TextView) rootView.findViewById(R.id.item_type_textView);
		itemNetPriceTextView = (TextView) rootView.findViewById(R.id.item_net_price_textView);
		itemGrossPriceTextView = (TextView) rootView.findViewById(R.id.item_gross_price_textView);
		itemSizeTextView = (TextView) rootView.findViewById(R.id.item_size_textView);
		itemMaterialTextView = (TextView) rootView.findViewById(R.id.item_material_textView);
		itemStocksButton = (Button) rootView.findViewById(R.id.item_stocks_button);
		itemDescriptionTextView = (TextView) rootView.findViewById(R.id.item_description_textView);
	
		fillControls();	
	}
	
	private void fillControls(){
		
		List<ItemStocks> itemStocks = item.getItemStocks();
		
		itemTypeTextView.setText(item.getType());
		itemNetPriceTextView.setText(String.valueOf(item.getPrice().getNetPrice()));
		itemGrossPriceTextView.setText(String.valueOf(item.getPrice().getGrossPrice()));
		itemSizeTextView.setText(item.getSize());
		itemMaterialTextView.setText(item.getMaterial());
		
		if(itemStocks.isEmpty())
			itemStocksButton.setText(R.string.none);
		else
			itemStocksButton.setText(String.valueOf(item.getItemStocks().get(0).getStocks()));	// tymczasowo- przeciez ta lista moze byc pusta
		
		itemDescriptionTextView.setText(item.getDescription());

	}
	
	
	private void deleteItem(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);

		builder.setMessage(R.string.do_you_want_to_delete_item)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						boolean isDeleted = ItemsManager.getInstance().deleteItem(item.getId());
						dialog.cancel();
						
						if(isDeleted){
							Toast.makeText(context, R.string.item_deleted, Toast.LENGTH_LONG).show();
							getActivity().setResult(ItemsActivity.RESULT_OK);
							getActivity().finish();
						}
						else
							Toast.makeText(context, R.string.item_deleted_error, Toast.LENGTH_LONG).show();
						
					}
				})
				.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();					
					}
				});
		
		builder.create().show();
	}
}