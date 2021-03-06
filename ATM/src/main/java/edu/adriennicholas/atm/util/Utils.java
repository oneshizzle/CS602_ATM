package edu.adriennicholas.atm.util;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Utils {

	public static NumberFormat createNumberFormat() {
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setGroupingUsed(false);
		return decimalFormat;
	}

	public static JFormattedTextField createNumericTextField() {
		return new JFormattedTextField(createNumberFormat());
	}

	public static JTextField createUppercaseTextField(int length) {
		JTextField textField = new JTextField(length);
		DocumentFilter filter = new Utils().new UppercaseDocumentFilter();

		textField.setPreferredSize(new Dimension(100, 20));
		((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
		return textField;
	}

	class UppercaseDocumentFilter extends DocumentFilter {
		public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
				throws BadLocationException {

			fb.insertString(offset, text.toUpperCase(), attr);
		}

		public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {

			fb.replace(offset, length, text.toUpperCase(), attrs);
		}
	}
}
