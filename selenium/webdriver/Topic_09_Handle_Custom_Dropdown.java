package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Handle_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver,15);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemCustomDropdown("span#number-button","ul#number-menu>li>div","10" );
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='ui-selectmenu-text' and text()='10']")).getText(), "10");
		selectItemCustomDropdown("span#number-button","ul#number-menu>li>div","19" );
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='ui-selectmenu-text' and text()='19']")).getText(), "19");
		

	}

	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemCustomDropdown("i.dropdown","div.item>span","Stevie Feliciano" );
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Stevie Feliciano");
		selectItemCustomDropdown("i.dropdown","div.item>span","Christian" );
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Christian");
		
		
		
	}
	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemCustomDropdown("li.dropdown-toggle","ul.dropdown-menu>li>a","Second Option" );
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
		
	}
	@Test
	public void TC_04_Nopcommerce() {
		driver.get("https://demo.nopcommerce.com/register");
		selectItemCustomDropdown("select[name = 'DateOfBirthDay']","select[name = 'DateOfBirthDay']>option","5" );
		Assert.assertTrue(driver.findElement(By.xpath("//select[@name = 'DateOfBirthDay']//option[text()='10']")).isSelected());
		
	}
	@Test
	public void TC_05_EditableDropdown() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		enterItemCustomDropdown("input.search","div.item","Aland Islands" );
		Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Aland Islands']")).getText(),"Aland Islands");
	}
	public void selectItemCustomDropdown(String parentLocator, String childLocator, String expectedTextItem) {
		driver.findElement(By.cssSelector(parentLocator)).click();
		sleepInSecond(3);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		List<WebElement> allDropdownItems = driver.findElements(By.cssSelector(childLocator));
		for (WebElement item : allDropdownItems) {
			String actualTextItem = item.getText();
			if (actualTextItem.equals(expectedTextItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true)", item);
				sleepInSecond(1);
				item.click();
				break;
			}
			
		}
	}
	public void enterItemCustomDropdown(String editableLocator, String childLocator, String expectedTextItem) {
		driver.findElement(By.cssSelector(editableLocator)).sendKeys(expectedTextItem);
		sleepInSecond(3);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		List<WebElement> allDropdownItems = driver.findElements(By.cssSelector(childLocator));
		for (WebElement item : allDropdownItems) {
			String actualTextItem = item.getText();
			if (actualTextItem.equals(expectedTextItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true)", item);
				sleepInSecond(1);
				item.click();
				break;
			}
			
		}
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
