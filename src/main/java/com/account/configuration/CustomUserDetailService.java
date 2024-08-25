package com.account.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.account.Repository.userRepository;
import com.account.entity.User;

@Component
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	userRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u =repo.findByEmail(username);
		if(u==null) {
			throw new UsernameNotFoundException("User not Found!");
		}
		else
		
		return new CustomUser(u);
	}

}
