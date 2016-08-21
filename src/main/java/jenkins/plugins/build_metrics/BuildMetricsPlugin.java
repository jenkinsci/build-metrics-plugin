package jenkins.plugins.build_metrics;

import jenkins.plugins.build_metrics.stats.StatsFactory;

import hudson.Extension;
import hudson.Plugin;
import hudson.model.ManagementLink;

import hudson.plugins.global_build_stats.business.GlobalBuildStatsBusiness;
import hudson.plugins.global_build_stats.FieldFilterFactory;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.ExportedBean;


/**
 * Entry point of the build metrics plugin
 * 
 * @author mgoss
 *
 */
@ExportedBean
public class BuildMetricsPlugin extends Plugin {
	/**
	 * Let's add a link in the administration panel linking to the build metrics search page
	 */
    @Extension
    public static class BuildMetricsPluginManagementLink extends ManagementLink {

        public String getIconFileName() {
            return "/plugin/build-metrics/icons/build-metrics.png";
        }

        public String getDisplayName() {
            return "Build Metrics";
        }

        public String getUrlName() {
            return "plugin/build-metrics/";
        }
        
        @Override 
        public String getDescription() {
            return "search the global build stats and generate build metrics";
        }
    }
    public void doGetBuildStats(StaplerRequest req, StaplerResponse res) throws ServletException, IOException {
    	BuildMetricsPluginSearchFactory factory = new BuildMetricsPluginSearchFactory();
    	BuildMetricsSearch searchCriteria = factory.createBuildMetricsSearch(req);
    	StatsFactory buildStats = factory.getBuildStats(searchCriteria);
    	List<BuildMetricsBuild> failedBuilds = factory.getFailedBuilds(searchCriteria);
        req.setAttribute("buildStats", buildStats);
    	req.setAttribute("failedBuilds", failedBuilds);
        req.setAttribute("searchCriteria", searchCriteria);
    	req.getView(this, "/jenkins/plugins/build_metrics/BuildMetricsPlugin/BuildStats.jelly").forward(req, res);
    }
    
    /**
     * Copied from GlobalBuildStatsPlugin
     * @param value Parameter which should be escaped
     * @return value where "\" are escaped
     */
	public static String escapeAntiSlashes(String value){
		return GlobalBuildStatsBusiness.escapeAntiSlashes(value);
	}
	
	/**
	 * Copied from GlobalBuildStatsPlugin
	 * @return FieldFilterFactory.ALL_VALUES_FILTER_LABEL
	 */
	public static String getFieldFilterALL(){
		return FieldFilterFactory.ALL_VALUES_FILTER_LABEL;
	}
	
	/**
	 * Copied from GlobalBuildStatsPlugin
	 * @return FieldFilterFactory.REGEX_FIELD_FILTER_LABEL
	 */
	public static String getFieldFilterRegex(){
		return FieldFilterFactory.REGEX_FIELD_FILTER_LABEL;
	}
}

