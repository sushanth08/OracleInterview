package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utils.CommonUtils;

public class WorldGamesPage {

	private WebDriver driver = null;



	@FindBy(how = How.XPATH, using = "//div[text()='Workflows']")
	private WebElement workflowElement;

	@FindBy(how = How.XPATH, using = "//div[text()='Assigned to my organization']")
	private WebElement assignedToMyOrg;

	@FindBy(how = How.XPATH, using = "//table[@id='resultTable']")
	private WebElement resultsTable;

	@FindBy(how = How.XPATH, using = "")
	private WebElement resultsTableRows;
	//a[@id[starts-with(., 'foo') and string-length() > 3]]

	CommonUtils commonUtils = new CommonUtils();

	public WorldGamesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}


	public void navigateToMyOrgWorkflow() {
		commonUtils.webDriverWaitMethod(driver, workflowElement);
		workflowElement.click();
		assignedToMyOrg.click();
	}

	public int getRecordsCount() {
		driver.switchTo().frame(0);
		commonUtils.webDriverWaitMethod(driver, resultsTable);
		List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='resultTable']/tbody[@id[starts-with(., 'wfTasks_WF-') and string-length() > 12]]/tr"));
		return tableRows.size();
	}

}
