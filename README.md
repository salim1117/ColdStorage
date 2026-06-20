# 🏢 Cold Storage Management System (CSMS)

> A full-stack Spring Boot application for managing cold storage rental facilities with dual interfaces: public storefront + admin dashboard + REST API

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**Key Features:** Role-based access control • RESTful API • File upload system • Real-time dashboard • Booking workflow • EAV metadata storage

---

## 🎯 Project Objective

Transform manual cold storage rental operations into a streamlined digital workflow supporting:
- Public clients browsing and booking storage units
- Administrative staff managing inventory, approvals, and customer inquiries
- Third-party integrations via secured REST API

---

## 🛠️ Technology Stack

### Backend
- **Java 21** — Latest LTS with modern language features
- **Spring Boot 3.2.5** — Application framework
- **Spring Security 6** — Authentication & authorization
- **Spring Data JPA** — ORM & repository pattern
- **Hibernate** — JPA implementation with automatic DDL
- **MySQL 8.0** — Relational database

### Frontend
- **Thymeleaf** — Server-side template engine
- **Bootstrap 4** — Responsive UI framework
- **jQuery** — DOM manipulation & AJAX
- **AdminLTE** — Admin dashboard theme

### Build & DevOps
- **Maven** — Dependency management & build automation
- **Embedded Tomcat** — Application server

---

## 🏗️ Architecture

**Layered Architecture (MVC + Service Layer):**

```
┌─────────────────────────────────────────────┐
│  Controllers (Thymeleaf + REST API)         │
├─────────────────────────────────────────────┤
│  Services (Business Logic)                  │
├─────────────────────────────────────────────┤
│  Repositories (Spring Data JPA)             │
├─────────────────────────────────────────────┤
│  Database (MySQL)                           │
└─────────────────────────────────────────────┘
```

**Dual Interface Design:**
- **Thymeleaf UI:** Server-rendered views for admin and public pages
- **REST API (`/api/**`):** JSON endpoints with session-based auth
- **Unified Security:** Same authentication context, different error handling

---

## 📦 Core Modules

