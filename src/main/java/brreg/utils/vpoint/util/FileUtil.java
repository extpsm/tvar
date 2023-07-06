package brreg.utils.vpoint.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class FileUtil {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FileUtil.class);
    public static void recreateEmptyFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            try {
                FileUtils.deleteDirectory(folder);
            } catch (IOException ioe) {
                log.error("Failed to delete folder :" + folderPath);
                throw new RuntimeException(ioe);
            }
        }
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
