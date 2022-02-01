package org.imt.k3tdl.k3dsa

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.LiteralValueUse
import org.etsi.mts.tdl.StructuredDataInstance
import org.etsi.mts.tdl.ParameterBinding
import org.imt.pssm.reactive.model.statemachines.BooleanAttribute
import org.imt.pssm.reactive.model.statemachines.BooleanAttributeValue
import org.imt.pssm.reactive.model.statemachines.CallEventOccurrence
import org.imt.pssm.reactive.model.statemachines.IntegerAttribute
import org.imt.pssm.reactive.model.statemachines.IntegerAttributeValue
import org.imt.pssm.reactive.model.statemachines.Signal
import org.imt.pssm.reactive.model.statemachines.SignalEventOccurrence
import org.imt.pssm.reactive.model.statemachines.StatemachinesFactory
import org.imt.pssm.reactive.model.statemachines.StringAttribute
import org.imt.pssm.reactive.model.statemachines.StringAttributeValue
import org.imt.pssm.reactive.model.statemachines.Operation
import static extension org.imt.k3tdl.k3dsa.DataInstanceUseAspect.*
import static extension org.imt.k3tdl.k3dsa.DataTypeAspect.*


class PSSMEventOccurrenceCreator {
	
	Resource MUTResource;
	boolean isAssertion;
	String DSLPath;
	
	def EObject createEventOccurrence(DataInstanceUse _self, Resource MUTResource, boolean isAssertion, String DSLPath){
		this.MUTResource = MUTResource
		this.isAssertion = isAssertion
		this.DSLPath = DSLPath
		if (_self.dataInstance.dataType.validName.equals("SignalEventOccurrence")){
			return createSignalEventOccurrence(_self)
		}else if (_self.dataInstance.dataType.validName.equals("CallEventOccurrence")){
			return createCallEventOccurrence(_self)
		}
	}
	def EObject createSignalEventOccurrence(DataInstanceUse _self){
		var SignalEventOccurrence signal_occurrence = StatemachinesFactory.eINSTANCE.createSignalEventOccurrence
		if (_self.dataInstance instanceof StructuredDataInstance && 
			(_self.dataInstance as StructuredDataInstance).memberAssignment.size>0){//check the member assignments
			val signalMember = (_self.dataInstance as StructuredDataInstance).memberAssignment.findFirst[
				ma | ma.member.name.equals("signal")]
			if (signalMember !== null && signalMember.memberSpec instanceof DataInstanceUse){
				signal_occurrence.signal = (signalMember.memberSpec as DataInstanceUse).getMatchedMUTElement(MUTResource, isAssertion, DSLPath) as Signal
			}
		}
		for (i : 0 ..<_self.argument.size){//check the parameter bindings
			val parameterBinding = _self.argument.get(i)
			if (parameterBinding.parameter.dataType.validName.equals("Signal")){
				signal_occurrence.signal = (parameterBinding.dataUse as DataInstanceUse).
					getMatchedMUTElement(MUTResource, isAssertion, DSLPath) as Signal
			}else if (parameterBinding.parameter.dataType.validName.equals("AttributeValue")){
				var List<DataInstanceUse> tdlAttributeValues = new ArrayList<DataInstanceUse>
				val paramValue = parameterBinding.dataUse as DataInstanceUse
				if (paramValue.item === null || paramValue.item.size <= 0){
					tdlAttributeValues.add(parameterBinding.dataUse as DataInstanceUse)
				}else {
					for (j:0 ..<paramValue.item.size){
						tdlAttributeValues.add(paramValue.item.get(j) as DataInstanceUse)
					}
				}
				for (j:0 ..<tdlAttributeValues.size){
					var tdlAttributeValueUse = tdlAttributeValues.get(j)
					val tdlAttributeValue = tdlAttributeValueUse.dataInstance as StructuredDataInstance
					var DataUse attributePropertyValue;
					var DataUse valuePropertyValue;
					//get the 'attribute' and 'value' properties of the AttributeValue, a long with their values
					if (tdlAttributeValue.memberAssignment.size>0){
						for (k:0 ..<tdlAttributeValue.memberAssignment.size){
							if (tdlAttributeValue.memberAssignment.get(k).member.name.equals("attribute")){
								attributePropertyValue = tdlAttributeValue.memberAssignment.get(k).memberSpec
							}else if (tdlAttributeValue.memberAssignment.get(k).member.name.equals("value")){
								valuePropertyValue = tdlAttributeValue.memberAssignment.get(k).memberSpec
							}
						}
					}
					if (tdlAttributeValueUse.argument.size>0){//check parameterBindings
						for (k:0 ..<tdlAttributeValueUse.argument.size){
							if (tdlAttributeValueUse.argument.get(k).parameter.name.equals("attribute")){
								attributePropertyValue = tdlAttributeValueUse.argument.get(k).dataUse
							}else if (tdlAttributeValueUse.argument.get(k).parameter.name.equals("value")){
								valuePropertyValue = tdlAttributeValueUse.argument.get(k).dataUse
							}
						}
					}
					var ArrayList<EObject> rootElements = new ArrayList
					rootElements.addAll(signal_occurrence.signal.attributes)
					var value = (valuePropertyValue as LiteralValueUse).value
					value = value.substring(1, value.length-1)
					if (tdlAttributeValueUse.dataInstance.dataType.validName.equals("IntegerAttributeValue")){	
						var IntegerAttributeValue integerAttributeValue = StatemachinesFactory.eINSTANCE.createIntegerAttributeValue
						integerAttributeValue.attribute = (attributePropertyValue as DataInstanceUse).
							getMatchedMUTElement(rootElements, MUTResource, isAssertion, DSLPath) as IntegerAttribute
						integerAttributeValue.value = Integer.parseInt(value)
						signal_occurrence.attributeValues.add(integerAttributeValue)
					}else if (tdlAttributeValueUse.dataInstance.dataType.validName.equals("StringAttributeValue")){
						var StringAttributeValue stringAttributeValue = StatemachinesFactory.eINSTANCE.createStringAttributeValue
						stringAttributeValue.attribute = (attributePropertyValue as DataInstanceUse).
							getMatchedMUTElement(rootElements, MUTResource, isAssertion, DSLPath) as StringAttribute
						stringAttributeValue.value = value
						signal_occurrence.attributeValues.add(stringAttributeValue)
					}else if (tdlAttributeValueUse.dataInstance.dataType.validName.equals("BooleanAttributeValue")){
						var BooleanAttributeValue booleanAttributeValue = StatemachinesFactory.eINSTANCE.createBooleanAttributeValue
						booleanAttributeValue.attribute = (attributePropertyValue as DataInstanceUse).
							getMatchedMUTElement(rootElements, MUTResource, isAssertion, DSLPath) as BooleanAttribute
						booleanAttributeValue.value = Boolean.parseBoolean(value)
						signal_occurrence.attributeValues.add(booleanAttributeValue)
					}
				}
			}
		}
		return signal_occurrence
	}
	
