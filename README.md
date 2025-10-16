# 🏦 API Cuenta Bancaria

Microservicio REST desarrollado con **Spring Boot 3** que expone un **CRUD completo** para gestionar entidades bancarias (`CuentaBancaria`).  
Además, el servicio incluye un endpoint adicional que **consume internamente su propio endpoint de consulta**, demostrando comunicación interna mediante **WebClient**.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.3.x**
  - Spring Web
  - Spring Data JPA
  - Spring Boot Starter Validation
- **H2 Database** (en memoria)
- **Lombok**
- **Jakarta Persistence**
- **WebClient** (para llamadas internas)
- **Maven**

---

## 📂 Estructura del proyecto

```
src/
 ├── main/
 │   ├── java/com/example/consulta_bd/
 │   │   ├── controllers/        → Controladores REST
 │   │   ├── dto/                → Objetos de transferencia (Request/Response)
 │   │   ├── exception/          → Manejo global de errores y excepciones personalizadas
 │   │   ├── mapper/             → Conversión entre entidades y DTOs
 │   │   ├── models/             → Entidades JPA
 │   │   ├── repository/         → Interfaces de persistencia
 │   │   ├── services/           → Interfaces de lógica de negocio
 │   │   ├── services/imp/       → Implementaciones de servicios
 │   │   ├── config/             → Configuración de beans y carga inicial de datos
 │   │   └── ConsultaBancosApplication.java → Clase principal
 │   └── resources/
 │       ├── application.yml     → Configuración del microservicio
 │       └── data.sql (opcional) → Datos iniciales
 └── test/java/...               → Tests unitarios
```

---

## 🧠 Entidad principal

### `CuentaBancaria`
| Campo          | Tipo     | Descripción                        |
|----------------|----------|------------------------------------|
| `id`           | Long     | Identificador único autogenerado   |
| `numeroCuenta` | String   | Número único de cuenta bancaria    |
| `saldo`        | Double   | Saldo disponible                   |
| `titular`      | String   | Nombre del titular de la cuenta    |

---

## ⚙️ Endpoints principales

| Método | Endpoint              | Descripción |
|--------|------------------------|-------------|
| `GET`  | `/api/cuentas`         | Listar todas las cuentas |
| `GET`  | `/api/cuentas/{id}`    | Consultar cuenta por ID |
| `POST` | `/api/cuentas`         | Crear una nueva cuenta *(valida duplicados)* |
| `PUT`  | `/api/cuentas/{id}`    | Actualizar una cuenta existente |
| `DELETE` | `/api/cuentas/{id}`  | Eliminar una cuenta |
| `GET`  | `/api/cuentas/self-call` | Llamada interna al endpoint `/api/cuentas` usando WebClient |

---

## 💥 Manejo de excepciones

- `DuplicateException`: al intentar crear una cuenta con número duplicado.  
- `NotFoundException`: cuando no se encuentra una cuenta.  
- `GlobalExceptionHandler`: captura y responde con un JSON uniforme.

Ejemplo de respuesta de error:
```json
{
  "timestamp": "2025-10-16T15:30:25.671+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Ya existe una cuenta con ese número",
  "path": "/api/cuentas"
}
```

---

## 🧪 Tests

- Se incluye test básico de contexto (`ConsultaBancosApplicationTests`)
- Posible extensión: MockMvc para pruebas de endpoints.

---

## 💾 Base de datos (H2)

La base H2 corre en memoria.  
Podés acceder a la consola en:

```
http://localhost:8080/h2-console
```

Configuración:
```
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vacío)
```

---

## ▶️ Cómo ejecutar el proyecto

```bash
# Clonar el repositorio
git clone https://github.com/mtssto/api-cuenta-bancaria.git

# Ingresar al proyecto
cd api-cuenta-bancaria

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

Luego acceder a:  
👉 `http://localhost:8080/api/cuentas`

---

## 🧩 Endpoint interno (auto-consumo)

El endpoint `/api/cuentas/self-call` demuestra cómo el microservicio puede consumir su propio endpoint REST de consulta utilizando `WebClient`.

```java
@GetMapping("/self-call")
public ResponseEntity<?> selfCall() {
    return webClient.get()
        .uri("/api/cuentas")
        .retrieve()
        .bodyToMono(Object.class)
        .map(ResponseEntity::ok)
        .block();
}
```

---

## 🧱 Arquitectura y diseño

- Arquitectura en **capas** (Controller → Service → Repository).
- Uso de **DTOs y Mapper** para desacoplar dominio de la API pública.
- **Inyección de dependencias** mediante Spring.
- **Manejo centralizado de excepciones.**
- **Validación con Jakarta Validation (`@NotNull`, `@Size`, etc.)**.
- **WebClient** para comunicación interna.

---

## ✨ Autor

**Matías Salto**  
📍 Buenos Aires, Argentina  
💻 [GitHub: mtssto](https://github.com/mtssto)
