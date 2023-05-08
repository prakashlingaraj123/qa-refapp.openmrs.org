package qa_refapp.openmrs.pageobjets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class DashboardPage extends AbstractComponent{
	WebDriver driver;
	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

@FindBy(css="#referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension")
WebElement registeraPatient;

@FindBy(css="#coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension")
WebElement findPatientRecoed;

@FindBy(xpath="//div[@class='col-12 col-sm-12 col-md-12 col-lg-12']")
WebElement title;

public String dashboardTitle() {
	return title.getText();
}

public String getHomePageTitle() {
	String pageTitle = driver.getTitle();
	return pageTitle;
}

public RegistrationPage registerPatient() {
	registeraPatient.click();
	RegistrationPage register = new RegistrationPage(driver);
	return register;
}
public FindPatientRecordPage findPatientRecord() {
	findPatientRecoed.click();
	FindPatientRecordPage patientRecord = new FindPatientRecordPage(driver);
	return patientRecord;
}

}
