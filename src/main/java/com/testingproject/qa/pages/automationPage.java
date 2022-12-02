package com.testingproject.qa.pages;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.testingproject.qa.base.TestBase;
import com.testingproject.qa.util.TestUtil;

public class automationPage extends TestBase  {

	//Page Factory - OR:
	@FindBy(xpath="//input[@name='name']")
	WebElement name;
	
	@FindBy(xpath="//input[@name='phone']")
	WebElement phone;
	
	@FindBy(xpath="//input[@name='email']")
	WebElement email;
	
	@FindBy(xpath="//select[@name='country']")
	WebElement country;
	
	@FindBy(xpath="//input[@name='city']")
	WebElement city;
	
	@FindBy(xpath="(//input[@name='username'])[2]")
	WebElement username;
	
	@FindBy(xpath="(//input[@name='password'])[2]")
	WebElement password;	
	
	@FindBy(xpath="(//input[@type='submit'])[2]")
	WebElement submitButton;
		
	public automationPage() throws FileNotFoundException {
		super();
		PageFactory.initElements(driver, this);
	}
	
	//Actions
	public String validateAutomationPageTitle()
	{
		return driver.getTitle();
	}
	
	
	public void submitTest(Hashtable<String,String> data) throws InterruptedException
	{
		type("//input[@name='name']",data.get("Name").toString(),"Name");
		type("//input[@name='phone']",data.get("Phone").toString(),"Phone");
		type("//input[@name='email']",data.get("Email").toString(),"Email");
		select("//select[@name='country']",data.get("Country").toString(),"Country");
		type("//input[@name='city']",data.get("City").toString(),"City");
		type("(//input[@name='username'])[2]",data.get("UserName").toString(),"UserName");
		type("(//input[@name='password'])[2]",data.get("Password").toString(),"Password");
		click("(//input[@type='submit'])[2]","SUBMIT Button");
				
	}

}
