package jenkins.plugins.build_metrics.stats;

public class StatsMath {

	public static double getPercent(double subVal, double totalVal){
		return roundTwoDecimals((totalVal == 0) ? 0.00 : (subVal / totalVal) * 100.00);
	}
	
	public static double roundTwoDecimals(double iVal){
		return (double)Math.round(iVal * 100) / 100;
	}
}
