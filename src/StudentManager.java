import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;  
import java.util.Scanner;

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

    private void displayMenu() {
        System.out.println("\n===== Student Management System =====");
        System.out.println("1. View All Students");
        System.out.println("2. Add Student");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student by ID");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }


    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            manager.displayMenu();
            String input = scanner.nextLine();
            System.out.println("Option " + input + " selected");
            }
        }
}