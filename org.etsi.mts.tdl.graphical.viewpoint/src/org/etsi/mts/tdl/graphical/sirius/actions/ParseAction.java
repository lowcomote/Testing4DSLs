package org.etsi.mts.tdl.graphical.sirius.actions;

import java.io.IOException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.etsi.mts.tdl.Assertion;
import org.etsi.mts.tdl.Assignment;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.BoundedLoopBehaviour;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.LocalExpression;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.PeriodicBehaviour;
import org.etsi.mts.tdl.StaticDataUse;
import org.etsi.mts.tdl.TimeConstraint;
import org.etsi.mts.tdl.TimeOperation;
import org.etsi.mts.tdl.TimerStart;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.tdlPackage;
import org.etsi.mts.tdl.graphical.labels.DataStandaloneSetup;
import org.etsi.mts.tdl.graphical.labels.parser.RuleBasedDataParser;
import org.etsi.mts.tdl.graphical.viewpoint.Activator;

import com.google.inject.Injector;

public class ParseAction implements IExternalJavaAction {

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		EObject element = (EObject) parameters.get("element");
		String newValue = (String) parameters.get("newValue");
		
		try {
			DataUse value = (DataUse) parse(element.eResource().getURI(), newValue, "DataUse");
			
			if (value != null) {
				EClass type = element.eClass();
				List<LocalExpression> localExpressions = null;
				switch (type.getClassifierID()) {
				
				case tdlPackage.MEMBER_ASSIGNMENT:
					((MemberAssignment)element).setMemberSpec((StaticDataUse) value);
					break;
				
				case tdlPackage.MESSAGE:
					((Message)element).setArgument(value);
					break;
					
				case tdlPackage.BLOCK:
					localExpressions = ((Block)element).getGuard();
				case tdlPackage.BOUNDED_LOOP_BEHAVIOUR:
					if (element instanceof BoundedLoopBehaviour)
						localExpressions = ((BoundedLoopBehaviour) element).getNumIteration();
				case tdlPackage.PERIODIC_BEHAVIOUR:
					if (element instanceof PeriodicBehaviour)
						localExpressions = ((PeriodicBehaviour) element).getPeriod();
					
					LocalExpression localExp;
					if (localExpressions.isEmpty())
						localExpressions.add(localExp = tdlFactory.eINSTANCE.createLocalExpression());
					else
						localExp = localExpressions.get(0);
					localExp.setExpression(value);
					break;

				case tdlPackage.WAIT:
				case tdlPackage.QUIESCENCE:
					((TimeOperation)element).setPeriod(value);
					break;

				case tdlPackage.TIMER_START:
					((TimerStart)element).setPeriod(value);
					break;

				case tdlPackage.TIME_CONSTRAINT:
					((TimeConstraint)element).setTimeConstraintExpression(value);
					break;

				case tdlPackage.ASSIGNMENT:
					((Assignment)element).setExpression(value);
					break;

				case tdlPackage.ASSERTION:
					((Assertion)element).setCondition(value);
					break;
				}
			}
			
		} catch (IOException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage()));
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}
	
	private EObject parse(URI uri, String text, String rule) throws IOException {
		Injector xtextInjector = new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
		IResourceFactory resourceFactory = xtextInjector.getInstance(IResourceFactory.class);
		XtextResourceSet rs = xtextInjector.getInstance(XtextResourceSet.class);
		rs.setClasspathURIContext(getClass());

		XtextResource xr = (XtextResource)resourceFactory.createResource(URI.createURI(uri.toString()));
		rs.getResources().add(xr);

		RuleBasedDataParser parser = (RuleBasedDataParser)xr.getParser();
		parser.setDefaultRuleName(rule);
		xr.reparse(text);

		List<EObject> contents = xr.getContents();
		if (contents.isEmpty()) {
			IParseResult result = xr.getParseResult();
			StringBuilder errorMessage = new StringBuilder();
			for (INode err: result.getSyntaxErrors()) {
				errorMessage.append(err.getSyntaxErrorMessage().getMessage());
				errorMessage.append(System.getProperty("line.separator"));
			}
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMessage.toString()),
					StatusManager.SHOW);
			return null;
		}

		//Resolve cross references using linker
		EObject element = contents.get(0);
		EcoreUtil.resolveAll(xr);
		EObject resolved = contents.get(0);
		if (!resolved.eClass().isInstance(element))
			//If linker replaced root element, resolve the new element 
			EcoreUtil.resolveAll(xr);

		return resolved;
	}

}
