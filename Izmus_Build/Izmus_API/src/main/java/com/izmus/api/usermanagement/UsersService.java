package com.izmus.api.usermanagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.api.usermanagement.PermissionData;
import com.izmus.data.api.usermanagement.UserData;
import com.izmus.data.api.usermanagement.UserManagementType;
import com.izmus.data.api.usermanagement.UserRoleData;
import com.izmus.data.domain.log.SystemLog;
import com.izmus.data.domain.users.Permission;
import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.ISystemLogRepository;
import com.izmus.data.repository.IUserRepository;
import com.izmus.data.repository.IUserRoleRepository;
import com.izmus.security.authentication.InvestITAuthenticationProvider;

@RestController
@RequestMapping("api/Users")
public class UsersService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
	private static final String NEW_USER_PROCESS = "NewUserProcessId";
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ISystemLogRepository systemLogRepository;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private IUserRoleRepository userRoleRepository;
	@Autowired
	private ObjectMapper jacksonObjectMapper;
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/SaveUserPassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public String saveUserPassword(@RequestParam(value = "currentPassword") String currentPassword, 
			@RequestParam(value = "newPassword") String newPassword) {
		User user = null;
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!currentPassword.equals(user.getPassword())){
				return "{\"result\": \"fail\"}";
			}
			user.setPassword(newPassword);
			user.setPasswordChangeDate(new Date());
			user = userRepository.findOne(user.getUserId());
			user.setPassword(newPassword);
			user.setPasswordChangeDate(new Date());
			userRepository.save(user);
			LOGGER.info("User Saved Password: " + user);
			return "{\"result\": \"success\"}";
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return "{\"result\": \"fail\"}";
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/GetOwnUser", method = RequestMethod.GET)
	public UserData getOwnUser() {
		LOGGER.info("User trying to get his own user information");
		try {
			User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (authenticatedUser == null) return null;
			UserData returnData = createUserData(authenticatedUser, "OwnUser");
			return returnData;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/IsUserExists", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public String isUserExist(@RequestParam(value = "userName") String userName) {
		LOGGER.info("User trying to register with user name: " + userName);
		if (userName.isEmpty()) return "true";
		try {
			User clientUser = userRepository.findDistinctUserByUserName(userName);
			if (clientUser != null)
				return "true";
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "false";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/CheckUserCredentials", method = RequestMethod.GET)
	public String checkUserCredentials(@RequestParam(value = "userName") String userName, 
			@RequestParam(value = "password") String password) {
		LOGGER.info("User trying to check user credentials. User name: " + userName + ", Password: " + password);
		try {
			User user = userRepository.findDistinctUserByUserName(userName);
			if (user == null || !user.getPassword().equals(password)){
				return "false";
			}
			else {
				return "true";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return "false";
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/SaveUserAvatar", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public void saveUserAvatar(@RequestParam(value = "userAvatar") String userAvatar) {
		User user = null;
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user.setUserAvatar(userAvatar);
			user = userRepository.findOne(user.getUserId());
			user.setUserAvatar(userAvatar);
			userRepository.save(user);
			LOGGER.info("User Saved Avatar: " + user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/AvailableRoles", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public Set<UserRoleData> getAvailableRoles() {
		Set<UserRoleData> userRoleData = new HashSet<>();
		List<UserRole> userRoles = userRoleRepository.findAll();
		for (UserRole userRole : userRoles){
			if (userRole.getRoleName().equals("ROLE_USER")) continue;
			if (userRole.getRoleName().equals("ROLE_SUPER_USER")) continue;
			UserRoleData newUserRoleData = new UserRoleData();
			newUserRoleData.setRoleId(userRole.getRoleId());
			newUserRoleData.setRoleName(userRole.getRoleName());
			addUserPermissions(newUserRoleData, userRole);
			userRoleData.add(newUserRoleData);
		}
		return userRoleData;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/CreateNewUser", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public String createNewUser(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "userType") String userType) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userName", userName);
		variables.put("email", email);
		variables.put("userType", userType);
		runtimeService.startProcessInstanceByKey(NEW_USER_PROCESS, variables);
		return "{\"result\": \"success\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/SaveUserData", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public void saveUserManagementData(@RequestParam(value = "userData") String userData) {
		UserData userDataObject = null;
		try {
			userDataObject = jacksonObjectMapper.readValue(userData, UserData.class);
			User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User changedUser = userRepository.findOne(userDataObject.getUserId());
			if (authenticatedUser.equals(changedUser)){
				changedUser = authenticatedUser;
			}
			changedUser.setUserName(userDataObject.getUserName());
			changedUser.getEntity().setEntityEmail(userDataObject.getUserEmail());
			changedUser.getEntity().setIsEntityMale(userDataObject.getIsUserMale());
			changedUser.setEnabled(userDataObject.getEnabled());
			changedUser.setUserAvatar(userDataObject.getUserAvatar());
			changedUser.setUserRoles(getUserRoles(changedUser, userDataObject.getUserRoles()));
			userRepository.save(changedUser);
			LOGGER.info("User Data Saved: " + changedUser);
		} catch (Exception e) {
			LOGGER.error("Could Not Save User Information: " + userDataObject);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private Set<UserRole> getUserRoles(User changedUser, Set<UserRoleData> userRoleData) {
		Set<UserRole> returnUserRoles = new HashSet<>();
		UserRole baseRole = userRoleRepository.findDistinctUserRoleByRoleName("ROLE_USER");
		returnUserRoles.add(baseRole);
		UserRole superUser = userRoleRepository.findDistinctUserRoleByRoleName("ROLE_SUPER_USER");
		if (changedUser.getUserRoles().contains(superUser)){
			returnUserRoles.add(superUser);
		}
		for (Iterator<UserRoleData> userRoleDataIterator = userRoleData.iterator(); userRoleDataIterator.hasNext();){
			UserRoleData roleData = null;
			try {
				roleData = userRoleDataIterator.next();
				UserRole userRole = null;
				if (roleData.getRoleId() != null){
					userRole = userRoleRepository.findOne(roleData.getRoleId());
				}
				else {
					userRole = userRoleRepository.findDistinctUserRoleByRoleName(roleData.getRoleName());
				}
				returnUserRoles.add(userRole);
			} catch (Exception e) {
				LOGGER.error("Could Not Add User Role: " + roleData);
			}
		}
		return returnUserRoles;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/UserManagementData", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public Set<UserManagementType> getUserManagementData() {
		List<User> allUsers = userRepository.findAll();
		TreeSet<UserManagementType> returnList = new TreeSet<>();
		for (User user : allUsers){
			UserManagementType newUserType = new UserManagementType();
			String title;
			try {
				title = context.getMessage("users.userTypes." + user.getEntity().getClass().getSimpleName(), 
						null, LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException e) {
				title = user.getEntity().getClass().getSimpleName();
			}
			newUserType.setTitle(title);
			if (returnList.contains(newUserType)){
				returnList.floor(newUserType).getUsers().add(createUserData(user, title));
			}
			else {
				newUserType.setUsers(new TreeSet<UserData>());
				newUserType.getUsers().add(createUserData(user, title));
				returnList.add(newUserType);
			}
		}
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private UserData createUserData(User user, String userType) {
		UserData newUserData = new UserData();
		newUserData.setUserId(user.getUserId());
		newUserData.setUserName(user.getUserName());
		newUserData.setUserType(userType);
		newUserData.setUserEmail(user.getEntity().getEntityEmail());
		newUserData.setEnabled(user.getEnabled());
		newUserData.setIsUserMale(user.getEntity().getIsEntityMale());
		newUserData.setUserAvatar(user.getUserAvatar());
		try {
			newUserData.setCreationTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(user.getCreationTime()));
		} catch (Exception e1) {
			newUserData.setCreationTime("");
		}
		try {
			List<SystemLog> logEntries = systemLogRepository
					.findByMessageIgnoreCaseContainingAndLogClassIgnoreCaseContainingOrderByLogTimeDesc(user.toString(), InvestITAuthenticationProvider.class.getName());
			newUserData.setLastLogin(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(logEntries.get(0).getLogTime()));
		} catch (Exception e) {
			newUserData.setLastLogin("Unknown");
		}
		addUserRoles(newUserData, user);
		return newUserData;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addUserRoles(UserData newUserData, User user) {
		Set<UserRole> roles = user.getUserRoles();
		Set<UserRoleData> newRoleDataList = new HashSet<UserRoleData>();
		for (UserRole userRole : roles){
			if (userRole.getRoleName().equals("ROLE_USER")) continue;
			UserRoleData newUserRoleData = new UserRoleData();
			newUserRoleData.setRoleId(userRole.getRoleId());
			newUserRoleData.setRoleName(userRole.getRoleName());
			addUserPermissions(newUserRoleData, userRole);
			newRoleDataList.add(newUserRoleData);
		}
		newUserData.setUserRoles(newRoleDataList);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addUserPermissions(UserRoleData newUserRoleData, UserRole userRole) {
		Set<Permission> permissions = userRole.getPermissions();
		Set<PermissionData> newPermissionDataList = new HashSet<PermissionData>();
		for (Permission permission : permissions){
			PermissionData newPermissionData = new PermissionData();
			newPermissionData.setPermissionId(permission.getPermissionId());
			newPermissionData.setPermissionName(permission.getPermissionName());
			newPermissionDataList.add(newPermissionData);
		}
		newUserRoleData.setPermissions(newPermissionDataList);
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/IsEmailExists", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public String isEmailExist(@RequestParam(value = "email") String email) {
		LOGGER.info("User trying to check email: " + email);
		if (email.isEmpty()) return "true";
		try {
			List<User> users = userRepository.findAll();
			for (User user : users){
				if (user.getEntity().getEntityEmail().toLowerCase().equals(email.toLowerCase())){
					return "true";
				}
			}
			return "false";
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return "false";
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/CheckUserAndEmail", method = RequestMethod.GET)
	public String checkUserAndEmail(@RequestParam(value = "userName") String userName, 
			@RequestParam(value = "email") String email) {
		String isUserExists = isUserExist(userName);
		String isEmailExists = isEmailExist(email);
		return "{\"isUserExists\":" + isUserExists + ", \"isEmailExists\":" + isEmailExists + "}";
	}
}
