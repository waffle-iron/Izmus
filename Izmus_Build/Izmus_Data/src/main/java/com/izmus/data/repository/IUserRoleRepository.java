package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.users.UserRole;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
	UserRole findDistinctUserRoleByRoleName(String roleName);
}
