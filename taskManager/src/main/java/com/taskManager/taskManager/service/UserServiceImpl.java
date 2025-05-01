package com.taskManager.taskManager.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskManager.taskManager.model.AppUser;
import com.taskManager.taskManager.model.ERole;
import com.taskManager.taskManager.repository.IUserRepository;

@Service	// Indicamos a Spring Boot que es un bean de servicio
public class UserServiceImpl implements IUserService {
	
	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	// Método constructor: Inyectamos el repositorio en la clase y el passwordEncoder
	public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// Método para obetener el usuario por el nombre de usuario
	@Override
	public AppUser getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	// Método para dar de alta un nuevo usuario
	@Override
	public AppUser registerUser(AppUser user) {
		
		// Si no tiene rol asignado, se pone USER por defecto
	    if (user.getRole() == null) {
	        user.setRole(ERole.USER);   
	    }
		// Codificamos la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	// Método para comprobar si existe un usuario por el nombre de usuario
	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	// Método para obtener una lista con todos los usuarios
	@Override
	public List<AppUser> getAllUsers() {
		return userRepository.findAll();
	}

}
