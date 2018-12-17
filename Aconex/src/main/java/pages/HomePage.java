package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage {
	
	
	private WebDriver driver = null;

	@FindBy(how = How.XPATH, using = "//span[@class='projectChanger-arrow']")
	public WebElement projectChangerArrow;


	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	
	public void selectProject(String projectName) {
		
		
		projectChangerArrow.click();
		String xpathExpression = "//div[@title='"+projectName+"']";
		driver.findElement(By.xpath(xpathExpression)).click();
	}

}
