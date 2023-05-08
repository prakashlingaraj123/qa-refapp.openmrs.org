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
import qa_refapp.openmrs.pageobjets.CaptureVitals;
import qa_refapp.openmrs.pageobjets.DashboardPage;
import qa_refapp.openmrs.pageobjets.FindPatientRecordPage;
import qa_refapp.openmrs.pageobjets.LoginPage;
import qa_refapp.openmrs.pageobjets.PatientDetailPage;
import qa_refapp.openmrs.pageobjets.RegistrationPage;
import qa_refapp.openmrs.pageobjets.VisitsPage;
import qa_refapp.openmrs.resources.ExtentFactory;

public class TC08_RecentVisitWithCurrentDateandVitalTest extends BaseTest{
	@Test(dataProvider = "dataset")
	public void RecentVisitWithCurrentDateandVitalTest(HashMap<String,String> input) {
		
		PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to patient details page");
		boolean tagVital =  patientDet.verifyRecentVisitwithTag(input.get("tag"));
		Assert.assertTrue(tagVital);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified recent visit.The revent visit with current date is "+tagVital);
	}
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC08_RecentVisitWithCurrentDateandVitalTest.json");
		return new Object[][] {{data.get(0)}};
	}
}
