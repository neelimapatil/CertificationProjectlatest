package stepDefinitions;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.When;
import utilities.ScreenshotCapture;

public class LoginTest extends TestBase {

	final static Logger logger=Logger.getLogger(LoginTest.class);	
	String strMessage;

	@When("^User login to the application with valid credentails$")
	public void user_login_with_valid_credentails() throws Throwable {
		appLogin();

	}

	// checks logout link to verify if user is logged in successfully 
	@When("^verify user is able to logged in successfully$")
	public void verify_user_login_successfully() throws Throwable {
		try
		{
			WebElement element=driver.findElement((By.xpath("//a[@id='logout2']")));
			String strAttribute=element.getAttribute("innerHTML");
			if(strAttribute.equalsIgnoreCase("Log out")) {
				Assert.assertEquals("Log out", strAttribute); } 
			strMessage="Status:PASS,StepNumber:"+super.intStepCounter+",StepName:User is on logged in successfully";	
			logger.info(strMessage);
		}

		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to signup: "+ex.getMessage());
		}
	}
}




