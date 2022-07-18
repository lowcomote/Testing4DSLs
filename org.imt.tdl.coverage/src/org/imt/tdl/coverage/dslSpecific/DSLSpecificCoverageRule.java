package org.imt.tdl.coverage.dslSpecific;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.imt.tdl.coverage.TDLCoverageUtil;

public class DSLSpecificCoverageRule {
	
	private EClass context;
	//?? maybe a map would be needed
	private EList<EReference> referenceCoverage;
	private EList<EReference> containerCoverageByAll;
	private EList<EReference> containerCoverageByOne;
	
	private List<DSLSpecificCoverageRule> dependencies = new ArrayList();

	private boolean ignoreClassFromCoverage;
	
	public EClass getContext() {
		return context;
	}
	public void setContext(EClass context) {
		this.context = context;
	}
	
	public EList<EReference> getReferenceCoverage() {
		return referenceCoverage;
	}
	
	//from the coverage of the context class, the coverage of its referenced classes can be implied
	public void setReferenceCoverage(EList<EReference> referenceCoverage) {
		this.referenceCoverage = referenceCoverage;
		this.referenceCoverage.forEach(r -> addNewCoverableClass(r));
	}
	
	public void setReferenceCoverage(EReference referenceCoverage) {
		this.referenceCoverage = ECollections.asEList(referenceCoverage);
		addNewCoverableClass(referenceCoverage);
	}
	
	//from the coverage of the context class, the coverage of its container class can be implied
	public EList<EReference> getContainerCoverageByAll() {
		return containerCoverageByAll;
	}
	public void setContainerCoverageByAll(EList<EReference> containerCoverageByAll) {
		this.containerCoverageByAll = containerCoverageByAll;
		this.containerCoverageByAll.forEach(r -> addNewCoverableClass((EClass) r.eContainer()));
	}
	public void setContainerCoverageByAll(EReference containerCoverageByAll) {
		this.containerCoverageByAll = ECollections.asEList(containerCoverageByAll);
		addNewCoverableClass((EClass) containerCoverageByAll.eContainer());
	}
	public EList<EReference> getContainerCoverageByOne() {
		return containerCoverageByOne;
	}
	public void setContainerCoverageByOne(EList<EReference> containerCoverageByOne) {
		this.containerCoverageByOne = containerCoverageByOne;
		this.containerCoverageByOne.forEach(r -> addNewCoverableClass((EClass) r.eContainer()));
	}
	public void setContainerCoverageByOne(EReference containerCoverageByOne) {
		this.containerCoverageByOne = ECollections.asEList(containerCoverageByOne);
		addNewCoverableClass((EClass) containerCoverageByOne.eContainer());
	}
	private void addNewCoverableClass(EReference r) {
		if (!TDLCoverageUtil.getInstance().isClassCoverable((EClass) r.getEType())) {
			TDLCoverageUtil.getInstance().addNewCoverableClass((EClass) r.getEType());
		}
	}
	
	private void addNewCoverableClass(EClass c) {
		if (!TDLCoverageUtil.getInstance().isClassCoverable(c)) {
			TDLCoverageUtil.getInstance().addNewCoverableClass(c);
		}
	}
	
	public void ignoreClassFromCoverage() {
		ignoreClassFromCoverage = true;
		if (TDLCoverageUtil.getInstance().isClassCoverable(context)) {
			TDLCoverageUtil.getInstance().removeCoverableClass(context);
		}
	}
	
	public void ignoreClass_subClassesFromCoverage() {
		ignoreClassFromCoverage = true;
		if (TDLCoverageUtil.getInstance().isClassCoverable(context)) {
			TDLCoverageUtil.getInstance().removeCoverableClass_subClass(context);
		}
	}
	
	public void addDependency (DSLSpecificCoverageRule rule) {
		dependencies.add(rule);
	}
	
	public void removeDependency (DSLSpecificCoverageRule rule) {
		if (dependencies.contains(rule)) {
			dependencies.remove(rule);
		}
	}
	public List<DSLSpecificCoverageRule> getDependencies() {
		return dependencies;
	}
}
