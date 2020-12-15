package org.etsi.mts.tdl.tools.rt.ui.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.util.Files;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class TranslationHandler extends AbstractHandler {
	@Inject Provider<ResourceSet> rsp;
	@Inject Injector injector;

	/**
	 * The constructor.
	 */
	public TranslationHandler() {
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
			Package p = (Package) r.getContents().get(0);
			if (file.getFullPath().toString().endsWith(".tdl")) {
				String s = "";
				URI targetURI = URI.createURI(uri.toString()+".tdlan2");
				
				Injector injector = Guice.createInjector(new org.etsi.mts.tdl.TDLan2RuntimeModule());
				XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
				Resource tr = resourceSet.createResource(targetURI);
				boolean method1 = true;
				//method 1
				if (method1) {
					tr.getContents().addAll(EcoreUtil.copyAll(r.getContents()));
					try {
						tr.save(null);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Serializer serializer = injector.getInstance(Serializer.class);  
					tr.getContents().add(p);
					try {  
						s += serializer.serialize(p);  
					} catch (Exception ex) { // fall back:  
						System.out.println("Model could not be serialized"); 
						ex.printStackTrace();
					}  
					System.out.println(s);
					
					Files.writeStringIntoFile(file.getRawLocation().toOSString()+".tdlan2", s);
				}

			} else if (file.getFullPath().toString().endsWith(".tdlan2")) {
				String extension = "tdl";
				Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			    Map<String, Object> m = reg.getExtensionToFactoryMap();
				tdlResourceFactoryImpl factory = new tdlResourceFactoryImpl();
				m.put(extension, factory);
				
				Resource outputResource = factory.createResource(URI.createURI(r.getURI().toString()+".tdl"));
			    outputResource.getContents().addAll(EcoreUtil.copyAll(r.getContents()));
			    Map options = new HashMap<>();
			    options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
			    try {
			      outputResource.save(options);
			    } catch (IOException e) {
			      e.printStackTrace();
			    }

			} else if (file.getFullPath().toString().endsWith(".tplan2")) {
				String extension = "tdl";
				Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			    Map<String, Object> m = reg.getExtensionToFactoryMap();
				tdlResourceFactoryImpl factory = new tdlResourceFactoryImpl();
				m.put(extension, factory);
				
				Resource outputResource = factory.createResource(URI.createURI(r.getURI().toString()+".tdl"));
			    outputResource.getContents().addAll(EcoreUtil.copyAll(r.getContents()));
			    Map options = new HashMap<>();
			    options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
			    try {
			      outputResource.save(options);
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
				
			} else {
				//TODO: handle unknown resource type
			}
		}
		return null;
	}
}
