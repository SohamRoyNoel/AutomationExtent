package Report;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentAventReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
	
public class ExtentLibraryTest {
	
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;

	
	public static void main(String args[]) throws IOException{
		htmlReporter = new ExtentHtmlReporter("C:\\Users\\Chirura\\Documents\\IBM_ICM_Automation\\STMExtentReport.html");
        	// Create an object of Extent Reports
		extent = new ExtentReports();  
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "SoftwareTestingMaterial");
        extent.setSystemInfo("Environment", "Production");
		extent.setSystemInfo("User Name", "Suman Chanda");
		htmlReporter.config().setDocumentTitle("Title of the Report Comes here "); 
                // Name of the report
		htmlReporter.config().setReportName("Name of the Report Comes here "); 
                // Dark Theme
		htmlReporter.config().setTheme(Theme.DARK);			
		logger = extent.createTest("To verify Google Title");
		logger.assignCategory("Sanity");
		logger.log(Status.FAIL, MarkupHelper.createLabel("Count Failed" + " - Test Case Failed", ExtentColor.RED));
		logger.log(Status.FAIL, MarkupHelper.createLabel("Data Failed" + " - Test Case Failed", ExtentColor.RED));
		String screenshotPath = "C:\\Users\\Chirura\\Documents\\IBM_ICM_Automation\\Capture.PNG";
		String screenshotPath1 = "C:\\Users\\Chirura\\Documents\\IBM_ICM_Automation\\download.jpeg";
		//To add it in the extent report 
		logger.fail("details", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

		//logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
		//logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath1));
		
		logger.log(Status.PASS, MarkupHelper.createLabel("Count Passed" + " - Test Case Failed", ExtentColor.GREEN));
		
		logger = extent.createTest("To verify Google Title1");
		logger.createNode("Step 1","Check if the count is matching");
		logger.assignCategory("Regression");
		logger.log(Status.PASS, MarkupHelper.createLabel("Count Failed" + " - Test Case Failed", ExtentColor.GREEN));
		logger.createNode("Step 2","Check if the Data is matching");
		logger.log(Status.PASS, MarkupHelper.createLabel("Data Failed" + " - Test Case Failed", ExtentColor.GREEN));
		logger.createNode("Step 3","Check if the count is matching");
		logger.log(Status.PASS, MarkupHelper.createLabel("Count Passed" + " - Test Case Failed", ExtentColor.GREEN));
		
		
		logger = extent.createTest("To verify Google Title2");
		logger.assignCategory("Functional");
		logger.log(Status.PASS, MarkupHelper.createLabel("Count Failed" + " - Test Case Failed", ExtentColor.GREEN));
		logger.log(Status.PASS, MarkupHelper.createLabel("Data Failed" + " - Test Case Failed", ExtentColor.GREEN));
		logger.log(Status.SKIP, MarkupHelper.createLabel("Count Passed" + " - Test Case Failed", ExtentColor.CYAN));
		
		logger = extent.createTest("To verify Google Title3");
		logger.assignCategory("Regression");
		logger.log(Status.PASS, MarkupHelper.createLabel("Count Failed" + " - Test Case Failed", ExtentColor.GREEN));
		logger.log(Status.ERROR, MarkupHelper.createLabel("Data Failed" + " - Test Case Failed", ExtentColor.ORANGE));
		logger.log(Status.INFO, MarkupHelper.createLabel("Count Passed" + " - Test Case Failed", ExtentColor.BLUE));
		
		
		
		extent.flush();
		
		
		
		
	}
	
	

	
}