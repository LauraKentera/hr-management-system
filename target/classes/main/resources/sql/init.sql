-- Creating a table
CREATE DATABASE IF NOT EXISTS hrms;

-- Create Role table
CREATE TABLE IF NOT EXISTS Role (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(50) NOT NULL
    );

-- Create User table
CREATE TABLE IF NOT EXISTS User (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id INT,
    employee_id INT,
    FOREIGN KEY (role_id) REFERENCES Role(id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id) -- Optional link
    );

-- Sample Role data
INSERT INTO Role (name) VALUES ('Admin'), ('HR'), ('Employee');

-- Sample User data
INSERT INTO User (username, password, role_id)
VALUES
    ('admin_user', 'admin123', 1),
    ('hr_user', 'hr123', 2),
    ('employee_user', 'emp123', 3);
