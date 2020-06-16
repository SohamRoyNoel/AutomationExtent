package Report;

import java.util.Map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExtentReportGenerationHelperClass {
	static Connection connection = null;
	static String FinalReportLocation="";
	static String ColumnList="";
	public static Recordset recordset;
	public static Fillo fillo = new Fillo();
	
	// Generates extent report
	public static void getExtentReport() throws Exception{

		ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(getterHtmlReport());
		String name=getterHtmlReport();
		String name1=name.substring(name.indexOf("Run"), name.indexOf("Run")+14);
		//System.out.println("Html name:"+name1);
		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(extentHtmlReporter);
		extentHtmlReporter.config().setDocumentTitle("Regression Report"); 
        // Name of the report
		extentHtmlReporter.config().setReportName("Regression Report for "+name1); 
        // Dark Theme
		extentHtmlReporter.config().setTheme(Theme.DARK);	
		listtheColumnnames();
		for(int i=1; i <= noOfrows(); i++){
			String text = fieldvalues(i).get("keys1").substring(fieldvalues(i).get("keys1").lastIndexOf("\\")+1, fieldvalues(i).get("keys1").length());
			String text1 =text.substring(0,text.indexOf("Before")-1);
					
			ExtentTest extentTest = extentReports.createTest("Test Report for test case : "+text1);
			extentTest.log(Status.INFO, fieldvalues(i).get("keys1"));
			extentTest.log(Status.INFO, fieldvalues(i).get("keys2"));
			if(fieldvalues(i).get("keys3").equalsIgnoreCase("Pass")){
				
				extentTest.log(Status.PASS, MarkupHelper.createLabel(fieldvalues(i).get("keys4"), ExtentColor.GREEN));
			}
			if(fieldvalues(i).get("keys3").equalsIgnoreCase("Fail")){
				extentTest.log(Status.FAIL, MarkupHelper.createLabel(fieldvalues(i).get("keys4"), ExtentColor.RED));
				
			}
				
			extentReports.flush();
		}
	}
	public static void setterConnection(String InputFile) throws FilloException{
		
		connection =fillo.getConnection(InputFile);
		
	}
public static Connection getterConnection() throws FilloException{
		
		return connection;
	}

public static String setHtmlReport(String InputFile) throws FilloException{
	String name=getColumnList();
	System.out.println("check:"+name);
	String name1=name.substring(name.indexOf("Run"), name.indexOf("Run")+14);
	
	FinalReportLocation =InputFile+"\\Regression_Report_"+name1+".html";
	return FinalReportLocation;
		
	}
public static String getterHtmlReport() throws FilloException{
	
		return FinalReportLocation;
	}

public static String setColumnList(String InputFile) throws FilloException{
	
	ColumnList =InputFile;
	return ColumnList;
		
	}
public static String getColumnList() throws FilloException{
	
	
		return ColumnList;
	}
	// Gets the fillo connection
	public static Connection getFilloconnection() throws Exception {
		Connection connection1 = getterConnection();
		return connection1;
	}

	// Counts the number of rows
	public static int noOfrows() throws Exception{
		String query = "select * from Summary_Report";
		//System.out.println("Connection in rows: "+getFilloconnection());
		Connection connection = getFilloconnection();
		int rowcount = connection.executeQuery(query).getCount();
		return rowcount;
	}

	// lists the column names and writes it it external properties file 
	public static ArrayList<String> listtheColumnnames() throws Exception {
		String query = "select * from Summary_Report";
		//System.out.println("Connection in array: "+getFilloconnection());
		Connection connection = getFilloconnection();
		Recordset rs = connection.executeQuery(query);
		ArrayList<String> elements = rs.getFieldNames();
		Properties properties = new Properties();
		for (int i = 0; i < elements.size(); i++) {
			String key = "key" + i;
			properties.setProperty(key, elements.get(i));
		}
		//System.out.println("Column list in array: "+getColumnList());
		File fl = new File(getColumnList());

		FileOutputStream fileOutputStream = new FileOutputStream(fl);
		properties.store(fileOutputStream, "Columns from the Report Generation excel sheet");
		fileOutputStream.close();
		return elements;
	}

	// Counts the column
	public static int noOfcolumns() throws Exception {
		String query = "select * from Summary_Report";
		//System.out.println("Connection in col: "+getFilloconnection());
		Connection connection = getFilloconnection();
		Recordset rs = connection.executeQuery(query);
		int count = rs.getFieldNames().size();
		return count;
	}

	// Read The property file
	public static String properties(String key) throws Exception {
		//System.out.println("Column list in properties: "+getColumnList());
		File fl = new File(getColumnList());
		FileInputStream file = new FileInputStream(fl);
		Properties rpop = new Properties();
		rpop.load(file);
		String data = rpop.getProperty(key);
		return data;
	}

	// get row data as per the row
	public static Map<String, String> fieldvalues(int rowcount) throws Exception {
		String query = "select * from Summary_Report";
		Connection connection = getFilloconnection();
		Recordset rs = connection.executeQuery(query);
		Map<String, String> rowmap = new HashMap<String, String>();
		int counter = 1;
		// As the number of the properties will always be same as the no of columns
		int noofproperties = noOfcolumns();
		//System.out.println("no of props : " + noofproperties);
		while (rs.next()) {
			if (counter == rowcount) {
				for (int i = 0; i < noofproperties; i++) {
					// prepare the key
					String prepareKey = "keys"+i;
					rowmap.put(prepareKey, rs.getField(properties("key"+i)));
				}
			}
			counter++;
		}
		return rowmap;
	}

	
}
