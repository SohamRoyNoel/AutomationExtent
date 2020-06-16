package Helper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class WebElementDriverRunner {


    public static void ExecuteRun(String chromeDrivePath,LinkedHashMap<String, String> arr1) throws InterruptedException {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.chrome.driver",chromeDrivePath);
		WebDriver driver = new ChromeDriver();
    	String baseUrl = "";
    	WebElement ElementClicker=null;
    	try{
    	Set set = arr1.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) 
        {
        	
           Map.Entry mentry = (Map.Entry)iterator.next();
         //  System.out.println("PathKey:"+mentry.getKey() +" "+"PathValue:"+mentry.getValue());
           if(mentry.getKey().equals("Parent URL"))
           {
        	   baseUrl=(String) mentry.getValue();
        	  // System.out.println(baseUrl);
        	   driver.get(baseUrl);
        	   driver.manage().window().maximize();
        	   Thread.sleep(2000);
           }
           else if (mentry.getKey().equals("Compensation Review - Externals")){
        	      
	        	   ElementClicker=driver.findElement(By.xpath((String) mentry.getValue()));
	        	  ElementClicker.click();
	        	  Thread.sleep(5000);
	        	  // System.out.println(XpathValue);
	        	   }
           else {
        	   ElementClicker=driver.findElement(By.xpath((String) mentry.getValue()));
	        	  ElementClicker.click();
	        	  Thread.sleep(3000);
           }
     }
    	}
        //close Fire fox
        finally {
        driver.close();
        }
      
    }

}
