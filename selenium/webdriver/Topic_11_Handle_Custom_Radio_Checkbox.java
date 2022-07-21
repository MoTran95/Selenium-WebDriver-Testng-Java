package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Handle_Custom_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		
	}

	@Test
	public void TC_01_Custom_Checkbox() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		By checkedCheckbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();",driver.findElement(checkedCheckbox));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());
		
	}

	@Test
	public void TC_02_Custom_Radio() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		By checkedRadio = By.xpath("//span[text()='After']/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();",driver.findElement(checkedRadio));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(checkedRadio).isSelected());
		
		
	}
	@Test
	public void TC_03_Google_Docs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		By hanoiRadio = By.xpath("//div[@aria-label='Hà Nội']");
		driver.findElement(hanoiRadio).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(hanoiRadio).getAttribute("aria-checked"),"true");
		By quangngaiCheckbox = By.id("i34");
		jsExecutor.executeScript("arguments[0].click();",driver.findElement(quangngaiCheckbox));
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(hanoiRadio).getAttribute("aria-checked"),"true");
		
		
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
