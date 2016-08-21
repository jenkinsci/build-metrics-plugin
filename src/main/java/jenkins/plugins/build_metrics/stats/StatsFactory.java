package jenkins.plugins.build_metrics.stats;

import hudson.model.Cause;
import hudson.model.Hudson;
import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.global_build_stats.model.JobBuildSearchResult;
import hudson.plugins.global_build_stats.model.BuildResult;
import hudson.plugins.global_build_stats.Messages;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

import jenkins.plugins.build_metrics.BuildMetricsSearch;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean
public class StatsFactory {
	
  private Hashtable<String, StatsModel> stats;
	private int totalSuccess;
	private int totalFailure;
	private int totalAbort;
	private int totalUnstable;
	private int totalNobuild;
	private int totalBuilds;
	private String space;
	
	public StatsFactory(){
	    this.stats = new Hashtable<String, StatsModel>();
		this.totalSuccess = 0;
		this.totalFailure = 0;
		this.totalAbort = 0;
		this.totalUnstable = 0;
		this.totalNobuild = 0;
		this.totalBuilds = 0;
		this.space="&nbsp;";
	}
	
	public static StatsFactory generateStats(List<JobBuildSearchResult> searchResults, BuildMetricsSearch bms){
		StatsFactory factory = new StatsFactory();
		for(JobBuildSearchResult result: searchResults){
			// Check if our own criteria "causeFilter" is set and if this result goes
			// threw the filter
			if ( bms.getCauseFilter()!=null && !bms.getCauseFilter().isEmpty() ) {
				String cause = StatsHelper.findBuildDescription(result.getJobName(), result.getBuildNumber());
				String regexp = StatsHelper.fieldRegex(bms.getCauseFilter());
				if ( cause!=null && cause.matches(regexp) ) {
					factory.addResult(result);
				}
			} else
				factory.addResult(result);
		}
		return factory;
	}

	public void addResult(JobBuildSearchResult result){
		StatsModel stat = null;
		if(this.stats.containsKey(result.getJobName())){
		  stat = this.stats.get(result.getJobName());	
		}else{
		  stat = new StatsModel(result.getJobName());
		  this.stats.put(result.getJobName(), stat);
		}
		BuildResult r = result.getResult();
		if(isSuccess(r)){
		  stat.addSuccess();
		  this.addSuccess();
		}else if(isFailure(r)){
		  stat.addFailure();
		  this.addFailure();
		}else if(isAbort(r)){
		  stat.addAbort();
		  this.addAbort();
		}else if(isUnstable(r)){
		  stat.addUnstable();
		  this.addUnstable();
		}else if(isNobuild(r)){
		  stat.addNoBuild();
		  this.addNoBuild();
		}
	}
  
	@Exported
  public List<StatsModel> getStats(){
  	ArrayList<StatsModel> l = new ArrayList<StatsModel>();
  	l.addAll(this.stats.values());
  	Collections.sort(l);
  	return l;
  }

	@Exported
	public boolean isEmpty(){
	  return this.stats.isEmpty();
	}
	
	public boolean isSuccess(BuildResult result){
	  return result.getLabel().equals(Messages.Build_Results_Statuses_SUCCESS());
	}

	public void addSuccess(){
	  this.totalSuccess++;
	  this.totalBuilds++;
	}

	@Exported
	public int getSuccesses(){
	  return this.totalSuccess;
	}

	@Exported
	public double getSuccessesPercent(){
	  return StatsMath.getPercent(this.totalSuccess, this.totalBuilds);
	}
	
	public boolean isFailure(BuildResult result){
	  return result.getLabel().equals(Messages.Build_Results_Statuses_FAILURES());
	}
	
	public void addFailure(){
	  this.totalFailure++;
	  this.totalBuilds++;
	}

	@Exported
	public int getFailures(){
	  return this.totalFailure;
	}
	
	public boolean isAbort(BuildResult result){
	  return result.getLabel().equals(Messages.Build_Results_Statuses_ABORTED());
	}
	
	public void addAbort(){
	  this.totalAbort++;
	  this.totalBuilds++;
	}

	@Exported
	public int getAborts(){
	  return this.totalAbort;
	}
	
	public boolean isUnstable(BuildResult result){
	  return result.getLabel().equals(Messages.Build_Results_Statuses_UNSTABLES());
	}
	
	public void addUnstable(){
	  this.totalUnstable++;
	  this.totalBuilds++;
	}

	@Exported
	public int getUnstables(){
	  return this.totalUnstable;
	}
	
	public boolean isNobuild(BuildResult result){
	  return result.getLabel().equals(Messages.Build_Results_Statuses_NOT_BUILD());
	}
	
	public void addNoBuild(){
	  this.totalNobuild++;
	  this.totalBuilds++;
	}

	@Exported
	public int getNoBuilds(){
	  return this.totalNobuild;
	}

	@Exported
	public int getTotalBuilds(){
	  return this.totalBuilds;
	}

	@Exported
	public double getFailureRate(){
	  return StatsMath.getPercent(this.totalBuilds - this.totalSuccess, this.totalBuilds);
	}

	@Exported
	public String getPaddedFailureRate(){
	  return String.format("%12.2f", this.getFailureRate()).replace(" ", this.space);	
	}
	
}