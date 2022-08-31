package org.etsi.mts.tdl.graphical.project;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.ui.business.api.session.UserSession;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.tdlFactory;

public class ProjectWizard extends BasicNewProjectResourceWizard implements INewWizard {

	private IWorkbench workbench;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
	}

	@Override
	public boolean performFinish() {
		
		if (!super.performFinish())
			return false;
		
		IProject project = getNewProject();
		
		IProgressMonitor monitor = new NullProgressMonitor();
		
		try {
			
			addNature(project, "org.eclipse.xtext.ui.shared.xtextNature");
			ModelingProjectManager.INSTANCE.convertToModelingProject(project, monitor);

			IFile file = project.getFile("model.tdl");
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			ResourceSet rs = new ResourceSetImpl();
			Resource r = rs.createResource(uri);
			
			Package pck = tdlFactory.eINSTANCE.createPackage();
			pck.setName("Model");
			r.getContents().add(pck);
			
			String[] typeNames = new String[]{"String", "Boolean", "Integer", "Verdict"};
			SimpleDataType verdictType = null;
			for (String typeName : typeNames) {
				SimpleDataType type = tdlFactory.eINSTANCE.createSimpleDataType();
				type.setName(typeName);
				pck.getPackagedElement().add(type);
				if (typeName.equals("Verdict"))
					verdictType = type;
			}
			
			String[] verdictNames = new String[]{"pass", "fail", "inconclusive"};
			for (String verdictName : verdictNames) {
				SimpleDataInstance verdict = tdlFactory.eINSTANCE.createSimpleDataInstance();
				verdict.setName(verdictName);
				verdict.setDataType(verdictType);
				pck.getPackagedElement().add(verdict);
			}
			
			r.save(null);
			
			URI sessionUri = URI.createPlatformResourceURI(project.getFile("representations.aird").getFullPath().toString(), true);
			Session session = SessionManager.INSTANCE.getSession(sessionUri, monitor);
			UserSession uSession = new UserSession(session);
			uSession.selectViewpoint("org.etsi.mts.tdl");

			uSession.save(monitor);
			session.save(monitor);
			
			return true;
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void addNature( IProject project, String... natureIds) throws CoreException {
		IProjectDescription desc = project.getDescription();
		String[] ids = desc.getNatureIds();

		String[] newIds = Arrays.copyOf(ids, ids.length + natureIds.length);
		System.arraycopy(natureIds, 0, newIds, ids.length, natureIds.length);
		desc.setNatureIds(newIds);
		project.setDescription(desc, null);
	}

}
