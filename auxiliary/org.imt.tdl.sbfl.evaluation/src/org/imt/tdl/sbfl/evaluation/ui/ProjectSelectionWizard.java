package org.imt.tdl.sbfl.evaluation.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class ProjectSelectionWizard extends Wizard implements INewWizard{
	private ProjectSelectionWizardPage selectProject;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	public ProjectSelectionWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Evaluating SBFL Techniques");
	}
	@Override
	public void addPages() {
		selectProject = new ProjectSelectionWizardPage("selectProject");
		addPage(selectProject);
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

