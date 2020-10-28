package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.DataUse;
import org.imt.k3tdl.k3dsa.DataUseAspectDataUseAspectProperties;

@SuppressWarnings("all")
public class DataUseAspectDataUseAspectContext {
  public static final DataUseAspectDataUseAspectContext INSTANCE = new DataUseAspectDataUseAspectContext();
  
  public static DataUseAspectDataUseAspectProperties getSelf(final DataUse _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.DataUseAspectDataUseAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<DataUse, DataUseAspectDataUseAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.DataUse, org.imt.k3tdl.k3dsa.DataUseAspectDataUseAspectProperties>();
  
  public Map<DataUse, DataUseAspectDataUseAspectProperties> getMap() {
    return map;
  }
}
