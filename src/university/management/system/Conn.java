package university.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {
    public Connection connection;
    public Statement statement;

    public Conn() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql:///UniversityManagement",  // DB URL
                    "root",                                // Username
                    "Adithya@4546"                         // Password
            );

            // Create statement
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    // Close connection method
    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the database connection.");
            e.printStackTrace();
        }
    }
}
