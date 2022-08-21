package org.imt.tdl.libraryGenerator.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class DSLSelectionWizard extends Wizard implements INewWizard{
	
	private DSLSelectionWizardPage selectDSL;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	public DSLSelectionWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("TDL Code Generator");
	}
	@Override
	public void addPages() {
		selectDSL = new DSLSelectionWizardPage("SelectDSL");
		addPage(selectDSL);
	}
	@Override
	public boolean performFinish() {
		return true;
	}
	@Override
	public boolean performCancel() {
		return true;
	}
}
