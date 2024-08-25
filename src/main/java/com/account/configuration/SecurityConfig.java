package com.account.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomSuccessHandler handler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public UserDetailsService getUserDetails() {
		return new CustomUserDetailService();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetails());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		http.csrf(c->c.disable()).authorizeHttpRequests(request->request
				.requestMatchers("/ADMIN/**").hasRole("ADMIN").requestMatchers("/USER/**")
				.hasRole("USER").requestMatchers("/**").permitAll())
				.formLogin(f->f.loginPage("/login").loginProcessingUrl("/login").successHandler(handler).permitAll())
				.logout(l->l.logoutUrl("/logout").permitAll());
		return http.build();
	}
//	@Bean
//	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
//		http.csrf(c->c.disable()).authorizeHttpRequests(request->request
//				
//				.requestMatchers("/admin/home").permitAll().anyRequest().authenticated()).logout(l->l.logoutUrl("/logout").permitAll())
//				;
//		return http.build();
//	}

}
