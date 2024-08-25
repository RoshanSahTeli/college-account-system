package com.account.configuration;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.account.entity.User;

public class CustomUser implements UserDetails {
	
	User u;
	

	public CustomUser(User u) {
		super();
		this.u = u;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority= new SimpleGrantedAuthority(u.getRole());
		
		// TODO Auto-generated method stub
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return u.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return u.getEmail();
	}

}
