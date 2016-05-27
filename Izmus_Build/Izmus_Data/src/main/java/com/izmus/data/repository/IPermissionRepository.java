package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izmus.data.domain.users.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Integer> {
	Permission findDistinctPermissionByPermissionName(String permissionName);
}
