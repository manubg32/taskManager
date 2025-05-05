package com.taskManager.taskManager.security;

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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// Instanciamos el JwtUtil
	private final JwtUtil jwtUtil;
	
	// Instanciamos el UserDetailsService
	private final UserDetailsService userDetailsService;

	// Inyectamos las dependencias en la clase
	public JwtAuthenticationFilter(JwtUtil jwutil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwutil;
		this.userDetailsService = userDetailsService;
	}

	// Metodo implementado del OncePerRequestFilter que contiene la funcionalidad
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization"); // Obtenemos el Header

		// Comprobamos que el Header comience con "Bearer "
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Obtenemos el token
		String token = authHeader.substring(7);
		
		// Obtenemos el usuario
		String username = jwtUtil.extractUsername(token);

		// Agregamos el usuario al SecurityContext
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// Si el token es valido creamos el token con sus detalles
			if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// Lo mandamos a la cadena de filtrado
		filterChain.doFilter(request, response);
	}

}
