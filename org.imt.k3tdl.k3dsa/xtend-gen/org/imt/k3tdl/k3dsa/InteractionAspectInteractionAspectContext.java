package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.Interaction;
import org.imt.k3tdl.k3dsa.InteractionAspectInteractionAspectProperties;

@SuppressWarnings("all")
public class InteractionAspectInteractionAspectContext {
  public static final InteractionAspectInteractionAspectContext INSTANCE = new InteractionAspectInteractionAspectContext();
  
  public static InteractionAspectInteractionAspectProperties getSelf(final Interaction _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.InteractionAspectInteractionAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<Interaction, InteractionAspectInteractionAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.Interaction, org.imt.k3tdl.k3dsa.InteractionAspectInteractionAspectProperties>();
  
  public Map<Interaction, InteractionAspectInteractionAspectProperties> getMap() {
    return map;
  }
}
