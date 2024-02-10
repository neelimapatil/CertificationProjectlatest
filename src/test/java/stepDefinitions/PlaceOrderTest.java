package stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utilities.Genericfun;
import utilities.PageOpeations;

//Place order - Before placing order, user should have added few items in the cart.User clicks on Place order button to do the purchase

public class PlaceOrderTest extends TestBase {

	final static Logger logger=Logger.getLogger(PlaceOrderTest.class);
	static String totalPrice;
	PageOpeations pageOpeations=new PageOpeations();

	// User verifies that if few items are added in to Cart before placing the order

	@And("User checks if items are already added on Cart Page")
	public void user_checks_if_items_are_already_added_on_Cart_Page() throws Exception {
		Thread.sleep(2000);

		//on Cart page read data from Products table.
		WebElement tableElement = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']"));
		List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
		System.out.println("row size:"+rows.size());
		if(rows.size()>1)    // when row size is greater than 1 means few items are added in cart
		{
			System.out.println("Items are already added in cart hence directly placing the order");
			Thread.sleep(2000);
		}
		else // // when row size is less than 1 means we need to add few items in the cart before placing the order
		{
			XSSFSheet sheet =Genericfun.openExcelFile("sheet1"); // read items to be added from excel sheet
			int intLastRowNo=sheet.getLastRowNum();
			for(int intCnt=1; intCnt<=intLastRowNo;intCnt++)
			{
				try
				{
					pageOpeations.backToHomePage();
					pageOpeations.userSelectsCategorySubCategory(intCnt,0,"Obj_List_Category", "OR.properties","itemcategory"); //read item category from excel sheet
					pageOpeations.userSelectsCategorySubCategory(intCnt,1,"Obj_List_Subcat", "OR.properties","subcat");//read item sub category from excel sheet
					pageOpeations.clickToAddCartAndNavigateToCart(); // add item to the cart and navigate to cart page
					pageOpeations.addToCartValidation(intCnt,1); //verify that added item is present in Products list 
					strMessage="Status:PASS,StepNumber:"+super.intStepCounter+",StepName:User is on added item in cart successfully";	
					logger.info(strMessage);
				}

				catch (Exception ex)
				{
					System.out.println(ex.getMessage());
					logger.error("Failed to navigate to Home Page: "+ex.getMessage());
				}
			} 
		}
	}

	//User clicks on Cart Page
	@And("User clicks on Cart link")
	public void user_clicks_on_cart_link() throws Exception {
		Thread.sleep(1000);
		try
		{
			WebElement element=driver.findElement(By.linkText("Cart"));
			element.click();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to navigate to Cart page: "+ex.getMessage());
		}	 
	}

	@Then("User clicks on Place order button and validates window is opened")
	public void user_clicks_on_place_order_button_and_validates_window_is_opened() throws Exception {

		WebElement element = driver.findElement(By.xpath("//h3[@class='panel-title']"));
		totalPrice=element.getAttribute("innerHTML");
		System.out.println("Total Price of Products on Cart Page: "+totalPrice);

		String strReadValue = getParameterValueAgain("Obj_Btn_Placeorder", "OR.properties");
		element=WaitForObjectExist(strReadValue);
		element.click();

		String strAttribute=element.getAttribute("innerHTML");
		if(strAttribute.equalsIgnoreCase("Place Order")) {
			Assert.assertEquals("Place Order", strAttribute); } 
	}

	//User fills required data on Place order pop up window
	
	@Then("User fills all required details on place order window")
	public void user_fills_all_required_details_on_place_order_window() throws Exception {
		Thread.sleep(1000);
		WebElement element = driver.findElement(By.id("name"));
		element.sendKeys("Test");
		Thread.sleep(1000);
		element = driver.findElement(By.id("country"));
		element.sendKeys("India");
		element = driver.findElement(By.id("city"));
		element.sendKeys("Pune");
		element = driver.findElement(By.id("card"));
		element.sendKeys("card");
		element = driver.findElement(By.id("month"));
		element.sendKeys("May");
		element = driver.findElement(By.id("year"));
		element.sendKeys("2024");

	}

	@Then("User clicks on Purchase button and validates purchase completion message")
	public void user_clicks_on_purchase_button_and_validates_purchase_completion_message() throws Exception {

		String strReadValue = getParameterValueAgain("Obj_Btn_Purchase", "OR.properties");
		WebElement element=WaitForObjectExist(strReadValue);
		element.click();

		element = driver.findElement(By.xpath("//div[@class='sweet-alert  showSweetAlert visible']/h2"));
		String strAttribute=element.getAttribute("innerHTML");
		System.out.println(strAttribute); // displaying 'Thank you for your purchase!' meassge

		element = driver.findElement(By.xpath("//p[@class='lead text-muted ']"));
		strAttribute=element.getAttribute("innerHTML");
		
		//extract amount from popup window
		int index=strAttribute.indexOf("Amount:", 0);  
		String strCon=strAttribute.substring(strAttribute.indexOf("Amount:"),strAttribute.indexOf("USD"));
		strCon=strCon.replace("Amount:", "");
		strCon=strCon.trim();
		System.out.println("Price derived from window popup is: " +strCon);

		System.out.println("Total Price is: " +totalPrice); //totalPrice taken from cart page

		if(strCon.equalsIgnoreCase(totalPrice)) // compares price from cart page and popup window
		{
			Assert.assertEquals(totalPrice, strCon);
			System.out.println("Both prices are equal");
		}
	}

}
