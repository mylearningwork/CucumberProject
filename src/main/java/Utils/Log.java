package Utils;

import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

public class Log extends TestBase{

	
	public static void info(String message) {  
		
		//System.out.println(driver);
		
		//eTest.log(LogStatus.INFO, message);//For extentTest HTML report
		
		logger.info("Message: " + message);
	    
	    Reporter.log(message); 
	}

	public static void error(String message) {  
		
		//eTest.log(LogStatus.ERROR, message);//For extentTest HTML report
		
		logger.error("Message: " + message);
		logger.error(message);
	    
	    Reporter.log(message);

	}
	public static void pass(String message) {  
		
	//	eTest.log(LogStatus.PASS, message);//For extentTest HTML report
		
		logger.info("Message: " + message);
	    
	    Reporter.log(message);

	}
	public static void fail(String message) {  
		
		//eTest.log(LogStatus.FAIL, message);//For extentTest HTML report
		
		logger.error("Message: " + message);
	    
	    Reporter.log(message);

	}
	
	public static void skip(String message) {  
		
		//eTest.log(LogStatus.SKIP, message);//For extentTest HTML report
		
		logger.info("Message: " + message);
	    
	    Reporter.log(message);

	}
	
	public static void fatal(String message) {  
		
	//	eTest.log(LogStatus.FATAL, message);//For extentTest HTML report
		
		logger.error("Message: " + message);
	    
	    Reporter.log(message);

	}
	
}
