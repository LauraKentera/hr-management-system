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

INSERT INTO
    Department (name, manager_id)
VALUES
    ('IT', NULL),
    ('HR', NULL);

INSERT INTO
    Employee (
        PIN,
        last_name,
        first_name,
        birth_date,
        date_of_hire,
        email,
        gender,
        nationality_id,
        department_id,
        position_id,
        employment_status,
        employment_type,
        tax_id
    )
VALUES
    (
        'EMP001',
        'Petrovic',
        'Marko',
        '1990-05-15',
        '2020-01-01',
        'marko@sharp.com',
        'Male',
        1,
        1,
        1,
        'Active',
        'Full-Time',
        'TAX-001'
    ),
    (
        'EMP002',
        'Müller',
        'Anna',
        '1985-08-22',
        '2022-06-01',
        'anna@sharp.com',
        'Female',
        2,
        2,
        2,
        'Active',
        'Full-Time',
        'TAX-002'
    );

UPDATE Department
SET
    manager_id = 1
WHERE
    department_id = 1;

UPDATE Department
SET
    manager_id = 2
WHERE
    department_id = 2;

INSERT INTO
    EmployeeBenefit (employee_id, benefit_id, effective_date)
VALUES
    (1, 1, '2023-01-01'),
    (2, 2, '2023-01-01');

INSERT INTO
    EmployeeAbsence (
        employee_id,
        absence_type_id,
        start_date,
        end_date
    )
VALUES
    (1, 1, '2023-07-01', '2023-07-14');