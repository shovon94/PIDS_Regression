package util;

import java.io.File;
import java.io.IOException;



import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshots {
	
	WebDriver driver;
	public CaptureScreenshots(WebDriver driver){
		this.driver = driver;
	}
	File screenshot;
	
	public void takeSnap(String path, String fileName){
		screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    try {
	        FileUtils.copyFile(screenshot, new File(path+"\\"+fileName+".png"));
	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
	}


}
