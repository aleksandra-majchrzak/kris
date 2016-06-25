package com.example.krismobile.payments;

import java.text.ParseException;

import com.example.krismobile.R;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.database.managers.PaymentsManager;
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
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentDialog extends DialogFragment{
	
	private Payment payment;
	private EditText paymentNameEditText;
	private EditText paymentValueEditText;
	private CheckBox finalizedCheckBox;
	private TextView paymentDateTextView;
	private EditText descriptionTextView;
	
	private boolean isNew;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
    	LayoutInflater inflater = getActivity().getLayoutInflater();	    
	    final View view=inflater.inflate(R.layout.dialog_payment , null);
	    payment = (Payment) getArguments().getParcelable("payment");
	    isNew = getArguments().getBoolean("isNew");
	    
	    paymentNameEditText = (EditText) view.findViewById(R.id.payment_name_editText);
	    paymentValueEditText = (EditText) view.findViewById(R.id.payment_value_editText);
	    paymentValueEditText.setText("0");
	    finalizedCheckBox = (CheckBox) view.findViewById(R.id.payment_finalized_checkBox);
	    paymentDateTextView = (TextView) view.findViewById(R.id.payment_date_textView);
	    descriptionTextView = (EditText) view.findViewById(R.id.payment_description_editText);
	    
	    final java.text.DateFormat df = DateFormat.getDateFormat(this.getActivity().getApplicationContext());
	    
	    paymentDateTextView.setText(df.format(payment.getPaymentDate()));
	    
	    paymentDateTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	    
	    
        return new AlertDialog.Builder(getActivity())
        		.setView(view)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                            	
                            	
                            	double paymentValue = Double.valueOf(paymentValueEditText.getText().toString());
                            	String paymentName = paymentNameEditText.getText().toString();
                            	boolean isPaid = finalizedCheckBox.isChecked();
                            	String paymentDate = paymentDateTextView.getText().toString();
                            	
                                if(paymentValue >0){
                                	
                                	payment.setPaymentName(paymentName);
                                	payment.setValue(paymentValue);
                                	
                                	try {
										payment.setPaymentDate(df.parse(paymentDate));
										
									} catch (ParseException e) {

										e.printStackTrace();
									}
                                	payment.setPaid(isPaid);
                                	
                                	payment.setDescription(descriptionTextView.getText().toString());
                                	
                                	PaymentsManager.getInstance().savePayment(payment);
                                	
                                	((PaymentsFragment)PaymentDialog.this.getTargetFragment())
                                		.refreshAdapter(isPaid ? PaymentsFragment.TAB_DUE : PaymentsFragment.TAB_FINALIZED);
                                	
                                }
                                else if(paymentValue  <= 0 ){
                                	
                                	Toast.makeText(getActivity(), R.string.cannot_save_payment_value, Toast.LENGTH_LONG).show();
                                	// moge jakos zablokowac zamytanie sie diaogu ??
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
