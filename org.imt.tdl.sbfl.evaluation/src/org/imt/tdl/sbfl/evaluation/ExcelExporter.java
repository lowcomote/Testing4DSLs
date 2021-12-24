package org.imt.tdl.sbfl.evaluation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.imt.tdl.faultLocalization.SBFLMeasures;

public class ExcelExporter {

	public void saveResult2Excel(String modelName, HashMap<String, SBFLMeasures> mutant_SBFLMeasures4FaultyObject) {
		String[] titles = {"SBFL Technique", "Susp", "Rank", "BC", "AC", "WC"};
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (Map.Entry<String, SBFLMeasures> entry:mutant_SBFLMeasures4FaultyObject.entrySet()) {//create one sheet per mutant
			XSSFSheet sheet = workbook.createSheet((String)entry.getKey());
			//create header row
			XSSFRow headerRow = sheet.createRow(0);
	        for (int i = 0; i < titles.length; i++) {
	        	XSSFCell headerCell = headerRow.createCell(i);
	            headerCell.setCellValue(titles[i]);
	        }
	        
	        SBFLMeasures SBFLMeasures4FaultyObject = (SBFLMeasures) entry.getValue();
			int rowNum = 1;
			for (Map.Entry<String, Double> suspEntry:SBFLMeasures4FaultyObject.getSusp().entrySet()) {
				XSSFRow row4technique = sheet.createRow(rowNum++);
				String SBFLTechnique = (String) suspEntry.getKey();
				row4technique.createCell(0).setCellValue(SBFLTechnique);
				row4technique.createCell(1).setCellValue((double) suspEntry.getValue());
				row4technique.createCell(2).setCellValue((int) SBFLMeasures4FaultyObject.getRank().get(SBFLTechnique));
				row4technique.createCell(3).setCellValue((double) SBFLMeasures4FaultyObject.getBestEXAMScore().get(SBFLTechnique));
				row4technique.createCell(4).setCellValue((double) SBFLMeasures4FaultyObject.getAverageEXAMScore().get(SBFLTechnique));
				row4technique.createCell(5).setCellValue((double) SBFLMeasures4FaultyObject.getWorseEXAMScore().get(SBFLTechnique));
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\evaluationData\\" + modelName + ".xlsx");
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
