package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.cart.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {
	Cart findDistinctCartByItemId(Integer itemId);
}
