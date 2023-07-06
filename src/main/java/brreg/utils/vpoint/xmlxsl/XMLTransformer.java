package brreg.utils.vpoint.xmlxsl;

import brreg.utils.vpoint.config.model.VpointConfig;
import brreg.utils.vpoint.excel.ExcelImporter;
import brreg.utils.vpoint.util.XMLUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLTransformer {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(XMLTransformer.class);
    static final String step1XmlResultFilename = "result_step1.xml";
    static final String environmentValuePrefix = "%";
    static final String environmentValueSuffix = "%";
    private VpointConfig vPointConfig;

    public XMLTransformer(VpointConfig c) {
        vPointConfig = c;
    }

    public void createXMLDocumentsFromExcel(Map<String, String> replaceMap) {
        String sheetName = vPointConfig.getTestCasesExcelSheetName();
        List<Variation> variationsList = new ExcelImporter().readTestVariationsFromExcel(
            vPointConfig.getExcelFilePath(),
            sheetName, vPointConfig.getTestCaseColumnName(), vPointConfig.getStartrow());
        doXmlXslParsing(variationsList, replaceMap);
    }

    private void doXmlXslParsing(List<Variation> liste, Map<String, String> replaceMap) {
        for (Variation variation : liste) {
            log.debug("Prosesserer variasjon: " + variation.getVariationName());
            Path step1XsldestPath = XSLVariantCreator.substituteBaseXslForVariation(
                vPointConfig.getBaseXslFilenamePath(),
                vPointConfig.getDestXslFilenamePath(),
                variation.getVariants());
            transformStep1(step1XsldestPath);
            File result = transformStep2(variation);
            replaceEnvironmentVariables(result, replaceMap);
        }
    }

    private void transformStep1(Path step1XslDestPath) {
        File resultStep1 = XMLUtil.xslTransformer(
            vPointConfig.getCommonXmlBaseFilepath(),
            step1XslDestPath.toString(),
            vPointConfig.getTmpSubdirPath() + step1XmlResultFilename,
            null);
    }

    private File transformStep2(Variation variation) {
        final String xmlResultfileName = variation.getVariationName() + "_" + vPointConfig.getName() + ".xml";
        log.debug("Resultatfil '" + xmlResultfileName + "'");
        try {
            FileUtils.forceMkdir(new File(vPointConfig.getTargetPath()));
        } catch (IOException e) {
            throw new RuntimeException("Lage sti feilet :" + vPointConfig.getTargetPath());
        }

        File resultStep2 = XMLUtil.xslTransformer(
            vPointConfig.getTmpSubdirPath() + step1XmlResultFilename,
            vPointConfig.getMergeXsltPath(),
            vPointConfig.getTargetPath() + xmlResultfileName,
            null);

        log.info("Laget XML fil :  " + resultStep2.getAbsolutePath());
        return resultStep2;
    }

    private void replaceEnvironmentVariables(File file, Map<String, String> replaceMap) {

        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            try {
                Path path = Paths.get(file.getAbsolutePath());
                Stream<String> lines = Files.lines(path);
                List<String> replaced = lines.map(line -> line.replaceAll
                    (environmentValuePrefix + entry.getKey() + environmentValueSuffix, entry.getValue())).collect(Collectors.toList());
                Files.write(path, replaced);
                lines.close();
            } catch (IOException e) {
                log.error("Prosessering av miljøvariabler feilet :" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}



