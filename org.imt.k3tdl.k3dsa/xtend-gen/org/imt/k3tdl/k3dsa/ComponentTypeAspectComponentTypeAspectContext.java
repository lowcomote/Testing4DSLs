package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.ComponentType;
import org.imt.k3tdl.k3dsa.ComponentTypeAspectComponentTypeAspectProperties;

@SuppressWarnings("all")
public class ComponentTypeAspectComponentTypeAspectContext {
  public static final ComponentTypeAspectComponentTypeAspectContext INSTANCE = new ComponentTypeAspectComponentTypeAspectContext();
  
  public static ComponentTypeAspectComponentTypeAspectProperties getSelf(final ComponentType _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.ComponentTypeAspectComponentTypeAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<ComponentType, ComponentTypeAspectComponentTypeAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.ComponentType, org.imt.k3tdl.k3dsa.ComponentTypeAspectComponentTypeAspectProperties>();
  
  public Map<ComponentType, ComponentTypeAspectComponentTypeAspectProperties> getMap() {
    return map;
  }
}
