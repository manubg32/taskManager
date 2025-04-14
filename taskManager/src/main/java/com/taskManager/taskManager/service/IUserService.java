package com.taskManager.taskManager.service;

import java.util.List;

import com.taskManager.taskManager.model.User;

// Interfaz que define los métodos para tratar la información relacionada con los usuarios
public interface IUserService {

	User getUserByUsername(String username);	// Obtener usuario por nombre de usuario
	
	User registerUser(User user);				// Dar de alta un nuevo usuario
	
	boolean existsByUsername(String username);	// Comprobar si existe un usuario
	
	List<User> getAllUsers();					// Obtener todos los usuarios
}
