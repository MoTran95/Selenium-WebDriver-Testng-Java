package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Login_Xpath {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstName, lastName,fullName, emailAddress, password;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		firstName = "Mo";
		lastName = "Tran";
		fullName = firstName +" "+ lastName;
		emailAddress = "TVN"+ generateRandomNumber() + "@hotmail.vn";
		password = "12345678";
	}

	@Test
	public void Login_01_Empty_Data() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.id("send2")).click();
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");

	}

	@Test
	public void Login_02_Invalid_Email() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("test@111");
		driver.findElement(By.id("pass")).sendKeys("12345567");
		driver.findElement(By.id("send2")).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");

	}
	@Test
	public void Login_03_Invalid_Password() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("test@gmailcom");
		driver.findElement(By.id("pass")).sendKeys("167");
		driver.findElement(By.id("send2")).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");

	}
	@Test
	public void Login_04_Incorrect_Email_Password() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("167123");
		driver.findElement(By.id("send2")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='account-login']//span[text()='Invalid login or password.']")).getText(), "Invalid login or password.");

		
	}
	@Test
	public void Login_05_Create_New_Account() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@class='button'][@title='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		String contactInformationText = driver.findElement(By.xpath("//h3[text()='Contact Information']//parent::div//following-sibling::div/p")).getText();
		Assert.assertTrue(contactInformationText.contains(fullName));
		Assert.assertTrue(contactInformationText.contains(emailAddress));
		driver.findElement(By.xpath("//span[@class= 'label'][text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
	}

	@Test
	public void Login_06_Login_Valid_Email_Password() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys(emailAddress);
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.id("send2")).click();
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

}
