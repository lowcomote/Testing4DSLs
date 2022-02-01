package org.eclipse.gemoc.example.k3fsm.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect



import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.eclipse.gemoc.example.k3fsm.FSM
import org.eclipse.gemoc.example.k3fsm.State
import org.eclipse.gemoc.example.k3fsm.Transition
import org.eclipse.emf.common.util.EList
	
import static extension org.eclipse.gemoc.example.k3fsm.k3dsa.TransitionAspect.*
//import static extension org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.*			
import static extension org.eclipse.gemoc.example.k3fsm.k3dsa.StateAspect.*			

@Aspect(className=FSM)
class FSMAspect {
	@Step 												
	@InitializeModel									// <1>
	def void initializeModel(EList<String> args){
		if (_self.currentState === null){
			_self.currentState = _self.initialState;
		}
		if (_self.unprocessedString === null || _self.unprocessedString.isEmpty){			
	 		_self.unprocessedString = args.get(0);
		}
		if (_self.consumedString === null){
			_self.consumedString = "";
		}
		if (_self.producedString === null){
			_self.producedString = "";
		}
	}
	@Step	
	@Main												// <2>
	def void main() {
    	try {
    		while (!_self.unprocessedString.isEmpty) {
    			_self.currentState.step(_self.unprocessedString)
    		}    		
		} catch (FSMRuntimeException nt){
			//println("Stopped due to "+nt.message)
		}
		//println("unprocessed string: "+_self.unprocessedString)
		//println("processed string: "+_self.consumedString)
		//println("produced string: "+_self.producedString)
	}

}
@Aspect(className=State)
class StateAspect {
	@Step												// <3>
	def void step(String inputString) {
		// Get the valid transitions	
		var validTransitions =  _self.outgoingTransitions.filter[t | 
			if (t.input !== null){
				inputString.startsWith(t.input)
			}else{
				false
			}
		]
		if(validTransitions.empty) {
			//throw new FSMRuntimeException("No Transition")
			validTransitions =  _self.outgoingTransitions.filter[t | t.input === null]
		}
		if(validTransitions.empty) {
			throw new FSMRuntimeException("No Transition")
		}
		if(validTransitions.size > 1) {
			throw new FSMRuntimeException("Non Determinism")
			
		}
		// Fire transition		
		validTransitions.get(0).fire()
	}
}

@Aspect(className=Transition)
class TransitionAspect {
	
	@Step												// <4>
	def void fire() {
		//println("Firing " + _self.name + " and entering " + _self.target.name)
		val fsm = _self.source.owningFSM
		fsm.currentState = _self.target
		if (_self.output !== null){
			if (fsm.producedString !== null){
				fsm.producedString = fsm.producedString + _self.output
			}else{
				fsm.producedString = _self.output
			}
		}
		if (_self.input !== null){
			if (fsm.consumedString !== null){
				fsm.consumedString = fsm.consumedString + _self.input
			}else{
				fsm.consumedString = _self.input
			}
			fsm.unprocessedString = fsm.unprocessedString.substring(_self.input.length)
		}
	}
}

class FSMRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}