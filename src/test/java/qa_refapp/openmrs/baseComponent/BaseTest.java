package qa_refapp.openmrs.baseComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import qa_refapp.openmrs.pageobjets.DashboardPage;
import qa_refapp.openmrs.pageobjets.FindPatientRecordPage;
import qa_refapp.openmrs.pageobjets.LoginPage;
import qa_refapp.openmrs.pageobjets.PatientDetailPage;

public class BaseTest {
	public WebDriver driver;
	public LoginPage loginpage ;
	public PatientDetailPage pdp;
	public DashboardPage dashboardPage;
	Properties prop;
	
	@FindBy(xpath = "//em[text()='Patient ID']/following-sibling::span")
	WebElement patientIdEle;

	public WebDriver initilizeDriver() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\qa_refapp\\openmrs\\resources\\globalData.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(option);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// Firefox code
		} else if (browserName.equalsIgnoreCase("edge")) {
			// edge code
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	@BeforeMethod(alwaysRun = true)
	public DashboardPage launchApplicationandLogin() throws IOException {
		driver = initilizeDriver();
		loginpage = new LoginPage(driver);
		String url = prop.getProperty("url");
		loginpage.goTo(url);
		String userName = prop.getProperty("userName");
		String password = prop.getProperty("password");
		String location = prop.getProperty("location");
		loginpage.logIn(userName, password, location);
		dashboardPage = new DashboardPage(driver);
		return dashboardPage;
		
	}
	@AfterMethod(alwaysRun = true)
	public void afterTest() {
		driver.close();
		
	}
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";	
	}
		
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
	
	public PatientDetailPage findAndOpenPatientRecoed(String Name){
		FindPatientRecordPage patientRecord = dashboardPage.findPatientRecord();
		PatientDetailPage patientDet = patientRecord.selectPatient(Name);
		return patientDet;
}
}