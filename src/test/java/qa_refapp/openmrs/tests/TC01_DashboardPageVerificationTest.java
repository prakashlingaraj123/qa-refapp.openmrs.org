package qa_refapp.openmrs.tests;

import java.time.Duration;

import org.apache.logging.log4j.core.Logger;
import org.checkerframework.common.reflection.qual.GetMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import qa_refapp.openmrs.resources.MyLogger;
import qa_refapp.openmrs.baseComponent.BaseTest;
import qa_refapp.openmrs.pageobjets.DashboardPage;
import qa_refapp.openmrs.pageobjets.LoginPage;
import qa_refapp.openmrs.resources.ExtentFactory;
public class TC01_DashboardPageVerificationTest extends BaseTest{
	@Test
	public void DashboardPageVerificationTest() {
		
		String pagetitle = dashboardPage.getHomePageTitle();
		Assert.assertEquals(pagetitle, "Home");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified Page title - passed. Thre home page title is "+pagetitle);
		String dashboardTitle = dashboardPage.dashboardTitle();
		Assert.assertEquals(dashboardTitle, "Logged in as Super User (admin) at Outpatient Clinic.");
		ExtentFactory.getInstance().getExtent().log(Status.PASS, "Verified dashboard title - passed. The tile of dashboard is "+dashboardTitle);
}
}
