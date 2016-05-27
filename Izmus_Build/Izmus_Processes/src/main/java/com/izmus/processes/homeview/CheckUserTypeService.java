package com.izmus.processes.homeview;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.User;

@Component("CheckUserTypeService")
public class CheckUserTypeService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckUserTypeService.class);
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			String userType = "unknown";
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userType = user.getEntity().getClass().getSimpleName();
			runtimeService.setVariable(execution.getId(), "userType", userType);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
