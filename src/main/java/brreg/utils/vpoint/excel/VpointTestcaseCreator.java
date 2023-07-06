package brreg.utils.vpoint.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class VpointTestcaseCreator {
    private static final Logger log = LoggerFactory.getLogger(VpointTestcaseCreator.class);

    public static File createNewExcelWithSheetContent(
            String excelFilePath, String sheetName,
            List<List<String>> sheetContent) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(sheetName);
        createExcelSheet(spreadsheet, sheetContent);
        try {
            File excelFile = new File(excelFilePath);
            FileOutputStream out = new FileOutputStream(excelFile);
            workbook.write(out);
            out.close();
            log.info("Excel fil : " + excelFilePath + " ble laget");
            return excelFile;
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            log.error("Fant ikke fil testCasesExcelFilePath", fe);
            throw new RuntimeException(fe);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error("Feilet ved skriving til fil", ioe);
            throw new RuntimeException(ioe);
        }
    }


    private static XSSFSheet createExcelSheet(
            XSSFSheet sheet,
            List<List<String>> listOLists) {
        int idx = 0;
        for (List<String> list : listOLists) {
            XSSFRow row = sheet.createRow(idx);
            createTextCellsInRow(row, 0, list);
            idx++;
        }
        return sheet;
    }


    private static XSSFRow createTextCellsInRow(XSSFRow row, int startColumn, List<String> cellTexts) {

        for (int i = 0; i < cellTexts.size(); i++) {
            XSSFCell cell = row.getCell(i + startColumn);
            if (cell == null) {
                cell = row.createCell(i + startColumn);
            }
            cell.setCellValue(cellTexts.get(i));
        }
        return row;
    }
}
