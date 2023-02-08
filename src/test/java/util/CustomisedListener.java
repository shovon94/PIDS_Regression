package util;




import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;


public class CustomisedListener implements ITestListener {
	    
    // ...
    public void onFinish(ITestContext testContext) {
    	
    	Reporter.log("PASSED TEST CASES");
        testContext.getPassedTests().getAllResults()
          .forEach(result -> {Reporter.log(result.getName());});
        
        Reporter.log("FAILED TEST CASES");
        testContext.getFailedTests().getAllResults()
          .forEach(result -> {Reporter.log(result.getName());});
        
        Reporter.log(
          "Test completed on: " + testContext.getEndDate().toString());
    }

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}  
 
    //...
}