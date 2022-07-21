package webdriver;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_PVI_Fluent {
	WebDriver driver;
	WebDriverWait explicitWait;
	FluentWait<WebDriver> fluentDriver;
	FluentWait<WebElement> fluentElement;
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
		driver.get("");
	}

	@Test
	public void TC_01() {
		fluentDriver =  new FluentWait<WebDriver>(driver);
		fluentDriver.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofSeconds(1))
		.ignoring(NoSuchElementException.class)
		.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(""));
			}
		});
		WebElement loginButton = driver.findElement(By.xpath(""));
		fluentElement = new FluentWait<WebElement>(loginButton);
		fluentElement.withTimeout(Duration.ofSeconds(60))
		.pollingEvery(Duration.ofMillis(200))
		.ignoring(ElementNotVisibleException.class);
		String loginButtonText = fluentElement.until(new Function<WebElement,String>(){

			public String apply(WebElement element) {
				// TODO Auto-generated method stub
				return element.getText();
			}
			
		});
		Assert.assertEquals(loginButtonText, "");
		
		boolean loginButtonStatus = fluentElement.until(new Function<WebElement,Boolean>(){

			public Boolean apply(WebElement element) {
				// TODO Auto-generated method stub
				return element.isDisplayed();
			}
			
		});
		Assert.assertTrue(loginButtonStatus);
	}

	@Test
	public void TC_02() {
		
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
