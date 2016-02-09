package com.example.krismobile.documents.positions;

import com.example.krismobile.R;
import com.example.krismobile.documents.DocumentActivity;
import com.example.krismobile.documents.DocumentFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DocumentPositionDialog extends DialogFragment{
	
//	private Context context;
//	private static DocumentFragment callerFragment;
	private DocumentPosition position;
	private EditText quantityEditText;
	private TextView unitTextView;
	private TextView valueNetTextView;
	private TextView valueGrossTextView;
	
	private boolean canEdit;
	private boolean isNew;

	public static DocumentPositionDialog newInstance(DocumentPosition position, boolean canEdit, boolean isNew) {
		
		DocumentPositionDialog frag = new DocumentPositionDialog();
        Bundle args = new Bundle();
//        args.putParcelable("context", context);
        args.putParcelable("position", position);
        args.putBoolean("canEdit", canEdit);
        args.putBoolean("isNew", isNew);
        frag.setArguments(args);
        
//        callerFragment = caller;
        
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
    	LayoutInflater inflater = getActivity().getLayoutInflater();	    
	    final View view=inflater.inflate(R.layout.dialog_document_position , null);
	    position = (DocumentPosition) getArguments().getParcelable("position");
	    canEdit = getArguments().getBoolean("canEdit");
	    isNew = getArguments().getBoolean("isNew");
	    
	    quantityEditText = (EditText) view.findViewById(R.id.position_quantity_editText);
	    unitTextView = (TextView) view.findViewById(R.id.position_unit_textView);
	    valueNetTextView = (TextView) view.findViewById(R.id.position_value_net_textView);
	    valueGrossTextView = (TextView) view.findViewById(R.id.position_value_gross_textView);
	    
	    unitTextView.setText(getString(R.string.pieces));
	    
	    quantityEditText.setText(String.valueOf(position.getQuantity()));
	    valueNetTextView.setText(String.valueOf(position.getNetValue()));
	    valueGrossTextView.setText(String.valueOf(position.getGrossValue()));

	    
	    if( !canEdit)
	    	quantityEditText.setEnabled(false);

        return new AlertDialog.Builder(getActivity())
        		.setView(view)
                .setTitle("["+position.getItem().getCode()+"] "+position.getItem().getName())
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                            	
                            	double positionQuantity = Double.valueOf(quantityEditText.getText().toString());
                            	
                                if(positionQuantity >0 && canEdit){
                                	
                                	position.setQuantity(positionQuantity);
                                	position.setNetValue(Double.valueOf(valueNetTextView.getText().toString()));
                                	position.setGrossValue(Double.valueOf(valueGrossTextView.getText().toString()));
                                	
                                	if(isNew){
                                		((DocumentActivity)	getActivity()).getDocument().getPositionsList().add(position);
                                	}
                                	else{
                                		((DocumentActivity)	getActivity()).getDocument().getPositionsList().edit(position);
                                	}
                                	
                                }
                                else if(positionQuantity  <= 0 ){
                                	
                                	Toast.makeText(getActivity(), R.string.cannot_save_position_quantity, Toast.LENGTH_LONG).show();
                                }
                                else{
                                	
                                	dismiss();
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                            	
                                dismiss();
                            }
                        }).create();
    }

}
