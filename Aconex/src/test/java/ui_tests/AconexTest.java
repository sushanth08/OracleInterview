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
import utils.Retry;


/**
 * @author sbhat5
 *
 */
public class AconexTest {

	WebDriver driver = null;
	CommonUtils utils = null;
	
	/**
	 * Creates the driver and login to the aconex server
	 * @param testMethod
	 * @throws Exception
	 */
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
	
	/**
	 * Quits the driver, takes screenshots on failure
	 * @param result
	 * @throws Exception
	 */
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
	
	/**
	 * Scenario to validate Hotel VIP Resort  and Spa Document upload process
	 * @param projectName
	 * @throws Exception
	 */
	@Test(dataProvider="project1", enabled=true, retryAnalyzer = Retry.class)
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
	
	/**
	 * Scenario to validate the workflow for World Games
	 * @param projectName
	 */
	@Test(dataProvider="project2", enabled=true, retryAnalyzer = Retry.class)
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
	
	/**
	 * Scenario to validate the Blank mail sending process
	 * @throws Exception
	 */
	
	@Test(enabled=true, retryAnalyzer = Retry.class)
	public void scenario3() throws Exception {
		
		MailPage mailPage = new MailPage(driver);
		mailPage.navigateToBlankMail();
		mailPage.sentBlankMail("Transmittal");
	}
	
	
	
}
