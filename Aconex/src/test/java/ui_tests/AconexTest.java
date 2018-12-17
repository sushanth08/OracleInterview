package ui_tests;
/**
 * 
 */


import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import junit.framework.Assert;
import pages.HomePage;
import pages.HotelResortPage;
import pages.LoginPage;
import pages.MailPage;
import pages.WorldGamesPage;
import utils.CommonUtils;


/**
 * @author sbhat5
 *
 */
public class AconexTest {

	WebDriver driver = null;
	CommonUtils utils = null;
	
	@BeforeMethod(alwaysRun = true)
	private void setupDriver(Method testMethod) throws Exception {
		System.out.println("Initializing the firefox driver and opening the Aconex server");
		String testMethodName = testMethod.getName();
		System.out.println("Testing method : " + testMethodName);
		
		TestConfig testConfig = new TestConfig();
		
		//Driver initialization and opening the browser
		utils = new CommonUtils();
		driver = utils.setUp(testConfig.getUrl());
		
		//Login to the Aconex Web page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(testConfig.getUsername(), testConfig.getPassword());
		
		Thread.sleep(5000);
		
	}
	
	@AfterMethod(alwaysRun = true)
	private void quitBrowser(ITestResult result) throws Exception{
		utils.captureScreenshots(result, driver);
		driver.quit();
	}
	
	@DataProvider(name="project1")
	public static Object[][] project1() {
		 return new Object[][] { 
			 { "Hotel VIP Resort & Spa" }
		 };
	}
	
	
	@Test(dataProvider="project1")
	public void scenario1(String projectName) throws Exception{
		HomePage homepage = new HomePage(driver);
		homepage.selectProject(projectName);
		homepage = null;
		HotelResortPage hotelResort = new HotelResortPage(driver);
		long docNumber = hotelResort.uploadDocument();
		boolean docStatus = hotelResort.checkDocumentStatus(docNumber);
		Assert.assertTrue("The document is not created", docStatus);
	}
	
	
	@DataProvider(name="project2")
	public static Object[][] project2() {
		 return new Object[][] { 
			 { "The World Games" }
		 };
	}
	
	
	@Test(dataProvider="project2")
	public void scenario2(String projectName) {
		
		HomePage homepage = new HomePage(driver);
		homepage.selectProject(projectName);
		homepage = null;
		
		WorldGamesPage worldGamesPage = new WorldGamesPage(driver);
		worldGamesPage.navigateToMyOrgWorkflow();
		int recordsCount = worldGamesPage.getRecordsCount();
		
		if(recordsCount==0) {
			Assert.fail("No Records Found");
		}
		
		
	}
	
	
	@Test
	public void scenario3() throws Exception {
		
		MailPage mailPage = new MailPage(driver);
		mailPage.navigateToBlankMail();
		mailPage.sentBlankMail("Transmittal");
	}
	
	
	
	
}
