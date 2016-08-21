package jenkins.plugins.build_metrics.stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jenkins.model.Jenkins;
import hudson.model.Cause;
import hudson.model.Job;
import hudson.model.Run;

/***
 * 
 * static helper class for functions 
 * @author rolf
 *
 */
public class StatsHelper {
	
	public static String findBuildCause(String jobName, int buildNumber){
		String description = null;
		Jenkins inst = Jenkins.getInstance();
		if ( inst != null ) {
			Job j = (Job)(inst.getItem(jobName));
			if(j != null){
				Run r = j.getBuildByNumber(buildNumber);
				if(r != null){
					description = ((Cause)r.getCauses().get(0)).getShortDescription();
				}
			}
		}
		return description;
	}

	public static String findBuildDescription(String jobName, int buildNumber){
		String description = null;
		Jenkins inst = Jenkins.getInstance();
		if ( inst != null ) {
			Job j = (Job)(inst.getItem(jobName));
			if(j != null){
				Run r = j.getBuildByNumber(buildNumber);
				if(r != null){
					description = r.getDescription();
				}
			}
		}
		return description;
	}

	public static String fieldRegex( String regEx ) {
        Pattern p = Pattern.compile("fieldRegex\\( *(.*) *\\)");
        Matcher m = p.matcher(regEx);
        m.find();
        return m.group(1);		
	}
}
