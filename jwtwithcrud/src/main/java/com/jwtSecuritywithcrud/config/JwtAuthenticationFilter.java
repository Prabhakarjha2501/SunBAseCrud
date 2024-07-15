package com.jwtSecuritywithcrud.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtSecuritywithcrud.service.JwtService;
import com.jwtSecuritywithcrud.service.UserService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
   private final JwtService jwtservice;
	
	private final UserService userservice;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String jwt ;
		final String userEmail;
		
		if (StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")) {
			
			logger.info("this is for heading bearer token");
			filterChain.doFilter(request, response);
			
			return;
		}
		
		  jwt = authHeader.substring(7);
		userEmail=jwtservice.extractUserName(jwt);
		
		logger.info(userEmail+"kjdkfhdfhdjfdsjfdkflksdflkds");
		
		
		if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetails= userservice.userDetailsService().loadUserByUsername(userEmail); 
			
			logger.info(userdetails+"dfdfdjfkjdkj+fdkfjPRABHAKAR");
			
			
			if(jwtservice.isTokenValid(jwt, userdetails)) {
				SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
				
				
				UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(userdetails, null,userdetails.getAuthorities());
				
				
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
			 securityContext.setAuthentication(token);
			 SecurityContextHolder.setContext(securityContext);
			 logger.info("Security context set with user: {}"+userdetails.getUsername());
			}
		}
		
		filterChain.doFilter(request, response);
	}

	
	
}
