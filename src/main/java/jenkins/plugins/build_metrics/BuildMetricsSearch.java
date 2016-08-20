package jenkins.plugins.build_metrics;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

/**
 * Dumb object for passing around build search criteria
 * @author mgoss
 *
 */
@ExportedBean
public class BuildMetricsSearch {
	private String label;
	private int range;
	private String rangeUnits;
	private String jobFilter;
	private String nodeFilter; 
	private String launcherFilter;
	private String causeFilter;
	
	public BuildMetricsSearch(String label, int range, String rangeUnits, String jobFilter, String nodeFilter, String launcherFilter, String causeFilter){
		this.label = label;
		this.range = range;
		this.rangeUnits = rangeUnits;
		this.jobFilter = jobFilter;
		this.nodeFilter = nodeFilter; 
		this.launcherFilter = launcherFilter;
		this.causeFilter = causeFilter;
	}

	@Exported
	public String getLabel(){
		return this.label;
	}
	public void setLabel(String label){
		this.label = label;
	}
	@Exported
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	@Exported
	public String getRangeUnits() {
		return rangeUnits;
	}
	public void setRangeUnits(String rangeUnits) {
		this.rangeUnits = rangeUnits;
	}
	@Exported
	public String getJobFilter() {
		return jobFilter;
	}
	public void setJobFilter(String jobFilter) {
		this.jobFilter = jobFilter;
	}
	@Exported
	public String getNodeFilter() {
		return nodeFilter;
	}
	public void setNodeFilter(String nodeFilter) {
		this.nodeFilter = nodeFilter;
	}
	@Exported
	public String getLauncherFilter() {
		return launcherFilter;
	}
	public void setLauncherFilter(String launcherFilter) {
		this.launcherFilter = launcherFilter;
	}
	@Exported
	public String getCauseFilter() {
		return causeFilter;
	}
	public void setCauseFilter(String causeFilter) {
		this.causeFilter = causeFilter;
	}
	
}
