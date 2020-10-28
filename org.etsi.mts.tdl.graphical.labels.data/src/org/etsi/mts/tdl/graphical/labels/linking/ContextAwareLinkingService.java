package org.etsi.mts.tdl.graphical.labels.linking;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.tdlPackage;

import com.google.inject.Inject;

public class ContextAwareLinkingService extends DefaultLinkingService {
	
	private Map<EClass, AlternativeContext> alternativeContexts;
	
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	
	@Override
	public List<EObject> getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		
		final EClass requiredType = ref.getEReferenceType();
		if (requiredType == null)
			return Collections.<EObject> emptyList();

		final String crossRefString = getCrossRefNodeAsString(node);
		if (crossRefString != null && !crossRefString.equals("")) {
			QualifiedName qualifiedLinkName =  qualifiedNameConverter.toQualifiedName(crossRefString);
			
			IScope scope = getScope(context, ref);
			Iterable<IEObjectDescription> eObjectDescriptions = scope.getElements(qualifiedLinkName);
			if (context instanceof DataUse && !eObjectDescriptions.iterator().hasNext()) {
				
				AlternativeContext alternative = getAlternativeContext(context);
				
				if (alternative != null) {
					//Keeps track of current object in the resource
					EObject current = context;
					for (int i = 0; i < alternative.classes.length; i++) {
						EObject alternativeContext = EcoreUtil.create(alternative.classes[i]);
						
						//Add new object to resource
						EcoreUtil.replace(current, alternativeContext);
						current = alternativeContext;
						
						IScope alternativeScope = getScope(alternativeContext, alternative.references[i]);
						Iterable<IEObjectDescription> altEObjectDescriptions = alternativeScope.getElements(qualifiedLinkName);
						if (altEObjectDescriptions.iterator().hasNext()) {
							
							alternative.copy(context, alternativeContext);
							
							context = alternativeContext;
							ref = alternative.references[i];
							scope = alternativeScope;
							eObjectDescriptions = altEObjectDescriptions;
							break;
						}
					}
					if (context != current)
						//No alternative match, restore original
						EcoreUtil.replace(current, context);
				}
			}
			
			Iterator<IEObjectDescription> iter = eObjectDescriptions.iterator();
			if (iter.hasNext()) {
				IEObjectDescription eObjectDescription = iter.next();
				EObject linkedObject = eObjectDescription.getEObjectOrProxy();
				if (!iter.hasNext() || !ref.isMany()) {
					// If we replaced the context object above then we need to
					// set this association here or it will be lost
					context.eSet(ref, linkedObject);
					return Collections.singletonList(linkedObject);
				}
			}
		}
		return Collections.emptyList();
	}

	public AlternativeContext getAlternativeContext(EObject contextObject) {
		if (alternativeContexts == null) {
			alternativeContexts = new Hashtable<EClass, AlternativeContext>();
			
			AlternativeContext dataUseContexts = new AlternativeContext() {
				@Override
				void copy(EObject from, EObject to) {
					((DataUse)to).getReduction().addAll(((DataUse)from).getReduction());
					((DataUse)to).getArgument().addAll(((DataUse)from).getArgument());
				}
			};
			dataUseContexts.classes = new EClass[]{
					tdlPackage.eINSTANCE.getDataInstanceUse(),
					tdlPackage.eINSTANCE.getFunctionCall(),
					tdlPackage.eINSTANCE.getPredefinedFunctionCall(),
					tdlPackage.eINSTANCE.getVariableUse(),
					tdlPackage.eINSTANCE.getFormalParameterUse(),
					tdlPackage.eINSTANCE.getTimeLabelUse()
			};
			dataUseContexts.references = new EReference[]{
					//TODO: extend with remaining kinds
					tdlPackage.eINSTANCE.getDataInstanceUse_DataInstance(),
					tdlPackage.eINSTANCE.getFunctionCall_Function(),
					tdlPackage.eINSTANCE.getPredefinedFunctionCall_Function(),
					tdlPackage.eINSTANCE.getVariableUse_Variable(),
					tdlPackage.eINSTANCE.getFormalParameterUse_Parameter(),
					tdlPackage.eINSTANCE.getTimeLabelUse_TimeLabel()
			};
			alternativeContexts.put(tdlPackage.eINSTANCE.getDataUse(), dataUseContexts);
		}

		for (EClass alternativeClass: alternativeContexts.keySet())
			if (alternativeClass.isInstance(contextObject))
				return alternativeContexts.get(alternativeClass);
		
		System.out.println(contextObject);
		return null;
	}

}

abstract class AlternativeContext {
	EClass[] classes;
	EReference[] references;
	abstract void copy(EObject from, EObject to);
}