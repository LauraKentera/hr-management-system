CREATE DATABASE IF NOT EXISTS hrms;

USE hrms;

-- ==============================
-- REFERENCE TABLES
-- ==============================
CREATE TABLE
    IF NOT EXISTS Role (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS Nationality (
        nationality_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        user_id INT,
        modification_date DATETIME,
        is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE
    IF NOT EXISTS EducationLevel (
        education_level_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        user_id INT,
        modification_date DATETIME,
        is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE
    IF NOT EXISTS AbsenceType (
        absence_type_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        code VARCHAR(20),
        description VARCHAR(255),
        is_paid BOOLEAN DEFAULT FALSE,
        requires_approval BOOLEAN DEFAULT TRUE,
        is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE
    IF NOT EXISTS DisabilityCategory (
        disability_category_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        description VARCHAR(255),
        legal_code VARCHAR(20),
        is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE
    IF NOT EXISTS Benefit (
        benefit_id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,
        description VARCHAR(255),
        is_taxable BOOLEAN DEFAULT TRUE,
        is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS Department (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    manager_id INT
);

DESCRIBE Department;


CREATE TABLE
    IF NOT EXISTS Position (
        position_id INT PRIMARY KEY AUTO_INCREMENT,
        parent_id INT,
        name VARCHAR(100) NOT NULL,
        short_name VARCHAR(50),
        education_level_id INT,
        benefits VARCHAR(255),
        requires_licensing BOOLEAN DEFAULT FALSE,
        is_active BOOLEAN DEFAULT TRUE,
        FOREIGN KEY (parent_id) REFERENCES Position (position_id),
        FOREIGN KEY (education_level_id) REFERENCES EducationLevel (education_level_id)
    );

-- ==============================
-- CORE TABLES
-- ==============================
CREATE TABLE
    IF NOT EXISTS Employee (
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
        FOREIGN KEY (position_id) REFERENCES Position (position_id),
        FOREIGN KEY (manager_id) REFERENCES Employee (id)
    );
ALTER TABLE Employee ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

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

ALTER TABLE User ADD CONSTRAINT unique_employee_id UNIQUE (employee_id);

-- ==============================
-- EMPLOYEE-RELATED DATA
-- ==============================
CREATE TABLE
    IF NOT EXISTS BenefitItem (
        benefit_item_id INT PRIMARY KEY AUTO_INCREMENT,
        benefit_id INT NOT NULL,
        region_id INT,
        from_date DATE NOT NULL,
        to_date DATE,
        allow_coefficient BOOLEAN DEFAULT FALSE,
        use_standard_amount BOOLEAN DEFAULT TRUE,
        amount DECIMAL(10, 2),
        coefficient DECIMAL(5, 2),
        FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id)
    );

CREATE TABLE
    IF NOT EXISTS EmployeeBenefit (
        employee_id INT,
        benefit_id INT,
        effective_date DATE NOT NULL,
        PRIMARY KEY (employee_id, benefit_id),
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id)
    );

CREATE TABLE
    IF NOT EXISTS EmployeeDisability (
        employee_disability_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        disability_category_id INT NOT NULL,
        official_code VARCHAR(20),
        from_date DATE NOT NULL,
        to_date DATE,
        description VARCHAR(255),
        percentage INT CHECK (percentage BETWEEN 0 AND 100),
        is_active BOOLEAN DEFAULT TRUE,
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (disability_category_id) REFERENCES DisabilityCategory (disability_category_id)
    );

CREATE TABLE
    IF NOT EXISTS EmployeeAbsence (
        absence_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        absence_type_id INT NOT NULL,
        start_date DATE NOT NULL,
        end_date DATE NOT NULL,
        notes TEXT,
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (absence_type_id) REFERENCES AbsenceType (absence_type_id)
    );

ALTER TABLE EmployeeAbsence
    ADD COLUMN status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    ADD COLUMN approved_by INT;


CREATE TABLE
    IF NOT EXISTS EmployeeChange (
        change_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        change_date DATE NOT NULL,
        old_position_id INT,
        new_position_id INT NOT NULL,
        old_salary DECIMAL(10, 2),
        new_salary DECIMAL(10, 2) NOT NULL,
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (old_position_id) REFERENCES Position (position_id),
        FOREIGN KEY (new_position_id) REFERENCES Position (position_id)
    );

CREATE TABLE
    IF NOT EXISTS EmployeeBankAccount (
        employee_id INT PRIMARY KEY,
        bank_name VARCHAR(100) NOT NULL,
        account_number VARCHAR(50) NOT NULL,
        iban VARCHAR(34),
        FOREIGN KEY (employee_id) REFERENCES Employee (id)
    );

CREATE TABLE
    IF NOT EXISTS Payroll (
        payroll_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        period_start DATE NOT NULL,
        period_end DATE NOT NULL,
        base_salary DECIMAL(10, 2) NOT NULL,
        bonus DECIMAL(10, 2) DEFAULT 0,
        deductions DECIMAL(10, 2) DEFAULT 0,
        net_pay DECIMAL(10, 2) NOT NULL,
        payment_date DATE,
        status ENUM ('Pending', 'Processed', 'Paid') DEFAULT 'Pending',
        FOREIGN KEY (employee_id) REFERENCES Employee (id)
    );

CREATE TABLE
    IF NOT EXISTS EmploymentContract (
        contract_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        start_date DATE NOT NULL,
        end_date DATE,
        position_id INT NOT NULL,
        salary DECIMAL(10, 2) NOT NULL,
        contract_type ENUM ('Permanent', 'Temporary', 'Internship') NOT NULL,
        signed_date DATE,
        document_path VARCHAR(255),
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (position_id) REFERENCES Position (position_id)
    );

CREATE TABLE
    IF NOT EXISTS ContractAnnex (
        annex_id INT PRIMARY KEY AUTO_INCREMENT,
        contract_id INT NOT NULL,
        change_date DATE NOT NULL,
        description VARCHAR(255),
        document_path VARCHAR(255),
        FOREIGN KEY (contract_id) REFERENCES EmploymentContract (contract_id)
    );

CREATE TABLE
    IF NOT EXISTS AuditLog (
        log_id INT PRIMARY KEY AUTO_INCREMENT,
        entity_type VARCHAR(50),
        entity_id INT,
        action ENUM ('CREATE', 'UPDATE', 'DELETE'),
        performed_by INT,
        timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
        old_value TEXT,
        new_value TEXT
    );

ALTER TABLE Employee ADD COLUMN user_id INT,
                     ADD CONSTRAINT fk_employee_user FOREIGN KEY (user_id) REFERENCES User(id);

ALTER TABLE Employee ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;
ALTER TABLE Department ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

