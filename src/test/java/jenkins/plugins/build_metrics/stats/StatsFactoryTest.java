package jenkins.plugins.build_metrics.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import hudson.plugins.global_build_stats.model.BuildResult;
import hudson.plugins.global_build_stats.model.JobBuildResult;
import hudson.plugins.global_build_stats.model.JobBuildSearchResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StatsFactoryTest {
	@Test
	public void testFailureRate(){
		ArrayList<JobBuildSearchResult> jbsr = new ArrayList<JobBuildSearchResult>();
		jbsr.add(createJobResult("build job 1", true));
		jbsr.add(createJobResult("build job 1", false));
		jbsr.add(createJobResult("build job 1", false));
		Map<Range<Double>, String> ranges = Maps.newHashMap();
		StatsFactory sf = StatsFactory.generateStats(jbsr, ranges);
		assertEquals("StatsFactory.failureRate", 66.67, sf.getFailureRate(), 0);
		assertEquals("StatsFactory.successRate", 33.33, sf.getSuccessRate(), 0);

		for(StatsModel stat: sf.getStats()){
		  assertEquals("StatsModel.failureRate", 66.67, stat.getFailureRate(), 0);
          assertEquals("StatsModel.successRate", 33.33, stat.getSuccessRate(), 0);
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