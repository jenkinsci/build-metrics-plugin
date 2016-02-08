package jenkins.plugins.build_metrics.stats;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangeFactory {

    private static final Pattern PATTERN = Pattern.compile("^[\\(|\\[](\\d+)_(\\d+)[\\)|\\]]$");

    public static Range<Double> from(String range) {
        Matcher m = PATTERN.matcher(range);
        if(m.matches()) {
            int length = range.length();

            Double lowerBound = Double.parseDouble(m.group(1));
            Double upperBound = Double.parseDouble(m.group(2));

            if(range.charAt(0) == '(') {
                if(range.charAt(length - 1) == ')') {
                    return Ranges.open(lowerBound, upperBound);
                }
                return Ranges.openClosed(lowerBound, upperBound);
            } else {
                if(range.charAt(length - 1) == ')') {
                    return Ranges.closedOpen(lowerBound, upperBound);
                }
                return Ranges.closed(lowerBound, upperBound);
            }
        }
        throw new IllegalArgumentException("Range " + range + " is not valid.");
    }
}
