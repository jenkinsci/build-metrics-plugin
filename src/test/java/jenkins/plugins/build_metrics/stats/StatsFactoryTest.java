package jenkins.plugins.build_metrics.stats;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import jenkins.plugins.build_metrics.BuildMetricsSearch;
import hudson.plugins.global_build_stats.model.BuildResult;
import hudson.plugins.global_build_stats.model.JobBuildResult;
import hudson.plugins.global_build_stats.model.JobBuildSearchResult;

public class StatsFactoryTest {
	@Test
	public void testFailureRate(){
		ArrayList<JobBuildSearchResult> jbsr = new ArrayList<JobBuildSearchResult>();
		BuildMetricsSearch bms = new BuildMetricsSearch("", 2, "", "", "", "", "");
		jbsr.add(createJobResult("build job 1", true));
		jbsr.add(createJobResult("build job 1", false));
		StatsFactory sf = StatsFactory.generateStats(jbsr,bms);
		assertEquals("StatsFactory.failureRate", 50.00, sf.getFailureRate(), 0);
		
		for(StatsModel stat: sf.getStats()){
		  assertEquals("StatsModel.failureRate", 50.00, stat.getFailureRate(), 0);
	  }
	}
	
	private JobBuildSearchResult createJobResult(String jobName, boolean isSuccess){
		JobBuildResult jbr = new JobBuildResult(
		  (isSuccess ? BuildResult.SUCCESS : BuildResult.FAILURE), 
		  jobName,
		  1, //build number
			Calendar.getInstance(),
			JobBuildResult.EMPTY_DURATION,
			JobBuildResult.EMPTY_NODE_NAME,
			JobBuildResult.EMPTY_USER_NAME);
		return new JobBuildSearchResult(jbr, true, true);
	}
}