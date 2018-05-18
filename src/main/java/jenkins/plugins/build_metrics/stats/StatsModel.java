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
	private String space;
	private String jobUrl;
	
	public StatsModel(String jobName){
	  this.jobName = jobName;
	  this.successes = 0;
	  this.failures = 0;
	  this.aborts = 0;
	  this.unstables = 0;
	  this.nobuilds = 0;
	  this.totalBuilds = 0;
	  this.space="&nbsp;";
          this.jobUrl = "job/" + jobName.replaceAll("/","/job/");
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

	@Exported
	public String getPaddedFailureRate(){
	  return String.format("%12.2f", this.getFailureRate()).replace(" ", this.space);
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
          return this.jobUrl;
        }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aborts;
		result = prime * result + failures;
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result + nobuilds;
		result = prime * result + ((space == null) ? 0 : space.hashCode());
		result = prime * result + successes;
		result = prime * result + totalBuilds;
		result = prime * result + unstables;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatsModel other = (StatsModel) obj;
		if (aborts != other.aborts)
			return false;
		if (failures != other.failures)
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (nobuilds != other.nobuilds)
			return false;
		if (space == null) {
			if (other.space != null)
				return false;
		} else if (!space.equals(other.space))
			return false;
		if (successes != other.successes)
			return false;
		if (totalBuilds != other.totalBuilds)
			return false;
		if (unstables != other.unstables)
			return false;
		return true;
	} 
}
