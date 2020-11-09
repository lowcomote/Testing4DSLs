package org.etsi.mts.tdl.scoping;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.etsi.mts.tdl.Action;
import org.etsi.mts.tdl.ActionReference;
import org.etsi.mts.tdl.Assignment;
import org.etsi.mts.tdl.Behaviour;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.ComponentInstanceBinding;
import org.etsi.mts.tdl.Connection;
import org.etsi.mts.tdl.DataElementMapping;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.Element;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Extension;
import org.etsi.mts.tdl.FormalParameterUse;
import org.etsi.mts.tdl.FunctionCall;
import org.etsi.mts.tdl.GateInstance;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.LocalExpression;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.MemberReference;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.Parameter;
import org.etsi.mts.tdl.ParameterBinding;
import org.etsi.mts.tdl.ParameterMapping;
import org.etsi.mts.tdl.ProcedureCall;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.TestConfiguration;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.TestDescriptionReference;
import org.etsi.mts.tdl.Timer;
import org.etsi.mts.tdl.TimerOperation;
import org.etsi.mts.tdl.ValueAssignment;
import org.etsi.mts.tdl.Variable;
import org.etsi.mts.tdl.VariableUse;
import org.etsi.mts.tdl.tdlPackage;
import org.etsi.mts.tdl.structuredobjectives.StructuredTestObjective;

