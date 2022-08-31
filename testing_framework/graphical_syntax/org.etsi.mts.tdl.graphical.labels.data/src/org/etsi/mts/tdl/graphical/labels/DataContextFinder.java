package org.etsi.mts.tdl.graphical.labels;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.analysis.SerializationContext;
import org.eclipse.xtext.serializer.sequencer.IContextFinder;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.graphical.labels.services.DataGrammarAccess;

import com.google.inject.Inject;

public class DataContextFinder implements IContextFinder {
	
	@Inject
	private IGrammarAccess grammarAccess;

	@Override
	public Set<ISerializationContext> findByContents(EObject semanticObject,
			Iterable<ISerializationContext> contextCandidates) {
		return findByContentsAndContainer(semanticObject, contextCandidates);
	}

	@Override
	public Set<ISerializationContext> findByContentsAndContainer(EObject semanticObject,
			Iterable<ISerializationContext> contextCandidates) {
		ParserRule rule = null;
		if (semanticObject instanceof DataUse)
			rule = ((DataGrammarAccess)grammarAccess).getDataUseRule();
		if (rule != null) {
			ISerializationContext context = SerializationContext.fromEObject(rule, semanticObject);
			return Collections.singleton(context);
		}
		return Collections.emptySet();
	}

	@Override
	public Iterable<EObject> findContextsByContents(EObject semanticObject, Iterable<EObject> contextCandidates) {
		// This is deprecated
		return null;
	}

	@Override
	public Iterable<EObject> findContextsByContentsAndContainer(EObject semanticObject,
			Iterable<EObject> contextCandidates) {
		// This is deprecated
		return null;
	}

}
