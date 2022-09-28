package org.etsi.mts.tdl.graphical.extensions;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.etsi.mts.tdl.ActionReference;
import org.etsi.mts.tdl.ComponentInstanceBinding;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.ParameterBinding;
import org.etsi.mts.tdl.TestDescriptionReference;
import org.etsi.mts.tdl.graphical.labels.DataStandaloneSetup;

import com.google.inject.Injector;

public class DataUseLabelProvider {

	public String separator(EObject o, List<String> c, String s) {
		String output = "";
		for (String e : c) {
			output+=s+e;
		}
		if (c.size()>0) {
			return output.substring(s.length());
		}
		return output;
	}

	@SuppressWarnings("restriction")
	public String serialise(EObject o, ActionReference u, String separator) {
		Injector injector = new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
		String s = "";
		Serializer serializer = injector.getInstance(Serializer.class); 

		try {
			for (ParameterBinding b : u.getArgument()) {
				s += separator + serialise(u, b);
			}
			if (s.length()>0) {
				s=s.substring(separator.length());
				s="("+s+")";
			}
		} catch (Exception ex) { // fall back:  
			System.out.println("Object could not be serialized"); 
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}  
		
		return s;
	}

	
	@SuppressWarnings("restriction")
	public String serialise(EObject o, TestDescriptionReference u, String type, String separator) {
		Injector injector = new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
		String s = "";
		Serializer serializer = injector.getInstance(Serializer.class); 

		try {  
			if (u instanceof TestDescriptionReference) {
				if (type.equals("bindings")) {
					for (ComponentInstanceBinding b : ((TestDescriptionReference)u).getComponentInstanceBinding()) {
						// DONE: grammar doesn't actually support this meta-class -> part of the removed rules -> rules re-added, context resolution added
						s+=separator+serializer.serialize(b);
					}
					s = s.replaceAll("\\s+", " ").replaceAll("\t+", " ");
					if (s.length()>0) {
						s=s.substring(separator.length());
					}
				} else if (type.equals("parameters")) {
					for (ParameterBinding b : ((TestDescriptionReference)u).getArgument()) {
						s+=separator+serialise(u, b);
					}
					if (s.length()>0) {
						s=s.substring(separator.length());
						s="("+s+")";
					}
					
				} else {
					s += "Unsupported type argument";
				}
			}
		} catch (Exception ex) { // fall back:  
			System.out.println("Object could not be serialized"); 
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}  
		
		return s;
	}

	@SuppressWarnings("restriction")
	public String serialise(EObject o, ParameterBinding p) {
		Injector injector = new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
		String s = "";
		ISerializer serializer = injector.getInstance(ISerializer.class);
		
		XtextResource xr = null;
		try {
			
			if (!(p.eResource() instanceof XtextResource)) {
				URI uri = p.eResource().getURI();
				p  = EcoreUtil.copy(p);
				xr = createVirtualXtextResource(uri, p);
			}
			
			SaveOptions options = SaveOptions.newBuilder().noValidation().getOptions();
			s += p.getParameter().getName();
			s += " = ";
			s += serializer.serialize(p.getDataUse(), options);
		} catch (Exception ex) { // fall back:  
			System.out.println("Object could not be serialized"); 
			System.err.println(ex);
			ex.printStackTrace();
			
		} finally {
			if (xr != null)
				xr.unload();
		}
		
		return s;
	}

	
	@SuppressWarnings("restriction")
	public String serialise(EObject o, DataUse u) {
		Injector injector = new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
		String s = "";
		ISerializer serializer = injector.getInstance(ISerializer.class);
		
		XtextResource xr = null;
		try {
			if (!(u.eResource() instanceof XtextResource)) {
				URI uri = u.eResource().getURI();
				u  = EcoreUtil.copy(u);
				xr = createVirtualXtextResource(uri, u);
			}
			
			SaveOptions options = SaveOptions.newBuilder().noValidation().getOptions();
			s += serializer.serialize((DataUse)u, options);
		} catch (Exception ex) { // fall back:  
			System.out.println("Object could not be serialized"); 
			System.err.println(ex);
			ex.printStackTrace();
			
		} finally {
			if (xr != null)
				xr.unload();
		}
		
		return s;
	}
	
	public String getDataUse(EObject o, DataUse u, String s) {
		String output = ""+u.eClass().getName();
		if (u instanceof DataInstanceUse) {
			DataInstanceUse d = (DataInstanceUse) u;
			output = d.getDataInstance().getName();
			if (d.getArgument().size()>0) {
				output+="(";
				for (ParameterBinding p : d.getArgument()) {
					output+=p.getParameter().getName()+" := ";
					getDataUse(d, p.getDataUse(), s);
				}
				output+=")";
			}
		} else {
			output+=" (not yet implemented)";
		}
		//output += 
		return output;
	}


	private XtextResource createVirtualXtextResource(URI uri, EObject semanticElement) throws IOException {
		Injector xtextInjector = new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
		IResourceFactory resourceFactory = xtextInjector.getInstance(IResourceFactory.class);
		XtextResourceSet rs = xtextInjector.getInstance(XtextResourceSet.class);
		rs.setClasspathURIContext(getClass());
		// Create virtual resource
		XtextResource xtextVirtualResource = (XtextResource) resourceFactory
				.createResource(URI.createURI(uri.toString()));
		rs.getResources().add(xtextVirtualResource);

		// Populate virtual resource with the given semantic element to edit
		xtextVirtualResource.getContents().add(semanticElement);

		// Save and reparse in order to initialize virtual Xtext resource
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		xtextVirtualResource.save(out, Collections.emptyMap());
//		xtextVirtualResource.reparse(new String(out.toByteArray()));

		return xtextVirtualResource;
	}
}