import com.google.common.base.Function;
import com.google.inject.Inject;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class TDLScopeProvider extends AbstractDeclarativeScopeProvider {
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Override
	public IScope getScope(EObject context, EReference reference) {
		if (PackageableElement.class.isAssignableFrom(reference.getEType().getInstanceClass())
				&& !(context instanceof ElementImport)) {
			EList<EObject> elements = getScopedElementsOfType(context, reference.getEType().getInstanceClass());
			return Scopes.scopeFor(elements);
		}
		if (reference.getEType().getInstanceClass() == GateInstance.class) {
			if (context instanceof GateReference) {
				GateReference gate = (GateReference) context;
				if (gate.getComponent().getType()!=null) {
					IScope scope = Scopes.scopeFor(gate.getComponent().getType().allGates());
					return scope;
				}
			}
		} else if (reference.getEType().getInstanceClass() == ComponentInstance.class) {
			if (context instanceof Behaviour || context instanceof Block || context instanceof LocalExpression || context instanceof VariableUse) {
				TestDescription testDescription = getTestDescription((Element) context);
				if (testDescription!=null) {
					TestConfiguration configuration = testDescription.getTestConfiguration();
					IScope scope = Scopes.scopeFor(configuration.getComponentInstance());
					return scope;
				}
			} else if (context instanceof GateReference) {
				TestConfiguration configuration = getTestConfiguration((Element) context);
				EList<ComponentInstance> components = configuration.getComponentInstance();
				IScope scope = Scopes.scopeFor(components);
				return scope;
			} else if (context instanceof ComponentInstanceBinding) {
				//assume actual
				TestDescription testDescription = getTestDescription((Element) context);
				if (reference.getFeatureID() == tdlPackage.COMPONENT_INSTANCE_BINDING__FORMAL_COMPONENT) {
					testDescription = ((TestDescriptionReference)context.eContainer()).getTestDescription();
				}
				TestConfiguration configuration = testDescription.getTestConfiguration();
				EList<ComponentInstance> components = configuration.getComponentInstance();
				IScope scope = Scopes.scopeFor(components);
				return scope;
			} 
		} else if (reference.getEType().getInstanceClass() == GateReference.class) {
			//TODO: safety checks in case invalid configuration is specified
			//TODO: only suggest connected gates as targets?

			if (context instanceof Behaviour || context instanceof Target || context instanceof Block) {
				TestConfiguration configuration = getTestDescription((Element) context).getTestConfiguration();
				EList<EObject> elements = new BasicEList<>();
				for (Connection c : configuration.getConnection()) {
					if (context instanceof Message) {
						//TODO: quick hack until a better solution is found
						ICompositeNode oppositeNode = NodeModelUtils.findActualNodeFor(((Message) context).getTarget().get(0));
						if (oppositeNode != null) {
							String targetGateName = oppositeNode.getText()
									.replaceAll("(?s)where\\s+it\\s+is.+", "")
									.replaceAll("(?s)with\\s*\\{.+", "")
									.trim();
							String[] split = targetGateName.split("\\.");
							if (split.length == 2) {
								String gate = split[1];
								String component = split[0];
								if (component.equals(c.getEndPoint().get(0).getComponent().getName()) &&
										gate.equals(c.getEndPoint().get(0).getGate().getName())) {
									elements.add(c.getEndPoint().get(1));
								} else if (component.equals(c.getEndPoint().get(1).getComponent().getName()) &&
										gate.equals(c.getEndPoint().get(1).getGate().getName())) {
									elements.add(c.getEndPoint().get(0));
								}
							} else {
								//TODO: handle?
							}
						} else {
							//TODO: double check
							elements.addAll(c.getEndPoint());
						}
						
//						GateReference opposite = ((Message) context).getTarget().get(0).getTargetGate();
//						if (c.getEndPoint().get(0) == opposite) {
//							elements.add(c.getEndPoint().get(1));
//						} else if (c.getEndPoint().get(1) == opposite) {
//							elements.add(c.getEndPoint().get(0));
//						}
					} else if (context instanceof Target) {
						GateReference opposite = null;
						if (context.eContainer() instanceof Message) {
							opposite = ((Message) context.eContainer()).getSourceGate();
						} else {
							opposite = ((ProcedureCall) context.eContainer()).getSourceGate();							
						}
						if (c.getEndPoint().get(0) == opposite) {
							elements.add(c.getEndPoint().get(1));
						} else if (c.getEndPoint().get(1) == opposite) {
							elements.add(c.getEndPoint().get(0));
						}
					} else {
						elements.addAll(c.getEndPoint());
					}
					
				}

				IScope scope = Scopes.scopeFor(elements, new Function<EObject, QualifiedName>() {
			        @Override 
			        public QualifiedName apply(EObject o) {
			        	GateReference r = (GateReference) o;
			        	String n = r.getComponent().getName()+"."+r.getGate().getName();
			            QualifiedName qualifiedName = qualifiedNameConverter.toQualifiedName(n);
			            return qualifiedName;
			        }

			    }, IScope.NULLSCOPE); 

				return scope;
			}
		} else if (Parameter.class.isAssignableFrom(reference.getEType().getInstanceClass())) {
			if (context instanceof MemberAssignment) {
				if (context.eContainer() instanceof StructuredDataInstance && ((StructuredDataInstance)context.eContainer()).getDataType() instanceof StructuredDataType) {
					IScope scope = Scopes.scopeFor(((StructuredDataType)((StructuredDataInstance)context.eContainer()).getDataType()).allMembers());
					return scope;
				}
			} else if (context instanceof ParameterMapping) {
				if (context.eContainer() instanceof DataElementMapping) {
					if (((DataElementMapping)context.eContainer()).getMappableDataElement() instanceof StructuredDataType) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((DataElementMapping)context.eContainer()).getMappableDataElement()).allMembers());
						return scope;
					} else if (((DataElementMapping)context.eContainer()).getMappableDataElement() instanceof Action) {
							IScope scope = Scopes.scopeFor(((Action)((DataElementMapping)context.eContainer()).getMappableDataElement()).getFormalParameter());
							return scope;
					}
				}				
			} else if (context instanceof ParameterBinding) {
				if (context.eContainer() instanceof DataInstanceUse) {
					if (((DataInstanceUse)context.eContainer()).getDataInstance() instanceof StructuredDataInstance) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((StructuredDataInstance)((DataInstanceUse)context.eContainer()).getDataInstance()).getDataType()).allMembers());
						return scope;
					} else if (((DataInstanceUse)context.eContainer()).getDataType() instanceof StructuredDataType) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((DataInstanceUse)context.eContainer()).getDataType()).allMembers());
						return scope;
					} else if (context.eContainer().eContainer() instanceof MemberAssignment) {
						DataType dataType = ((MemberAssignment)context
								.eContainer()
								.eContainer())
								.getMember()
								.getDataType();
						if (dataType!=null) {
							IScope scope = Scopes.scopeFor(((StructuredDataType)dataType)
									.allMembers());
							return scope;
						}
					} else if (context.eContainer().eContainer() instanceof ParameterBinding) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((ParameterBinding)context.eContainer().eContainer()).getParameter().getDataType()).allMembers());
						return scope;
					}
				} else if (context.eContainer() instanceof FunctionCall) {
//					if (((FunctionCall)context.eContainer()).getFunction().get instanceof StructuredDataInstance) {
						IScope scope = Scopes.scopeFor(((FunctionCall)context.eContainer()).getFunction().getFormalParameter());
						return scope;
//					}
				} else if (context.eContainer() instanceof ActionReference) {
//					if (((FunctionCall)context.eContainer()).getFunction().get instanceof StructuredDataInstance) {
						IScope scope = Scopes.scopeFor(((ActionReference)context.eContainer()).getAction().getFormalParameter());
						return scope;
//					}
				} else if (context.eContainer() instanceof TestDescriptionReference) {
//					if (((FunctionCall)context.eContainer()).getFunction().get instanceof StructuredDataInstance) {
						IScope scope = Scopes.scopeFor(((TestDescriptionReference)context.eContainer()).getTestDescription().getFormalParameter());
						return scope;
//					}
				} else if (context.eContainer() instanceof FormalParameterUse) {
					IScope scope = Scopes.scopeFor(((StructuredDataType)((FormalParameterUse)context.eContainer()).getParameter().getDataType()).allMembers());
					return scope;
				} else if (context.eContainer() instanceof VariableUse) {
					IScope scope = Scopes.scopeFor(((StructuredDataType)((VariableUse)context.eContainer()).getVariable().getDataType()).allMembers());
					return scope;
				} else if (context.eContainer() instanceof ProcedureCall) {
//					if (((FunctionCall)context.eContainer()).getFunction().get instanceof StructuredDataInstance) {
						IScope scope = Scopes.scopeFor(((ProcedureCall)context.eContainer()).getSignature().getParameter());
						return scope;
//					}
				}
			} else if (context instanceof MemberReference) {
				if (((DataUse)context.eContainer()).getReduction().indexOf(context)>0) {
					EObject targetContext = ((DataUse)context.eContainer()).getReduction().get(((DataUse)context.eContainer()).getReduction().indexOf(context)-1);
					if (((MemberReference)targetContext).getMember()!=null) {
						if (((MemberReference)targetContext).getMember().getDataType() instanceof StructuredDataType) {
							IScope scope = Scopes.scopeFor(((StructuredDataType)((MemberReference)targetContext).getMember().getDataType()).allMembers());
							return scope;
						}
					}
				} 
				if (context.eContainer() instanceof DataInstanceUse) {
					if (((DataInstanceUse)context.eContainer()).getDataInstance() instanceof StructuredDataInstance) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((StructuredDataInstance)((DataInstanceUse)context.eContainer()).getDataInstance()).getDataType()).allMembers());
						return scope;
					}
				} else if (context.eContainer() instanceof FunctionCall) {
					if (((FunctionCall)context.eContainer()).getFunction().getReturnType() instanceof StructuredDataType) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((FunctionCall)context.eContainer()).getFunction().getReturnType()).allMembers());
						return scope;
					}
				} else if (context.eContainer() instanceof FormalParameterUse) {
					if (((FormalParameterUse)context.eContainer()).getParameter().getDataType() instanceof StructuredDataType) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((FormalParameterUse)context.eContainer()).getParameter().getDataType()).allMembers());
						return scope;
					}
				} else if (context.eContainer() instanceof VariableUse) {
					if (((VariableUse)context.eContainer()).getVariable().getDataType() instanceof StructuredDataType) {
						IScope scope = Scopes.scopeFor(((StructuredDataType)((VariableUse)context.eContainer()).getVariable().getDataType()).allMembers());
						return scope;
					}
				} else {
				}
			}
		} else if (reference.getEType().getInstanceClass() == Variable.class) {
			if (context instanceof Assignment) {
				if (((Assignment)context).getComponentInstance()!=null) {
					IScope scope = Scopes.scopeFor(((Assignment)context).getComponentInstance().getType().allVariables());
					return scope;
				}
			}
			if (context instanceof VariableUse) {
				if (((VariableUse)context).getComponentInstance()!=null) {
					IScope scope = Scopes.scopeFor(((VariableUse)context).getComponentInstance().getType().allVariables());
					return scope;
				}
			}
			else if (context instanceof ValueAssignment) {
				if (((Target)context.eContainer()).getTargetGate().getComponent()!=null) {
					IScope scope = Scopes.scopeFor(((Target)context.eContainer()).getTargetGate().getComponent().getType().allVariables());
					return scope;
				}
			}
		} else if (reference.getEType().getInstanceClass() == Timer.class) {
			if (context instanceof TimerOperation) {
				IScope scope = Scopes.scopeFor(((TimerOperation)context).getComponentInstance().getType().allTimers());
				return scope;
			}
		} else if (context instanceof Extension) {
			EList<EObject> elements = getScopedElementsOfType(context, ((Element)context.eContainer()).getClass());
			return Scopes.scopeFor(elements);
		} else {
		}
		
		
		if (context instanceof Block) {
			TestDescription testDescription = getTestDescription((Element) context);
			if (testDescription!=null) {
				TestConfiguration configuration = testDescription.getTestConfiguration();
				IScope scope = Scopes.scopeFor(configuration.getComponentInstance());
				return scope;
			}
		}	
		return super.getScope(context, reference);
	}

	private TestConfiguration getTestConfiguration(Element self) {
		if (self.eContainer()!=null) {
			if (!(self.eContainer() instanceof TestConfiguration)) {
				return getTestConfiguration((Element) self.eContainer());
			} else {
				return (TestConfiguration)self.eContainer();
			}
		} 
		return null;
	}

	
	private TestDescription getTestDescription(Element self) {
		if (self.eContainer()!=null) {
			if (!(self.eContainer() instanceof TestDescription)) {
				return getTestDescription((Element) self.eContainer());
			} else {
				return (TestDescription)self.eContainer();
			}
		} 
		return null; 
	}

	public IScope scope_ElementImport_importedElement(ElementImport context, EReference ref) {
		EList<EObject> elements = new BasicEList<>();
		Package ip = context.getImportedPackage();
		for (PackageableElement e : ip.getPackagedElement()) {
			elements.add(e);
		}
		IScope scope = Scopes.scopeFor(elements);
		return scope;
	}
	
	private Package getPackage(EObject e) {
		if (e.eContainer()!=null) {
			if (e.eContainer() instanceof Package) {
				return (Package)e.eContainer();
			} else {
				return getPackage(e.eContainer());
			}
		} else {
			return (Package)e;
		}
	}
	
	private EList<EObject> getScopedElementsOfType(EObject context, Class c) {
		EList<EObject> elements = new BasicEList<>();
		Package p = getPackage(context);
		
		//within same package
		for (PackageableElement e : p.getPackagedElement()) {
			if (c.isInstance(e)) {
				elements.add(e);
			}
		}
		
		//within imported packages
		for (ElementImport i : p.getImport()) {
			Package ip = i.getImportedPackage();
			if (ip != null) {
				if (i.getImportedElement().isEmpty()) {
					//import all
					for (PackageableElement e : ip.getPackagedElement()) {
						if (c.isInstance(e)) {
							elements.add(e);
						}
					}
				} else {
					//specific imports
					for (PackageableElement e : i.getImportedElement()) {
						if (c.isInstance(e)) {
							elements.add(e);
						}
					}
				}
			}
		}
		return elements;
	}

}