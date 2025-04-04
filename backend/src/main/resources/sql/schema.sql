CREATE DATABASE IF NOT EXISTS hrms;
USE hrms;

-- Basic reference tables
CREATE TABLE IF NOT EXISTS Role
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Nationality
(
    nationality_id    INT PRIMARY KEY AUTO_INCREMENT,
    name              VARCHAR(100) NOT NULL,
    user_id           INT,
    modification_date DATETIME,
    is_active         BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS EducationLevel
(
    education_level_id INT PRIMARY KEY AUTO_INCREMENT,
    name               VARCHAR(100) NOT NULL,
    user_id            INT,
    modification_date  DATETIME,
    is_active          BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS AbsenceType
(
    absence_type_id   INT PRIMARY KEY AUTO_INCREMENT,
    name              VARCHAR(100) NOT NULL,
    code              VARCHAR(20),
    description       VARCHAR(255),
    is_paid           BOOLEAN DEFAULT FALSE,
    requires_approval BOOLEAN DEFAULT TRUE,
    is_active         BOOLEAN DEFAULT TRUE
);

-- A simple Region table for BenefitItem
CREATE TABLE IF NOT EXISTS Region
(
    region_id INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS DisabilityCategory
(
    disability_category_id INT PRIMARY KEY AUTO_INCREMENT,
    name                   VARCHAR(100) NOT NULL,
    description            VARCHAR(255),
    legal_code             VARCHAR(20),
    is_active              BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS Benefit
(
    benefit_id  INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    is_taxable  BOOLEAN DEFAULT TRUE,
    is_active   BOOLEAN DEFAULT TRUE
);

-- Additional HR tables from previous code

CREATE TABLE IF NOT EXISTS ContractType
(
    contract_type_id INT PRIMARY KEY AUTO_INCREMENT,
    type_name        VARCHAR(50) NOT NULL,
    description      TEXT,
    duration         INT, -- duration in months
    user_id          INT,
    entry_date       DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Bank
(
    bank_id        INT PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(100) NOT NULL,
    address        VARCHAR(200),
    city           VARCHAR(100),
    phone          VARCHAR(20),
    fax            VARCHAR(20),
    website        VARCHAR(100),
    email          VARCHAR(100),
    contact_person VARCHAR(100),
    note           TEXT,
    our_bank       BOOLEAN DEFAULT FALSE,
    ebanking       BOOLEAN DEFAULT FALSE,
    bcpld          BOOLEAN DEFAULT FALSE,
    user_id        INT
);

CREATE TABLE IF NOT EXISTS Evaluation
(
    evaluation_id INT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL,
    user_id       INT,
    entry_date    DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS EmploymentTermination
(
    termination_type_id INT PRIMARY KEY AUTO_INCREMENT,
    name                VARCHAR(100) NOT NULL,
    user_id             INT,
    entry_date          DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tables with dependencies
CREATE TABLE IF NOT EXISTS Position
(
    position_id        INT PRIMARY KEY AUTO_INCREMENT,
    parent_id          INT,
    name               VARCHAR(100) NOT NULL,
    short_name         VARCHAR(50),
    education_level_id INT,
    benefits           VARCHAR(255),
    requires_licensing BOOLEAN DEFAULT FALSE,
    is_active          BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_position_parent FOREIGN KEY (parent_id) REFERENCES Position (position_id),
    CONSTRAINT fk_position_education FOREIGN KEY (education_level_id) REFERENCES EducationLevel (education_level_id)
);


-- Employee table
-- (Note: The foreign key on department_id is omitted to avoid a circular dependency with Department.)
CREATE TABLE IF NOT EXISTS Employee
(
    id                      INT PRIMARY KEY AUTO_INCREMENT,
    PIN                     VARCHAR(20) UNIQUE NOT NULL,
    last_name               VARCHAR(50)        NOT NULL,
    first_name              VARCHAR(50)        NOT NULL,
    birth_date              DATE               NOT NULL,
    date_of_hire            DATE               NOT NULL,
    date_of_dismissal       DATE,
    phone_number            VARCHAR(20),
    email                   VARCHAR(100) UNIQUE,
    address                 VARCHAR(255),
    gender                  ENUM ('Male', 'Female', 'Other'),
    nationality_id          INT,
    department_id           INT,
    position_id             INT,
    employment_status       ENUM ('Active', 'On Leave', 'Terminated') DEFAULT 'Active',
    emergency_contact_name  VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    marital_status          ENUM ('Single', 'Married', 'Divorced', 'Widowed'),
    employment_type         ENUM ('Full-Time', 'Part-Time', 'Contractor'),
    manager_id              INT,
    tax_id                  VARCHAR(50) UNIQUE,
    bank_account_number     VARCHAR(50),
    FOREIGN KEY (nationality_id) REFERENCES Nationality (nationality_id),
    FOREIGN KEY (position_id) REFERENCES Position (position_id),
    FOREIGN KEY (manager_id) REFERENCES Employee (id)
);

-- Department table – manager_id references an Employee
CREATE TABLE IF NOT EXISTS Department
(
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(50) NOT NULL UNIQUE,
    manager_id    INT,
    FOREIGN KEY (manager_id) REFERENCES Employee (id)
);

CREATE TABLE IF NOT EXISTS EmployeeDisability
(
    employee_disability_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id            INT  NOT NULL,
    disability_category_id INT  NOT NULL,
    official_code          VARCHAR(20),
    from_date              DATE NOT NULL,
    to_date                DATE,
    description            VARCHAR(255),
    percentage             INT CHECK (percentage BETWEEN 0 AND 100),
    is_active              BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (employee_id) REFERENCES Employee (id),
    FOREIGN KEY (disability_category_id) REFERENCES DisabilityCategory (disability_category_id)
);

CREATE TABLE IF NOT EXISTS BenefitItem
(
    benefit_item_id     INT PRIMARY KEY AUTO_INCREMENT,
    benefit_id          INT  NOT NULL,
    region_id           INT,
    from_date           DATE NOT NULL,
    to_date             DATE,
    allow_coefficient   BOOLEAN DEFAULT FALSE,
    use_standard_amount BOOLEAN DEFAULT TRUE,
    amount              DECIMAL(10, 2),
    coefficient         DECIMAL(5, 2),
    FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id),
    FOREIGN KEY (region_id) REFERENCES Region (region_id)
);

-- Use composite key version for EmployeeBenefit as per your new code
CREATE TABLE IF NOT EXISTS EmployeeBenefit
(
    employee_id    INT,
    benefit_id     INT,
    effective_date DATE NOT NULL,
    PRIMARY KEY (employee_id, benefit_id),
    FOREIGN KEY (employee_id) REFERENCES Employee (id),
    FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id)
);

CREATE TABLE IF NOT EXISTS EmployeeChange
(
    change_id       INT PRIMARY KEY AUTO_INCREMENT,
    employee_id     INT            NOT NULL,
    change_date     DATE           NOT NULL,
    old_position_id INT,
    new_position_id INT            NOT NULL,
    old_salary      DECIMAL(10, 2),
    new_salary      DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES Employee (id),
    FOREIGN KEY (old_position_id) REFERENCES Position (position_id),
    FOREIGN KEY (new_position_id) REFERENCES Position (position_id)
);

CREATE TABLE IF NOT EXISTS EmployeeBankAccount
(
    employee_id    INT PRIMARY KEY,
    bank_name      VARCHAR(100) NOT NULL,
    account_number VARCHAR(50)  NOT NULL,
    iban           VARCHAR(34),
    FOREIGN KEY (employee_id) REFERENCES Employee (id)
);

CREATE TABLE IF NOT EXISTS EmployeeAbsence
(
    absence_id      INT PRIMARY KEY AUTO_INCREMENT,
    employee_id     INT  NOT NULL,
    absence_type_id INT  NOT NULL,
    start_date      DATE NOT NULL,
    end_date        DATE NOT NULL,
    notes           TEXT,
    FOREIGN KEY (employee_id) REFERENCES Employee (id),
    FOREIGN KEY (absence_type_id) REFERENCES AbsenceType (absence_type_id)
);

CREATE TABLE IF NOT EXISTS EmployeeEvaluation
(
    employee_evaluation_id INT PRIMARY KEY AUTO_INCREMENT,
    evaluation_id          INT,
    evaluation_date        DATE,
    comment                TEXT,
    score                  DECIMAL(5, 2),
    user_id                INT,
    entry_date             DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evaluation_id) REFERENCES Evaluation (evaluation_id)
);
