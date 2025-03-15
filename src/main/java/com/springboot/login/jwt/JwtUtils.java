package com.springboot.login.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${spring.app.jwtExpirations}")
	private String jwtExpirations;
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getJwtFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		logger.debug("Authorization Token {}",bearerToken);

		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			Optional<Cookie> cookieJwt = Arrays.stream(request.getCookies())
					.filter(cookie -> "jwt".equals(cookie.getName())).findFirst();

			if (cookieJwt.isPresent()) {
				logger.info("Cookie get atrribute from request : {}",cookieJwt.get().getValue());
				return cookieJwt.get().getValue();
			}
		} else {
			return bearerToken.substring(7);
		}

		return null;
	}
	
	public String generateTokenFromUsername(UserDetails userDetails) {
		String username = userDetails.getUsername();
		logger.info("jwtExpiration : {}",jwtExpirations);
		
		long expirationMillis = Long.parseLong(jwtExpirations);
		
		Date expirationDate = Date.from(Instant.now().plusMillis(expirationMillis));	
		
		logger.info("expiration date {}",expirationDate);
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expirationDate)
				.signWith(key())
				.compact();
				
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			
			logger.debug(authToken);
			
			Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(authToken);
			
			return true;
			
		} catch (MalformedJwtException e) {
			logger.error("Invalid jwt token : {}",e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("jwt token is expired : {}",e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("jwt token is unsupported : {}",e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("jwt claims string is empty : {}",e.getMessage());
		}
		
		return false;
	}
	
}
