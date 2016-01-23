package com.example.krismobile.main.utilities;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.krismobile.R;

public class ComboBox extends LinearLayout {

   private AutoCompleteTextView autocompleteTextView;
   private ImageButton button;

   public ComboBox(Context context) {
       super(context);
       this.createChildControls(context);
   }

   public ComboBox(Context context, AttributeSet attrs) {
       super(context, attrs);
       this.createChildControls(context);
   }

   private void createChildControls(Context context) {
       this.setOrientation(HORIZONTAL);
       this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                       LayoutParams.WRAP_CONTENT));

       autocompleteTextView = new AutoCompleteTextView(context);
       autocompleteTextView.setSingleLine();
       autocompleteTextView.setInputType(InputType.TYPE_CLASS_TEXT
                       | InputType.TYPE_TEXT_VARIATION_NORMAL
                       | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                       | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
                       | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
       autocompleteTextView.setRawInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
       this.addView(autocompleteTextView, new LayoutParams(LayoutParams.WRAP_CONTENT,
                       LayoutParams.WRAP_CONTENT, 1));

       button = new ImageButton(context);
       button.setImageResource(android.R.drawable.arrow_down_float);
       button.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {
                       autocompleteTextView.showDropDown();
               }
       });
       this.addView(button, new LayoutParams(LayoutParams.WRAP_CONTENT,
                       LayoutParams.WRAP_CONTENT));
   }

   public void setSuggestionSource(Context context, String[] suggestions) {
	   
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_row , suggestions);
       
       autocompleteTextView.setAdapter(adapter);
   }

   public String getText() {
       return autocompleteTextView.getText().toString();
   }

   public void setText(String text) {   
       autocompleteTextView.setText(text);
   }
 
   public void setPosition(int position) {
	   String text = (String) autocompleteTextView.getAdapter().getItem(position);
       autocompleteTextView.setText(text);
   }
}
