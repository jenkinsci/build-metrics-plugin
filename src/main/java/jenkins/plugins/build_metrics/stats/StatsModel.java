package jenkins.plugins.build_metrics.stats;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean
public class StatsModel implements Comparable{
	private String jobName;
	private int successes;
	private int failures;
	private int aborts;
	private int unstables;
	private int nobuilds;
  private int totalBuilds;
	
	public StatsModel(String jobName){
	  this.jobName = jobName;
	  this.successes = 0;
	  this.failures = 0;
	  this.aborts = 0;
	  this.unstables = 0;
	  this.nobuilds = 0;
	  this.totalBuilds = 0;
	}
	
	public String getJobName(){
	  return this.jobName;
	}
	
	public void addSuccess(){
	  this.successes++;
	  this.totalBuilds++;
	}

	@Exported
	public int getSuccesses(){
	  return this.successes;
	}
	
	public void addFailure(){
	  this.failures++;
	  this.totalBuilds++;
	}

	@Exported
	public int getFailures(){
	  return this.failures;
	}
	
	public void addAbort(){
	  this.aborts++;
	  this.totalBuilds++;
	}

	@Exported
	public int getAborts(){
	  return this.aborts;
	}
	
	public void addUnstable(){
	  this.unstables++;
	  this.totalBuilds++;
	}

	@Exported
	public int getUnstables(){
	  return this.unstables;
	}
	
	public void addNoBuild(){
	  this.nobuilds++;
	  this.totalBuilds++;
	}

	@Exported
	public int getNoBuilds(){
	  return this.nobuilds;
	}

	@Exported
	public int getTotalBuilds(){
	  return this.totalBuilds;
	}
	
	@Exported
	public double getFailureRate(){
	  return StatsMath.getPercent(this.totalBuilds - this.successes, this.totalBuilds);
	}
	
	public int compareTo(Object o){
	  if(o instanceof StatsModel) return compareTo((StatsModel)o);
	  return -1;
	}
	
	public int compareTo(StatsModel sm){
	  return this.jobName.toUpperCase().compareTo(sm.getJobName().toUpperCase());
	}

    @Exported
    public String getJobUrl() {
        return "job/" + this.jobName.replace("/", "/job/");
    }
}