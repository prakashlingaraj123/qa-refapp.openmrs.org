package qa_refapp.openmrs.tests;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import qa_refapp.openmrs.baseComponent.BaseTest;
import qa_refapp.openmrs.pageobjets.AttachmentsPage;
import qa_refapp.openmrs.pageobjets.DashboardPage;
import qa_refapp.openmrs.pageobjets.FindPatientRecordPage;
import qa_refapp.openmrs.pageobjets.LoginPage;
import qa_refapp.openmrs.pageobjets.PatientDetailPage;
import qa_refapp.openmrs.pageobjets.RegistrationPage;
import qa_refapp.openmrs.pageobjets.VisitsPage;
import qa_refapp.openmrs.resources.ExtentFactory;

public class TC04_AttachmentTosterofStartVisitTest extends BaseTest{
@Test(dataProvider = "dataset")
	public void AttachmentTosterofStartVisitTest(HashMap<String,String> input) throws InterruptedException, AWTException {
		PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to Patient Details Page");
		VisitsPage visits = patientDet.startVisit();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Started new visit successful");
		AttachmentsPage attach = visits.clickOnAttachmentButton();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Attachment added successfullf");
		String toastMes = attach.addAttachment(System.getProperty("user.dir")+input.get("filepath"),input.get("coption"));
		Assert.assertEquals(toastMes, "The attachment was successfully uploaded.");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Attachment toaster verified successfully. Toasrter message id "+toastMes);
		attach.redirectToPatientDetails();
		boolean isDisplayed= patientDet.verifyPatientid();
		Assert.assertTrue(isDisplayed);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to Padient Details page successfully");
		
	}
@DataProvider
public Object[][] dataset() throws IOException {
	List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
			+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC04_AttachmentTosterofStartVisitTest.json");
	return new Object[][] {{data.get(0)}};
}
}
	