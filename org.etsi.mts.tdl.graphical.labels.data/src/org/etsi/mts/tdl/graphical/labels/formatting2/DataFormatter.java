package org.etsi.mts.tdl.graphical.labels.formatting2;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.util.Pair;
import org.etsi.mts.tdl.graphical.labels.services.DataGrammarAccess;

import com.google.inject.Inject;

public class DataFormatter extends AbstractFormatter2 {

	@Inject
	private DataGrammarAccess extensions;

	@Override
	public void format(Object obj, IFormattableDocument document) {
		// TODO Auto-generated method stub
	}

	protected void configureFormatting(FormattingConfig c) {
		
		//TODO this code should be converted to support new API
		
		for (Pair<Keyword, Keyword> pair : extensions.findKeywordPairs("{", "}")) {
			c.setIndentation(pair.getFirst(), pair.getSecond());
			c.setLinewrap(1).after(pair.getFirst());
			c.setLinewrap(1).before(pair.getSecond());
			c.setLinewrap(1).after(pair.getSecond());
		}
		System.out.println("formatting?");
		for (Pair<Keyword, Keyword> pair : extensions.findKeywordPairs("(", ")")) {
			c.setIndentation(pair.getFirst(), pair.getSecond());
			c.setLinewrap(1).after(pair.getFirst());
			c.setLinewrap(1).before(pair.getSecond());
			c.setLinewrap(1).after(pair.getSecond());
		}

		for (Keyword comma : extensions.findKeywords(",")) {
//			c.setNoLinewrap().before(comma);
//			c.setNoSpace().before(comma);
//			c.setNoLinewrap().after(comma);
			c.setNoLinewrap().before(comma);
			c.setNoSpace().before(comma);
			c.setLinewrap(1).after(comma);
		}
		c.setLinewrap(0, 1, 2).before(extensions.getSL_COMMENTRule());
		c.setLinewrap(0, 1, 2).before(extensions.getML_COMMENTRule());
		c.setLinewrap(0, 1, 1).after(extensions.getML_COMMENTRule());
	}

}
