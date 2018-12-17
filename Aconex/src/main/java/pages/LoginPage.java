/**
 * 
 */
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utils.CommonUtils;

/**
 * @author sbhat5
 *
 */
public class LoginPage {
	
	private WebDriver driver = null;

	@FindBy(how = How.XPATH, using = "//input[@id='userName']")
	private WebElement usernameElement;

	@FindBy(how = How.XPATH, using = "//input[@id='password']")
	private WebElement passwordElement;

	@FindBy(how = How.XPATH, using = "//button[@id='login']")
	private WebElement loginBtn;
	
	@FindBy(how = How.XPATH, using = "//div[@class='auiForm-section logon-logo']")
	private WebElement title;
	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	public void login(String username, String password) {
		CommonUtils commonUtils = new CommonUtils();
		commonUtils.webDriverWaitMethod(driver, title);
		usernameElement.clear();
		passwordElement.clear();
		usernameElement.sendKeys(username);
		passwordElement.sendKeys(password);
		loginBtn.click();
		commonUtils=null;
	}

}
