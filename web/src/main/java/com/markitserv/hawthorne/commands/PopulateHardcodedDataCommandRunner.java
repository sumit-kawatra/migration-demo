package com.markitserv.hawthorne.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;
import com.markitserv.msws.command.AbstractCommandRunner;
import com.markitserv.msws.command.Command;

@Service
public class PopulateHardcodedDataCommandRunner extends AbstractCommandRunner implements
		ApplicationContextAware {

	ApplicationContext springContext;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected Object run(Command cmd) {

		// Note that this will only work if we're using hardcoded data.
		HardcodedHawthorneBackend backend = springContext
				.getBean(HardcodedHawthorneBackend.class);

		log.info("Starting population of hardcoded data.");
		backend.populateAllHardcodedData();
		log.info("Completed population of hardcoded data.");

		return null;

	}

	@Override
	public Class<?> getCommandType() {
		return PopulateHardcodedDataCommand.class;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		log.debug("SETTING CONF");
		this.springContext = applicationContext;
	}
}
