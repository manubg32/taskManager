package com.taskManager.taskManager.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity				// Indicamos que es una entidad para la persistencia JPA en BBDD
@Data				// Indicamos que es una entidad (@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode)
@AllArgsConstructor	// Constructor con todos los parametros
@NoArgsConstructor	// Constructor sin parametros
@Builder			// Nos permite la creación más ordenada de objetos de esta clase
public class Task {
	
	@Id							// Indicamos que este atributo será la clave primaria
	@GeneratedValue				// Y que se generará automaticamente
	private Long id;
	
	private String title;
	
	private String description;
	
	private LocalDate dueDate;
	
	private Boolean complete;
	
	@Enumerated(EnumType.STRING)// Esto nos permite que el atributo enum se muestre como el texto y no como número	
	private EPriority priority;
	
	@ManyToOne					//Relación N:1
	@JoinColumn(name = "user_id")// Clave foránea que relaciona la tarea con el usuario
	private User user;
}
