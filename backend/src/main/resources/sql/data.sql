USE hrms;

INSERT INTO Role (name) VALUES ('Admin'), ('HR'), ('Employee');

INSERT INTO User (username, password, role_id)
VALUES
    ('admin_user', '$2a$10$bBuT1K9bliBB.H35cYallOAo3GFk4BfLG4iYM021evOBpRvWAjUCe', 1),
    ('hr_user', '$2a$10$D.C6RCTrgOLITP34Z9.Hi.1RVi3uPrioaaMPWN6r9qSlXmnlLvXme', 2),
    ('employee_user', '$2a$10$4miM5cIJRVtEIonxpx3aO.K4TFkojoYTVJXaOyXr4JUmgBP0IpIr.', 3);

