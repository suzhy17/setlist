package com.daou.setlist.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.daou.setlist.admin.security.CustomAuthenticationFailureHandler;
import com.daou.setlist.admin.security.CustomAuthenticationSuccessHandler;
import com.daou.setlist.admin.service.AdminUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AdminUserService adminUserService;

	@Bean
	public ShaPasswordEncoder shaPasswordEncoder() {
		return new ShaPasswordEncoder(512);
	}

	@Bean
	public ReflectionSaltSource sltSource() {
		ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
		reflectionSaltSource.setUserPropertyToUse("salt");
		return reflectionSaltSource;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setSaltSource(sltSource());
		provider.setUserDetailsService(adminUserService);
		provider.setPasswordEncoder(shaPasswordEncoder());
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/static/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login-process").permitAll()
				.passwordParameter("password")
				.usernameParameter("userId")
				.successHandler(customAuthenticationSuccessHandler())
				.failureHandler(customAuthenticationFailureHandler())
				.and()
			.logout()
				.logoutSuccessUrl("/login")
				.and()
			.authorizeRequests()
				.antMatchers("/**").access("hasAuthority('ROLE_USER')")
				.and()
			.csrf()
				.disable();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() throws Exception {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() throws Exception {
		return new CustomAuthenticationFailureHandler();
	}
}
