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
		this.EXAMScores.forEach(s -> this.sum += s);
		BigDecimal bd = new BigDecimal(this.sum).setScale(3, RoundingMode.HALF_UP);
		this.sum = bd.doubleValue();
	}
	
	public void calculateMean() {
		if (this.sum == 0) {
			this.calculateSum();
		}
		this.mean = this.sum / this.EXAMScores.size();
		BigDecimal bd = new BigDecimal(this.mean).setScale(3, RoundingMode.HALF_UP);
		this.mean = bd.doubleValue();
	}
	
	public void calculateMedian() {
		Collections.sort(this.EXAMScores, Collections.reverseOrder());
		this.median = this.EXAMScores.get(this.EXAMScores.size()/2);
		BigDecimal bd = new BigDecimal(this.median).setScale(3, RoundingMode.HALF_UP);
		this.median = bd.doubleValue();
	}
	
	public void calculateSD() {
		if (this.mean == 0) {
			this.calculateMean();
		}
		for (double score:this.EXAMScores) {
			standardDeviation += Math.pow((score - this.mean), 2);
		}
		standardDeviation /= this.EXAMScores.size();
		standardDeviation = Math.sqrt(standardDeviation);
		BigDecimal bd = new BigDecimal(this.standardDeviation).setScale(3, RoundingMode.HALF_UP);
		this.standardDeviation = bd.doubleValue();
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
