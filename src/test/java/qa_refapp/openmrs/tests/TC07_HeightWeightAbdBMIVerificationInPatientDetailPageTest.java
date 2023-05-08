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

public class TC07_HeightWeightAbdBMIVerificationInPatientDetailPageTest extends BaseTest{
	@Test(dataProvider = "dataset")
	public void HeightWeightAbdBMIVerificationInPatientDetailPageTest(HashMap<String,String> input) {
		PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "navigated to patient details page");
		float calcBMI = patientDet.calculatBMI(155, 62);
		float height = patientDet.heightinPatientDetailPage();
		float weight = patientDet.weightinPatientDetailPage();
		float bmi = patientDet.bmiinPatientDetailPage();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Calculated BMI is "+calcBMI);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "BMI shown in patient details page "+bmi);
		Assert.assertEquals(height, 155);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Height shown in patient details page is as expected. Height is "+height);
		Assert.assertEquals(weight, 62);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Weight shown in patient details page is as expected. Weight is "+weight);
		Assert.assertEquals(bmi, calcBMI);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Calculated BMI is same as shown in patient details page. BMI is  "+bmi);
	}
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC07_HeightWeightAbdBMIVerificationInPatientDetailPageTest.json");
		return new Object[][] {{data.get(0)}};
	}
}
