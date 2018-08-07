package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@SuppressWarnings("unused")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {//
	
	@Autowired
	UserDetail userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN", "USER");
//		auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 
			http
			
			// debut de config autorisation
			.authorizeRequests()

			.antMatchers("/comptes", "/clients" ).hasAuthority("ADMIN")
			.antMatchers("/operation").hasAnyAuthority("ADMIN","USER")
		.antMatchers("/", "/index.html", "/app/**","/user").permitAll()
			// authentifier toutes les URL restantes
			.anyRequest().fullyAuthenticated().and()
			// activer basic authentication
			.httpBasic().and()
			// configuration  la  session comme state less. 
			// Ce qui signifie qu'il y a pas de session dans le serveur
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			// désactiver  CSRF ( Cross Site Request Forgery )
			.csrf().disable();

	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	 @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  };

//@SuppressWarnings("deprecation")
//@Bean
//public static NoOpPasswordEncoder passwordEncoder() {
//return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//}
}
