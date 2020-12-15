package org.etsi.mts.tdl.constraints.evl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.tools.MathTool;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlFixer;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.profiling.ProfilerTargetSummary;
import org.eclipse.epsilon.profiling.ProfilingExecutionListener;
import org.etsi.mts.tdl.tdlPackage;
import org.osgi.framework.Bundle;


@SuppressWarnings("unused")
public class Validator {
	
	public List<UnsatisfiedConstraint> validate(Resource r) {
		List<UnsatisfiedConstraint> violations = new ArrayList<>(); 
		try {
			String source = "epsilon/constraints/tdl.evl";
			//TODO: also for TO?

		    Bundle bundle = Platform.getBundle("org.etsi.mts.tdl.constraints");
			URL url = bundle.getEntry(source);

			EvlModule module = new EvlModule();
			module.parse(url.toURI());

			//TODO: integrate error reporting
			if (module.getParseProblems().size() > 0) {
				System.err.println("Parse errors occured...");
				for (ParseProblem problem : module.getParseProblems()) {
					System.err.println(problem.toString());
				}
			}

			IModel tdlModel = getTDLModel(r, true, false);
			tdlModel.load();
			module.getContext().getModelRepository().addModel(tdlModel);
			
			//execute
			module.execute();
			
			EvlModule m = (EvlModule) module;
			violations.addAll(m.getContext().getUnsatisfiedConstraints());
			for (UnsatisfiedConstraint constraint : m.getContext().getUnsatisfiedConstraints()) {
				System.out.println("  " + constraint.getMessage());
			}
			
			tdlModel.dispose();
			//TODO: Needed? New API does not provide it
			//module.reset();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return violations;
	}

	//TODO: extract to shared library
	public IModel getTDLModel(Resource resource, boolean read, boolean write) throws Exception {
		EmfModel model;
		model = new InMemoryEmfModel("TDL", resource, tdlPackage.eINSTANCE);
		model.setStoredOnDisposal(write);
		model.setReadOnLoad(read);
		model.setCachingEnabled(true);
		return model;
	}

}
