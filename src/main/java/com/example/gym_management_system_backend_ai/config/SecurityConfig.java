package com.example.gym_management_system_backend_ai.config;

//Bug Fix: Removed All Unnecessary Imports
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtAuthFilter jwtAuthFilter;
	
	private final CustomUserServiceDetails customUserServiceDetails;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomUserServiceDetails customUserServiceDetails) {
		super();
		this.jwtAuthFilter = jwtAuthFilter;
		this.customUserServiceDetails = customUserServiceDetails;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.cors(c -> c.configurationSource(corsConfigurationSource()));
		
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/auth/login",
							"/api/auth/register",
							"/api/auth/send-otp-email").permitAll()
//					Bug Fix: Using PathPatternRequestMatcher instead of AntPathRequestMatcher which is deprecated, to match the paths correctly
					.requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/v3/api-docs/**")).permitAll()
					.requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/swagger-ui.html")).permitAll()
					.requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/swagger-ui/**")).permitAll()
					.anyRequest().authenticated()
					);
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
//		Below line is responsible for validating our JWT token 
//		(used to add filter before User Authentication filter)
		
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource()
	{
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://localhost:5173/");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		AuthenticationManagerBuilder authenticationManagerBuilder = http.
				getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(customUserServiceDetails);
		
		return authenticationManagerBuilder.build();
	}

}
