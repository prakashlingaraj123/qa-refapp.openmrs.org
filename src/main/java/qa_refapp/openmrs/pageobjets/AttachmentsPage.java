package qa_refapp.openmrs.pageobjets;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class AttachmentsPage extends AbstractComponent {
	WebDriver driver;

	public AttachmentsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "#visit-documents-dropzone")
	WebElement attachmentdropZone;

	@FindBy(xpath = "//textarea[@placeholder='Enter a caption']")
	WebElement captionArea;

	@FindBy(xpath = "//button[@ng-click='uploadFile()']")
	WebElement uploadButton;

	@FindBy(xpath = "//div[@class='toast-item-wrapper']")
	WebElement toaster;

	By upload = By.xpath("//button[@ng-click='uploadFile()']");

	public String addAttachment(String filepath, String caption) throws InterruptedException, AWTException {
		StringSelection sel = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
		attachmentdropZone.click();
		Thread.sleep(7000);
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		captionArea.click();
		captionArea.sendKeys(caption);
		waitforElementTobeClickable(upload);
		uploadButton.click();
		return toasterMessage();
	}
	
}
