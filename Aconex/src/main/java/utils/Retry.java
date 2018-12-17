package utils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
 
/**
 * Created by ONUR on 17.12.2016.
 */
public class Retry implements IRetryAnalyzer {
 
    private int count = 0;
    private static int maxTry = 3;
 
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      
            if (count < maxTry) {                            
                count++;                                     
                iTestResult.setStatus(ITestResult.FAILURE);  
                return true;                                
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);  
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      
        }
        return false;
    }
 
}
