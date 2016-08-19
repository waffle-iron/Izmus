package com.izmus.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.User;
import com.izmus.data.domain.users.UserRole;
import com.izmus.data.repository.IUserRepository;
import com.izmus.logger.DatabaseLogger;

@Component("IzmusAuthenticationProvider")
public class IzmusAuthenticationProvider implements AuthenticationProvider {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(IzmusAuthenticationProvider.class);
	@Autowired
	private IUserRepository userRepository;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		return authenticate(name, password);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private Authentication authenticate(String name, String password){
		User matchedUser = userRepository.findDistinctUserByUserName(name);
		if (matchedUser == null) return null;
		//TODO SHA Hash
		if (!matchedUser.getPassword().toString().equals(password)) return null;
		if (!matchedUser.getEnabled()) return null;
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        for (UserRole role : matchedUser.getUserRoles()){
        	grantedAuths.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(matchedUser, password, grantedAuths);
        LOGGER.info("Login"+ DatabaseLogger.FUNCTION_LOG_SEPERATOR + "User Authenticated: " + matchedUser);
        return auth;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void loginUser(User user){
		Authentication auth = authenticate(user.getUserName(), user.getPassword());
		if (auth != null){
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}
}
