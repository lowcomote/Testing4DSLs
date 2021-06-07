package org.imt.tdl.codeGenerator.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.imt.TdlTest4pssm.ETest2TDLTest;
import org.imt.tdl.codeGenerator.ui.DSLSelectionWizard;
import org.imt.tdl.codeGenerator.ui.DSLSelectionWizardPage;
import org.imt.tdl.libraryGenerator.TDLCodeGenerator;

public class CodeGenerationHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			DSLSelectionWizard wizard = new DSLSelectionWizard();
			WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.open();
			DSLSelectionWizardPage page = (DSLSelectionWizardPage) wizard.getPage("SelectDSL");
			String tdlProjectPath = page.getSelectedProjectPath().toString();
			String dslFilePath = page.getSelectedDSLFilePath();
			if (wizard.performFinish()) {
				TDLCodeGenerator tdlCodeGenerator = new TDLCodeGenerator(dslFilePath, tdlProjectPath);
				//TODO: The following code (and its related dependency) has to be removed
				//ETest2TDLTest tdl4pssm = new ETest2TDLTest();
				//tdl4pssm.transformTestSuite();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
