package org.etsi.mts.tdl.graphical.labels.parser;

import org.etsi.mts.tdl.graphical.labels.parser.antlr.DataParser;

public class RuleBasedDataParser extends DataParser {
	
	private String defaultRuleName = super.getDefaultRuleName();
	
	public void setDefaultRuleName(String defaultRuleName) {
		this.defaultRuleName = defaultRuleName;
	}

	@Override
	protected String getDefaultRuleName() {
		return defaultRuleName;
	}
}
