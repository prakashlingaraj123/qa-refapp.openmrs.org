package qa_refapp.openmrs.tests;

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
import qa_refapp.openmrs.pageobjets.DashboardPage;
import qa_refapp.openmrs.pageobjets.FindPatientRecordPage;
import qa_refapp.openmrs.pageobjets.LoginPage;
import qa_refapp.openmrs.pageobjets.PatientDetailPage;
import qa_refapp.openmrs.pageobjets.RegistrationPage;
import qa_refapp.openmrs.resources.ExtentFactory;

public class TC03_PatientDetailPageVerificationandVerifyAgeCorrectnessBasedOnDOBTset extends BaseTest {
	@Test(dataProvider = "dataset")
	public  void PatientDetailPageVerificationandVerifyAgeCorrectnessBasedOnDOBTset(HashMap<String,String> input) throws ParseException {
				
		PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
		Assert.assertTrue(patientDet.verifyPatientid());
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to Patient Details Page");
		int actualAge = patientDet.getPatitentAge();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Get patient form patient details success. Age is "+actualAge);
		int expectedAge = patientDet.patientAgeCalcBasedonDOB(patientDet.getPatitentDOB());
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Age Calculation based on DoB success. Age is "+expectedAge);
		Assert.assertEquals(actualAge, expectedAge);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Patiet age in patient details and calculated age is same");
	}
	
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC03_PatientDetailPageVerificationandVerifyAgeCorrectnessBasedOnDOBTset.json");
		return new Object[][] {{data.get(0)}};
	}
}
