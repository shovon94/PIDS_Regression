package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import util.PopupController;


public class UserManagementPage {

	WebDriver driver;
	Boolean found;
	String screenshot_path = ".//Screenshots";
	//CaptureScreenshots snap;
	PopupController control;
	
	public UserManagementPage(WebDriver driver) {
		this.driver = driver;

		control = new PopupController(driver);
	}

	public void lunchUserManagement(String item, String title) throws InterruptedException {

		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='USER MANAGEMENT']"))).click();
		WebElement link = driver.findElement(By.xpath("//h2"));
		
		
		System.out.println(link.getText());
		if (link.getText().contains(item)) {
			found = true;
			String page_title = driver.getTitle();
			Assert.assertEquals(page_title, title, "User Management Not Lunched");
		}
		else
			found = false;

		if (!found)
			Assert.assertTrue(false, "User Management Link Not Found");

	}

	public void createUser(String item, String username, String password, String name, String phone, String role, String status) throws InterruptedException {
		
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Create User']"))).click();
		Thread.sleep(3000);
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		WebElement link = driver.findElement(By.xpath("//form/div[@id='popup-bar']"));	
		
		found=false;
		if (link.getText().contains(item)) {
			found = true;
			Assert.assertTrue(found,"Create Form Not Opened");
		}
		
		driver.findElement(By.xpath("//input[@name='user.username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='user.appUserProfile.name']")).sendKeys(name);
		driver.findElement(By.xpath("//input[@name='user.password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='uun_confirm_password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@name='user.appUserProfile.phone']")).sendKeys(phone);
		driver.findElement(By.xpath("//select[@id='uun_role']")).click();
		Thread.sleep(1000);
		
		Select roles = new Select(driver.findElement(By.name("user.role.id")));
		roles.selectByVisibleText(role);
		
		Select stat = new Select(driver.findElement(By.name("user.status")));
		stat.selectByVisibleText(status);

		
		driver.findElement(By.xpath("//input[@value='Create User']")).click();
		Thread.sleep(1000);
		
		control.controlPopup(screenshot_path, "createUser1");

/*		WebElement pop_up = driver.switchTo().activeElement(); 
		
		
		
		String message = driver.findElement(By.id("popup_message")).getText();
		
		if(message.contains("successful")||message.contains("already"))
		{
			pop_up.click();
			if(message.contains("already"))
			{
				driver.findElement(By.xpath("//input[@name='close']")).click();
				
				Thread.sleep(2000);
			}

		}
			
		else
			Assert.assertFalse(true, message);*/
	}
	
	public void searchUser(String username, String role) throws InterruptedException{
		List<WebElement> search=null;
		driver.navigate().refresh();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='dashboard']"))).click();
		driver.findElement(By.xpath("//span[text()='USER MANAGEMENT']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='user.username']"))).sendKeys(username);
	
		//driver.findElement(By.xpath("//input[@name='user.username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='search']")).click();
		
		Thread.sleep(3000);
		
		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		else
			Assert.assertTrue(false, "User Not Found");
		
		
		found=false;
		
		for(WebElement s:search)
		{
			if (s.getText().contains(username))
				found=true;				
		}
		if(!found)
			Assert.assertTrue(found, "User Not Found");
	}

	public void updateUser(String name, String phone, String role, String status) throws InterruptedException {
		
		driver.findElement(By.xpath("//img")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		driver.findElement(By.xpath("//input[@name='user.appUserProfile.name']")).clear();
		driver.findElement(By.xpath("//input[@name='user.appUserProfile.name']")).sendKeys(name);
		driver.findElement(By.xpath("//input[@name='user.appUserProfile.phone']")).clear();
		driver.findElement(By.xpath("//input[@name='user.appUserProfile.phone']")).sendKeys(phone);
		
		Select roles = new Select(driver.findElement(By.name("user.role.id")));
		roles.selectByVisibleText(role);
		
		Select stat = new Select(driver.findElement(By.name("user.status")));
		stat.selectByVisibleText(status);
		
		driver.findElement(By.xpath("//input[@name='save']")).click();
		
		control.controlPopup(screenshot_path, "updateUser1");
		
/*		WebElement pop_up = driver.switchTo().activeElement(); 
		
		
		String message = driver.findElement(By.id("popup_message")).getText();
		
		if(message.contains("successfully"))
		{
			pop_up.click();

		}
			
		else
			Assert.assertFalse(true, message);*/
	}


	public void launchRoleManagement(String item, String title){
		
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a//span[text()='User']"))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='lookup-submenu']//a[text()='Role Management']"))).click();
		//driver.findElement(By.xpath("//a[text()='Role Management']")).click();
		
		WebElement link = driver.findElement(By.xpath("//h2"));
		System.out.println(link.getText());
		if (link.getText().contains(item)) {
			found = true;
			String page_title = driver.getTitle();
			Assert.assertEquals(page_title, title, "Role Management Not Lunched");
		}
		else
			found = false;

		if (!found)
			Assert.assertTrue(false, "Role Management Link Not Found");
	}
	
	
	public void createRole(String roleName, String displayroleName) throws InterruptedException{
		driver.findElement(By.xpath("//input[@value='Create Role']")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		driver.findElement(By.xpath("//input[@name='role.name']")).sendKeys(roleName);
		driver.findElement(By.xpath("//input[@name='role.displayName']")).sendKeys(displayroleName);
		driver.findElement(By.xpath("//input[@name='save']")).click();
		Thread.sleep(1000);
		
		
		control.controlPopup(screenshot_path, "createRole1");
/*		WebElement pop_up = driver.switchTo().activeElement(); 
		
		
		String message = driver.findElement(By.id("popup_message")).getText();
		
		if(message.contains("successfully")||message.contains("already"))
		{
			pop_up.click();
			Thread.sleep(2000);
			if(message.contains("already"))
			{
				driver.findElement(By.xpath("//input[@name='close']")).click();
				
				Thread.sleep(2000);
			}

		}
			
		else
			Assert.assertFalse(true, message);	*/
	}
	
	public void searchRole(String displayroleName) throws InterruptedException{
		List<WebElement> search=null;
		Select roles = new Select(driver.findElement(By.name("role.id")));
		roles.selectByVisibleText(displayroleName);
		driver.findElement(By.xpath("//input[@name='search']")).click();
		Thread.sleep(3000);
		
		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		else
			Assert.assertTrue(false, "Role Not Found");
		
		found=false;
		
		for(WebElement s:search)
		{
			if (s.getText().contains(displayroleName))
				found=true;				
		}
		if(!found)
			Assert.assertTrue(found, "Role Not Found");
		
	}
	
	
	public void updateRole(String roleName, String displayroleName) throws InterruptedException{
		driver.findElement(By.xpath("//img")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		
		driver.findElement(By.xpath("//input[@name='role.name']")).clear();
		driver.findElement(By.xpath("//input[@name='role.name']")).sendKeys(roleName);
		driver.findElement(By.xpath("//input[@name='role.displayName']")).clear();
		driver.findElement(By.xpath("//input[@name='role.displayName']")).sendKeys(displayroleName);
		
		driver.findElement(By.xpath("//input[@name='save']")).click();
		
		control.controlPopup(screenshot_path, "updateRole1");
		
/*		WebElement pop_up = driver.switchTo().activeElement(); 
		
		String message = driver.findElement(By.id("popup_message")).getText();
		
		if(message.contains("successfully")||message.contains("already"))
		{
			pop_up.click();
			Thread.sleep(2000);
			if(message.contains("already"))
			{
				driver.findElement(By.xpath("//input[@name='close']")).click();
				
				Thread.sleep(2000);
			}
		}
			
		else
			Assert.assertFalse(true, message);*/
		
	}
	
	
	public void launchUserAssignment(String item, String title){
		
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("useradmin-links"))).click();
		//driver.findElement(By.xpath("//a//span[text()='User']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='lookup-submenu']//a[text()='User Assignment']"))).click();
		//driver.findElement(By.xpath("//a[text()='Role Management']")).click();
		
		WebElement link = driver.findElement(By.xpath("//h2"));
		System.out.println(link.getText());
		if (link.getText().contains(item)) {
			found = true;
			String page_title = driver.getTitle();
			Assert.assertEquals(page_title, title, "User Assignment Not Lunched");
		}
		else
			found = false;

		if (!found)
			Assert.assertTrue(false, "User Assignment Link Not Found");
		
	}
	public void assignUser(String username, String prisonDist) throws InterruptedException{
		List<WebElement> search=null;
		driver.findElement(By.name("user.username")).sendKeys(username);
		driver.findElement(By.name("search")).click();
		Thread.sleep(2000);
		
		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		else
			Assert.assertTrue(false, "User Not Found");
		
		
		found=false;
		
		for(WebElement s:search)
		{
			if (s.getText().contains(username))
				found=true;
			else
				Assert.assertTrue(found, "User Not Found");
				
		}
		
		driver.findElement(By.xpath("//img")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		
		Select prison = new Select(driver.findElement(By.name("userAssignment.prisonId")));
		prison.selectByVisibleText(prisonDist);
		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Thread.sleep(1000);
	
		control.controlPopup(screenshot_path, "assignUser1");
/*		WebElement pop_up = driver.switchTo().activeElement(); 
		String message = driver.findElement(By.id("popup_message")).getText();
		if(message.contains("successful")||message.contains("successfully"))
		{
			pop_up.click();
			Thread.sleep(2000);
		}
			
		else
			Assert.assertFalse(true, message);*/
	}
	
}
