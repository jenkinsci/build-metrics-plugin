package jenkins.plugins.build_metrics.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import org.junit.Test;

import java.util.Map;

public class StatsModelTest {
	@Test
	public void testFailurePercent() {
		Map<Range<Double>, String> ranges = Maps.newHashMap();
		StatsModel sm = new StatsModel("test", ranges);
		assert 0 == sm.getSuccesses();
		assert 0 == sm.getFailures();
		assert 0 == sm.getAborts();
		assert 0 == sm.getUnstables();
		assert 0 == sm.getNoBuilds();
		assert 0 == sm.getTotalBuilds();
		assert 0.00 == sm.getFailureRate();
		assert 100.0 == sm.getSuccessRate();

		sm.addSuccess();
		sm.addFailure();
		sm.addAbort();
		sm.addUnstable();
		sm.addNoBuild();

		assert 1 == sm.getSuccesses();
		assert 1 == sm.getFailures();
		assert 1 == sm.getAborts();
		assert 1 == sm.getUnstables();
		assert 1 == sm.getNoBuilds();
		assert 5 == sm.getTotalBuilds();
		assert ((4.00 / 5.00) * 100.00) == sm.getFailureRate();
		assert 20.0 == sm.getSuccessRate();
	}
}