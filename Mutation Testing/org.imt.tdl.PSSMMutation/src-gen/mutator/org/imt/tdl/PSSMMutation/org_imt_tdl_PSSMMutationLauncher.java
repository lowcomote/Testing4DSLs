package mutator.org.imt.tdl.PSSMMutation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.AbstractMap.SimpleEntry;
import org.eclipse.core.resources.IProject;
import exceptions.*;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import mutator.PSSMMutation.PSSMMutation;
import manager.IMutatorExecutor;
import manager.IWodelTest;
import manager.ModelManager;
import manager.MutatorUtils;
import manager.MutatorUtils.MutationResults;

public class org_imt_tdl_PSSMMutationLauncher implements IMutatorExecutor {
	public MutationResults execute(int maxAttempts, int numMutants, boolean registry, boolean metrics,
			boolean debugMetrics, String[] blockNames, IProject project, IProgressMonitor monitor, boolean serialize,
			Object testObject, TreeMap<String, List<String>> classes)
			throws ReferenceNonExistingException, WrongAttributeTypeException, MaxSmallerThanMinException,
			AbstractCreationException, ObjectNoTargetableException, ObjectNotContainedException,
			MetaModelNotFoundException, ModelNotFoundException, IOException {
		IWodelTest test = testObject != null ? (IWodelTest) testObject : null;
		String ecoreURI = "/org.imt.tdl.PSSMMutation/data/model/statemachines.ecore";
		List<EPackage> packages = ModelManager.loadMetaModel(ecoreURI, this.getClass());
		boolean isRegistered = ModelManager.isRegistered(packages);
		Map<String, EPackage> registeredPackages = new HashMap<String, EPackage>();
		if (isRegistered == true) {
			registeredPackages = ModelManager.unregisterMetaModel(packages);
		}
		MutationResults mutationResults = new MutationResults();
		MutatorUtils mutPSSMMutation = new PSSMMutation();
		MutationResults resultsPSSMMutation = mutPSSMMutation.execute(maxAttempts, numMutants, registry, metrics,
				debugMetrics, packages, registeredPackages, blockNames, project, monitor, serialize, test, classes);
		mutationResults.numMutatorsApplied += resultsPSSMMutation.numMutatorsApplied;
		mutationResults.numMutantsGenerated += resultsPSSMMutation.numMutantsGenerated;
		if (resultsPSSMMutation.mutatorsApplied != null) {
			if (mutationResults.mutatorsApplied == null) {
				mutationResults.mutatorsApplied = new ArrayList<String>();
			}
			mutationResults.mutatorsApplied.addAll(resultsPSSMMutation.mutatorsApplied);
		}
		if (isRegistered == true) {
			ModelManager.registerMetaModel(registeredPackages);
		}
		return mutationResults;
	}
}
