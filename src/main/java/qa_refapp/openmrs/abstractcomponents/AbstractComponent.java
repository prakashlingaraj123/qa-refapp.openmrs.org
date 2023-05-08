package qa_refapp.openmrs.abstractcomponents;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import qa_refapp.openmrs.pageobjets.PatientDetailPage;

public class AbstractComponent {
	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

	
	
	@FindBy(xpath = "//button[@id='next-button']")
	WebElement nextButton;

	@FindBy(css = ".PersonName-givenName")
	WebElement givenName;

	By nameLocator = (By.cssSelector(".PersonName-givenName"));
	
	@FindBy(xpath = "//div[@class='toast-item-wrapper']")
	WebElement toaster;
	@FindBy(xpath = "//ul[@class='navbar-nav ml-auto user-options']/li[3]")
	WebElement logOut;
	
	
	public float getVituals(WebElement element) {
		return Float.parseFloat(element.getText());
	}

	public String getPatientDetails(WebElement element) {
		return splitString(element.getText(), ":", 1);
	}

	public void waitforVisibilityofElementLocated(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitforElementTobeClickable(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitforVisibilityofElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	public void waitforElementTobeClickablebyEle(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public PatientDetailPage redirectToPatientDetails() {
		driver.navigate().refresh();
		waitforElementTobeClickable(nameLocator);
		givenName.click();
		PatientDetailPage patientDet = new PatientDetailPage(driver);
		return patientDet;
		
	}

	public void clickNext() {
		nextButton.click();
	}

	public void selectbyValue(WebElement element, String value) {
		Select gender = new Select(element);
		gender.selectByValue(value);

	}
	
	

	public String splitString(String strToSplit, String splitChar, int index) {
		String[] splitStr = strToSplit.split(splitChar);
		return splitStr[index].trim();
	}

	public String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
		Date currentDate = new Date();
		return dateFormat.format(currentDate);
	}

	public float calculatedBMI(float height, float weight) {
		float bmi = (100 * 100 * weight) / (height * height);
		DecimalFormat df = new DecimalFormat("#.0");
		String actualBMIstr = df.format(bmi);
		return Float.parseFloat(actualBMIstr);
	}

	public boolean isClickable(WebElement element) {
		try {
			waitforElementTobeClickablebyEle(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public String toasterMessage() {
		waitforVisibilityofElement(toaster);
		return toaster.getText();
	}
	public void logOutAppln() {
		
		waitforVisibilityofElement(logOut);
		logOut.click();
	}

}
