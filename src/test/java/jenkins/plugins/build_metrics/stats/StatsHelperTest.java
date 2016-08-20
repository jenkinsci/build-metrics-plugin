package jenkins.plugins.build_metrics.stats;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatsHelperTest {
	@Test
	public void testfieldRegex() {
		assertTrue( StatsHelper.fieldRegex("fieldRegex(.*(admin).*)").equals(".*(admin).*") );
	}
}
