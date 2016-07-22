package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.cart.WishList;

@Repository
public interface IWishlistRepository extends JpaRepository<WishList, Integer> {
	WishList findDistinctWishListByItemId(Integer itemId);
}
