package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel;
import fr.inria.diverse.k3.al.annotationprocessor.Main;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.etsi.mts.tdl.Interaction;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties;
import org.imt.k3tdl.k3dsa.TDLRuntimeException;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;

@Aspect(className = org.etsi.mts.tdl.Package.class)
@SuppressWarnings("all")
public class PackageAspect {
  @Step
  @InitializeModel
  public static void initializeModel(final org.etsi.mts.tdl.Package _self) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void initializeModel()
    if (_self instanceof org.etsi.mts.tdl.Package){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.PackageAspect._privk3_initializeModel(_self_, (org.etsi.mts.tdl.Package)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "Package", "initializeModel");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  @Step
  @Main
  public static void main(final org.etsi.mts.tdl.Package _self) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void main()
    if (_self instanceof org.etsi.mts.tdl.Package){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.PackageAspect._privk3_main(_self_, (org.etsi.mts.tdl.Package)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "Package", "main");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  public static List<TestDescription> testcases(final org.etsi.mts.tdl.Package _self) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    Object result = null;
    // #DispatchPointCut_before# List<TestDescription> testcases()
    if (_self instanceof org.etsi.mts.tdl.Package){
    	result = org.imt.k3tdl.k3dsa.PackageAspect._privk3_testcases(_self_, (org.etsi.mts.tdl.Package)_self);
    };
    return (java.util.List<org.etsi.mts.tdl.TestDescription>)result;
  }
  
  public static void testcases(final org.etsi.mts.tdl.Package _self, final List<TestDescription> testcases) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void testcases(List<TestDescription>)
    if (_self instanceof org.etsi.mts.tdl.Package){
    	org.imt.k3tdl.k3dsa.PackageAspect._privk3_testcases(_self_, (org.etsi.mts.tdl.Package)_self,testcases);
    };
  }
  
