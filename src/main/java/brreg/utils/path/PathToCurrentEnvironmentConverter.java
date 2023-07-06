package brreg.utils.path;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;

import java.util.function.Function;

public class PathToCurrentEnvironmentConverter implements Function<String, String> {

    @Override
    public  String apply(String path) {
        if(SystemUtils.IS_OS_WINDOWS) {
            return FilenameUtils.separatorsToWindows(path).replace("\\\\", "\\");
        } else {
            return FilenameUtils.separatorsToUnix(path).replace("//", "/");
        }
    }
}