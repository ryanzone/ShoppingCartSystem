package GUI;

import java.sql.*;

public class PrintUsers {
    // Database URL
    private static final String DB_URL = "jdbc:sqlite:shopping.db";

    public static void main(String[] args) {
        printAllUsers();
    }

    /**
     * Print all users and products from their tables
     */
    public static void printAllUsers() {
        String sql_users = "SELECT * FROM users";
        String sql_products = "SELECT * FROM products";

        // ===== USERS TABLE =====
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_users)) {

            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("                            USERS TABLE                                    ");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.printf("%-10s %-15s %-25s %-15s %-15s%n",
                    "user_id", "username", "email", "phone", "created_at");
            System.out.println("───────────────────────────────────────────────────────────────────────────");

            int count = 0;
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String created_at = rs.getString("created_at");

                System.out.printf("%-10d %-15s %-25s %-15s %-15s%n",
                        user_id, username, email, phone, created_at);

                count++;
            }

            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Total Users: " + count);
            System.out.println("═══════════════════════════════════════════════════════════════════════════");

        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }

        // ===== PRODUCTS TABLE =====
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_products)) {

            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("                            PRODUCTS TABLE                                 ");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.printf("%-10s %-20s %-15s %-10s %-10s %-30s%n",
                    "product_id", "product_name", "category", "price", "stock", "description");
            System.out.println("───────────────────────────────────────────────────────────────────────────");

            int count = 0;
            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String product_name = rs.getString("product_name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String description = rs.getString("description");

                System.out.printf("%-10d %-20s %-15s %-10.2f %-10d %-30s%n",
                        product_id, product_name, category, price, stock, description);

                count++;
            }

            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Total Products: " + count);
            System.out.println("═══════════════════════════════════════════════════════════════════════════");

        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }
}
