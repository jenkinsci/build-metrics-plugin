package jenkins.plugins.build_metrics;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import hudson.Extension;
import hudson.Plugin;
import hudson.model.ManagementLink;
import hudson.plugins.global_build_stats.FieldFilterFactory;
import hudson.plugins.global_build_stats.business.GlobalBuildStatsBusiness;
import jenkins.plugins.build_metrics.stats.RangeFactory;
import jenkins.plugins.build_metrics.stats.StatsFactory;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.ExportedBean;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Entry point of the build metrics plugin
 *
 * @author mgoss
 * @plugin
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
        Map<Range<Double>, String> ranges = getRanges(req);
        StatsFactory buildStats = factory.getBuildStats(searchCriteria, ranges);
    	List<BuildMetricsBuild> failedBuilds = factory.getFailedBuilds(searchCriteria);
        req.setAttribute("buildStats", buildStats);
    	req.setAttribute("failedBuilds", failedBuilds);
        req.setAttribute("searchCriteria", searchCriteria);
        if (req.getParameter("fontSize") == null) {
            req.setAttribute("fontSize", "180%");
        } else {
            req.setAttribute("fontSize", req.getParameter("fontSize"));
        }
        String viewName = req.getParameter("wallView") != null && req.getParameter("wallView").equals("on") ? "BuildStatsWall" : "BuildStats";
        req.getView(this, "/jenkins/plugins/build_metrics/BuildMetricsPlugin/" + viewName + ".jelly").forward(req, res);
    }

    private Map<Range<Double>, String> getRanges(StaplerRequest req) {
//        try {
//            throw new RuntimeException(new File(Range.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
//        } catch (URISyntaxException e) {
//            throw new RuntimeException("xxx");
//        }
        String ranges = req.getParameter("ranges");
        if (ranges == null || ranges.isEmpty()) {
            return ImmutableMap.of(Ranges.<Double>all(), "FFFFFF");
        } else {
            ImmutableMap.Builder<Range<Double>, String> rangeMapBuilder = ImmutableMap.builder();
            List<String> entries = Arrays.asList(ranges.split(","));
            for (String entry : entries) {
                String[] split = entry.split("=");
                rangeMapBuilder.put(RangeFactory.from(split[0]), split[1]);
            }
            return rangeMapBuilder.build();
        }
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

