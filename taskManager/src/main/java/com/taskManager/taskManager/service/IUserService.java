package com.taskManager.taskManager.service;

import java.util.List;

import com.taskManager.taskManager.model.AppUser;

// Interfaz que define los métodos para tratar la información relacionada con los usuarios
public interface IUserService {

	AppUser getUserByUsername(String username);	// Obtener usuario por nombre de usuario
	
	AppUser registerUser(AppUser user);				// Dar de alta un nuevo usuario
	
	boolean existsByUsername(String username);	// Comprobar si existe un usuario
	
	List<AppUser> getAllUsers();					// Obtener todos los usuarios
}
