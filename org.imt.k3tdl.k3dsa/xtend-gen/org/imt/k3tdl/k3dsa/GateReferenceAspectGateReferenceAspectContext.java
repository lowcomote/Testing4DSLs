package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.GateReference;
import org.imt.k3tdl.k3dsa.GateReferenceAspectGateReferenceAspectProperties;

@SuppressWarnings("all")
public class GateReferenceAspectGateReferenceAspectContext {
  public static final GateReferenceAspectGateReferenceAspectContext INSTANCE = new GateReferenceAspectGateReferenceAspectContext();
  
  public static GateReferenceAspectGateReferenceAspectProperties getSelf(final GateReference _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.GateReferenceAspectGateReferenceAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<GateReference, GateReferenceAspectGateReferenceAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.GateReference, org.imt.k3tdl.k3dsa.GateReferenceAspectGateReferenceAspectProperties>();
  
  public Map<GateReference, GateReferenceAspectGateReferenceAspectProperties> getMap() {
    return map;
  }
}
