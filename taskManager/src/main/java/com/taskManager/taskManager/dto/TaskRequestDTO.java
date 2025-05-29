package com.taskManager.taskManager.dto;

import java.time.LocalDate;

import com.taskManager.taskManager.model.EPriority;

import lombok.Data;

@Data
public class TaskRequestDTO { // Data Tansfer Object para gestionar el envio de datos de una peticion del frontend al backend
	
	private String title;
	private String description;
	private LocalDate dueDate;
	private EPriority priority;
	Boolean complete;
	String username;
	
	

}
