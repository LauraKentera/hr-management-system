USE
hrms;

INSERT INTO Role (name)
VALUES ('Admin'),
       ('HR'),
       ('Employee');

INSERT INTO User (username, password, role_id)
VALUES ('admin_user',
        '$2a$10$bBuT1K9bliBB.H35cYallOAo3GFk4BfLG4iYM021evOBpRvWAjUCe',
        1),
       ('hr_user',
        '$2a$10$D.C6RCTrgOLITP34Z9.Hi.1RVi3uPrioaaMPWN6r9qSlXmnlLvXme',
        2),
       ('employee_user',
        '$2a$10$4miM5cIJRVtEIonxpx3aO.K4TFkojoYTVJXaOyXr4JUmgBP0IpIr.',
        3);

INSERT INTO Nationality (name, is_active)
VALUES ('Croatian', TRUE),
       ('Serbian', TRUE),
       ('Bosnian', TRUE),
       ('Slovenian', TRUE),
       ('Montenegrin', TRUE);

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

INSERT INTO Position(parent_id,
                     name,
                     short_name,
                     education_level_id,
                     requires_licensing)
VALUES (NULL, 'Chief Executive Officer', 'CEO', 5, TRUE),
       (1, 'Chief Technology Officer', 'CTO', 5, TRUE),
       (1, 'Chief Financial Officer', 'CFO', 5, TRUE),
       (2,
        'Software Development Manager',
        'SDM',
        4,
        FALSE),
       (3, 'Senior Accountant', 'SACC', 4, TRUE),
       (4, 'Frontend Developer', 'FE', 3, FALSE);

INSERT INTO DisabilityCategory (name, legal_code)
VALUES ('Category I - Mild', 'M86-1'),
       ('Category II - Moderate', 'M86-2'),
       ('Category III - Severe', 'M86-3');

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

INSERT INTO EmployeeDisability (employee_id,
                                disability_category_id,
                                official_code,
                                from_date,
                                percentage)
VALUES (1, 1, 'M86-2023-001', '2023-03-15', 20),
       (3, 2, 'M86-2023-002', '2023-05-10', 40);

INSERT INTO EmployeeBenefit (employee_id, benefit_id, from_date, amount)
VALUES (1, 1, '2023-01-01', 50.00),
       (2, 1, '2023-01-01', 50.00),
       (3, 2, '2023-01-01', 30.00),
       (4, 3, '2023-06-01', 100.00),
       (5, 4, '2023-01-01', 200.00);

INSERT INTO Department (name, manager_id)
VALUES ('IT', NULL),
       ('HR', NULL);

INSERT INTO Employee (PIN,
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
                      tax_id)
VALUES ('EMP001',
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
        'TAX-001'),
       ('EMP002',
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
        'TAX-002');

UPDATE Department
SET manager_id = 1
WHERE department_id = 1;

UPDATE Department
SET manager_id = 2
WHERE department_id = 2;

INSERT INTO EmployeeBenefit (employee_id, benefit_id, effective_date)
VALUES (1, 1, '2023-01-01'),
       (2, 2, '2023-01-01');

INSERT INTO EmployeeAbsence (employee_id,
                             absence_type_id,
                             start_date,
                             end_date)
VALUES (1, 1, '2023-07-01', '2023-07-14');

