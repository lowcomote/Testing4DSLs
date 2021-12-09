package org.imt.tdl.faultLocalization;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class SBFLEvaluation {

	public void findMutants() {
		String mutantsProjectName = "";
		String tdlProjectName = "";
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		IProject mutantsProject = null;
		IProject tdlProject = null;
		for (int i=0; i<projects.length; i++) {
			if (projects[i].getName().equals(mutantsProjectName)) {
				mutantsProject = projects[i];
			}else if (projects[i].getName().equals(tdlProjectName)) {
				tdlProject = projects[i];
			}
		}
		getMutantsPaths(mutantsProject);
	}
	
	String workspacePath;
	private List<String> getMutantsPaths(IProject mutantProject) {
		List<String> artifactPaths = new ArrayList<String>();
		File projectFolder = new File(mutantProject.getProjectRelativePath() + "\\model");
		for (File file : projectFolder.listFiles()) {
			mutantsPathsHelper(mutantProject.getName(), file, artifactPaths);
		}
		return artifactPaths;
	}
	
	private void mutantsPathsHelper(String projectName, File file, List<String> artifactPaths) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (this.workspacePath == null) {
				this.workspacePath = filePath.substring(0, filePath.lastIndexOf(projectName)-1);
			}		
			//get the relative path of the file
			filePath = filePath.replace(this.workspacePath, "");
			//get the name of the seed model
			String seedName = filePath.substring(1);
			seedName = filePath.substring(filePath.indexOf("\\model\\") + "\\model\\".length(), filePath.length());
			if (!file.getName().equals(seedName)) {//file is not the seed model
				artifactPaths.add(filePath);
			}			
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				mutantsPathsHelper(projectName, innerFile, artifactPaths);
			}
		}
	}
}
