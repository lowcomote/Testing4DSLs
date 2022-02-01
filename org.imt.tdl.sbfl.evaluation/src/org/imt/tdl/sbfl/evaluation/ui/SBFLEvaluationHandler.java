package org.imt.tdl.sbfl.evaluation.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.imt.tdl.sbfl.evaluation.SBFLEvaluation;

public class SBFLEvaluationHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ProjectSelectionWizard wizard = new ProjectSelectionWizard();
			WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.open();
			ProjectSelectionWizardPage page = (ProjectSelectionWizardPage) wizard.getPage("selectProject");
			if (wizard.performFinish()) {
				Job job = new Job("SBFLEvaluation") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						SBFLEvaluation evaluation = new SBFLEvaluation(page.getMutantProject(), page.getTestProject());
						evaluation.evaluateSBFLTechniques();
						return null;
					}
				};
				job.schedule();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

