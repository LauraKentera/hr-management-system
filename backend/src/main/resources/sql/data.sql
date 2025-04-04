USE hrms;

-- Role and User data
INSERT INTO Role (name)
VALUES ('Admin'), ('HR'), ('Employee');

INSERT INTO User (username, password, role_id)
VALUES
    ('admin_user', '$2a$10$bBuT1K9bliBB.H35cYallOAo3GFk4BfLG4iYM021evOBpRvWAjUCe', 1),
    ('hr_user', '$2a$10$D.C6RCTrgOLITP34Z9.Hi.1RVi3uPrioaaMPWN6r9qSlXmnlLvXme', 2),
    ('employee_user', '$2a$10$4miM5cIJRVtEIonxpx3aO.K4TFkojoYTVJXaOyXr4JUmgBP0IpIr.', 3);

-- Nationality data
INSERT INTO Nationality (name, is_active) 
VALUES 
    ('Croatian', TRUE),
    ('Serbian', TRUE),
    ('Bosnian', TRUE),
    ('Slovenian', TRUE),
    ('Montenegrin', TRUE);

-- EducationLevel data
INSERT INTO EducationLevel (name, is_active)
VALUES 
    ('Primary School', TRUE),
    ('High School', TRUE),
    ('Vocational School', TRUE),
    ('Bachelor Degree', TRUE),
    ('Master Degree', TRUE),
    ('PhD', TRUE);

-- AbsenceType data
INSERT INTO AbsenceType (name, code, is_paid, requires_approval)
VALUES 
    ('Annual Leave', 'AL', TRUE, TRUE),
    ('Sick Leave', 'SL', TRUE, FALSE),
    ('Maternity Leave', 'ML', TRUE, TRUE),
    ('Paternity Leave', 'PL', TRUE, TRUE),
    ('Unpaid Leave', 'UL', FALSE, TRUE);

-- Region data (for BenefitItem)
INSERT INTO Region (name)
VALUES ('North'), ('South'), ('East'), ('West');

-- Position data
INSERT INTO Position (parent_id, name, short_name, education_level_id, benefits, requires_licensing)
VALUES 
    (NULL, 'Chief Executive Officer', 'CEO', 5, NULL, TRUE),
    (1, 'Chief Technology Officer', 'CTO', 5, NULL, TRUE),
    (1, 'Chief Financial Officer', 'CFO', 5, NULL, TRUE),
    (2, 'Software Development Manager', 'SDM', 4, NULL, FALSE),
    (3, 'Senior Accountant', 'SACC', 4, NULL, TRUE),
    (4, 'Frontend Developer', 'FE', 3, NULL, FALSE);

-- DisabilityCategory data
INSERT INTO DisabilityCategory (name, legal_code)
VALUES 
    ('Category I - Mild', 'M86-1'),
    ('Category II - Moderate', 'M86-2'),
    ('Category III - Severe', 'M86-3');

-- Benefit data
INSERT INTO Benefit (name, is_taxable)
VALUES 
    ('Meal Allowance', FALSE),
    ('Transportation Allowance', FALSE),
    ('Child Allowance', TRUE),
    ('Housing Allowance', TRUE),
    ('Education Reimbursement', TRUE);

-- BenefitItem data
INSERT INTO BenefitItem (benefit_id, region_id, from_date, amount)
VALUES 
    (1, 1, '2023-01-01', 50.00),
    (2, 2, '2023-01-01', 30.00),
    (3, 3, '2023-01-01', 100.00),
    (4, 4, '2023-01-01', 200.00),
    (5, 1, '2023-01-01', 500.00);

-- EmployeeDisability data
INSERT INTO EmployeeDisability (employee_id, disability_category_id, official_code, from_date, percentage)
VALUES 
    (1, 1, 'M86-2023-001', '2023-03-15', 20),
    (3, 2, 'M86-2023-002', '2023-05-10', 40);

-- Employee data
INSERT INTO Employee (
    PIN, last_name, first_name, birth_date, date_of_hire, email, gender, nationality_id,
    department_id, position_id, employment_status, employment_type, tax_id
)
VALUES 
    ('EMP001', 'Petrovic', 'Marko', '1990-05-15', '2020-01-01', 'marko@sharp.com', 'Male', 1, 1, 1, 'Active', 'Full-Time', 'TAX-001'),
    ('EMP002', 'Müller', 'Anna', '1985-08-22', '2022-06-01', 'anna@sharp.com', 'Female', 2, 2, 2, 'Active', 'Full-Time', 'TAX-002');

-- Department data
INSERT INTO Department (name, manager_id)
VALUES 
    ('IT', NULL),
    ('HR', NULL);

-- Update Department manager_id values
UPDATE Department SET manager_id = 1 WHERE department_id = 1;
UPDATE Department SET manager_id = 2 WHERE department_id = 2;

-- EmployeeBenefit data (using composite key version)
INSERT INTO EmployeeBenefit (employee_id, benefit_id, effective_date)
VALUES 
    (1, 1, '2023-01-01'),
    (2, 2, '2023-01-01');

-- EmployeeAbsence data
INSERT INTO EmployeeAbsence (employee_id, absence_type_id, start_date, end_date)
VALUES 
    (1, 1, '2023-07-01', '2023-07-14');

-- ContractType data
INSERT INTO ContractType (type_name, description, duration, user_id)
VALUES 
    ('Full-Time', 'Full-time employment contract', 12, 1),
    ('Part-Time', 'Part-time employment contract', 6, 2);

-- Bank data
INSERT INTO Bank (name, address, city, phone, fax, website, email, contact_person, note, our_bank, ebanking, bcpld, user_id)
VALUES 
    ('ABC Bank', '123 Main St', 'New York', '123-456-7890', '123-456-7891', 'www.abcbank.com', 'contact@abcbank.com', 'John Doe', 'Main branch', TRUE, TRUE, FALSE, 1),
    ('XYZ Bank', '456 Elm St', 'Los Angeles', '987-654-3210', '987-654-3211', 'www.xyzbank.com', 'support@xyzbank.com', 'Jane Smith', 'Secondary branch', FALSE, TRUE, TRUE, 2);

-- Evaluation data
INSERT INTO Evaluation (name, user_id)
VALUES 
    ('Annual Review', 1),
    ('Probation Review', 2);

-- EmployeeEvaluation data
INSERT INTO EmployeeEvaluation (evaluation_id, evaluation_date, comment, score, user_id)
VALUES 
    (1, '2023-12-31', 'Excellent performance', 95.50, 1),
    (2, '2023-11-30', 'Satisfactory performance', 85.00, 2);

-- EmploymentTermination data
INSERT INTO EmploymentTermination (name, user_id)
VALUES 
    ('Resignation', 1),
    ('Termination for Cause', 2);
