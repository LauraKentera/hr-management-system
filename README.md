**Human Resource Management System - Project Report**

**Project Title:** Human Resource Management System (HRMS)

**Course:** ISTE-330 - Database Connectivity and Access
**Instructor:** dr. sc. Branko Mihaljević
**Team Members:** Laura Kentera, Karmen Penga, Lucija Nesnidal, Jan Stanko

---

### 1. Introduction
The Human Resource Management System (HRMS) is a Java-based full-stack project designed to support the core administrative and HR operations within an organization. This system enables streamlined employee management, benefits tracking, departmental organization, and secure authentication.

### 2. Objectives
- Develop a modular HRMS system using Java, MySQL, and Spring Boot.
- Provide core functionalities such as employee records, roles, positions, and benefits.
- Implement user authentication and role-based access control.
- Use DAO and service patterns to separate logic and promote code maintainability.

### 3. Technologies Used
- **Backend:** Java, Spring Boot, JDBC
- **Database:** MySQL
- **Authentication:** JWT (JSON Web Tokens), BCrypt hashing
- **Tools:** IntelliJ IDEA, MySQL Workbench, Postman (for API testing)

### 4. System Design

#### 4.1 Database Schema
The database contains over 15 well-normalized tables, including:
- `Employee`, `Position`, `Department`
- `User`, `Role` (for authentication)
- `Benefit`, `BenefitItem`, `EmployeeBenefit`
- `DisabilityCategory`, `EmployeeDisability`
- `EmployeeAbsence`, `AbsenceType`
- `EmployeeBankAccount`, `EmployeeChange`
- `Nationality`, `EducationLevel`

All tables were manually created in `schema.sql`, following foreign key dependency order.

#### 4.2 Entity Relationship
Each `Employee` can have:
- A department, position, and nationality
- Benefits assigned through `EmployeeBenefit`
- Disability entries via `EmployeeDisability`
- Bank account and absence records

#### 4.3 Authentication and Authorization
Authentication is handled via a `User` table linked to a `Role`. Passwords are hashed using BCrypt. JWT tokens are issued upon login and used to control access to protected endpoints.

### 5. Features Implemented
- CRUD operations for all entities
- Secure login and token-based access
- Role-restricted access to API endpoints
- Sample data population with realistic HR values

### 6. Project Structure
- `model/` — Entity classes
- `repository/` — DAO classes using JDBC
- `service/` — Business logic and service layer
- `controller/` — RESTful API endpoints
- `util/` — Utility classes (e.g., `PasswordUtil`, `JwtUtil`)
- `resources/` — Contains `schema.sql`, `data.sql`, and `application.properties`

### 7. Testing & Validation
- SQL scripts were manually tested in MySQL Workbench
- API endpoints tested using Postman
- Authentication flow validated using real JWT tokens

### 8. Challenges & Solutions
- Managing foreign key dependencies in the correct table creation order
- Handling password hashing and validation logic securely
- Designing realistic sample data for development and demo purposes

### 9. Conclusion
This HRMS project provides a complete and scalable base for employee management in an organizational setting. It adheres to modern software engineering principles and is extensible for future features such as payroll, recruitment, or performance reviews.

### 10. Future Improvements
- Add user interface using React or Thymeleaf
- Implement role management UI for admins
- Extend to include payroll and contract tracking modules
- Integrate employee document uploads (PDF, images)

### Appendix
- `schema.sql`: contains ordered table creation
- `data.sql`: inserts test data into all main tables
- API Documentation: available via Postman collection or Swagger (optional)

