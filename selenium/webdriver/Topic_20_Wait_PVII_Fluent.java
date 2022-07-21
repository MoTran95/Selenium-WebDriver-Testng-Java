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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_PVII_Fluent {
	WebDriver driver;
	WebDriverWait explicitWait;
	FluentWait<WebDriver> fluentDriver;
	
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	long sunTime =30;
	long pollTime = 1;

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
	public void TC_01() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		fluentDriver = new FluentWait<WebDriver>(driver);
		clickToElement(By.cssSelector("div#start>button"));
		isElementDisplayed(By.cssSelector("div#finish>h4"));
	}

	@Test
	public void TC_02() {
		
	}
	public WebElement findElement(By locator) {
		FluentWait<WebDriver> wait  =  new FluentWait<WebDriver>(driver);
		fluentDriver.withTimeout(Duration.ofSeconds(sunTime))
		.pollingEvery(Duration.ofSeconds(pollTime))
		.ignoring(NoSuchElementException.class);
		
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return element;
	}
	public void clickToElement (By locator) {
		WebElement element = findElement(locator);
		 element.click();
	}
	public boolean isElementDisplayed(By locator) {
		WebElement element = findElement(locator);
		FluentWait<WebElement> wait  =  new FluentWait<WebElement>(element)
		.withTimeout(Duration.ofSeconds(sunTime))
		.pollingEvery(Duration.ofSeconds(pollTime))
		.ignoring(NoSuchElementException.class);
		
		boolean isDisplayed = wait.until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement element) {
				boolean flag = element.isDisplayed();
				return flag;
			}
		});
		return isDisplayed;
		
	}
	public WebElement waitElementVisible(By locator) {
		FluentWait<WebDriver> wait  =  new FluentWait<WebDriver>(driver);
		fluentDriver.withTimeout(Duration.ofSeconds(sunTime))
		.pollingEvery(Duration.ofSeconds(pollTime))
		.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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
