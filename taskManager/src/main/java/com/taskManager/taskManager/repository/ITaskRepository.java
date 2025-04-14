package com.taskManager.taskManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.model.User;

// Interfaz que recoge los métodos para comunicarse con la BD relacionados con las tareas
public interface ITaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser(User user);	//Obtener tareas por usuario
}
