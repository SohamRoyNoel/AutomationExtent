package CoreFunction;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

import Helper.Constants;
import Helper.ExcelAsDatabase;

import Helper.WebElementDriverRunner;
import PDFCompare.ListFilesUtil;
import Report.ExtentReportGenerationHelperClass;

public class Main 
{
	public Main(){
		
	}
	static String FilePath="C:\\Users\\Chirura\\Documents\\IBM_ICM_Automation\\IBMICM_Automation_Poc.xlsx"; 
	static String DriverSheet="BatchRun";
	static String ObjectRepositorySheet="ObjRep";
	static String BusinessFlowSheet="BusinessFlow";
	static String chromeDrivePath="C:\\Users\\Chirura\\Documents\\IBM_ICM_Automation\\driver\\chromedriver.exe";
	static String timeStamp2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	static String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
	static String TrgtFilename="";
	static String File_details="";
	static String directory_details="";
    
	public static void main(String[] args) throws Exception 
  {
		// TODO Auto-generated method stub
		CreateUserDirectory(Constants.ResourcePath.ReportDirectory);
		
	ArrayList<String> Driver =ExcelAsDatabase.ReadDriverExcel(FilePath, DriverSheet);
	for(String str:Driver)  
	{
        System.out.println(str); 
        if(str.split("\\|")[1].equals("Before Migration") && str.split("\\|")[2].equals("Y")) 
        {
        	String FinalPath=createReportDirectoryAndGetPath(Constants.ResourcePath.ReportDirectory);
        	LinkedHashMap<String, String>  ObjectRepository =ExcelAsDatabase.ReadObjRep(FilePath, ObjectRepositorySheet);
        	ArrayList<String> BusinessFlow =ExcelAsDatabase.ReadBusinessFlow(FilePath, BusinessFlowSheet);
        	CreateUserDirectory(FinalPath+"\\"+"Before Migration");
        	BusinessFlow(BusinessFlow,ObjectRepository,FinalPath+"\\"+"Before Migration");
		ExcelAsDatabase.ExcelInsert(FilePath,DriverSheet,str.split("\\|")[0],str.split("\\|")[1],str.split("\\|")[2],timeStamp2,File_details.substring(1, File_details.length()),directory_details);
        }
        if(str.split("\\|")[1].equals("After Migration") && str.split("\\|")[2].equals("Y")) 
        {
        	String FinalPath=createReportDirectoryAndGetPath(Constants.ResourcePath.ReportDirectory);
        	LinkedHashMap<String, String>  ObjectRepository =ExcelAsDatabase.ReadObjRep(FilePath, ObjectRepositorySheet);
        	ArrayList<String> BusinessFlow =ExcelAsDatabase.ReadBusinessFlow(FilePath, BusinessFlowSheet);
        	CreateUserDirectory(FinalPath+"\\"+"After Migration");
        	BusinessFlow(BusinessFlow,ObjectRepository,FinalPath+"\\"+"After Migration");
        	ExcelAsDatabase.ExcelInsert(FilePath,DriverSheet,str.split("\\|")[0],str.split("\\|")[1],str.split("\\|")[2],timeStamp2,File_details.substring(1, File_details.length()),directory_details);
             }
        if(str.split("\\|")[1].equals("Full run") && str.split("\\|")[2].equals("Y")) 
        {
        	String FinalPath=createReportDirectoryAndGetPath(Constants.ResourcePath.ReportDirectory);
        	//LinkedHashMap<String, String>  ObjectRepository =ExcelAsDatabase.ReadObjRep(FilePath, ObjectRepositorySheet);
        	ArrayList<String> BusinessFlow =ExcelAsDatabase.ReadFullRun(FilePath, DriverSheet);
        	//System.out.println("FinalPath:"+FinalPath);
        	CreateUserDirectory(FinalPath+"\\"+"Full run");
        	String filedetails=ListFilesUtil.test(FinalPath+"\\"+"Full run",BusinessFlow);
        //	BusinessFlow(BusinessFlow,ObjectRepository,FinalPath+"\\"+"Full run");
        	ExcelAsDatabase.ExcelInsert(FilePath,DriverSheet,str.split("\\|")[0],str.split("\\|")[1],str.split("\\|")[2],timeStamp2,filedetails.substring(filedetails.lastIndexOf("\\")+1,filedetails.length()),filedetails.substring(0, filedetails.lastIndexOf("\\")));
        	
        	//System.out.println("filedetails: "+filedetails);
        	ExtentReportGenerationHelperClass.setterConnection(filedetails);
        	ExtentReportGenerationHelperClass.setColumnList(FinalPath+"\\"+"\\Overall_Report\\ColumnListfromexcelReports.properties");
        	ExtentReportGenerationHelperClass.setHtmlReport(FinalPath+"\\"+"\\Overall_Report\\");
    		ExtentReportGenerationHelperClass.getExtentReport();
        }
	}
	
  }
	public static String createReportDirectoryAndGetPath(String location) {
        String username = null;
        String stringPath;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        username = System.getProperty("user.name");
        File main_directory = new File(location);
        if (!main_directory.exists()) {
            if (main_directory.mkdir()) {
                //System.out.println("Main Directory is created!");
            } else {
                //System.out.println("Failed to create main directory!");
            }
        }
     /*   File Root_directory = new File(location + "DataComparaTo\\" + username);
        if (!Root_directory.exists()) {
            if (Root_directory.mkdir()) {
                System.out.println("Root Directory is created!");
            } else {
                System.out.println("Failed to create root directory!");
            }
        }*/
        File Run_directory = new File(location + "\\Run_" + dateFormat.format(date));
        if (!Run_directory.exists()) {
            if (Run_directory.mkdir()) {
                //System.out.println("Directory is created!");
            } else {
                //System.out.println("Failed to create directory!");
            }
        }
        stringPath = location + "\\Run_" + dateFormat.format(date);
        return stringPath;
    }
	public static  void CreateUserDirectory(String usrDir) {

        File theDir = new File(usrDir);
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
                // System.out.println("User Directoty: "+usrDir+" Sucessfully Created ");

            } catch (SecurityException se) {
                //handle it
            	//System.out.println("User Directoty: "+usrDir+" Failed to be Created "+se);
                throw se;
                
            }
        }
    }

	
    public static void ObjectRepository(ArrayList<String> arr) throws FilloException, IOException {
		
		for(String str:arr)  
		{
	        System.out.println(str); 
	       
		}
	}
