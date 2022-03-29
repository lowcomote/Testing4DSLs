package org.imt.tdl.amplification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MutantPathHelper {

	String workspacePath;
	private void artifactPathsHelper(File file, List<String> artifactPaths) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			artifactPaths.add(file.getPath());		
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				artifactPathsHelper(innerFile, artifactPaths);
			}
		}
	}

	public List<String> getMutantPaths() {
		String folderPath = "wodelOutput";
		List<String> mutantPaths = new ArrayList<String>();
		File mutantsFolder = new File(folderPath);
		for (File file : mutantsFolder.listFiles()) {
			artifactPathsHelper(file, mutantPaths);
		}
		return mutantPaths;
	}
}
