# -- hard reset
# SET FOREIGN_KEY_CHECKS = 0;
#
# DELETE FROM User;
# DELETE FROM Role;
# DELETE FROM EmployeeBenefit;
# DELETE FROM BenefitItem;
# DELETE FROM Benefit;
# DELETE FROM EmployeeDisability;
# DELETE FROM DisabilityCategory;
# DELETE FROM EmployeeAbsence;
# DELETE FROM AbsenceType;
# DELETE FROM EmployeeChange;
# DELETE FROM EmployeeBankAccount;
# DELETE FROM Employee;
# DELETE FROM Department;
# DELETE FROM Position;
# DELETE FROM EducationLevel;
# DELETE FROM Nationality;
#
# SET FOREIGN_KEY_CHECKS = 1;
-- Then rerun your full schema.sql and data.sql

-- Set database context
USE hrms;

-- ==============================
-- ROLES & USERS
-- ==============================

-- User roles
INSERT INTO Role (name)
VALUES ('Admin'),
       ('HR'),
       ('Employee');

-- Users with hashed passwords and assigned roles
INSERT INTO User (username, password, role_id)
VALUES ('admin_user', '$2a$10$bBuT1K9bliBB.H35cYallOAo3GFk4BfLG4iYM021evOBpRvWAjUCe', 1),
       ('hr_user', '$2a$10$D.C6RCTrgOLITP34Z9.Hi.1RVi3uPrioaaMPWN6r9qSlXmnlLvXme', 2),
       ('employee_user', '$2a$10$4miM5cIJRVtEIonxpx3aO.K4TFkojoYTVJXaOyXr4JUmgBP0IpIr.', 3);

-- ==============================
-- REFERENCE DATA
-- ==============================

-- Nationalities
INSERT INTO Nationality (name, is_active)
VALUES ('Croatian', TRUE),
       ('Serbian', TRUE),
       ('Bosnian', TRUE),
       ('Slovenian', TRUE),
       ('Montenegrin', TRUE);

-- Education levels
INSERT INTO EducationLevel (name, is_active)
VALUES ('Primary School', TRUE),
       ('High School', TRUE),
       ('Vocational School', TRUE),
       ('Bachelor Degree', TRUE),
       ('Master Degree', TRUE),
       ('PhD', TRUE);

-- Absence types
INSERT INTO AbsenceType (name, code, is_paid, requires_approval)
VALUES ('Annual Leave', 'AL', TRUE, TRUE),
       ('Sick Leave', 'SL', TRUE, FALSE),
       ('Maternity Leave', 'ML', TRUE, TRUE),
       ('Paternity Leave', 'PL', TRUE, TRUE),
       ('Unpaid Leave', 'UL', FALSE, TRUE);

-- Disability categories
INSERT INTO DisabilityCategory (name, legal_code)
VALUES ('Category I - Mild', 'M86-1'),
       ('Category II - Moderate', 'M86-2'),
       ('Category III - Severe', 'M86-3');

-- ==============================
-- ORGANIZATIONAL STRUCTURE
-- ==============================

-- Departments (will assign managers later)
INSERT INTO Department (name, manager_id)
VALUES ('IT', NULL),
       ('HR', NULL);

-- Positions (managerial hierarchy)
INSERT INTO Position (parent_id, name, short_name, education_level_id, requires_licensing)
VALUES (NULL, 'Chief Executive Officer', 'CEO', 5, TRUE),
       (1, 'Chief Technology Officer', 'CTO', 5, TRUE),
       (1, 'Chief Financial Officer', 'CFO', 5, TRUE),
       (2, 'Software Development Manager', 'SDM', 4, FALSE),
       (3, 'Senior Accountant', 'SACC', 4, TRUE),
       (4, 'Frontend Developer', 'FE', 3, FALSE);

-- ==============================
-- EMPLOYEE CORE
-- ==============================

-- Employees (2 sample employees)
INSERT INTO Employee (PIN, last_name, first_name, birth_date, date_of_hire, email, gender,
                      nationality_id, department_id, position_id, employment_status, employment_type, tax_id)
