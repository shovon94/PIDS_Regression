package regression;

import java.awt.AWTException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.AFISAdjudication;
import pages.LoginPage;
import pages.Search;
import pages.SetUp;
import pages.UserManagementPage;
import util.CaptureScreenshots;
import util.ReadXMLData;



public class PidsRegressionSuite {

	WebDriver driver;

	LoginPage login;
	String screenshot_path = ".//Screenshots";
	ReadXMLData readData;
	
	@BeforeTest
	public void OpenBrowser() {
		System.out.println("Browser Opened");
		
		System.out.println(System.getProperty("user.dir"));
        System.setProperty("webdriver.chrome.driver",".\\Driver\\chromedriver.exe");
       //System.getProperty("user.dir")+"\\Driver\\chromedriver.exe"
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
		
		//driver = new HtmlUnitDriver();
        driver.manage().window().maximize();

        login = new LoginPage(driver);
        readData = new ReadXMLData(".\\Data\\testdata.xml");
	}
	
	@Test(priority=0)
	@Parameters({"URL","Login_Page_Tile"})
	public void launchPIDS(String url, String page_title) {
		
		//LoginPage login = new LoginPage(driver);
		login.lunchPIDS(url, page_title);

	}

	@Test(priority=1)
	@Parameters({"Login_User","Login_Password","Login_Breadcrumb","Login_Welcome_Message"})
	public void loginPIDS(String user, String password, String breadcrumb, String welocme_message) throws InterruptedException {
		//LoginPage login = new LoginPage(driver);
		login.performLogin(user, password, breadcrumb,welocme_message);
	}
	
