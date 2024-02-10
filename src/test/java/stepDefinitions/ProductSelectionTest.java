package stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.PageOpeations;
import utilities.Genericfun;
// Product selection - User selects category/sub category on home page to select the item.Then user does 'add to cart' operation. 
//After adding items into the cart user verifies if added item is present in Cart>Product table

public class ProductSelectionTest extends TestBase{
	
	PageOpeations pageOpeations=new PageOpeations();
	
	@And("^User selects the category as per test data sheet$")
	public void user_clicks_product_category() throws Throwable {
		try
		{
			pageOpeations.userSelectsCategorySubCategory(1,0,"Obj_List_Category", "OR.properties","itemcategory");
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to navigate to Home Page: "+ex.getMessage());
		}

	} 

	@And("^User selects the subcateogy as per test data sheet$")
	public void user_clicks_product_subcategory() throws Throwable {
		try
		{
			pageOpeations.userSelectsCategorySubCategory(1,1,"Obj_List_Subcat", "OR.properties","subcat");
			String strReadValue = getParameterValueAgain("Obj_Label_Catselected", "OR.properties");
			String strExcelData=Genericfun.getExcelData("Sheet1",1,1);
			strReadValue=strReadValue.replace("${catselected}", strExcelData);
			WebElement To=WaitForObjectExist(strReadValue);
			String strAttribute=To.getAttribute("innerHTML");
			if(strAttribute.equalsIgnoreCase(strExcelData)) {
				Assert.assertEquals(strExcelData, strAttribute);
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to navigate to Home Page: "+ex.getMessage());
		}
	}	
	@And("^User clicks on Add to Cart button and navigates to Cart Page$")
	public void user_clicks_addtoaart_and_navigates_cartpage() throws Throwable {
		try
		{
			pageOpeations.clickToAddCartAndNavigateToCart();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to navigate to Home Page: "+ex.getMessage());
		}

	}

	@Then("^User validates that selected item is added in Cart$")
	public void validates_selected_items_addedtocart() throws Throwable {
		try
		{
			pageOpeations.addToCartValidation(1,1);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to navigate to Home Page: "+ex.getMessage());
		}
	}
	@And("^User nagivates back to Home Page$")
	public void user_navigates_tohomepage() throws Throwable {
		try
		{
			pageOpeations.backToHomePage();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to navigate to Home Page: "+ex.getMessage());
		}
	}

	@Then("^User adds remaining products from test data sheet to cart$")
	public void user_adds_remainingproducts() throws Throwable {
		XSSFSheet sheet =Genericfun.openExcelFile("sheet1");
		int intLastRowNo=sheet.getLastRowNum();
		for(int intCnt=2; intCnt<=intLastRowNo;intCnt++)
		{
			try
			{
				
				pageOpeations.userSelectsCategorySubCategory(intCnt,0,"Obj_List_Category", "OR.properties","itemcategory"); //read item category from excel sheet
				pageOpeations.userSelectsCategorySubCategory(intCnt,1,"Obj_List_Subcat", "OR.properties","subcat"); //read item sub category from excel sheet
				pageOpeations.clickToAddCartAndNavigateToCart(); // add item to the cart and navigate to cart page
				pageOpeations.addToCartValidation(intCnt,1);//verify that added item is present in Products list
				pageOpeations.backToHomePage();
			}

			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
				logger.error("Failed to navigate to Home Page: "+ex.getMessage());
			}
		} 
	}
}
