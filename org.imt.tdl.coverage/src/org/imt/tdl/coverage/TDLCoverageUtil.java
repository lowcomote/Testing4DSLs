package org.imt.tdl.coverage;

public class TDLCoverageUtil {
	
	private static TDLCoverageUtil instance = new TDLCoverageUtil();
	private TDLTestSuiteCoverage testSuiteCoverage;

	   //make the constructor private so that this class cannot be
	   //instantiated
	   private TDLCoverageUtil(){}

	   //Get the only object available
	   public static TDLCoverageUtil getInstance(){
	      return instance;
	   }
	   public TDLTestSuiteCoverage getTestSuiteCoverage() {
		   return instance.testSuiteCoverage;
	   }
	   public void setTestSuiteCoverage(TDLTestSuiteCoverage coverage) {
		   instance.testSuiteCoverage = coverage;
	   }
}
