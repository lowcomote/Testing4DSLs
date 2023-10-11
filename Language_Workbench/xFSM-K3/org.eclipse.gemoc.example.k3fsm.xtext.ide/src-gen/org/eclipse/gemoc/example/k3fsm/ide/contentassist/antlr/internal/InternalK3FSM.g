/*
 * generated by Xtext 2.25.0
 */
grammar InternalK3FSM;

options {
	superClass=AbstractInternalContentAssistParser;
}

@lexer::header {
package org.eclipse.gemoc.example.k3fsm.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.eclipse.gemoc.example.k3fsm.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.gemoc.example.k3fsm.services.K3FSMGrammarAccess;

}
@parser::members {
	private K3FSMGrammarAccess grammarAccess;

	public void setGrammarAccess(K3FSMGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleFSM
entryRuleFSM
:
{ before(grammarAccess.getFSMRule()); }
	 ruleFSM
{ after(grammarAccess.getFSMRule()); } 
	 EOF 
;

// Rule FSM
ruleFSM 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFSMAccess().getGroup()); }
		(rule__FSM__Group__0)
		{ after(grammarAccess.getFSMAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEString
entryRuleEString
:
{ before(grammarAccess.getEStringRule()); }
	 ruleEString
{ after(grammarAccess.getEStringRule()); } 
	 EOF 
;

// Rule EString
ruleEString 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEStringAccess().getAlternatives()); }
		(rule__EString__Alternatives)
		{ after(grammarAccess.getEStringAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleState
entryRuleState
:
{ before(grammarAccess.getStateRule()); }
	 ruleState
{ after(grammarAccess.getStateRule()); } 
	 EOF 
;

// Rule State
ruleState 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStateAccess().getGroup()); }
		(rule__State__Group__0)
		{ after(grammarAccess.getStateAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTransition
entryRuleTransition
:
{ before(grammarAccess.getTransitionRule()); }
	 ruleTransition
{ after(grammarAccess.getTransitionRule()); } 
	 EOF 
;

// Rule Transition
ruleTransition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTransitionAccess().getGroup()); }
		(rule__Transition__Group__0)
		{ after(grammarAccess.getTransitionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EString__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); }
		RULE_STRING
		{ after(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); }
		RULE_ID
		{ after(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__0__Impl
	rule__FSM__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getFSMKeyword_0()); }
	'FSM'
	{ after(grammarAccess.getFSMAccess().getFSMKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__1__Impl
	rule__FSM__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getNameAssignment_1()); }
	(rule__FSM__NameAssignment_1)
	{ after(grammarAccess.getFSMAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__2__Impl
	rule__FSM__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getLeftCurlyBracketKeyword_2()); }
	'{'
	{ after(grammarAccess.getFSMAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__3__Impl
	rule__FSM__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_3()); }
	(rule__FSM__Group_3__0)?
	{ after(grammarAccess.getFSMAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__4__Impl
	rule__FSM__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_4()); }
	(rule__FSM__Group_4__0)?
	{ after(grammarAccess.getFSMAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__5__Impl
	rule__FSM__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_5()); }
	(rule__FSM__Group_5__0)?
	{ after(grammarAccess.getFSMAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__6__Impl
	rule__FSM__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getInitialStateKeyword_6()); }
	'initialState'
	{ after(grammarAccess.getFSMAccess().getInitialStateKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__7__Impl
	rule__FSM__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getInitialStateAssignment_7()); }
	(rule__FSM__InitialStateAssignment_7)
	{ after(grammarAccess.getFSMAccess().getInitialStateAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__8__Impl
	rule__FSM__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_8()); }
	(rule__FSM__Group_8__0)?
	{ after(grammarAccess.getFSMAccess().getGroup_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__9__Impl
	rule__FSM__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_9()); }
	(rule__FSM__Group_9__0)?
	{ after(grammarAccess.getFSMAccess().getGroup_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__10__Impl
	rule__FSM__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_10()); }
	(rule__FSM__Group_10__0)?
	{ after(grammarAccess.getFSMAccess().getGroup_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group__11__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getRightCurlyBracketKeyword_11()); }
	'}'
	{ after(grammarAccess.getFSMAccess().getRightCurlyBracketKeyword_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_3__0__Impl
	rule__FSM__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getUnprocessedStringKeyword_3_0()); }
	'unprocessedString'
	{ after(grammarAccess.getFSMAccess().getUnprocessedStringKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getUnprocessedStringAssignment_3_1()); }
	(rule__FSM__UnprocessedStringAssignment_3_1)
	{ after(grammarAccess.getFSMAccess().getUnprocessedStringAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_4__0__Impl
	rule__FSM__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getConsummedStringKeyword_4_0()); }
	'consummedString'
	{ after(grammarAccess.getFSMAccess().getConsummedStringKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getConsumedStringAssignment_4_1()); }
	(rule__FSM__ConsumedStringAssignment_4_1)
	{ after(grammarAccess.getFSMAccess().getConsumedStringAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_5__0__Impl
	rule__FSM__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getProducedStringKeyword_5_0()); }
	'producedString'
	{ after(grammarAccess.getFSMAccess().getProducedStringKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getProducedStringAssignment_5_1()); }
	(rule__FSM__ProducedStringAssignment_5_1)
	{ after(grammarAccess.getFSMAccess().getProducedStringAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_8__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_8__0__Impl
	rule__FSM__Group_8__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_8__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getFinalStateKeyword_8_0()); }
	'finalState'
	{ after(grammarAccess.getFSMAccess().getFinalStateKeyword_8_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_8__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_8__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_8__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getFinalStateAssignment_8_1()); }
	(rule__FSM__FinalStateAssignment_8_1)
	{ after(grammarAccess.getFSMAccess().getFinalStateAssignment_8_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_9__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_9__0__Impl
	rule__FSM__Group_9__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_9__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getCurrentStateKeyword_9_0()); }
	'currentState'
	{ after(grammarAccess.getFSMAccess().getCurrentStateKeyword_9_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_9__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_9__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_9__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getCurrentStateAssignment_9_1()); }
	(rule__FSM__CurrentStateAssignment_9_1)
	{ after(grammarAccess.getFSMAccess().getCurrentStateAssignment_9_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_10__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10__0__Impl
	rule__FSM__Group_10__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getOwnedStatesKeyword_10_0()); }
	'ownedStates'
	{ after(grammarAccess.getFSMAccess().getOwnedStatesKeyword_10_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10__1__Impl
	rule__FSM__Group_10__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getLeftCurlyBracketKeyword_10_1()); }
	'{'
	{ after(grammarAccess.getFSMAccess().getLeftCurlyBracketKeyword_10_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10__2__Impl
	rule__FSM__Group_10__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getOwnedStatesAssignment_10_2()); }
	(rule__FSM__OwnedStatesAssignment_10_2)
	{ after(grammarAccess.getFSMAccess().getOwnedStatesAssignment_10_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10__3__Impl
	rule__FSM__Group_10__4
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getGroup_10_3()); }
	(rule__FSM__Group_10_3__0)*
	{ after(grammarAccess.getFSMAccess().getGroup_10_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getRightCurlyBracketKeyword_10_4()); }
	'}'
	{ after(grammarAccess.getFSMAccess().getRightCurlyBracketKeyword_10_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__Group_10_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10_3__0__Impl
	rule__FSM__Group_10_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getCommaKeyword_10_3_0()); }
	','
	{ after(grammarAccess.getFSMAccess().getCommaKeyword_10_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FSM__Group_10_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__Group_10_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFSMAccess().getOwnedStatesAssignment_10_3_1()); }
	(rule__FSM__OwnedStatesAssignment_10_3_1)
	{ after(grammarAccess.getFSMAccess().getOwnedStatesAssignment_10_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__0__Impl
	rule__State__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getStateAction_0()); }
	()
	{ after(grammarAccess.getStateAccess().getStateAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__1__Impl
	rule__State__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getStateKeyword_1()); }
	'State'
	{ after(grammarAccess.getStateAccess().getStateKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__2__Impl
	rule__State__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getNameAssignment_2()); }
	(rule__State__NameAssignment_2)
	{ after(grammarAccess.getStateAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__3__Impl
	rule__State__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_3()); }
	'{'
	{ after(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__4__Impl
	rule__State__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getGroup_4()); }
	(rule__State__Group_4__0)?
	{ after(grammarAccess.getStateAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_5()); }
	'}'
	{ after(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4__0__Impl
	rule__State__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getOutgoingTransitionsKeyword_4_0()); }
	'outgoingTransitions'
	{ after(grammarAccess.getStateAccess().getOutgoingTransitionsKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4__1__Impl
	rule__State__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_4_1()); }
	'{'
	{ after(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4__2__Impl
	rule__State__Group_4__3
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getOutgoingTransitionsAssignment_4_2()); }
	(rule__State__OutgoingTransitionsAssignment_4_2)
	{ after(grammarAccess.getStateAccess().getOutgoingTransitionsAssignment_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4__3__Impl
	rule__State__Group_4__4
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getGroup_4_3()); }
	(rule__State__Group_4_3__0)*
	{ after(grammarAccess.getStateAccess().getGroup_4_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_4_4()); }
	'}'
	{ after(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_4_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group_4_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4_3__0__Impl
	rule__State__Group_4_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getCommaKeyword_4_3_0()); }
	','
	{ after(grammarAccess.getStateAccess().getCommaKeyword_4_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_4_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_4_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getOutgoingTransitionsAssignment_4_3_1()); }
	(rule__State__OutgoingTransitionsAssignment_4_3_1)
	{ after(grammarAccess.getStateAccess().getOutgoingTransitionsAssignment_4_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__0__Impl
	rule__Transition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); }
	'Transition'
	{ after(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__1__Impl
	rule__Transition__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getNameAssignment_1()); }
	(rule__Transition__NameAssignment_1)
	{ after(grammarAccess.getTransitionAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__2__Impl
	rule__Transition__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_2()); }
	'{'
	{ after(grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__3__Impl
	rule__Transition__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGroup_3()); }
	(rule__Transition__Group_3__0)?
	{ after(grammarAccess.getTransitionAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__4__Impl
	rule__Transition__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGroup_4()); }
	(rule__Transition__Group_4__0)?
	{ after(grammarAccess.getTransitionAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__5__Impl
	rule__Transition__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getTargetKeyword_5()); }
	'target'
	{ after(grammarAccess.getTransitionAccess().getTargetKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__6__Impl
	rule__Transition__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getTargetAssignment_6()); }
	(rule__Transition__TargetAssignment_6)
	{ after(grammarAccess.getTransitionAccess().getTargetAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_7()); }
	'}'
	{ after(grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_3__0__Impl
	rule__Transition__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getInputKeyword_3_0()); }
	'input'
	{ after(grammarAccess.getTransitionAccess().getInputKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getInputAssignment_3_1()); }
	(rule__Transition__InputAssignment_3_1)
	{ after(grammarAccess.getTransitionAccess().getInputAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_4__0__Impl
	rule__Transition__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getOutputKeyword_4_0()); }
	'output'
	{ after(grammarAccess.getTransitionAccess().getOutputKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getOutputAssignment_4_1()); }
	(rule__Transition__OutputAssignment_4_1)
	{ after(grammarAccess.getTransitionAccess().getOutputAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FSM__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getNameEStringParserRuleCall_1_0()); }
		ruleEString
		{ after(grammarAccess.getFSMAccess().getNameEStringParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__UnprocessedStringAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getUnprocessedStringEStringParserRuleCall_3_1_0()); }
		ruleEString
		{ after(grammarAccess.getFSMAccess().getUnprocessedStringEStringParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__ConsumedStringAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getConsumedStringEStringParserRuleCall_4_1_0()); }
		ruleEString
		{ after(grammarAccess.getFSMAccess().getConsumedStringEStringParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__ProducedStringAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getProducedStringEStringParserRuleCall_5_1_0()); }
		ruleEString
		{ after(grammarAccess.getFSMAccess().getProducedStringEStringParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__InitialStateAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getInitialStateStateCrossReference_7_0()); }
		(
			{ before(grammarAccess.getFSMAccess().getInitialStateStateEStringParserRuleCall_7_0_1()); }
			ruleEString
			{ after(grammarAccess.getFSMAccess().getInitialStateStateEStringParserRuleCall_7_0_1()); }
		)
		{ after(grammarAccess.getFSMAccess().getInitialStateStateCrossReference_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__FinalStateAssignment_8_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getFinalStateStateCrossReference_8_1_0()); }
		(
			{ before(grammarAccess.getFSMAccess().getFinalStateStateEStringParserRuleCall_8_1_0_1()); }
			ruleEString
			{ after(grammarAccess.getFSMAccess().getFinalStateStateEStringParserRuleCall_8_1_0_1()); }
		)
		{ after(grammarAccess.getFSMAccess().getFinalStateStateCrossReference_8_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__CurrentStateAssignment_9_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getCurrentStateStateCrossReference_9_1_0()); }
		(
			{ before(grammarAccess.getFSMAccess().getCurrentStateStateEStringParserRuleCall_9_1_0_1()); }
			ruleEString
			{ after(grammarAccess.getFSMAccess().getCurrentStateStateEStringParserRuleCall_9_1_0_1()); }
		)
		{ after(grammarAccess.getFSMAccess().getCurrentStateStateCrossReference_9_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__OwnedStatesAssignment_10_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getOwnedStatesStateParserRuleCall_10_2_0()); }
		ruleState
		{ after(grammarAccess.getFSMAccess().getOwnedStatesStateParserRuleCall_10_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FSM__OwnedStatesAssignment_10_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFSMAccess().getOwnedStatesStateParserRuleCall_10_3_1_0()); }
		ruleState
		{ after(grammarAccess.getFSMAccess().getOwnedStatesStateParserRuleCall_10_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getNameEStringParserRuleCall_2_0()); }
		ruleEString
		{ after(grammarAccess.getStateAccess().getNameEStringParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__OutgoingTransitionsAssignment_4_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getOutgoingTransitionsTransitionParserRuleCall_4_2_0()); }
		ruleTransition
		{ after(grammarAccess.getStateAccess().getOutgoingTransitionsTransitionParserRuleCall_4_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__OutgoingTransitionsAssignment_4_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getOutgoingTransitionsTransitionParserRuleCall_4_3_1_0()); }
		ruleTransition
		{ after(grammarAccess.getStateAccess().getOutgoingTransitionsTransitionParserRuleCall_4_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getNameEStringParserRuleCall_1_0()); }
		ruleEString
		{ after(grammarAccess.getTransitionAccess().getNameEStringParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__InputAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getInputEStringParserRuleCall_3_1_0()); }
		ruleEString
		{ after(grammarAccess.getTransitionAccess().getInputEStringParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__OutputAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getOutputEStringParserRuleCall_4_1_0()); }
		ruleEString
		{ after(grammarAccess.getTransitionAccess().getOutputEStringParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__TargetAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getTargetStateCrossReference_6_0()); }
		(
			{ before(grammarAccess.getTransitionAccess().getTargetStateEStringParserRuleCall_6_0_1()); }
			ruleEString
			{ after(grammarAccess.getTransitionAccess().getTargetStateEStringParserRuleCall_6_0_1()); }
		)
		{ after(grammarAccess.getTransitionAccess().getTargetStateCrossReference_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;