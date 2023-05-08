package qa_refapp.openmrs.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.hc.core5.util.ByteArrayBuffer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import qa_refapp.openmrs.pageobjets.LoginPage;

public class StandaloneTest {

	public static void main(String[] args) throws ParseException, AWTException, InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://qa-refapp.openmrs.org/openmrs/login.htm");
		//Login and verify dashboard page
		LoginPage loginpage = new LoginPage(driver);
		driver.findElement(By.id("username")).sendKeys("Admin");
		driver.findElement(By.id("password")).sendKeys("Admin123");
		driver.findElement(By.xpath("//li[@id='Outpatient Clinic']")).click();
		driver.findElement(By.cssSelector("#loginButton")).click();
		String homePage = driver.getTitle();
		Assert.assertEquals("Home", homePage);
		System.out.println(homePage);
		
		//Register a patient and verify given details.
		driver.findElement(By.cssSelector("#referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
		driver.findElement(By.cssSelector("input[name='givenName']")).sendKeys("Prakash");
		driver.findElement(By.cssSelector("input[name='familyName']")).sendKeys("Lingaraj");
		driver.findElement(By.xpath("//button[@id='next-button']")).click();
		Select gender = new Select(driver.findElement(By.id("gender-field")));
		gender.selectByValue("M");
		driver.findElement(By.xpath("//button[@id='next-button']")).click();
		driver.findElement(By.cssSelector("#birthdateDay-field")).sendKeys("28");
		Select birthMonth = new Select(driver.findElement(By.id("birthdateMonth-field")));
		birthMonth.selectByValue("6");
		driver.findElement(By.cssSelector("#birthdateYear-field")).sendKeys("1989");
		driver.findElement(By.xpath("//button[@id='next-button']")).click();
		driver.findElement(By.cssSelector("#address1")).sendKeys("13B");
		driver.findElement(By.cssSelector("#cityVillage")).sendKeys("CBE");
		driver.findElement(By.cssSelector("#stateProvince")).sendKeys("TN");
		driver.findElement(By.cssSelector("#country")).sendKeys("India");
		driver.findElement(By.cssSelector("#postalCode")).sendKeys("641029");
		driver.findElement(By.xpath("//button[@id='next-button']")).click();
		driver.findElement(By.cssSelector("input[name='phoneNumber']")).sendKeys("9655274123");
		driver.findElement(By.cssSelector("#confirmation_label")).click();
		String[] regName = driver.findElement(By.xpath("//span[text()='Name: ']/..")).getText().split(":");
		String name = regName[1].trim();
		System.out.println(name);
		String[] regGender = driver.findElement(By.xpath("//span[text()='Gender: ']/..")).getText().split(":");
		String sex = regGender[1].trim();
		System.out.println(sex);
		String[] regBirthdate = driver.findElement(By.xpath("//span[text()='Birthdate: ']/..")).getText().split(":");
		String birthdate = regBirthdate[1].trim();
		System.out.println(birthdate);
		String[] regAddress = driver.findElement(By.xpath("//span[text()='Address: ']/..")).getText().split(":");
		String address = regAddress[1].trim();
		System.out.println(address);
		String[] regPhone = driver.findElement(By.xpath("//span[text()='Phone Number: ']/..")).getText().split(":");
		String phone = regPhone[1].trim();
		System.out.println(phone);
		driver.findElement(By.id("submit")).click();
		
		//verify Patient details page is redirected and verify the age is calculated correctly based on the date of birth provided
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//em[text()='Patient ID']")));
		System.out.println(driver.findElement(By.xpath("//em[text()='Patient ID']")).getText());
		
		String[] yearsOld = driver.findElement(By.xpath("//span[contains(text(),'year(s)')]")).getText().split("year");
		int actualAge = Integer.parseInt(yearsOld[0].trim());
		System.out.println(actualAge);
		
		String dob = "28, June, 1989";
		SimpleDateFormat formatter = new SimpleDateFormat("dd, MMM, yyyy");
		Date date = formatter.parse(dob);
	      //Converting obtained Date object to LocalDate object
	      Instant instant = date.toInstant();
	      ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
	      LocalDate givenDate = zone.toLocalDate();
	      //Calculating the difference between given date to current date.
	      Period period = Period.between(givenDate, LocalDate.now());
	      int expectedAge = period.getYears();
	      System.out.println(expectedAge);

		//StartVisit and confirm 
	   driver.findElement(By.xpath("//a[@id='org.openmrs.module.coreapps.createVisit']")).click();
	   wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#start-visit-with-visittype-confirm")));
	   driver.findElement(By.cssSelector("#start-visit-with-visittype-confirm")).click();
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='attachments.attachments.visitActions.default']")));
	   driver.findElement(By.xpath("//a[@id='attachments.attachments.visitActions.default']")).click();
	   
	   StringSelection sel = new StringSelection("C:\\Users\\pli6cob\\Pictures\\Chrysanthemum.jpg");
	   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
	   driver.findElement(By.cssSelector("#visit-documents-dropzone")).click();
	   Thread.sleep(7000);
	   Robot r= new Robot();	
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		driver.findElement(By.xpath("//textarea[@placeholder='Enter a caption']")).click();
		driver.findElement(By.xpath("//textarea[@placeholder='Enter a caption']")).sendKeys("Attachment1");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@ng-click='uploadFile()']"))));
		driver.findElement(By.xpath("//button[@ng-click='uploadFile()']")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='toast-item-wrapper']"))));
		String toastMes = driver.findElement(By.xpath("//div[@class='toast-item-wrapper']")).getText();
		System.out.println(toastMes);
		
