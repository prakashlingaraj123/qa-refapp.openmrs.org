package qa_refapp.openmrs.pageobjets;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class PatientDetailPage extends AbstractComponent {
	WebDriver driver;
	
	public PatientDetailPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	By patientIdTextLoc = By.xpath("//em[text()='Patient ID']");

	
	@FindBy(xpath = "//em[text()='Patient ID']")
	WebElement patientIDTextEle;

	
	@FindBy(xpath = "//em[text()='Patient ID']/following-sibling::span")
	WebElement patientIdEle;

	@FindBy(xpath = "//span[contains(text(),'year(s)')]")
	WebElement padientAge;
	
	@FindBy(xpath = "//a[@id='org.openmrs.module.coreapps.createVisit']")
	WebElement startVisitEle;
	
	@FindBy(css = "#start-visit-with-visittype-confirm")
	WebElement confirmVisit;
	
	@FindBy(xpath = "(//div[@class='att_thumbnail-image-section att_click-pointer'])[1]/following-sibling::div")
	WebElement attachmentCoption;
	
	@FindBy(xpath = "//visitbyencountertype[@class='ng-isolate-scope']/ul/li")
	List<WebElement> recentVisit;	
			
	By confirmButtoninDialog = By.cssSelector("#start-visit-with-visittype-confirm");
	
	@FindBy(xpath = "//ul[@class='float-left d-none d-lg-block']//a[@id='referenceapplication.realTime.endVisit'][1]")
	WebElement endVisit;	
	
	@FindBy(xpath = "//div[@id='end-visit-dialog']/div")
	WebElement endVisitDialog;	
	
	@FindBy(xpath = "//div[@id='end-visit-dialog']/div//button[@class='confirm right']")
	WebElement endVisitConfirm;
	
	@FindBy(xpath = "//span[@id='height']/span[1]")
	WebElement heightinPatientDetailPage;
	
	@FindBy(xpath = "//span[@id='weight']/span[1]")
	WebElement weightinPatientDetailPage;

	@FindBy(id = "calculated-bmi")
	WebElement bmiinPatientDetailPage;
	
	@FindBy(id = "org.openmrs.module.coreapps.mergeVisits")
	WebElement mergVisitsEle;
	
	@FindBy(xpath = "//a[@id='org.openmrs.module.coreapps.createRetrospectiveVisit']")
	WebElement addPasrVisit;
	
	@FindBy(xpath = "//table[@class=' table-condensed']/tbody/tr/td")
	List<WebElement> calanderDaysEle;
	
	@FindBy(xpath = "//div[@id='retrospective-visit-creation-dialog']//button[@class='cancel']")
	WebElement cancelDatePick;
	
	@FindBy(id = "org.openmrs.module.coreapps.deletePatient")
	WebElement deletepatientEle;
	
	@FindBy(id = "delete-reason")
	WebElement deletereasonText;
	
	@FindBy(xpath = "//div[@id='delete-patient-creation-dialog']//button[@class='confirm right']")
	WebElement confirmDeletion;
	

	
	public float heightinPatientDetailPage() {
		return getVituals(heightinPatientDetailPage);
	}
	
	public float weightinPatientDetailPage() {
		return getVituals(weightinPatientDetailPage);
	}
	
	public float bmiinPatientDetailPage() {
		return getVituals(bmiinPatientDetailPage);
	}
	
	public float calculatBMI(float height, float weight) {
		return calculatedBMI(height, weight);
	}
	
	public boolean verifyPatientid() {
		return patientIdEle.isDisplayed();
	}
	public String getPatientid() {
		return patientIdEle.getText();
	}
	public int getPatitentAge() {
		String partialStr = padientAge.getText();
		String age =splitString(partialStr, "year", 0);		 
		return Integer.parseInt(age);
	}
	public String getPatitentDOB() {
		String partialDOB = padientAge.getText();
		String partialDOB2 =splitString(partialDOB, "year\\(s\\) \\( ", 1);
		return splitString(partialDOB2, "\\)", 0);
	}
	

	public int patientAgeCalcBasedonDOB(String birthDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yyyy");
		Date date = formatter.parse(birthDate);
		Instant instant = date.toInstant();
		ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
		LocalDate givenDate = zone.toLocalDate();
		Period period = Period.between(givenDate, LocalDate.now());
		return period.getYears();
	}
	
	public VisitsPage startVisit() {
		startVisitEle.click();
		waitforElementTobeClickable(confirmButtoninDialog);
		confirmVisit.click();
		VisitsPage visits = new VisitsPage(driver);
		return visits;
	}
	
	public void endVisit() {
		endVisit.click();
		waitforVisibilityofElement(endVisitDialog);
		endVisitConfirm.click();
	}
	
	public String verifyAttachmentinPatientDetailPage() {
		redirectToPatientDetails();
		waitforVisibilityofElement(attachmentCoption);
		return attachmentCoption.getText();
	}
	public boolean verifyRecentVisitwithTag(String tag) {
		boolean match = false;
		for(int i=0; i<recentVisit.size();i++) {
			String ele = recentVisit.get(i).getText();
			String[] dateStr = ele.split("\\n");
			String visitDate = dateStr[0].trim();
			String actualTag = dateStr[1].trim();
			if(visitDate.equals(currentDate())) {
				if(actualTag.equals(tag)) {
				System.out.println("passed "+tag);
				match=true;
				break;
				}
			}		
		}
		return match;			
	}
	public MergeVisitsPage selectmergeVisitsMenu() {
		mergVisitsEle.click();
		MergeVisitsPage mergObj = new MergeVisitsPage(driver);
		return mergObj;
	}
	
	public void selectAddPastVisitMenu() {
		addPasrVisit.click();
	}
	
	public WebElement getFutureDateWebElement() {
		WebElement futureDateele = null;
		for(int j=0; j<calanderDaysEle.size();j++) {
			String attributeValue = calanderDaysEle.get(j).getAttribute("class");
			if(attributeValue.equalsIgnoreCase("day active")) {
				futureDateele  = calanderDaysEle.get(j+1);
				break;
			}
		}
		return futureDateele;	
	}
	public boolean isFutureDateClickble() {
		return isClickable(getFutureDateWebElement());
	}
	
	public String verifyFuturedateClickablebyAttribute(String expectedAttriblteVal){
		String featureDayAttributeValue = null;
		for(int j=0; j<calanderDaysEle.size();j++) {			
			String attribute = calanderDaysEle.get(j).getAttribute("class");
			if(attribute.equalsIgnoreCase(expectedAttriblteVal)) {
				featureDayAttributeValue = calanderDaysEle.get(j+1).getAttribute("class");
				System.out.println(featureDayAttributeValue);
				break;
			}
		}
		return featureDayAttributeValue;
	}
	
	public void closeDatePicker() {
		cancelDatePick.click();
	}
	
	public FindPatientRecordPage deletePatient(String deleteConfirmMes) {
		deletepatientEle.click();
		deletereasonText.sendKeys(deleteConfirmMes);
		confirmDeletion.click();
		FindPatientRecordPage patientRecord = new FindPatientRecordPage(driver);
		return patientRecord;	
	}

	
	
}
