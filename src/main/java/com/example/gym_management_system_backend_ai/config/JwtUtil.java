package com.example.gym_management_system_backend_ai.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${app.secret.key}")
	private String SECRET_KEY;

	public String generateToken(String email)
	{
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 12))
				.and()
				.signWith(getKey())
				.compact()
				;
	}

	private SecretKey getKey() {
		// TODO Auto-generated method stub

		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver)
	{
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public String extractEmail(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}

	public boolean validateToken(String token, String email)
    {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }
	
	private boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}
}

