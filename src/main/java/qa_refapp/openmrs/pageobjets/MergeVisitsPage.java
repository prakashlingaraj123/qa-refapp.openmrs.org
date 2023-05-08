package qa_refapp.openmrs.pageobjets;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qa_refapp.openmrs.abstractcomponents.AbstractComponent;

public class MergeVisitsPage extends AbstractComponent {
	WebDriver driver;
	public MergeVisitsPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//table[@id='active-visits']/tbody/tr/td[1]/input")
	List<WebElement> mergeItems;
	
	@FindBy(id = "mergeVisitsBtn")
	WebElement mergeVisitButton;
	
	@FindBy(xpath = "//input[@class='cancel']")
	WebElement returnButton;
	
	
	public void mergVisits() {
		 for(WebElement merge:mergeItems) {
			  merge.click();
		  }
		 mergeVisitButton.click();
		 returnButton.click();
	}
	
}
