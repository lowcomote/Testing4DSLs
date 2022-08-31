package org.etsi.mts.tdl.graphical.extensions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.etsi.mts.tdl.Action;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.Behaviour;
import org.etsi.mts.tdl.BehaviourDescription;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.ComponentType;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Element;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Function;
import org.etsi.mts.tdl.GateType;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.TestConfiguration;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.Timer;
import org.etsi.mts.tdl.Variable;
import org.etsi.mts.tdl.tdlPackage;

public class ModelHelper {
	
	public String className(EObject element) {
		return element.eClass().getName();
	}
	
	public EObject owner(EObject element) {
		return element.eContainer();
	}

	public TestDescription getTestDescription(EObject behaviourContent) {
		EObject c = behaviourContent;
		while (c != null) {
			if (c instanceof TestDescription)
				return (TestDescription) c;
			c = c.eContainer();
		}
		return null;
	}
	
	public ComponentInstance getContainingTestComponent(DDiagramElement configurationDiagramElement) {
		while (configurationDiagramElement != null) {
			EObject t = configurationDiagramElement.getTarget();
			if (t instanceof ComponentInstance)
				return (ComponentInstance) t;
			if (!(configurationDiagramElement.eContainer() instanceof DDiagramElement))
				return null;
			configurationDiagramElement = (DDiagramElement) configurationDiagramElement.eContainer();
		}
		return null;
	}
	
	public TestConfiguration getTestConfiguration(EObject behaviourContent) {
		TestDescription td = getTestDescription(behaviourContent);
		return td != null ? td.getTestConfiguration() : null;
	}
	
	public List<TestDescription> getTestDescriptions(EObject context) {
		List<TestDescription> tdList = (List<TestDescription>) getOf(context, tdlPackage.eINSTANCE.getTestDescription());
		TestDescription td = getTestDescription(context);
		tdList.remove(td);
		return tdList;
	}

	public List<Function> getFunctions(EObject context) {
		return (List<Function>) getOf(context, tdlPackage.eINSTANCE.getFunction());
	}

	public List<Action> getActions(EObject context) {
		return (List<Action>) getOf(context, tdlPackage.eINSTANCE.getAction());
	}

	public List<SimpleDataType> getSimpleDataTypes(EObject context) {
		return (List<SimpleDataType>) getOf(context, tdlPackage.eINSTANCE.getSimpleDataType());
	}

	public List<StructuredDataType> getStructuredDataTypes(EObject context) {
		return (List<StructuredDataType>) getOf(context, tdlPackage.eINSTANCE.getStructuredDataType());
	}

	public List<DataType> getDataTypes(EObject context) {
		return (List<DataType>) getOf(context, tdlPackage.eINSTANCE.getDataType());
	}

	public List<DataInstance> getDataInstances(EObject context) {
		return (List<DataInstance>) getOf(context, tdlPackage.eINSTANCE.getDataInstance());
	}

	public List<TestConfiguration> getTestConfigurations(EObject context) {
		return (List<TestConfiguration>) getOf(context, tdlPackage.eINSTANCE.getTestConfiguration());
	}

	public List<ComponentType> getComponentTypes(EObject context) {
		return (List<ComponentType>) getOf(context, tdlPackage.eINSTANCE.getComponentType());
	}

	public List<AnnotationType> getAnnotationTypes(EObject context) {
		return (List<AnnotationType>) getOf(context, tdlPackage.eINSTANCE.getAnnotationType());
	}

	public List<GateType> getGateTypes(EObject context) {
		return (List<GateType>) getOf(context, tdlPackage.eINSTANCE.getGateType());
	}

	public List<Variable> getVariables(EObject context) {
		List<Variable> vars = new LinkedList<Variable>();
		TestDescription td = getTestDescription(context);
		for (ComponentInstance c: td.getTestConfiguration().getComponentInstance())
			vars.addAll(c.getType().getVariable());
		return vars;
	}

	public List<Timer> getTimers(EObject context) {
		List<Timer> timers = new LinkedList<Timer>();
		TestDescription td = getTestDescription(context);
		for (ComponentInstance c: td.getTestConfiguration().getComponentInstance())
			timers.addAll(c.getType().getTimer());
		return timers;
	}
	
	private List<? extends Element> getOf(EObject context, EClassifier eClass) {
		List<Element> list = new LinkedList<Element>();
		for (Element e: visibleElements(context))
			if (eClass.isInstance(e))
				list.add(e);
		return list;
	}
	
	public List<SimpleDataInstance> getVerdicts(EObject context) {
		List<SimpleDataInstance> verdicts = new LinkedList<SimpleDataInstance>();
		for (Element e: visibleElements(context))
			if (e instanceof SimpleDataInstance) {
				DataType type = ((SimpleDataInstance) e).getDataType();
				if (type.getName().equalsIgnoreCase("Verdict"))
					verdicts.add((SimpleDataInstance) e);
			}
		return verdicts;
	}
	
	private List<Element> visibleElements(EObject context) {
		List<Element> elements = new LinkedList<Element>();
		
		while (!(context instanceof Package))
			context = context.eContainer();
		
		Package p = (Package) context;
		elements.addAll(p.getPackagedElement());
		
		for (ElementImport imp: p.getImport()) {
			if (imp.getImportedElement().isEmpty())
				elements.addAll(imp.getImportedPackage().getPackagedElement());
			else
				elements.addAll(imp.getImportedElement());
		}
		
		return elements;
	}
	
	public List<EObject> allBehaviours(TestDescription e) {
		List<EObject> behaviours = new LinkedList<EObject>();
		TreeIterator<EObject> iter = e.eAllContents();
		while (iter.hasNext()) {
			EObject c = iter.next();
			if (c.eContainer() instanceof BehaviourDescription)
				//Top level CompoundBehaviour
				continue;
			if (c instanceof Behaviour)
				behaviours.add(c);
			else if (c instanceof Target) 
				behaviours.add(c);
		}
		return behaviours;
	}
}
