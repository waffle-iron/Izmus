package com.izmus.api.rolesmanagement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.api.usermanagement.PermissionData;
import com.izmus.data.api.usermanagement.UserRoleData;
import com.izmus.data.domain.users.Permission;
import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.IPermissionRepository;
import com.izmus.data.repository.IUserRepository;
import com.izmus.data.repository.IUserRoleRepository;

@RestController
@RequestMapping("api/Roles")
public class RolesService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesService.class);
	@Autowired
	private IUserRoleRepository userRoleRepository;
	@Autowired
	private IPermissionRepository permissionRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ObjectMapper jacksonObjectMapper;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/RolesManagementData", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Roles', '')")
	public Set<UserRoleData> getAvailableRoles() {
		Set<UserRoleData> userRoleData = null;
		try {
			userRoleData = new HashSet<>();
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
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return userRoleData;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/SaveRoleData", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Admin Menu/Roles', '')")
	public void saveUserManagementData(@RequestParam(value = "roleData") String roleData) {
		UserRoleData userRoleDataObject = null;
		try {
			userRoleDataObject = jacksonObjectMapper.readValue(roleData, UserRoleData.class);
			UserRole changedUserRole = null;
			if (userRoleDataObject.getRoleId() != null){
				changedUserRole = userRoleRepository.findOne(userRoleDataObject.getRoleId());
			}
			else {
				changedUserRole = userRoleRepository.findDistinctUserRoleByRoleName(userRoleDataObject.getRoleName());
			}
			if (changedUserRole == null) changedUserRole = new UserRole();
			changedUserRole.setRoleName(userRoleDataObject.getRoleName());
			changedUserRole.setPermissions(getUserRolesPermissions(changedUserRole, userRoleDataObject.getPermissions()));
			userRoleRepository.save(changedUserRole);
			LOGGER.info("User Role Data Saved: " + changedUserRole);
		} catch (Exception e) {
			LOGGER.error("Could Not Save User Role Information: " + userRoleDataObject);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/DeleteRoleData", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Admin Menu/Roles', '')")
	public void deleteUserManagementData(@RequestParam(value = "roleData") String roleData) {
		UserRoleData userRoleDataObject = null;
		try {
			userRoleDataObject = jacksonObjectMapper.readValue(roleData, UserRoleData.class);
			UserRole deletedUserRole = null;
			if (userRoleDataObject.getRoleId() != null){
				deletedUserRole = userRoleRepository.findOne(userRoleDataObject.getRoleId());
			}
			else {
				deletedUserRole = userRoleRepository.findDistinctUserRoleByRoleName(userRoleDataObject.getRoleName());
			}
			if (deletedUserRole == null) return;
			removeRoleFromUsers(deletedUserRole);
			userRoleRepository.delete(deletedUserRole.getRoleId());
			LOGGER.info("User Role Data Deleted: " + deletedUserRole);
		} catch (Exception e) {
			LOGGER.error("Could Not Delete User Role: " + userRoleDataObject);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void removeRoleFromUsers(UserRole deletedUserRole) {
		Set<User> users = deletedUserRole.getUsers();
		for (User user : users){
			Set<UserRole> roles = user.getUserRoles();
			roles.remove(deletedUserRole);
			userRepository.save(user);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private Set<Permission> getUserRolesPermissions(UserRole changedUserRole, Set<PermissionData> permissions) {
		Set<Permission> returnPermissions = new HashSet<>();
		for (Iterator<PermissionData> userRolePermissionDataIterator = permissions.iterator(); userRolePermissionDataIterator.hasNext();){
			PermissionData permissionData = null;
			try {
				permissionData = userRolePermissionDataIterator.next();
				Permission permission = null;
				if (permissionData.getPermissionId() != null){
					permission = permissionRepository.findOne(permissionData.getPermissionId());
				}
				else {
					permission = permissionRepository.findDistinctPermissionByPermissionName(permissionData.getPermissionName());
				}
				returnPermissions.add(permission);
			} catch (Exception e) {
				LOGGER.error("Could Not Add User Role: " + permissionData);
			}
		}
		return returnPermissions;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addUserPermissions(UserRoleData newUserRoleData, UserRole userRole) {
		Set<PermissionData> newPermissionDataList = null;
		try {
			Set<Permission> permissions = userRole.getPermissions();
			newPermissionDataList = new HashSet<PermissionData>();
			for (Permission permission : permissions){
				PermissionData newPermissionData = new PermissionData();
				newPermissionData.setPermissionId(permission.getPermissionId());
				newPermissionData.setPermissionName(permission.getPermissionName());
				newPermissionDataList.add(newPermissionData);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		newUserRoleData.setPermissions(newPermissionDataList);
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/AvailablePermissions", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Roles', '')")
	public Set<PermissionData> getAvailablePermissions() {
		Set<PermissionData> permissionsData = null;
		try {
			permissionsData = new HashSet<>();
			List<Permission> permissions = permissionRepository.findAll();
			for (Permission permission : permissions){
				PermissionData newPermission = new PermissionData();
				newPermission.setPermissionId(permission.getPermissionId());
				newPermission.setPermissionName(permission.getPermissionName());
				permissionsData.add(newPermission);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return permissionsData;
	}
}
