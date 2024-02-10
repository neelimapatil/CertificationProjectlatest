package utilities;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import stepDefinitions.SignUpTest;
import stepDefinitions.TestBase;

public class PageOpeations extends TestBase {
	final static Logger logger=Logger.getLogger(PageOpeations.class);
    static boolean blnCategoryFlag = false;
    static boolean blnSubcategoryFlag = false;

	public void userSelectsCategorySubCategory(int intRowNo, int intColNo,String strCategorySelect, String strFileName,String strXpath) throws Exception
	{
		if(blnCategoryFlag==false && blnSubcategoryFlag==false)
		{
		String strReadValue = getParameterValueAgain(strCategorySelect, strFileName);
		String strExcelData=Genericfun.getExcelData("Sheet1",intRowNo,intColNo); //read data from 1st row and 0th col
		strReadValue=strReadValue.replace("${"+strXpath+"}", strExcelData);
		try
		{
			WebElement element=WaitForObjectExist(strReadValue);
			element.click();
		}
		catch (Exception ex)
		{

			if(intColNo==0)
			{
				blnCategoryFlag=true;
			System.out.println(ex.getMessage());
			logger.error("Failed to find "+strExcelData +" Category in Product selection: ");
			logger.error(ex.getMessage());
			}
			else if(intColNo==1)
			{
				blnSubcategoryFlag=true;
			System.out.println(ex.getMessage());
			logger.error("Failed to find "+strExcelData +" Subcategory in Product selection: ");
			logger.error(ex.getMessage());
			}
		}
		}
		else if(blnSubcategoryFlag=true)
		{
			logger.error("<b>User selects the subcateogy as per test data sheet - Test step is Skipped</b>");
		}
		}
	public void addToCartValidation(int intRowNo, int intColNo) throws Exception
	{
		if(blnCategoryFlag==false && blnSubcategoryFlag==false)
		{
		Thread.sleep(1000);
		String strExcelData=Genericfun.getExcelData("Sheet1",intRowNo,intColNo);
		WebElement tableElement = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']"));
		List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
		System.out.println("row size:"+rows.size());
		System.out.println("Row contains : "+rows.get(0).getText());
		// Iterate through each row
		outerloop:
			for (WebElement rowElement : rows) {
				List<WebElement> cells = rowElement.findElements(By.tagName("td"));

				// Iterate through each cell in the row
				for (WebElement cellElement : cells) {
					String cellData = cellElement.getText();
					// Process the cell data as needed
					System.out.print("cell data : "+cellData + "\t");
					if(cellData.equalsIgnoreCase(strExcelData))
					{
						Assert.assertEquals(strExcelData,cellData);
						break outerloop;
					}

				}

				// Move to the next line after processing each row
				System.out.println();
			}
		}
		else
		{
			logger.error("User validates that selected item is added in Cart - Test step is Skipped");
		}
	}
	public void backToHomePage()
	{
		blnCategoryFlag=false;
		blnSubcategoryFlag= false;
		try
		{
			String strReadValue = getParameterValueAgain("Obj_Link_Home", "OR.properties");
			WebElement To=WaitForObjectExist(strReadValue);
			To.click();
		}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
				logger.error("Failed to navigate to Home Page: "+ex.getMessage());
			}

}
	public void clickToAddCartAndNavigateToCart() throws Exception
	{
		if(blnCategoryFlag==false && blnSubcategoryFlag==false)
		{
		try
		{
			String strReadValue = getParameterValueAgain("Obj_Btn_Addtocart", "OR.properties");
			WebElement element=WaitForObjectExist(strReadValue);
			element.click();
		}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
				logger.error("Failed to click on Add Cart button: "+ex.getMessage());
			}
		  //3.Validate the alert and close the alert.
		  Thread.sleep(1000);
		  Alert alert = driver.switchTo().alert();
		  String msg = alert.getText();
		  System.out.println("alertmessage: "+msg);
		  
		  Assert.assertEquals("Product added.", msg.trim());
		  Thread.sleep(2000);
		  alert.accept();
			try
			{
				String strReadValue = getParameterValueAgain("Obj_Link_Cart", "OR.properties");
				WebElement element=WaitForObjectExist(strReadValue);
				element.click();
			}
				catch (Exception ex)
				{
					System.out.println(ex.getMessage());
					logger.error("Failed to navigate to Cart page: "+ex.getMessage());
				}
	}
	
	else
	{
		logger.error("User clicks on Add to Cart button and navigates to Cart Page - Test step is Skipped");
	}
}
}
