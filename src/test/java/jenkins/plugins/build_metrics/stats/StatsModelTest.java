package jenkins.plugins.build_metrics.stats;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StatsModelTest {
	@Test
	public void testFailurePercent(){
		StatsModel sm = new StatsModel("test");
		assert 0 == sm.getSuccesses();
		assert 0 == sm.getFailures();
		assert 0 == sm.getAborts();
		assert 0 == sm.getUnstables();
		assert 0 == sm.getNoBuilds();
		assert 0 == sm.getTotalBuilds();
		assert 0.00 == sm.getFailureRate();
		
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
	}

    @Test
    public void testFolders() {
        String expectedJobUrl = "job/folder1/job/folder2/job/test";
        String expectedJobName = "folder1/folder2/test";
        StatsModel sm = new StatsModel(expectedJobName);
        assertEquals(expectedJobName, sm.getJobName());
        assertEquals(expectedJobUrl, sm.getJobUrl());
    }
}
