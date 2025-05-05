package com.taskManager.taskManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.taskManager.taskManager.security.CustomUserDetailsService;
import com.taskManager.taskManager.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	// Declaramos JwtAuthenticationFilter
	private final JwtAuthenticationFilter jwtAuthFilter;
	// Declaramos CustomUserDetailsService
	private final CustomUserDetailsService userDetailsService;
	
	// Inyectamos las dependencias en el constructor
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	// Declaramos la cadena de seguridad de Spring Boot
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .csrf(csrf -> csrf.disable()) // Desactivamos la proteccion csrf
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Le quitamos la responsabilidad de la gestion de la seguridad a Spring Security
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/users/login", "/users/create", "/users/check/**").permitAll() // Permitimos el acceso libre a /login y a /check 
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitimos el acceso libre a swagger y a api-docs
            .anyRequest().authenticated() // El resto de peticiones necesitaran autenticacion
        )
        .authenticationProvider(authenticationProvider()) // Declaramos quien tendra la responsabilidad de autenticar
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Agregamos el filtro antes del propio de Spring Security

        return http.build();
    }
    
    // Configuracion del authenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userDetailsService);
    	provider.setPasswordEncoder(passwordEncoder());
    	return provider;
    }
    
    // Configuracion del authenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
    
    // Configuracion del passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}
