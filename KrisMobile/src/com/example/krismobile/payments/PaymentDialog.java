package com.example.krismobile.payments;

import com.example.krismobile.R;
import com.example.krismobile.documents.DocumentActivity;
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.payments.entities.Payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentDialog extends DialogFragment{
	
	private Payment payment;
	private EditText paymentNameEditText;
	private EditText paymentValueEditText;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
    	LayoutInflater inflater = getActivity().getLayoutInflater();	    
	    final View view=inflater.inflate(R.layout.dialog_payment , null);
	    payment = (Payment) getArguments().getParcelable("position");
	    
	    paymentNameEditText = (EditText) view.findViewById(R.id.position_quantity_editText);
	    
	    
        return new AlertDialog.Builder(getActivity())
        		.setView(view)
         //       .setTitle("["+position.getItem().getCode()+"] "+position.getItem().getName())
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                            	
                            	/*
                            	double paymentValue = Double.valueOf(paymentValueEditText.getText().toString());
                            	
                                if(paymentValue >0){
                                	
                                	position.setQuantity(paymentValue);
                                	position.setNetValue(Double.valueOf(valueNetTextView.getText().toString()));
                                	position.setGrossValue(Double.valueOf(valueGrossTextView.getText().toString()));
                                	
                                	if(isNew){
                                		((DocumentActivity)	getActivity()).getDocument().getPositionsList().add(position);
                                	}
                                	else{
                                		((DocumentActivity)	getActivity()).getDocument().getPositionsList().edit(position);
                                	}
                                	
                                }
                                else if(paymentValue  <= 0 ){
                                	
                                	Toast.makeText(getActivity(), R.string.cannot_save_position_quantity, Toast.LENGTH_LONG).show();
                                }
                                else{
                                	
                                	dismiss();
                                }
                                */
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
