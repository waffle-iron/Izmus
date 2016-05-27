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

import com.izmus.data.configurations.ViewConfig;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IUserRepository;

@Component("RegistrationView")
public class RegistrationView {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationView.class);
	private static final String CLIENT_REGISTRATION_PROCESS_ID = "ClientRegistrationProcessId";
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;

	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			User user = userRepository
					.findOne(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
			Task registrationTask = taskService.createTaskQuery().processDefinitionKey(CLIENT_REGISTRATION_PROCESS_ID)
					.taskAssignee(user.getUserName()).singleResult();
			ViewConfig taskViewConfig = (ViewConfig) runtimeService.getVariable(registrationTask.getExecutionId(),
					"viewConfig");
			ViewConfig thisViewConfig = (ViewConfig) runtimeService.getVariable(execution.getId(), "viewConfig");
			thisViewConfig.copyConfig(taskViewConfig);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
