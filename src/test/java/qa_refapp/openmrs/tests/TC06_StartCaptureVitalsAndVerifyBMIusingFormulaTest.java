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

public class TC06_StartCaptureVitalsAndVerifyBMIusingFormulaTest extends BaseTest{
	@Test(dataProvider = "dataset")
		public void StartCaptureVitalsAndVerifyBMIusingFormulaTest(HashMap<String,String> input) {
			PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to patient details page");
			patientDet.endVisit();
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "End Visit Successfull");
			VisitsPage visits = patientDet.startVisit();
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Start Visit Successfull");
			CaptureVitals capVitals = visits.clickOnCaptureVitals();
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Opening Capturel Vitals Success");
			capVitals.fillCaptureVituals(155, 62);
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Filling Height and Weight Success");
			float calculatedBMI = capVitals.calculatedBMI(155, 62);
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Calculated BMI using BMI formula "+calculatedBMI);
			float capturedBMI = capVitals.getBMI();	
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "BMI shown in Capturel Visit "+capturedBMI);
			Assert.assertEquals(capturedBMI, calculatedBMI);
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Calculated BMI is same as Captured BMI. BMI is "+capturedBMI);
			capVitals.saveVitual();
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Save Visit Success");
			visits.endVisit();
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "End Visit Success");
			Assert.assertTrue(patientDet.verifyPatientid());
			ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to patient detail page successfully");
	}	
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC06_StartCaptureVitalsAndVerifyBMIusingFormulaTest.json");
		return new Object[][] {{data.get(0)}};
	}
}
