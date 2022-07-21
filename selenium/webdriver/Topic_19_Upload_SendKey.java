package webdriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_19_Upload_SendKey {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	String vietnam = "VN.jpg";
	String thailan = "TL.jpg";
	String nhatban = "JP.jpg";
	String uploadFileFolderPath = projectPath + File.separator + "uploadFiles" + File.separator;
	String vietnamFilePath = uploadFileFolderPath + vietnam;
	String thailanFilePath = uploadFileFolderPath + thailan;
	String nhatbanFilePath = uploadFileFolderPath + nhatban;

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

	
	public void TC_01_Upload_One_File_Per_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(vietnamFilePath);
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(thailanFilePath);
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(nhatbanFilePath);
	
		driver.findElement(By.xpath("//p[@class='name' and text()= '" + vietnam + "']")).isDisplayed();
		driver.findElement(By.xpath("//p[@class='name' and text()= '" + thailan + "']")).isDisplayed();
	    driver.findElement(By.xpath("//p[@class='name' and text()= '" + nhatban + "']")).isDisplayed();
		List<WebElement> startButtons = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(2);
		}

		driver.findElement(By.xpath("//p[@class='name']/a[ text() ='" + vietnam + "']")).isDisplayed();
		driver.findElement(By.xpath("//p[@class='name']/a[ text() ='" + thailan + "']")).isDisplayed();
		driver.findElement(By.xpath("//p[@class='name']/a[ text() ='" + nhatban + "']")).isDisplayed();
		

	}

	@Test
	public void TC_02_Multiple_File_Per_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(vietnamFilePath + "\n" + thailanFilePath + "\n" + nhatbanFilePath);

	
		driver.findElement(By.xpath("//p[@class='name' and text()= '" + vietnam + "']")).isDisplayed();
		driver.findElement(By.xpath("//p[@class='name' and text()= '" + thailan + "']")).isDisplayed();
	    driver.findElement(By.xpath("//p[@class='name' and text()= '" + nhatban + "']")).isDisplayed();
		List<WebElement> startButtons = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(2);
		}
		
		driver.findElement(By.xpath("//p[@class='name']/a[text() ='" + vietnam + "']")).isDisplayed();
		driver.findElement(By.xpath("//p[@class='name']/a[text() ='" + thailan + "']")).isDisplayed();
		driver.findElement(By.xpath("//p[@class='name']/a[text() ='" + nhatban + "']")).isDisplayed();
		
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
