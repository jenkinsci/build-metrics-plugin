package jenkins.plugins.build_metrics;

import java.util.Calendar;
import java.text.SimpleDateFormat;
/**
 * Dumb object for passing around build results
 * @author U0082132
 *
 */
public class BuildMetricsBuild {
	private int buildNumber;
	private String jobName;
	private String nodeName;
	private String userName;
	private Calendar buildDate;
	private long duration;
	private String status;
	private String description;
	private String jobUrl;
	private String buildUrl;
	
	public BuildMetricsBuild(int buildNumber, String jobName, String nodeName, String userName, Calendar buildDate, long duration, String status, String description){
		this.buildNumber = buildNumber;
		this.jobName = jobName;
		this.nodeName = nodeName;
		this.userName = userName;
		this.buildDate = buildDate;
		this.duration = duration;
		this.status = status;
		this.description = description;
                this.jobUrl = "job/" + jobName.replaceAll("/","/job/");
                this.buildUrl = this.jobUrl + "/" + String.valueOf(buildNumber);
	}
	
	public int getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Calendar getBuildDate() {
		return buildDate;
	}
	public String getBuildDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aaa");
		return sdf.format(this.buildDate.getTime());
	}
	public void setBuildDate(Calendar buildDate) {
		this.buildDate = buildDate;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
        public String getJobUrl() {
            return this.jobUrl;
        }
        public String getBuildUrl() {
            return this.buildUrl;
        }
}