	@Test(priority=2)
	@Parameters({"Menu_User_Management","User_Management_Page_Title"})
	public void openUserManagement(String menu, String page_tilte) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.lunchUserManagement(menu,page_tilte);
	}
	
	@Test(priority=3)
	@Parameters({"Menu_Create_User","Create_User_UserName","Create_User_Password","Create_User_Name","Create_User_Mobile","Create_User_Role","Status_Active"})
	public void createUser(String menu, String user_name, String password, String name, String mobile, String role, String status) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.createUser(menu, user_name, password, name, mobile, role, status);

	}
	
	@Test(priority=4)
	@Parameters({"Create_User_UserName","Create_User_Role"})
	public void searchUser(String username, String role) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.searchUser(username, role);
	}
	
	
	@Test(priority=5)
	@Parameters({"Update_User_Name","Update_User_Role","Status_Inactive","Update_User_Mobile"})
	public void updateUser(String name, String role, String status, String mobile) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.updateUser(name, mobile, role, status);
	}
	
	@Test(priority=6)
	@Parameters({"Menu_Role_Management","Role_Management_Page_Tile"})
	public void openRoleManagement(String menu, String title) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.launchRoleManagement(menu, title);
	}
	
	@Test(priority=7)
	@Parameters({"Create_Role_Code","Create_Role_DisplayName"})
	public void createRole(String code, String display_name) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.createRole(code,display_name);
	}
	
	@Test(priority=8)
	@Parameters({"Menu_Setup","Setup_Page_Title"})
	public void clearCache(String menu, String title) throws InterruptedException{
		SetUp setup = new SetUp(driver);
		setup.launchSetup(menu, title);
		setup.clearCache();
		
	}
	
	@Test(priority=9)
	@Parameters({"Menu_Role_Management","Role_Management_Page_Tile","Create_Role_DisplayName"})
	public void searchRole(String menu, String title, String display_name) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.launchRoleManagement(menu,title);
		manage_user.searchRole(display_name);
		
	}
	
	@Test(priority=10)
	@Parameters({"Create_Role_Code","Update_Role_DisplayName"})
	public void updatehRole(String code, String display_name) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.updateRole(code, display_name);
		
	}
	
	
	@Test(priority=11)
	@Parameters({"Menu_User_Assignment","User_Management_Page_Title"})
	public void launchUserAssignment(String menu, String title) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.launchUserAssignment(menu, title);
		
	}
	
	
	@Test(priority=12)
	@Parameters({"Create_User_UserName","Jail"})
	public void assignUser(String username, String jail) throws InterruptedException{
		UserManagementPage manage_user = new UserManagementPage(driver);
		manage_user.assignUser(username, jail);
		
	}
	
	@Test(priority=13)
	@Parameters({"Create_Prison_Name","Create_Prison_Code","Create_Prison_Location","Create_Prison_eMail","Status_Active"})
	public void createPrison(String name, String code, String location, String email, String status) throws InterruptedException{

		SetUp setup = new SetUp(driver);
		setup.launchSetup("Setup", "Setup :: Prison Inmate Admin Portal");
		setup.createPrison(name, code, location, email, status);
	}
	
	@Test(priority=14)
	@Parameters({"Create_Prison_Name"})
	public void searchPrison(String name) throws InterruptedException{

		SetUp setup = new SetUp(driver);
		setup.searchPrison(name);
	}
	
	@Test(priority=15)
	@Parameters({"Update_Prison_Name","Update_Prison_Location","Update_Prison_eMail","Status_Inactive"})
	public void updatePrison(String name, String location, String email, String status) throws InterruptedException{

		SetUp setup = new SetUp(driver);
		setup.updatePrison(name, location, email, status);
	}
	
	@Test(priority=16)
	@Parameters({"Lookup_Type","Create_Lookup_Code","Create_Lookup_Value","Status_Active"})
	public void createLookup(String type, String code, String value, String status) throws InterruptedException{

		SetUp setup = new SetUp(driver);
		setup.createLookup(type, value, code, status);
	}
	
	
	@Test(priority=17)
	@Parameters({"Create_Lookup_Code","Create_Lookup_Value"})
	public void searchLookup(String code, String value) throws InterruptedException{

		SetUp setup = new SetUp(driver);
		setup.searchLookup(value, code);
	}
	
	
	@Test(priority=18)
	@Parameters({"Update_Lookup_Value","Status_Inactive"})
	public void updateLookup(String value, String status) throws InterruptedException{

		SetUp setup = new SetUp(driver);
		setup.updateLookup(status, value);
	}
	
	@Test(priority=19)
	@Parameters({"Menu_Search","Search_Page_Title"})
	public void openSearch(String menu, String title) throws InterruptedException{
		
		Search search = new Search(driver);
		search.launchSearch(menu, title);
	}
	
	
	@Test(priority=20)
	@Parameters({"PrisonerID","Jail","PrisonerCategory"})
	public void seachByPrisonerID(String id, String jail, String category) throws InterruptedException{
		
		Search search = new Search(driver);
		search.quickSearchByPrisonerID(jail, id, category);
	}
	
	@Test(priority=21)
	@Parameters({"Jail","PrisonerName"})
	public void seachByPrisonerName(String jail, String name) throws InterruptedException{
		
		Search search = new Search(driver);
		search.quickSearchByPrisonerName(jail,name);
	
	}
	
	@Test(priority=22)
	@Parameters({"Jail","NID"})
	public void seachByNID(String jail, String nid) throws InterruptedException, AWTException{
		
		Search search = new Search(driver);
		search.quickSearchByNID(jail, nid);
	
	}
	
	@Test(priority=23)
	@Parameters({"Jail","CaseNo"})
	public void seachByCaseNo(String jail, String case_no) throws InterruptedException{
		
		Search search = new Search(driver);
		search.quickSearchByCaseNo(jail, case_no);
	
	}
	
	@Test(priority=24)
	@Parameters({"Prisoner_No_AdvanceSearch","FatherName","MotherName","Gender","MobileNo"})
	public void advancedSearch(String prisoner_no, String father, String mother, String gender, String mobile) throws InterruptedException, AWTException{
		
		Search search = new Search(driver);
		search.advanceSearch(prisoner_no,father,mother,mobile,gender);
	}
	
	@Test(priority=25)
	@Parameters({"Menu_AFIS_Adjudication","Adjudication_Page_Title"})
	public void openAFIS(String menu, String title) throws InterruptedException, AWTException{
		
		AFISAdjudication afis = new AFISAdjudication(driver);
		afis.launchAFIS(menu, title);
	}
	
	@Test(priority=26)
	@Parameters({"Status_Pending","Prisoner_No_AfisMacthFound"})
	public void matchVerifiedAFIS(String status, String prisoner_no) throws InterruptedException, AWTException{
		
		AFISAdjudication afis = new AFISAdjudication(driver);
		afis.matchVerifiedAFIS(status,prisoner_no);
	}
	
	@Test(priority=27)
	@Parameters({"Status_Pending","Prisoner_No_AfisClear"})
	public void matchClearAFIS(String status, String prisoner_no) throws InterruptedException, AWTException{
		
		AFISAdjudication afis = new AFISAdjudication(driver);
		//afis.launchAFIS("AFIS Adjudication", "Adjudication :: Prison Inmate Admin Portal");
		afis.matchClearAFIS(status, prisoner_no);
	}
	
	@Test(priority=28)
	@Parameters({"Status_Pending","Prisoner_No_AfisMacthFound"})
	public void viewFingerPrints(String status, String prisoner_no) throws InterruptedException, AWTException{
		
		AFISAdjudication afis = new AFISAdjudication(driver);
		afis.launchAFIS("AFIS Adjudication", "Adjudication :: Prison Inmate Admin Portal");
		afis.viewFingerPrint(status, prisoner_no);
	}
	@AfterMethod
	public void capture(ITestResult result){
		if(ITestResult.FAILURE==result.getStatus() || ITestResult.SUCCESS==result.getStatus())
		{
			CaptureScreenshots snap = new CaptureScreenshots(driver);
			snap.takeSnap(screenshot_path, result.getName());
		}
	}
	
	@AfterTest
	public void Quit() {
		driver.quit();
	}

}
