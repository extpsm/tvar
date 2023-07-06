package brreg.utils.vpoint.config.model;

import brreg.utils.path.PathToCurrentEnvironmentConverter;
import brreg.utils.vpoint.util.FileUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("vpoint-config")
public class VpointConfig {

    private static final Logger log = LoggerFactory.getLogger(VpointConfig.class);
    @XStreamAlias("rootDir")
    @XStreamAsAttribute
    private String rootDir;
    @XStreamAlias("baseXslFilename")
    @XStreamAsAttribute
    private String baseXslFilename;
    @XStreamAlias("destXslFilename")
    @XStreamAsAttribute
    private String destXslFilename;
    @XStreamAlias("tmpSubdir")
    @XStreamAsAttribute
    private String tmpSubdir;
    @XStreamAlias("commonXmlBaseFile")
    @XStreamAsAttribute
    private String commonXmlBaseFile;
    @XStreamAlias("targetSubdir")
    @XStreamAsAttribute
    private String targetSubdir;
    @XStreamAlias("mergeXslt")
    @XStreamAsAttribute
    private String mergeXslt;
    @XStreamAlias("templateSubdir")
    @XStreamAsAttribute
    private String templateSubdir;
    @XStreamAlias("name")
    @XStreamAsAttribute

    private String name;
    @XStreamAlias("basedir")
    @XStreamAsAttribute
    private String basedir;
    @XStreamAlias("excelfile")
    @XStreamAsAttribute
    private String excelfile;
    @XStreamAlias("testCaseColumnName")
    @XStreamAsAttribute
    private String testCaseColumnName;
    @XStreamAlias("verifyOptionsSheetName")
    @XStreamAsAttribute
    private String verifyOptionsSheetName;
    @XStreamAlias("testCasesExcelSheetName")
    @XStreamAsAttribute
    private String testCasesExcelSheetName;
    @XStreamAlias("startrow")
    @XStreamAsAttribute
    private int startrow;

    /* Using {CurrentDir} to denote vpoint root dir so that the demo case can be run
      from wherever it may be located. This will be a generic path to the location.
     */

    public String getRootDir() {
        return substituteWindowsEnvVariables(rootDir);
    }

