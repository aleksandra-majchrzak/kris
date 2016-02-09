package com.example.krismobile.items;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krismobile.R;
import com.example.krismobile.contractors.ContractorsActivity;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.items.entities.Price;
import com.example.krismobile.main.base.FragmentBase;
import com.example.krismobile.main.utilities.ComboBox;
import com.example.krismobile.warehouses.stocks.ItemStocks;

public class ModifyItemFragment extends FragmentBase{
	
	private static int NO_CODE = 1;
	private static int NO_NAME = 2;
	private static int NO_PRICE = 3;
	
	private Item item;
	
	private EditText itemCodeEditText;
	private EditText itemNameEditText;
	private ComboBox itemTypeSpinner;
	private EditText itemNetPriceEditText;
	private EditText itemGrossPriceEditText;
	private ComboBox itemSizeSpinner;
	private ComboBox itemMaterialSpinner;
//	
	private EditText itemDescriptionEditText;

	public ModifyItemFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle args = getArguments();
		
		//czy to nie powinno byc gdziej indziej?
		this.item = ((ModifyItemActivity) context).getItem();		
		
		View rootView = null;
		
		rootView = inflater.inflate(R.layout.fragment_item_modify, container, false);
			
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
		
		if(id == R.id.action_save_item){
			
			int validationResult = validate();
			
			if(validationResult == 0){
				saveItem();
				return true;
			}
			else{
				if(validationResult == 1)
					Toast.makeText(context, R.string.cannot_save_item_without_code, Toast.LENGTH_LONG).show();
				else if(validationResult == 2)
					Toast.makeText(context, R.string.cannot_save_item_without_name, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(context, R.string.cannot_save_item_without_price, Toast.LENGTH_LONG).show();
				return false;
			}
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
		
		itemCodeEditText = (EditText) rootView.findViewById(R.id.item_code_editText);
		itemNameEditText = (EditText) rootView.findViewById(R.id.item_name_editText);
		itemTypeSpinner = (ComboBox) rootView.findViewById(R.id.item_type_spinner);
		itemNetPriceEditText = (EditText) rootView.findViewById(R.id.item_net_price_editText);
		itemGrossPriceEditText = (EditText) rootView.findViewById(R.id.item_gross_price_editText);
		itemSizeSpinner = (ComboBox) rootView.findViewById(R.id.item_size_spinner);
		itemMaterialSpinner = (ComboBox) rootView.findViewById(R.id.item_material_spinner);
//		itemStocksButton = (Button) rootView.findViewById(R.id.item_stocks_button);
		itemDescriptionEditText = (EditText) rootView.findViewById(R.id.item_description_editText);
	
		fillControls();	
	}
	
	private void fillControls(){
		
		List<ItemStocks> itemStocks = item.getItemStocks();
		Price price = item.getPrice();
		
		itemCodeEditText.setText(item.getCode());
		itemNameEditText.setText(item.getName());
		
		itemTypeSpinner.setText(item.getType());		
		String[] itemTypes = ItemsManager.getInstance().getItemTypes();
		itemTypeSpinner.setSuggestionSource(context, itemTypes);
		
		if(price != null){
			itemNetPriceEditText.setText(String.valueOf(item.getPrice().getNetPrice()));
			itemGrossPriceEditText.setText(String.valueOf(item.getPrice().getGrossPrice()));
		}
		else{
			itemNetPriceEditText.setText("");
			itemGrossPriceEditText.setText("");
		}
		
		itemSizeSpinner.setText(item.getSize());
		String[] itemSizes = ItemsManager.getInstance().getItemSizes();
		itemSizeSpinner.setSuggestionSource(context, itemSizes);
		
		itemMaterialSpinner.setText(item.getMaterial());
		String[] itemMateials = ItemsManager.getInstance().getItemMaterials();
		itemMaterialSpinner.setSuggestionSource(context, itemMateials);
/*		
		if(itemStocks == null || itemStocks.isEmpty())
			itemStocksButton.setText(R.string.none);
		else
			itemStocksButton.setText(String.valueOf(item.getItemStocks().get(0).getStocks()));	// tymczasowo- przeciez ta lista moze byc pusta
*/		
		itemDescriptionEditText.setText(item.getDescription());

	}
	
	private int validate(){
		boolean noPrice = itemNetPriceEditText.getText().toString().equals("") 
				|| itemGrossPriceEditText.getText().toString().equals("");
		
		boolean noCode = itemCodeEditText.getText().toString().equals("");
		
		boolean noName = itemNameEditText.getText().toString().equals("");
		
		return  (noCode) ? 1 : ( (noName) ? 2 : (noPrice) ? 3: 0);
	}
	
	private void saveItem(){
		
		item.setCode(itemCodeEditText.getText().toString());
		item.setName(itemNameEditText.getText().toString());
		item.setSize(itemSizeSpinner.getText());
		item.setMaterial(itemMaterialSpinner.getText());
		Price price = new Price();
		price.setNetPrice(Double.valueOf(itemNetPriceEditText.getText().toString()));
		price.setGrossPrice(Double.valueOf(itemGrossPriceEditText.getText().toString()));
		item.setPrice(price);
		item.setDescription(itemDescriptionEditText.getText().toString());
		item.setType(itemTypeSpinner.getText());
//		item.itemStocks = itemStocks;
			
		ItemsManager.getInstance().saveItem(item);
			
		getActivity().setResult(ContractorsActivity.RESULT_OK);
		getActivity().finish();
	}

}
