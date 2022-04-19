package com.internship.retail_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This configuration class, takes care of the password's
 * encryption.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@Configuration
public class AppConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
