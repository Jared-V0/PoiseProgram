// Project.java

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Project {
  // Fields for project details and associated IDs


  public int projectId;
  /**
   * Unique identifier for the project.
   */
  public String projectName;

  /**
   * Name of the project.
   */
  public String startDate;

  /**
   * Start date of the project in YYYY-MM-DD format.
   */
  public String endDate;
  /**
   * End date of the project in YYYY-MM-DD format.
   */
  public double budget;

  /**
   * Budget allocated for the project.
   */
  public int engineerId;

  /**
   * engineer ID number
   */
  public int managerId;

  /**
   * manager ID number
   */
  public int architectId;
  /**
   * architect ID number
   */
  public int contractorId;
  /**
   * contractor ID number
   */
  public int customerId;

  /**
   * customer ID number


  /**
   * Constructs a Project object with the specified details.
   *
   * @param projectId The unique identifier for the project.
   * @param projectName The name of the project.
   * @param startDate The start date of the project.
   * @param endDate The end date of the project.
   * @param budget The budget allocated for the project.
   * @param engineerId The ID of the assigned engineer.
   * @param managerId The ID of the assigned project manager.
   * @param architectId The ID of the assigned architect.
   * @param contractorId The ID of the assigned contractor.
   * @param customerId The ID of the customer.
   */

  // Constructor to initialize a Project object
  public Project(int projectId, String projectName, String startDate, String endDate, double budget, int engineerId, int managerId, int architectId, int contractorId, int customerId) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.budget = budget;
    this.engineerId = engineerId;
    this.managerId = managerId;
    this.architectId = architectId;
    this.contractorId = contractorId;
    this.customerId = customerId;
  }

  // Method to save a project to the database
  public void saveToDatabase() {
    // SQL query to insert a new project record
    String sql = "INSERT INTO Projects (project_name, start_date, end_date, budget, engineer_id, manager_id, architect_id, contractor_id, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      // Set project details as parameters in the query
      pstmt.setString(1, this.projectName);
      pstmt.setString(2, this.startDate);
      pstmt.setString(3, this.endDate);
      pstmt.setDouble(4, this.budget);
      pstmt.setInt(5, this.engineerId);
      pstmt.setInt(6, this.managerId);
      pstmt.setInt(7, this.architectId);
      pstmt.setInt(8, this.contractorId);
      pstmt.setInt(9, this.customerId);
      // Execute the insert query
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a project from the database based on the provided project ID.
   *
   * @param projectId the ID of the project to load
   * @return a Project object if found, otherwise null
   */

  // Method to load a project from the database by its ID
  public static Project loadFromDatabase(int projectId) {
    String sql = "SELECT * FROM Projects WHERE project_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, projectId); // Set the project ID as a parameter
      ResultSet rs = pstmt.executeQuery(); // Execute the query
      if (rs.next()) { // If a project with the given ID is found, create and return a Project object
        return new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getDouble("budget"),
                rs.getInt("engineer_id"),
                rs.getInt("manager_id"),
                rs.getInt("architect_id"),
                rs.getInt("contractor_id"),
                rs.getInt("customer_id")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null; // Return null if no project is found
  }

  // Method to update an existing project in the database
  public void updateInDatabase() {
    // SQL query to update a project record based on its ID
    String sql = "UPDATE Projects SET project_name = ?, start_date = ?, end_date = ?, budget = ?, engineer_id = ?, manager_id = ?, architect_id = ?, contractor_id = ?, customer_id = ? WHERE project_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      // Set updated project details as parameters in the query
      pstmt.setString(1, this.projectName);
      pstmt.setString(2, this.startDate);
      pstmt.setString(3, this.endDate);
      pstmt.setDouble(4, this.budget);
      pstmt.setInt(5, this.engineerId);
      pstmt.setInt(6, this.managerId);
      pstmt.setInt(7, this.architectId);
      pstmt.setInt(8, this.contractorId);
      pstmt.setInt(9, this.customerId);
      pstmt.setInt(10, this.projectId); // Specify the project to update by its ID

      int rowsAffected = pstmt.executeUpdate(); // Execute the update query
      if (rowsAffected > 0) {
        System.out.println("Project updated successfully.");
      } else {
        System.out.println("Project update failed. Project with ID " + this.projectId + " may not exist.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Method to delete a project from the database
  public void deleteFromDatabase() {
    String sql = "DELETE FROM Projects WHERE project_id = ?"; // SQL query to delete a project record by ID
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, this.projectId); // Set the project ID as a parameter
      int rowsAffected = pstmt.executeUpdate(); // Execute the delete query
      if (rowsAffected > 0) {
        System.out.println("Project deleted successfully.");
      } else {
        System.out.println("Project deletion failed. Project with ID " + this.projectId + " may not exist.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Finds all projects that have not yet been completed.
   *
   * @return A list of incomplete projects.
   */
  // Method to find all projects that still need to be completed (i.e., their end date is in the future)
  public static List<Project> findIncompleteProjects() {
    List<Project> incompleteProjects = new ArrayList<>(); // List to hold incomplete projects
    String sql = "SELECT * FROM Projects WHERE end_date > CURDATE()"; // Query to find projects with a future end date

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      // Iterate through results and add each project to the list
      while (rs.next()) {
        Project project = new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getDouble("budget"),
                rs.getInt("engineer_id"),
                rs.getInt("manager_id"),
                rs.getInt("architect_id"),
                rs.getInt("contractor_id"),
                rs.getInt("customer_id")
        );
        incompleteProjects.add(project);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return incompleteProjects; // Return the list of incomplete projects
  }

  // Method to find all projects that are overdue (i.e., their end date is in the past)
  public static List<Project> findOverdueProjects() {
    List<Project> overdueProjects = new ArrayList<>(); // List to hold overdue projects
    String sql = "SELECT * FROM Projects WHERE end_date < CURDATE()"; // Query to find projects with a past end date

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      // Iterate through results and add each project to the list
      while (rs.next()) {
        Project project = new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getDouble("budget"),
                rs.getInt("engineer_id"),
                rs.getInt("manager_id"),
                rs.getInt("architect_id"),
                rs.getInt("contractor_id"),
                rs.getInt("customer_id")
        );
        overdueProjects.add(project);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return overdueProjects; // Return the list of overdue projects
  }

  // Method to find a project by its ID or name
  public static Project findProjectByIdOrName(Integer projectId, String projectName) {
    String sql;
    Project project = null;

    try (Connection conn = DatabaseConnection.getConnection();
         // Prepare SQL query based on whether we're searching by ID or name
         PreparedStatement pstmt = (projectId != null) ?
                 conn.prepareStatement("SELECT * FROM Projects WHERE project_id = ?") :
                 conn.prepareStatement("SELECT * FROM Projects WHERE project_name = ?")) {

      // Set parameter based on search type (by ID or by name)
      if (projectId != null) {
        pstmt.setInt(1, projectId);
      } else {
        pstmt.setString(1, projectName);
      }

      ResultSet rs = pstmt.executeQuery(); // Execute the query
      if (rs.next()) { // If a matching project is found, create and return a Project object
        project = new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getDouble("budget"),
                rs.getInt("engineer_id"),
                rs.getInt("manager_id"),
                rs.getInt("architect_id"),
                rs.getInt("contractor_id"),
                rs.getInt("customer_id")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return project; // Return the project if found, otherwise return null
  }

  // Getters for Project class fields
  public int getProjectId() {

    return projectId;

  }
  public String getProjectName() {

    return projectName;

  }
  public String getStartDate() {

    return startDate;

  }
  public String getEndDate() {

    return endDate;

  }
  public double getBudget() {

    return budget;

  }
}
