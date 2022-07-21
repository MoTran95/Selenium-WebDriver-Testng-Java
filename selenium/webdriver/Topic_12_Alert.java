package webdriver;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Alert {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Alert alert;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(3);
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked an alert successfully");
	}

	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(3);
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");

	}

	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(3);
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		String text = "auto";
		alert.sendKeys(text);
		sleepInSecond(3);
		alert.accept();
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: " + text);

	}
	@Test
	public void TC_04_Authentication_Alert_Trick() {
		String username = "admin";
		String password = "admin";
		String url = "https://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth";
		driver.get(url);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='content']//p")).getText().contains("Congratulations! You must have the proper credentials."));

	}
	@Test
	public void TC_05_Authentication_Alert_Trick() {
		String username = "admin";
		String password = "admin";
		driver.get("https://the-internet.herokuapp.com/");
		String basicAuthenLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		String [] authenLinkArray = basicAuthenLink.split("//");
		String newAuthenLinkUrl = authenLinkArray[0] + "//" + username + ":" +password + "@" + authenLinkArray[1];
		driver.get(newAuthenLinkUrl);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='content']//p")).getText().contains("Congratulations! You must have the proper credentials."));

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
