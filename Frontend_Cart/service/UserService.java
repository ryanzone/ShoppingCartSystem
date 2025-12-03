package service;

import dao.UserDAO;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Authenticate user login
     */
    public User login(String username, String password) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        // Get user from database
        User user = userDAO.findByUsername(username);

        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    /**
     * Authenticate user (alias for login)
     */
    public User authenticate(String username, String password) {
        return login(username, password);
    }

    /**
     * Register new user
     */
    public boolean signup(User user) {
        // Validate input
        if (!validateUser(user)) {
            return false;
        }

        // Check if username already exists
        if (userDAO.findByUsername(user.getUsername()) != null) {
            return false;
        }

        // Check if email already exists
        if (userDAO.findByEmail(user.getEmail()) != null) {
            return false;
        }

        // Insert user
        return userDAO.insertUser(user);
    }

    /**
     * Delete user account
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

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

    /**
     * Update user password
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:shopping.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validate user object
     */
    private boolean validateUser(User user) {
        if (user == null) return false;
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) return false;
        if (user.getEmail() == null || !user.getEmail().contains("@")) return false;
        if (user.getPassword() == null || user.getPassword().length() < 6) return false;
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) return false;
        return true;
    }
}