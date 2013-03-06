package com.markitserv.hawthorne.actions;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;
import com.markitserv.msws.Type;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;

@Service
public class TestBackendDataAction extends AbstractAction {

	public class TestBackendType extends Type {
		public Map<Integer, Participant> data;
	}

	@Autowired
	private HardcodedHawthorneBackend backend;

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {

		Map<Integer, Participant> x = backend.buildAndGetParticipants();
		TestBackendType data = new TestBackendType();
		data.data = x;

		ActionResult res = new ActionResult(data);
		return res;
	}
}
