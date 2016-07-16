package com.izmus.processes.homeview;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.configurations.ViewConfig;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IUserRepository;

@Component("CheckUserLastViewService")
public class CheckUserLastViewService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckUserLastViewService.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			boolean lastView = false;
			User user = userRepository
					.findDistinctUserByUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
			if (user.getLastView() != null && !user.getLastView().isEmpty()){
				lastView = true;
				ViewConfig thisViewConfig = (ViewConfig) runtimeService.getVariable(execution.getId(), "viewConfig");
				thisViewConfig.setView(user.getLastView());
			}
			runtimeService.setVariable(execution.getId(), "lastView", lastView);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
