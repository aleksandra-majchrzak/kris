package com.example.krismobile.contractors;

import com.example.krismobile.R;
import com.example.krismobile.contractors.adapters.ModifyContractorDialogAdapter;
import com.example.krismobile.database.managers.ContractorsManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ModifyContractorDialog extends Dialog{
	
	private ContractorsFragment fragment;
	private Context context;
	private String contractorId;

	public ModifyContractorDialog(ContractorsFragment fragment, String contractorId) {
		super(fragment.getContext());

		this.fragment = fragment;
		this.context = fragment.getContext();
		this.contractorId = contractorId;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstance){		

		GridView rootView = (GridView) ((Activity)context).getLayoutInflater().inflate(R.layout.contractor_modify_grid_view, null);

		rootView.setAdapter(new ModifyContractorDialogAdapter(this.context));

		rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {							

				if(position == ModifyContractorDialogAdapter.POSITION_VIEW){
					
					Intent intent = new Intent(context, ContractorActivity.class);
				
					Bundle args = new Bundle();
					args.putString("contractorId", contractorId);
					intent.putExtras(args);
					
					((Activity)context).startActivityForResult(intent, ContractorsActivity.REQUEST_DELETE_CONTRACTOR);
					dismiss();
				}
				
				else if(position == ModifyContractorDialogAdapter.POSITION_EDIT){
					
					Intent intent = new Intent(context, ModifyContractorActivity.class);
					
					Bundle args = new Bundle();
					args.putString("contractorId", contractorId);
					intent.putExtras(args);
					
					((Activity)context).startActivityForResult(intent, ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR);
					dismiss();
				}
				
				else if(position == ModifyContractorDialogAdapter.POSITION_DELETE){
					deleteContractor();
					dismiss();
				}
				
			}
		});
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(rootView);
		
	}
	
private void deleteContractor(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);

		builder.setMessage(R.string.do_you_want_to_delete_contractor)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						boolean isDeleted = ContractorsManager.getInstance().deleteContractor(contractorId);
						dialog.cancel();
						
						if(isDeleted){
							Toast.makeText(context, R.string.contractor_deleted, Toast.LENGTH_LONG).show();
							fragment.refresh();
						}
						else
							Toast.makeText(context, R.string.contractor_deleted_error, Toast.LENGTH_LONG).show();
						
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
