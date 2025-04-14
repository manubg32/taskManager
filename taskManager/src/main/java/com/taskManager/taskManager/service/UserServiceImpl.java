package com.taskManager.taskManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taskManager.taskManager.model.User;
import com.taskManager.taskManager.repository.IUserRepository;

@Service	// Indicamos a Spring Boot que es un bean de servicio
public class UserServiceImpl implements IUserService {
	
	private final IUserRepository userRepository;
	
	// Método constructor: Inyectamos el repositorio en la clase
	public UserServiceImpl(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Método para obetener el usuario por el nombre de usuario
	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	// Método para dar de alta un nuevo usuario
	@Override
	public User registerUser(User user) {
		return userRepository.save(user);
	}

	// Método para comprobar si existe un usuario por el nombre de usuario
	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	// Método para obtener una lista con todos los usuarios
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
