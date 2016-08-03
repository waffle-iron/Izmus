package com.izmus.api.cart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.domain.cart.Cart;
import com.izmus.data.domain.cart.WishList;
import com.izmus.data.domain.startups.StartupAbstract;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.ICartRepository;
import com.izmus.data.repository.IStartupAbstractRepository;
import com.izmus.data.repository.IWishlistRepository;

@RestController
@RequestMapping("api/Cart")
public class CartApi {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CartApi.class);
	@Autowired
	private ICartRepository cartRepository;
	@Autowired
	private IWishlistRepository wishlistRepository;
	@Autowired
	private IStartupAbstractRepository startupAbstractRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/MyRequests")
	@PreAuthorize("hasPermission('Cart Menu/My Requests', '')")
	public List<Cart> getMyRequests() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Cart> returnList = cartRepository.findCartByUserId(user.getUserId());
		LOGGER.info("User Got My Request List");
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/Wishlist")
	@PreAuthorize("hasPermission('Cart Menu/Wish List', '')")
	public List<WishList> getWishlist() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<WishList> returnList = wishlistRepository.findWishListByUserId(user.getUserId());
		LOGGER.info("User Got Wish List");
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/PagedWishlist")
	@PreAuthorize("hasPermission('Cart Menu/Wish List', '')")
	public List<Object> getPagedWishlist(
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "pageSize") Integer pageSize) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PageRequest pageable = new PageRequest(pageNumber, pageSize);
		Page<WishList> pageableList;
		pageableList = wishlistRepository.findByUserId(user.getUserId(), pageable);
		List<StartupAbstract> startupList = new ArrayList<>();
		for (WishList listItem : pageableList.getContent()){
			StartupAbstract startup = startupAbstractRepository.findDistinctStartupAbstractByStartupId(listItem.getStartupId());
			startupList.add(startup);
		}
		List<Object> returnList = new ArrayList<>();
		returnList.add(startupList);
		returnList.add(pageableList.getNumberOfElements());
		LOGGER.info("User Got Paged Wish List");
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/PagedMyRequests")
	@PreAuthorize("hasPermission('Cart Menu/Wish List', '')")
	public List<Object> getPagedMyRequests(
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "pageSize") Integer pageSize) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PageRequest pageable = new PageRequest(pageNumber, pageSize);
		Page<Cart> pageableList;
		pageableList = cartRepository.findByUserId(user.getUserId(), pageable);
		List<StartupAbstract> startupList = new ArrayList<>();
		for (Cart listItem : pageableList.getContent()){
			StartupAbstract startup = startupAbstractRepository.findDistinctStartupAbstractByStartupId(listItem.getStartupId());
			startupList.add(startup);
		}
		List<Object> returnList = new ArrayList<>();
		returnList.add(startupList);
		returnList.add(pageableList.getNumberOfElements());
		LOGGER.info("User Got Paged My Requests");
		return returnList;
	}
}
