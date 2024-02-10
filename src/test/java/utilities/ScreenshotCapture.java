package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import stepDefinitions.Hooks;
import stepDefinitions.TestBase;



public class ScreenshotCapture extends TestBase{

static String strScreenshotFolderPath;


public ScreenshotCapture() {

}
 
public static void captureScreenshot(String Screenname) throws IOException
	{
			strScreenshotFolderPath=TestBase.readConfigParam("Screenshotfolder");
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
			String FileName =  new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
			
		     //FileName =FileName+(ScreenshotFolderPath +"//"+  Screenname + ".png"); 
			
			//File destFile = new File(FileName); 
			File destFile = new File(strScreenshotFolderPath+"\\"+Screenname+FileName);
			FileUtils.copyFile(srcFile, destFile);
			 	
	}
}
