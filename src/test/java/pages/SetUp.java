package pages;

import java.io.File;
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



public class SetUp {

	WebDriver driver;
	Boolean found;
	File screenshot;
	
	String screenshot_path = ".//Screenshots";
	//CaptureScreenshots snap;
	PopupController control;
	
	public SetUp(WebDriver driver) {
		this.driver = driver;
		control = new PopupController(driver);
	}

	
	
	public void launchSetup(String item, String title) {

		driver.navigate().refresh();
		String page_title = null;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("setup-links"))).click();

		WebElement link = driver.findElement(By.xpath("//h2"));

		System.out.println(link.getText());
		if (link.getText().contains(item)) {
			found = true;
			page_title = driver.getTitle();
			Assert.assertEquals(page_title, title, "Setup Not Lunched");

			
		} else
			found = false;

		if (!found){
			Assert.assertTrue(false, "Setup Link Not Found");
		}
			

	}

	public void clearCache() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//ul[@id='lookup-submenu']//a[text()='Cache']"))).click();
		driver.findElement(By.xpath("//input[@name='reset']")).click();
		Thread.sleep(2000);
		control.controlPopup(screenshot_path, "clearCache1");
/*		driver.switchTo().activeElement();
		String message = driver.findElement(By.id("popup_message")).getText();
		System.out.println(message);
		if (message.contains("sucessfully")) {
			driver.findElement(By.id("popup_ok")).click();
			// driver.switchTo().alert().accept();

		} else
			Assert.assertFalse(true, message);*/
	}

	public void createPrison(String prison_name, String prison_code, String prison_location, String email,
			String status) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//ul[@id='lookup-submenu']//a[text()='Prison']"))).click();

		driver.findElement(By.xpath("//input[@name='new']")).click();

		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));

		driver.findElement(By.name("prison.name")).sendKeys(prison_name);
		driver.findElement(By.name("prison.code")).sendKeys(prison_code);
		driver.findElement(By.name("prison.location")).sendKeys(prison_location);
		driver.findElement(By.name("prison.trustedEmail")).sendKeys(email);
		Select stat = new Select(driver.findElement(By.name("prison.status")));
		stat.selectByVisibleText(status);

		driver.findElement(By.xpath("//input[@name='save']")).click();


		Thread.sleep(1000);
		control.controlPopup(screenshot_path, "createPrison1");

