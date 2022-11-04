package org.imt.tdl.mutation;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.imt.tdl.utilities.DSLProcessor;
import org.imt.tdl.utilities.PathHelper;

import manager.MutatorAPILauncher;

public class MutantGenerator {

	PathHelper pathHelper;
	Path workspacePath;
	Path mutatorFilePath; 
	Path seedModelPath;
	IProject mutantsProject;
	
	List<String> mutants;
	int numOfMutants;
	boolean noMutantsExists;
	
	//this constructor is used by the test amplification tool
	public MutantGenerator(Path seedModelPath, Path DSLPath) {
		this.seedModelPath = seedModelPath;
		pathHelper = new PathHelper(seedModelPath);
		workspacePath = pathHelper.getWorkspacePath();
		mutatorFilePath = findMutationOperatorFilePath (DSLPath);
	}
	
	public MutantGenerator(Path seedModelPath, String mutatorFilePath) {
		this.seedModelPath = seedModelPath;
		pathHelper = new PathHelper(seedModelPath);
		workspacePath = pathHelper.getWorkspacePath();
		this.mutatorFilePath = Paths.get(mutatorFilePath);
	}
	
	private Path findMutationOperatorFilePath(Path dslPath) {
		DSLProcessor dslProcessor = new DSLProcessor(dslPath);
		String path = dslProcessor.getPath2MutationOperators();
		if (path != null) {
			return pathHelper.getPath(path);
		}
		return null;
	}

	public List<String> findMutants() {
		mutants = new ArrayList<>();
		mutantsProject =  pathHelper.getProject(seedModelPath);
		String mutantsPath = Paths.get(mutantsProject.getLocation().toString(), "mutants").toString();
		File modelFolder = new File(mutantsPath);
		//if there is no mutant, and there is a mutator file, generate mutants
		if (modelFolder.listFiles() == null && mutatorFilePath != null) {
			generateMutants(mutantsPath);
		}
		else {//if there is any mutant, find them
			for (File file : modelFolder.listFiles()) {
				mutantsPathsHelper(file);
			}
		}
		if (mutants.isEmpty()) {
			noMutantsExists = true;
		}
		return mutants;
	}
	
	private void generateMutants(String outputPath) {
		IProject wodelProject = pathHelper.getProject(mutatorFilePath);
		List<String> wodelPrograms = new ArrayList<String>();
		List<List<String>> wodelOperators = new ArrayList<List<String>>();
		
		
		
		String[] mutatorPrograms = null;
		String[][] mutationOperators = null;
		MutatorAPILauncher mutatorAPILauncher = new MutatorAPILauncher(
				null, wodelProject, mutatorPrograms, mutationOperators, seedModelPath.toString(), outputPath);
		try {
			mutatorAPILauncher.run(null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//after generating mutants, finding them in the output folder
		findMutants();
	}

	private void mutantsPathsHelper(File file) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();	
			//get the relative path of the file
			if (!filePath.equals(seedModelPath.toString())){
				filePath = filePath.replace(workspacePath.toString(), "");
				mutants.add(filePath);
				numOfMutants++;
			}
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				mutantsPathsHelper(innerFile);
			}
		}
	}

	public int getNumOfMutants() {
		return numOfMutants;
	}

	public boolean noMutantsExists() {
		return noMutantsExists;
	}

	public List<String> getMutants() {
		return mutants;
	}
	
}
