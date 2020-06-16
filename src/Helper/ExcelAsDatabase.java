/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

/**
 *
 * @author 402025
 */
//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;

public class ExcelAsDatabase
{
	public static void ExcelInsert(String InputFile,String sheet,String sl_no,String run,String flag,String Completion_time,String Location,String DRLocation) throws FilloException, IOException {
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(InputFile);
		FileInputStream input_file = new FileInputStream(new File(InputFile)); //create XLSX file
        XSSFWorkbook new_workbook = new XSSFWorkbook(input_file);
        
XSSFSheet ws = new_workbook.getSheet(sheet);

String strQuery="Update "+sheet+" Set Completion_time='"+Completion_time+"' where Flag='"+flag+"' and run='"+run+"' and sl_no="+sl_no;
String strQuery1="Update "+sheet+" Set File_names='"+Location+"' where Flag='"+flag+"' and run='"+run+"' and sl_no="+sl_no+" and Completion_time='"+Completion_time+"'";
String strQuery2="Update "+sheet+" Set Directory_Location='"+DRLocation+"' where Flag='"+flag+"' and run='"+run+"' and sl_no="+sl_no+" and Completion_time='"+Completion_time+"'";


System.out.println(strQuery);
connection.executeUpdate(strQuery);
connection.executeUpdate(strQuery1);
connection.executeUpdate(strQuery2);
new_workbook.close();
input_file.close();
		connection.close();
	}
  public static ArrayList<String> ReadDriverExcel(String FilePath,String Sheet) throws ClassNotFoundException, FilloException 
  {
	  
	  System.out.println(FilePath +"   "+Sheet);
	  Recordset rs=null;
	  Connection connection=null;
    java.sql.Connection con =null;
    ArrayList<String> BatchRunValue = new ArrayList<String>();

      try
       {         
    	  System.out.println("Here");
    	  Fillo fillo=new Fillo();
  		connection=fillo.getConnection(FilePath);
  		
        
        String excelQuery = "select * from "+Sheet;
        rs=connection.executeQuery(excelQuery);
        while (rs.next()) {
        	String Value=rs.getField("Sl_no") + "|"+rs.getField("Run") + "|" + rs.getField("Flag") + "|"
                    + rs.getField("Completion_time");
       // System.out.println(Value);
        BatchRunValue.add(Value);
                           }
       }
      finally 
      {
        rs.close();
       
        connection.close();
      }
      return(BatchRunValue);
   }
  public static LinkedHashMap<String, String> ReadObjRep(String FilePath,String Sheet) throws ClassNotFoundException, FilloException 
  {
	  
	  System.out.println(FilePath +"   "+Sheet);
	  Recordset rs=null;
	  Connection connection=null;
    java.sql.Connection con =null;
    LinkedHashMap<String, String> BatchRunValue = new LinkedHashMap<String, String>();
    

      try
       {         
    	  System.out.println("Here");
    	  Fillo fillo=new Fillo();
  		connection=fillo.getConnection(FilePath);
  		
        
        String excelQuery = "select * from "+Sheet;
        rs=connection.executeQuery(excelQuery);
        while (rs.next()) {
        	//String Value=rs.getField("Elements") + "|"+rs.getField("Xpath");
       // System.out.println(Value);
        BatchRunValue.put(rs.getField("Elements"),rs.getField("Xpath"));
                           }
       }
      finally 
      {
        rs.close();
       
        connection.close();
      }
      return(BatchRunValue);
   }
  public static ArrayList<String> ReadBusinessFlow(String FilePath,String Sheet) throws ClassNotFoundException, FilloException 
  {
	  
	  System.out.println(FilePath +"   "+Sheet);
	  Recordset rs=null;
	  Connection connection=null;
    java.sql.Connection con =null;
    ArrayList<String> BatchRunValue = new ArrayList<String>();
    ArrayList<String> FieldNames = new ArrayList<String>();
   
      try
       {         
	    	System.out.println("Here");
	    	Fillo fillo=new Fillo();
	  		connection=fillo.getConnection(FilePath);
	  		String excelQuery = "select * from "+Sheet;
	        rs=connection.executeQuery(excelQuery);
	        FieldNames=rs.getFieldNames(); 
	        int columncount=FieldNames.size();
	       
	        while (rs.next()) 
	        {
	        String test="";
		        for(int i=0;i<columncount;i++) 
		        {
		        	
		            	String Value=rs.getField(FieldNames.get(i));
		            	test=test+Value+"|";
		         }
	        test=test.substring(0, test.lastIndexOf("|"));
	        BatchRunValue.add(test);
	        }
       }
      finally 
      {
        rs.close();
       
        connection.close();
      }
      return(BatchRunValue);
   }
  public static ArrayList<String> ReadFullRun(String FilePath,String Sheet) throws ClassNotFoundException, FilloException 
  {
	  
	  System.out.println(FilePath +"   "+Sheet);
	  Recordset rs=null;
	  Connection connection=null;
    java.sql.Connection con =null;
    ArrayList<String> BatchRunValue = new ArrayList<String>();

      try
       {         
    	  System.out.println("Here");
    	  Fillo fillo=new Fillo();
  		connection=fillo.getConnection(FilePath);
  		
        
        String excelQuery = "select * from "+Sheet;
        rs=connection.executeQuery(excelQuery);
        while (rs.next()) {
        	String Value=rs.getField("Sl_no") + "|"+rs.getField("Run") + "|" + rs.getField("File_names") + "|"
                    + rs.getField("Directory_Location");
       // System.out.println(Value);
        BatchRunValue.add(Value);
                           }
       }
      finally 
      {
        rs.close();
       
        connection.close();
      }
      return(BatchRunValue);
   }
}