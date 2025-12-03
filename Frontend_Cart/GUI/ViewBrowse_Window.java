package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import service.ProductService;
import service.CartService;
import model.User;
import model.Product;

public class ViewBrowse_Window extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cmbCategory;
    private JSpinner spinnerQuantity;
    private JButton btnAddToCart, btnViewCart, btnLogout;

    private User currentUser;

    // Services
    private final ProductService productService;
    private final CartService cartService;

    // Modern color palette (matching Main_Window)
    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    private static final Color INPUT_BG = new Color(40, 40, 40);
    private static final Color HOVER_BG = new Color(50, 50, 50);

    public ViewBrowse_Window(User user) {
        this.currentUser = user;
        this.productService = new ProductService();
        this.cartService = new CartService();

        setTitle("Browse Products - Shopping Cart System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        initComponents();

        // Load products after components are initialized
        loadProducts();
    }

    private void initComponents() {
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("Browse Products");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);

        JPanel headerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        headerButtonPanel.setBackground(BACKGROUND);

        btnViewCart = createModernButton("View Cart", SUCCESS);
        btnViewCart.setPreferredSize(new Dimension(130, 45));
        btnViewCart.addActionListener(e -> openCartWindow());

        btnLogout = createModernButton("Logout", DANGER);
        btnLogout.setPreferredSize(new Dimension(110, 45));
        btnLogout.addActionListener(e -> logout());

        headerButtonPanel.add(btnViewCart);
        headerButtonPanel.add(btnLogout);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(headerButtonPanel, BorderLayout.EAST);

        // Content Card
        JPanel contentCard = new JPanel(new BorderLayout(0, 15));
        contentCard.setBackground(CARD_BG);
        contentCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HOVER_BG, 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchPanel.setBackground(CARD_BG);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setForeground(TEXT_SECONDARY);
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchPanel.add(lblSearch);

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBackground(INPUT_BG);
        txtSearch.setForeground(TEXT_PRIMARY);
        txtSearch.setCaretColor(TEXT_PRIMARY);
        txtSearch.setPreferredSize(new Dimension(250, 38));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_SECONDARY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(txtSearch);

        JLabel lblCategory = new JLabel("Category:");
        lblCategory.setForeground(TEXT_SECONDARY);
        lblCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchPanel.add(lblCategory);

        String[] categories = {"All Categories", "Electronics", "Furniture", "Accessories", "Clothing", "Books"};
        cmbCategory = new JComboBox<>(categories);
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCategory.setBackground(INPUT_BG);
        cmbCategory.setForeground(TEXT_SECONDARY);
        cmbCategory.setPreferredSize(new Dimension(160, 38));
        searchPanel.add(cmbCategory);

        JButton btnSearch = createModernButton("Search", PRIMARY);
        btnSearch.setPreferredSize(new Dimension(100, 38));
        btnSearch.addActionListener(e -> filterProducts());
        searchPanel.add(btnSearch);

        // Product Table
        String[] columns = {"ID", "Product Name", "Category", "Price", "Stock", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(45);
        productTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        productTable.setForeground(TEXT_PRIMARY);
        productTable.setBackground(INPUT_BG);
        productTable.setGridColor(HOVER_BG);
        productTable.setSelectionBackground(HOVER_BG);
        productTable.setSelectionForeground(TEXT_SECONDARY);
        productTable.setShowGrid(true);

        // Custom header rendering
        JTableHeader header = productTable.getTableHeader();
        header.setBackground(CARD_BG);
        header.setForeground(TEXT_SECONDARY);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY));
        header.setOpaque(true);

        // Custom cell renderer to ensure colors are applied
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(HOVER_BG);
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground(INPUT_BG);
                    c.setForeground(TEXT_PRIMARY);
                }
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        };

        // Apply renderer to all columns
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(HOVER_BG, 1));
        scrollPane.getViewport().setBackground(INPUT_BG);
        scrollPane.setBackground(CARD_BG);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setBackground(CARD_BG);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setForeground(TEXT_SECONDARY);
        lblQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinnerQuantity.setPreferredSize(new Dimension(80, 38));
        spinnerQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spinnerQuantity.getEditor()).getTextField().setBackground(INPUT_BG);
        ((JSpinner.DefaultEditor) spinnerQuantity.getEditor()).getTextField().setForeground(TEXT_PRIMARY);

        btnAddToCart = createModernButton("Add to Cart", SUCCESS);
        btnAddToCart.setPreferredSize(new Dimension(150, 45));
        btnAddToCart.addActionListener(e -> addToCart());

        bottomPanel.add(lblQuantity);
        bottomPanel.add(spinnerQuantity);
        bottomPanel.add(btnAddToCart);

        // Add to content card
        contentCard.add(searchPanel, BorderLayout.NORTH);
        contentCard.add(scrollPane, BorderLayout.CENTER);
        contentCard.add(bottomPanel, BorderLayout.SOUTH);

        // Add to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentCard, BorderLayout.CENTER);

        add(mainPanel);
    }

    // NEW METHOD: Load all products from the database
    private void loadProducts() {
        List<Product> products = productService.getAllProducts();
        populateTable(products);
    }

    // NEW METHOD: Populate table with product list
    private void populateTable(List<Product> products) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add each product as a row
        for (Product product : products) {
            Object[] row = {
                    product.getProductId(),
                    product.getProductName(),
                    product.getCategory(),
                    String.format("$%.2f", product.getPrice()),
                    product.getStock(),
                    product.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    // NEW METHOD: Filter products based on search criteria
    private void filterProducts() {
        String searchText = txtSearch.getText().trim().toLowerCase();
        String selectedCategory = (String) cmbCategory.getSelectedItem();

        List<Product> allProducts = productService.getAllProducts();

        // Filter products
        List<Product> filteredProducts = allProducts.stream()
                .filter(p -> {
                    // Filter by search text (name or description)
                    boolean matchesSearch = searchText.isEmpty() ||
                            p.getProductName().toLowerCase().contains(searchText) ||
                            p.getDescription().toLowerCase().contains(searchText);

                    // Filter by category
                    boolean matchesCategory = selectedCategory.equals("All Categories") ||
                            p.getCategory().equals(selectedCategory);

                    return matchesSearch && matchesCategory;
                })
                .toList();

        populateTable(filteredProducts);
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product first!",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get product ID from the selected row
        String productIdStr = tableModel.getValueAt(selectedRow, 0).toString();
        String productName = tableModel.getValueAt(selectedRow, 1).toString();
        int quantity = (Integer) spinnerQuantity.getValue();

        // Get the actual product object to check stock
        Product product = productService.getProductById(productIdStr);

        if (product == null) {
            JOptionPane.showMessageDialog(this,
                    "Product not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if requested quantity is available
        if (quantity > product.getStock()) {
            JOptionPane.showMessageDialog(this,
                    "Insufficient stock! Only " + product.getStock() + " items available.",
                    "Stock Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Add to cart using CartService
        boolean success = cartService.addToCart(currentUser.getUserId(), productIdStr, quantity);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    productName + " (Qty: " + quantity + ") added to cart!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Reset quantity spinner
            spinnerQuantity.setValue(1);

            // Don't refresh the table - keep it as is
            // The stock will be validated again when adding to cart next time
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add product to cart.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openCartWindow() {
        Cart_Window cartWindow = new Cart_Window(currentUser);
        cartWindow.setVisible(true);
        dispose();
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Main_Window mainWindow = new Main_Window();
            mainWindow.setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ViewBrowse_Window window = new ViewBrowse_Window(new User());
            window.setVisible(true);
        });
    }
}