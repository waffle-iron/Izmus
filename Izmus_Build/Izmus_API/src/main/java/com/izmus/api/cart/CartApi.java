package com.izmus.api.cart;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.domain.cart.Cart;
import com.izmus.data.domain.cart.WishList;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.ICartRepository;
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
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/MyRequests")
	@PreAuthorize("hasPermission('Cart Menu/My Requests', '')")
	public List<Cart> getMyRequests() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Cart> returnList = cartRepository.findCartByUserId(user.getUserId());
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/Wishlist")
	@PreAuthorize("hasPermission('Cart Menu/Wish List', '')")
	public List<WishList> getWishlist() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<WishList> returnList = wishlistRepository.findWishListByUserId(user.getUserId());
		return returnList;
	}
}
