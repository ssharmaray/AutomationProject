package com.testingproject.qa.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;
import com.testingproject.qa.base.TestBase;
import com.testingproject.qa.util.TestUtil;;

public class CustomListeners extends TestBase implements ITestListener {

	public CustomListeners() throws FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		String testName = result.getName().toUpperCase();
		
		if (!result.getName().toUpperCase().contains("TEST"))
		{
			testName =result.getName().toUpperCase()+" TEST";
		}
		
		test.log(LogStatus.PASS, testName +" PASSED");
		rep.endTest(test);
		rep.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		//Setting property to reportng, to enable HTML code, in reportng HTML reports
		String testName = result.getName().toUpperCase();
		
		if (!result.getName().toUpperCase().contains("TEST"))
		{
			testName =result.getName().toUpperCase()+" TEST";
		}
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Adding custom comments to reportng HTML report
		Reporter.log("--Capturing screenshot --");
		String schreenshotImagePath = TestUtil.screenshotName;
		String imageThumbNail = "<img src="+schreenshotImagePath+" height=50 width=50></img>";
		
		test.log(LogStatus.FAIL, testName+ " Falied with exception :"+result.getThrowable());
		test.log(LogStatus.FAIL,test.addScreenCapture(schreenshotImagePath));
		
		Reporter.log("<a href=\""+schreenshotImagePath+"\" target=\"_blank\">Screenshot</a>");
		Reporter.log("<br>");		
		Reporter.log("<a href=\""+schreenshotImagePath+"\">"+imageThumbNail+"</a>");
		
		rep.endTest(test);
		rep.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		String testName = result.getName().toUpperCase();
		
		if (!result.getName().toUpperCase().contains("TEST"))
		{
			testName =result.getName().toUpperCase()+" TEST";
		}
		test.log(LogStatus.SKIP,testName+" Skipped the test as Runmode is No");
		rep.endTest(test);
		rep.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {		
		
		test=rep.startTest(context.getName().toUpperCase());
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
	}

}
