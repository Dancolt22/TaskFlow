# TaskFlow Backend - Java & Spring Boot (SQL Database)

This backend replaces the Node/Express backend with a **Java & Spring Boot** architecture utilizing a **SQL Relational Database**, aligned with the **Software Engineering Diploma (Year 1 Term 2)** curriculum standards.

---

## 📋 Diploma Curriculum Alignment (Year 1 Term 2)

- **Language / Framework:** Java & Spring Boot Fundamentals
- **Architecture:** Layered RESTful API (`Controller` -> `Service` -> `Repository` -> `Entity`)
- **Database:** SQL Relational Database Management Systems (`SQL & NoSQL` module, Course 9)
  - Default: **H2 Embedded SQL Database** (zero setup required for immediate local running and automated testing).
  - Production-ready: **PostgreSQL** / **MySQL** (configured in `application.properties`).
- **Security:** Spring Security Crypto (`BCryptPasswordEncoder` with 10 salt rounds) for password hashing.
- **Testing:** Unit Testing & MockMvc Integration Testing (Course 11).

---

## 🛠️ Requirements

- **Java JDK 17** or newer
- **Apache Maven 3.8+** (or run with `./mvnw` if configured)

---

## 🚀 How to Run the Server

From the `server` directory:

```bash
# 1. Compile and run the Spring Boot server
mvn spring-boot:run
```

The server will start on port `5000` (`http://localhost:5000`).

### Inspecting the SQL Database
With the server running, you can access the interactive H2 SQL Web Console:
- **URL:** http://localhost:5000/h2-console
- **JDBC URL:** `jdbc:h2:mem:taskflowdb`
- **User Name:** `sa`
- **Password:** *(leave blank)*

---

## 🧪 How to Run Tests

To execute all unit, integration, and end-to-end tests:

```bash
mvn test
```

Test suites included:
- `TaskControllerTest.java`: Validates HTTP contracts, status codes (200, 201, 400), and JSON payloads.
- `SignupControllerTest.java`: Verifies user registration, password length constraints, and duplicate prevention.
- `TaskServiceTest.java`: Unit tests business logic with Mockito mocks.
- `TaskFlowIntegrationTest.java`: Full Spring Boot integration test with live SQL database persistence.

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/tasks` | Returns all tasks ordered newest first (with both `id` and `_id` fields for React). |
| `POST` | `/api/tasks` | Validates and creates a new task. |
| `POST` | `/api/signup` | Registers a user, hashes password with BCrypt, and returns safe user data. |

---

## 🔄 Switching to PostgreSQL or MySQL

Open `src/main/resources/application.properties` and uncomment the PostgreSQL or MySQL section:

```properties
# Example for PostgreSQL:
spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```
