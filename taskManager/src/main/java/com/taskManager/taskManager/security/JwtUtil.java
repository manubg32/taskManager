package com.taskManager.taskManager.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	// Configuraciones que podriamos encapsular en un fichero .properties
	private static final String SECRET_KEY = "mySecretKey123456789012345678901234567890"; // Clave para cifrar el token, minimo 32 char
	private static final long EXPIRATION_TIME = 86400000; // Tiempo de expiracion del token: 24 horas en ms
	
	// Generamos una key con el cifrado
	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	// Metodo para generar token
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	// Metodo para obtener el username del token
	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}
	
	// Metodo para validar el token
	public boolean isTokenValid(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return extractedUsername.equals(username) && !isTokenExpired(token);
	}
	
	// Metodo para comprobar si el token ha expirado
	private boolean isTokenExpired(String token) {
		return parseClaims(token).getExpiration().before(new Date());
	}
	
	// Metodo para parsear informacion del token
	private Claims parseClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
