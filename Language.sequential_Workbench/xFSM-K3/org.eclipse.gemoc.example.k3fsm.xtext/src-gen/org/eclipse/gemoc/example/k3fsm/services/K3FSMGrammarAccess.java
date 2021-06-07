/*
 * generated by Xtext 2.24.0
 */
package org.eclipse.gemoc.example.k3fsm.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class K3FSMGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class FSMElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.gemoc.example.k3fsm.K3FSM.FSM");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cFSMKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameEStringParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cUnprocessedStringKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cUnprocessedStringAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cUnprocessedStringEStringParserRuleCall_3_1_0 = (RuleCall)cUnprocessedStringAssignment_3_1.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cConsummedStringKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cConsumedStringAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cConsumedStringEStringParserRuleCall_4_1_0 = (RuleCall)cConsumedStringAssignment_4_1.eContents().get(0);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cProducedStringKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Assignment cProducedStringAssignment_5_1 = (Assignment)cGroup_5.eContents().get(1);
		private final RuleCall cProducedStringEStringParserRuleCall_5_1_0 = (RuleCall)cProducedStringAssignment_5_1.eContents().get(0);
		private final Keyword cInitialStateKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cInitialStateAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final CrossReference cInitialStateStateCrossReference_7_0 = (CrossReference)cInitialStateAssignment_7.eContents().get(0);
		private final RuleCall cInitialStateStateEStringParserRuleCall_7_0_1 = (RuleCall)cInitialStateStateCrossReference_7_0.eContents().get(1);
		private final Group cGroup_8 = (Group)cGroup.eContents().get(8);
		private final Keyword cFinalStateKeyword_8_0 = (Keyword)cGroup_8.eContents().get(0);
		private final Assignment cFinalStateAssignment_8_1 = (Assignment)cGroup_8.eContents().get(1);
		private final CrossReference cFinalStateStateCrossReference_8_1_0 = (CrossReference)cFinalStateAssignment_8_1.eContents().get(0);
		private final RuleCall cFinalStateStateEStringParserRuleCall_8_1_0_1 = (RuleCall)cFinalStateStateCrossReference_8_1_0.eContents().get(1);
		private final Group cGroup_9 = (Group)cGroup.eContents().get(9);
		private final Keyword cCurrentStateKeyword_9_0 = (Keyword)cGroup_9.eContents().get(0);
		private final Assignment cCurrentStateAssignment_9_1 = (Assignment)cGroup_9.eContents().get(1);
		private final CrossReference cCurrentStateStateCrossReference_9_1_0 = (CrossReference)cCurrentStateAssignment_9_1.eContents().get(0);
		private final RuleCall cCurrentStateStateEStringParserRuleCall_9_1_0_1 = (RuleCall)cCurrentStateStateCrossReference_9_1_0.eContents().get(1);
		private final Group cGroup_10 = (Group)cGroup.eContents().get(10);
		private final Keyword cOwnedStatesKeyword_10_0 = (Keyword)cGroup_10.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_10_1 = (Keyword)cGroup_10.eContents().get(1);
		private final Assignment cOwnedStatesAssignment_10_2 = (Assignment)cGroup_10.eContents().get(2);
		private final RuleCall cOwnedStatesStateParserRuleCall_10_2_0 = (RuleCall)cOwnedStatesAssignment_10_2.eContents().get(0);
		private final Group cGroup_10_3 = (Group)cGroup_10.eContents().get(3);
		private final Keyword cCommaKeyword_10_3_0 = (Keyword)cGroup_10_3.eContents().get(0);
		private final Assignment cOwnedStatesAssignment_10_3_1 = (Assignment)cGroup_10_3.eContents().get(1);
		private final RuleCall cOwnedStatesStateParserRuleCall_10_3_1_0 = (RuleCall)cOwnedStatesAssignment_10_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_10_4 = (Keyword)cGroup_10.eContents().get(4);
		private final Keyword cRightCurlyBracketKeyword_11 = (Keyword)cGroup.eContents().get(11);
		
		//FSM:
		//	'FSM'
		//	name=EString
		//	'{' ('unprocessedString' unprocessedString=EString)? ('consummedString' consumedString=EString)? ('producedString'
		//	producedString=EString)?
		//	'initialState' initialState=[State|EString] ('finalState' finalState=[State|EString])? ('currentState'
		//	currentState=[State|EString])? ('ownedStates' '{' ownedStates+=State ("," ownedStates+=State)* '}')?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'FSM'
		//name=EString
		//'{' ('unprocessedString' unprocessedString=EString)? ('consummedString' consumedString=EString)? ('producedString'
		//producedString=EString)?
		//'initialState' initialState=[State|EString] ('finalState' finalState=[State|EString])? ('currentState'
		//currentState=[State|EString])? ('ownedStates' '{' ownedStates+=State ("," ownedStates+=State)* '}')?
		//'}'
		public Group getGroup() { return cGroup; }
		
		//'FSM'
		public Keyword getFSMKeyword_0() { return cFSMKeyword_0; }
		
		//name=EString
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//EString
		public RuleCall getNameEStringParserRuleCall_1_0() { return cNameEStringParserRuleCall_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//('unprocessedString' unprocessedString=EString)?
		public Group getGroup_3() { return cGroup_3; }
		
		//'unprocessedString'
		public Keyword getUnprocessedStringKeyword_3_0() { return cUnprocessedStringKeyword_3_0; }
		
		//unprocessedString=EString
		public Assignment getUnprocessedStringAssignment_3_1() { return cUnprocessedStringAssignment_3_1; }
		
		//EString
		public RuleCall getUnprocessedStringEStringParserRuleCall_3_1_0() { return cUnprocessedStringEStringParserRuleCall_3_1_0; }
		
		//('consummedString' consumedString=EString)?
		public Group getGroup_4() { return cGroup_4; }
		
		//'consummedString'
		public Keyword getConsummedStringKeyword_4_0() { return cConsummedStringKeyword_4_0; }
		
		//consumedString=EString
		public Assignment getConsumedStringAssignment_4_1() { return cConsumedStringAssignment_4_1; }
		
		//EString
		public RuleCall getConsumedStringEStringParserRuleCall_4_1_0() { return cConsumedStringEStringParserRuleCall_4_1_0; }
		
		//('producedString' producedString=EString)?
		public Group getGroup_5() { return cGroup_5; }
		
		//'producedString'
		public Keyword getProducedStringKeyword_5_0() { return cProducedStringKeyword_5_0; }
		
		//producedString=EString
		public Assignment getProducedStringAssignment_5_1() { return cProducedStringAssignment_5_1; }
		
		//EString
		public RuleCall getProducedStringEStringParserRuleCall_5_1_0() { return cProducedStringEStringParserRuleCall_5_1_0; }
		
		//'initialState'
		public Keyword getInitialStateKeyword_6() { return cInitialStateKeyword_6; }
		
		//initialState=[State|EString]
		public Assignment getInitialStateAssignment_7() { return cInitialStateAssignment_7; }
		
		//[State|EString]
		public CrossReference getInitialStateStateCrossReference_7_0() { return cInitialStateStateCrossReference_7_0; }
		
		//EString
		public RuleCall getInitialStateStateEStringParserRuleCall_7_0_1() { return cInitialStateStateEStringParserRuleCall_7_0_1; }
		
		//('finalState' finalState=[State|EString])?
		public Group getGroup_8() { return cGroup_8; }
		
		//'finalState'
		public Keyword getFinalStateKeyword_8_0() { return cFinalStateKeyword_8_0; }
		
		//finalState=[State|EString]
		public Assignment getFinalStateAssignment_8_1() { return cFinalStateAssignment_8_1; }
		
		//[State|EString]
		public CrossReference getFinalStateStateCrossReference_8_1_0() { return cFinalStateStateCrossReference_8_1_0; }
		
		//EString
		public RuleCall getFinalStateStateEStringParserRuleCall_8_1_0_1() { return cFinalStateStateEStringParserRuleCall_8_1_0_1; }
		
		//('currentState' currentState=[State|EString])?
		public Group getGroup_9() { return cGroup_9; }
		
		//'currentState'
		public Keyword getCurrentStateKeyword_9_0() { return cCurrentStateKeyword_9_0; }
		
		//currentState=[State|EString]
		public Assignment getCurrentStateAssignment_9_1() { return cCurrentStateAssignment_9_1; }
		
		//[State|EString]
		public CrossReference getCurrentStateStateCrossReference_9_1_0() { return cCurrentStateStateCrossReference_9_1_0; }
		
		//EString
		public RuleCall getCurrentStateStateEStringParserRuleCall_9_1_0_1() { return cCurrentStateStateEStringParserRuleCall_9_1_0_1; }
		
		//('ownedStates' '{' ownedStates+=State ("," ownedStates+=State)* '}')?
		public Group getGroup_10() { return cGroup_10; }
		
		//'ownedStates'
		public Keyword getOwnedStatesKeyword_10_0() { return cOwnedStatesKeyword_10_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_10_1() { return cLeftCurlyBracketKeyword_10_1; }
		
		//ownedStates+=State
		public Assignment getOwnedStatesAssignment_10_2() { return cOwnedStatesAssignment_10_2; }
		
		//State
		public RuleCall getOwnedStatesStateParserRuleCall_10_2_0() { return cOwnedStatesStateParserRuleCall_10_2_0; }
		
		//("," ownedStates+=State)*
		public Group getGroup_10_3() { return cGroup_10_3; }
		
		//","
		public Keyword getCommaKeyword_10_3_0() { return cCommaKeyword_10_3_0; }
		
		//ownedStates+=State
		public Assignment getOwnedStatesAssignment_10_3_1() { return cOwnedStatesAssignment_10_3_1; }
		
		//State
		public RuleCall getOwnedStatesStateParserRuleCall_10_3_1_0() { return cOwnedStatesStateParserRuleCall_10_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_10_4() { return cRightCurlyBracketKeyword_10_4; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_11() { return cRightCurlyBracketKeyword_11; }
	}
	public class EStringElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.gemoc.example.k3fsm.K3FSM.EString");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSTRINGTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cIDTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//EString:
		//	STRING | ID;
		@Override public ParserRule getRule() { return rule; }
		
		//STRING | ID
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//STRING
		public RuleCall getSTRINGTerminalRuleCall_0() { return cSTRINGTerminalRuleCall_0; }
		
		//ID
		public RuleCall getIDTerminalRuleCall_1() { return cIDTerminalRuleCall_1; }
	}
	public class StateElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.gemoc.example.k3fsm.K3FSM.State");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cStateAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cStateKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cNameEStringParserRuleCall_2_0 = (RuleCall)cNameAssignment_2.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cOutgoingTransitionsKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_4_1 = (Keyword)cGroup_4.eContents().get(1);
		private final Assignment cOutgoingTransitionsAssignment_4_2 = (Assignment)cGroup_4.eContents().get(2);
		private final RuleCall cOutgoingTransitionsTransitionParserRuleCall_4_2_0 = (RuleCall)cOutgoingTransitionsAssignment_4_2.eContents().get(0);
		private final Group cGroup_4_3 = (Group)cGroup_4.eContents().get(3);
		private final Keyword cCommaKeyword_4_3_0 = (Keyword)cGroup_4_3.eContents().get(0);
		private final Assignment cOutgoingTransitionsAssignment_4_3_1 = (Assignment)cGroup_4_3.eContents().get(1);
		private final RuleCall cOutgoingTransitionsTransitionParserRuleCall_4_3_1_0 = (RuleCall)cOutgoingTransitionsAssignment_4_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4_4 = (Keyword)cGroup_4.eContents().get(4);
		private final Keyword cRightCurlyBracketKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//State:
		//	{State}
		//	'State'
		//	name=EString
		//	'{' ('outgoingTransitions' '{' outgoingTransitions+=Transition ("," outgoingTransitions+=Transition)* '}')?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{State}
		//'State'
		//name=EString
		//'{' ('outgoingTransitions' '{' outgoingTransitions+=Transition ("," outgoingTransitions+=Transition)* '}')?
		//'}'
		public Group getGroup() { return cGroup; }
		
		//{State}
		public Action getStateAction_0() { return cStateAction_0; }
		
		//'State'
		public Keyword getStateKeyword_1() { return cStateKeyword_1; }
		
		//name=EString
		public Assignment getNameAssignment_2() { return cNameAssignment_2; }
		
		//EString
		public RuleCall getNameEStringParserRuleCall_2_0() { return cNameEStringParserRuleCall_2_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_3() { return cLeftCurlyBracketKeyword_3; }
		
		//('outgoingTransitions' '{' outgoingTransitions+=Transition ("," outgoingTransitions+=Transition)* '}')?
		public Group getGroup_4() { return cGroup_4; }
		
		//'outgoingTransitions'
		public Keyword getOutgoingTransitionsKeyword_4_0() { return cOutgoingTransitionsKeyword_4_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_4_1() { return cLeftCurlyBracketKeyword_4_1; }
		
		//outgoingTransitions+=Transition
		public Assignment getOutgoingTransitionsAssignment_4_2() { return cOutgoingTransitionsAssignment_4_2; }
		
		//Transition
		public RuleCall getOutgoingTransitionsTransitionParserRuleCall_4_2_0() { return cOutgoingTransitionsTransitionParserRuleCall_4_2_0; }
		
		//("," outgoingTransitions+=Transition)*
		public Group getGroup_4_3() { return cGroup_4_3; }
		
		//","
		public Keyword getCommaKeyword_4_3_0() { return cCommaKeyword_4_3_0; }
		
		//outgoingTransitions+=Transition
		public Assignment getOutgoingTransitionsAssignment_4_3_1() { return cOutgoingTransitionsAssignment_4_3_1; }
		
		//Transition
		public RuleCall getOutgoingTransitionsTransitionParserRuleCall_4_3_1_0() { return cOutgoingTransitionsTransitionParserRuleCall_4_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4_4() { return cRightCurlyBracketKeyword_4_4; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_5() { return cRightCurlyBracketKeyword_5; }
	}
	public class TransitionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.gemoc.example.k3fsm.K3FSM.Transition");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cTransitionKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameEStringParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cInputKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cInputAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cInputEStringParserRuleCall_3_1_0 = (RuleCall)cInputAssignment_3_1.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cOutputKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cOutputAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cOutputEStringParserRuleCall_4_1_0 = (RuleCall)cOutputAssignment_4_1.eContents().get(0);
		private final Keyword cTargetKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Assignment cTargetAssignment_6 = (Assignment)cGroup.eContents().get(6);
		private final CrossReference cTargetStateCrossReference_6_0 = (CrossReference)cTargetAssignment_6.eContents().get(0);
		private final RuleCall cTargetStateEStringParserRuleCall_6_0_1 = (RuleCall)cTargetStateCrossReference_6_0.eContents().get(1);
		private final Keyword cRightCurlyBracketKeyword_7 = (Keyword)cGroup.eContents().get(7);
		
		//Transition:
		//	'Transition'
		//	name=EString
		//	'{' ('input' input=EString)? ('output' output=EString)?
		//	'target' target=[State|EString]
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'Transition'
		//name=EString
		//'{' ('input' input=EString)? ('output' output=EString)?
		//'target' target=[State|EString]
		//'}'
		public Group getGroup() { return cGroup; }
		
		//'Transition'
		public Keyword getTransitionKeyword_0() { return cTransitionKeyword_0; }
		
		//name=EString
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//EString
		public RuleCall getNameEStringParserRuleCall_1_0() { return cNameEStringParserRuleCall_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//('input' input=EString)?
		public Group getGroup_3() { return cGroup_3; }
		
		//'input'
		public Keyword getInputKeyword_3_0() { return cInputKeyword_3_0; }
		
		//input=EString
		public Assignment getInputAssignment_3_1() { return cInputAssignment_3_1; }
		
		//EString
		public RuleCall getInputEStringParserRuleCall_3_1_0() { return cInputEStringParserRuleCall_3_1_0; }
		
		//('output' output=EString)?
		public Group getGroup_4() { return cGroup_4; }
		
		//'output'
		public Keyword getOutputKeyword_4_0() { return cOutputKeyword_4_0; }
		
		//output=EString
		public Assignment getOutputAssignment_4_1() { return cOutputAssignment_4_1; }
		
		//EString
		public RuleCall getOutputEStringParserRuleCall_4_1_0() { return cOutputEStringParserRuleCall_4_1_0; }
		
		//'target'
		public Keyword getTargetKeyword_5() { return cTargetKeyword_5; }
		
		//target=[State|EString]
		public Assignment getTargetAssignment_6() { return cTargetAssignment_6; }
		
		//[State|EString]
		public CrossReference getTargetStateCrossReference_6_0() { return cTargetStateCrossReference_6_0; }
		
		//EString
		public RuleCall getTargetStateEStringParserRuleCall_6_0_1() { return cTargetStateEStringParserRuleCall_6_0_1; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_7() { return cRightCurlyBracketKeyword_7; }
	}
	
	
	private final FSMElements pFSM;
	private final EStringElements pEString;
	private final StateElements pState;
	private final TransitionElements pTransition;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public K3FSMGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pFSM = new FSMElements();
		this.pEString = new EStringElements();
		this.pState = new StateElements();
		this.pTransition = new TransitionElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.gemoc.example.k3fsm.K3FSM".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//FSM:
	//	'FSM'
	//	name=EString
	//	'{' ('unprocessedString' unprocessedString=EString)? ('consummedString' consumedString=EString)? ('producedString'
	//	producedString=EString)?
	//	'initialState' initialState=[State|EString] ('finalState' finalState=[State|EString])? ('currentState'
	//	currentState=[State|EString])? ('ownedStates' '{' ownedStates+=State ("," ownedStates+=State)* '}')?
	//	'}';
	public FSMElements getFSMAccess() {
		return pFSM;
	}
	
	public ParserRule getFSMRule() {
		return getFSMAccess().getRule();
	}
	
	//EString:
	//	STRING | ID;
	public EStringElements getEStringAccess() {
		return pEString;
	}
	
	public ParserRule getEStringRule() {
		return getEStringAccess().getRule();
	}
	
	//State:
	//	{State}
	//	'State'
	//	name=EString
	//	'{' ('outgoingTransitions' '{' outgoingTransitions+=Transition ("," outgoingTransitions+=Transition)* '}')?
	//	'}';
	public StateElements getStateAccess() {
		return pState;
	}
	
	public ParserRule getStateRule() {
		return getStateAccess().getRule();
	}
	
	//Transition:
	//	'Transition'
	//	name=EString
	//	'{' ('input' input=EString)? ('output' output=EString)?
	//	'target' target=[State|EString]
	//	'}';
	public TransitionElements getTransitionAccess() {
		return pTransition;
	}
	
	public ParserRule getTransitionRule() {
		return getTransitionAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' |
	//	"'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}