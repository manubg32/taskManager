package com.taskManager.taskManager.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity				// Indicamos que es una entidad para la persistencia JPA en BBDD
@AllArgsConstructor	// Constructor con todos los parametros
@NoArgsConstructor	// Constructor sin parametros
@Builder			// Nos permite la creación más ordenada de objetos de esta clase
@Data				// Indicamos que es una entidad (@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode)
public class AppUser {
	
	@Id							// Indicamos que este atributo será la clave primaria
	@GeneratedValue				// Y que se generará automaticamente
	private Long id;
	
	private String username;
	
	private String password;
	
	@Enumerated(EnumType.STRING)// Esto nos permite que el atributo enum se muestre como el texto y no como número	
	private ERole role;
	
	@OneToMany(mappedBy = "user")// Indicamos que un usuario tendrá varias tareas 1:N y que estarán mapeadas a este
	private List<Task> tasks;
}
