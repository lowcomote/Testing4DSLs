package org.imt.tdl.mutation;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.imt.k3tdl.utilities.PathHelper;

public class MutantGenerator {

	PathHelper pathHelper;
	Path workspacePath;
	String mutatorFilePath; 
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
		this.mutatorFilePath = mutatorFilePath;
	}
	
	private String findMutationOperatorFilePath(Path dslPath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslPath.toString()), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("mutationOperators") != null) {
			return dsl.getEntry("mutationOperators").getValue().replaceFirst("resource", "plugin");
		}
		return null;
	}

	public List<String> findMutants() {
		mutants = new ArrayList<>();
		String projectName = seedModelPath.getParent().toString().substring(1);
		mutantsProject =  ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		File modelFolder = new File(mutantsProject.getLocation() + File.separator + "mutants");
		if (modelFolder.listFiles() == null) {
			if (mutatorFilePath != null) {
				//TODO: generate mutants if nothing exists
			}
			noMutantsExists = true;
		}else {
			for (File file : modelFolder.listFiles()) {
				mutantsPathsHelper(projectName, file);
			}
		}
		return mutants;
	}
	
	private void mutantsPathsHelper(String projectName, File file) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (workspacePath == null) {
				String path = filePath.substring(0, filePath.lastIndexOf(projectName)-1);
				workspacePath = Paths.get(path);
			}		
			//get the relative path of the file
			if (!filePath.equals(seedModelPath.toString())){
				filePath = filePath.replace(workspacePath.toString(), "");
				mutants.add(filePath);
				numOfMutants++;
			}
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				mutantsPathsHelper(projectName, innerFile);
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
