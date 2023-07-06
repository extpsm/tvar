package brreg.utils.vpoint.xmlxsl;

import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class XSLVariantCreator {
    private static Logger log = Logger.getLogger(XSLVariantCreator.class);
    static final String appendix = "XX";
    public static Path substituteBaseXslForVariation(String baseXslFilenamePath, String destVariantXslFilenamePath, List<String> substititions) {

        Path path = Paths.get(baseXslFilenamePath);
        Path destPath = Paths.get(destVariantXslFilenamePath);
        Charset charset = StandardCharsets.UTF_8;
        try {
            String content = new String(Files.readAllBytes(path), charset);
            String replaced = new String(content);
            for (String s : substititions) {
                int startSub = 0;
                int endSub = s.indexOf(":");
                String substituteString = s.substring(startSub, endSub) + appendix;
                String replaceString = s.substring(endSub + 1);
                replaced = replaced.replaceAll(substituteString, replaceString);
            }
            Path destination = Files.write(destPath, replaced.getBytes(charset));
            log.debug("Skrev konvertert xsl fil til sti :" + destination);
            return destination;
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

}



