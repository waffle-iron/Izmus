package com.izmus.processes.homeview;

import java.util.Calendar;
import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IUserRepository;

@Component("CheckUserPasswordService")
public class CheckUserPasswordService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckUserPasswordService.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			boolean changePassword = false;
			User user = userRepository
					.findOne(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -6);
			if (user.getPasswordChangeDate() == null || user.getPasswordChangeDate().before(cal.getTime())){
				changePassword = true;
			}
			runtimeService.setVariable(execution.getId(), "user", user);
			runtimeService.setVariable(execution.getId(), "changePassword", changePassword);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
