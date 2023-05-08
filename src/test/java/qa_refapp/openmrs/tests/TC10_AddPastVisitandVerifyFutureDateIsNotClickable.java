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
import qa_refapp.openmrs.pageobjets.MergeVisitsPage;
import qa_refapp.openmrs.pageobjets.PatientDetailPage;
import qa_refapp.openmrs.pageobjets.RegistrationPage;
import qa_refapp.openmrs.pageobjets.VisitsPage;
import qa_refapp.openmrs.resources.ExtentFactory;

public class TC10_AddPastVisitandVerifyFutureDateIsNotClickable extends BaseTest{
	@Test(dataProvider = "dataset")
	public void AddPastVisitandVerifyFutureDateIsNotClickable(HashMap<String,String> input) {
		PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to patient details page");
		patientDet.selectAddPastVisitMenu();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Date picker opens");
		/*
		 * boolean elementState = patientDet.isFutureDateClickble();
		 * Assert.assertFalse(elementState);
		 */
		//String expectedAttriblteVal = "day disabled";
		String actualAttriblteVal = patientDet.verifyFuturedateClickablebyAttribute(input.get("expectedAttriblteVal"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Vefified that the future date is not clickable ");
		patientDet.closeDatePicker();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Date picker closed");
		Assert.assertEquals(actualAttriblteVal, input.get("expectedAttriblteVal"));
		
	}
	
	
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC10_AddPastVisitandVerifyFutureDateIsNotClickable.json");
		return new Object[][] {{data.get(0)}};
	}
}
