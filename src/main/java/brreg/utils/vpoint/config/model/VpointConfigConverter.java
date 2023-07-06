package brreg.utils.vpoint.config.model;

import com.thoughtworks.xstream.XStream;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;

public class VpointConfigConverter {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(VpointConfigConverter.class);

    public static VpointConfig fromXml(String configFilePath) {
        try {
            String vpointConfigXml = new String(Files.readAllBytes(new File(configFilePath).toPath()));
            XStream xstream = new XStream();
            xstream.processAnnotations(VpointConfig.class);
            VpointConfig vpointConfig = (VpointConfig) xstream.fromXML(vpointConfigXml);
            return vpointConfig;
        } catch (Exception e) {
            log.error("Feil ved konvertering av xml fil til VpointConfig , filsti :  { }" , configFilePath);
            throw new RuntimeException(e);
        }
    }
}
