package webdriver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_User_Interaction {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	Actions action;
	String jsFileContent;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_Hover() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		WebElement ageTextbox = driver.findElement(By.id("age"));
		action.moveToElement(ageTextbox).perform();
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(),
				"We ask for your age only for statistical purposes.");
	}

	@Test
	public void TC_02_Hover() {
		driver.get("https://www.myntra.com/");
		WebElement kidLink = driver.findElement(By.xpath("//a[@class='desktop-main' and text() ='Kids']"));
		action.moveToElement(kidLink).perform();
		sleepInSecond(3);
		action.click(driver.findElement(By.xpath("//a[@class='desktop-categoryName' and text() ='Home & Bath']")))
				.perform();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Kids Home Bath']")).isDisplayed());

	}

	@Test
	public void TC_03_Hover() {
		driver.get("https://fptshop.com.vn/");
		WebElement dienthoaiLink = driver.findElement(By.xpath("//a[@title='ĐIỆN THOẠI']"));
		action.moveToElement(dienthoaiLink).perform();
		sleepInSecond(3);
		driver.findElement(By.xpath("//a[@title='Apple (iPhone)']")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "https://fptshop.com.vn/dien-thoai/apple-iphone");
	}

	@Test
	public void TC_04_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		action.clickAndHold(allNumbers.get(0)).moveToElement(allNumbers.get(10)).release().perform();
		allNumbers = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumbers.size(), 9);
	}
	@Test
	public void TC_05_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		Assert.assertEquals(allNumbers.size(), 12);
		action.keyDown(Keys.COMMAND).perform();
		action.click(allNumbers.get(0)).click(allNumbers.get(2)).click(allNumbers.get(5)).perform();
		action.keyUp(Keys.COMMAND).perform();
		allNumbers = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumbers.size(), 3);
	}
	@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement doubleClickMeText = driver.findElement(By.xpath("//button[text()='Double click me']"));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", doubleClickMeText);
		sleepInSecond(3);
		action.doubleClick(doubleClickMeText).perform();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Automation Guys!");
	}
	@Test
	public void TC_07_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		sleepInSecond(3);
		WebElement deleteBefore = driver.findElement(By.cssSelector("li.context-menu-icon-delete"));
		action.moveToElement(deleteBefore).perform();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-delete.context-menu-hover.context-menu-visible")).isDisplayed());
		action.click(deleteBefore).perform();
		sleepInSecond(3);
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "clicked: delete");
		alert.accept();
		sleepInSecond(3);
		Assert.assertFalse(deleteBefore.isDisplayed());
	}
	@Test
	public void TC_08_Drag_Drop() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		WebElement sourceCircle = driver.findElement(By.id("draggable"));
		WebElement targerCircle = driver.findElement(By.id("droptarget"));
		action.dragAndDrop(sourceCircle, targerCircle).perform();
		Assert.assertEquals((targerCircle.getText()), "You did great!");
	}
	@Test
	public void TC_09_Drag_Drop_Html5_JavaScript() throws IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		jsFileContent = getContentFile(projectPath + "/dragAndDrop/drag_and_drop_helper.js");
		jsFileContent = jsFileContent +  "$('div#column-a').simulateDragDrop({ dropTarget: 'div#column-b'});";
		System.out.println(jsFileContent);
		jsExecutor.executeScript(jsFileContent);
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");
	
	}
	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
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
