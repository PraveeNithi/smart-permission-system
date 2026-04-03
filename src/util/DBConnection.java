package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:smart_permission.db";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the SQLite JDBC Driver
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        
        // Auto-initialize the table if it's the first time
        initializeDatabase(conn);
        
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS permission_requests (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "employee_id INTEGER, " +
                                "employee_name TEXT, " +
                                "reason TEXT, " +
                                "request_date DATE, " +
                                "number_of_days INTEGER, " +
                                "leave_type TEXT, " +
                                "past_leave_count INTEGER, " +
                                "status TEXT, " +
                                "decision_reason TEXT, " +
                                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                                ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}
