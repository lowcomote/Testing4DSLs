package org.imt.tdl.sbfl.evaluation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.imt.tdl.faultLocalization.SBFLMeasures;

public class ExcelExporter {

	private HashMap<String, SBFLMeasures> mutant_SBFLMeasures4FaultyObject;
	private List<String> SBFLTechniques = new ArrayList<>();
	private XSSFWorkbook workbook;
	private String seedModelName;
	
	public ExcelExporter(HashMap<String, SBFLMeasures> mutant_SBFLMeasures4FaultyObject) {
		workbook = new XSSFWorkbook();
		this.mutant_SBFLMeasures4FaultyObject = mutant_SBFLMeasures4FaultyObject;
		SBFLMeasures firstMeasure = mutant_SBFLMeasures4FaultyObject.values().stream().findFirst().get();
       	firstMeasure.getRank().keySet().stream().forEach(t -> SBFLTechniques.add(t));
	}
	
	public void saveResults2Excelfile() throws FileNotFoundException{
		exportMutantResult2Excel();
		exportOverallResult2Excel();
		String filePath = "C:\\labtop\\GitHub\\xtdl\\org.imt.tdl.sbfl.evaluation\\evaluationData\\" + seedModelName + ".xlsx";
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Results saved in an Excel file: " + filePath);
	}
	
	@SuppressWarnings("resource")
	private void exportMutantResult2Excel() {
		String[] titles = {"SBFL Technique", "Susp", "Rank", "BC", "AC", "WC"};
		for (Map.Entry<String, SBFLMeasures> entry:mutant_SBFLMeasures4FaultyObject.entrySet()) {
			//create one sheet per mutant
			String mutantPath = entry.getKey();
			String mutantName = mutantPath.substring(mutantPath.indexOf("mutants\\") + 8, mutantPath.indexOf(".model")).replaceAll("\\\\", "\\."); 
			XSSFSheet sheet = workbook.createSheet(mutantName);
			//create header row
			XSSFRow headerRow = sheet.createRow(0);
	        for (int i = 0; i < titles.length; i++) {
	        	XSSFCell headerCell = headerRow.createCell(i);
	            headerCell.setCellValue(titles[i]);
	        }
	        //create one row per technique with the following columns: susp, rank, BC, AC, WC
	        SBFLMeasures SBFLMeasures4FaultyObject = (SBFLMeasures) entry.getValue();
			int rowNum = 1;
			for (String SBFLTechnique:SBFLTechniques) {
				XSSFRow row4technique = sheet.createRow(rowNum++);
				row4technique.createCell(0).setCellValue(SBFLTechnique);
				row4technique.createCell(1).setCellValue((double) SBFLMeasures4FaultyObject.getSusp().get(SBFLTechnique));
				row4technique.createCell(2).setCellValue((int) SBFLMeasures4FaultyObject.getRank().get(SBFLTechnique));
				row4technique.createCell(3).setCellValue((double) SBFLMeasures4FaultyObject.getBestEXAMScore().get(SBFLTechnique));
				row4technique.createCell(4).setCellValue((double) SBFLMeasures4FaultyObject.getAverageEXAMScore().get(SBFLTechnique));
				row4technique.createCell(5).setCellValue((double) SBFLMeasures4FaultyObject.getWorseEXAMScore().get(SBFLTechnique));
			}
		}
	}
	
	private void exportOverallResult2Excel() {
		//Calculate the overall result: median, mean, and standard deviation for EXAM scores of all mutants
		String[] overallTitles = {"SBFL Technique", "BC-Median", "BC-Mean", "BC-SD", "AC-Median", "AC-Mean", "AC-SD","WC-Median", "WC-Mean", "WC-SD"};
		XSSFSheet sheet = workbook.createSheet(seedModelName + "_overall");
		//create header row
		XSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < overallTitles.length; i++) {
        	XSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(overallTitles[i]);
        }
        //get the scores of all mutants and keep them for calculation
        HashMap<String, List<Double>> allBC = new HashMap<>();
        HashMap<String, List<Double>> allAC = new HashMap<>();
        HashMap<String, List<Double>> allWC = new HashMap<>();
        this.SBFLTechniques.stream().forEach(SBFLTechnique -> {
			allBC.put(SBFLTechnique, new ArrayList<>());
			allAC.put(SBFLTechnique, new ArrayList<>());
			allWC.put(SBFLTechnique, new ArrayList<>());
		});
		for (SBFLMeasures measure4mutant:mutant_SBFLMeasures4FaultyObject.values()) {
			SBFLTechniques.stream().forEach(SBFLTechnique -> {
				allBC.get(SBFLTechnique).add(measure4mutant.getBestEXAMScore().get(SBFLTechnique));
				allAC.get(SBFLTechnique).add(measure4mutant.getAverageEXAMScore().get(SBFLTechnique));
				allWC.get(SBFLTechnique).add(measure4mutant.getWorseEXAMScore().get(SBFLTechnique));
			});
		}
		//calculate mean, median, standard deviation for BC, AC, WC
		CalculateStandardDeviation sdCalculator = new CalculateStandardDeviation();
		int rowNum = 1;
		for (String SBFLTechnique:SBFLTechniques) {
			XSSFRow row4technique = sheet.createRow(rowNum++);
			row4technique.createCell(0).setCellValue((String) SBFLTechnique);
			
			//calculate mean, median, standard deviation for BC
			sdCalculator.setEXAMScores(allBC.get(SBFLTechnique));
			sdCalculator.calculateMedian();
			sdCalculator.calculateMean();
			sdCalculator.calculateSD();
			row4technique.createCell(1).setCellValue(sdCalculator.getMedian());//BC-Median
			row4technique.createCell(2).setCellValue(sdCalculator.getMean());//BC-Mean
			row4technique.createCell(3).setCellValue(sdCalculator.getStandardDeviation());//BC-SD
			
			//calculate mean, median, standard deviation for AC
			sdCalculator.setEXAMScores(allAC.get(SBFLTechnique));
			sdCalculator.calculateMedian();
			sdCalculator.calculateMean();
			sdCalculator.calculateSD();
			row4technique.createCell(4).setCellValue(sdCalculator.getMedian());//AC-Median
			row4technique.createCell(5).setCellValue(sdCalculator.getMean());//AC-Mean
			row4technique.createCell(6).setCellValue(sdCalculator.getStandardDeviation());//AC-SD
			
			//calculate mean, median, standard deviation for WC
			sdCalculator.setEXAMScores(allWC.get(SBFLTechnique));
			sdCalculator.calculateMedian();
			sdCalculator.calculateMean();
			sdCalculator.calculateSD();
			row4technique.createCell(7).setCellValue(sdCalculator.getMedian());//WC-Median
			row4technique.createCell(8).setCellValue(sdCalculator.getMean());//WC-Mean
			row4technique.createCell(9).setCellValue(sdCalculator.getStandardDeviation());//WC-SD
		}
	}

	public String getSeedModelName() {
		return seedModelName;
	}

	public void setSeedModelName(String seedModelName) {
		this.seedModelName = seedModelName;
	}
	
}
