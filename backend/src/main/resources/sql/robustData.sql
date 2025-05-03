-- ==============================
-- SAFETY RESET
-- ==============================

SET
FOREIGN_KEY_CHECKS = 0;
SET
FOREIGN_KEY_CHECKS = 1;
USE hrms;

-- ==============================
-- ROLES & USERS
-- ==============================

INSERT INTO Role (name)
VALUES ('Admin'),
       ('HR'),
       ('Employee');

-- Users with hashed passwords and assigned roles
INSERT INTO User (username, password, role_id)
VALUES ('admin_user', '$2a$10$bBuT1K9bliBB.H35cYallOAo3GFk4BfLG4iYM021evOBpRvWAjUCe', 1),
       ('hr_user', '$2a$10$D.C6RCTrgOLITP34Z9.Hi.1RVi3uPrioaaMPWN6r9qSlXmnlLvXme', 2),
       ('employee_user', '$2a$10$4miM5cIJRVtEIonxpx3aO.K4TFkojoYTVJXaOyXr4JUmgBP0IpIr.', 3),
       ('employee4', 'LauraPass123', 3),
       ('employee5', 'MilanSecure456', 3),
       ('employee6', 'ArtaPwd789', 3),
       ('employee7', 'ElvisKey321', 3),
       ('employee8', 'TamaraAccess654', 3);

-- ==============================
-- REFERENCE DATA
-- ==============================

INSERT INTO Nationality (name, is_active)
VALUES ('Croatian', TRUE),
       ('Serbian', TRUE),
       ('Bosnian', TRUE),
       ('Slovenian', TRUE),
       ('Montenegrin', TRUE),
       ('Hungarian', TRUE),
       ('Albanian', TRUE);

INSERT INTO EducationLevel (name, is_active)
VALUES ('Primary School', TRUE),
       ('High School', TRUE),
       ('Vocational School', TRUE),
       ('Bachelor Degree', TRUE),
       ('Master Degree', TRUE),
       ('PhD', TRUE);

INSERT INTO AbsenceType (name, code, is_paid, requires_approval)
VALUES ('Annual Leave', 'AL', TRUE, TRUE),
       ('Sick Leave', 'SL', TRUE, FALSE),
       ('Maternity Leave', 'ML', TRUE, TRUE),
       ('Paternity Leave', 'PL', TRUE, TRUE),
       ('Unpaid Leave', 'UL', FALSE, TRUE);

INSERT INTO DisabilityCategory (name, legal_code)
VALUES ('Category I - Mild', 'M86-1'),
       ('Category II - Moderate', 'M86-2'),
       ('Category III - Severe', 'M86-3');

-- ==============================
-- STRUCTURE
-- ==============================

INSERT INTO Department (name)
VALUES ('IT'),
       ('HR'),
       ('Marketing'),
       ('Finance');

INSERT INTO Position (name, short_name, education_level_id, requires_licensing)
VALUES ('CEO', 'CEO', 5, TRUE),
       ('CTO', 'CTO', 5, TRUE),
       ('CFO', 'CFO', 5, TRUE),
       ('Frontend Developer', 'FE', 3, FALSE),
       ('Senior Accountant', 'SACC', 4, TRUE),
       ('Software Developer', 'Dev', 4, FALSE),
       ('Project Manager', 'PM', 4, TRUE),
       ('HR Manager', 'HRM', 4, TRUE),
       ('Marketing Specialist', 'MS', 4, FALSE),
       ('Finance Analyst', 'FA', 4, TRUE);

-- ==============================
-- EMPLOYEES
-- ==============================

INSERT INTO Employee (PIN, last_name, first_name, birth_date, date_of_hire, email, gender,
                      nationality_id, department_id, position_id, employment_status, employment_type, tax_id, user_id)
VALUES ('EMP001', 'Petrovic', 'Marko', '1990-05-15', '2020-01-01', 'marko@sharp.com', 'Male', 1, 1, 1, 'Active',
        'Full-Time', 'TAX-001', 1),
       ('EMP002', 'Müller', 'Anna', '1985-08-22', '2022-06-01', 'anna@sharp.com', 'Female', 2, 2, 2, 'Active',
        'Full-Time', 'TAX-002', 2),
       ('EMP003', 'Ivanovic', 'Jelena', '1992-04-12', '2023-02-01', 'jelena@example.com', 'Female', 3, 1, 5, 'Active',
        'Full-Time', 'TAX-003', 3),
       ('EMP004', 'Autogen', 'Laura', '1990-01-01', '2025-05-03', 'laura@autogen.local', 'Other', 1, 1, 6, 'Active',
        'Full-Time', 'TAX-004', 4),
       ('EMP005', 'Kovacs', 'Milan', '1988-03-10', '2022-01-15', 'milan.kovacs@example.com', 'Male', 6, 1, 6, 'Active',
        'Full-Time', 'TAX-005', 5),
       ('EMP006', 'Gjini', 'Arta', '1993-12-30', '2021-09-01', 'arta.gjini@example.com', 'Female', 7, 2, 7, 'Active',
        'Full-Time', 'TAX-006', 6),
       ('EMP007', 'Mehmedovic', 'Elvis', '1990-07-18', '2019-04-10', 'elvis@example.com', 'Male', 1, 2, 2, 'Active',
        'Full-Time', 'TAX-007', 7),
       ('EMP008', 'Radic', 'Tamara', '1984-11-11', '2018-11-01', 'tamara@example.com', 'Female', 2, 4, 10, 'Active',
        'Full-Time', 'TAX-008', 8);

