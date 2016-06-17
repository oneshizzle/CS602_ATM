package edu.adriennicholas.atm.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;

public class Utils {

	public static JFormattedTextField createNumericTextField() {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		return new JFormattedTextField(decimalFormat);
	}

}
