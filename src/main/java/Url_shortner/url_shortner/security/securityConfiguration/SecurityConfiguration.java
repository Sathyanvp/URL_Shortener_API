package Url_shortner.url_shortner.security.securityConfiguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import Url_shortner.url_shortner.security.securityFilters.JWTFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	
	private UserDetailsService userDetailsService;
	
	private JWTFilter jwtFilter;
	
	public SecurityConfiguration(UserDetailsService userDetailsService, JWTFilter jwtFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		
		return security
				.csrf(customizer ->customizer.disable())
		        .authorizeHttpRequests(request -> request
		        		.requestMatchers("/register",
		        				"/login", 
		        				"/v1/api/**",
		                        "/v2/api-docs",
		                        "/v3/api-docs",
		                        "/v3/api-docs/**",
		                        "/swagger-resources",
		                        "/swagger-resources/**",
		                        "/configuration/ui",
		                        "/configuration/security",
		                        "/swagger-ui/**",
		                        "/webjars/**",
		                        "/swagger-ui.html")
		        		.permitAll()
		        		.anyRequest()
		        		.authenticated())
		        .httpBasic(Customizer.withDefaults())
		        .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		        .oauth2Login(Customizer.withDefaults())
		        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		        .build();
		
	}

	@Bean 
	public AuthenticationProvider authenticationprovider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}
	
	

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true); // allow encoded slashes
	    firewall.setAllowSemicolon(true);       // allow semicolons
	    firewall.setAllowUrlEncodedPercent(true); // allow % encoded
	    firewall.setAllowBackSlash(true); // optional
	    firewall.setAllowUrlEncodedPeriod(true); // optional
	    return firewall;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(HttpFirewall firewall) {
	    return (web) -> web.httpFirewall(firewall);
	}


}

