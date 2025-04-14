package com.taskManager.taskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskManager.taskManager.model.User;

//Interfaz que recoge los métodos para comunicarse con la BD relacionados con los usuarios
public interface IUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);	// Encontrar usuario por nombre de usuario
	boolean existsByUsername(String username);		// Comprobar si existe el nombre de usuario ya creado

}
