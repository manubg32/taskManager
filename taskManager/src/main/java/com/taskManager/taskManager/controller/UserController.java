package com.taskManager.taskManager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManager.taskManager.model.User;
import com.taskManager.taskManager.service.IUserService;

@RestController				// Indicamos que es un controlador
@RequestMapping("/users")	// Indicamos su endpoint
public class UserController {

	// Creamos un objeto logger para escribir en el log
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	// Instanciamos la interfaz que contiene los métodos
	private final IUserService userService;		
	
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	// Llamada HTTP a la API REST para crear un usuario
	@PostMapping("/users")
	public ResponseEntity<User> createUser (@RequestBody User user) {
		
		// Si el usuario ya existe, lo indicamos mediante un error 409
		if (userService.existsByUsername(user.getUsername())) {
	        logger.warn("Username '{}' already exists", user.getUsername());
	        return new ResponseEntity<>(HttpStatus.CONFLICT);
	    }

		// Si faltan datos, lo indicamos mediante un error 400
	    if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
	        logger.warn("Username or password missing");
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    try {
	        User saved = userService.registerUser(user);
	        logger.info("User '{}' registered correctly", user.getUsername());
	        return new ResponseEntity<>(saved, HttpStatus.CREATED);	//Si se crea correctamente, devolvemos el usuario y un codigo 201
	    } catch (Exception e) {
	        logger.error("Error registering user '{}': {}", user.getUsername(), e.getMessage());
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Devolvemos un error 500 si hay algun fallo
	    }
	}
	
	// Llamada HTTP a la API REST para obtener un usuario
	@GetMapping("/users/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = userService.getUserByUsername(username); 	// Obtenemos el usuario
		
		// Si el usuario no existe devolvemos un 404
		if (user == null) {
			logger.warn("User '{}' not found", username);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		// Si el usuario existe lo devolvemos junto a un 200
		logger.info("User '{}' found", username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	// Llamada HTTP a la API REST para obtener todos los usuarios
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		
		// Si no hay usuarios devolvemos un 204
		if (users.isEmpty()) {
			logger.info("No users found in the system");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		// Si todo está correcto, devolvemos la lista junto a un 200
		logger.info("Found {} users", users.size());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	// Llamada HTTP a la API REST para comprobar si existe un usuario
	@GetMapping("/users/check/{username}")
	public ResponseEntity<Void> checkIfUserExists(@PathVariable String username) {
		
		boolean exists = userService.existsByUsername(username);
		
		// Si el usuario existe devolvemos un error 409 y si no existe un 200
		logger.info("Username '{}' is {}", username, exists ? "already taken" : "available");
		return ResponseEntity
				.status(exists ? HttpStatus.CONFLICT : HttpStatus.OK)
				.build();
	}
	
}
