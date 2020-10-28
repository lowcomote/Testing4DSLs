package org.imt.k3tdl.k3dsa;

import java.util.ArrayList;
import java.util.List;
import org.etsi.mts.tdl.Interaction;
import org.etsi.mts.tdl.TestDescription;

@SuppressWarnings("all")
public class PackageAspectPackageAspectProperties {
  public List<TestDescription> testcases = new ArrayList<TestDescription>();
  
  public TestDescription enabledTestCase;
  
  public Interaction currentInteraction;
  
  public String verdictValue;
}
