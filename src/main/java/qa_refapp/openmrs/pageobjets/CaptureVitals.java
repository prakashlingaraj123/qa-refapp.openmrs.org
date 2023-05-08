package qa_refapp.openmrs.pageobjets;

import java.text.DecimalFormat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class CaptureVitals extends AbstractComponent{
	WebDriver driver;

	public CaptureVitals(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(css = "#w8")
	WebElement heightEle;
	
	@FindBy(css = "#w10")
	WebElement weightEle;
	
	@FindBy(css = "#calculated-bmi-wrapper")
	WebElement bmiWrapper;
	
	@FindBy(xpath = "//span[text()='Confirm']")
	WebElement confirmVitualEle;
	
	@FindBy(css = "#save-form")
	WebElement saveForm;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement saveButton;
	
	
	public void fillCaptureVituals(float height,float weight) {
		heightEle.sendKeys(Float.toString(height));
		clickNext();
		weightEle.sendKeys(Float.toString(weight));
		clickNext();
	}
	public float getBMI() {
		String bmiWraper = bmiWrapper.getText();
		String bmiStr = splitString(bmiWraper, ":", 1);
		return Float.parseFloat(bmiStr);
	}
	public void confirmVitual() {
		confirmVitualEle.click();
	}
	
	public VisitsPage saveVitual() {
		saveForm.click();
		saveButton.click();
		VisitsPage visits = new VisitsPage(driver);
		return visits;
	}
	
	public float calculatBMI(float height, float weight) {
		return calculatedBMI(height, weight);
	}
	}
	
	

