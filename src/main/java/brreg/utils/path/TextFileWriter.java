package brreg.utils.path;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TextFileWriter {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TextFileWriter.class);

    public static  Path writeToTextFile(String filepath, String xml) {
        try {
            new File(Paths.get(filepath).getParent().toAbsolutePath().toString()).mkdirs();
            return Files.write(Paths.get(filepath), xml.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
            log.error("Feil ved forsøk på å skrive til fil  : {} " , filepath);
            throw new RuntimeException(e);

        }
    }

}
