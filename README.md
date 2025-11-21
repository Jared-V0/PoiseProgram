# PoisePMS Project Management System

The **PoisePMS Project Management System** is a Java-based application designed to manage projects, with features for tracking project details, associated personnel, and project completion status. It provides options to add, update, delete, and view project information, as well as the ability to generate Javadoc documentation for the codebase.


## Project Structure

PoisePMS/
│
├── Person.java
├── Architect.java
├── Contractor.java
├── Customer.java
├── StructuralEngineer.java
├── Project.java
├── PoisedProgram.java
├── DatabaseConnection.java
│
└── SQL/
    ├── PoisePMS.sql
    └── Articles.sql (optional additional schema)


## Database Structure

The PoisePMS MySQL database contains the following tables:
- StructuralEngineers
- ProjectManagers
- Architects
- Contractors
- Customers
- Projects

The `Projects` table contains foreign keys linking to all personnel types:
(engineer_id, manager_id, architect_id, contractor_id, customer_id)

The full SQL schema is available in `PoisePMS.sql`.

## Features

- **Project Management**: Add, update, and delete projects.
- **Personnel Tracking**: Track engineers, managers, architects, contractors, and customers.
- **Status Tracking**: Identify incomplete and overdue projects.
- **Database Integration**: Uses MySQL for data storage.
- **Javadoc Documentation**: Easily generate API documentation for the codebase.

## Technologies Used

- **Java**: Core language used for the application.
- **MySQL**: Database management system for storing project and personnel data.
- **JDBC**: Java Database Connectivity to connect Java and MySQL.
- **IntelliJ IDEA**: Recommended IDE for development.


## Running the Application

1. Import and run `PoisePMS.sql` in MySQL to create all tables.
2. Edit `DatabaseConnection.java` with your MySQL username and password.
3. Add the MySQL JDBC Driver to your IDE (mysql-connector-j.jar).
4. Open and run `PoisedProgram.java` in your IDE.


## Requirements

- **Java** 
- **MySQL Database** 
- **MySQL Connector/J**: JDBC driver to enable MySQL connectivity in Java. [Download MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

## Future Improvements

- Add JavaFX or Swing GUI
- Convert the program to a REST API (Spring Boot)
- Add authentication for admin users
- Add reporting tools (financial summary, overdue report)
- Export project summaries to a text/HTML/PDF file
- Implement logging using Log4j or SLF4J

## Setup Instructions

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/yourusername/PoisePMS.git


## Author

**Jared Valensky**  
Backend Developer 

