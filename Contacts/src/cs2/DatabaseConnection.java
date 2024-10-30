package cs2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://172.31.2.32:3306/contact";
    private static final String USER = "contact";
    private static final String PASSWORD = "K5NjXw5am4sXc7f5";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
