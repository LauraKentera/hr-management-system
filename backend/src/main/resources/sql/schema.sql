CREATE DATABASE IF NOT EXISTS hrms;

USE hrms;

CREATE TABLE
    IF NOT EXISTS Role (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS User (
        id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(100) NOT NULL,
        role_id INT,
        employee_id INT,
        FOREIGN KEY (role_id) REFERENCES Role (id),
        FOREIGN KEY (employee_id) REFERENCES Employee (id)
    );

CREATE TABLE
    Employee (
        id INT PRIMARY KEY AUTO_INCREMENT,
        PIN VARCHAR(20) UNIQUE NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        first_name VARCHAR(50) NOT NULL,
        birth_date DATE NOT NULL,
        date_of_hire DATE NOT NULL,
        date_of_dismissal DATE,
        phone_number VARCHAR(20),
        email VARCHAR(100) UNIQUE,
        address VARCHAR(255),
        gender ENUM ('Male', 'Female', 'Other'),
        nationality_id INT,
        department_id INT,
        position_id INT,
        employment_status ENUM ('Active', 'On Leave', 'Terminated') DEFAULT 'Active',
        emergency_contact_name VARCHAR(100),
        emergency_contact_phone VARCHAR(20),
        marital_status ENUM ('Single', 'Married', 'Divorced', 'Widowed'),
        employment_type ENUM ('Full-Time', 'Part-Time', 'Contractor'),
        manager_id INT,
        tax_id VARCHAR(50) UNIQUE,
        bank_account_number VARCHAR(50),
        FOREIGN KEY (nationality_id) REFERENCES Nationality (nationality_id),
        FOREIGN KEY (department_id) REFERENCES Department (department_id),
        FOREIGN KEY (position_id) REFERENCES Position(position_id),
        FOREIGN KEY (manager_id) REFERENCES Employee (id)
    );

CREATE TABLE
    Department (
        department_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL UNIQUE,
        manager_id INT,
        FOREIGN KEY (manager_id) REFERENCES Employee (id)
    );

CREATE TABLE
    EmployeeAbsence (
        absence_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        absence_type_id INT NOT NULL,
        start_date DATE NOT NULL,
        end_date DATE NOT NULL,
        notes TEXT,
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (absence_type_id) REFERENCES AbsenceType (absence_type_id)
    );

CREATE TABLE
    EmployeeBenefit (
        employee_id INT,
        benefit_id INT,
        effective_date DATE NOT NULL,
        PRIMARY KEY (employee_id, benefit_id),
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id)
    );

CREATE TABLE
    EmployeeChange (
        change_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        change_date DATE NOT NULL,
        old_position_id INT,
        new_position_id INT NOT NULL,
        old_salary DECIMAL(10, 2),
        new_salary DECIMAL(10, 2) NOT NULL,
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (old_position_id) REFERENCES Position(position_id),
        FOREIGN KEY (new_position_id) REFERENCES Position(position_id)
    );

CREATE TABLE
    EmployeeBankAccount (
        employee_id INT PRIMARY KEY,
        bank_name VARCHAR(100) NOT NULL,
        account_number VARCHAR(50) NOT NULL,
        iban VARCHAR(34),
        FOREIGN KEY (employee_id) REFERENCES Employee (id)
    );
