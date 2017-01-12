package com.daou.setlist.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daou.setlist.admin.domain.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {

}
