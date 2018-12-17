package pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.CommonUtils;
import utils.RobotWrite;
import static java.awt.event.KeyEvent.*;

public class HotelResortPage {
	
	private WebDriver driver = null;
	
	
	
	@FindBy(how = How.XPATH, using = "//div[text()='Documents']")
	private WebElement docsElement;

	@FindBy(how = How.XPATH, using = "//div[text()='Upload a New Document']")
	private WebElement uploadNewDoc;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Document Register']")
	private WebElement docRegister;
	
	
	@FindBy(how = How.XPATH, using = "//div[text()='Tasks']")
	private WebElement tasks;
	
	@FindBy(how = How.XPATH, using = "//table[@id='docEditFieldCollection']")
	private WebElement docTable;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='docno']//input")
	private WebElement docNoElement;
	
	@FindBy(how = How.XPATH, using = "//input[@name='revision_0']")
	private WebElement revisionElement;
	
	@FindBy(how = How.XPATH, using = "//input[@name='title_0']")
	private WebElement titleElement;
	
	@FindBy(how = How.XPATH, using = "//select[@id='doctype_0']")
	private WebElement docTypeElement;
	
	@FindBy(how = How.XPATH, using = "//select[@id='docstatus_0']")
	private WebElement docStatusElement;
	
	@FindBy(how = How.XPATH, using = "//div[@title='Attribute 1 List']")
	private WebElement attributeElement;
	
	@FindBy(how = How.XPATH, using = "//button[@id='btnUploadDocument']")
	private WebElement uploadDocumentBtn;
	
	@FindBy(how = How.XPATH, using = "//div[@id='attribute1_0']")
	private WebElement divAttribute;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='attribute1_0_panel']")
	private WebElement attributePanel;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='attribute1_0_bidi']//select")
	private WebElement selectAttributeElement;
	
	@FindBy(how = How.XPATH, using = "//button[@id='attribute1_0_bidi_add']")
	private WebElement addAttribute;
	
	@FindBy(how = How.XPATH, using = "//button[@id='attribute1_0_panel-commit']")
	private WebElement commitAttribute;
	
	
	@FindBy(how = How.XPATH, using = "//span[@id='clickToUpload']")
	private WebElement fileUploadElement;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='regSuccessPanel']/div/div")
	private WebElement closeBtn;
	
	@FindBy(how = How.XPATH, using = "//input[@id='docno']")
	private WebElement docNumberSearch;
	
	@FindBy(how = How.XPATH, using = "//button[@id='btnSearch_page']")
	private WebElement searchBtn;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='nav-bar']/button[@id='nav-bar-DOC']")
	private WebElement docBtnElement;
	
	@FindBy(how = How.XPATH, using = "//table[@id='resultTable']")
	private WebElement resultsTable;
	
	@FindBy(how = How.XPATH, using = "//button[@id='login']")
	private WebElement loginBtn;
	
	CommonUtils commonUtils = new CommonUtils();
	
	public HotelResortPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	
	public long uploadDocument() throws Exception{
		
		commonUtils.webDriverWaitMethod(driver, tasks);
		docsElement.click();
		uploadNewDoc.click();
		long docNo = fillDocumentFields();
		uploadDocumentBtn.click();
		closeBtn.click();
		return docNo;
		
	}
	
	
	public long fillDocumentFields() throws Exception{
		driver.switchTo().frame(0);
		long docNo = generateRandom(12);
		//uploadDocumentBtn.click();
		docNoElement.sendKeys(String.valueOf(docNo));
		String filePath = "src/test/java/dataFiles/document.json";
		Map<String, String> docValues = commonUtils.jsonReader(filePath);
		revisionElement.sendKeys(docValues.get("DocumentRevision"));
		titleElement.sendKeys(docValues.get("Title"));
		Select docType = new Select(docTypeElement);
		docType.selectByVisibleText(docValues.get("Document Type"));
		Select docStatus = new Select(docStatusElement);
		docStatus.selectByVisibleText(docValues.get("Document Status"));
		selectAttribute(docValues.get("Attribute"));
		//fileUpload();
		return docNo;
	}
	
	
	public void selectAttribute(String text) {
		divAttribute.click();
		attributePanel.isDisplayed();
		System.out.println("Opening select attribute panel");
		Select attributeSelect = new Select(selectAttributeElement);
		attributeSelect.selectByVisibleText(text);
		addAttribute.click();
		commitAttribute.click();
	}
	
	public static long generateRandom(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}
	
	
	
	public boolean checkDocumentStatus(long docNumber) throws Exception {
		driver.switchTo().defaultContent();
		docBtnElement.click();
		docRegister.click();
		driver.switchTo().frame(0);
		docNumberSearch.sendKeys(String.valueOf(docNumber));
		//Scroll down to the search button
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		commonUtils.webDriverWaitMethod(driver, searchBtn);
		Thread.sleep(4000);
		searchBtn.click();
		
		//Scroll down to the table
		js.executeScript("window.scrollBy(0,1200)");
		commonUtils.webDriverWaitMethod(driver, resultsTable);
		List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
		if(tableRows.size()==1) {
			return true;
		}
		return false;
	}
	
	
	public void fileUpload() throws Exception{
		fileUploadElement.click();

		String file = "/Users⁩/⁨sbhat5⁩/Documents⁩/⁨CRMS⁩/⁨AutomationCode⁩/⁨OracleInterview⁩/Aconex⁩/src⁩/test⁩/java⁩/dataFiles/test.pdf";
		// hit enter
		RobotWrite robot = new RobotWrite();

		robot.upload(driver, file);
		Thread.sleep(10000);
	}
	
	

}
