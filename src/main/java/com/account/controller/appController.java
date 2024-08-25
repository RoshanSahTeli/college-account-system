package com.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class appController {
	
	@GetMapping("/home")
	public String home() {
		return "admin_base";	
	}
	@GetMapping("/login")
	public String login() {
		 return "login_form";
	}

}
