package pages;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import util.CaptureScreenshots;
import util.WindowHandler;

public class AFISAdjudication {

	WebDriver driver;
	Boolean found;
	WindowHandler handler;
	String screenshot_path = ".//Screenshots";
	CaptureScreenshots snap;

	String suspect_pids_id;
	String primary_pids_id;

	public AFISAdjudication(WebDriver driver) throws AWTException {

		this.driver = driver;
		snap = new CaptureScreenshots(driver);
		handler = new WindowHandler(driver);
	}

	public void launchAFIS(String item, String title) {

		String page_title = null;
		driver.findElement(By.id("adjudicate-links")).click();
		WebElement link = driver.findElement(By.xpath("//h2"));

		System.out.println(link.getText());
		if (link.getText().contains(item)) {
			found = true;
			page_title = driver.getTitle();
			Assert.assertEquals(page_title, title, "Adjudication Not Lunched");

		} else
			found = false;

		if (!found) {
			Assert.assertTrue(false, "Adjudication Link Not Found");
		}
	}

	public void matchVerifiedAFIS(String pending, String prisoner_no) throws InterruptedException, AWTException {
		WebElement link = driver.findElement(By.xpath("//h2"));
		found = false;
		
		if (link.getText().contains(pending)) {
			found = true;

		} else
			Assert.assertTrue(found, "Adjudication Pending Not Opened");

		driver.findElement(By.className("advanced-search")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("clear")).click();
		driver.findElement(By.name("utpConvictNo")).sendKeys(prisoner_no);
		driver.findElement(By.id("searchButtonId")).click();
		Thread.sleep(2000);
		List<WebElement> afis_pending = driver
				.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td/span"));

		if (afis_pending.size() < 1) {
			found = false;
			Assert.assertTrue(false, "Pending Profile Not Found");
		}
		for (WebElement p : afis_pending) {
			System.out.println(p.getText());
			if (p.getText().equals("MATCHED")) {
				handler = new WindowHandler(driver);
				found = true;
				p.click();
				Thread.sleep(15000);
				handler.switchToChild();
				WebElement breadcrumb = driver.findElement(By.xpath("//h2"));
				if (!(breadcrumb.getText().contains("View Matches")))
					found = false;

				List<WebElement> prisoner_id = driver.findElements(By.xpath("//a[@title='Click to view details']"));

				String primary_prisoner_id = prisoner_id.get(0).getText();
				String suspect_prisoner_id = prisoner_id.get(1).getText();
				System.out.println(primary_prisoner_id + suspect_prisoner_id);

				List<WebElement> pids_id = driver.findElements(By.xpath("//table/tbody/tr/td"));
				primary_pids_id = pids_id.get(6).getText();
				suspect_pids_id = pids_id.get(7).getText();
				System.out.println("PIDS:" + primary_pids_id + "PIDS:" + suspect_pids_id);

			
				handler.zoomOut(3);
				Thread.sleep(2000);
				snap.takeSnap(screenshot_path, "BeforeAfisVerified");
				driver.findElement(By.xpath("//input[@value='Positive Match']")).click();
				Thread.sleep(1000);
				driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
				driver.findElement(By.id("save")).click();
				driver.switchTo().defaultContent();
				Thread.sleep(1000);
				driver.findElement(By.id("popup_ok")).click();

				if (primary_prisoner_id.equals(prisoner_no)) {
					found = true;
					break;
				}

			}

		}
		handler.zoomIn(3);
		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

		handler.closeChild();
		handler.switchToParent();

		Search search = new Search(driver);
		search.launchSearch("Search", "Search :: Prison Inmate Admin Portal");
		driver.findElement(By.className("advanced-search")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("clear")).click();
		driver.findElement(By.name("utpConvictNo")).sendKeys(prisoner_no);
		driver.findElement(By.id("searchButtonId")).click();
		driver.navigate().refresh();
		Thread.sleep(2000);

		List<WebElement> result = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));

		if (suspect_pids_id.length() > 1) {
			if (result.get(0).getText().contains(suspect_pids_id) || result.get(11).getText().contains("REGISTERED")) {
				if (!(result.get(0).getText().contains(suspect_pids_id)))
					Assert.assertTrue(false, "PIDS No Not Found");
			} else
				Assert.assertTrue(false, "Status Not Found REGISTERED");
			System.out.println(result.get(0).getText());

		} else {
			if (result.get(11).getText().contains("ADJUDICATED")&&result.get(0).getText().contains(suspect_pids_id)) {
				found = true;
			} else
				Assert.assertTrue(false, "Status Not Found ADJUDICATED");
		}

	}

	public void matchClearAFIS(String pending, String prisoner_no) throws InterruptedException, AWTException {
		WebElement link = driver.findElement(By.xpath("//h2"));
		found = false;
		
		if (link.getText().contains(pending)) {
			found = true;

		} else
			Assert.assertTrue(found, "Adjudication Pending Not Opened");

		driver.findElement(By.className("advanced-search")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("clear")).click();
		driver.findElement(By.name("utpConvictNo")).sendKeys(prisoner_no);
		driver.findElement(By.id("searchButtonId")).click();
		Thread.sleep(2000);
		List<WebElement> afis_pending = driver
				.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td/span"));

		if (afis_pending.size() < 1) {
			found = false;
			Assert.assertTrue(false, "Pending Profile Not Found");
		}
		for (WebElement p : afis_pending) {
			System.out.println(p.getText());
			if (p.getText().equals("MATCHED")) {
				handler = new WindowHandler(driver);
				found = true;
				p.click();
				Thread.sleep(15000);
				handler.switchToChild();
				WebElement breadcrumb = driver.findElement(By.xpath("//h2"));
				if (!(breadcrumb.getText().contains("View Matches")))
					found = false;

				List<WebElement> prisoner_id = driver.findElements(By.xpath("//a[@title='Click to view details']"));

				String primary_prisoner_id = prisoner_id.get(0).getText();
				String suspect_prisoner_id = prisoner_id.get(1).getText();
				System.out.println(primary_prisoner_id + suspect_prisoner_id);

				List<WebElement> pids_id = driver.findElements(By.xpath("//table/tbody/tr/td"));
				primary_pids_id = pids_id.get(6).getText();
				suspect_pids_id = pids_id.get(7).getText();
				System.out.println("PIDS:" + primary_pids_id + "PIDS:" + suspect_pids_id);

	
				handler.zoomOut(5);
				Thread.sleep(2000);
				
				List<WebElement> pages = driver
						.findElements(By.xpath("//ul[@class='tsc_pagination tsc_paginationA tsc_paginationA01']/li"));
				int size = pages.size()-1;
				WebElement last_page = driver
						.findElement(By.xpath("//ul[@class='tsc_pagination tsc_paginationA tsc_paginationA01']/li["+size+"]"));

				int page_count = Integer.parseInt(last_page.getText());

				for (int i = 0; i < page_count; i++) {
					snap.takeSnap(screenshot_path, "BeforeAfisCleared"+i);
					driver.findElement(By.xpath("//input[@value='No Match']")).click();
					Thread.sleep(1000);
					// driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
					driver.findElement(By.id("popup_ok")).click();
					Thread.sleep(1000);
					driver.findElement(By.id("popup_ok")).click();
					// driver.switchTo().defaultContent();
					Thread.sleep(1000);
					// driver.findElement(By.id("popup_ok")).click();
				}
				if (primary_prisoner_id.equals(prisoner_no)) {
					found = true;
					break;
				}

			}

		}
		handler.zoomIn(5);
		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

		handler.closeChild();
		handler.switchToParent();
		
		SetUp setup = new SetUp(driver);
		setup.launchSetup("Setup", "Setup :: Prison Inmate Admin Portal");
		setup.clearCache();
		Search search = new Search(driver);
		search.launchSearch("Search", "Search :: Prison Inmate Admin Portal");
		driver.findElement(By.className("advanced-search")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("clear")).click();
		driver.findElement(By.name("utpConvictNo")).sendKeys(prisoner_no);
		driver.findElement(By.id("searchButtonId")).click();
		driver.navigate().refresh();
		Thread.sleep(2000);


		
		List<WebElement> result;
		found = false;
		do {
			driver.navigate().refresh();
			result = driver.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td"));
			Thread.sleep(10000);
		} while (result.get(0).getText().length() < 1);
	}

	
	public void viewFingerPrint(String pending, String prisoner_no) throws InterruptedException, AWTException {
		WebElement link = driver.findElement(By.xpath("//h2"));
		found = false;
		
		if (link.getText().contains(pending)) {
			found = true;

		} else
			Assert.assertTrue(found, "Adjudication Pending Not Opened");

		driver.findElement(By.className("advanced-search")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("clear")).click();
		driver.findElement(By.name("utpConvictNo")).sendKeys(prisoner_no);
		driver.findElement(By.id("searchButtonId")).click();
		Thread.sleep(2000);
		List<WebElement> afis_pending = driver
				.findElements(By.xpath("//table[@summary='search result']/tbody/tr/td/span"));

		if (afis_pending.size() < 1) {
			found = false;
			Assert.assertTrue(false, "Pending Profile Not Found");
		}
		for (WebElement p : afis_pending) {
			System.out.println(p.getText());
			if (p.getText().equals("MATCHED")) {
				handler = new WindowHandler(driver);
				found = true;
				p.click();
				Thread.sleep(15000);
				handler.switchToChild();
				WebElement breadcrumb = driver.findElement(By.xpath("//h2"));
				if (!(breadcrumb.getText().contains("View Matches")))
					found = false;

				List<WebElement> prisoner_id = driver.findElements(By.xpath("//a[@title='Click to view details']"));

				String primary_prisoner_id = prisoner_id.get(0).getText();
				String suspect_prisoner_id = prisoner_id.get(1).getText();
				System.out.println(primary_prisoner_id + suspect_prisoner_id);

				List<WebElement> pids_id = driver.findElements(By.xpath("//table/tbody/tr/td"));
				primary_pids_id = pids_id.get(6).getText();
				suspect_pids_id = pids_id.get(7).getText();
				System.out.println("PIDS:" + primary_pids_id + "PIDS:" + suspect_pids_id);


				handler.zoomOut(5);
				Thread.sleep(2000);
				
				List<WebElement> pages = driver
						.findElements(By.xpath("//ul[@class='tsc_pagination tsc_paginationA tsc_paginationA01']/li"));
				int size = pages.size()-1;
				WebElement last_page = driver
						.findElement(By.xpath("//ul[@class='tsc_pagination tsc_paginationA tsc_paginationA01']/li["+size+"]"));

				int page_count = Integer.parseInt(last_page.getText());

				int page_no=1;
				do {
					
					List<WebElement> finger = driver.findElements(By.xpath("//table/tbody/tr/td"));
					for(int j=0;j<finger.size();j++){
						if(finger.get(j).getText().contains("%")){
							String[] percentage_value = finger.get(j).getText().split("%");
							
							int percentage = Integer.parseInt(percentage_value[0]);
							if(percentage>0){
								finger.get(j+1).click();
								Thread.sleep(1000);
								driver.switchTo().frame(driver.findElement(By.xpath("//iFrame")));
								WebElement primary_finger= driver.findElement(By.id("primaryFinger"));
								WebElement suspect_finger= driver.findElement(By.id("suspectFinger"));
								found=false;
								if(primary_finger.isDisplayed()&&suspect_finger.isDisplayed())
										found=true;
								else{
									Assert.assertTrue(found, "Both Finger Not Found");
									snap.takeSnap(screenshot_path, "FingerPrintMissing"+driver.getCurrentUrl());
								}
								driver.findElement(By.name("Close")).click();
								driver.switchTo().defaultContent();	
							}
						}
					}
					if(page_no<page_count)
						driver.findElement(By.xpath("//a[text()='Next']")).click();
					page_no++;
					Thread.sleep(1000);
				}while(page_no<=page_count);
				if (primary_prisoner_id.equals(prisoner_no)) {
					found = true;
					break;
				}

			}

		}
		

		handler.zoomIn(5);
		if (!found)
			Assert.assertTrue(found, "Prisoner Not Found");

		handler.closeChild();
		handler.switchToParent();
	}
}
