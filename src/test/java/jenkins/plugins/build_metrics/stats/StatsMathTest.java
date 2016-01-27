package jenkins.plugins.build_metrics.stats;

import org.junit.Test;
import static org.junit.Assert.*;

public class StatsMathTest {
	@Test
	public void testRoundTwoDecimals(){
		double d1 = 1.2345;
		assertEquals(d1 + " rounded to two decimal places", 1.23, StatsMath.roundTwoDecimals(d1), 0);
	}
	
	@Test
	public void testPercent(){
		double subVal = 2;
		double totalVal = 3;
		assertEquals("zero total", 0.00, StatsMath.getPercent(subVal, 0), 0);
		assertEquals("2/3", 66.67, StatsMath.getPercent(subVal, totalVal), 0);
		assertEquals("1/1", 100.00, StatsMath.getPercent(1, 1), 0);
		assertEquals("3/2", 150.00, StatsMath.getPercent(3, 2), 0);
	}
}
