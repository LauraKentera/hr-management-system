USE hrms;

INSERT INTO Role (name) VALUES ('Admin'), ('HR'), ('Employee');

INSERT INTO User (username, password, role_id)
VALUES
    ('admin_user', '$2a$10$bBuT1K9bliBB.H35cYallOAo3GFk4BfLG4iYM021evOBpRvWAjUCe', 1),
    ('hr_user', '$2a$10$D.C6RCTrgOLITP34Z9.Hi.1RVi3uPrioaaMPWN6r9qSlXmnlLvXme', 2),
    ('employee_user', '$2a$10$4miM5cIJRVtEIonxpx3aO.K4TFkojoYTVJXaOyXr4JUmgBP0IpIr.', 3);



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