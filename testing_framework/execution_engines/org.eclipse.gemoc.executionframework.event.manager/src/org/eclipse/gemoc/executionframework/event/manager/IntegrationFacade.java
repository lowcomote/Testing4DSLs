package org.eclipse.gemoc.executionframework.event.manager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;

public class IntegrationFacade {

	private final Map<String, IMetalanguageRuleExecutor> metalanguageIntegrations = new HashMap<>();
	private final IExecutionEngine<?> executionEngine;

	public IntegrationFacade(IExecutionEngine<?> executionEngine) {
		this.executionEngine = executionEngine;
	}

	public void handleCallRequest(SimpleCallRequest callRequest) throws IllegalArgumentException {
		final IMetalanguageRuleExecutor ruleExecutor = metalanguageIntegrations
				.computeIfAbsent(callRequest.getMetalanguage(), m -> findMetalanguageRuleExecutor(m));
		ruleExecutor.setExecutionEngine(executionEngine);
		ruleExecutor.handleCallRequest(callRequest);
	}

	private IMetalanguageRuleExecutor findMetalanguageRuleExecutor(String metalanguage) {
		/*return Arrays
				.asList(Platform.getExtensionRegistry().getExtension(metalanguage).getConfigurationElements())
				.stream().findFirst().map(c -> {
					IMetalanguageRuleExecutor result = null;
					try {
						result = (IMetalanguageRuleExecutor) c.createExecutableExtension("class");
						result.setExecutionEngine(executionEngine);
					} catch (CoreException e) {
						e.printStackTrace();
					}
					return result;
				}).orElse(null);
		*/
		//TODO: it has to be set automatically
		return new K3RuleExecutor(metalanguage);
	}
}
