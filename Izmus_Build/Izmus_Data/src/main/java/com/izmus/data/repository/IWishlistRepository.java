package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.cart.WishList;

@Repository
public interface IWishlistRepository extends JpaRepository<WishList, Integer> {
	WishList findDistinctWishListByItemId(Integer itemId);
	List<WishList> findWishListByUserId(Integer userId);
	WishList findDistinctWishListByStartupIdAndUserId(Integer startupId, Integer userId);
	Page<WishList> findByUserId(Integer userId, Pageable pageable);
}
