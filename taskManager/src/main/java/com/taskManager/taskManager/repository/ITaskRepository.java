package com.taskManager.taskManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.model.AppUser;

// Interfaz que recoge los métodos para comunicarse con la BD relacionados con las tareas
public interface ITaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUserUsername(String username);	//Obtener tareas por usuario
	boolean existsById(Long id);		// Comprobar si existe el id de la tarea

}
