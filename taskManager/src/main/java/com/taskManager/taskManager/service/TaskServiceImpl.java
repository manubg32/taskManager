package com.taskManager.taskManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.model.User;
import com.taskManager.taskManager.repository.ITaskRepository;

@Service	// Indicamos a Spring Boot que es un bean de servicio
public class TaskServiceImpl implements ITaskService {

	private final ITaskRepository taskRepository;
	
	// Método constructor: Inyectamos el repositorio en la clase
	public TaskServiceImpl(ITaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	// Método para obtener todas las tareas
	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	// Método para obtener una tarea por ID
	@Override
	public Task getTaskById(Long id) {
		return taskRepository.findById(id).orElse(null);
	}

	// Método para crear una tarea
	@Override
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	// Método para borrar una tarea
	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);		
	}

	// Método para obtener todas las tareas de un usuario
	@Override
	public List<Task> getTasksByUsername(String username) {
	    return taskRepository.findByUserUsername(username);

	}

	// Método para comprobar si existe una tarea por Id
	@Override
	public boolean existsById(Long id) {
		return taskRepository.existsById(id);
	}
	
}
