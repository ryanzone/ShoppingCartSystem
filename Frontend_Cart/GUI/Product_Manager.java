package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Product_Manager extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:shopping.db";
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtCategory, txtPrice, txtStock;
    private JTextArea txtDescription;

    private static final Color BG = new Color(18, 18, 18);
    private static final Color CARD = new Color(30, 30, 30);
    private static final Color TEXT = new Color(255, 255, 255);
    private static final Color SECONDARY = new Color(156, 163, 175);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);

    public Product_Manager() {
        setTitle("Admin Product Management - Shopping Cart System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadProducts();
    }

    private void initComponents() {
        // Header
        JLabel header = new JLabel("ADMIN PRODUCT MANAGEMENT", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(TEXT);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Price", "Stock", "Description"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setBackground(CARD);
        table.setForeground(TEXT);
        table.setGridColor(Color.GRAY);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(CARD);
        add(scrollPane, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(CARD);
        formPanel.setBorder(new LineBorder(new Color(60, 60, 60), 1, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("Product ID:");
        lblId.setForeground(SECONDARY);
        lblId.setFont(labelFont);
        formPanel.add(lblId, gbc);

        gbc.gridx = 1;
        txtId = createTextField();
        formPanel.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblName = new JLabel("Name:");
        lblName.setForeground(SECONDARY);
        lblName.setFont(labelFont);
        formPanel.add(lblName, gbc);

        gbc.gridx = 1;
        txtName = createTextField();
        formPanel.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblCategory = new JLabel("Category:");
        lblCategory.setForeground(SECONDARY);
        lblCategory.setFont(labelFont);
        formPanel.add(lblCategory, gbc);

        gbc.gridx = 1;
        txtCategory = createTextField();
        formPanel.add(txtCategory, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setForeground(SECONDARY);
        lblPrice.setFont(labelFont);
        formPanel.add(lblPrice, gbc);

        gbc.gridx = 1;
        txtPrice = createTextField();
        formPanel.add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setForeground(SECONDARY);
        lblStock.setFont(labelFont);
        formPanel.add(lblStock, gbc);

        gbc.gridx = 1;
        txtStock = createTextField();
        formPanel.add(txtStock, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setForeground(SECONDARY);
        lblDescription.setFont(labelFont);
        formPanel.add(lblDescription, gbc);

        gbc.gridx = 1;
        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setBackground(BG);
        txtDescription.setForeground(TEXT);
        formPanel.add(new JScrollPane(txtDescription), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BG);

        JButton btnAdd = createButton("Add Product", SUCCESS);
        btnAdd.addActionListener(e -> addProduct());

        JButton btnUpdate = createButton("Update Product", PRIMARY);
        btnUpdate.addActionListener(e -> updateProduct());

        JButton btnDelete = createButton("Delete Product", DANGER);
        btnDelete.addActionListener(e -> deleteProduct());

        JButton btnRefresh = createButton("Refresh", new Color(120, 120, 120));
        btnRefresh.addActionListener(e -> loadProducts());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG);
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(BG);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setBorder(new LineBorder(new Color(60, 60, 60), 1, true));
        return field;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(color.darker()); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        return btn;
    }

    private void loadProducts() {
        model.setRowCount(0);
        String sql = "SELECT * FROM products ORDER BY product_id";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("description")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProduct() {
        String id = txtId.getText();
        String name = txtName.getText();
        String category = txtCategory.getText();
        String priceStr = txtPrice.getText();
        String stockStr = txtStock.getText();
        String desc = txtDescription.getText();

        if (id.isEmpty() || name.isEmpty() || category.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO products (product_id, product_name, category, price, stock, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setDouble(4, Double.parseDouble(priceStr));
            pstmt.setInt(5, Integer.parseInt(stockStr));
            pstmt.setString(6, desc);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadProducts();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding product:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        String id = txtId.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Product ID to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE products SET product_name=?, category=?, price=?, stock=?, description=? WHERE product_id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, txtName.getText());
            pstmt.setString(2, txtCategory.getText());
            pstmt.setDouble(3, Double.parseDouble(txtPrice.getText()));
            pstmt.setInt(4, Integer.parseInt(txtStock.getText()));
            pstmt.setString(5, txtDescription.getText());
            pstmt.setString(6, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Product not found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating product:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        String id = txtId.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Product ID to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete product " + id + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM products WHERE product_id=?";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, id);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(this, "Product not found!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting product:\n" + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Product_Manager().setVisible(true));
    }
}