  public static TestDescription enabledTestCase(final org.etsi.mts.tdl.Package _self) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    Object result = null;
    // #DispatchPointCut_before# TestDescription enabledTestCase()
    if (_self instanceof org.etsi.mts.tdl.Package){
    	result = org.imt.k3tdl.k3dsa.PackageAspect._privk3_enabledTestCase(_self_, (org.etsi.mts.tdl.Package)_self);
    };
    return (org.etsi.mts.tdl.TestDescription)result;
  }
  
  public static void enabledTestCase(final org.etsi.mts.tdl.Package _self, final TestDescription enabledTestCase) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void enabledTestCase(TestDescription)
    if (_self instanceof org.etsi.mts.tdl.Package){
    	org.imt.k3tdl.k3dsa.PackageAspect._privk3_enabledTestCase(_self_, (org.etsi.mts.tdl.Package)_self,enabledTestCase);
    };
  }
  
  public static Interaction currentInteraction(final org.etsi.mts.tdl.Package _self) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    Object result = null;
    // #DispatchPointCut_before# Interaction currentInteraction()
    if (_self instanceof org.etsi.mts.tdl.Package){
    	result = org.imt.k3tdl.k3dsa.PackageAspect._privk3_currentInteraction(_self_, (org.etsi.mts.tdl.Package)_self);
    };
    return (org.etsi.mts.tdl.Interaction)result;
  }
  
  public static void currentInteraction(final org.etsi.mts.tdl.Package _self, final Interaction currentInteraction) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void currentInteraction(Interaction)
    if (_self instanceof org.etsi.mts.tdl.Package){
    	org.imt.k3tdl.k3dsa.PackageAspect._privk3_currentInteraction(_self_, (org.etsi.mts.tdl.Package)_self,currentInteraction);
    };
  }
  
  public static String verdictValue(final org.etsi.mts.tdl.Package _self) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    Object result = null;
    // #DispatchPointCut_before# String verdictValue()
    if (_self instanceof org.etsi.mts.tdl.Package){
    	result = org.imt.k3tdl.k3dsa.PackageAspect._privk3_verdictValue(_self_, (org.etsi.mts.tdl.Package)_self);
    };
    return (java.lang.String)result;
  }
  
  public static void verdictValue(final org.etsi.mts.tdl.Package _self, final String verdictValue) {
    final org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties _self_ = org.imt.k3tdl.k3dsa.PackageAspectPackageAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void verdictValue(String)
    if (_self instanceof org.etsi.mts.tdl.Package){
    	org.imt.k3tdl.k3dsa.PackageAspect._privk3_verdictValue(_self_, (org.etsi.mts.tdl.Package)_self,verdictValue);
    };
  }
  
  protected static void _privk3_initializeModel(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self) {
    final Function1<PackageableElement, Boolean> _function = (PackageableElement p) -> {
      return Boolean.valueOf((p instanceof TestDescription));
    };
    Iterable<PackageableElement> _filter = IterableExtensions.<PackageableElement>filter(_self.getPackagedElement(), _function);
    for (final Object o : _filter) {
      PackageAspect.testcases(_self).add(((TestDescription) o));
    }
    int _size = PackageAspect.testcases(_self).size();
    boolean _equals = (_size == 0);
    if (_equals) {
      String _name = _self.getName();
      String _plus = ("There is no test case in the package " + _name);
      String _plus_1 = (_plus + "to be executed");
      InputOutput.<String>println(_plus_1);
    }
  }
  
  protected static void _privk3_main(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self) {
    try {
      List<TestDescription> _testcases = PackageAspect.testcases(_self);
      for (final TestDescription tc : _testcases) {
        {
          PackageAspect.enabledTestCase(_self, tc);
          TestDescriptionAspect.executeTestCase(PackageAspect.enabledTestCase(_self));
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof TDLRuntimeException) {
        final TDLRuntimeException nt = (TDLRuntimeException)_t;
        String _message = nt.getMessage();
        String _plus = ("Stopped due" + _message);
        InputOutput.<String>println(_plus);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  protected static List<TestDescription> _privk3_testcases(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self) {
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("getTestcases") &&
    			m.getParameterTypes().length == 0) {
    				Object ret = m.invoke(_self);
    				if (ret != null) {
    					return (java.util.List) ret;
    				} else {
    					return null;
    				}
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    return _self_.testcases;
  }
  
  protected static void _privk3_testcases(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self, final List<TestDescription> testcases) {
    boolean setterCalled = false;
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("setTestcases")
    				&& m.getParameterTypes().length == 1) {
    			m.invoke(_self, testcases);
    			setterCalled = true;
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    if (!setterCalled) {
    	_self_.testcases = testcases;
    }
  }
  
  protected static TestDescription _privk3_enabledTestCase(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self) {
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("getEnabledTestCase") &&
    			m.getParameterTypes().length == 0) {
    				Object ret = m.invoke(_self);
    				if (ret != null) {
    					return (org.etsi.mts.tdl.TestDescription) ret;
    				} else {
    					return null;
    				}
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    return _self_.enabledTestCase;
  }
  
  protected static void _privk3_enabledTestCase(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self, final TestDescription enabledTestCase) {
    boolean setterCalled = false;
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("setEnabledTestCase")
    				&& m.getParameterTypes().length == 1) {
    			m.invoke(_self, enabledTestCase);
    			setterCalled = true;
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    if (!setterCalled) {
    	_self_.enabledTestCase = enabledTestCase;
    }
  }
  
  protected static Interaction _privk3_currentInteraction(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self) {
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("getCurrentInteraction") &&
    			m.getParameterTypes().length == 0) {
    				Object ret = m.invoke(_self);
    				if (ret != null) {
    					return (org.etsi.mts.tdl.Interaction) ret;
    				} else {
    					return null;
    				}
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    return _self_.currentInteraction;
  }
  
  protected static void _privk3_currentInteraction(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self, final Interaction currentInteraction) {
    boolean setterCalled = false;
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("setCurrentInteraction")
    				&& m.getParameterTypes().length == 1) {
    			m.invoke(_self, currentInteraction);
    			setterCalled = true;
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    if (!setterCalled) {
    	_self_.currentInteraction = currentInteraction;
    }
  }
  
  protected static String _privk3_verdictValue(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self) {
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("getVerdictValue") &&
    			m.getParameterTypes().length == 0) {
    				Object ret = m.invoke(_self);
    				if (ret != null) {
    					return (java.lang.String) ret;
    				} else {
    					return null;
    				}
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    return _self_.verdictValue;
  }
  
  protected static void _privk3_verdictValue(final PackageAspectPackageAspectProperties _self_, final org.etsi.mts.tdl.Package _self, final String verdictValue) {
    boolean setterCalled = false;
    try {
    	for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    		if (m.getName().equals("setVerdictValue")
    				&& m.getParameterTypes().length == 1) {
    			m.invoke(_self, verdictValue);
    			setterCalled = true;
    		}
    	}
    } catch (Exception e) {
    	// Chut !
    }
    if (!setterCalled) {
    	_self_.verdictValue = verdictValue;
    }
  }
}
