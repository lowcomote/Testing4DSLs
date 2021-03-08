package org.imt.tdl.design.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.executionframework.extensions.sirius.services.AbstractGemocAnimatorServices;
import org.etsi.mts.tdl.Message;

public class TDLAnimatorServices extends AbstractGemocAnimatorServices{

	@Override
	protected List<StringCouple> getRepresentationRefreshList() {
		final List<StringCouple> res = new ArrayList<StringCouple>();
		res.add(new StringCouple("TDL", "Animation"));
		return res;
	}
}
