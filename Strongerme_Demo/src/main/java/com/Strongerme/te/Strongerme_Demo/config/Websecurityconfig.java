package com.Strongerme.te.Strongerme_Demo.config;

import java.security.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@SuppressWarnings("deprecation")
//@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Websecurityconfig {//extends WebSecurityConfigurerAdapter{
	

	public static final String[] PUBLI_URLS= {       //for swagger
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api/docs",
			"swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	
//		http
//				.csrf().disable()
//				.authorizeHttpRequests()
//				.antMatchers(HttpMethod.POST).hasRole("ADMIN")
//				.antMatchers(HttpMethod.GET).permitAll()          //.hasRole("NORMAL")
//				//.antMatchers("/v3/api-docs").permitAll()
//				.antMatchers(PUBLI_URLS).permitAll()
//				.anyRequest()
//				.authenticated()
//				.and()
//				.httpBasic();
////				.formLogin();
//	}
	
	@Bean
	public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers(HttpMethod.POST).hasRole("ADMIN")
		.antMatchers(HttpMethod.GET).permitAll()          //.hasRole("NORMAL")
		//.antMatchers("/v3/api-docs").permitAll()
		.antMatchers(PUBLI_URLS).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
//		.formLogin();
		
		return http.build();
	}
	
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user123").password(this.passwordEncoder().encode("0000")).roles("NORMAL");
//		auth.inMemoryAuthentication().withUser("admin123").password(this.passwordEncoder().encode("1111")).roles("ADMIN");
//		
//	}
	
	@Bean
	public  InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
		
		UserDetails user= User.withUsername("user123")
				.password(passwordEncoder.encode("0000"))
				.roles("NORMAL").build();
		UserDetails admin=User.withUsername("admin123")
				.password(passwordEncoder.encode("1111"))
				.roles("ADMIN").build();
		
		InMemoryUserDetailsManager inmemoryconfiger=new InMemoryUserDetailsManager(user,admin);
		return inmemoryconfiger;
		
	}
	
	
	/**
	 *  * ROUGH
	 */
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//	provider.setUserDetailsService(userDetailsService);
//		return provider;
//	}

	
	
//	@Bean  //so we can auto_wired it
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(11);
//	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }

	
}
