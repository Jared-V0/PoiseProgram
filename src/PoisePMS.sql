-- Drop the database if it exists
DROP DATABASE IF EXISTS PoisePMS;
-- Create the database
CREATE DATABASE PoisePMS;
USE PoisePMS;

-- Create StructuralEngineers table
CREATE TABLE IF NOT EXISTS StructuralEngineers (
    engineer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    telephone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE
);

-- Create ProjectManagers table
CREATE TABLE IF NOT EXISTS ProjectManagers (
    manager_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    telephone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE
);

-- Create Architects table
CREATE TABLE IF NOT EXISTS Architects (
    architect_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    telephone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    physical_address TEXT
);

-- Create Contractors table
CREATE TABLE IF NOT EXISTS Contractors (
    contractor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    telephone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    physical_address TEXT
);

-- Create Customers table
CREATE TABLE IF NOT EXISTS Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    telephone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    physical_address TEXT
);

-- Create Projects table
CREATE TABLE IF NOT EXISTS Projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    budget DECIMAL(15, 2),
    
    -- Foreign Keys for assigned individuals
    engineer_id INT,
    manager_id INT,
    architect_id INT,
    contractor_id INT,
    customer_id INT,
    
    -- Foreign Key Constraints
    FOREIGN KEY (engineer_id) REFERENCES StructuralEngineers(engineer_id) ON DELETE SET NULL,
    FOREIGN KEY (manager_id) REFERENCES ProjectManagers(manager_id) ON DELETE SET NULL,
    FOREIGN KEY (architect_id) REFERENCES Architects(architect_id) ON DELETE SET NULL,
    FOREIGN KEY (contractor_id) REFERENCES Contractors(contractor_id) ON DELETE SET NULL,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE SET NULL
);

USE PoisePMS;

-- Insert data into StructuralEngineers
INSERT INTO StructuralEngineers (name, telephone_number, email) VALUES
('Jared Valensky', '027-921-1568', 'jared@gmail.com'),
('Joshua Van Blerk', '019-981-9816', 'josh@gmail.com');

-- Insert data into ProjectManagers
INSERT INTO ProjectManagers (name, telephone_number, email) VALUES
('Wilbur Kepkey', '165-156-6168', 'wilbur@gmail.com'),
('Adam White', '165-865-6182', 'adam@gmail.com');

-- Insert data into Architects
INSERT INTO Architects (name, telephone_number, email, physical_address) VALUES
('Dylan Williams', '156-616-8156', 'dyl@gmail.com', '23 Test Ave, Cape Town'),
('Aidan Lewis', '156-568-1230', 'ads@gmail.com', '46 Waterloo Blvd, Cape Town');

-- Insert data into Contractors
INSERT INTO Contractors (name, telephone_number, email, physical_address) VALUES
('Mike Larson', '342-111-7890', 'mike@gmail.com', '789 Pinleands Rd, Cape Town'),
('Emma Lee', '896-222-3421', 'emma@gmail.com', '10 Thronton St, Cape Town');

-- Insert data into Customers
INSERT INTO Customers (name, telephone_number, email, physical_address) VALUES
('Uzair Bardey', '567-333-7890', 'uzi@gmail.com', '20 Poplar Dr, Cape Town'),
('Jordan Coetzee', '453-444-7890', 'jords@gmail.com', '30 Pine Ln, Cape Town');

-- Insert data into Projects with correct foreign key references
-- Assuming that IDs for each of the related records are 1 and 2
INSERT INTO Projects (project_name, start_date, end_date, budget, engineer_id, manager_id, architect_id, contractor_id, customer_id) VALUES
('Bridge Haley', '2023-01-05', '2023-12-31', 2342345.00, 1, 1, 1, 1, 1),
('Shop Renovation', '2023-03-29', '2024-09-30', 3245260.00, 2, 2, 2, 2, 2);
