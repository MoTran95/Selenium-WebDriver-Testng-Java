package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Frame_Iframe {
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
	public void TC_01_Iframe_Kyna() {
		driver.get("https://kyna.vn/");
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));
		String facebookLikeNumber = driver.findElement(By.xpath("//div[text()='166K lượt thích']")).getText();
		Assert.assertEquals(facebookLikeNumber,"166K lượt thích");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("cs_chat_iframe");
		driver.findElement(By.cssSelector("div.meshim_widget_widgets_BorderOverlay")).click();
		sleepInSecond(3);
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("John");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987654567");
		new Select(driver.findElement(By.cssSelector("#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("testing");
		sleepInSecond(5);
		driver.switchTo().defaultContent();
		String keyword = "Excel";
		driver.findElement(By.id("live-search-bar")).sendKeys(keyword);
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(5);
		List<WebElement> courseNames = driver.findElements(By.cssSelector("div.content h4"));
		Assert.assertEquals(courseNames.size(), 9);
		for (WebElement course : courseNames) {
			Assert.assertTrue(course.getText().contains(keyword));
		}
	}

	@Test
	public void TC_02_HDFC() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame("login_page");
		driver.findElement(By.name("fldLoginUserId")).sendKeys("automationFC");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
		driver.findElement(By.id("fldPasswordDispId")).sendKeys("automafc");
		sleepInSecond(3);
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
