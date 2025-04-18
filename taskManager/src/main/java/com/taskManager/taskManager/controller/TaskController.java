package com.taskManager.taskManager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.model.User;
import com.taskManager.taskManager.service.ITaskService;

@RestController				// Indicamos que es un controlador
@RequestMapping("/tasks")	// Indicamos su endpoint
public class TaskController {
	
	// Creamos un objeto logger para escribir en el log
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	// Instanciamos la interfaz que contiene los métodos
	private final ITaskService taskService;
	
	public TaskController(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	// Llamada HTTP a la API REST para obtener todas las tareas
	@GetMapping("")
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		
		// Si no hay tareas devolvemos un 204
		if (tasks.isEmpty()) {
			logger.info("No tasks found in the system");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		// Si todo está correcto, devolvemos la lista junto a un 200
		logger.info("Found {} tasks", tasks.size());
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	// Llamada HTTP a la API REST para obtener una tarea
		@GetMapping("/{id}")
		public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
			Task task = taskService.getTaskById(id);	// Obtenemos la tarea
			
			// Si la tarea no existe devolvemos un 404
			if (task == null) {
				logger.warn("Task '{}' not found", id);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			// Si la tarea existe lo devolvemos junto a un 200
			logger.info("Task '{}' found", id);
			return new ResponseEntity<>(task, HttpStatus.OK);
		}
		
		// Llamada HTTP a la API REST para crear una tarea
		@PostMapping("")
		public ResponseEntity<Task> createTask(@RequestBody Task task) {
			
			// Si faltan datos, lo indicamos mediante un error 400
		    if (task.getTitle() == null || task.getTitle().isEmpty() ||
		    	    task.getDescription() == null || task.getDescription().isEmpty() ||
		    	    task.getDueDate() == null || task.getComplete() == null) {
		        logger.warn("Information missing");
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }

		    try {
		        Task saved = taskService.createTask(task);
		        logger.info("Task '{}' created correctly", task.getTitle());
		        return new ResponseEntity<>(saved, HttpStatus.CREATED);	//Si se crea correctamente, devolvemos la tarea y un codigo 201
		    } catch (Exception e) {
		        logger.error("Error creating task '{}': {}", task.getTitle(), e.getMessage());
		        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Devolvemos un error 500 si hay algun fallo
		    }
		}
		
		// Llamada HTTP a la API REST para actualizar una tarea
		@PutMapping("/{id}")
		public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
			Task existingTask = taskService.getTaskById(id);
			
			// Si no existe devolvemos un 404
			if (existingTask == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
			// Si existe actualizamos los campos y la guardamos
			existingTask.setTitle(task.getTitle());
			existingTask.setDescription(task.getDescription());
			existingTask.setDueDate(task.getDueDate());
			existingTask.setComplete(task.getComplete());
			existingTask.setPriority(task.getPriority());
			existingTask.setUser(task.getUser());
			
			// Llamamos a createTask para que actualize con el método save de JPA
			try {
		        Task saved = taskService.createTask(existingTask);
		        logger.info("Task '{}' updated correctly", task.getTitle());
		        return new ResponseEntity<>(saved, HttpStatus.OK);	//Si se crea correctamente, devolvemos la tarea y un codigo 200
		    } catch (Exception e) {
		        logger.error("Error creating task '{}': {}", task.getTitle(), e.getMessage());
		        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Devolvemos un error 500 si hay algun fallo
		    }
		}
		
		// Llamada HTTP a la API REST para borrar una tarea
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
			
			// Comprobamos si existe una tarea con ese Id
			if (taskService.existsById(id)) {
				// Borramos la tarea y retornamos un 204
				taskService.deleteTask(id);
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			} else {
				// Si no existe la tarea retornamos un 404
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
		
		// Llamada HTTP a la API REST para obtener todas las tareas de un usuario
		@GetMapping("/user/{username}")
		public ResponseEntity<List<Task>> getTasksByUsername(@PathVariable String username) {
		    List<Task> tasks = taskService.getTasksByUsername(username);

		    if (tasks.isEmpty()) {
		        logger.info("No tasks found for user '{}'", username);
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		    logger.info("Found {} tasks for user '{}'", tasks.size(), username);
		    return new ResponseEntity<>(tasks, HttpStatus.OK);
		}

}
