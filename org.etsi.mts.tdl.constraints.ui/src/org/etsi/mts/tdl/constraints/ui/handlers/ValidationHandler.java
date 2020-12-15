package org.etsi.mts.tdl.constraints.ui.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.etsi.mts.tdl.constraints.evl.Validator;

import com.google.inject.Inject;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ValidationHandler extends AbstractHandler {
	 @Inject
	  IResourceSetProvider resourceSetProvider;

	 private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public ValidationHandler() {
	}
	
	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		IEditorInput input = HandlerUtil.getActiveEditorInput(event);
		IFile file = null;
		IProject project = null;
		if (input != null && input instanceof FileEditorInput) {
			file = ((FileEditorInput) input).getFile();
			project = file.getProject();
		} else if (selection !=null && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IFile) {
				file = (IFile) firstElement;
				project = file.getProject();
			}
		}
		
		if (file !=null) {
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			ResourceSet rs = new ResourceSetImpl();
			Resource r = rs.getResource(uri, true);
			Validator validator = new Validator();
			List<UnsatisfiedConstraint> violations = validator.validate(r);
			String messages = "";
			for (UnsatisfiedConstraint v : violations) {
				messages += v.getMessage()+"\n";
			}
			
			if (violations.size() == 0) {
				messages = "No violations!";
			}
			MessageDialog.openInformation(
					Display.getDefault().getActiveShell(),
//					window.getShell(),
					"Validation Results",
					messages);
		}
		return null;
	}
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
