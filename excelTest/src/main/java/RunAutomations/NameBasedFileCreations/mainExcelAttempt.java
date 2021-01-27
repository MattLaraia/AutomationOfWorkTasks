package RunAutomations.NameBasedFileCreations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.nio.file.NoSuchFileException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class mainExcelAttempt {
    public static Workbook workbook;
    public static Sheet sheet;
    public static Row row;
    public static Row headerRow;
    public static Iterator<Row> rowsIterator;
    public static Iterator<Cell> cellsIterator;
    public static Cell cell;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        updateMainExcel("Sharon","C:/Users/mlar5/OneDrive/Desktop/Code Folder/Java Projects/Main Patient Table.xlsx");
    }



    public static void updateMainExcel(String patientName, String mainExcelFilePath) throws IOException, FileNotFoundException {
        try{
            File mainExcelFile = new File(mainExcelFilePath);
            FileInputStream readExcelFile = new FileInputStream(mainExcelFile); 
            System.out.println(readExcelFile.toString());
            workbook = new XSSFWorkbook(readExcelFile);
            DataFormatter dataFormatter = new DataFormatter();
            rowsIterator = sheet.iterator();
            while(rowsIterator.hasNext()){
                row = rowsIterator.next();
                //System.out.println(row.getRowNum());
                cellsIterator = row.iterator();
                while(cellsIterator.hasNext()){
                    cell = cellsIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.println(cellValue + "\t");
                    /*if(row.getRowNum()>2){
                        cell = row.getCell(0);
                        String patientAtCurrentRow = cell.getStringCellValue();
                        if(patientAtCurrentRow.compareTo(patientName)>0||patientAtCurrentRow==null){
                            System.out.println("HELLO");
                            Row newPatientRow = sheet.createRow(row.getRowNum()-1);
                            newPatientRow.createCell(0).setCellValue(patientName);
                            newPatientRow.createCell(1).setCellValue("Insurances...");
                            newPatientRow.createCell(2);
                            //if the patientName im adding comes later in alphabet, remember the last name's row in case it's supposed to come after it.
                            //OR, just wait until the name to b added comes before patientAtCurrent row, and then go back 1 row and add 2 news row w/ the name
                        }
                    }*/

                    
                    /*while(cellsIterator.hasNext()){
                        cell = cellsIterator.next();
                        //if statement
                        String cellValue = dataFormatter.formatCellValue(cell);
                    }*///ONLY NEED CELLS 1 AND 2 TO ADD NAME AND (MAYBE INSURANCE)
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