| Module | Responsibility |
|--------|---------------|
| **config/** | Spring Security, Web MVC, password encoders |
| **controller/** | Admin UI + Public UI (Thymeleaf) |
| **controller/api/** | REST API endpoints (JSON) |
| **entity/** | JPA entities (User, Storage, Booking, Inquiry) |
| **repository/** | Spring Data repositories |
| **service/** | Business logic layer |

---

## 🗄️ Database Design

**6 Core Entities:**

```
users ─────────┐
               │
storage ───────┼──→ booking_list ──→ booking_detail (EAV)
               │
inquiry ───────┘

system_info (key-value config store)
```

**Key Design Patterns:**
- **EAV (Entity-Attribute-Value):** Flexible metadata storage for bookings
- **Composite Primary Key:** `booking_detail` uses (`booking_id`, `meta_field`)
- **Audit Timestamps:** Automatic `@CreationTimestamp` / `@UpdateTimestamp`

---

## ✨ Key Features

### Public Interface
- 🏠 Home page with active storage listings
- 📦 Storage detail pages with availability
- 📝 Booking request form with validation
- 💬 Contact/inquiry submission
- ℹ️ About & programs pages

### Admin Dashboard
- 📊 Real-time metrics (pending bookings, unread inquiries, active storages)
- 🔐 Role-based access control (Admin/Staff)
- ✅ Booking approval workflow (Pending → Approved → Rejected → Completed)
- 🗂️ Full CRUD for: Storages, Bookings, Users, Inquiries, System Settings
- 📤 File upload system (storage thumbnails, user avatars)
- ⚙️ System-wide configuration editor (site name, logo, contact info)

### REST API Layer
- 🔗 **Endpoints:** `/api/storages`, `/api/bookings`, `/api/users`, `/api/inquiries`, `/api/system-info`
- 🔒 **Auth:** Session-based (JSESSIONID cookie)
- 📋 **Verbs:** GET, POST, PUT, PATCH, DELETE
- 📄 **Response Formats:** JSON with proper HTTP status codes
- ❌ **Error Handling:** JSON 401 for unauthenticated API requests (no login redirect)

---

## 🚀 Installation & Setup

### Prerequisites
- JDK 21 or higher
- Maven 3.6+
- MySQL 8.0+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/salim1117/ColdStorage.git
   cd ColdStorage
   ```

2. **Create MySQL database**
   ```sql
   CREATE DATABASE csms_db;
   ```

3. **Configure application**
   
   Edit `csms-java/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/csms_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   server.port=8081
   ```

4. **Build and run**
   ```bash
   cd csms-java
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the application**
   - Public site: `http://localhost:8081/`
   - Admin login: `http://localhost:8081/admin/login`

---

## 🔐 API Authentication

**Authenticate for REST API (using Postman):**

1. POST `http://localhost:8081/admin/login` with form-data:
   - `username`: your_username
   - `password`: your_password

2. The `JSESSIONID` cookie is automatically sent on subsequent `/api/**` requests

**Example API Calls:**

```bash
# List all storages
GET http://localhost:8081/api/storages

# Get booking by ID
GET http://localhost:8081/api/bookings/1

# Create new booking
POST http://localhost:8081/api/bookings
Content-Type: application/json

{
  "clientName": "John Doe",
  "storageId": 1,
  "amount": 1500.00,
  "dateFrom": "2024-01-01",
  "dateTo": "2024-01-31",
  "status": 0
}

# Update booking status
PUT http://localhost:8081/api/bookings/1/status
Content-Type: application/json

{ "status": 1 }
```

---

## 🔒 Security Features

- **Role-Based Access:** `ROLE_ADMIN` (full access) vs. `ROLE_STAFF` (limited)
- **Hybrid Password Encoding:** BCrypt for new passwords + MD5 fallback for legacy migration
- **Custom Authentication Entry Point:** API routes return JSON 401, UI routes redirect
- **CSRF Protection:** Enabled for UI forms, disabled for REST API
- **Session Management:** HttpOnly cookies, configurable timeout

---

## 🚧 Challenges Solved

1. **Legacy Password Migration:**  
   Implemented `LegacyAwarePasswordEncoder` to support existing MD5-hashed passwords while encoding all new passwords with BCrypt, enabling seamless migration without forcing password resets.

2. **Dual Interface Authentication:**  
   Designed custom `AuthenticationEntryPoint` to return JSON 401 for `/api/**` requests while preserving login-redirect behavior for Thymeleaf UI routes—all under a single security filter chain.

3. **Flexible Metadata Storage:**  
   Adopted EAV pattern for `booking_detail` table allowing admin forms to capture arbitrary key-value pairs beyond core booking fields without schema changes.

4. **Transactional Integrity:**  
   Ensured booking updates with child details maintain consistency using `@Transactional` with manual cascade deletes before batch inserts.

5. **File Lifecycle Management:**  
   Built centralized `FileUploadService` with UUID-based naming, directory organization, and automatic cleanup on entity deletion to prevent orphaned files.

---

## 🔮 Future Enhancements

- [ ] **Caching Layer:** Redis for `SystemInfo` and frequent queries
- [ ] **Pagination:** Implement `Pageable` for large datasets
- [ ] **Real-Time Notifications:** WebSocket for live booking updates
- [ ] **File Validation:** Apache Tika integration for MIME type verification
- [ ] **Method Security:** `@PreAuthorize` annotations on service layer
- [ ] **API Documentation:** Swagger/OpenAPI integration
- [ ] **Unit Tests:** JUnit 5 + Mockito coverage (target 80%+)
- [ ] **Docker:** Containerization with docker-compose
- [ ] **CI/CD:** GitHub Actions pipeline
- [ ] **Observability:** Spring Boot Actuator + Prometheus metrics

---

## 📁 Project Structure

```
csms-java/
├── src/
│   ├── main/
│   │   ├── java/com/csms/
│   │   │   ├── config/              # Security & configuration
│   │   │   ├── controller/          # Thymeleaf controllers
│   │   │   │   └── api/             # REST API controllers
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── repository/          # Spring Data repositories
│   │   │   ├── service/             # Business logic
│   │   │   └── CsmsApplication.java # Main entry point
│   │   └── resources/
│   │       ├── templates/           # Thymeleaf views
│   │       ├── static/              # CSS, JS, images
│   │       └── application.properties
│   └── test/                        # Unit & integration tests
├── target/                          # Compiled output
└── pom.xml                          # Maven dependencies
```

---

## 📄 API Documentation

### Storages — `/api/storages`

| Method | Path | Description | Success | Error |
|--------|------|-------------|---------|-------|
| GET | `/api/storages` | List all storage units | 200 OK | — |
| GET | `/api/storages/{id}` | Get storage by ID | 200 OK | 404 |
| POST | `/api/storages` | Create new storage | 201 Created | — |
| PUT | `/api/storages/{id}` | Update storage | 200 OK | 404 |
| DELETE | `/api/storages/{id}` | Delete storage | 204 No Content | 404 |

### Bookings — `/api/bookings`

| Method | Path | Description | Success | Error |
|--------|------|-------------|---------|-------|
| GET | `/api/bookings` | List all bookings | 200 OK | — |
| GET | `/api/bookings/{id}` | Get booking by ID | 200 OK | 404 |
| GET | `/api/bookings/{id}/details` | Get booking detail rows | 200 OK | 404 |
| POST | `/api/bookings` | Create new booking | 201 Created | — |
| PUT | `/api/bookings/{id}` | Update booking core fields | 200 OK | 404 |
| PUT | `/api/bookings/{id}/status` | Update booking status only | 200 OK | 404, 400 |
| DELETE | `/api/bookings/{id}` | Delete booking + details | 204 No Content | 404 |

**Note:** `bookCode` is auto-generated on create. Status values: `0=Pending`, `1=Approved`, `2=Rejected`, `3=Completed`

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Saleem**  
📧 Email: [salim07238@gmail.com](mailto:salim07238@gmail.com)  
🔗 GitHub: [@salim1117](https://github.com/salim1117)

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- AdminLTE for the dashboard theme
- Bootstrap team for the UI components

---

⭐ **If you found this project helpful, please consider giving it a star!**
