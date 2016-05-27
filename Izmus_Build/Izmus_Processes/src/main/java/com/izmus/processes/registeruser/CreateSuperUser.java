package com.izmus.processes.registeruser;

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

import com.izmus.data.domain.users.Administrator;
import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.IUserRepository;
import com.izmus.data.repository.IUserRoleRepository;

@Component("CreateSuperUser")
public class CreateSuperUser {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateSuperUser.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IUserRoleRepository userRoleRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
//			String userName = runtimeService.getVariable(execution.getId(), "userName").toString();
//			String email = runtimeService.getVariable(execution.getId(), "email").toString();
//			LOGGER.info("Creating Super User For User Name: " + userName);
//			String uuid = UUID.randomUUID().toString().split("-")[0];
//			User newUser = new User();
//			Administrator superUser = new Administrator();
//			superUser.setUser(newUser);
//			newUser.setEntity(superUser);
//			newUser.setEnabled(true);
//			newUser.setUserName(userName);
//			newUser.setCreationTime(new Date());
//			superUser.setEntityEmail(email.toLowerCase());
//			newUser.setPassword(uuid);
//			addSuperuserRoleToUser(newUser);
//			userRepository.save(newUser);
//			runtimeService.setVariable(execution.getId(), "user", newUser);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addSuperuserRoleToUser(User newUser) {
		Set<UserRole> userRoles = new HashSet<UserRole>();
		UserRole baseRole = userRoleRepository.findDistinctUserRoleByRoleName("ROLE_USER");
		if (baseRole == null){
			baseRole = createBaseRole();
		}
		UserRole superUser = userRoleRepository.findDistinctUserRoleByRoleName("ROLE_SUPER_USER");
		if (superUser == null){
			superUser = createSuperUser();
		}
		userRoles.add(baseRole);
		baseRole.getUsers().add(newUser);
		userRoles.add(superUser);
		superUser.getUsers().add(newUser);
		newUser.setUserRoles(userRoles);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private UserRole createBaseRole() {
		UserRole userRole = new UserRole();
		userRole.setRoleName("ROLE_USER");
		userRole.setUsers(new HashSet<User>());
		userRoleRepository.save(userRole);
		return userRole;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private UserRole createSuperUser() {
		UserRole superUserRole = new UserRole();
		superUserRole.setRoleName("ROLE_SUPER_USER");
		superUserRole.setUsers(new HashSet<User>());
		userRoleRepository.save(superUserRole);
		return superUserRole;
	}
}