	def EObject createCallEventOccurrence(DataInstanceUse _self){
		var CallEventOccurrence call_occurrence = StatemachinesFactory.eINSTANCE.createCallEventOccurrence
		if (_self.dataInstance instanceof StructuredDataInstance && 
			(_self.dataInstance as StructuredDataInstance).memberAssignment.size>0){//check the member assignments
			val operationMember = (_self.dataInstance as StructuredDataInstance).memberAssignment.findFirst[
				ma | ma.member.name.equals("operation")]
			if (operationMember !== null && operationMember.memberSpec instanceof DataInstanceUse){
				call_occurrence.operation = (operationMember.memberSpec as DataInstanceUse).getMatchedMUTElement(MUTResource, isAssertion, DSLPath) as Operation
			}
		}
		for (i : 0 ..<_self.argument.size){//check the parameter bindings
			val parameterBinding = _self.argument.get(i)
			if (parameterBinding.parameter.dataType.validName.equals("Operation")){
				call_occurrence.operation = (parameterBinding.dataUse as DataInstanceUse).
					getMatchedMUTElement(MUTResource, isAssertion, DSLPath) as Operation
			}else if (parameterBinding.parameter.dataType.validName.equals("AttributeValue")){
				if (parameterBinding.parameter.name.equals("inParameterValues")){
					createParameters(OPERATION_IN_PARAMETER, parameterBinding, call_occurrence)
				}
				else if (parameterBinding.parameter.name.equals("outParameterValues")){
					createParameters(OPERATION_OUT_PARAMETER, parameterBinding, call_occurrence)
				}
				else if (parameterBinding.parameter.name.equals("returnValue")){
					createParameters(OPERATION_RETURN_PARAMETER, parameterBinding, call_occurrence)
				}
				
			}
		}
		return call_occurrence
	}
	
	static String OPERATION_IN_PARAMETER = "Operation inParameters";
	static String OPERATION_OUT_PARAMETER = "Operation outParameters";
	static String OPERATION_RETURN_PARAMETER = "Operation returnParameter";
	
