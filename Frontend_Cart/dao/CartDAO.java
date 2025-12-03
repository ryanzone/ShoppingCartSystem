package dao;

import model.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    /**
     * Insert item into cart
     */
    public boolean insertCartItem(CartItem item) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, item.getUserId());
            pstmt.setString(2, item.getProductId());
            pstmt.setInt(3, item.getQuantity());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting cart item: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get cart items for a user (with product details)
     */
    public List<CartItem> getCartByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.cart_id, c.user_id, c.product_id, c.quantity, c.added_at, " +
                "p.product_name, p.category, p.price " +
                "FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartId(rs.getInt("cart_id"));
                item.setUserId(rs.getInt("user_id"));
                item.setProductId(rs.getString("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setAddedAt(rs.getString("added_at"));
                cartItems.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error getting cart items: " + e.getMessage());
        }

        return cartItems;
    }

    /**
     * Get single cart item by ID
     */
    public CartItem getCartItemById(int cartId) {
        String sql = "SELECT c.*, p.product_name, p.category, p.price " +
                "FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.cart_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cartId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                CartItem item = new CartItem();
                item.setCartId(rs.getInt("cart_id"));
                item.setUserId(rs.getInt("user_id"));
                item.setProductId(rs.getString("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                return item;
            }

        } catch (SQLException e) {
            System.out.println("Error getting cart item: " + e.getMessage());
        }

        return null;
    }

    /**
     * Delete cart item
     */
    public boolean deleteCartItem(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cartId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting cart item: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clear all cart items for a user
     */
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error clearing cart: " + e.getMessage());
            return false;
        }
    }
}