package GUI;

import model.CartItem;
import model.User;
import service.CartService;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Cart_Window extends JFrame {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private JButton btnRemove, btnClear, btnContinueShopping, btnProceedCheckout;

    private User currentUser;
    private CartService cartService;

    // Modern color palette
    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color WARNING = new Color(245, 158, 11);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    private static final Color HOVER_BG = new Color(40, 40, 40);

    public Cart_Window(User user) {
        this.currentUser = user;
        this.cartService = new CartService();

        setTitle("Shopping Cart System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        initComponents();
        loadCart();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("My Shopping Cart");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subtitleLabel = new JLabel("Review your items before checkout");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Table setup
        String[] columns = {"ID", "Product Name", "Category", "Unit Price", "Qty", "Subtotal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(tableModel);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.setRowHeight(50);
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cartTable.setBackground(CARD_BG);
        cartTable.setForeground(TEXT_PRIMARY);
        cartTable.setGridColor(new Color(50, 50, 50));
        cartTable.setSelectionBackground(HOVER_BG);
        cartTable.setSelectionForeground(TEXT_PRIMARY);
        cartTable.setShowGrid(true);
        cartTable.setIntercellSpacing(new Dimension(1, 1));

        JTableHeader header = cartTable.getTableHeader();
        header.setBackground(CARD_BG);
        header.setForeground(TEXT_SECONDARY);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY));

        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(CARD_BG);
        centerRenderer.setForeground(TEXT_PRIMARY);
        for (int i = 0; i < cartTable.getColumnCount(); i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        scrollPane.setBackground(CARD_BG);

        // Bottom section
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 20));
        bottomPanel.setBackground(BACKGROUND);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(CARD_BG);
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JPanel totalContentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        totalContentPanel.setBackground(CARD_BG);

        JLabel lblTotalText = new JLabel("Total Amount:");
        lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotalText.setForeground(TEXT_SECONDARY);

        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotal.setForeground(SUCCESS);

        totalContentPanel.add(lblTotalText);
        totalContentPanel.add(lblTotal);
        totalPanel.add(totalContentPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BACKGROUND);

        btnRemove = createModernButton("Remove Item", DANGER);
        btnClear = createModernButton("Clear Cart", WARNING);
        btnContinueShopping = createModernButton("Continue Shopping", PRIMARY);
        btnProceedCheckout = createModernButton("Proceed to Checkout", SUCCESS);

        btnRemove.addActionListener(e -> removeItem());
        btnClear.addActionListener(e -> clearCart());
        btnContinueShopping.addActionListener(e -> continueShopping());
        btnProceedCheckout.addActionListener(e -> proceedToCheckout());

        buttonPanel.add(btnRemove);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnContinueShopping);
        buttonPanel.add(btnProceedCheckout);

        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
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
        button.setPreferredSize(new Dimension(180, 45));

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

    private void removeItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the product ID from the selected row
        int productId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

        // Remove from database
        boolean success = cartService.removeFromCart(productId, currentUser.getUsername());

        if (success) {
            // Remove from table
            tableModel.removeRow(selectedRow);
            updateTotal();
            JOptionPane.showMessageDialog(this, "Item removed from cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove item!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearCart() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Cart is already empty!", "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all items?", "Clear Cart", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Clear from database
            boolean success = cartService.clearCart(currentUser.getUserId());

            if (success) {
                tableModel.setRowCount(0);
                updateTotal();
                JOptionPane.showMessageDialog(this, "Cart cleared successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to clear cart!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTotal() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String subtotalStr = tableModel.getValueAt(i, 5).toString();
            subtotalStr = subtotalStr.replace("$", "").replace(",", "");
            total += Double.parseDouble(subtotalStr);
        }
        lblTotal.setText(String.format("$%.2f", total));
    }

    private void continueShopping() {
        ViewBrowse_Window viewBrowseWindow = new ViewBrowse_Window(currentUser);
        viewBrowseWindow.setVisible(true);
        dispose();
    }

    private void proceedToCheckout() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Purchase_Window purchaseWindow = new Purchase_Window(currentUser);
        purchaseWindow.setVisible(true);
        dispose();
    }

    private void loadCart() {
        List<CartItem> cartItems = cartService.getCartItems(currentUser.getUserId());
        tableModel.setRowCount(0);

        for (CartItem item : cartItems) {
            // Calculate subtotal = price * quantity
            double subtotal = item.getPrice() * item.getQuantity();

            tableModel.addRow(new Object[]{
                    item.getProductId(),
                    item.getProductName(),
                    item.getCategory(),
                    String.format("$%.2f", item.getPrice()),
                    item.getQuantity(),
                    String.format("$%.2f", subtotal)
            });
        }
        updateTotal();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Cart_Window cartWindow = new Cart_Window(new User());
            cartWindow.setVisible(true);
        });
    }
}