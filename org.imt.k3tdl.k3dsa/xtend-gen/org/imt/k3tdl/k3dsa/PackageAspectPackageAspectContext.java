package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties;

@SuppressWarnings("all")
public class PackageAspectPackageAspectContext {
  public static final PackageAspectPackageAspectContext INSTANCE = new PackageAspectPackageAspectContext();
  
  public static PackageAspectPackageAspectProperties getSelf(final org.etsi.mts.tdl.Package _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<org.etsi.mts.tdl.Package, PackageAspectPackageAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.Package, org.imt.k3tdl.k3dsa.PackageAspectPackageAspectProperties>();
  
  public Map<org.etsi.mts.tdl.Package, PackageAspectPackageAspectProperties> getMap() {
    return map;
  }
}
