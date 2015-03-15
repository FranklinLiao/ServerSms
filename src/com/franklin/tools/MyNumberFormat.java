package com.franklin.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.R.integer;

public class MyNumberFormat {
	public static String numberPrecise(double number) {
		BigDecimal bigDecimal = new BigDecimal(number); 
		double num = bigDecimal.setScale(6,BigDecimal.ROUND_CEILING).doubleValue();
		String formatString = String.valueOf(num);
		return formatString;
	}
	
	public static String numberPrecise(double number,int precise) {
		BigDecimal bigDecimal = new BigDecimal(number); 
		double num = bigDecimal.setScale(precise,BigDecimal.ROUND_CEILING).doubleValue();
		String formatString = String.valueOf(num);
		return formatString;
	}
}
