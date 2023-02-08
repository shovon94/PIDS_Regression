package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PopupController {
	
	WebDriver driver;
	CaptureScreenshots snap;
	public PopupController(WebDriver driver){
		this.driver = driver;
		snap = new CaptureScreenshots(driver);
	}
	
	public void controlPopup(String screenshot_path, String fileName) throws InterruptedException{
		WebElement pop_up = driver.switchTo().activeElement();  
		
		String message = driver.findElement(By.id("popup_message")).getText();
		
		if(message.contains("successful")||message.contains("already")||message.contains("successfully")||message.contains("sucessfully"))
		{
			snap.takeSnap(screenshot_path, fileName);
			pop_up.click();
			if(message.contains("already"))
			{
				driver.findElement(By.xpath("//input[@name='close']")).click();
				
				Thread.sleep(2000);
			}

		}
			
		else if(message.contains("failed")||message.contains("error")||message.length()>0){
			Assert.assertFalse(true, message);
		}
			
	}

}
