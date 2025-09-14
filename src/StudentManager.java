import java.sql.*;
import java.util.Scanner;

public class StudentManager {
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:mysql://mysql:3306/student_db";
    private static final String USERNAME = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "rootpassword";

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

    private void viewAllStudents() {
        String sql = "SELECT * FROM students";
        Connection conn = connectToDatabase();
        if (conn == null) return;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Email: %s, Age: %d, Enrollment Date: %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getDate("enrollment_date"));
            }
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private void addStudent(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter age: ");
        int age;
        while (true) {
            String input = scanner.nextLine();
            try {
                age = Integer.parseInt(input);
                if (age < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid age. Enter a positive number: ");
            }
        }
        System.out.print("Enter enrollment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String sql = "INSERT INTO students (name, email, age, enrollment_date) VALUES (?, ?, ?, ?)";
        Connection conn = connectToDatabase();
        if (conn == null) return;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, age);
            stmt.setDate(4, java.sql.Date.valueOf(date));
            stmt.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new name (leave blank to skip): ");
        String name = scanner.nextLine();
        System.out.print("Enter new email (leave blank to skip): ");
        String email = scanner.nextLine();
        System.out.print("Enter new age (leave blank to skip): ");
        String ageInput = scanner.nextLine();
        System.out.print("Enter new enrollment date (YYYY-MM-DD, leave blank to skip): ");
        String date = scanner.nextLine();

        StringBuilder sql = new StringBuilder("UPDATE students SET ");
        boolean first = true;
        if (!name.isEmpty()) { sql.append("name = ?"); first = false; }
        if (!email.isEmpty()) { sql.append(first ? "" : ", ").append("email = ?"); first = false; }
        if (!ageInput.isEmpty()) { sql.append(first ? "" : ", ").append("age = ?"); first = false; }
        if (!date.isEmpty()) { sql.append(first ? "" : ", ").append("enrollment_date = ?"); }
        sql.append(" WHERE id = ?");

        Connection conn = connectToDatabase();
        if (conn == null) return;

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (!name.isEmpty()) stmt.setString(index++, name);
            if (!email.isEmpty()) stmt.setString(index++, email);
            if (!ageInput.isEmpty()) stmt.setInt(index++, Integer.parseInt(ageInput));
            if (!date.isEmpty()) stmt.setDate(index++, java.sql.Date.valueOf(date));
            stmt.setInt(index, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Student updated successfully!");
            else System.out.println("No student found with given ID.");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM students WHERE id = ?";
        Connection conn = connectToDatabase();
        if (conn == null) return;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Student deleted successfully!");
            else System.out.println("No student found with given ID.");
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private void searchStudentById(Scanner scanner) {
        System.out.print("Enter student ID to search: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "SELECT * FROM students WHERE id = ?";
        Connection conn = connectToDatabase();
        if (conn == null) return;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("ID: %d, Name: %s, Email: %s, Age: %d, Enrollment Date: %s\n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getDate("enrollment_date"));
                } else {
                    System.out.println("No student found with given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            manager.displayMenu();
            String input = scanner.nextLine();
            System.out.println("Option " + input + " selected");
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        manager.viewAllStudents();
                        break;
                    case 2:
                        manager.addStudent(scanner);
                        break;
                    case 3:
                        manager.updateStudent(scanner);
                        break;
                    case 4:
                        manager.deleteStudent(scanner);
                        break;
                    case 5:
                        manager.searchStudentById(scanner);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}