package com.springboot.login.auth;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.login.model.Users;


@SuppressWarnings("serial")
public class MyUserDetails implements UserDetails{
	
	private static final Logger log = LoggerFactory.getLogger(MyUserDetails.class);
	
	private Users usr;
	
	public MyUserDetails(Users user) {		
		this.usr = user;
		log.debug("User details {}",user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.info("user roles : {}",usr.getRole());
		return Collections.singleton(new SimpleGrantedAuthority(usr.getRole()));
	}

	@Override
	public String getPassword() {
		return usr.getPassword();
	}

	@Override
	public String getUsername() {		
		return usr.getEmail();
	}

}
