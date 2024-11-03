// Person.java

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * Constructs a Person object with the specified details.
 *
 * @param id              The unique identifier for the person.
 * @param name            The name of the person.
 * @param telephoneNumber The telephone number of the person.
 * @param email           The email address of the person.
 * @param physicalAddress The physical address of the person.
 */
public class Person {
  private int id;
  private String name;
  private String telephoneNumber;
  private String email;
  private String physicalAddress;

  public Person(int id, String name, String telephoneNumber, String email, String physicalAddress) {
    this.id = id;
    this.name = name;
    this.telephoneNumber = telephoneNumber;
    this.email = email;
    this.physicalAddress = physicalAddress;
  }


  /**
   * Establishes and returns a connection to the MySQL database.
   *
   * @return A Connection object for the database.
   * @throws SQLException If a database access error occurs.
   */
  // Save person to the specified table in the database
  public void saveToDatabase(String tableName) {
    String sql = "INSERT INTO " + tableName + " (name, telephone_number, email, physical_address) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, this.name);
      pstmt.setString(2, this.telephoneNumber);
      pstmt.setString(3, this.email);
      pstmt.setString(4, this.physicalAddress);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Load person from database by ID
  public static Person loadFromDatabase(String tableName, int id) {
    String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        return new Person(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("telephone_number"),
                rs.getString("email"),
                rs.getString("physical_address")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * Deletes this person from the specified table in the database.
   *
   * @param tableName The name of the table from which to delete the person.
   */
  public void deleteFromDatabase(String tableName) {
    // Check if this person is referenced in the Projects table
    if (isPersonReferencedInProjects(tableName, this.id)) {
      System.out.println("This person is referenced by a project and cannot be deleted directly.");
      System.out.print("Do you want to set references to NULL and proceed with deletion? (yes/no): ");
      Scanner scanner = new Scanner(System.in);
      String confirmation = scanner.nextLine();
      if (!confirmation.equalsIgnoreCase("yes")) {
        System.out.println("Deletion canceled.");
        return;
      }
      setReferencesToNullInProjects(tableName, this.id);  // Set foreign keys to NULL
    }

    // Proceed with deletion
    String sql = "DELETE FROM " + tableName + " WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, this.id);
      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Person deleted successfully from " + tableName + ".");
      } else {
        System.out.println("Person deletion failed. Person with ID " + this.id + " may not exist in " + tableName + ".");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Helper method to check if a person is referenced in the Projects table
  private boolean isPersonReferencedInProjects(String tableName, int id) {
    String column = "";
    switch (tableName) {
      case "Architects":
        column = "architect_id";
        break;
      case "ProjectManagers":
        column = "manager_id";
        break;
      case "StructuralEngineers":
        column = "engineer_id";
        break;
      case "Contractors":
        column = "contractor_id";
        break;
      case "Customers":
        column = "customer_id";
        break;
      default:
        return false;
    }

    String sql = "SELECT 1 FROM Projects WHERE " + column + " = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      return rs.next();  // Returns true if the person is referenced
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Helper method to set foreign key references to NULL in Projects table
  private void setReferencesToNullInProjects(String tableName, int id) {
    String column = "";
    switch (tableName) {
      case "Architects":
        column = "architect_id";
        break;
      case "ProjectManagers":
        column = "manager_id";
        break;
      case "StructuralEngineers":
        column = "engineer_id";
        break;
      case "Contractors":
        column = "contractor_id";
        break;
      case "Customers":
        column = "customer_id";
        break;
      default:
        return;
    }

    String sql = "UPDATE Projects SET " + column + " = NULL WHERE " + column + " = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  // Getters for Person class fields
  public int getId() { return id; }
  public String getName() { return name; }
  public String getTelephoneNumber() { return telephoneNumber; }
  public String getEmail() { return email; }
  public String getPhysicalAddress() { return physicalAddress; }
}
