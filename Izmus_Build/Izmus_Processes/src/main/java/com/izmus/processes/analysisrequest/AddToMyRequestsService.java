package com.izmus.processes.analysisrequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.cart.Cart;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.ICartRepository;

@Component("AddToMyRequestsService")
public class AddToMyRequestsService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(AddToMyRequestsService.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ICartRepository cartRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) throws Exception{
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer startupId = (Integer) runtimeService.getVariable(execution.getId(), "startupId");
			
			Cart cart = new Cart();
			cart.setStartupId(startupId);
			cart.setUserId(user.getUserId());
			cartRepository.save(cart);
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to Admins for analysis request\r\n" + e.getMessage());
			throw(e);
		}
	}
}