-- ==============================
-- ASSIGN DEPARTMENT MANAGERS
-- ==============================

UPDATE Department
SET manager_id = 1
WHERE department_id = 1;
UPDATE Department
SET manager_id = 2
WHERE department_id = 2;

-- ==============================
-- CONTRACTS
-- ==============================

INSERT INTO EmploymentContract (employee_id, start_date, end_date, position_id, salary, contract_type, signed_date,
                                document_path)
VALUES (1, '2023-01-01', '2024-01-01', 1, 50000.00, 'Permanent', '2023-01-01', 'path/to/document1.pdf'),
       (2, '2022-06-01', '2023-06-01', 2, 45000.00, 'Temporary', '2022-06-01', 'path/to/document2.pdf');

INSERT INTO ContractAnnex (contract_id, change_date, description, document_path)
VALUES (1, '2023-05-01', 'Contract extension for 6 months', 'path/to/annex1.pdf'),
       (2, '2022-12-01', 'Salary adjustment', 'path/to/annex2.pdf');

-- ==============================
-- PAYROLL
-- ==============================

INSERT INTO Payroll (employee_id, period_start, period_end, base_salary, bonus, deductions, net_pay, payment_date,
                     status)
VALUES (1, '2024-04-01', '2024-04-30', 1000.00, 150.00, 50.00, 1100.00, '2024-05-01', 'Paid'),
       (2, '2024-04-01', '2024-04-30', 1200.00, 100.00, 80.00, 1220.00, '2024-05-01', 'Paid'),
       (3, '2024-04-01', '2024-04-30', 900.00, 100.00, 50.00, 950.00, '2024-05-01', 'Paid'),
       (4, '2024-04-01', '2024-04-30', 1100.00, 120.00, 30.00, 1190.00, '2024-05-01', 'Paid'),
       (5, '2024-04-01', '2024-04-30', 2500.00, 300.00, 200.00, 2600.00, '2024-05-01', 'Paid'),
       (6, '2024-04-01', '2024-04-30', 3200.00, 150.00, 100.00, 3250.00, '2024-05-01', 'Paid'),
       (7, '2024-04-01', '2024-04-30', 6000.00, 500.00, 200.00, 6300.00, '2024-05-01', 'Paid'),
       (8, '2024-04-01', '2024-04-30', 8000.00, 800.00, 250.00, 8550.00, '2024-05-01', 'Paid');

-- ==============================
-- REMAINING DATA INSERTIONS
-- ==============================

INSERT INTO Benefit (name, is_taxable)
VALUES ('Meal Allowance', FALSE),
       ('Transportation Allowance', FALSE),
       ('Child Allowance', TRUE),
       ('Housing Allowance', TRUE),
       ('Education Reimbursement', TRUE);

INSERT INTO BenefitItem (benefit_id, from_date, amount)
VALUES (1, '2023-01-01', 50.00),
       (2, '2023-01-01', 30.00),
       (3, '2023-01-01', 100.00),
       (4, '2023-01-01', 200.00),
       (5, '2023-01-01', 500.00);

INSERT INTO EmployeeBenefit (employee_id, benefit_id, effective_date)
VALUES (1, 1, '2023-01-01'),
       (1, 2, '2023-03-01'),
       (2, 3, '2023-04-01');

INSERT INTO EmployeeDisability (employee_id, disability_category_id, official_code, from_date, percentage)
VALUES (1, 1, 'M86-2023-001', '2023-03-15', 20),
       (2, 2, 'M86-2023-002', '2023-05-10', 40);

INSERT INTO EmployeeAbsence (employee_id, absence_type_id, start_date, end_date)
VALUES (1, 1, '2023-07-01', '2023-07-14');

INSERT INTO EmployeeBankAccount (employee_id, bank_name, account_number, iban)
VALUES (1, 'NLB Banka', '123456789', 'ME25505000012345678951'),
       (2, 'Erste Bank', '987654321', 'ME25505000098765432198');

INSERT INTO EmployeeChange (employee_id, change_date, old_position_id, new_position_id, old_salary, new_salary)
VALUES (1, '2023-02-15', 2, 4, 1500.00, 1800.00),
       (2, '2023-05-01', 3, 5, 1600.00, 1900.00);