		//redirect to padient details and verify attachment and Verfiy Recent Visit has one entry for current date with Attachment Upload tag.
		driver.findElement(By.cssSelector(".PersonName-givenName")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//div[@class='att_thumbnail-image-section att_click-pointer'])[1]/following-sibling::div"))));
		String actualAttachmentCoption =  driver.findElement(By.xpath("(//div[@class='att_thumbnail-image-section att_click-pointer'])[1]/following-sibling::div")).getText();
		System.out.println(actualAttachmentCoption);
		DateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
		Date currentDate = new Date();
		String date1= dateFormat.format(currentDate);
		System.out.println(date1);
		List<WebElement> elelist = driver.findElements(By.xpath("//visitbyencountertype[@class='ng-isolate-scope']/ul/li"));
		  for(int i=0; i<elelist.size();i++) {
			String ele = elelist.get(i).getText();
			String[] dateStr = ele.split("\\n");
			String visitDate = dateStr[0].trim();
			System.out.println(visitDate);
			String tag = dateStr[1].trim();
			String expectedTag = "Attachment Upload";
			if(visitDate.equals(date1)) {
				if(tag.equals(expectedTag))
				System.out.println("passed AttachmentTag");
				break;
			}
			
		}
		
		  //End visit, Start visit, calculate and verify BMI using formula, Save and End visit
		  float height = 152;
		  float weight = 64;
		  float bmi = (100*100*weight)/(height*height);
		  DecimalFormat df = new DecimalFormat("#.0");
		  String actualBMIstr = df.format(bmi);
		  float actualBMI = Float.parseFloat(actualBMIstr);
		  System.out.println("actual bmi "+actualBMI);
		  driver.findElement(By.xpath("//ul[@class='float-left d-none d-lg-block']//a[@id='referenceapplication.realTime.endVisit'][1]")).click();
		  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='end-visit-dialog']/div"))));
		  driver.findElement(By.xpath("//div[@id='end-visit-dialog']/div//button[@class='confirm right']")).click();
		  
