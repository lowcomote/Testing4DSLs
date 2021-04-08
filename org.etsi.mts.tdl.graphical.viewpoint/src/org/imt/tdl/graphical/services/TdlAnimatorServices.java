/*******************************************************************************
 * Copyright (c) 2018 Inria.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.imt.tdl.graphical.services;

import java.util.ArrayList;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.executionframework.extensions.sirius.services.AbstractGemocAnimatorServices;

// tag::FsmAnimatorServicesClass[]
public class TdlAnimatorServices extends AbstractGemocAnimatorServices {
	@Override
	protected List<StringCouple> getRepresentationRefreshList() {  // <1>
		final List<StringCouple> res = new ArrayList<StringCouple>();
		res.add(new StringCouple("TDL", "Animation"));
		return res;
	}
}
// end::FsmAnimatorServicesClass[]
