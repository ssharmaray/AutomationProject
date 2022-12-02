package com.testingproject.qa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.testingproject.qa.base.TestBase;


public class TestUtil extends TestBase  {

	//Utilities class
	public TestUtil() throws FileNotFoundException {
		super();
		
	}

	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 50;
	
	@DataProvider(name="dataProvider")
	public static Object[][] getData(Method m)
	{
		String sheetName="RegistrationTest";

		int rows=excel.getRowCount(sheetName);
		int columns=excel.getColumnCount(sheetName);
		
		Object[][] data=new Object[rows-1][1];
		
		Hashtable<String,String> table = null;
		
		for(int rowNum=2; rowNum<=rows;rowNum++) 
		{
				
			table = new Hashtable<String,String>();
			for(int colNum=0;colNum<columns;colNum++) 
			{
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum-2][0]=table;
			}

		}
		return data;
	}
	
	public static String screenshotPath;
	public static String screenshotName;

	
	public static void captureScreenShot() throws IOException
	{
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
		FileUtils.copyFile(scrFile,
				new File(".\\reports\\" + screenshotName));
		
		

	}
}
