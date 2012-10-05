package com.markitserv.rest;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class SandboxBeanSerializerModifierModule extends Module {

	@Override
	public String getModuleName() {
		return "SandboxBeanSerializerModifier";
	}

	@Override
	public Version version() {
		return new Version(1, 0, 0, null, "com", "markitserv");
	}

	@Override
	public void setupModule(SetupContext context) {
		context.addBeanSerializerModifier(SandboxBeanSerializerModifier
				.instance());
	}

}
