package com.izmus.processes.newcontactuser;

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

import com.izmus.data.domain.contacts.FinderContact;
import com.izmus.data.domain.users.IzmusFinder;
import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.IFinderContactRepository;
import com.izmus.data.repository.IUserRepository;
import com.izmus.data.repository.IUserRoleRepository;

@Component("CreateContactIzmusFinder")
public class CreateContactIzmusFinder {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateContactIzmusFinder.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IFinderContactRepository finderContactRepository;
	@Autowired
	private IUserRoleRepository userRoleRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			Integer contactId = (Integer) runtimeService.getVariable(execution.getId(), "contactId");
			FinderContact contact = finderContactRepository.findDistinctFinderContactByContactId(contactId);
			String userName = contact.getFirstName() + "." + contact.getLastName();
			LOGGER.info("Creating Izmus Finder User For Contact User Name: " + userName);
			String uuid = UUID.randomUUID().toString().split("-")[0];
			User newUser = new User();
			IzmusFinder newIzmusFinder = new IzmusFinder();
			newIzmusFinder.setUser(newUser);
			newIzmusFinder.setContactId(contactId);
			newUser.setEntity(newIzmusFinder);
			newUser.setEnabled(true);
			newUser.setUserName(userName);
			newUser.setCreationTime(new Date());
			newIzmusFinder.setEntityEmail(contact.getEmail().toLowerCase());
			newUser.setPassword(uuid);
			addBaseRoleToUser(newUser);
			newUser = userRepository.save(newUser);
			contact.setEntityId(newUser.getEntity().getEntityId());
			finderContactRepository.save(contact);
			runtimeService.setVariable(execution.getId(), "userId", newUser.getUserId());
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
