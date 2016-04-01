package jenkins.plugins.build_metrics;

import com.google.common.collect.Range;
import hudson.model.Hudson;
import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.global_build_stats.GlobalBuildStatsPlugin;
import hudson.plugins.global_build_stats.business.GlobalBuildStatsBusiness;
import hudson.plugins.global_build_stats.model.BuildHistorySearchCriteria;
import hudson.plugins.global_build_stats.model.BuildSearchCriteria;
import hudson.plugins.global_build_stats.model.JobBuildSearchResult;
import jenkins.plugins.build_metrics.stats.StatsFactory;
import org.kohsuke.stapler.StaplerRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class BuildMetricsPluginSearchFactory {
	/* the following static values come back from the search form*/
	public static String RANGE_DAYS = "Days";
	public static String RANGE_WEEKS = "Weeks";
	public static String RANGE_MONTHS = "Months";
	public static String RANGE_YEARS = "Years";
	
	public BuildMetricsPluginSearchFactory(){}
	
	public StatsFactory getBuildStats(BuildMetricsSearch bms, Map<Range<Double>, String> ranges){
	  return StatsFactory.generateStats(searchBuilds(bms), ranges);
	}
	
	public List<JobBuildSearchResult> searchBuilds(BuildMetricsSearch bms){
	  GlobalBuildStatsBusiness business = GlobalBuildStatsPlugin.getPluginBusiness();
	  BuildSearchCriteria criteria = createBuildSearchCriteria(bms);
	  return business.searchBuilds(createBuildHistorySearchCriteria(bms, criteria));
	}

	public List<BuildMetricsBuild> getFailedBuilds(BuildMetricsSearch bms){
	  GlobalBuildStatsBusiness business = GlobalBuildStatsPlugin.getPluginBusiness();
	  BuildSearchCriteria criteria = createFailedBuildSearchCriteria(bms);
	  List<JobBuildSearchResult> results = business.searchBuilds(createBuildHistorySearchCriteria(bms, criteria));
	  List<BuildMetricsBuild> failedBuilds = new ArrayList<BuildMetricsBuild>();
	  for(JobBuildSearchResult result: results){
		  failedBuilds.add(
		    new BuildMetricsBuild( 
		      result.getBuildNumber(),
			    result.getJobName(),
			    result.getNodeName(),
			    result.getUserName(),
			    result.getBuildDate(),
			    result.getDuration(),
			    result.getResult().getLabel(),
			    findBuildDescription(result.getJobName(), result.getBuildNumber())
			));
	  }
	  return failedBuilds;
	}
	
	private String findBuildDescription(String jobName, int buildNumber){
		String description = null;
		Job j = (Job)(Hudson.getInstance().getItem(jobName));
		if(j != null){
			Run r = j.getBuildByNumber(buildNumber);
			if(r != null){
				description = r.getDescription();
			}
		}
		return description;
	}
	

	public Long getStartDate(int range, String rangeUnits){
		int iRange = range * -1;
		int iUnits = Calendar.DAY_OF_YEAR;//default = RANGE_DAYS
		if(RANGE_WEEKS.equals(rangeUnits)){
		  iUnits = Calendar.WEEK_OF_YEAR;
		}else if(RANGE_MONTHS.equals(rangeUnits)){
	      iUnits = Calendar.MONTH;
		}else if(RANGE_YEARS.equals(rangeUnits)){
	      iUnits = Calendar.YEAR;
		}
		Calendar tmpCal = Calendar.getInstance();
		tmpCal.add(iUnits, iRange);
		return new Long(tmpCal.getTimeInMillis());
	}
	
	public Long getDefaultStartDate(){
		Calendar tmpCal = Calendar.getInstance();
		tmpCal.roll(Calendar.WEEK_OF_YEAR, -2);
		return new Long(tmpCal.getTimeInMillis());
	}
	
	public Long getDefaultEndDate(){
		return new Long(Calendar.getInstance().getTimeInMillis());
	}
	
	public BuildMetricsSearch createBuildMetricsSearch(StaplerRequest req){
		return new BuildMetricsSearch(
				req.getParameter("label"),
				Integer.parseInt(req.getParameter("range")),
				req.getParameter("rangeUnits"),
				req.getParameter("jobFilter"),
				req.getParameter("nodeFilter"),
				req.getParameter("launcherFilter"),
				req.getParameter("wallCss")
				);
	}
	
	public BuildHistorySearchCriteria createBuildHistorySearchCriteria(BuildMetricsSearch bms, BuildSearchCriteria criteria){
		Long start = getStartDate(bms.getRange(), bms.getRangeUnits());
	    Long end = getDefaultEndDate();
		return new BuildHistorySearchCriteria(start, end, criteria);
	}
	
	public BuildSearchCriteria createBuildSearchCriteria(BuildMetricsSearch bms){
		BuildSearchCriteria criteria = new BuildSearchCriteria(bms.getJobFilter(), 
				bms.getNodeFilter(), 
				bms.getLauncherFilter(),
				Boolean.TRUE, //successShown
				Boolean.TRUE, //failuresShown
				Boolean.TRUE, //unstablesShown
				Boolean.TRUE, //abortedShown
				Boolean.TRUE //notBuildsShown
				);
		return criteria;
	}
	
	public BuildSearchCriteria createFailedBuildSearchCriteria(BuildMetricsSearch bms){
		BuildSearchCriteria criteria = new BuildSearchCriteria(bms.getJobFilter(), 
				bms.getNodeFilter(), 
				bms.getLauncherFilter(),
				Boolean.FALSE, //successShown
				Boolean.TRUE, //failuresShown
				Boolean.TRUE, //unstablesShown
				Boolean.TRUE, //abortedShown
				Boolean.TRUE //notBuildsShown
				);
		return criteria;
	}
}