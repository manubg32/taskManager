package com.taskManager.taskManager.service;

import java.util.List;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.model.User;

// Interfaz que define los métodos para tratar la información relacionada con las tareas
public interface ITaskService {
	
	List <Task> getAllTasks();				// Obtener todas las tareas
		
	Task getTaskById(Long id);				// Obtener la tarea por ID
	
	Task createTask(Task task);				// Crear una tarea
	
	void deleteTask(Long id);				// Borrar una tarea

	List <Task> getTasksByUsername(String username);	// Obtener las tareas de un usuario
	
	boolean existsById(Long id);	// Comprobar si existe una tarea

}
