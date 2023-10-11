/*
 * generated by Xtext 2.25.0
 */
package org.eclipse.gemoc.example.k3fsm.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.gemoc.example.k3fsm.parser.antlr.internal.InternalK3FSMParser;
import org.eclipse.gemoc.example.k3fsm.services.K3FSMGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class K3FSMParser extends AbstractAntlrParser {

	@Inject
	private K3FSMGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalK3FSMParser createParser(XtextTokenStream stream) {
		return new InternalK3FSMParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "FSM";
	}

	public K3FSMGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(K3FSMGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}