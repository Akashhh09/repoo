package com.dev.akash.DataJpa.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dev.akash.DataJpa.filter.JwtFilter;
import com.dev.akash.DataJpa.services.UserServices;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter{

	@Autowired
	UserServices userServices;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userServices);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}



	@Autowired
	private JwtFilter jwtFilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().disable();

		//http.csrf().disable();


		http.csrf().disable().authorizeRequests().antMatchers("/loginUser").permitAll()
		.antMatchers("/adminLogin").permitAll()
		.antMatchers("/addUser").permitAll()
		.antMatchers("/getUserByName").permitAll()
		.antMatchers("/resetPassword/{email}").permitAll()
		.antMatchers("/verifyOtp/{otp}").permitAll()
		.antMatchers(HttpMethod.OPTIONS ,"/**").permitAll()
		//.antMatchers(HttpMethod.GET).permitAll()
		//.antMatchers(HttpMethod.PUT).permitAll()
		.antMatchers(HttpMethod.DELETE).permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}








}
