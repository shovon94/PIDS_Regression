package pages;

import java.awt.AWTException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import util.CaptureScreenshots;
import util.WindowHandler;

public class Search {

	WebDriver driver;
	Boolean found;
	WindowHandler handler;
	String screenshot_path = ".//Screenshots";

	public Search(WebDriver driver) {
		this.driver = driver;
		// handler = new WindowHandler(driver);
	}

	public void launchSearch(String item, String title) {
		driver.navigate().refresh();
		String page_title = null;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-links"))).click();

		WebElement link = driver.findElement(By.xpath("//h2"));

		System.out.println(link.getText());
		if (link.getText().contains(item)) {
			found = true;
			page_title = driver.getTitle();
			Assert.assertEquals(page_title, title, "Search Not Lunched");

		} else
			found = false;

		if (!found) {
			Assert.assertTrue(false, "Search Link Not Found");
		}
	}

	public void quickSearchByPrisonerID(String prison_name, String prisoner_id, String category)
			throws InterruptedException {

		List<WebElement> search=null;
		Select prison = new Select(driver.findElement(By.id("prisonId")));
		prison.selectByVisibleText(prison_name);

		driver.findElement(By.id("simple-search")).clear();
		driver.findElement(By.id("simple-search")).sendKeys(prisoner_id);
		driver.findElement(By.id("quickSearchButtonId")).click();
		Thread.sleep(2000);

		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		else
			Assert.assertTrue(false, "Prisoner Not Found");
		

		found = false;
		ArrayList<String> result = new ArrayList<String>();

		for (WebElement s : search) {
			result.add(s.getText());
		}

		if (result.contains(prisoner_id) && result.contains(prison_name) && result.contains(category))
			found = true;

		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

	}

	public void quickSearchByPrisonerName(String prison_name, String prisoner_name) throws InterruptedException {

		List<WebElement> search=null;
		Select prison = new Select(driver.findElement(By.id("prisonId")));
		prison.selectByVisibleText(prison_name);

		driver.findElement(By.id("simple-search")).clear();
		driver.findElement(By.id("simple-search")).sendKeys(prisoner_name);
		driver.findElement(By.id("quickSearchButtonId")).click();
		Thread.sleep(2000);

		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		else
			Assert.assertTrue(false, "Prisoner Not Found");
	

		found = false;
		ArrayList<String> result = new ArrayList<String>();

		for (WebElement s : search) {
			result.add(s.getText());
		}

		if (result.size() > 0)
			found = true;

		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

	}

	public void quickSearchByNID(String prison_name, String nid) throws InterruptedException, AWTException {

		List<WebElement> search =null;
		Select prison = new Select(driver.findElement(By.id("prisonId")));
		prison.selectByVisibleText(prison_name);

		driver.findElement(By.id("simple-search")).clear();
		driver.findElement(By.id("simple-search")).sendKeys(nid);
		driver.findElement(By.id("quickSearchButtonId")).click();
		Thread.sleep(2000);

		Boolean isPresent = driver.findElements(By.xpath("//td[@class='view-profile']/img")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//td[@class='view-profile']/img"));
		else
			Assert.assertTrue(false, "Prisoner Not Found");


		if (search.size() < 1)
			Assert.assertTrue(false, "Prisoner Not Found");

		found = false;

		for (WebElement s : search) {
			handler = new WindowHandler(driver);
			s.click();
			Thread.sleep(2000);
			handler.switchToChild();
			driver.findElement(By.xpath("//li[@aria-controls='personalInfoBlock']")).click();
			Thread.sleep(1000);

			// WebDriverWait wait = new WebDriverWait(driver,
			// Duration.ofSeconds(30));
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("profile.personalInfo.nids")));

			String nid_text = driver.findElement(By.name("profile.personalInfo.nids")).getAttribute("value");
			CaptureScreenshots snap = new CaptureScreenshots(driver);
			snap.takeSnap(screenshot_path, "searchByNID_" + nid_text);

			if (nid_text.equals(nid)) {
				found = true;
				break;
			}

		}

		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

