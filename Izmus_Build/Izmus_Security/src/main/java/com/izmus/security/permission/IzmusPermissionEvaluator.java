package com.izmus.security.permission;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

import com.izmus.data.domain.users.Permission;
import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.IPermissionRepository;
import com.izmus.data.repository.IUserRepository;

public class IzmusPermissionEvaluator implements PermissionEvaluator, Filter,
WebInvocationPrivilegeEvaluator{
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(IzmusPermissionEvaluator.class);
	private static final String SUPER_USER = "ROLE_SUPER_USER";
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IPermissionRepository permissionRepository;
	/*----------------------------------------------------------------------------------------------------*/
	private boolean getUriPermission(Authentication authentication, String uri) {
		try{
			User user = userRepository
					.findOne(((User)authentication.getPrincipal()).getUserId());
			if (!isPermissionExists(uri)){
				if (isUserSuperUser(user)) return true;
				return false;
			}
			else {
				if (isUserSuperUser(user)) return true;
			}
			for (UserRole userRole : user.getUserRoles()){
				for (Permission permission : userRole.getPermissions()){
					if (permission.getPermissionName().equals(uri)){
						return true;
					}
				}
			}
		}
		catch (Exception e){
			LOGGER.debug(e.getMessage());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private boolean isUserSuperUser(User user) {
		for (UserRole userRole : user.getUserRoles()){
			if (userRole.getRoleName().equals(SUPER_USER)) return true;
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private boolean isPermissionExists(String uri) {
		try{
			Permission permission = permissionRepository.findDistinctPermissionByPermissionName(uri);
			if (permission == null){
				permission = new Permission();
				permission.setPermissionName(uri);
				permissionRepository.save(permission);
				return false;
			}
			return true;
		}
		catch (Exception e){
			LOGGER.debug(e.getMessage());
			return false;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public boolean hasPermission(Authentication authentication, Object uri, Object targetObject) {
		boolean isPermitted = false;
		isPermitted = getUriPermission(authentication, uri.toString());
		return isPermitted;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public boolean hasPermission(Authentication authentication, Serializable arg1, String arg2, Object arg3) {
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setAttribute(WebAttributes.WEB_INVOCATION_PRIVILEGE_EVALUATOR_ATTRIBUTE, this);
        chain.doFilter(request, response);
    }
	/*----------------------------------------------------------------------------------------------------*/
    @Override
    public boolean isAllowed(String uri, Authentication authentication) {
    	boolean isPermitted = false;
		isPermitted = getUriPermission(authentication, uri);
		return isPermitted;
    }
	/*----------------------------------------------------------------------------------------------------*/
    @Override
    public boolean isAllowed(String contextPath, String uri, String method,
            Authentication authentication) {
    	boolean isPermitted = false;
		isPermitted = getUriPermission(authentication, uri);
		return isPermitted;
    }
	/*----------------------------------------------------------------------------------------------------*/
    @Override
    public void destroy() {}
	/*----------------------------------------------------------------------------------------------------*/
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
}
