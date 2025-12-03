package GUI;

import java.sql.*;
import java.util.Scanner;

public class database {
    private static final String DB_URL = "jdbc:sqlite:shopping.db";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("      ADMIN PRODUCT MANAGEMENT CONSOLE");
        System.out.println("==============================================\n");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    System.out.println("\nExiting... Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice! Try again.");
            }
        }
    }

    private static void addProduct() {
        System.out.println("\n--- ADD NEW PRODUCT ---");

        System.out.print("Enter Product ID (e.g., P021): ");
        String productId = scanner.nextLine();

        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter Category (Electronics/Furniture/Accessories/Clothing/Books): ");
        String category = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Stock Quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO products (product_id, product_name, category, price, stock, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productId);
            pstmt.setString(2, productName);
            pstmt.setString(3, category);
            pstmt.setDouble(4, price);
            pstmt.setInt(5, stock);
            pstmt.setString(6, description);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n✓ Product added successfully!");
            } else {
                System.out.println("\nFailed to add product!");
            }

        } catch (SQLException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void viewProducts() {
        System.out.println("\n--- ALL PRODUCTS ---");
        String sql = "SELECT * FROM products ORDER BY product_id";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n" + String.format("%-8s %-25s %-15s %-10s %-8s %-30s",
                    "ID", "Name", "Category", "Price", "Stock", "Description"));
            System.out.println("=".repeat(110));

            while (rs.next()) {
                System.out.println(String.format("%-8s %-25s %-15s $%-9.2f %-8d %-30s",
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("description")));
            }

        } catch (SQLException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void updateProduct() {
        System.out.println("\n--- UPDATE PRODUCT ---");

        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine();

        System.out.print("Enter new Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter new Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter new Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter new Description: ");
        String description = scanner.nextLine();

        String sql = "UPDATE products SET product_name = ?, category = ?, price = ?, stock = ?, description = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productName);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stock);
            pstmt.setString(5, description);
            pstmt.setString(6, productId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n✓ Product updated successfully!");
            } else {
                System.out.println("\nProduct not found!");
            }

        } catch (SQLException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        System.out.println("\n--- DELETE PRODUCT ---");

        System.out.print("Enter Product ID to delete: ");
        String productId = scanner.nextLine();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("\nDeletion cancelled!");
            return;
        }

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n✓ Product deleted successfully!");
            } else {
                System.out.println("\nProduct not found!");
            }

        } catch (SQLException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}