package qa_refapp.openmrs.pageobjets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class LoginPage extends AbstractComponent{
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	WebElement userId;

	@FindBy(id = "password")
	WebElement passwordEle;

	@FindBy(xpath = "//li[@id='Outpatient Clinic']")
	WebElement outpatientClinic;

	@FindBy(css = "#loginButton")
	WebElement logIn;

	public void logIn(String userName, String password, String location) {
		userId.sendKeys(userName);
		passwordEle.sendKeys(password);
		if (location.equalsIgnoreCase("Outpatient Clinic")) {
			outpatientClinic.click();
		}
		logIn.click();		
		
	}

	public void goTo(String url) {
		driver.get(url);
	}

}
