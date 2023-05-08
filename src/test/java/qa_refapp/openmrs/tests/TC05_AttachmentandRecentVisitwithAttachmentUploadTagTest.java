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

public class TC05_AttachmentandRecentVisitwithAttachmentUploadTagTest extends BaseTest {
	@Test(dataProvider = "dataset")
	public void AttachmentandRecentVisitwithAttachmentUploadTagTest(HashMap<String,String> input) {
		PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to Padient Details page successfully");
		String actualCoption = patientDet.verifyAttachmentinPatientDetailPage();
		Assert.assertEquals(actualCoption, "Attachment1");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Attachment verified in patient details page successfully");
		boolean tag =  patientDet.verifyRecentVisitwithTag(input.get("tag"));
		Assert.assertTrue(tag);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Recent Visit verified. The visit is "+input.get("tag"));
	}
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC05_AttachmentandRecentVisitwithAttachmentUploadTagTest.json");
		return new Object[][] {{data.get(0)}};
	}	
	
}

