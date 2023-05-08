package qa_refapp.openmrs.pageobjets;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class FindPatientRecordPage extends AbstractComponent{
	WebDriver driver;
	
	public FindPatientRecordPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@id='patient-search']")
	WebElement patientSerach;
	
	@FindBy(xpath = "//table[@id='patient-search-results-table']/tbody/tr/td")
	WebElement tableResultEle;
	
	@FindBy(xpath = "//tbody[@role='alert']/tr/td[2]")
	List<WebElement> patientNameEle;
	
	By tableResultLocator = By.xpath("//table[@id='patient-search-results-table']/tbody/tr/td");
	
	By patientSearchLocator = By.id("patient-search");
	
	
	public String searchDeletedPatient(String name) {
		waitforVisibilityofElementLocated(patientSearchLocator);
		patientSerach.sendKeys(name);
		waitforVisibilityofElementLocated(tableResultLocator);
		return tableResultEle.getAttribute("class");
	}
	
	public PatientDetailPage selectPatient(String patientName) {
		waitforVisibilityofElementLocated(patientSearchLocator);
		patientSerach.sendKeys(patientName);
		waitforVisibilityofElementLocated(tableResultLocator);
		for(WebElement ele:patientNameEle) {
			if(ele.getText().equalsIgnoreCase(patientName)) {
				ele.click();
				break;
			}
		}
		PatientDetailPage patientDet = new PatientDetailPage(driver);
		return patientDet;	
	}
	
	public boolean isSearchTextBoxDisplayed() {
		waitforVisibilityofElement(patientSerach);
		return patientSerach.isDisplayed();
		
	}
}
