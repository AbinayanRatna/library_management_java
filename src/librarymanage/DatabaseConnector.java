package librarymanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage the database connection for the library management system.
 * This class provides a method to establish a connection to the MySQL database.
 */
public class DatabaseConnector {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/Library_DB"; // Database URL
    private static final String USER = "root";                                   // Database username
    private static final String PASSWORD = "";                                   // Database password

    /**
     * Establishes and returns a connection to the MySQL database.
     * Uses the database URL, username, and password to connect.
     * 
     * @return Connection object to interact with the database.
     * @throws SQLException if there is a database access error or invalid credentials.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}