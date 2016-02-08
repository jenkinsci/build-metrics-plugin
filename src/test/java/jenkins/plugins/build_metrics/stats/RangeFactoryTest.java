package jenkins.plugins.build_metrics.stats;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RangeFactoryTest {

	@Test
	public void testParsing() {
		List<String> ranges =  Arrays.asList("(0_100)", "[0_100]", "[0_100)", "(0_100]");
        for(String range : ranges) {
            RangeFactory.from(range);
        }
    }

    @Test
	public void testNotParsing() {
		List<String> ranges =  Arrays.asList("", "()", "(0_100", "[_100]", "[100]");
        int err = 0;
        for(String range : ranges) {
            try {
                RangeFactory.from(range);
            } catch (IllegalArgumentException ex) {
                err++;
            }
        }
        assert err == 5;
    }
}