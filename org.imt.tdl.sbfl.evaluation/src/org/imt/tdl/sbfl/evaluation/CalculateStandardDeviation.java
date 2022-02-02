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
		this.sum = 0;
		this.EXAMScores.forEach(s -> this.sum += s);
		BigDecimal bd = new BigDecimal(this.sum).setScale(3, RoundingMode.HALF_UP);
		this.sum = bd.doubleValue();
	}
	
	public void calculateMean() {
		this.calculateSum();
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
		this.standardDeviation = 0;
		for (double score:this.EXAMScores) {
			this.standardDeviation += Math.pow((score - this.mean), 2);
		}
		this.standardDeviation /= this.EXAMScores.size();
		this.standardDeviation = Math.sqrt(this.standardDeviation);
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
		return this.sum;
	}

	public double getMean() {
		return this.mean;
	}
	
	public double getMedian() {
		return this.median;
	}
	
	public double getStandardDeviation() {
		return this.standardDeviation;
	}
}
