package com.taskManager.taskManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.service.ITaskService;
// Clase encargada de gestionar las vistas del Frontend
@Controller
public class WebController {
	
	@Autowired
	ITaskService taskService;

	@GetMapping("users/login/view")
	public String showLoginForm() {
		return "login";
	}
	
	@GetMapping("users/register/view")
	public String showRegisterForm() {
		return "register";
	}
	
	@GetMapping("/tasks/view")
	public String showTaskPage() {
		return "tasks";
	}
	
	@GetMapping("/tasks/create/view")
	public String showCreateTaskForm() {
		return "create_task";
	}
	
	@GetMapping("/tasks/edit/view/{id}")
	public String showEditTask(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    if (userDetails == null) {
	        return "redirect:/login"; // usuario no autenticado
	    }

	    Task task = taskService.getTaskById(id);

	    if (task == null || !task.getUser().getUsername().equals(userDetails.getUsername())) {
	        return "redirect:/access-denied"; // tarea no existe o no pertenece al usuario
	    }

	    model.addAttribute("task", task);
	    return "edit_task";  // nombre de tu plantilla Thymeleaf, ej: edit-task.html
	}
	
}
