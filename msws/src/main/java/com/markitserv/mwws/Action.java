package com.markitserv.mwws;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.markitserv.mwws.exceptions.FilterNotApplicableForActionException;
import com.markitserv.mwws.exceptions.ProgrammaticException;
import com.markitserv.mwws.filters.ActionFilters;
import com.markitserv.mwws.parameters.ActionParameters;

public abstract class Action implements InitializingBean {

	private static final String FILTERS_CLASS_NAME = "Filters";

	@Autowired
	private ActionRegistry actionRegistry;

	public ActionResult performAction(ActionCommand command) {

		ActionParameters params = buildParamObjFromCommand(command);
		ActionFilters filters = buildFilterObjFromCommand(command);

		return this.performAction(params, filters);

	}

	private ActionParameters buildParamObjFromCommand(ActionCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	private ActionFilters buildFilterObjFromCommand(ActionCommand command) {
		ActionFilters filtersBean = instantiateFiltersClass();

		Map<String, List<String>> filtersFromCommand = (Map<String, List<String>>) command
				.getParams().get(CommonParamKeys.Filter.toString());

		// map each filter from the command to the Filters bean
		for (String filterName : filtersFromCommand.keySet()) {

			List<String> filterValue = filtersFromCommand.get(filterName);

			try {
				PropertyUtils.setProperty(filtersBean, filterName, filterValue);
			} catch (NoSuchMethodException e) {
				throw FilterNotApplicableForActionException.standardException(
						filterName, this.getActionName());
			} catch (Exception e) {
				throw new ProgrammaticException(
						"Could not populate '%s' filter for Filter class '%s'",
						e, filterName, filtersBean.getClass()
								.getCanonicalName());
			}
		}

		return filtersBean;

	}

	/**
	 * Determines the instance of ActionFilters that will be used for this
	 * action. By default will use the inner class named "Filters".
	 * 
	 * @return The Filters class for the Action
	 */
	protected ActionFilters instantiateFiltersClass() {
		return (ActionFilters) this.instantiateInnerClass(FILTERS_CLASS_NAME);
	}

	private Object instantiateInnerClass(String innerClassName) {

		Object innerClassInst = null;
		Constructor[] constructors = null;

		// iterate through inner classes to find the one we want. Set
		// constructors of that class
		Class<?>[] x = this.getClass().getDeclaredClasses();
		for (Class<?> clazz : x) {
			String className = clazz.getSimpleName();
			if (className.equals(innerClassName)) {
				Class filterClass = clazz;
				constructors = filterClass.getConstructors();

				break;
			}
		}

		if (constructors == null) {
			throw new ProgrammaticException(
					"Could not find 'Filters' class for this Action class");
		}

		if (constructors.length != 1) {
			throw new ProgrammaticException(
					"Could not instantiate 'Filters' class for this Action.  Class must have only one constructor");
		}

		try {
			innerClassInst = constructors[0].newInstance(this);
		} catch (Exception e) {
			throw new ProgrammaticException(
					"Could not create instance of filter class '%s'.", e,
					innerClassName);
		}

		return innerClassInst;
	}

	protected abstract ActionResult performAction(ActionParameters params,
			ActionFilters filters);

	private void registerWithActionRegistry() {
		actionRegistry.registerAction(this.getActionName(), this);
	}

	/*
	 * By default the name of the action that's put into the registry is the
	 * simple name of the class. Can be overriden if necessary (but when would
	 * it be necessisary?)
	 */
	protected String getActionName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerWithActionRegistry();
	}
}
