package RunAutomations.NameBasedFileCreations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PersonalAndMainXLSXOps {
    private static Workbook workbook;
    private static Sheet sheet;
    private static Row row;
    private static Row headerRow;
    private static Iterator<Row> rowsIterator;
    private static Cell cell;


    public static void addPersonalExcelFormat(File patientFile) throws NoSuchFileException {
        try {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();
            String[] coulmnHeadings = { "No. of Visits", "Status", "COPAY Amt.", "PRIMARY INSURANCE",
                    "SECONDARY INSURANCE" };
            // u can make a font w bold, size, etc!
            headerRow = sheet.createRow(0);
            for (int i = 0; i < coulmnHeadings.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(coulmnHeadings[i]);
            }
            // ArrayList<
            // CreationHelper creationHelper = workbook.getCreationHelper();
            for (int i = 0; i < coulmnHeadings.length; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileout = new FileOutputStream(patientFile);
            workbook.write(fileout);
            fileout.close();
            workbook.close();
        } catch (Exception e) {
            System.out.println("User file does not exist");
            e.printStackTrace();
        }
    }

    public static void updateMainExcel(String patientName, String mainExcelFilePath) throws IOException, FileNotFoundException {
        try{
            File mainExcelFile = new File(mainExcelFilePath);
            FileInputStream readExcelFile = new FileInputStream(mainExcelFile);
            workbook = new XSSFWorkbook(readExcelFile);
            sheet = workbook.getSheet("Sheet0");
            rowsIterator = sheet.iterator();
            while(rowsIterator.hasNext()){
                row = rowsIterator.next();
                if(!(rowsIterator.hasNext())){
                    Row newPatientRow = sheet.createRow(row.getRowNum()+1);
                    newPatientRow.createCell(0).setCellValue(patientName);
                    newPatientRow.createCell(1).setCellValue("Insurances...");
                    newPatientRow.createCell(2);
                    Row gapRow = sheet.createRow(newPatientRow.getRowNum()+1);
                    gapRow.createCell(0);
                }
                else if(row.getRowNum()>2){
                    cell = row.getCell(0);
                    String patientAtCurrentRow = cell.getStringCellValue();
                    if(patientAtCurrentRow.compareTo(patientName)>0||!(rowsIterator.hasNext())){
                        sheet.shiftRows(row.getRowNum(), sheet.getLastRowNum(), 2);
                        Row squeezeNewPatientRow = sheet.createRow(row.getRowNum()-2);
                        squeezeNewPatientRow.createCell(0).setCellValue(patientName);
                        squeezeNewPatientRow.createCell(1).setCellValue("Insurances...");
                        squeezeNewPatientRow.createCell(2);
                        readExcelFile.close();
                        FileOutputStream fileout = new FileOutputStream(mainExcelFile);
                        workbook.write(fileout);
                        fileout.close();
                        workbook.close();
                        return;
                    }
                }  
            }
            readExcelFile.close();
            FileOutputStream fileout = new FileOutputStream(mainExcelFile);
            workbook.write(fileout);
            fileout.close();
            workbook.close();
        }catch(Exception exception){
            //ADD MORE DEFINED EXCEPTION MSGS!
            exception.printStackTrace();
        }
    }
}
