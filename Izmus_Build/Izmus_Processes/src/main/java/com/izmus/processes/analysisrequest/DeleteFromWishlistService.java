package com.izmus.processes.analysisrequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.cart.WishList;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IWishlistRepository;

@Component("DeleteFromWishlistService")
public class DeleteFromWishlistService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFromWishlistService.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IWishlistRepository wishlistRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) throws Exception{
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer startupId = (Integer) runtimeService.getVariable(execution.getId(), "startupId");
			WishList wishlistItem = wishlistRepository.findDistinctWishListByStartupIdAndUserId(startupId, user.getUserId());
			if (wishlistItem != null){
				wishlistRepository.delete(wishlistItem);
			}
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to Admins for analysis request\r\n" + e.getMessage());
			throw(e);
		}
	}
}
