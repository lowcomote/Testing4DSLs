package org.imt.tdl.eventManager;

public class StopRequest implements ICallRequest {

	@Override
	public boolean isRunToCompletion() {
		return true;
	}

}
