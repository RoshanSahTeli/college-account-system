package com.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("Spring"));
		System.out.println("Checking");
		System.out.println("checking second");
		System.out.println("Third");
	}

}
