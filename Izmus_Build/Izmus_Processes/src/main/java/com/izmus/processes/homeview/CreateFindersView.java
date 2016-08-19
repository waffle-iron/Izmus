package com.izmus.processes.homeview;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izmus.data.configurations.ViewConfig;

@Component("CreateFindersView")
public class CreateFindersView {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateFindersView.class);
	private static final String FINDERS_DASHBOARD = "findersDashboard";
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			ViewConfig thisViewConfig = (ViewConfig) runtimeService.getVariable(execution.getId(), "viewConfig");
			thisViewConfig.setView(FINDERS_DASHBOARD);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
