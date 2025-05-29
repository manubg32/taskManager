# Task Manager - API REST con Spring Boot

Este proyecto es una aplicación web backend desarrollada con **Spring Boot** que permite gestionar tareas de manera eficiente. La API expone endpoints para crear, leer, actualizar y eliminar tareas, así como para la gestión de usuarios con autenticación basada en JWT.

## 🛠️ Tecnologías utilizadas

- Java 24
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Maven
- Eclipse IDE
- Lombok
- Swagger UI (documentación)
- Docker (para despliegue)

## 📁 Estructura del proyecto

- `controller/`: Controladores REST.
- `service/`: Lógica de negocio.
- `repository/`: Interfaces de acceso a datos.
- `model/`: Entidades JPA.
- `dto/`: Clases para transferencia de datos.
- `security/`: Configuración de seguridad y JWT.
- `config/`: Configuraciones generales.

## 🔐 Autenticación

La autenticación de usuarios se realiza mediante JWT. Los endpoints públicos son:

- `POST /users/login`: Iniciar sesión y obtener token JWT.
- `POST /users/create`: Registrar nuevo usuario.

Los demás endpoints requieren el uso del token JWT en la cabecera `Authorization`:

Authorization: Bearer <token>

## 🚀 Ejecución local

### Requisitos previos

- JDK 21 o superior
- Maven
- PostgreSQL
- (Opcional) Docker

### Pasos

####1. Clonar el repositorio:
   

   git clone https://github.com/manubg32/taskManager
   
   cd taskManager
 

####2. Configurar la base de datos en application.properties:


spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanagerdb

spring.datasource.username=tu_usuario

spring.datasource.password=tu_contraseña


####3. Compilar y ejecutar:

mvn spring-boot:run

####4. Acceder a la documentación Swagger (si está habilitada):

http://localhost:8080/swagger-ui/index.html

####5. Acceder al login

http://localhost:8080/users/login/view

#📦 Docker (opcional)
Puedes ejecutar la aplicación y la base de datos usando Docker (pendiente de incluir Dockerfile y docker-compose.yml).

#✍️ Autor

Trabajo de Fin de Grado - TaskManager

Autor: Manuel Borrero Guerrero

Centro Educativo: I.E.S. El Majuelo

Año: 2025