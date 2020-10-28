package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.Assertion;
import org.imt.k3tdl.k3dsa.AssertionAspectAssertionAspectProperties;

@SuppressWarnings("all")
public class AssertionAspectAssertionAspectContext {
  public static final AssertionAspectAssertionAspectContext INSTANCE = new AssertionAspectAssertionAspectContext();
  
  public static AssertionAspectAssertionAspectProperties getSelf(final Assertion _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.AssertionAspectAssertionAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<Assertion, AssertionAspectAssertionAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.Assertion, org.imt.k3tdl.k3dsa.AssertionAspectAssertionAspectProperties>();
  
  public Map<Assertion, AssertionAspectAssertionAspectProperties> getMap() {
    return map;
  }
}
