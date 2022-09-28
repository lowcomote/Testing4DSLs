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
package org.etsi.mts.tdl.graphical.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.executionframework.extensions.sirius.services.AbstractGemocAnimatorServices;
import org.etsi.mts.tdl.Message;
import org.imt.k3tdl.k3dsa.MessageAspectMessageAspectContext;

public class TdlAnimatorServices extends AbstractGemocAnimatorServices {
	@Override
	protected List<StringCouple> getRepresentationRefreshList() {  // <1>
		final List<StringCouple> res = new ArrayList<StringCouple>();
		res.add(new StringCouple("TestDescriptionDiagram", "Animation"));
		return res;
	}
	public boolean isPassedMessage(EObject o){    
		if(o.eContainer() instanceof Message){
			Message message = (Message) o.eContainer();
			String messageResult = MessageAspectMessageAspectContext.INSTANCE.getSelf(message).messageVerdict.getValue();
			if (messageResult == "PASS") {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
	public boolean isFailedMessage(EObject o){     
		if(o.eContainer() instanceof Message){
			Message message = (Message) o.eContainer();
			String messageResult = MessageAspectMessageAspectContext.INSTANCE.getSelf(message).messageVerdict.getValue();
			if (messageResult == "FAIL") {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
}
