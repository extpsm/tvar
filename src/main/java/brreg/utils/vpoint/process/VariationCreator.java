package brreg.utils.vpoint.process;

import brreg.utils.vpoint.config.model.VpointConfig;
import brreg.utils.vpoint.xmlxsl.XMLTransformer;

import java.util.Map;

public class VariationCreator {
    VpointConfig vPointConfig;

    public VariationCreator(VpointConfig config) {
        vPointConfig = config;
    }

    public void doStep(boolean recreateOutputFolder, Map<String,String> replaceMap) {
        vPointConfig.createWorkDirs(recreateOutputFolder);
        new XMLTransformer(vPointConfig).createXMLDocumentsFromExcel(replaceMap);
    }
}

