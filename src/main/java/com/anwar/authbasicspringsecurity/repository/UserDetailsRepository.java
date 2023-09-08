package com.anwar.authbasicspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anwar.authbasicspringsecurity.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByName(String username);
	Optional<UserInfo> findByUsername(String username);
}