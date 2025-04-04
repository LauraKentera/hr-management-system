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
    IF NOT EXISTS Position(
        position_id INT PRIMARY KEY AUTO_INCREMENT,
        parent_id INT,
        name VARCHAR(100) NOT NULL,
        short_name VARCHAR(50),
        education_level_id INT,
        benefits VARCHAR(255),
        requires_licensing BOOLEAN DEFAULT FALSE,
        is_active BOOLEAN DEFAULT TRUE,
        FOREIGN KEY (parent_id) REFERENCES Position(position_id),
        FOREIGN KEY (education_level_id) REFERENCES EducationLevel (education_level_id)
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
        FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id),
        FOREIGN KEY (region_id) REFERENCES Region (region_id)
    );

CREATE TABLE
    IF NOT EXISTS EmployeeBenefit (
        employee_benefit_id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        benefit_id INT NOT NULL,
        from_date DATE NOT NULL,
        to_date DATE,
        use_standard_amount BOOLEAN DEFAULT TRUE,
        amount DECIMAL(10, 2),
        coefficient DECIMAL(5, 2),
        description VARCHAR(255),
        is_active BOOLEAN DEFAULT TRUE,
        FOREIGN KEY (employee_id) REFERENCES Employee (id),
        FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id)
    );