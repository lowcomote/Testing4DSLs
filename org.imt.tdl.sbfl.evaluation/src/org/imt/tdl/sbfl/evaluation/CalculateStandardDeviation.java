package org.imt.tdl.sbfl.evaluation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class CalculateStandardDeviation {

	private List<Double> EXAMScores;
	private double sum;
	private double mean;
	private double median;
	private double standardDeviation;
	
	public void calculateSum() {
		sum = 0;
		EXAMScores.forEach(s -> sum += s);
		BigDecimal bd = new BigDecimal(sum).setScale(3, RoundingMode.HALF_UP);
		sum = bd.doubleValue();
	}
	
	public void calculateMean() {
		calculateSum();
		mean = sum / EXAMScores.size();
		BigDecimal bd = new BigDecimal(mean).setScale(3, RoundingMode.HALF_UP);
		mean = bd.doubleValue();
	}
	
	public void calculateMedian() {
		Collections.sort(EXAMScores, Collections.reverseOrder());
		median = EXAMScores.get(EXAMScores.size()/2);
		BigDecimal bd = new BigDecimal(median).setScale(3, RoundingMode.HALF_UP);
		median = bd.doubleValue();
	}
	
	public void calculateSD() {
		if (mean == 0) {
			calculateMean();
		}
		standardDeviation = 0;
		for (double score:EXAMScores) {
			standardDeviation += Math.pow((score - mean), 2);
		}
		standardDeviation /= EXAMScores.size();
		standardDeviation = Math.sqrt(standardDeviation);
		BigDecimal bd = new BigDecimal(standardDeviation).setScale(3, RoundingMode.HALF_UP);
		standardDeviation = bd.doubleValue();
	}
	
	public List<Double> getEXAMScores() {
		return EXAMScores;
	}

	public void setEXAMScores(List<Double> eXAMScores) {
		EXAMScores = eXAMScores;
	}
	
	public double getSum() {
		return sum;
	}

	public double getMean() {
		return mean;
	}
	
	public double getMedian() {
		return median;
	}
	
	public double getStandardDeviation() {
		return standardDeviation;
	}
}
