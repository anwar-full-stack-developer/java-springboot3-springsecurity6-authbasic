package com.anwar.authbasicspringsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.anwar.authbasicspringsecurity.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("", "/basic-auth/welcome", "/basic-auth/add-new-user", "/basic-auth/generate-token").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/basic-auth/user/**").authenticated()
				.and()
				.authorizeHttpRequests().requestMatchers("/basic-auth/admin/**").authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
                .authenticationManager(authenticationManager)
//                .and()
//                .anyRequest().authenticated()
//                .and()
                .httpBasic();

        return httpSecurity.build();
    }
	
//      @Bean
//      public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//          httpSecurity.csrf().disable()
//                  .authorizeHttpRequests()
////                  .requestMatchers(”/users/signUp”).permitAll()
//                  .anyRequest()
//                  .authenticated()
//                  .and()
//                  .httpBasic();
//          return httpSecurity.build();
//      }
      
      //in memory user details
//      @Bean
//      public InMemoryUserDetailsManager userDetailsService() {
//          UserDetails user = User.withDefaultPasswordEncoder()
//                  .username("anwar")
//                  .password("123")
//                  .roles("ADMIN")
//                  .build();
//          return new InMemoryUserDetailsManager(user);
//      }
      
      
}