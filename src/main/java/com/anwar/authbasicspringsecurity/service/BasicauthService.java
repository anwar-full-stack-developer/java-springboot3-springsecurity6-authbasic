package com.anwar.authbasicspringsecurity.service;

import org.springframework.stereotype.Component;

import com.anwar.authbasicspringsecurity.component.Base64Component;

@Component
public class BasicauthService {

	public String generateToken(String userName, String password) {
		String str = Base64Component.encode(userName+":"+password);
		return str;
	}
}
