package com.anwar.authbasicspringsecurity.controller;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.anwar.authbasicspringsecurity.component.Base64Component;
import com.anwar.authbasicspringsecurity.entity.AuthRequest;
import com.anwar.authbasicspringsecurity.entity.UserInfo;
import com.anwar.authbasicspringsecurity.service.BasicauthService;
import com.anwar.authbasicspringsecurity.service.UserDetailsServiceImpl;


@RestController
@RequestMapping("/basic-auth")
public class BasicauthController {

	@Autowired
	private UserDetailsServiceImpl service;

	@Autowired
	private BasicauthService basicauthService;


	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/add-new-user")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	@GetMapping("/user/user-profile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

	@GetMapping("/admin/admin-profile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generate-token")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return basicauthService.generateToken(authRequest.getUsername(), authRequest.getPassword());  
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

}

