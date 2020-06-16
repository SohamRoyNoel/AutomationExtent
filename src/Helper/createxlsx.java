package Helper;


import java.text.SimpleDateFormat;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;
import java.util.*;
public class createxlsx {  
	
	 static  String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
	    static  XSSFWorkbook workbook = new XSSFWorkbook();
	     static  XSSFSheet XSSFSheetPrjWithoutRisk = createReportSheet("Project without risk", workbook);
	     static  List<String> PrjWithoutRisk = new ArrayList();
        public static void create(String InputFile) throws Exception{
        	
        	FileInputStream input_file = new FileInputStream(new File(InputFile)); //create XLSX file
            XSSFWorkbook new_workbook = new XSSFWorkbook(input_file);
        }
        
        static int rownum5=1;
        public static void excelWrite(){
         createColumnsXSSFSheetPrjWithoutRisk(XSSFSheetPrjWithoutRisk, workbook);
         writeDataIntoCellsMitigationPlanNotAvailable(XSSFSheetPrjWithoutRisk, workbook,PrjWithoutRisk);
          
        }
        public static XSSFSheet createReportSheet(String sheetName, XSSFWorkbook workbook) {
            XSSFSheet sheet = workbook.createSheet(sheetName);
            return sheet;
        }
        private static void createColumnsXSSFSheetPrjWithoutRisk(XSSFSheet XSSFSheetMaster, XSSFWorkbook workbook) {

            String[] columnNames = {"Project Id","DRM Parameter","Project Name","PM ID","DE Poc","Account Lead","Parent Account","Status","Comments"};
          createRowInExcel(0, columnNames, XSSFSheetMaster, workbook, createCellStyle(workbook));
          //createRowInExcel1(0, columnNames, XSSFSheetMaster, workbook, createCellStyle(workbook));
        }
        
        public static void createRowInExcel(int rowNumber, String[] RowValues, XSSFSheet XSSFSheet, XSSFWorkbook workbook, CellStyle style) {
            Row row = XSSFSheet.createRow(rowNumber);
            for (int i = 0; i < RowValues.length; i++) {
                Cell cell = row.createCell(i + 1);
                cell.setCellValue(RowValues[i]);
                
                System.out.println("Row values: "+RowValues.length);
                cell.setCellStyle(style);
                XSSFSheet.autoSizeColumn(i + 1);
            }
            
        }
        public static CellStyle createCellStyle(XSSFWorkbook workbook) {
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderRight((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderTop((short) 1);
            style.setBorderBottom((short) 1);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 15);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            font.setColor((short) 1);
            style.setFont(font);
            return style;
        }
            public static CellStyle createCellStyle1(XSSFWorkbook workbook) {
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderRight((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderTop((short) 1);
            style.setBorderBottom((short) 1);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 13);
            font.setBoldweight((short) 5);
            font.setColor((short) 0);
            style.setFont(font);
            return style;
        }
            public static String createReportDirectoryAndGetPath(String location) {
            String username = null;
            String stringPath;
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            username = System.getProperty("user.name");
            File main_directory = new File(location + "ExcelReport");
            if (!main_directory.exists()) {
                if (main_directory.mkdir()) {
                    System.out.println("Main Directory is created!");
                } else {
                    System.out.println("Failed to create main directory!");
                }
            }
            File Root_directory = new File(location + "ExcelReport\\" + username);
            if (!Root_directory.exists()) {
                if (Root_directory.mkdir()) {
                    System.out.println("Root Directory is created!");
                } else {
                    System.out.println("Failed to create root directory!");
                }
            }
            File Run_directory = new File(location + "ExcelReport\\" + username + "\\ReportFor_" + dateFormat.format(date));
            if (!Run_directory.exists()) {
                if (Run_directory.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
            stringPath = location + "ExcelReport\\" + username + "\\ReportFor_" + dateFormat.format(date);
            return stringPath;
        }
             public static void writeWorkbook(String fileName, XSSFWorkbook workbook) {
            try {
                System.out.println("writing to file"+fileName);
                FileOutputStream out = new FileOutputStream(new File(fileName));
                workbook.write(out);
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        private static void writeDataIntoCellsMitigationPlanNotAvailable(XSSFSheet XSSFSheet, XSSFWorkbook workbook, List<String> MitigationPlanNotAvailable ) {
        	for (String temp : MitigationPlanNotAvailable) {
        		 String[] rowData = temp.split("\\|");
        	        createRowInExcel(rownum5++, rowData, XSSFSheet, workbook, createCellStyle1(workbook));
        			}
        	      }   


}