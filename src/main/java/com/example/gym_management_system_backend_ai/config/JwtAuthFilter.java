package com.example.gym_management_system_backend_ai.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
private final JwtUtil jwtUtil;
	
	private final UserDetailsService userDetailsService;

	public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			
			String jwt = authHeader.substring(7);
			String email = jwtUtil.extractEmail(jwt);
			
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				
				if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
					
					UsernamePasswordAuthenticationToken authenticationToken = new
							UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
//					authenticationToken should also know about request param , since it has 
//					the valuable info and below is used to achive this
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
//					used to add the authenticationToken in the chain
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
