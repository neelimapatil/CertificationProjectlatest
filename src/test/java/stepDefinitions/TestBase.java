package stepDefinitions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.SimpleLayout;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import utilities.ScreenshotCapture;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;

public class TestBase {
        private static String strPropertyFilePath= "src//test//resources//Properties//Configuation.properties";
		private static Properties properties;
		public static int intStepCounter = 1; 
		
		public static long longImplicitWait;
		private static String LOG_FOLDER;
		public  static WebDriver driver=null;
		public static WebDriverWait objWaitDriver=null;
		public static String strAlertMessage;
		
		final static Logger logger=Logger.getLogger(TestBase.class);	
		String strMessage;
		
			public TestBase(){
			BufferedReader reader;
			PropertyConfigurator.configure("D:\\Neelima\\Selenium_Edureka_class\\Workspace\\CertificationProjectlatest\\src\\test\\resources\\Properties\\log4j.properties");
			try {
				reader = new BufferedReader(new FileReader(strPropertyFilePath));
				properties = new Properties();
				try {
					properties.load(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Configuration.properties not found at " + strPropertyFilePath);
			}		
			SimpleLayout layout = new SimpleLayout(); 
			RollingFileAppender appender;

			try {
				appender = new RollingFileAppender(layout,"E:/log4j-application.log",true);
				logger.addAppender(appender);
				logger.setLevel((Level) Level.DEBUG);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
		public static String readConfigParam(String strParameterName){
			String strConfigParam = properties.getProperty(strParameterName);
			if(strConfigParam!= null) return strConfigParam;
			else throw new RuntimeException("No Parameter/Value is specified in the Configuration.properties file for the Key: "+strConfigParam);		
		}
	
		private void setLogFileName() {

			System.setProperty("logfile.name", LOG_FOLDER+"\\Log4j-application.log");
    	}
		
		public String getParameterValueAgain(String strParamterName, String strFileName) throws IOException
		{
			InputStream input=null;
			Properties objProp=new Properties();
			input=getClass().getClassLoader().getResourceAsStream(strFileName);
			objProp.load(input);
			String strParameterValue= objProp.getProperty(strParamterName);
			return strParameterValue;
		}
		public static void setStepCounter()
		{
			intStepCounter++;
		}
		public static WebElement WaitForObjectExist(String strXpath)
		{
			//return objWaitDriver.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strXpath)));
			return objWaitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
		}
		public void appLogin()
		{
		String strUserName= readConfigParam("Username");
		String strPassword= readConfigParam("Password");
		String strMessage;
		
		try
		{
			/*
			 * String strReadValue = getParameterValueAgain("Obj_Link_Login",
			 * "OR.properties"); WebElement element=WaitForObjectExist(strReadValue);
			 * element.click();
			 */
			
			WebElement element=driver.findElement(By.partialLinkText("Log"));
			element.click();
			String strReadValue = getParameterValueAgain("Obj_Txbx_Login", "OR.properties");
			element=WaitForObjectExist(strReadValue);
			element.sendKeys(strUserName);
			Thread.sleep(2000);
			strReadValue = getParameterValueAgain("Obj_Txbx_PassLogin", "OR.properties");
			element=WaitForObjectExist(strReadValue);
			element.sendKeys(strPassword);
			Thread.sleep(2000);
			strReadValue = getParameterValueAgain("Obj_Btn_Login", "OR.properties");
			element=WaitForObjectExist(strReadValue); element.click();
			/*
			 * Thread.sleep(2000); Alert alert = driver.switchTo().alert(); alertMessage =
			 * alert.getText(); System.out.println("Alert message: "+ alertMessage);
			 * Thread.sleep(2000); alert.accept();
			 */
			  
			ScreenshotCapture.captureScreenshot(strAlertMessage);
			 
			strMessage="Status:PASS,StepNumber:"+intStepCounter+",StepName:User is on Application Page";
			logger.info(strMessage);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			logger.error("Failed to signup: "+ex.getMessage());
		}
		}
}
