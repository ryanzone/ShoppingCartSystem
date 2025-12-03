package service;

import dao.CartDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CartService {
    private CartDAO cartDAO;
    private ProductDAO productDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
    }

    public boolean addToCart(int userId, String productId, int quantity) {
        // Get product
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            return false;
        }

        // Check stock
        if (product.getStock() < quantity) {
            return false;
        }

        // Create cart item
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        // Add to cart
        boolean success = cartDAO.insertCartItem(cartItem);

        if (success) {
            // Reduce stock
            int newStock = product.getStock() - quantity;
            productDAO.updateStock(productId, newStock);
            return true;
        }

        return false;
    }

    public List<CartItem> getCartItems(int userId) {
        return cartDAO.getCartByUserId(userId);
    }


    public boolean removeFromCart(int userId, String productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:shopping.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, productId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromCartById(int cartId) {
        // Get cart item to know product and quantity
        CartItem cartItem = cartDAO.getCartItemById(cartId);
        if (cartItem == null) return false;

        // Delete from cart
        boolean success = cartDAO.deleteCartItem(cartId);

        if (success) {
            // Restore stock
            Product product = productDAO.getProductById(cartItem.getProductId());
            if (product != null) {
                int newStock = product.getStock() + cartItem.getQuantity();
                productDAO.updateStock(cartItem.getProductId(), newStock);
            }
            return true;
        }

        return false;
    }

    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:shopping.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public double getCartTotal(int userId) {
        List<CartItem> cartItems = cartDAO.getCartByUserId(userId);
        double total = 0.0;

        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }

        return total;
    }
}