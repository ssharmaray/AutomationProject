package com.testingproject.qa.testcases;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testingproject.qa.base.TestBase;
import com.testingproject.qa.pages.automationPage;
import com.testingproject.qa.util.TestUtil;

public class AutomationPageTest extends TestBase {
	
	//Test cases for the Automation Page Registration Test
	//Atempting merging

	automationPage automationPage;
	
	public AutomationPageTest() throws FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
	@BeforeTest
	public void setUp() throws FileNotFoundException
	{
		initialization();
		automationPage = new automationPage();
	}
	
	@Test(priority=1)
	public void homePageTitleTest()
	{
		String title = automationPage.validateAutomationPageTitle();
		Assert.assertEquals(title, "Welcome to the Test Site");
		Assert.fail();
	}
	
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dataProvider", priority=2)
	public void Submitclick(Hashtable<String,String> data) throws InterruptedException
	{
		automationPage.submitTest(data);
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
}