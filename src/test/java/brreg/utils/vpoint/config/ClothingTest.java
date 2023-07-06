package brreg.utils.vpoint.config;

import brreg.utils.vpoint.config.model.VpointConfigConverter;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import brreg.utils.path.PathToCurrentEnvironmentConverter;
import brreg.utils.vpoint.base.Tvar;
import brreg.utils.vpoint.config.model.VpointConfig;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ClothingTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ClothingTest.class);

    @Test
    public void testPathVariables()  {

        VpointConfig config = VpointConfigConverter.fromXml(VpointConfig.substituteWindowsEnvVariables(
             new PathToCurrentEnvironmentConverter().apply(".\\src\\test\\resources\\eksempel_rot\\antrekk\\Antrekk-prosjekt.xml")));
        File currentDirectory = new File(new File(".").getAbsolutePath().replace(".",""));
        String tvarDirectory =  new PathToCurrentEnvironmentConverter().apply(
            currentDirectory.getParentFile().getAbsolutePath());
        System.getProperties().put("TVAR_HOME",  tvarDirectory);

        assertEquals( tvarDirectory +   new PathToCurrentEnvironmentConverter().apply("\\tvar\\target\\antrekk\\"), config.getTargetPath());
        assertEquals( tvarDirectory +   new PathToCurrentEnvironmentConverter().apply("\\tvar\\target\\antrekk\\tmp\\"), config.getTmpSubdirPath());
        assertEquals( tvarDirectory +
                           new PathToCurrentEnvironmentConverter().apply( "\\tvar\\src\\test\\resources\\eksempel_rot\\antrekk\\base\\"),
                      config.getBasepath());
        assertEquals( tvarDirectory +  new PathToCurrentEnvironmentConverter().apply(
                          "\\tvar\\src\\test\\resources\\eksempel_rot\\antrekk"), config.getStepPath());
        assertEquals( "base.xslt", config.getBaseXslFilename());
        assertEquals("destfile.xslt", config.getDestXslFilename());
        assertEquals(tvarDirectory +  new PathToCurrentEnvironmentConverter().apply("\\tvar\\target\\antrekk\\tmp\\destfile.xslt"), config.getDestXslFilenamePath());
        assertEquals(tvarDirectory +  new PathToCurrentEnvironmentConverter().apply(
                         "\\tvar\\src\\test\\resources\\eksempel_rot\\antrekk\\base\\basis.xml"), config.getCommonXmlBaseFilepath());

        assertEquals("basis.xml", config.getCommonXmlBaseFile());
        log.info("getTmpSubdir :" +  config.getTmpSubdir(),"\n");
    }
    @Test
    public void testConfig() throws Exception {
        File currentDirectory = new File(new File(".").getAbsolutePath().replace(".",""));
        System.getProperties().put("TVAR_HOME",  currentDirectory.getParentFile().getAbsolutePath());
        String args[] = {"-f",
             new PathToCurrentEnvironmentConverter().apply(".\\src\\test\\resources\\eksempel_rot\\antrekk\\Antrekk-prosjekt.xml"), "-v"};
        Tvar.main(args);
        String tvarDirectory = currentDirectory.getParentFile().getAbsolutePath();
        assertTrue(new File(tvarDirectory +  new PathToCurrentEnvironmentConverter().apply("\\tvar\\target\\antrekk\\PartyConfig_clothing.xml")).exists());
        assertTrue(new File(tvarDirectory +   new PathToCurrentEnvironmentConverter().apply("\\tvar\\target\\antrekk\\WorkConfig_clothing.xml")).exists());
    }
}