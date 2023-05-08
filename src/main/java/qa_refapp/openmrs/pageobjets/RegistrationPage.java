package qa_refapp.openmrs.pageobjets;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class RegistrationPage extends AbstractComponent {
	WebDriver driver;

	public RegistrationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	By title = By.tagName("h2");

	@FindBy(css = "input[name='givenName']")
	WebElement givenName;

	@FindBy(css = "input[name='familyName']")
	WebElement familyName;

	@FindBy(id = "gender-field")
	WebElement genderEle;

	@FindBy(css = "#birthdateDay-field")
	WebElement birthDay;

	@FindBy(id = "birthdateMonth-field")
	WebElement birthMonth;

	@FindBy(css = "#birthdateYear-field")
	WebElement birthYear;

	@FindBy(css = "#address1")
	WebElement addressEle;

	@FindBy(css = "#cityVillage")
	WebElement cityEle;

	@FindBy(css = "#stateProvince")
	WebElement stateEle;

	@FindBy(css = "#country")
	WebElement countryEle;

	@FindBy(css = "#postalCode")
	WebElement postalCodeEle;

	@FindBy(css = "input[name='phoneNumber']")
	WebElement phoneNumberEle;

	@FindBy(css = "#confirmation_label")
	WebElement confirmEle;

	@FindBy(xpath = "//span[text()='Name: ']/..")
	WebElement registeredNameEle;

	@FindBy(xpath = "//span[text()='Gender: ']/..")
	WebElement regsteredGenderEle;

	@FindBy(xpath = "//span[text()='Birthdate: ']/..")
	WebElement regsteredBirthdateEle;

	@FindBy(xpath = "//span[text()='Address: ']/..")
	WebElement regsteredAddressEle;

	@FindBy(xpath = "//span[text()='Phone Number: ']/..")
	WebElement registeredPhoneNumberEle;

	@FindBy(id = "submit")
	WebElement submit;

	public void fillDemographics(String firstName, String lastName, String gender, String birthDate, String birthMon,
			String birthYr) {
		waitforVisibilityofElementLocated(title);
		givenName.sendKeys(firstName);
		familyName.sendKeys(lastName);
		clickNext();
		selectbyValue(genderEle, gender);
		clickNext();
		birthDay.sendKeys(birthDate);
		selectbyValue(birthMonth, birthMon);
		birthYear.sendKeys(birthYr);
		clickNext();
	}

	public void fillContactInfo(String address, String city, String state, String country, String postalCode,
			String phone) {
		addressEle.sendKeys(address);
		cityEle.sendKeys(city);
		stateEle.sendKeys(state);
		countryEle.sendKeys(country);
		postalCodeEle.sendKeys(postalCode);
		clickNext();
		phoneNumberEle.sendKeys(phone);
		confirmEle.click();
	}

	public String getName() {
		return getPatientDetails(registeredNameEle);
	}

	public String getGender() {
		return getPatientDetails(regsteredGenderEle);
	}

	public String getBirthDate() {
		return getPatientDetails(regsteredBirthdateEle);

	}

	public String getAddress() {
		return getPatientDetails(regsteredAddressEle);
	}

	public String getPhoneNumber() {
		return getPatientDetails(registeredPhoneNumberEle);
	}

	public PatientDetailPage confirmSubmission() {
		submit.click();
		PatientDetailPage patientDet = new PatientDetailPage(driver);
		return patientDet;
		
	}

}
