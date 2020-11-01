package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect


import fr.inria.diverse.k3.al.annotationprocessor.Step
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.etsi.mts.tdl.DataResourceMapping
import org.etsi.mts.tdl.SimpleDataInstance
import org.etsi.mts.tdl.SimpleDataType
import org.etsi.mts.tdl.PredefinedFunction
import org.etsi.mts.tdl.UnassignedMemberTreatment
import org.etsi.mts.tdl.Function
import org.etsi.mts.tdl.Action
import org.etsi.mts.tdl.Variable
import org.etsi.mts.tdl.FormalParameter
import org.etsi.mts.tdl.Parameter
import org.etsi.mts.tdl.MappableDataElement
import org.etsi.mts.tdl.DataElementMapping
import org.etsi.mts.tdl.ParameterMapping
import org.etsi.mts.tdl.DataType
import org.etsi.mts.tdl.DataInstance
import org.etsi.mts.tdl.Member
import org.etsi.mts.tdl.StructuredDataInstance
import org.etsi.mts.tdl.MemberAssignment
import org.etsi.mts.tdl.CollectionDataType
import org.etsi.mts.tdl.CollectionDataInstance
import org.etsi.mts.tdl.ProcedureSignature
import org.etsi.mts.tdl.ProcedureParameter
import org.etsi.mts.tdl.StructuredDataType
import org.etsi.mts.tdl.OmitValue
import org.etsi.mts.tdl.PredefinedFunctionCall
import org.etsi.mts.tdl.VariableUse
import org.etsi.mts.tdl.FormalParameterUse
import org.etsi.mts.tdl.DynamicDataUse
import org.etsi.mts.tdl.AnyValueOrOmit
import org.etsi.mts.tdl.AnyValue
import org.etsi.mts.tdl.SpecialValueUse
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.StaticDataUse
import org.etsi.mts.tdl.MemberReference
import org.etsi.mts.tdl.ParameterBinding
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.FunctionCall

@Aspect (className = DataResourceMapping)
class DataResourceMappingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = MappableDataElement)
class MappableDataElementAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataElementMapping)
class DataElementMappingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = ParameterMapping)
class ParameterMappingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataType)
class DataTypeAspect extends MappableDataElementAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataInstance)
class DataInstanceAspect extends MappableDataElementAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = SimpleDataType)
class SimpleDataTypeAspect extends DataTypeAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = SimpleDataInstance)
class SimpleDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = StructuredDataType)
class StructuredDataTypeAspect extends DataTypeAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = Member)
class MemberAspect extends ParameterAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = StructuredDataInstance)
class StructuredDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = MemberAssignment)
class MemberAssignmentAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = CollectionDataType)
class CollectionDataTypeAspect extends DataTypeAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = CollectionDataInstance)
class CollectionDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def void mapDataResource(){
		
	}
}
@Aspect (className = ProcedureSignature)
class ProcedureSignatureAspect extends DataTypeAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = ProcedureParameter)
class ProcedureParameterAspect extends ParameterAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = Parameter)
class ParameterAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = FormalParameter)
class FormalParameterAspect extends ParameterAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = Variable)
class VariableAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = Action)
class ActionAspect extends MappableDataElementAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = Function)
class FunctionAspect extends ActionAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = UnassignedMemberTreatment)
class UnassignedMemberTreatmentAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = PredefinedFunction)
class PredefinedFunctionAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataUse)
class DataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = ParameterBinding)
class ParameterBindingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = MemberReference)
class MemberReferenceAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = StaticDataUse)
class StaticDataUseAspect extends DataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataInstanceUse)
class DataInstanceUseAspect extends StaticDataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = SpecialValueUse)
class SpecialValueUseAspect extends StaticDataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = AnyValue)
class AnyValueAspect extends SpecialValueUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = AnyValueOrOmit)
class AnyValueOrOmitAspect extends SpecialValueUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = OmitValue)
class OmitValueAspect extends SpecialValueUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DynamicDataUse)
class DynamicDataUseAspect extends DataUseAspect{
	def void mapDataResource(){
	}
}
@Aspect (className = FunctionCall)
class FunctionCallAspect extends DynamicDataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = FormalParameterUse)
class FormalParameterUseAspect extends DynamicDataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = VariableUse)
class VariableUseAspect extends DynamicDataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = PredefinedFunctionCall)
class PredefinedFunctionCallAspect extends DynamicDataUseAspect{
	def void mapDataResource(){
		
	}
}