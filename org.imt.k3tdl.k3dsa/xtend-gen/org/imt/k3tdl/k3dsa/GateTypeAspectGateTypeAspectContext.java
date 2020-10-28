package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.GateType;
import org.imt.k3tdl.k3dsa.GateTypeAspectGateTypeAspectProperties;

@SuppressWarnings("all")
public class GateTypeAspectGateTypeAspectContext {
  public static final GateTypeAspectGateTypeAspectContext INSTANCE = new GateTypeAspectGateTypeAspectContext();
  
  public static GateTypeAspectGateTypeAspectProperties getSelf(final GateType _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.GateTypeAspectGateTypeAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<GateType, GateTypeAspectGateTypeAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.GateType, org.imt.k3tdl.k3dsa.GateTypeAspectGateTypeAspectProperties>();
  
  public Map<GateType, GateTypeAspectGateTypeAspectProperties> getMap() {
    return map;
  }
}
