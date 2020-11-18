package org.imt.tdl.defaultPackage.generator;

public class Test {

	public static void main(String[] args) {
		TdlPackageGenerator tpg = new TdlPackageGenerator();
		tpg.generateCommonPackage();
		tpg.savePackageIntoFile("FSM");
	}

}
