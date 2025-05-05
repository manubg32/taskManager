package com.taskManager.taskManager.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskManager.taskManager.model.AppUser;
import com.taskManager.taskManager.repository.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Declaramos el userRepository
	private final IUserRepository userRepository;
	
	// Inyectamos las dependencias en el constructor
	public CustomUserDetailsService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// Sobreescribimos el metodo de carga por usuario
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())  // Asegúrate de que los roles se pasan correctamente
                .build();
	}

}
