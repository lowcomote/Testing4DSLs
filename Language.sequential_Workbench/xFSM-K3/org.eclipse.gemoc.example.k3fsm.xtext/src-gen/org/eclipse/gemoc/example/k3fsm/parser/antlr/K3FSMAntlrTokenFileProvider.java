/*
 * generated by Xtext 2.24.0
 */
package org.eclipse.gemoc.example.k3fsm.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class K3FSMAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("org/eclipse/gemoc/example/k3fsm/parser/antlr/internal/InternalK3FSM.tokens");
	}
}