	def void createParameters(String parameterType, ParameterBinding parameterBinding, CallEventOccurrence call_occurrence){
		var List<DataInstanceUse> tdlAttributeValues = new ArrayList<DataInstanceUse>
		val paramValue = parameterBinding.dataUse as DataInstanceUse
		if (paramValue.item === null || paramValue.item.size <= 0){
			tdlAttributeValues.add(parameterBinding.dataUse as DataInstanceUse)
		}else {
			for (j:0 ..<paramValue.item.size){
				tdlAttributeValues.add(paramValue.item.get(j) as DataInstanceUse)
			}
		}
		for (j:0 ..<tdlAttributeValues.size){
			var tdlAttributeValueUse = tdlAttributeValues.get(j)
			val tdlAttributeValue = tdlAttributeValueUse.dataInstance as StructuredDataInstance
			var DataUse attributePropertyValue;
			var DataUse valuePropertyValue;
			//get the 'attribute' and 'value' properties of the AttributeValue, a long with their values
			if (tdlAttributeValue.memberAssignment.size>0){
				for (k:0 ..<tdlAttributeValue.memberAssignment.size){
					if (tdlAttributeValue.memberAssignment.get(k).member.name.equals("attribute")){
						attributePropertyValue = tdlAttributeValue.memberAssignment.get(k).memberSpec
					}else if (tdlAttributeValue.memberAssignment.get(k).member.name.equals("value")){
						valuePropertyValue = tdlAttributeValue.memberAssignment.get(k).memberSpec
					}
				}
			}
			if (tdlAttributeValueUse.argument.size>0){//check parameterBindings
				for (k:0 ..<tdlAttributeValueUse.argument.size){
					if (tdlAttributeValueUse.argument.get(k).parameter.name.equals("attribute")){
						attributePropertyValue = tdlAttributeValueUse.argument.get(k).dataUse
					}else if (tdlAttributeValueUse.argument.get(k).parameter.name.equals("value")){
						valuePropertyValue = tdlAttributeValueUse.argument.get(k).dataUse
					}
				}
			}
			var ArrayList<EObject> rootElements = new ArrayList
			if (parameterType == OPERATION_IN_PARAMETER){
				rootElements.addAll(call_occurrence.operation.inParameters)
			}
			else if (parameterType == OPERATION_OUT_PARAMETER){
				rootElements.addAll(call_occurrence.operation.outParameters)
			}
			else if (parameterType == OPERATION_RETURN_PARAMETER){
				rootElements.add(call_occurrence.operation.^return)
			}
			var value = (valuePropertyValue as LiteralValueUse).value
			value = value.substring(1, value.length-1)
			if (tdlAttributeValueUse.dataInstance.dataType.validName.equals("IntegerAttributeValue")){	
				var IntegerAttributeValue integerAttributeValue = StatemachinesFactory.eINSTANCE.createIntegerAttributeValue
				integerAttributeValue.attribute = (attributePropertyValue as DataInstanceUse).
					getMatchedMUTElement(rootElements, MUTResource, isAssertion, DSLPath) as IntegerAttribute
				integerAttributeValue.value = Integer.parseInt(value)
				if (parameterType == OPERATION_IN_PARAMETER){
					call_occurrence.inParameterValues.add(integerAttributeValue)
				}
				else if (parameterType == OPERATION_OUT_PARAMETER){
					call_occurrence.outParameterValues.add(integerAttributeValue)
				}
				else if (parameterType == OPERATION_RETURN_PARAMETER){
					call_occurrence.returnValue = integerAttributeValue
				}
				
			}else if (tdlAttributeValueUse.dataInstance.dataType.validName.equals("StringAttributeValue")){
				var StringAttributeValue stringAttributeValue = StatemachinesFactory.eINSTANCE.createStringAttributeValue
				stringAttributeValue.attribute = (attributePropertyValue as DataInstanceUse).
					getMatchedMUTElement(rootElements, MUTResource, isAssertion, DSLPath) as StringAttribute
				stringAttributeValue.value = value
				if (parameterType == OPERATION_IN_PARAMETER){
					call_occurrence.inParameterValues.add(stringAttributeValue)
				}
				else if (parameterType == OPERATION_OUT_PARAMETER){
					call_occurrence.outParameterValues.add(stringAttributeValue)
				}
				else if (parameterType == OPERATION_RETURN_PARAMETER){
					call_occurrence.returnValue = stringAttributeValue
				}
			}else if (tdlAttributeValueUse.dataInstance.dataType.validName.equals("BooleanAttributeValue")){
				var BooleanAttributeValue booleanAttributeValue = StatemachinesFactory.eINSTANCE.createBooleanAttributeValue
				booleanAttributeValue.attribute = (attributePropertyValue as DataInstanceUse).
					getMatchedMUTElement(rootElements, MUTResource, isAssertion, DSLPath) as BooleanAttribute
				booleanAttributeValue.value = Boolean.parseBoolean(value)
				if (parameterType == OPERATION_IN_PARAMETER){
					call_occurrence.inParameterValues.add(booleanAttributeValue)
				}
				else if (parameterType == OPERATION_OUT_PARAMETER){
					call_occurrence.outParameterValues.add(booleanAttributeValue)
				}
				else if (parameterType == OPERATION_RETURN_PARAMETER){
					call_occurrence.returnValue = booleanAttributeValue
				}
			}
		}
	}
}