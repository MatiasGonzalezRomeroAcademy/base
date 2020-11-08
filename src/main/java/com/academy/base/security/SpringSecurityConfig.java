package com.academy.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.academy.base.service.JpaUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUserDetailService userDetailService;

	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder //
				.userDetailsService(userDetailService) //
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.addFilter(new JwtAuthenticationFilter(authenticationManager(), getApplicationContext())) //
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), getApplicationContext())) //
				.csrf().disable() // we are going to use JWT
				.exceptionHandling() //
				// .authenticationEntryPoint(unauthorizedHandler) //
				.and() //
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
				.and() //
				.authorizeRequests().antMatchers("/auth/login", "/swagger-ui.html").permitAll() //
				.and() //
				.authorizeRequests().antMatchers("/api/**").authenticated() //
				.antMatchers("/", //
						"/favicon.ico", //
						"/**/*.png", //
						"/**/*.gif", //
						"/**/*.svg", //
						"/**/*.jpg", //
						"/**/*.html", //
						"/**/*.css", //
						"/**/*.js")
				.permitAll();
	}
}
