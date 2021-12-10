package org.imt.tdl.libraryGenerator.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.imt.tdl.libraryGenerator.TDLCodeGenerator;
import org.imt.tdl.libraryGenerator.ui.DSLSelectionWizard;
import org.imt.tdl.libraryGenerator.ui.DSLSelectionWizardPage;

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
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
