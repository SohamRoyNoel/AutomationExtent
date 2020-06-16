package Helper;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class DeleteRowExcel {


	   public static void deleteRow(String Filetest) throws FilloException, FileNotFoundException, IOException, InvalidFormatException {

	      //  String file = "C:\\Users\\402025\\Desktop\\Test.xlsx";
	        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(Filetest)));
	       
	        int srNo=3;
	        Sheet sheet = wb.getSheetAt(0);
	        int delRowNum = deleteRowNumber(sheet, srNo);
	        removeRow(sheet, delRowNum);
	        FileOutputStream out = new FileOutputStream(Filetest);
	       wb.write(out);
	        out.flush();
	        out.close();

	    }

	    public static void removeRow(Sheet sheet, int rowIndex) {
	        int lastRowNum = sheet.getLastRowNum();
	        if (rowIndex >= 0 && rowIndex < lastRowNum) {
	            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
	        }
	        if (rowIndex == lastRowNum) {
	            Row removingRow = sheet.getRow(rowIndex);
	            if (removingRow != null) {
	                sheet.removeRow(removingRow);
	            }
	        }
	    }

	    public static int deleteRowNumber(Sheet sheet, int srNo) {
	        int lastRowNum = sheet.getLastRowNum();
	        int rowIndex = 0;
	        Cell cell;
	        for (Row r : sheet) {
	            if (r.getRowNum() == 0)
	                continue;
	            cell = r.getCell(0);
	            if (r.getRowNum() == lastRowNum) {
	                break;
	            }
	            if (cell.getNumericCellValue()==srNo && r.getRowNum()!=0) {
	                rowIndex = r.getRowNum();
	            }
	        }
	        System.out.println(rowIndex);
	        return rowIndex;
	    }
	}
