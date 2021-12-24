package org.imt.tdl.sbfl.evaluation;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ComparisonCanceledException;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.InternalEList;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ModularizableMatcher implements IEObjectMatcher{

	@Override
	public void createMatches(Comparison comparison, Iterator<? extends EObject> leftEObjects, Iterator<? extends EObject> rightEObjects,
			Iterator<? extends EObject> originEObjects, Monitor monitor) {		
		Set<Match> matches = Sets.newLinkedHashSet();
		Map<EObject, Match> leftEObjectsToMatch = Maps.newHashMap();	
		EList<EObject> listOfRightEObjects = getEListFromIterator(rightEObjects);
		//iterate objects
		while (leftEObjects.hasNext()) {
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}
			EObject leftEObject = (EObject) leftEObjects.next();
			final Match match = CompareFactory.eINSTANCE.createMatch();
			match.setLeft(leftEObject);			
			Iterator<EObject> itRightEObjects = listOfRightEObjects.iterator();
			//iterate objects
			while (itRightEObjects.hasNext()) {
				EObject rightEObject = (EObject) itRightEObjects.next();
				boolean equalsEObject = false;
				if (rightEObject.getClass().equals(leftEObject.getClass())) {
					
					if (equalsEObject == true) {
						match.setRight(rightEObject);
						break;
					}
				}
			}
			// Can we find a parent?
			EObject parentEObject = getParentEObject(leftEObject);
			final Match parent = leftEObjectsToMatch.get(parentEObject);
			if (parentEObject != null) {
				((InternalEList<Match>)parent.getSubmatches()).addUnique(match);			
			}		
			// Add match
			matches.add(match);		
			leftEObjectsToMatch.put(leftEObject, match);
		}	
		Iterables.addAll(comparison.getMatches(), matches);
	}
	
	private EList<EObject> getEListFromIterator(Iterator<? extends EObject> rightEObjects) {
		EList<EObject> listOfRightEObjects = new BasicEList<EObject>();
		rightEObjects.forEachRemaining(listOfRightEObjects::add);
		return listOfRightEObjects;
	}
	
	private EObject getParentEObject(EObject eObject) {
		final EObject parent;
		if (eObject != null) {
			parent = eObject.eContainer();
		} else {
			parent = null;
		}
		return parent;
	}
}

