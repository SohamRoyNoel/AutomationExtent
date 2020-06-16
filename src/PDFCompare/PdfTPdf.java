package PDFCompare;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.File;
import java.awt.Color;
import com.taguru.utility.PDFUtil;

import CoreFunction.Main;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class PdfTPdf
{
    static String outputDirLocation;
    static JFrame frame;
    
    public static void appendStrToFile(final String fileName, final String str) {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            System.out.println("Exception occoured:" + e);
        }
    }
    
    public static void compare(final String srcfile, final String trgfile, final String output, final String Temp_file1) {
        int src_count = 0;
        int trg_count = 0;
        final String file1 = srcfile;
        final String file2 = trgfile;
        final PDFUtil pdfUtil = new PDFUtil();
        try {
            src_count = pdfUtil.getPageCount(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            trg_count = pdfUtil.getPageCount(file2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (src_count != trg_count) {
            if (src_count > trg_count) {
                final String str2 =  file1 + "," + file2 + ",FAIL," + "Source count is not matching with target count where Source count=" + src_count + " and Target Count=" + trg_count + ". Source count is greater than Target count." + System.lineSeparator();
                appendStrToFile(Temp_file1, str2);
            }
            if (src_count < trg_count) {
                final String str2 =  file1 + "," + file2 + ",FAIL," + "Source count is not matching with target count where Source count=" + src_count + " and Target Count=" + trg_count + ". Target count is greater than Source count." + System.lineSeparator();
                appendStrToFile(Temp_file1, str2);
            }
        }
        else {
            //final Color colorCode = new Color(255, 102, 102);
        	final Color colorCode = new Color(211,33,45);
            final File inputFile1 = new File(srcfile);
            final File inputFile2 = new File(trgfile);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            PdfTPdf.outputDirLocation = output;
            final String filename = srcfile;
            final int n = filename.lastIndexOf("\\");
            final String s = filename.substring(n + 1);
            final int i = s.lastIndexOf(46);
            final String s2 = s.substring(0, i);
           // System.out.println("s2:"+s2+"n:"+n+"s"+s+"i"+i);
            final String path = PdfTPdf.outputDirLocation;
            pdfUtil.highlightPdfDifference(true);
            pdfUtil.highlightPdfDifference(colorCode);
            pdfUtil.setImageDestinationPath(path);
            
            try {
                pdfUtil.comparePdfFilesBinaryMode(file1, file2);
                final File f = new File(String.valueOf(path) + "\\" + s2 + "_1_diff.png");
                final String fileName1 = String.valueOf(path) + "\\PDFFileCompare_" + inputFile1.getName() + "VS" + inputFile2.getName() + timeStamp + ".png";
                f.renameTo(new File(String.valueOf(path) + "\\PDFFileCompare_" + inputFile1.getName() + "VS" + inputFile2.getName() + timeStamp + ".png"));
                final File file3 = new File(fileName1);
                final boolean exists = file3.exists();
                String Status = "";
                String Comment = "";
                if (file3.exists() && file3.isFile()) {
                    Status = "FAIL";
                    Comment = "Check the file: " + fileName1 + " for detailed issue/issues.";
                }
                else {
                    Status = "PASS";
                    Comment = "No Issues.";
                }
                final String load = file1 + "," + file2 + "," + Status + "," + Comment + System.lineSeparator();
                appendStrToFile(Temp_file1, load);
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}