    public static String substituteWindowsEnvVariables(String path) {
        //log.error("TODO REMOVE ***** substituteWindowsEnvVariables : " + path);
        String tvarOdmsHomeEnvVariable = "%TVAR_HOME%";
        String tvarOdmsHomeEnv = "TVAR_HOME";
        if (path.contains(tvarOdmsHomeEnvVariable)) {
            // log.error("TODO REMOVE ***** tvarOdmsHomeEnvVariable : " + tvarOdmsHomeEnvVariable);
            String value = System.getenv(tvarOdmsHomeEnv);
            if (value == null) {
                value = System.getProperty("TVAR_HOME");
            }
            if (value == null) {
                throw new RuntimeException(tvarOdmsHomeEnv + " found in config file but no environment variable TVAR_HOME ");
            }

            String p = path.replace(tvarOdmsHomeEnvVariable, value);

            return p;
        } else {
            return path;
        }
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasedir() {
        return basedir;
    }

    public void setBasedir(String basedir) {
        this.basedir = basedir;
    }

    public String getExcelfile() {
        return excelfile;
    }

    public void setExcelfile(String excelfile) {
        this.excelfile = excelfile;
    }

    public String getTestCaseColumnName() {
        return testCaseColumnName;
    }

    public void setTestCaseColumnName(String testCaseColumnName) {
        this.testCaseColumnName = testCaseColumnName;
    }

    public String getVerifyOptionsSheetName() {
        return verifyOptionsSheetName;
    }

    public void setVerifyOptionsSheetName(String verifyOptionsSheetName) {
        this.verifyOptionsSheetName = verifyOptionsSheetName;
    }

    public String getTestCasesExcelSheetName() {
        return testCasesExcelSheetName;
    }

    public void setTestCasesExcelSheetName(String testCasesExcelSheetName) {
        this.testCasesExcelSheetName = testCasesExcelSheetName;
    }

    public String getBaseXslFilename() {
        return baseXslFilename;
    }

    public void setBaseXslFilename(String baseXslFilename) {
        this.baseXslFilename = baseXslFilename;
    }

    public String getDestXslFilename() {
        return destXslFilename;
    }

    public void setDestXslFilename(String destXslFilename) {
        this.destXslFilename = destXslFilename;
    }

    public String getTmpSubdir() {
        return substituteWindowsEnvVariables(targetSubdir);
    }

    public void setTmpSubdir(String tmpSubdir) {
        this.tmpSubdir = tmpSubdir;
    }

    public String getCommonXmlBaseFile() {
        return commonXmlBaseFile;
    }

    public void setCommonXmlBaseFile(String commonXmlBaseFile) {
        this.commonXmlBaseFile = commonXmlBaseFile;
    }

    public String getTargetSubdir() {
        return new PathToCurrentEnvironmentConverter().apply(substituteWindowsEnvVariables(targetSubdir));
    }

    public void setTargetSubdir(String targetSubdir) {

        this.targetSubdir = targetSubdir;
    }

    public String getMergeXslt() {
        return mergeXslt;
    }

    public void setMergeXslt(String mergeXslt) {
        this.mergeXslt = mergeXslt;
    }

    public String getTemplateSubdir() {
        return templateSubdir;
    }

    public void setTemplateSubdir(String templateSubdir) {
        this.templateSubdir = templateSubdir;
    }

    public int getStartrow() {
        return startrow;
    }

    public void setStartrow(int startrow) {
        this.startrow = startrow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
            append("name", name).
            append("basedir", basedir).
            append("excelfile", excelfile).
            append("testCaseColumnName", testCaseColumnName).
            append("verifyOptionsSheetName", verifyOptionsSheetName).
            append("testCasesExcelSheetName", testCasesExcelSheetName).
            append("baseXslFilename", baseXslFilename).
            append("tmpSubdir", tmpSubdir).
            append("commonXmlBaseFile", commonXmlBaseFile).
            append("targetSubdir", targetSubdir).
            append("mergeXslt", mergeXslt).
            append("templateSubdir", templateSubdir).
            append("startrow", startrow).
            toString();
    }

    public String getStepPath() {
        return new PathToCurrentEnvironmentConverter().apply(getRootDir() + name);
    }

    public String getBasepath() {
        return new PathToCurrentEnvironmentConverter().apply(
            getStepPath() + "\\" + basedir + "\\");
    }

    public String getTargetPath() {
        return new PathToCurrentEnvironmentConverter().apply(
            getTargetSubdir());
    }

    public String getExcelFilePath() {
        return new PathToCurrentEnvironmentConverter().apply(getStepPath() + "\\" + getExcelfile());
    }

    public String getBaseXslFilenamePath() {
        return new PathToCurrentEnvironmentConverter().apply(getBasepath() + getBaseXslFilename());
    }

    public String getDestXslFilenamePath() {
        return new PathToCurrentEnvironmentConverter().apply(getTmpSubdirPath() + getDestXslFilename());
    }

    public String getCommonXmlBaseFilepath() {
        return new PathToCurrentEnvironmentConverter().apply(getBasepath() + commonXmlBaseFile);
    }

    public String getTmpSubdirPath() {
        return new PathToCurrentEnvironmentConverter().apply(substituteWindowsEnvVariables(tmpSubdir));
    }

    public String getMergeXsltPath() {
        return new PathToCurrentEnvironmentConverter().apply(
            getBasepath() + mergeXslt);
    }


    public void setDefaults() {
        setBasedir("base");
        setExcelfile("testcases.xlsx");
        setTestCaseColumnName("Testtilfelle");
        setVerifyOptionsSheetName("verifiser");
        setTestCasesExcelSheetName("testtilfeller");
        setBaseXslFilename("base.xslt");
        setDestXslFilename("destfile.xslt");
        setTmpSubdir("target/tmp");
        setCommonXmlBaseFile("basis.xml");
        setTargetSubdir("target");
        setMergeXslt("merge.xslt");
        setTemplateSubdir("templates");
    }

    public void createWorkDirs(boolean recreateOutputFolder) {

        if (recreateOutputFolder) {
            FileUtil.recreateEmptyFolder(getTargetPath());
        }
        FileUtil.recreateEmptyFolder(getTmpSubdirPath());
    }
}
