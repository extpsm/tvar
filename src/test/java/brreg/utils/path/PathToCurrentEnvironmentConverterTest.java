package brreg.utils.path;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class PathToCurrentEnvironmentConverterTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PathToCurrentEnvironmentConverterTest.class);

    @Test
    public void checkConvertWindowsPath()  {
        String converted =  new PathToCurrentEnvironmentConverter().apply(".\\src\\test\\resources\\Config.xml");
        if (SystemUtils.IS_OS_WINDOWS) {
            assertEquals(".\\src\\test\\resources\\Config.xml", converted);

        } else {
            assertEquals("./src/test/resources/Config.xml",  converted);
        }
    }

    @Test
    public void checkEnvironmentLinuxPath() throws Exception {
        String converted =  new PathToCurrentEnvironmentConverter().apply("./src/test/resources/Config.xml");
        if (SystemUtils.IS_OS_WINDOWS) {
            assertEquals(".\\src\\test\\resources\\Config.xml", converted);
        } else {
            assertEquals("./src/test/resources/Config.xml",  converted);
        }
    }
}