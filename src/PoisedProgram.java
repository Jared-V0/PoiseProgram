// PoisedProgram.java

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class PoisedProgram {


  /**
   * Adds a new project to the database.
   */
  public static void addNewProject() {
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Capture project information from user
    System.out.print("Enter Project Name: ");
    String projectName = scanner.nextLine();

    // Validate start date
    String startDate;
    while (true) {
      System.out.print("Enter Start Date (YYYY-MM-DD): ");
      startDate = scanner.nextLine();
      if (isValidDate(startDate, dateFormatter)) {
        break;
      } else {
        System.out.println("Invalid date format or value. Please try again.");
      }
    }

    // Validate end date
    String endDate;
    while (true) {
      System.out.print("Enter End Date (YYYY-MM-DD): ");
      endDate = scanner.nextLine();
      if (isValidDate(endDate, dateFormatter)) {
        break;
      } else {
        System.out.println("Invalid date format or value. Please try again.");
      }
    }

    System.out.print("Enter Budget: ");
    double budget = scanner.nextDouble();
    scanner.nextLine();  // Consume newline

    // Validate foreign key IDs
    int engineerId;
    while (true) {
      System.out.print("Enter Engineer ID: ");
      engineerId = scanner.nextInt();
      if (doesIdExist("StructuralEngineers", "engineer_id", engineerId)) {
        break;
      } else {
        System.out.println("Engineer ID does not exist. Please enter a valid Engineer ID.");
      }
    }

    int managerId;
    while (true) {
      System.out.print("Enter Manager ID: ");
      managerId = scanner.nextInt();
      if (doesIdExist("ProjectManagers", "manager_id", managerId)) {
        break;
      } else {
        System.out.println("Manager ID does not exist. Please enter a valid Manager ID.");
      }
    }

    int architectId;
    while (true) {
      System.out.print("Enter Architect ID: ");
      architectId = scanner.nextInt();
      if (doesIdExist("Architects", "architect_id", architectId)) {
        break;
      } else {
        System.out.println("Architect ID does not exist. Please enter a valid Architect ID.");
      }
    }

    int contractorId;
    while (true) {
      System.out.print("Enter Contractor ID: ");
      contractorId = scanner.nextInt();
      if (doesIdExist("Contractors", "contractor_id", contractorId)) {
        break;
      } else {
        System.out.println("Contractor ID does not exist. Please enter a valid Contractor ID.");
      }
    }

    int customerId;
    while (true) {
      System.out.print("Enter Customer ID: ");
      customerId = scanner.nextInt();
      if (doesIdExist("Customers", "customer_id", customerId)) {
        break;
      } else {
        System.out.println("Customer ID does not exist. Please enter a valid Customer ID.");
      }
    }

    // Create a new Project object with user input
    Project newProject = new Project(0, projectName, startDate, endDate, budget, engineerId, managerId, architectId, contractorId, customerId);

    // Save the project to the database
    newProject.saveToDatabase();
    System.out.println("Project has been successfully added to the database.");
  }

  // Helper method to validate date format and values
  private static boolean isValidDate(String dateStr, DateTimeFormatter formatter) {
    try {
      LocalDate.parse(dateStr, formatter);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }


  public static void updateProjectInDatabase() {
    Scanner scanner = new Scanner(System.in);

    // Prompt for project ID
    System.out.print("Enter Project ID to update: ");
    int projectId = scanner.nextInt();
    scanner.nextLine();  // Consume newline

    // Load existing project from database
    Project project = Project.loadFromDatabase(projectId);
    if (project == null) {
      System.out.println("Project with ID " + projectId + " not found.");
      return;
    }

    // Prompt user for new values, showing current values as defaults
    System.out.println("Current Project Name: " + project.getProjectName());
    System.out.print("Enter new Project Name (or press Enter to keep current): ");
    String projectName = scanner.nextLine();
    if (!projectName.isEmpty()) project.projectName = projectName;

    System.out.println("Current Start Date: " + project.getStartDate());
    System.out.print("Enter new Start Date (YYYY-MM-DD) (or press Enter to keep current): ");
    String startDate = scanner.nextLine();
    if (!startDate.isEmpty()) project.startDate = startDate;

    System.out.println("Current End Date: " + project.getEndDate());
    System.out.print("Enter new End Date (YYYY-MM-DD) (or press Enter to keep current): ");
    String endDate = scanner.nextLine();
    if (!endDate.isEmpty()) project.endDate = endDate;

    System.out.println("Current Budget: " + project.getBudget());
    System.out.print("Enter new Budget (or press Enter to keep current): ");
    String budgetStr = scanner.nextLine();
    if (!budgetStr.isEmpty()) project.budget = Double.parseDouble(budgetStr);

    // Validate and update Engineer ID
    System.out.println("Current Engineer ID: " + project.engineerId);
    System.out.print("Enter new Engineer ID (or press Enter to keep current): ");
    String engineerIdStr = scanner.nextLine();
    if (!engineerIdStr.isEmpty()) {
      int engineerId = Integer.parseInt(engineerIdStr);
      if (doesIdExist("StructuralEngineers", "engineer_id", engineerId)) {
        project.engineerId = engineerId;
      } else {
        System.out.println("Engineer ID does not exist. Keeping the current Engineer ID.");
      }
    }

    // Validate and update Manager ID
    System.out.println("Current Manager ID: " + project.managerId);
    System.out.print("Enter new Manager ID (or press Enter to keep current): ");
    String managerIdStr = scanner.nextLine();
    if (!managerIdStr.isEmpty()) {
      int managerId = Integer.parseInt(managerIdStr);
      if (doesIdExist("ProjectManagers", "manager_id", managerId)) {
        project.managerId = managerId;
      } else {
        System.out.println("Manager ID does not exist. Keeping the current Manager ID.");
      }
    }

    // Validate and update Architect ID
    System.out.println("Current Architect ID: " + project.architectId);
    System.out.print("Enter new Architect ID (or press Enter to keep current): ");
    String architectIdStr = scanner.nextLine();
    if (!architectIdStr.isEmpty()) {
      int architectId = Integer.parseInt(architectIdStr);
      if (doesIdExist("Architects", "architect_id", architectId)) {
        project.architectId = architectId;
      } else {
        System.out.println("Architect ID does not exist. Keeping the current Architect ID.");
      }
    }

    // Validate and update Contractor ID
    System.out.println("Current Contractor ID: " + project.contractorId);
    System.out.print("Enter new Contractor ID (or press Enter to keep current): ");
    String contractorIdStr = scanner.nextLine();
    if (!contractorIdStr.isEmpty()) {
      int contractorId = Integer.parseInt(contractorIdStr);
      if (doesIdExist("Contractors", "contractor_id", contractorId)) {
        project.contractorId = contractorId;
      } else {
        System.out.println("Contractor ID does not exist. Keeping the current Contractor ID.");
      }
    }

    // Validate and update Customer ID
    System.out.println("Current Customer ID: " + project.customerId);
    System.out.print("Enter new Customer ID (or press Enter to keep current): ");
    String customerIdStr = scanner.nextLine();
    if (!customerIdStr.isEmpty()) {
      int customerId = Integer.parseInt(customerIdStr);
      if (doesIdExist("Customers", "customer_id", customerId)) {
        project.customerId = customerId;
      } else {
        System.out.println("Customer ID does not exist. Keeping the current Customer ID.");
      }
    }

    // Save updated project to database
    project.updateInDatabase();
  }


  public static void deleteProject() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Project ID to delete: ");
    int projectId = scanner.nextInt();

    // Load the project to confirm it exists
    Project project = Project.loadFromDatabase(projectId);
    if (project == null) {
      System.out.println("Project with ID " + projectId + " not found.");
      return;
    }

    // Confirm and delete project
    System.out.print("Are you sure you want to delete this project? (yes/no): ");
    scanner.nextLine();  // consume newline
    String confirmation = scanner.nextLine();
    if (confirmation.equalsIgnoreCase("yes")) {
      project.deleteFromDatabase();
    } else {
      System.out.println("Project deletion canceled.");
    }
  }

  public static void deletePerson() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Role of Person to delete (e.g., Architect, Customer, Contractor): ");
    String role = scanner.nextLine().toLowerCase();

    // Map role to table name
    String tableName = "";
    switch (role) {
      case "architect":
        tableName = "Architects";
        break;
      case "customer":
        tableName = "Customers";
        break;
      case "contractor":
        tableName = "Contractors";
        break;
      case "projectmanager":
        tableName = "ProjectManagers";
        break;
      case "structuralengineer":
        tableName = "StructuralEngineers";
        break;
      default:
        System.out.println("Invalid role specified.");
        return;
    }

    // Prompt for person ID
    System.out.print("Enter ID of the " + role + " to delete: ");
    int personId = scanner.nextInt();

    // Load the person to confirm they exist
    Person person = Person.loadFromDatabase(tableName, personId);
    if (person == null) {
      System.out.println(role + " with ID " + personId + " not found in " + tableName + ".");
      return;
    }

    // Confirm and delete person
    System.out.print("Are you sure you want to delete this person? (yes/no): ");
    scanner.nextLine();  // consume newline
    String confirmation = scanner.nextLine();
    if (confirmation.equalsIgnoreCase("yes")) {
      person.deleteFromDatabase(tableName);
    } else {
      System.out.println("Person deletion canceled.");
    }
  }

  public static void showIncompleteProjects() {
    List<Project> incompleteProjects = Project.findIncompleteProjects();

    if (incompleteProjects.isEmpty()) {
      System.out.println("All projects are complete.");
    } else {
      System.out.println("Incomplete Projects:");
      for (Project project : incompleteProjects) {
        System.out.println("Project ID: " + project.getProjectId());
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Start Date: " + project.getStartDate());
        System.out.println("End Date: " + project.getEndDate());
        System.out.println("Budget: " + project.getBudget());
        System.out.println("Engineer ID: " + project.engineerId);
        System.out.println("Manager ID: " + project.managerId);
        System.out.println("Architect ID: " + project.architectId);
        System.out.println("Contractor ID: " + project.contractorId);
        System.out.println("Customer ID: " + project.customerId);
        System.out.println("---------------------------");
      }
    }
  }

  public static void showOverdueProjects() {
    List<Project> overdueProjects = Project.findOverdueProjects();

    if (overdueProjects.isEmpty()) {
      System.out.println("No projects are overdue.");
    } else {
      System.out.println("Overdue Projects:");
      for (Project project : overdueProjects) {
        System.out.println("Project ID: " + project.getProjectId());
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Start Date: " + project.getStartDate());
        System.out.println("End Date: " + project.getEndDate());
        System.out.println("Budget: " + project.getBudget());
        System.out.println("Engineer ID: " + project.engineerId);
        System.out.println("Manager ID: " + project.managerId);
        System.out.println("Architect ID: " + project.architectId);
        System.out.println("Contractor ID: " + project.contractorId);
        System.out.println("Customer ID: " + project.customerId);
        System.out.println("---------------------------");
      }
    }
  }

  public static void searchProject() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Search by (1) Project ID or (2) Project Name: ");
    int choice = scanner.nextInt();
    scanner.nextLine();  // consume newline

    Project project = null;
    if (choice == 1) {
      System.out.print("Enter Project ID: ");
      int projectId = scanner.nextInt();
      project = Project.findProjectByIdOrName(projectId, null);

    } else if (choice == 2) {
      System.out.print("Enter Project Name: ");
      String projectName = scanner.nextLine();
      project = Project.findProjectByIdOrName(null, projectName);
    } else {
      System.out.println("Invalid option. Returning to menu.");
      return;
    }

    // Display project details if found
    if (project != null) {
      System.out.println("Project ID: " + project.getProjectId());
      System.out.println("Project Name: " + project.getProjectName());
      System.out.println("Start Date: " + project.getStartDate());
      System.out.println("End Date: " + project.getEndDate());
      System.out.println("Budget: " + project.getBudget());
      System.out.println("Engineer ID: " + project.engineerId);
      System.out.println("Manager ID: " + project.managerId);
      System.out.println("Architect ID: " + project.architectId);
      System.out.println("Contractor ID: " + project.contractorId);
      System.out.println("Customer ID: " + project.customerId);
    } else {
      System.out.println("Project not found.");
    }
  }

  private static boolean doesIdExist(String tableName, String columnName, int id) {
    String sql = "SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      return rs.next();  // Returns true if the ID exists, false otherwise
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("\nPoise Project Management System");
      System.out.println("1. Add New Project");
      System.out.println("2. Update Existing Project");
      System.out.println("3. Delete Project");
      System.out.println("4. Delete Person");
      System.out.println("5. Show Incomplete Projects");
      System.out.println("6. Show Overdue Projects");
      System.out.println("7. Search for a Project");
      System.out.println("8. Exit");
      System.out.print("Select an option: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          addNewProject();
          break;
        case 2:
          updateProjectInDatabase();
          break;
        case 3:
          deleteProject();
          break;
        case 4:
          deletePerson();
          break;
        case 5:
          showIncompleteProjects();
          break;
        case 6:
          showOverdueProjects();
          break;
        case 7:
          searchProject();
          break;
        case 8:
          System.out.println("Exiting program.");
          scanner.close();
          return;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

}