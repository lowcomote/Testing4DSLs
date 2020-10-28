package org.imt.k3tdl.k3dsa;

import java.util.Map;
import org.etsi.mts.tdl.TestConfiguration;
import org.imt.k3tdl.k3dsa.TestConfigurationAspectTestConfigurationAspectProperties;

@SuppressWarnings("all")
public class TestConfigurationAspectTestConfigurationAspectContext {
  public static final TestConfigurationAspectTestConfigurationAspectContext INSTANCE = new TestConfigurationAspectTestConfigurationAspectContext();
  
  public static TestConfigurationAspectTestConfigurationAspectProperties getSelf(final TestConfiguration _self) {
    		if (!INSTANCE.map.containsKey(_self))
    			INSTANCE.map.put(_self, new org.imt.k3tdl.k3dsa.TestConfigurationAspectTestConfigurationAspectProperties());
    		return INSTANCE.map.get(_self);
  }
  
  private Map<TestConfiguration, TestConfigurationAspectTestConfigurationAspectProperties> map = new java.util.WeakHashMap<org.etsi.mts.tdl.TestConfiguration, org.imt.k3tdl.k3dsa.TestConfigurationAspectTestConfigurationAspectProperties>();
  
  public Map<TestConfiguration, TestConfigurationAspectTestConfigurationAspectProperties> getMap() {
    return map;
  }
}
