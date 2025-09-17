package org.oapen.memoproject.clientweb.config;

import org.oapen.memoproject.clientweb.ClientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity( // need this to use @PreAuthorize on methods
		prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	/*
	 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { // configure Web
	 * security... }
	 */
	
	// Use to generate test passwords https://bcrypt-generator.com/

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		
		return new ClientDetailsService();
	}
	
	// configure SecurityFilterChain
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable() 
			.authorizeRequests()
			.antMatchers("/assets/**","/customassets/**","/file/**","/favicon.ico","/clients/*")
            	.permitAll()
			.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/", true)  // redirect to home after successful login
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.logoutSuccessUrl("/login")
			.and()	
				.rememberMe()
				.key("somethingextremelysecretandutterlyincomprehensible")
				.tokenValiditySeconds(86400) // 1 day, default = two weeks validity
			; 
		
		return http.build();
	}

	
	
}