VALUES ('EMP001', 'Petrovic', 'Marko', '1990-05-15', '2020-01-01', 'marko@sharp.com', 'Male',
        1, 1, 1, 'Active', 'Full-Time', 'TAX-001'),
       ('EMP002', 'Müller', 'Anna', '1985-08-22', '2022-06-01', 'anna@sharp.com', 'Female',
        2, 2, 2, 'Active', 'Full-Time', 'TAX-002');

-- Assign managers to departments
UPDATE Department
SET manager_id = 1
WHERE department_id = 1;
UPDATE Department
SET manager_id = 2
WHERE department_id = 2;

-- ==============================
-- BENEFITS
-- ==============================

-- Benefit types
INSERT INTO Benefit (name, is_taxable)
VALUES ('Meal Allowance', FALSE),
       ('Transportation Allowance', FALSE),
       ('Child Allowance', TRUE),
       ('Housing Allowance', TRUE),
       ('Education Reimbursement', TRUE);

-- Benefit items (global amount config, not tied to employee yet)
INSERT INTO BenefitItem (benefit_id, from_date, amount)
VALUES (1, '2023-01-01', 50.00),
       (2, '2023-01-01', 30.00),
       (3, '2023-01-01', 100.00),
       (4, '2023-01-01', 200.00),
       (5, '2023-01-01', 500.00);

-- Employee-specific benefit assignments (correct table: EmployeeBenefit)
CREATE TABLE IF NOT EXISTS EmployeeBenefit
(
    employee_id    INT,
    benefit_id     INT,
    effective_date DATE NOT NULL,
    PRIMARY KEY (employee_id, benefit_id),
    FOREIGN KEY (employee_id) REFERENCES Employee (id),
    FOREIGN KEY (benefit_id) REFERENCES Benefit (benefit_id)
);


-- ==============================
-- DISABILITIES
-- ==============================

-- Employee disability records
INSERT INTO EmployeeDisability (employee_id, disability_category_id, official_code, from_date, percentage)
VALUES (1, 1, 'M86-2023-001', '2023-03-15', 20),
       (2, 2, 'M86-2023-002', '2023-05-10', 40);

-- ==============================
-- ABSENCES
-- ==============================

-- Leave / absence record
INSERT INTO EmployeeAbsence (employee_id, absence_type_id, start_date, end_date)
VALUES (1, 1, '2023-07-01', '2023-07-14');

-- ==============================
-- BANK ACCOUNT
-- ==============================

INSERT INTO EmployeeBankAccount (employee_id, bank_name, account_number, iban)
VALUES
    (1, 'NLB Banka', '123456789', 'ME25505000012345678951'),
    (2, 'Erste Bank', '987654321', 'ME25505000098765432198');

-- ==============================
-- EMPLOYEE BENEFIT
-- ==============================

INSERT INTO EmployeeBenefit (employee_id, benefit_id, effective_date)
VALUES
    (1, 1, '2023-01-01'),
    (1, 2, '2023-03-01'),
    (2, 3, '2023-04-01');

-- ==============================
-- EMPLOYEE CHANGE
-- ==============================
INSERT INTO EmployeeChange (employee_id, change_date, old_position_id, new_position_id, old_salary, new_salary)
VALUES
    (1, '2023-02-15', 2, 4, 1500.00, 1800.00),
    (2, '2023-05-01', 3, 5, 1600.00, 1900.00);



UPDATE Employee
SET user_id = 1
WHERE id = 2; -- or whichever Employee ID should be linked

INSERT INTO Payroll (
    employee_id, period_start, period_end,
    base_salary, bonus, deductions, net_pay,
    payment_date, status
) VALUES
      (1, '2024-04-01', '2024-04-30', 1000.00, 150.00, 50.00, 1100.00, '2024-05-01', 'Paid'),
      (2, '2024-04-01', '2024-04-30', 1200.00, 100.00, 80.00, 1220.00, '2024-05-01', 'Paid');


SELECT * FROM Employee WHERE user_id = 18;

