package PDFCompare;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// 
// Decompiled by Procyon v0.5.36
// 

public class ReportGenerationHelperClass
{
    public CellStyle createCellStyle(final XSSFWorkbook workbook) {
        final CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern((short)1);
        style.setBorderRight((short)1);
        style.setBorderLeft((short)1);
        style.setBorderTop((short)1);
        style.setBorderBottom((short)1);
        final Font font = workbook.createFont();
        font.setFontHeightInPoints((short)15);
        font.setBoldweight((short)700);
        font.setColor((short)1);
        style.setFont(font);
        return style;
    }
    
    public XSSFSheet createReportSheet(final String sheetName, final XSSFWorkbook workbook) {
        final XSSFSheet sheet = workbook.createSheet(sheetName);
        return sheet;
    }
    
    public String getCellValueInString(final Cell cell) {
        String stringCellValue = "";
        switch (cell.getCellType()) {
            case 1: {
                stringCellValue = cell.getRichStringCellValue().getString();
                break;
            }
            case 0: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    stringCellValue = String.valueOf(cell.getDateCellValue());
                    break;
                }
                final int temp = (int)cell.getNumericCellValue();
                stringCellValue = String.valueOf(temp);
                break;
            }
            case 4: {
                stringCellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            }
            case 2: {
                stringCellValue = String.valueOf(cell.getCellFormula());
                break;
            }
            default: {
                System.out.println();
                break;
            }
        }
        System.out.println(stringCellValue);
        return stringCellValue;
    }
    
    public CellStyle createCellStyle1(final XSSFWorkbook workbook) {
        final CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern((short)1);
        style.setBorderRight((short)1);
        style.setBorderLeft((short)1);
        style.setBorderTop((short)1);
        style.setBorderBottom((short)1);
        final Font font = workbook.createFont();
        font.setFontHeightInPoints((short)13);
        font.setBoldweight((short)5);
        font.setColor((short)0);
        style.setFont(font);
        return style;
    }
    
    public CellStyle createCellStyle2(final XSSFWorkbook workbook) {
        final CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern((short)1);
        style.setBorderRight((short)0);
        style.setBorderLeft((short)0);
        style.setBorderTop((short)0);
        style.setBorderBottom((short)0);
        final Font font = workbook.createFont();
        font.setFontHeightInPoints((short)13);
        font.setBoldweight((short)5);
        font.setColor((short)0);
        style.setFont(font);
        return style;
    }
    
    public void createRowInExcel(final int rowNumber, final String[] RowValues, final XSSFSheet XSSFSheet, final XSSFWorkbook workbook, final CellStyle style) {
        final Row row = XSSFSheet.createRow(rowNumber);
        for (int i = 0; i < RowValues.length; ++i) {
            final Cell cell = row.createCell(i + 1);
            cell.setCellValue(RowValues[i]);
            cell.setCellStyle(style);
            XSSFSheet.autoSizeColumn(i + 1);
        }
    }
    
    public void writeWorkbook(final String fileName, final XSSFWorkbook workbook) {
        try {
            System.out.println("writing to file" + fileName);
            final FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
  /*  public String createReportDirectoryAndGetPath(final String location) {
        String username = null;
        final Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        username = System.getProperty("user.name");
        final File main_directory = new File(String.valueOf(location) + "TIFtoTIFCompare");
        if (!main_directory.exists()) {
            if (main_directory.mkdir()) {
                System.out.println("Main Directory is created!");
            }
            else {
                System.out.println("Failed to create main directory!");
            }
        }
        final File Root_directory = new File(String.valueOf(location) + "TIFtoTIFCompare\\" + username);
        if (!Root_directory.exists()) {
            if (Root_directory.mkdir()) {
                System.out.println("Root Directory is created!");
            }
            else {
                System.out.println("Failed to create root directory!");
            }
        }
        final File Run_directory = new File(String.valueOf(location) + "TIFtoTIFCompare\\" + username + "\\Run_" + dateFormat.format(date));
        if (!Run_directory.exists()) {
            if (Run_directory.mkdir()) {
                System.out.println("Directory is created!");
            }
            else {
                System.out.println("Failed to create directory!");
            }
        }
        final String stringPath = String.valueOf(location) + "TIFtoTIFCompare\\" + username + "\\Run_" + dateFormat.format(date);
        return stringPath;
    }*/
}
