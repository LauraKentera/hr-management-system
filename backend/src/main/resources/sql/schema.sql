CREATE DATABASE IF NOT EXISTS hrms;

USE hrms;

CREATE TABLE IF NOT EXISTS Role (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS User (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id INT,
    employee_id INT,
    FOREIGN KEY (role_id) REFERENCES Role(id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id)
    );
