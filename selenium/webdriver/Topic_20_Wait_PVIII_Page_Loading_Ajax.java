package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_PVIII_Page_Loading_Ajax {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	Actions action;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver");
		}
		
		driver = new FirefoxDriver();
		action = new Actions(driver);
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Explicit() {
		driver.get("https://api.orangehrm.com/");
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.spinner")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div#project  h1")).getText(), "OrangeHRM REST API Documentation");
		
	}

	@Test
	public void TC_02_Page_Ready() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		Assert.assertTrue(isJQueryLoadedSucess());
		Assert.assertTrue(driver.findElement(By.cssSelector("div#div_graph_display_emp_distribution")).isDisplayed());

	}
	@Test
	public void TC_03_Page_Ready() {
		driver.get("https://blog.testproject.io/");
		if (driver.findElement(By.cssSelector("div.mailch-wrap")).isDisplayed()) {
			driver.findElement(By.cssSelector("div#close-mailch")).click();
		}
		action.moveToElement(driver.findElement(By.cssSelector("section#search-2 input.search-field"))).perform();
		Assert.assertTrue(isJQueryLoadedSucess());
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("Selenium");
		driver.findElement(By.cssSelector("section#search-2 span.glass")).click();
		
		Assert.assertTrue(isJQueryLoadedSucess());
		
		List<WebElement> firstAllPostTitle = driver.findElements(By.cssSelector("h3.post-title>a"));
		for (WebElement postTitle : firstAllPostTitle) {
			Assert.assertTrue(postTitle.getText().contains("Selenium"));
		}

	}
	public boolean isJQueryLoadedSucess() {
		explicitWait = new WebDriverWait(driver, 30);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
			
		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
			
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
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
//		driver.quit();
	}

}
