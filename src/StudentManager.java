import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;  


public class StudentManager {
    private static final Dotenv dotenv = Dotenv.load();  // Load .env file
    private static final String URL = dotenv.get("DB_URL");
    private static final String USERNAME = dotenv.get("DB_USERNAME");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");


    private Connection connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }


    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
    StudentManager manager = new StudentManager();
    Connection conn = manager.connectToDatabase();
    if (conn != null) {
        System.out.println("Database connection successful.");
        manager.closeConnection(conn);
    }
    }
}