public static void BusinessFlow(ArrayList<String> arr,LinkedHashMap<String, String> arr1,String FinalPath) throws FilloException, IOException, InterruptedException {
	
	String[] elementchecker =null;
	String PathKey="";
		String PathValue="";
	for(String str:arr)  
	{
		System.out.println(str);
		elementchecker=str.split("\\|");
		
		LinkedHashMap<String, String> EntrFlow = new LinkedHashMap<String, String>();
       
	for (int i=0;i<elementchecker.length;i++)
       {
		
		
		if(!elementchecker[i].contains(";"))
   	        {
		       if(!(arr1.get(elementchecker[i])==null))
		       {
		    	  // System.out.println("ElementValue"+elementchecker[i]);
		  			PathKey=elementchecker[i];
		  			PathValue=arr1.get(elementchecker[i]);
		  			//System.out.println("PathKey:"+PathKey +" "+"PathValue:"+PathValue);
		  			EntrFlow.put(PathKey, PathValue);
				//WebElementDriverRunner.ExecuteRun(chromeDrivePath,PathKey,PathValue);   
		       }
    	 }
		else
    		{
   	        	//System.out.println("ElementValue"+elementchecker[i]);
   	        		PathValue=arr1.get(elementchecker[i].split("\\;")[0]);
   	        	//	System.out.println("Pathh"+PathValue);
    	   			String LastIndex=PathValue.substring(PathValue.indexOf("#"),PathValue.lastIndexOf("'"));
	       	        	//System.out.println(LastIndex);
	       	        	String finalValue=PathValue.replace(LastIndex, elementchecker[i].split("\\;")[1]);
	       	        	PathKey=elementchecker[i];
	       	        	//System.out.println("PathKey:"+elementchecker[i].split("\\;")[0] +" "+"PathValue:"+finalValue);
			  			EntrFlow.put(elementchecker[i].split("\\;")[0], finalValue);
				}
		//EntrFlow=null;
   	        }
	/*Set set = EntrFlow.entrySet();
    Iterator iterator = set.iterator();
    while(iterator.hasNext()) 
    {
       Map.Entry mentry = (Map.Entry)iterator.next();
       System.out.println("key is: "+ mentry.getKey() + " & Value is: "+mentry.getValue() );
    }*/
	WebElementDriverRunner.ExecuteRun(chromeDrivePath,EntrFlow);
    EntrFlow=null;
  File Srcfile=  getTheNewestFile("C:\\Users\\chirura\\Downloads","pdf");
  TrgtFilename=elementchecker[0]+"_"+ FinalPath.substring(FinalPath.lastIndexOf("\\")+1,FinalPath.length())+"_"+timeStamp+".pdf";
  System.out.println(TrgtFilename);
  File targetFile=new File(FinalPath+"\\"+TrgtFilename);
  System.out.println(Srcfile.getAbsolutePath());
  System.out.println(targetFile.getAbsolutePath());
  File_details=File_details+";"+targetFile.getName();
  directory_details=targetFile.getAbsolutePath().substring(0, targetFile.getAbsolutePath().lastIndexOf("\\"));
  copy(Srcfile.getAbsolutePath(),targetFile.getAbsolutePath());
  //copy(Srcfile,targetFile);
    	   }
       
       
	elementchecker=null;
    }
private static void copy(String src, String dest) throws IOException {
//private static void copy(File src, File dest) throws IOException {
/*    File file=new File(src);
    if(file.renameTo(new File(dest)))
    {
    	file.delete();
    	System.out.println("Completed");
    }else
    {
    	System.out.println("not Completed");
    }*/
	/*InputStream is = null;
    OutputStream os = null;
    try {
        is = new FileInputStream(src);
        os = new FileOutputStream(dest);

        // buffer size 1K
        byte[] buf = new byte[1024];

        int bytesRead;
        while ((bytesRead = is.read(buf)) > 0) {
            os.write(buf, 0, bytesRead);
        }
    } finally {
        is.close();
        os.close();
    }*/
	Path temp=Files.move(Paths.get(src), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
	if(temp!= null)
	{System.out.println("Done");
		
	}
	else
	{
		System.out.println("Not Done");	
	}

}
public static File getTheNewestFile(String filePath, String ext) {
    File theNewestFile = null;
    File dir = new File(filePath);
    FileFilter fileFilter = new WildcardFileFilter("*." + ext);
    File[] files = dir.listFiles(fileFilter);

    if (files.length > 0) {
        /** The newest file comes first **/
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        theNewestFile = files[0];
    }

    return theNewestFile;
}
public static String format(long time) {
    DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    return sdf.format(new Date(time));
  }
}
