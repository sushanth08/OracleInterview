/**
 * 
 */
package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;




/**
 * @author sbhat5
 * Common methods required are placed in this class
 */
public class CommonUtils {
	private WebDriver driver = null;


	/**
	 * Creates the firefox driver instance and returns the same
	 */
	static Object startupSync = new Object();
	private WebDriver setUpFirefoxDriver() throws IOException, InterruptedException{
		synchronized (startupSync) {
			FirefoxProfile firefoxprofile = new FirefoxProfile();
			String OS = System.getProperty("os.name").toLowerCase();
			if (OS.indexOf("mac") >= 0) {
				System.setProperty("webdriver.gecko.driver", "src/test/java/browserEngine/geckodriver");
			} else if (OS.indexOf("win") >= 0) {
				System.setProperty("webdriver.gecko.driver", "src/test/java/browserEngine/geckodriver.exe");
			}
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		return driver;
	}


	/**
	 * Initializes the firefox driver and opens the browser with the desired url
	 * @param applicationURL
	 * @return
	 */
	public WebDriver setUp(String applicationURL)
	{
		WebDriver driver = null;
		try{
			driver = setUpFirefoxDriver();
			if(applicationURL!=null) {
				driver.get(applicationURL);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * Captures the screenshot upon error
	 * @param result
	 * @param driver
	 */
	
	public void captureScreenshots(ITestResult result, WebDriver driver) {
		if(ITestResult.FAILURE==result.getStatus())
		{
		try 
		{
		// Create reference of TakesScreenshot
		TakesScreenshot ts=(TakesScreenshot)driver;
		 
		// Call method to capture screenshot
		File source=ts.getScreenshotAs(OutputType.FILE);
		 
		// Copy files to specific location here it will save all screenshot in our project home directory and
		// result.getName() will return name of test case so that screenshot name will be same
		FileUtils.copyFile(source, new File("././././Screenshots/"+result.getName()+".png"));
		 
		System.out.println("Screenshot taken");
		} 
		catch (Exception e)
		{
		 
		System.out.println("Exception while taking screenshot "+e.getMessage());
		} 
		 
		 
		 
		}
	}

	/**
	 * Wait for the element to be visible
	 * @param driver
	 * @param by
	 */
	public void webDriverWaitMethod(WebDriver driver, By by) {

		WebElement element = driver.findElement(by);
		webDriverWaitMethod(driver, element);
	}
	
	public void webDriverWaitMethod( WebDriver driver, WebElement element) {

		new WebDriverWait(driver, 5)
		.pollingEvery(1, TimeUnit.SECONDS)
		.withTimeout(5, TimeUnit.SECONDS)
		.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**
	 * Read json file and store it in a Map
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> jsonReader(String filePath) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		FileReader reader = new FileReader(filePath);
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(reader);
		for(String key:jsonObject.keySet()) {
			map.put(key, jsonObject.get(key).toString().replaceAll("\"", ""));
		}
		return map;
	}

}
