package com.izmus.processes.homeview;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.User;

@Component("IsClientInRegistrationService")
public class IsClientInRegistrationService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(IsClientInRegistrationService.class);
	private static final String CLIENT_REGISTRATION_PROCESS_ID = "ClientRegistrationProcessId";
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Task registrationTask = taskService.createTaskQuery().processDefinitionKey(CLIENT_REGISTRATION_PROCESS_ID)
					.taskAssignee(user.getUserName()).singleResult();
			Boolean isUserInRegistration = false;
			if (registrationTask != null) {
				isUserInRegistration = true;
			}
			runtimeService.setVariable(execution.getId(), "isUserInRegistration", isUserInRegistration);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
