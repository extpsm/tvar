package brreg.utils.vpoint.base;

import brreg.utils.vpoint.config.model.VpointConfig;
import brreg.utils.vpoint.config.model.VpointConfigConverter;
import brreg.utils.vpoint.process.VariationCreator;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import brreg.utils.vpoint.util.XercesWarningFilter;

import java.util.HashMap;
import java.util.Map;

public class Tvar {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Tvar.class);

    public static void main(String[] args) throws Exception {
        TvarCmdLineArgs cmdLineArgs = new TvarCmdLineArgs(args).parse();

        Assert.isTrue(cmdLineArgs.getConfigFilename() != null, "-f argument is mandatory" );

        VpointConfig config = VpointConfigConverter.fromXml(VpointConfig.substituteWindowsEnvVariables(cmdLineArgs.getConfigFilename()));
        log.debug("cmdLineArgs + " + cmdLineArgs.getConfigFilename() );
        if(cmdLineArgs.isSuppressWarnings()) {
            XercesWarningFilter.start();
        }
        if (cmdLineArgs.isCreateVariations()) {
            Map<String,String> replaceValues = new HashMap<String,String>();
            if(cmdLineArgs.getHomedirEnv() != null && cmdLineArgs.getHomedirEnvValue() != null) {
                replaceValues.put(cmdLineArgs.getHomedirEnv(), cmdLineArgs.getHomedirEnvValue());
            }
            new VariationCreator(config).doStep(cmdLineArgs.isRecreateOutputFolder(), replaceValues);
        }

        log.info("Tvar ferdig ......");
    }
}