UPDATE Employee
SET user_id = 2
WHERE id = 1; -- Link hr_user to Marko Petrovic (Employee ID 1)

UPDATE Employee
SET user_id = 3
WHERE id = 2; -- Link employee_user to Anna Müller (Employee ID 2)

SELECT id, first_name, last_name, user_id FROM Employee;

-- Create a new employee for employee_user (user_id = 3)
INSERT INTO Employee (
    PIN, last_name, first_name, birth_date, date_of_hire,
    email, gender, nationality_id, department_id, position_id,
    employment_status, employment_type, tax_id
) VALUES (
             'EMP003', 'Ivanovic', 'Jelena', '1992-04-12', '2023-02-01',
             'jelena@example.com', 'Female', 3, 1, 5,
             'Active', 'Full-Time', 'TAX-003'
         );

-- Link the users to employees
UPDATE Employee SET user_id = 1 WHERE id = 1;  -- admin_user → Marko
UPDATE Employee SET user_id = 2 WHERE id = 2;  -- hr_user → Anna
UPDATE Employee SET user_id = 3 WHERE id = 3;  -- employee_user → Jelena

-- Insert sample positions
INSERT INTO ContractAnnex (contract_id, change_date, description, document_path)
VALUES
    (1, '2023-05-01', 'Contract extension for 6 months', 'path/to/annex1.pdf'),
    (2, '2022-12-01', 'Salary adjustment', 'path/to/annex2.pdf');

INSERT INTO Position (name, short_name, education_level_id, requires_licensing)
VALUES
    ('Software Developer', 'Dev', 4, FALSE),
    ('Project Manager', 'PM', 4, TRUE),
    ('HR Manager', 'HRM', 4, TRUE);
INSERT INTO EmploymentContract (employee_id, start_date, end_date, position_id, salary, contract_type, signed_date, document_path)
VALUES
    (1, '2023-01-01', '2024-01-01', 1, 50000.00, 'Permanent', '2023-01-01', 'path/to/document1.pdf'),
    (2, '2022-06-01', '2023-06-01', 2, 45000.00, 'Temporary', '2022-06-01', 'path/to/document2.pdf');





-- Insert unencrypted users for employees 4–8
INSERT INTO User (username, password, role_id) VALUES
                                                   ('employee4', 'LauraPass123', 3),
                                                   ('employee5', 'MilanSecure456', 3),
                                                   ('employee6', 'ArtaPwd789', 3),
                                                   ('employee7', 'ElvisKey321', 3),
                                                   ('employee8', 'TamaraAccess654', 3);

-- Insert extra nationalities and departments if needed
INSERT INTO Nationality (name, is_active) VALUES
                                              ('Hungarian', TRUE),
                                              ('Albanian', TRUE);

INSERT INTO Department (name) VALUES
                                  ('Marketing'),
                                  ('Finance');

-- Insert extra positions
INSERT INTO Position (name, short_name, education_level_id, requires_licensing) VALUES
                                                                                    ('Marketing Specialist', 'MS', 4, FALSE),
                                                                                    ('Finance Analyst', 'FA', 4, TRUE);
-- Insert new users with readable passwords (dummy data)
INSERT INTO User (username, password, role_id) VALUES
                                                   ('employee4', 'LauraPass123', 3),
                                                   ('employee5', 'MilanSecure456', 3),
                                                   ('employee6', 'ArtaPwd789', 3),
                                                   ('employee7', 'ElvisKey321', 3),
                                                   ('employee8', 'TamaraAccess654', 3);

-- Add new nationalities and departments if not present
INSERT IGNORE INTO Nationality (name, is_active) VALUES ('Hungarian', TRUE), ('Albanian', TRUE);
INSERT IGNORE INTO Department (name) VALUES ('Marketing'), ('Finance');

-- Add positions if not already in use
INSERT IGNORE INTO Position (name, short_name, education_level_id, requires_licensing) VALUES
                                                                                           ('Marketing Specialist', 'MS', 4, FALSE),
                                                                                           ('Finance Analyst', 'FA', 4, TRUE),
                                                                                           ('Senior Developer', 'SD', 4, FALSE);
