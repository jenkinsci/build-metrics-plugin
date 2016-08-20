package jenkins.plugins.build_metrics.stats;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StatsMath {

	public static double getPercent(double subVal, double totalVal){
		return roundTwoDecimals((totalVal == 0) ? 0.00 : (subVal / totalVal) * 100.00);
	}
	
	public static double roundTwoDecimals(double iVal){
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH); 
		DecimalFormat twoDForm = new DecimalFormat("#.00",decimalFormatSymbols);
		return Double.valueOf(twoDForm.format(iVal));
	}
}
