package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.users.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	User findDistinctUserByUserName(String userName);
}
