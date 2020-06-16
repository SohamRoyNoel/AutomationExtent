package PDFCompare;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.File;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class ListFilesUtil
{
    static ReportGenerationHelperClass reportGenerationHelperObj;
    String Target_file_name;
    String Target_Directory_name;
    String Target_path;
    String Target_Output_path;
    String Source_file_name;
    String Source_Directory_name;
    String Source_path;
    String Result_path;
    String Source_Output_path;
    static String[] arr;
    static String[] filename;
    static JFrame frame;
    int i;
    static int m;
    static int n;
    static int rownum;
    
    static {
        ListFilesUtil.reportGenerationHelperObj = new ReportGenerationHelperClass();
        ListFilesUtil.arr = new String[100];
        ListFilesUtil.filename = new String[100];
        ListFilesUtil.m = 0;
        ListFilesUtil.n = 0;
        ListFilesUtil.rownum = 1;
    }
    
    public ListFilesUtil() {
        this.Target_file_name = "";
        this.Target_Directory_name = "";
        this.Target_path = "";
        this.Target_Output_path = "";
        this.Source_file_name = "";
        this.Source_Directory_name = "";
        this.Source_path = "";
        this.Result_path = "";
        this.Source_Output_path = "";
        this.i = 0;
    }
    
   
   
    
    public static void CreateUserDirectory(final String usrDir) {
        final File theDir = new File(usrDir);
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            }
            catch (SecurityException se) {
                throw se;
            }
        }
    }
    
   
    
    public static String test(String Directory,ArrayList<String> arr) throws IOException {
        final ListFilesUtil listFilesUtil = new ListFilesUtil();
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        final String test = Directory;
       // System.out.println(test);
        final String directoryWindows = test;
        CreateUserDirectory(String.valueOf(directoryWindows) + "\\Overall_Report");
        final String outputDirLocation = String.valueOf(directoryWindows) + "\\Overall_Report\\";
        final String temp_file1 = String.valueOf(directoryWindows) + "\\Overall_Report\\Temp1.txt";
        final File f1 = new File(temp_file1);
        f1.createNewFile();
     String srcDr="";
     String trgDr="";
        String[] srcfile = null;
        String[] trgfile = null;
        String output = "";
        String path = "";
        String temp_name = "";
        String folder_name = "";
      
        String[] elementchecker =null;
    	String PathKey="";
    		String PathValue="";
    	for(String str:arr)  
    	{
    		String temp="";
    		//System.out.println("Inside:"+str);
    		if(str.split("\\|")[1].equals("Before Migration")){
    			temp= str.split("\\|")[2];
    			srcDr=str.split("\\|")[3];
    			srcfile=temp.split("\\;");
    		}
    		if(str.split("\\|")[1].equals("After Migration")){
    			temp= str.split("\\|")[2];
    			trgDr=str.split("\\|")[3];
    			trgfile=temp.split("\\;");
    		}
    		
           // PdfTPdf.compare(srcfile, trgfile, output, temp_file1, folder_name);
    	}
    	for(int i=0;i<srcfile.length;i++)
    	{
    		String src=srcDr+"\\"+srcfile[i];
    		String trg=trgDr+"\\"+trgfile[i];
    		//System.out.println("Source flnm:"+src+" "+"Targ:"+trg);
    		path=src.substring(0, src.lastIndexOf("\\"));
     	  
     	   
     	   
    		PdfTPdf.compare(src, trg, outputDirLocation, temp_file1);
    	}
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet XSSFSheetSummary = ListFilesUtil.reportGenerationHelperObj.createReportSheet("Summary_Report", workbook);
        createSummaryReport(XSSFSheetSummary, workbook);
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(temp_file1));
        String line = null;
        final int line_count = 0;
        final String delimiter = ",";
        String str = " ";
        String str2 = " ";
        String str3 = " ";
        String str4 = " ";
       
        while ((line = bufferedReader.readLine()) != null) {
            final String[] temp = line.split(delimiter);
            for (int i = 0; i < temp.length; ++i) {
                str = temp[0];
                str2 = temp[1];
                str3 = temp[2];
                str4 = temp[3];
                
            }
            writeDataIntoCellsMismatch(XSSFSheetSummary, workbook, str, str2, str3, str4);
        }
        bufferedReader.close();
       // System.out.println("Test"+outputDirLocation);
       // final String path2 = ListFilesUtil.reportGenerationHelperObj.createReportDirectoryAndGetPath(outputDirLocation);
        ListFilesUtil.reportGenerationHelperObj.writeWorkbook(String.valueOf(outputDirLocation) + "Overall_test_report_" + timeStamp + ".xlsx", workbook);
        
        final File file = new File(temp_file1);
        if (file.exists() && file.isFile()) {
            file.delete();
           // System.out.println("Done");
        }
       // JOptionPane.showMessageDialog(ListFilesUtil.frame, "Execution Completed Please Check The Report Directory");
        return String.valueOf(outputDirLocation) + "\\Overall_test_report_" + timeStamp + ".xlsx";
    }
    
    private static void createSummaryReport(final XSSFSheet XSSFSheetMismatch, final XSSFWorkbook workbook) {
        final String[] columnNames = { "Source File Name", "Target File Name", "Status", "Comment" };
        ListFilesUtil.reportGenerationHelperObj.createRowInExcel(0, columnNames, XSSFSheetMismatch, workbook, ListFilesUtil.reportGenerationHelperObj.createCellStyle(workbook));
    }
    
    private static void writeDataIntoCellsMismatch(final XSSFSheet XSSFSheet, final XSSFWorkbook workbook,  final String Source_File_Name, final String Target_File_Name, final String Status, final String Comment) {
        final String[] rowData = { Source_File_Name, Target_File_Name, Status, Comment };
        ListFilesUtil.reportGenerationHelperObj.createRowInExcel(ListFilesUtil.rownum++, rowData, XSSFSheet, workbook, ListFilesUtil.reportGenerationHelperObj.createCellStyle1(workbook));
    }
}
