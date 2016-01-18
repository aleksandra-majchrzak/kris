package com.example.krismobile.main.utilities;

import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;

public class DateUtilities {
	
	public static String contvertDateToString(Context context, Date date){

		java.text.DateFormat df = DateFormat.getDateFormat(context.getApplicationContext());
		
		return df.format(date);
	}

}
