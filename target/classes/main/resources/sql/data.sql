INSERT INTO Role (name) VALUES ('Admin'), ('HR'), ('Employee');

INSERT INTO User (username, password, role_id)
VALUES
    ('admin_user', '$2a$10$eW5YbX1aF.abc123HashedPass...', 1),
    ('hr_user', '$2a$10$eW5YbX1aF.abc123HashedPass...', 2),
    ('employee_user', '$2a$10$eW5YbX1aF.abc123HashedPass...', 3);
