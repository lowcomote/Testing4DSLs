package org.imt.tdl.codeGenerator.handler;

import org.eclipse.core.commands.AbstractHandler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.imt.tdl.codeGenerator.ui.DSLSelectionWizard;
import org.imt.tdl.codeGenerator.ui.DSLSelectionWizardPage;
import org.imt.tdl.libraryGenerator.TDLCodeGenerator;

public class CodeGenerationHandler extends AbstractHandler {
	
	private IWorkbenchWindow window;
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
