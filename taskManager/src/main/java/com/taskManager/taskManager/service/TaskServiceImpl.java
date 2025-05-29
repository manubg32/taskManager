package com.taskManager.taskManager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.model.AppUser;
import com.taskManager.taskManager.repository.ITaskRepository;

@Service	// Indicamos a Spring Boot que es un bean de servicio
public class TaskServiceImpl implements ITaskService {

	private final ITaskRepository taskRepository;
    private final IUserService userService;

	
	// Método constructor: Inyectamos el repositorio en la clase
	public TaskServiceImpl(ITaskRepository taskRepository, IUserService userService) {
		this.taskRepository = taskRepository;
		this.userService = userService;
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
		 // Recuperar el usuario desde la base de datos
	    AppUser user = userService.getUserByUsername(task.getUser().getUsername());
	    if (user == null) {
	        throw new IllegalArgumentException("User does not exist");
	    }

	    // Asociar el usuario persistido a la tarea
	    task.setUser(user);

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
	
	// Método para guardar una tarea
	@Override
	public Task save(Task task) {
	    AppUser user = userService.getUserByUsername(task.getUser().getUsername());
	    if (user == null) {
	        throw new IllegalArgumentException("User does not exist");
	    }
	    task.setUser(user);
	    return taskRepository.save(task);
	}
}
