package com.springapp.inventoryapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springapp.inventoryapi.service.UserService;

@Configuration
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter{	
	@Autowired
	private UserService userService; 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* List out APIs and define roles for access */
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/user/login").authenticated()
			/*
			.antMatchers(HttpMethod.POST, "/category/add").hasAnyAuthority("EXECUTIVE")
			.antMatchers(HttpMethod.GET, "/category/all").permitAll()
			.antMatchers(HttpMethod.PUT, "/category/update/{id}").hasAnyAuthority("EXECUTIVE")
			.antMatchers(HttpMethod.DELETE, "/category/delete/{id}").hasAnyAuthority("EXECUTIVE")
			
			.antMatchers(HttpMethod.POST, "/customer/add").permitAll()
			.antMatchers(HttpMethod.GET, "/order/all").hasAnyAuthority("SUPPLIER")
			*/
			.anyRequest().permitAll()
			.and().httpBasic()
			.and().csrf().disable();
	}
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private DaoAuthenticationProvider getAuthenticationProvider(){
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(getEncoder());
		dao.setUserDetailsService(userService);
		return dao;
	}
}