		handler.closeChild();
		handler.switchToParent();

	}

	public void quickSearchByCaseNo(String prison_name, String case_no) throws InterruptedException {

		List<WebElement> search =null;
		Select prison = new Select(driver.findElement(By.id("prisonId")));
		prison.selectByVisibleText(prison_name);

		driver.findElement(By.id("simple-search")).clear();
		driver.findElement(By.name("quickSearchString")).sendKeys(case_no);
		driver.findElement(By.id("quickSearchButtonId")).click();
		Thread.sleep(2000);

		Boolean isPresent = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
		else
			Assert.assertTrue(false, "Prisoner Not Found");
		

		found = false;
		ArrayList<String> result = new ArrayList<String>();

		for (WebElement s : search) {
			result.add(s.getText());
		}

		if (result.size() > 0)
			found = true;

		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");
	}

	public void advanceSearch(String prisoner_no, String fatherName, String motherName, String mobileNo, String gender)
			throws InterruptedException, AWTException {

		List<WebElement> search =null;
		driver.findElement(By.className("advanced-search")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("clear")).click();
		driver.findElement(By.name("fatherName")).click();
		driver.findElement(By.name("fatherName")).sendKeys(fatherName);
		driver.findElement(By.name("motherName")).click();
		driver.findElement(By.name("motherName")).sendKeys(motherName);
		driver.findElement(By.name("mobileNo")).click();
		driver.findElement(By.name("mobileNo")).sendKeys(mobileNo);

		driver.findElement(By.name("utpConvictNo")).click();
		driver.findElement(By.name("utpConvictNo")).sendKeys(prisoner_no);
		
		Select sex = new Select(driver.findElement(By.name("gender")));
		sex.selectByVisibleText(gender);

		driver.findElement(By.name("search")).click();
		Thread.sleep(2000);

		Boolean isPresent = driver.findElements(By.xpath("//td[@class='view-profile']/img")).size() > 0;
		if(isPresent)
			search = driver.findElements(By.xpath("//td[@class='view-profile']/img"));
		else
			Assert.assertTrue(false, "Prisoner Not Found");
		
		
		found = false;
		if (search.size() < 1)
			Assert.assertTrue(false, "Prisoner Not Found");

		for (WebElement s : search) {
			handler = new WindowHandler(driver);
			s.click();
			handler.switchToChild();
			Thread.sleep(2000);
			handler.switchToChild();
			driver.findElement(By.xpath("//li[@aria-controls='personalInfoBlock']")).click();
			Thread.sleep(1000);

			// WebDriverWait wait = new WebDriverWait(driver,
			// Duration.ofSeconds(30));
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("profile.personalInfo.nids")));

			String phone_text = driver.findElement(By.name("profile.personalInfo.mobileNo")).getAttribute("value");
			String gender_text = driver.findElement(By.name("applicationBean.profile.personalInfo.genderLookup"))
					.getAttribute("value");

			CaptureScreenshots snap = new CaptureScreenshots(driver);
			snap.takeSnap(screenshot_path, "phoneNo_" + phone_text);

			if (phone_text.equals(mobileNo) && gender_text.equals(gender)) {

				driver.findElement(By.xpath("//li[@aria-controls='familyBlock']")).click();
				List<WebElement> family_father = driver.findElements(By.xpath("//tr[@class='even-row']"));
				for (WebElement father : family_father) {
					if (father.getText().trim().contains(fatherName)) {
						found = true;
					} 
				}
				found=false;
				List<WebElement> family_mother = driver.findElements(By.xpath("//tr[@class='odd-row']"));
				for (WebElement mother : family_mother) {
					
					if (mother.getText().trim().contains(motherName)) {
						found = true;
					} 
				}
				snap.takeSnap(screenshot_path, "family_info");		
				break;
			}
		}

		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

		handler.closeChild();
		handler.switchToParent();

	}

}
