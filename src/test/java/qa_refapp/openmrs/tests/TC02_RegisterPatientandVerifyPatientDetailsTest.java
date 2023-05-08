package qa_refapp.openmrs.tests;

import java.io.IOException;
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
import qa_refapp.openmrs.pageobjets.LoginPage;
import qa_refapp.openmrs.pageobjets.PatientDetailPage;
import qa_refapp.openmrs.pageobjets.RegistrationPage;
import qa_refapp.openmrs.resources.ExtentFactory;

public class TC02_RegisterPatientandVerifyPatientDetailsTest extends BaseTest {
	@Test(dataProvider = "dataset")
	public void RegisterPatientandVerifyPatientDetailsTest(HashMap<String,String> input) throws InterruptedException {
		RegistrationPage register = dashboardPage.registerPatient();
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated to Registration Page");
		register.fillDemographics(input.get("firstName"), input.get("lastName"), input.get("gender"), input.get("birthDate"), input.get("birthMon"), input.get("birthYr"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Filled firstName, lastName, Gender, BirthDate, BirthMonth, BirthYear Successfullf of the patient");
		register.fillContactInfo(input.get("address"), input.get("city"), input.get("state"), input.get("country"), input.get("postalCode"), input.get("phone"));
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Filled City, State, Country, Postal, Phone numbe rof patient");
		Assert.assertEquals(register.getName(), "Tom, Cat");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified patirnt name. The name is "+register.getName());
		Assert.assertEquals(register.getGender(), "Male");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified Gender of patient. Patient Gender "+register.getGender());
		Assert.assertEquals(register.getBirthDate(), "24, June, 1990");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified date of birth of patient. Patient date of birth"+register.getBirthDate());
		Assert.assertEquals(register.getAddress(), "13B, CBE, TN, IND, 641029");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified patient address. The address is "+register.getAddress());
		Assert.assertEquals(register.getPhoneNumber(), "9877656789");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified patient's phone number. The phone numbe is "+register.getAddress());
		PatientDetailPage patientDet = register.confirmSubmission();
		boolean isDisplayed = patientDet.verifyPatientid();
		Assert.assertTrue(isDisplayed);
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "patient Details update successfully and navigated to patient detail page");
	}

	@DataProvider
	public Object[][] dataset() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qa_refapp\\openmrs\\data\\TC02_RegisterPatientandVerifyPatientDetailsTest.json");
		return new Object[][] {{data.get(0)}};
	}
}
