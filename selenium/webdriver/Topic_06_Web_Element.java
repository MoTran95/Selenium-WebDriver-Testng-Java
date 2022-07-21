package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		this.jsExecutor = ((JavascriptExecutor) driver);
	}

	@Test
	public void TC_01_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement emailTextbox = driver.findElement(By.id("mail"));
		
		if (emailTextbox.isDisplayed()) {
			emailTextbox.sendKeys("Automation Testing");
			System.out.println("Email textbox is display");
		} else {
			System.out.println("Email textbox is NOT display");
		}
		WebElement ageUnder18Radio = driver.findElement(By.id("under_18"));

		if (ageUnder18Radio.isDisplayed()) {
			ageUnder18Radio.click();
			System.out.println("Age Under 18 raido is display");
		} else {
			System.out.println("Age Under 18 radio is NOT display");
		}
		WebElement educationTextarea = driver.findElement(By.id("edu"));

		if (educationTextarea.isDisplayed()) {
			educationTextarea.sendKeys("Automation Testing");
			System.out.println("Education textarea is display");
		} else {
			System.out.println("Education textarea is NOT display");
		}
		
	}

	@Test
	public void TC_02_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement emailTextbox = driver.findElement(By.id("mail"));
		
		if (emailTextbox.isEnabled()) {
			System.out.println("Email textbox is enabled");
		} else {
			System.out.println("Email textbox is disabled");
		}
		
	}
	@Test
	public void TC_03_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement ageUnder18Radio = driver.findElement(By.id("under_18"));
		
		ageUnder18Radio.click();
		Assert.assertTrue(ageUnder18Radio.isSelected());
		ageUnder18Radio.click();
		Assert.assertTrue(ageUnder18Radio.isSelected());
		
		
	}
	@Test
	public void TC_04_Mailchimp() {
		driver.get("https://login.mailchimp.com/signup/");
		driver.findElement(By.id("email")).sendKeys("automation56788967@gmail.com");
		
//		driver.findElement(By.name("username")).sendKeys("dreamtest12344");
		
		WebElement passwordTextbox = driver.findElement(By.id("new_password"));
		passwordTextbox.sendKeys("auto");
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		passwordTextbox.clear();
		passwordTextbox.sendKeys("AUTO");
		scrollToElement("//li[@class='uppercase-char completed']");		
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
		passwordTextbox.clear();
		
		passwordTextbox.sendKeys("12");
		sleepInSecond(1);
		scrollToElement("//li[@class='number-char completed']");
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		
		passwordTextbox.clear();
		passwordTextbox.sendKeys("#!");
		sleepInSecond(1);
		scrollToElement("//li[@class='special-char completed']");
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
		
		passwordTextbox.clear();
		passwordTextbox.sendKeys("12345678");
		
		sleepInSecond(1);
		scrollToElement("//li[@class='number-char completed']");
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
		
		passwordTextbox.clear();
		passwordTextbox.sendKeys("Auto134#");
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.xpath("//h4[text()=\"Your password is secure and you're all set!\"]")).isDisplayed());
		
	}

	public void scrollToElement(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}
	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
