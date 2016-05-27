package com.izmus.processes.newuser;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.IzmusAssessor;
import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.IUserRepository;
import com.izmus.data.repository.IUserRoleRepository;

@Component("CreateIzmusAssessor")
public class CreateIzmusAssessor {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateIzmusAssessor.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IUserRoleRepository userRoleRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			String userName = runtimeService.getVariable(execution.getId(), "userName").toString();
			String email = runtimeService.getVariable(execution.getId(), "email").toString();
			LOGGER.info("Creating Izmus Assessor User For User Name: " + userName);
			String uuid = UUID.randomUUID().toString().split("-")[0];
			User newUser = new User();
			IzmusAssessor newIzmusAssessor = new IzmusAssessor();
			newIzmusAssessor.setUser(newUser);
			newUser.setEntity(newIzmusAssessor);
			newUser.setEnabled(true);
			newUser.setUserName(userName);
			newUser.setCreationTime(new Date());
			newIzmusAssessor.setEntityEmail(email.toLowerCase());
			newUser.setPassword(uuid);
			addBaseRoleToUser(newUser);
			userRepository.save(newUser);
			runtimeService.setVariable(execution.getId(), "user", newUser);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addBaseRoleToUser(User newUser) {
		Set<UserRole> userRoles = new HashSet<UserRole>();
		UserRole baseRole = userRoleRepository.findDistinctUserRoleByRoleName("ROLE_USER");
		userRoles.add(baseRole);
		baseRole.getUsers().add(newUser);
		newUser.setUserRoles(userRoles);
	}
}