/*		WebElement pop_up = driver.switchTo().activeElement();

		String message = driver.findElement(By.id("popup_message")).getText();
		System.out.println(message);
		if (message.contains("successfully") || message.contains("already")) {
			pop_up.click();
			if (message.contains("already")) {
				driver.findElement(By.xpath("//input[@name='close']")).click();

				Thread.sleep(2000);
			}

		} else
			Assert.assertFalse(true, message);*/
	}

	public void searchPrison(String prison_name) throws InterruptedException{
		List<WebElement> search = null;
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		//wait.until(ExpectedConditions.elementToBeClickable(By.name("prison.name"))).click();
		driver.navigate().refresh();
		driver.findElement(By.name("prison.name")).click();
		driver.findElement(By.name("prison.name")).sendKeys(prison_name);
		driver.findElement(By.name("search")).click();
		Thread.sleep(2000);
		
		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent){
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		}
		else
			Assert.assertTrue(false, "Lookup Not Found");
		
		
		found=false;
		
		for(WebElement s:search)
		{
			if (s.getText().contains(prison_name))
				found=true;				
		}	
		if(!found)
			Assert.assertTrue(found, "Lookup Not Found");
	}
	
	public void updatePrison(String prison_name, String prison_location, String email, String status) throws InterruptedException{
		driver.findElement(By.xpath("//img")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		
		driver.findElement(By.name("prison.name")).clear();
		driver.findElement(By.name("prison.name")).sendKeys(prison_name);
		driver.findElement(By.name("prison.location")).clear();
		driver.findElement(By.name("prison.location")).sendKeys(prison_location);
		driver.findElement(By.name("prison.trustedEmail")).clear();
		driver.findElement(By.name("prison.trustedEmail")).sendKeys(email);
		
		Select stat = new Select(driver.findElement(By.name("prison.status")));
		stat.selectByVisibleText(status);
		
		driver.findElement(By.name("save")).click();
		
		Thread.sleep(1000);
		
		control.controlPopup(screenshot_path, "updatePrison1");

/*		WebElement pop_up = driver.switchTo().activeElement();

		String message = driver.findElement(By.id("popup_message")).getText();
		System.out.println(message);
		if (message.contains("successfully") || message.contains("already")) {
			pop_up.click();

		} else
			Assert.assertFalse(true, message);*/
		
	}
	
	public void createLookup(String lookup_type, String value_en, String code, String status) throws InterruptedException{
		
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='lookup-submenu']//a[text()='Lookup']"))).click();
		
		driver.findElement(By.name("new")).click();
		Thread.sleep(2000);
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		
		//Select type = new Select(driver.findElement(By.name("lookup.type.id")));
		//type.selectByVisibleText(lookup_type);
//		driver.findElement(By.xpath("input[@name='lookup.value']")).click();
//		driver.findElement(By.xpath("input[@name='lookup.value']")).sendKeys(value_en);
		
		
//		driver.findElement(By.xpath("input[@name='lookup.code']")).click();
		//WebElement webElement = driver.findElement(By.xpath("lookup.type.id"));//You can use xpath, ID or name whatever you like
		//webElement.sendKeys(Keys.TAB);
		Select type = new Select(driver.findElement(By.name("lookup.type.id")));
		type.selectByVisibleText(lookup_type);
		
		Select stat = new Select(driver.findElement(By.name("lookup.status")));
		stat.selectByVisibleText(status);
		
		driver.findElement(By.name("lookup.value")).click();
		//driver.findElement(By.xpath("input[@name='lookup.value']")).click();
		driver.findElement(By.name("lookup.code")).sendKeys(code);
		
		driver.findElement(By.name("lookup.value")).sendKeys(value_en);
		

		
		driver.findElement(By.name("save")).click();
		
		Thread.sleep(1000);
		control.controlPopup(screenshot_path, "createLookup1");

/*		WebElement pop_up = driver.switchTo().activeElement();

		String message = driver.findElement(By.id("popup_message")).getText();
		System.out.println(message);
		if (message.contains("successfully")) {
			pop_up.click();

		} else
			Assert.assertFalse(true, message);*/
		
	}
	
	public void searchLookup(String value_en, String code) throws InterruptedException{
		List<WebElement> search = null;
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='lookup-submenu']//a[text()='Lookup']"))).click();
		driver.findElement(By.name("lookup.value")).sendKeys(value_en);
		driver.findElement(By.name("lookup.code")).sendKeys(code);
		driver.findElement(By.name("search")).click();
		
		Thread.sleep(2000);
		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent){
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		}
		else
			Assert.assertTrue(false, "Lookup Not Found");
		
		found=false;
		
		for(WebElement s:search)
		{
			if (s.getText().contains(value_en)||s.getText().contains(code))
				found=true;		
		}
		if(!found)
			Assert.assertTrue(found, "Lookup Not Found");
	}
	
	public void updateLookup(String status, String value_en) throws InterruptedException{
		

		Boolean isPresent = driver.findElements(By.xpath("//img")).size() > 0;
		if(isPresent)
			driver.findElement(By.xpath("//img")).click();
		else
			Assert.assertTrue(false, "Lookup Not Found");
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
		
		driver.findElement(By.name("lookup.value")).clear();
		driver.findElement(By.name("lookup.value")).sendKeys(value_en);
		
		Select stat = new Select(driver.findElement(By.name("lookup.status")));
		stat.selectByVisibleText(status);
		
		driver.findElement(By.name("save")).click();
		Thread.sleep(1000);
		
		control.controlPopup(screenshot_path, "updateLookup1");

/*		WebElement pop_up = driver.switchTo().activeElement();

		String message = driver.findElement(By.id("popup_message")).getText();
		System.out.println(message);
		if (message.contains("successfully")) {
			pop_up.click();

		} else
			Assert.assertFalse(true, message);*/
		
	}
	
}
