package qa_refapp.openmrs.pageobjets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class VisitsPage extends AbstractComponent{
	WebDriver driver;

	public VisitsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//a[@id='attachments.attachments.visitActions.default']")
	WebElement attachments;
	
	@FindBy(xpath = "//a[@id='referenceapplication.realTime.vitals']")
	WebElement captureVitals;
	
	@FindBy(css = ".icon-off")
	WebElement endVisitEle;
	
	@FindBy(xpath = "//div[@id='end-visit-dialog']/div")
	WebElement endVisitDialog;
	
	@FindBy(xpath = "//div[@id='end-visit-dialog']/div//button[@class='confirm right']")
	WebElement confirmEndVisit;
	
	@FindBy(css = "#visit-details h4")
	WebElement visitDetail;
	
	@FindBy(css = "//ul[@id='breadcrumbs']/li[2]/a")
	WebElement patientName;
	
	public AttachmentsPage clickOnAttachmentButton() {
		attachments.click();
		AttachmentsPage attach = new AttachmentsPage(driver);
		return attach;
	}
	
	public CaptureVitals clickOnCaptureVitals() {
		captureVitals.click();
		CaptureVitals capVitals = new CaptureVitals(driver);
		return capVitals;
	}
	public void endVisit() {
		endVisitEle.click();
		waitforVisibilityofElement(endVisitDialog);
		confirmEndVisit.click();
		redirectToPatientDetails();
		
	}

}
