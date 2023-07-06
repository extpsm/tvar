package brreg.utils.vpoint.util;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLUtil {
    private static Logger log = Logger.getLogger(XMLUtil.class);
    private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    public static File xslTransformer(String xmlFilename, String xslFilename,
                                      String xmlResultfileName, String schemaPath) {

        Assert.notNull(xmlFilename, "'xmlFilename' kan ikke være null");

        Assert.notNull(xslFilename, "'xslFilename' kan ikke være null");

        Assert.notNull(xmlResultfileName, "'xmlResultfileName' kan ikke være null");
        File xmlFile = new File(xmlFilename);
        FileInputStream xmlFileInputStream;

        InputSource in;
        Document doc;

        try {
            xmlFileInputStream = new FileInputStream(xmlFile);
            Assert.isTrue(xmlFile.exists(), xmlFilename + " eksisterer ikke ");
            File xslFile = new File(xslFilename);
            Assert.isTrue(xslFile.exists(), xslFilename + "eksisterer ikke  ");
            in = new InputSource(xmlFileInputStream);
            DocumentBuilderFactory dfactory =  DocumentBuilderFactory.newInstance();

            if (schemaPath != null) {
                File skjema = new File(schemaPath);
                if (!skjema.exists()) {
                    throw new IllegalArgumentException("Did not find schema" + skjema.getAbsolutePath());
                }
                dfactory.setAttribute(JAXP_SCHEMA_SOURCE, new File(schemaPath));
            }
            dfactory.setNamespaceAware(true);

            doc = dfactory.newDocumentBuilder().parse(in);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(
                    xslFile));
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            File resultFile = new File(xmlResultfileName);
            StreamResult outputXml = new StreamResult(resultFile);
            transformer.transform(new DOMSource(doc), outputXml);
            if (schemaPath != null) {
                log.info("Validating result for file " + resultFile.getAbsolutePath() + " against schema :" + schemaPath);

            }
            return resultFile;

        } catch (Exception e) {
            log.error(xmlResultfileName + " is illegal due to : ");
            log.error(e.getMessage());
            e.printStackTrace();
            log.error(e.getCause().getMessage());
            throw new IllegalArgumentException(e);

        }
    }
}