# Renta de Vehículos API

REST API para gestión de alquiler de vehículos construida con Spring Boot 3.

## Stack tecnológico

- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Data JPA** — persistencia con Hibernate
- **Spring Security** — BCrypt para contraseñas, OAuth2 resource server incluido
- **PostgreSQL 15** — base de datos relacional
- **Lombok** — reducción de boilerplate
- **Spring Validation** — validación de requests
- **Docker / Docker Compose** — entorno de base de datos

## Requisitos previos

- Java 17+
- Maven 3.8+
- Docker y Docker Compose

## Configuración y ejecución

### 1. Levantar la base de datos

```bash
docker-compose up -d
```

Esto inicia PostgreSQL en el puerto `5433` con:
- Base de datos: `vehicle_rental`
- Usuario: `alejo`

### 2. Configurar `application.properties`

Copia `application.properties.example` como `application.properties` y rellena los valores. Los campos requeridos son:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/vehicle_rental
spring.datasource.username=<tu-usuario>
spring.datasource.password=<tu-contraseña>
spring.jpa.hibernate.ddl-auto=update
encryption.secret.key=<clave-AES-256-en-base64>
```

Para generar una clave AES válida puedes usar `EncryptionUtil.generateKey()`.

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Endpoints

### Usuarios — `/vehicle-rental/user`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/create` | Crear usuario |
| `GET` | `/list` | Listar todos los usuarios activos |
| `GET` | `/?id={id}` | Obtener usuario por ID |
| `DELETE` | `/?id={id}` | Desactivar usuario (soft delete) |

### Vehículos — `/vehicle-rental/vehicle`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/` | Registrar vehículo |
| `GET` | `/all` | Listar vehículos activos |
| `GET` | `/onlyAvailable` | Listar vehículos disponibles para alquilar |
| `DELETE` | `/delete?licensePlate={plate}` | Desactivar vehículo |

### Métodos de pago — `/vehicle-rental/payment`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/` | Agregar método de pago a un usuario |
| `PUT` | `/` | Actualizar número de tarjeta |
| `DELETE` | `/?id={id}` | Desactivar método de pago |

### Rentas — `/vehicle-rental/rent`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/` | Crear renta (abre el alquiler) |
| `GET` | `/listAll` | Listar todas las rentas |
| `GET` | `/?id={id}` | Obtener renta por ID |
| `GET` | `/user?id={userId}` | Rentas de un usuario |
| `PUT` | `/close?id={id}` | Cerrar renta y simular cobro |

## Estados de una renta

```
OPEN  →  (cerrar)  →  UNDER_REVIEW  (pago con tarjeta simulado)
OPEN  →  (cerrar)  →  CLOSED        (pago en efectivo)
```

## Estructura del proyecto

```
src/main/java/com/alejo/rentadevehiculos/
├── api/
│   ├── controllers/          # REST controllers + error handlers
│   └── models/
│       ├── request/          # DTOs de entrada
│       └── response/         # DTOs de salida
├── config/                   # SecurityConfig, EncryptionConfig
├── domain/
│   ├── entities/             # Entidades JPA
│   └── repositories/        # Interfaces de repositorios Spring Data
├── infrastructure/
│   ├── abstractServices/     # Interfaces de servicios
│   └── services/             # Implementaciones de servicios
└── util/
    ├── encrypt/              # AES encryption utility
    └── exceptions/           # Excepciones de dominio
```
