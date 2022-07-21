package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Fixed_Popup {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@Test
	public void TC_01_NgoaiNgu24h_Fixed_In_Dom() {
		driver.get("https://ngoaingu24h.vn/");
		WebElement loginPopup = driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]"));
		Assert.assertFalse(loginPopup.isDisplayed());
		driver.findElement(By.cssSelector("button.login_")).click();
		sleepInSecond(3);
		Assert.assertTrue(loginPopup.isDisplayed());
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][ class *='fade in'] input#account-input")).sendKeys("automationFC");
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][ class *='fade in'] input#password-input")).sendKeys("12345678");
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][ class *='fade in'] button.btn-login-v1")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("div[id='modal-login-v1'][ class *='fade in'] div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
		driver.findElement( By.cssSelector("div[id='modal-login-v1'][ class *='fade in'] button.close")).click();
		sleepInSecond(3);
		Assert.assertFalse(loginPopup.isDisplayed());
	}

	@Test
	public void TC_02_Kyna_Fixed_In_Dom() {
		driver.get("https://kyna.vn/");
		WebElement loginPopup = driver.findElement(By.cssSelector("div#k-popup-account-login"));
		Assert.assertFalse(loginPopup.isDisplayed());
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(3);
		Assert.assertTrue(loginPopup.isDisplayed());
	}
	@Test
	public void TC_03_Fixed_Not_In_Dom_Tiki() {
		driver.get("https://tiki.vn/");
		List<WebElement> loginPopup = driver.findElements(By.cssSelector("div.ReactModal__Content"));
		Assert.assertEquals(loginPopup.size(), 0);
		driver.findElement(By.xpath("//span[text()='Đăng Nhập / Đăng Ký']")).click();
		sleepInSecond(3);
		loginPopup = driver.findElements(By.cssSelector("div.ReactModal__Content"));
		Assert.assertEquals(loginPopup.size(), 1);
		Assert.assertTrue(loginPopup.get(0).isDisplayed());
		driver.findElement(By.cssSelector("img.close-img")).click();
		sleepInSecond(3);
		loginPopup = driver.findElements(By.cssSelector("div.ReactModal__Content"));
		Assert.assertEquals(loginPopup.size(), 0);
	}
	@Test
	public void TC_04_Random_In_Dom_KMPlayer() {
		driver.get("https://www.kmplayer.com/home");
		WebElement popupLayer = driver.findElement(By.cssSelector("div.pop-layer"));
		if (popupLayer.isDisplayed()) {
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//area[@alt='close']")));
			sleepInSecond(3);
		}
		
	}
	@Test
	public void TC_05_Random_Not_In_Dom_KMPlayer() {
		driver.get("https://dehieu.vn/");
		List <WebElement> contentPopup = driver.findElements(By.cssSelector("div.popup-content"));
		if(contentPopup.size()>0 && contentPopup.get(0).isDisplayed()) {
			driver.findElement(By.id("popup-name")).sendKeys("Adam");
			driver.findElement(By.id("popup-email")).sendKeys("Adam333@hotmail.com");
			driver.findElement(By.id("popup-phone")).sendKeys("0934567890");
			driver.findElement(By.id("close-popup")).click();
			sleepInSecond(3);
		}
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		
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
