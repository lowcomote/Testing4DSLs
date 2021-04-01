package org.imt.tdl.testResult;

public class TestResultUtil {
	
	private static TestResultUtil instance = new TestResultUtil();
	private TDLTestPackageResult testResult;

	   //make the constructor private so that this class cannot be
	   //instantiated
	   private TestResultUtil(){}

	   //Get the only object available
	   public static TestResultUtil getInstance(){
	      return instance;
	   }
	   public TDLTestPackageResult getTestPackageResult() {
		   return instance.testResult;
	   }
	   public void setTestPackageResult(TDLTestPackageResult result) {
		   instance.testResult = result;
	   }

}
