package pages;





import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage {
	
	
	WebDriver driver;
		
	public LoginPage(WebDriver driver){
		this.driver = driver;
	}
	
	
	public void lunchPIDS(String url, String title)
	{
		driver.get(url);
		String site_title= driver.getTitle();
		Assert.assertEquals(site_title, title, "PIDS URL Not Loaded Successfully");
	}
	
	public void performLogin(String username, String password, String title, String welcome_msg) throws InterruptedException{
		driver.findElement(By.xpath("//input[@id='user_id']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='breadcrumb']")));
		String page_title = driver.getTitle();
		if(page_title.equals(title))
		{
			String welcome = driver.findElement(By.xpath("//div[@id='header']/p[@id='loggedin']/span")).getText();
			Assert.assertEquals(welcome, welcome_msg, "Login Not Successful");
		}
		
	}

}
