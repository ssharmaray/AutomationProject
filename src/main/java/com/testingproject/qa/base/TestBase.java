package com.testingproject.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.testingproject.qa.util.ExcelReader_Mine;
import com.testingproject.qa.util.ExtentManager;
import com.testingproject.qa.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties properties;
	public static Properties objectRepository = new Properties();
	public static FileInputStream fileInputStream;
	public static FileInputStream orFileStream;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader_Mine excel = new ExcelReader_Mine(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	public TestBase() throws FileNotFoundException {
		
		properties = new Properties();
		
		try {
			//FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir", "C:\\Users\\sshar\\eclipse-workspace\\CalcProject\\TestingProject\\src\\main\\java\\com\\testingproject\\qa\\config\\config.properties"));
			fileInputStream = new FileInputStream("C:\\Users\\sshar\\eclipse-workspace\\CalcProject\\Project1\\src\\main\\java\\com\\testingproject\\qa\\config\\config.properties");
			properties.load(fileInputStream);
			log.debug("--Config file loaded!--");
			initialization();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@BeforeSuite
	public static void initialization() {
		String browserName = properties.getProperty("browser");
		
		if (driver==null)
		{
			
			//Jenkins Browser filter configuration
			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browserName = System.getenv("browser");
			} else {

				browserName = properties.getProperty("browser");

			}

			properties.setProperty("browser", browserName);

			if(browserName.equals("chrome")) 
			{
				WebDriverManager.chromedriver().setup();
				//driver = new ChromeDriver();
				//System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\executables\\chromedriver.exe");

				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");

				driver = new ChromeDriver(options);

				log.debug("--Chrome launched!--");
			}
			else if(browserName.equals("firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("--Firefox launched!--");
			}
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
			
			driver.get(properties.getProperty("url"));
			log.debug("--Navigated to "+ properties.getProperty("url"));
		}
		
	}
	
	public void click(String locator, String field)
	{		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(50L)) .pollingEvery(Duration.ofSeconds(5L))
				  .ignoring(NoSuchElementException.class);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.ByXPath.xpath(locator))).click();
		test.log(LogStatus.INFO, "Clicking on : "+field);
	}

	public void type(String locator, String value, String field)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(50L)) .pollingEvery(Duration.ofSeconds(5L))
				  .ignoring(NoSuchElementException.class);
		
		WebElement element = driver.findElement(By.ByXPath.xpath(locator));
		element.sendKeys(value);		
		test.log(LogStatus.INFO, "Typing in : "+field + " entered value as " + value);
	}
	
	static WebElement dropdown;
	public void select(String locator, String value, String field)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(50L)) .pollingEvery(Duration.ofSeconds(5L))
				  .ignoring(NoSuchElementException.class);
		
		dropdown = driver.findElement(By.xpath(locator));
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		test.log(LogStatus.INFO, "Dropdown in : "+field + " selected value as " + value);
	}
	public static void verifyEquals(String expected, String actual) throws IOException
	{
		try
		{
			Assert.assertEquals(expected, actual);
			
		}catch(Throwable t)
		{			
			TestUtil.captureScreenShot();
			String schreenshotImagePath = TestUtil.screenshotName;
			String imageThumbNail = "<img src="+TestUtil.screenshotName+" height=50 width=50></img>";
			
			//For ReportNG
			Reporter.log("<br>"+"Verification failure : "+t.getMessage()+"<br>");		
			Reporter.log("<a href=\""+schreenshotImagePath+"\">"+imageThumbNail+"</a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//Extent Reports			
			test.log(LogStatus.FAIL, "Verification Falied with exception :"+t.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(schreenshotImagePath));
			
		}
	}
	@AfterSuite
	public void tearDown()
	{
		if(driver!=null)
		{
			driver.quit();
		}
		
		log.debug("--Test Execution completed--");
		
	}
}
