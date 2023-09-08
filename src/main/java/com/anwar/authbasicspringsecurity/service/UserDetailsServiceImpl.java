package com.anwar.authbasicspringsecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anwar.authbasicspringsecurity.entity.UserInfo;
import com.anwar.authbasicspringsecurity.repository.UserDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

	@Autowired
	private PasswordEncoder encoder;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Optional<UserInfo> userDetail = userDetailsRepository.findByUsername(username);
        
        if (userDetail == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        
     // Converting userDetail to UserDetails
     		return userDetail.map(UserInfoDetails::new)
     				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
     		
        // Create and return the Spring Security UserDetails object
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(userDetails.getUsername())
//                .password(userDetails.getPassword())
//                .roles("ADMIN") // Replace with appropriate roles from userDetails if available
//                .build();
    }

    public String addUser(UserInfo userInfo){
//        public String addUserDetails(UserInfo userInfo){
//        String encodedPassword = new BCryptPasswordEncoder().encode(userDetails.getPassword());
//        userInfo.setPassword(encodedPassword);
//        userDetailsRepository.save(userDetails);
    	
    	userInfo.setPassword(encoder.encode(userInfo.getPassword()));
    	userDetailsRepository.save(userInfo);
    	userDetailsRepository.flush();
		return "User Added Successfully";
    }
}
