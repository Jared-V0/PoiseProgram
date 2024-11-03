// DatabaseConnection.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * This class provides a connection to the MySQL database for the Poise project management system.
 */
public class DatabaseConnection {
  private static final String URL = "jdbc:mysql://localhost:3306/PoisePMS";
  private static final String USER = "root";
  private static final String PASSWORD = "yassuoistrash1@";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
