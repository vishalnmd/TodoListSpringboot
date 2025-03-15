package com.springboot.login.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthTokenFilter extends OncePerRequestFilter{
	
	private Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class); 
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.debug("AuthTokenFilter called for URI : {}",request.getRequestURI());
		
		try {
			String jwt = parseJwt(request);
			
			if(jwt!=null && jwtUtils.validateJwtToken(jwt)) {
				
				String username = jwtUtils.getUsernameFromJwtToken(jwt);
				
				UserDetails userDetails = userDetailService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authenticate = 
						new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
				
				logger.debug("Roles for JWT {}",userDetails.getAuthorities());
				
				authenticate.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticate);				
			} 
		} catch (Exception e) {
			logger.error("Cannot set user Authentication : {}",e.getMessage());
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String parseJwt(HttpServletRequest request) {
		String jwt = jwtUtils.getJwtFromHeader(request);
		
		logger.debug("AuthTokenFilter jwt : {}",jwt);
		
		return jwt;
	}

}
