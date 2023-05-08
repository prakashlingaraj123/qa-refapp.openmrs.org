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

public class TC11_DeletePatientVerifyToasterTestAndSearchDeletedPatient extends BaseTest{
	@Test(dataProvider = "dataset")
	public void DeletePatientVerifyToasterTestAndSearchDeletedPatient(HashMap<String,String> input) {
	PatientDetailPage patientDet = findAndOpenPatientRecoed(input.get("Name"));
	ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to patient details page");
	FindPatientRecordPage patientRecord =patientDet.deletePatient(input.get("deleteConfirmMes"));
	ExtentFactory.getInstance().getExtent().log(Status.PASS, "Delete patient success");
	String deleteToastMes = patientDet.toasterMessage();
	Assert.assertEquals(deleteToastMes, "Patient has been deleted successfully");
	ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified toaster message of delete patient. The toaster is "+deleteToastMes);
	boolean match = patientRecord.isSearchTextBoxDisplayed();
	Assert.assertTrue(match);
	ExtentFactory.getInstance().getExtent().log(Status.PASS, "System automatically navigates to patient records page");
	String actualAttribute = patientRecord.searchDeletedPatient(input.get("Name"));
	Assert.assertEquals(actualAttribute, "dataTables_empty");
	ExtentFactory.getInstance().getExtent().log(Status.PASS, "Search deleted patient completed.No patient shown. Value of table attribute is "+actualAttribute);
	
}
	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC11_DeletePatientVerifyToasterTestAndSearchDeletedPatient.json");
		return new Object[][] {{data.get(0)}};
	}
}