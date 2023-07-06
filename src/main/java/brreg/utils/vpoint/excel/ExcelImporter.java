package brreg.utils.vpoint.excel;


import brreg.utils.vpoint.xmlxsl.Variation;
import org.apache.poi.xssf.usermodel.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExcelImporter {
    private static final Logger log = LoggerFactory.getLogger(ExcelImporter.class);

    public List<Variation> doImport(final File excelFil, String sheetName, String testcaseNameColumn, int hdrRow) {
        FileInputStream excelFilStrom = null;
        try {
            excelFilStrom = new FileInputStream(excelFil);
            log.debug("Prosesserer atk  :" + sheetName + " for excel fil :" + excelFil.getAbsolutePath());
            return doImportFromXlsx(excelFilStrom, sheetName, testcaseNameColumn, hdrRow);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(excelFilStrom);
        }
    }

    public List<Variation> doImportFromXlsx(final FileInputStream excelFilStrom, String sheetName, String testCaseColumnName, int hdrRow) {
        final List<Variation> variations = new ArrayList<Variation>();
        try {
            final XSSFWorkbook bok = new XSSFWorkbook(excelFilStrom);
            final XSSFSheet ark = bok.getSheet(sheetName);
            if (ark == null) {
                throw new RuntimeException("Sheet : " + sheetName + " not found in file " + excelFilStrom.getFD());
            }

            XSSFCell testCaseHeaderCell = hentCelle(testCaseColumnName, hdrRow, ark, hdrRow);
            if(testCaseHeaderCell == null ) {
                throw new RuntimeException("Check config :Missing header cell :" + testCaseColumnName +  " in sheet : " + sheetName + " not found in file " + excelFilStrom.getFD());
            }
            List<String> variationPoints = getVariationPoints(ark, hdrRow);
            for (int i = hdrRow + 1; i < ark.getPhysicalNumberOfRows(); i++) {
               if(ark.getRow(i) == null) {
                   break;
               }
                XSSFCell cell = hentCelle(testCaseColumnName, i, ark, hdrRow);
                if (cell == null)
                    break;

                String variationName = cell.getRichStringCellValue().toString().trim();

                if (variationName.length() == 0) {
                    break;
                } else if (variationName.startsWith("_")){
                    log.debug("Ignorerer rad :" + variationName);
                    continue;
                } else {
                    log.debug("Prosesserer varasjon (testcase):" + variationName);
                    Variation var = getVariant(ark, testCaseColumnName, i, variationPoints, hdrRow);
                    log.debug("Legger til test case :" + var.getVariationName() + ": " + var.getVariants().toString());
                    variations.add(getVariant(ark, testCaseColumnName, i, variationPoints,hdrRow));
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return variations;
    }


    private List<String> getVariationPoints(final XSSFSheet ark, int hdrRow) {
        final XSSFRow titleRow = ark.getRow(hdrRow);
        List<String> variationPoints = new ArrayList<String>();
        for (int i = 1; i < titleRow.getPhysicalNumberOfCells(); i++) {
            final XSSFCell title = titleRow.getCell(i);
            if (StringUtils.isNotBlank(title.toString()) && title.toString().charAt(0) == '_'){
                continue;
            }
            if (StringUtils.isNotBlank(title.toString()) && title.toString().charAt(0) == '$'){
                continue;
            }
            if (title == null || StringUtils.isBlank(title.toString())) {
                break;
            } else {
                variationPoints.add(title.toString());
            }
        }
        return variationPoints;
    }


    private Variation getVariant(final XSSFSheet sheet, String testCaseColumnName, final int row, List<String> variationPoints, int hdrRow) {
        try {
            String variationName = hentCelle(testCaseColumnName, row, sheet,hdrRow).getRichStringCellValue().toString().trim();
            if (variationName.length() == 0) {
                throw new RuntimeException("Expected non-blank testcase name in for row :" + row);
            }
            Variation variation = new Variation(variationName);
            for (String variationPoint : variationPoints) {

                XSSFCell cell = hentCelle(variationPoint, row, sheet,hdrRow);
                String textValue = (cell == null||  getCellValueAsString(cell).trim().length() == 0 ) ?
                        "blank" : getCellValueAsString(cell);
                variation.getVariants().add(variationPoint + ":" + textValue);
            }
            return variation;
        } catch (Exception e) {
            throw new RuntimeException("Feil ved lesing av excel ark :" + e.getMessage());
        }
    }

    private XSSFCell hentCelle(final String colName, final int row, final XSSFSheet ark, int headerRow) {
        final XSSFRow titleRow = ark.getRow(headerRow);
        for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
            final XSSFCell title = titleRow.getCell(i);

            if (title.toString().equals(colName)) {
                log.debug("Henter celle rad :" + row + " Kolonne :" + colName);

                return ark.getRow(row).getCell(title.getColumnIndex());
            }
        }
        return null;
    }

    public  List<Variation> readTestVariationsFromExcel(String testCasesExcelFilePath, String sheetName, String testcaseColumnName, int hdrRow) {
        log.debug("Importerre excel fil :" + testCasesExcelFilePath);
        File file = new File(testCasesExcelFilePath);
        if (!file.exists()) {
            log.error("File does not exist :" + file.getAbsolutePath());
            throw new RuntimeException("Fil eksisterer ikke :" + file.getAbsolutePath());
        }
        final List<Variation> variations = doImport(file, sheetName, testcaseColumnName, hdrRow);
        for (Variation variation : variations) {
            log.debug("Importet variasjonsnavn : " + variation.getVariationName() + " " + variation.getVariants().toString());
        }
        log.debug("Importert antall variasjoner (testcases) : " + variations.size());
        return variations;
    }

   public  String getCellValueAsString(XSSFCell cell) {
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_FORMULA:
                switch(cell.getCachedFormulaResultType()) {
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        return new Double(cell.getNumericCellValue()).toString();

                    case XSSFCell.CELL_TYPE_STRING:
                        XSSFRichTextString xssfRichTextString =  cell.getRichStringCellValue();
                        return  xssfRichTextString.getString();
                }
                return new Boolean(cell.getBooleanCellValue()).toString();

            case XSSFCell.CELL_TYPE_BOOLEAN:

                return new Boolean(cell.getBooleanCellValue()).toString();
            case XSSFCell.CELL_TYPE_NUMERIC:
                return new Integer(new Double(cell.getNumericCellValue()).intValue()).toString();

            case XSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }
}

