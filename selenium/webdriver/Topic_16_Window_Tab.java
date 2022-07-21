package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Window_Tab {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver");
		}
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Basic_Form() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		String parenetId = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(5);
		switchToWindowTitle("Google");
		driver.findElement(By.cssSelector("input[title='Tìm kiếm']")).sendKeys("selenium");
		sleepInSecond(5);
		switchToWindowTitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(5);
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(2);
		switchToWindowTitle("Facebook - Đăng nhập hoặc đăng ký");
		driver.findElement(By.id("email")).sendKeys("john@hotmail.com");
		driver.findElement(By.id("pass")).sendKeys("pass");
		switchToWindowTitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(5);
		closeAllWindowWithoutByTitle(parenetId);
		sleepInSecond(2);
	}

	@Test
	public void TC_02_TechPanda() {
		driver.get("http://live.techpanda.org/index.php/mobile.html");
		driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']")).getText(), "The product Samsung Galaxy has been added to comparison list.");
		driver.findElement(By.xpath("//a[@title='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='The product Sony Xperia has been added to comparison list.']")).getText(), "The product Sony Xperia has been added to comparison list.");
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		sleepInSecond(2);
		switchToWindowTitle("Products Comparison List - Magento Commerce");
		Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='Compare Products']")).getText(), "COMPARE PRODUCTS");
		driver.findElement(By.cssSelector("button[title='Close Window']")).click();
		switchToWindowTitle("Mobile");
		sleepInSecond(2);
		driver.findElement(By.id("search")).sendKeys("mobile");
		
	}
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// No chi dung cho 2 tab/window
	public void switchToWindowByID(String parentID) {
		// Lay ra tat ca cac ID cua tab/window dang co
		Set <String> allWindowIDs = driver.getWindowHandles();
		// Dung vong lap de duyet qua tung ID
		for (String id : allWindowIDs) {
			//Neu co ID khac voi parent ID
			if(!id.equals(parentID)) {
				// Switch vao
				driver.switchTo().window(id);
				sleepInSecond(2);
			}
		}
	}
	public void switchToWindowTitle(String expectedPageTitle) {
		Set <String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			driver.switchTo().window(id);
			String currentPageTitle = driver.getTitle();
			if(currentPageTitle.equals(expectedPageTitle)) {
				break;
			}
		}
	}
	public void closeAllWindowWithoutByTitle(String parentID) {
		Set <String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			//Neu co ID khac voi parent ID
			if(!id.equals(parentID)) {
				// Switch vao
				driver.switchTo().window(id);
				sleepInSecond(2);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
