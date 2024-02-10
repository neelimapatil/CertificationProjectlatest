package utilities;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import stepDefinitions.TestBase;



public class Genericfun extends TestBase {
	
	public static String getExcelData(String sheetname, int rowNum, int cellNum) throws Exception {

		XSSFSheet sheet =openExcelFile(sheetname);
	 		// get the control of the row
	 		XSSFRow row = sheet.getRow(rowNum);

	 		// Get the data from desired cell of the current row
	 		String data = row.getCell(cellNum).getStringCellValue();

	 		System.out.println("Data from Test data sheet is = " + data);

			return data;

	}
	public static String searchValueInExcel(String sheetname, int rowNum, int cellNum) throws Exception {
	 		XSSFSheet sheet =openExcelFile(sheetname);

	 		// get the control of the row
	 
	for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
        XSSFRow row = sheet.getRow(rowIndex);
        if (row != null && row.getCell(0).getStringCellValue().equals("Field")) {
            return row.getCell(1).getRawValue();
        }
}
	return"";
	}
public static XSSFSheet openExcelFile(String sheetname) throws IOException
{
    //Create an object of File class to open xlsx file

    File file = new File("D:\\Neelima\\Selenium_Edureka_class\\Workspace\\CertificationProjectlatest\\src\\test\\resources\\TestData"+"\\"+"TestData.xlsx");

    //Create an object of FileInputStream class to read excel file

    FileInputStream inputStream = new FileInputStream(file);
    
 // Open Work book in a read mode.
 		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

 // Get the control of sheet
 		XSSFSheet sheet = workbook.getSheet(sheetname);
	return sheet;
}

}
