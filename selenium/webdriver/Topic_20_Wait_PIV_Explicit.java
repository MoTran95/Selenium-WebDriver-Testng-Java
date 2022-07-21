package webdriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_PIV_Explicit {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By loadingIcon = By.cssSelector("div#loading");
	By helloText = By.xpath("//h4[text()='Hello World!']");
	String uploadFileFolderPath = projectPath + File.separator + "uploadFiles" + File.separator;
	String img1 =  "01.jpg";
	String img2 =  "02.jpg";
	String img3 =  "03.jpg";
	String img1FilePath = uploadFileFolderPath + img1;
	String img2FilePath = uploadFileFolderPath + img2;
	String img3FilePath = uploadFileFolderPath + img3;


	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver");
		}
		
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();
		
	}

	@Test
	public void TC_01_Invisible() {
		explicitWait = new WebDriverWait(driver, 15);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		Assert.assertEquals(driver.findElement(By.xpath("//h4[text()='Hello World!']")).getText(), "Hello World!");
	}

	@Test
	public void TC_02_Visible() {
		explicitWait = new WebDriverWait(driver, 15);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(helloText));
		Assert.assertEquals(driver.findElement(By.xpath("//h4[text()='Hello World!']")).getText(), "Hello World!");
		
	}
	@Test
	public void TC_03_Number() {
		explicitWait = new WebDriverWait(driver, 15);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		explicitWait.until(ExpectedConditions.numberOfElementsToBe(helloText, 1));
		Assert.assertEquals(driver.findElement(By.xpath("//h4[text()='Hello World!']")).getText(), "Hello World!");
		
	}
	@Test
	public void TC_04_AjaxLoading() {
		explicitWait = new WebDriverWait(driver, 30);
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceholder1_Panel1")));
		WebElement selectedDateText = driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"));
		Assert.assertEquals(selectedDateText.getText(), "No Selected Dates to display.");
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='18']"))).click();
//		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar']>div.raDiv")));
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#ctl00_ContentPlaceholder1_RadAjaxLoadingPanel1ctl00_ContentPlaceholder1_RadCalendar1>div.raDiv")));
		selectedDateText = driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"));
		Assert.assertEquals(selectedDateText.getText(), "Monday, July 18, 2022");
		WebElement todaySelected = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='18']")));
		Assert.assertTrue(todaySelected.isDisplayed());
		
	}
	@Test
	public void TC_05_Upload_Files() {
		explicitWait = new WebDriverWait(driver, 90);
		driver.get("https://gofile.io/uploadFiles");
		By uploadFileBy = By.xpath("//input[@type='file']");
		driver.findElement(uploadFileBy).sendKeys(img1FilePath + "\n" + img2FilePath + "\n" + img3FilePath);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress-bar>span"))));
		WebElement uploadText = explicitWait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//h5[text()='Your files have been successfully uploaded']"))));
		Assert.assertTrue(uploadText.isDisplayed());
		
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