		  driver.findElement(By.xpath("//a[@id='org.openmrs.module.coreapps.createVisit']")).click();
		   wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#start-visit-with-visittype-confirm")));
		   driver.findElement(By.cssSelector("#start-visit-with-visittype-confirm")).click();
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='referenceapplication.realTime.vitals']")));
		   driver.findElement(By.xpath("//a[@id='referenceapplication.realTime.vitals']")).click();
		   driver.findElement(By.cssSelector("#w8")).sendKeys(Float.toString(height));
		   driver.findElement(By.xpath("//button[@id='next-button']")).click();
		   driver.findElement(By.cssSelector("#w10")).sendKeys(Float.toString(weight));
		   driver.findElement(By.xpath("//button[@id='next-button']")).click();
		   String calculatedBMIWraprer = driver.findElement(By.cssSelector("#calculated-bmi-wrapper")).getText();
		   String[] calculatedBMI = calculatedBMIWraprer.split(":");
		   String stringBMI = calculatedBMI[1];
		   float expectedBMI = Float.parseFloat(stringBMI);
		   System.out.println("exp bmi "+expectedBMI);
		   if(actualBMI == expectedBMI) {
			   System.out.println("passed BMI");
		   }
		   driver.findElement(By.xpath("//span[text()='Confirm']")).click();
		   driver.findElement(By.cssSelector("#save-form")).click();
		   driver.findElement(By.xpath("//button[@type='submit']")).click();
		   driver.findElement(By.cssSelector(".icon-off")).click();
		   wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='end-visit-dialog']/div"))));
		   driver.findElement(By.xpath("//div[@id='end-visit-dialog']/div//button[@class='confirm right']")).click();
		   wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#visit-details h4"))));
		   //In Patient details screen, verify the given Height and Weight is displayed correctly along with calculated BMI
		   driver.navigate().refresh();
		   wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='PersonName-givenName']")));
		   
		   driver.findElement(By.xpath("//ul[@id='breadcrumbs']/li[2]/a")).click();
		   String heightVirtualSec = driver.findElement(By.xpath("//span[@id='height']/span[1]")).getText();
		   float actualheightVirtualSec = Float.parseFloat(heightVirtualSec);
		   System.out.println(actualheightVirtualSec);
		   String weightVirtualSec = driver.findElement(By.xpath("//span[@id='weight']/span[1]")).getText();
		   float actualweightVirtualSec = Float.parseFloat(weightVirtualSec);
		   System.out.println(actualweightVirtualSec);
		   String bmiVirtualSec = driver.findElement(By.id("calculated-bmi")).getText();
		   float actualbmiVirtualSec = Float.parseFloat(bmiVirtualSec);
		   System.out.println(actualbmiVirtualSec);
		   
		   //Verfiy Recent Visit has one more new entry for current date with Vitals tag
		   List<WebElement> elelist2 = driver.findElements(By.xpath("//visitbyencountertype[@class='ng-isolate-scope']/ul/li"));
			  for(int i=0; i<elelist2.size();i++) {
				String ele = elelist2.get(i).getText();
				String[] dateStr = ele.split("\\n");
				String visitDate = dateStr[0].trim();
				System.out.println(visitDate);
				String tag = dateStr[1].trim();
				String expectedTag = "Vitals";
				if(visitDate.equals(date1)) {
					if(tag.equals(expectedTag))
					System.out.println("passed Vitals");
					break;
				}
				
			}
			  
		//Merge and Verfiy Recent Visit has become one entry for current date with Vitals,Attachment Upload tag.	

			  driver.findElement(By.id("org.openmrs.module.coreapps.mergeVisits")).click();
			  List<WebElement> mergItems = driver.findElements(By.xpath("//table[@id='active-visits']/tbody/tr/td[1]/input"));
			  for(WebElement merge:mergItems) {
				  merge.click();
			  }
			  driver.findElement(By.id("mergeVisitsBtn")).click();
			  driver.findElement(By.xpath("//input[@class='cancel']")).click();
			  List<WebElement> elelist3 = driver.findElements(By.xpath("//visitbyencountertype[@class='ng-isolate-scope']/ul/li"));
			  for(int i=0; i<elelist3.size();i++) {
				String ele = elelist3.get(i).getText();
				String[] dateStr = ele.split("\\n");
				String visitDate = dateStr[0].trim();
				System.out.println(visitDate);
				String tag = dateStr[1].trim();
				String expectedTag = "Vitals, Attachment Upload";
				if(visitDate.equals(date1)) {
					if(tag.equals(expectedTag))
					System.out.println("passed Vitals, Attachment Upload");
					break;
				}
				
			}
			  
			  driver.findElement(By.xpath("//a[@id='org.openmrs.module.coreapps.createRetrospectiveVisit']")).click();
				List<WebElement> calDays=driver.findElements(By.xpath("//table[@class=' table-condensed']/tbody/tr/td"));
				for(int j=0; j<calDays.size();j++) {
					
					String attribute = calDays.get(j).getAttribute("class");
					System.out.println(attribute);
					if(attribute.equalsIgnoreCase("day active")) {
						String currentDay = calDays.get(j).getText();
						
						
						System.out.println(currentDay);
						String featureDayAttribute = calDays.get(j+1).getAttribute("class");
						System.out.println(featureDayAttribute+ ". Therefore not clickable");
						break;
					}
				}
	driver.findElement(By.xpath("//div[@id='retrospective-visit-creation-dialog']//button[@class='cancel']")).click();
	//delete patien and verify toast
	driver.findElement(By.id("org.openmrs.module.coreapps.deletePatient")).click();
	driver.findElement(By.id("delete-reason")).sendKeys("OP Closed");
	driver.findElement(By.xpath("//div[@id='delete-patient-creation-dialog']//button[@class='confirm right']")).click();
	String deleteToastMes = driver.findElement(By.xpath("//div[@class='toast-item-wrapper']")).getText();
	System.out.println(deleteToastMes);
	//verify the deleted patient should not listed out in the table using search options
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("patient-search")));
	driver.findElement(By.id("patient-search")).sendKeys("Prakash");
	//driver.navigate().refresh();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='patient-search-results-table']/tbody/tr/td")));
	WebElement searchEle = driver.findElement(By.xpath("//table[@id='patient-search-results-table']/tbody/tr/td"));
	String classAttribute = searchEle.getAttribute("class");
	if(classAttribute.equals("dataTables_empty")) {
		System.out.println("Table Empty");
	}
	}
}

