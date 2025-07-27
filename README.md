# 🔗 URL Shortener with JWT and OAuth2

A secure, full-featured URL shortener built using Spring Boot.  
Supports login via **OAuth2 (Google, GitHub)** and **JWT-based API authentication**.

---

## 🚀 Features

- ✅ Shorten long URLs into clean base62 tokens
- 🔐 Login using Google/GitHub (OAuth2)
- 🔑 JWT token generation after OAuth2 login
- 🔒 Stateless, token-based authentication
- 📡 REST APIs for shortening and redirecting
- 🧠 Clean architecture: Controller → Service → Repository

---

## 🧱 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security** (JWT + OAuth2)
- **Spring Data JPA**
- **Postgres
- **JWT (io.jsonwebtoken)**
  
---

## 🏁 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven 3.6+
- OAuth2 Client ID/Secret from Google or GitHub

---

### ⚙️ Configuration

Update `application.properties` with:

   

    # JWT
    jwt.secret=your_jwt_secret_key

    # OAuth2
    spring.security.oauth2.client.registration.google.client-id=your-client-id
    spring.security.oauth2.client.registration.google.client-secret=your-client-secret

    #  DB
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName= db Driver
    spring.datasource.username= username
    spring.datasource.password= passsword
    spring.jpa.hibernate.ddl-auto=update



----

## 📘 API Documentation (Swagger UI)

The project uses **Swagger UI** for API documentation.

### 🔗 Accessing Swagger UI
Run the Spring Boot application and navigate to: http://localhost:8080/swagger-ui.html

----

## 🔐 Security Flow

1. User logs in via **OAuth2** (e.g. Google)
2. `OAuth2SuccessHandler` generates a **JWT**
3. **JWT** is returned in the login response
4. All **protected APIs** require the JWT for access (sent in the `Authorization` header)



## 🧪 API Endpoints

### 🔐 Authentication

| Method | Endpoint                             | Description                |
|--------|--------------------------------------|----------------------------|
| GET    | `/oauth2/authorization/google`       | OAuth2 login redirect      |
| POST   | `/register`                          | Register a new user        |
| POST   | `/login`                             | Login with email/password  |

> After OAuth2 login, a **JWT token** is returned in the response.

---



### 🔗 URL Operations

| Method | Endpoint           | Description                       |
|--------|--------------------|-----------------------------------|
| POST   | `/api/shorten`     | Create short URL *(Requires JWT)* |
| GET    | `/api/{shortCode}` | Redirect to original URL          |

> Send the JWT token in the `Authorization` header:

    
        Authorization: Bearer <your_token>




🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first.
