package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectProperties;

@SuppressWarnings("all")
public class TestDescriptionAspectTestDescriptionAspectContext {
  public static final TestDescriptionAspectTestDescriptionAspectContext INSTANCE = new TestDescriptionAspectTestDescriptionAspectContext();
  
  public static TestDescriptionAspectTestDescriptionAspectProperties getSelf(final TestDescription _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<TestDescription, TestDescriptionAspectTestDescriptionAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.TestDescription, org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectProperties>();
  
  public Map<TestDescription, TestDescriptionAspectTestDescriptionAspectProperties> getMap() {
    return map;
  }
}
