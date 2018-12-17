package pages;

import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

import utils.CommonUtils;

public class MailPage {

	
	private WebDriver driver = null;



	@FindBy(how = How.XPATH, using = "//div[text()='Mail']")
	private WebElement mailElement;

	@FindBy(how = How.XPATH, using = "//div[text()='Blank Mail']")
	private WebElement blankMail;
	
	
	@FindBy(how = How.XPATH, using = "//select[@id='Correspondence_correspondenceTypeID']")
	private WebElement type;
	
	@FindBy(how = How.XPATH, using = "//input[@id='SPEED_ADDRESS_TO']")
	private WebElement addressTo;
	
	
	@FindBy(how = How.XPATH, using = "//input[@id='Correspondence_subject']")
	private WebElement subject;
	
	@FindBy(how = How.XPATH, using = "//div[@id='multiselect0']")
	private WebElement divAttribute;
	
	@FindBy(how = How.XPATH, using = "//div[@id='attributePanel']")
	private WebElement attributePanel;
	
	@FindBy(how = How.XPATH, using = "//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//select")
	private WebElement selectPrimaryAttribute;
	
	@FindBy(how = How.XPATH, using = "//div[@id='attributeBidi_SECONDARY_ATTRIBUTE']//select")
	private WebElement selectSecondaryAttribute;
	
	@FindBy(how = How.XPATH, using = "//button[@id='attributeBidi_PRIMARY_ATTRIBUTE_add']")
	private WebElement addPrimaryBtn;
	
	@FindBy(how = How.XPATH, using = "//button[@id='attributeBidi_SECONDARY_ATTRIBUTE_add']")
	private WebElement addSecondaryBtn;
	
	@FindBy(how = How.XPATH, using = "//button[@id='attributePanel-commit']")
	private WebElement commitAttribute;
	
	@FindBy(how = How.XPATH, using = "//button[@id='btnSend']")
	private WebElement sendBtn;
	
	
	CommonUtils commonUtils = new CommonUtils();

	public MailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	public void navigateToBlankMail() {
		commonUtils.webDriverWaitMethod(driver, mailElement);
		mailElement.click();
		blankMail.click();
	}
	
	public void sentBlankMail(String typeName) throws Exception {
		driver.switchTo().frame(0);
		String filePath = "src/test/java/dataFiles/mail.json";
		Map<String, String> mailValues = commonUtils.jsonReader(filePath);
		Select select = new Select(type);
		select.selectByVisibleText(typeName);
		addressTo.sendKeys(mailValues.get("name"));
		addressTo.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		subject.sendKeys(mailValues.get("Subject"));
		selectAttribute(mailValues.get("Attribute1"), mailValues.get("Attribute2"));
		sendBtn.click();
		
	}
	
	public void selectAttribute(String primaryValue, String secondaryValue) {
		divAttribute.click();
		attributePanel.isDisplayed();
		System.out.println("Opening select attribute panel");
		Select primarySelect = new Select(selectPrimaryAttribute);
		primarySelect.selectByVisibleText(primaryValue);
		Select secondarySelect = new Select(selectSecondaryAttribute);
		secondarySelect.selectByVisibleText(secondaryValue);
		addPrimaryBtn.click();
		addSecondaryBtn.click();
		commitAttribute.click();
		
	}
}
