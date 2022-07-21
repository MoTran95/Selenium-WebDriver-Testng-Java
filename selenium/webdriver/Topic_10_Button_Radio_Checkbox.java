package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Button_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		By loginButtonBy = By.cssSelector("button.fhs-btn-login");
		Assert.assertFalse(driver.findElement(loginButtonBy).isEnabled());
		driver.findElement(By.id("login_username")).sendKeys("John@hotmail.net");
		driver.findElement(By.id("login_password")).sendKeys("123456");
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(loginButtonBy).isEnabled());
		String loginButtonBackgroundColorRgba = driver.findElement(loginButtonBy).getCssValue("background-color");
		String loginButtonBackgroundHexa = Color.fromString(loginButtonBackgroundColorRgba).asHex().toUpperCase();
		Assert.assertEquals(loginButtonBackgroundHexa,"#C92127");
	}

	@Test
	public void TC_02_Default_Checkbox_Radio() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		driver.findElement(By.xpath("//input[@value='Anemia']")).click();
		driver.findElement(By.xpath("//input[@value='Asthma']")).click();
		driver.findElement(By.xpath("//input[@value='Gout']")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//input[@value='2+ packs/day']")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Anemia']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Asthma']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Gout']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='2+ packs/day']")).isSelected());
		
		driver.findElement(By.xpath("//input[@value='Anemia']")).click();
		driver.findElement(By.xpath("//input[@value='Asthma']")).click();
		driver.findElement(By.xpath("//input[@value='Gout']")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//input[@value='2+ packs/day']")).click();
		sleepInSecond(3);
		
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Anemia']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Asthma']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Gout']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='2+ packs/day']")).isSelected());


	}
	@Test
	public void TC_03_Select_All_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		List<WebElement> allCheckboxes =  driver.findElements(By.xpath("//input[@type='checkbox']"));
		for (WebElement checkbox : allCheckboxes) {
			if(!checkbox.isSelected()) {
				checkbox.click();
				sleepInSecond(1);
			}
			
		}
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertTrue(checkbox.isSelected());
		}
		for (WebElement checkbox : allCheckboxes) {
			if(checkbox.isSelected()) {
				checkbox.click();
				sleepInSecond(1);
			}
			
		}
		for (WebElement checkbox : allCheckboxes) {
			Assert.assertFalse(checkbox.isSelected());
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
