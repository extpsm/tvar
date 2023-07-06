package brreg.utils.vpoint.base;

import org.apache.commons.cli.*;
import org.slf4j.LoggerFactory;

import static brreg.utils.vpoint.config.model.VpointConfig.substituteWindowsEnvVariables;

public class TvarCmdLineArgs {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TvarCmdLineArgs.class);
    private String[] args = null;
    private String configFilename;
    private Options options = new Options();
    private boolean createVariations = false;
    private boolean recreateOutputFolder = false;
    private boolean suppressWarnings = true; //suppress compiler warnings
    private String homedirEnv;
    private String homedirEnvValue;

    public TvarCmdLineArgs(String[] args) {
        this.args = args;
        options.addOption("f", "config-file", true, "Mandatory -f argument missing. Config file must be supplied");
        options.addOption("v","create-variations", false, "Mandatory -v argument is missing");
        options.addOption("hd","homedir", true, "Optional -e argument is missing");
        options.addOption("hv","homedir value", true, "Optional -hv argument is missing");

        options.addOption("h", "help", false, "show help.");
    }

    public TvarCmdLineArgs parse() {
        CommandLineParser parser = new BasicParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h") || !cmd.hasOption("f"))
                help();

            if (cmd.hasOption("v")) {
                createVariations = true;
            }
            if (cmd.hasOption("w")) {
                suppressWarnings = false;
            }
            if (cmd.hasOption("d")) {
                recreateOutputFolder = true;
            }

            if (cmd.hasOption("hd")) {
                homedirEnv = cmd.getOptionValue("hd");
            }

            if (cmd.hasOption("hv")) {
                homedirEnvValue = cmd.getOptionValue("hv");
                log.info("fikk -hv opsjon,  value : " + homedirEnvValue);
            }
            if (cmd.hasOption("f")) {
                log.info("Bruker  argument -f=" + cmd.getOptionValue("f"));
                setConfigFilename(cmd.getOptionValue("f"));
            } else {
                log.info("Oblgatorisk -f opsjon mangler");

            }
        } catch (ParseException e) {
            log.info("Feilet ved tolking av kommandolinjeopsjoner", e);
            help();
        }
        return this;
    }

    public void help() {
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp("Main", options);

        System.exit(0);

    }

    public String getConfigFilename() {
        return substituteWindowsEnvVariables(configFilename);
    }

    public void setConfigFilename(String configFilename) {
        this.configFilename = configFilename;
    }

    public boolean isCreateVariations() {
        return createVariations;
    }

    public boolean isSuppressWarnings() {
        return suppressWarnings;
    }


    public boolean isRecreateOutputFolder() {
        return recreateOutputFolder;
    }

    public String getHomedirEnv() {
        return homedirEnv;
    }

    public String getHomedirEnvValue() {
        return homedirEnvValue;
    }
}
