package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import service.CartService;
import model.User;
import model.CartItem;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Receipt_Window extends JFrame {
    private JTextArea receiptArea;
    private JButton btnBackToShop, btnExit, btnDownload;

    private User currentUser;
    private CartService cartService;
    private String shippingAddress;
    private String orderId;

    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);

    public Receipt_Window(User user, String shippingAddress) {
        this.currentUser = user;
        this.cartService = new CartService();
        this.shippingAddress = shippingAddress;
        this.orderId = "ORD" + (System.currentTimeMillis() % 100000);

        setTitle("Purchase Receipt - Shopping Cart System");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 25));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("Payment Successful!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel thankYouLabel = new JLabel("Thank you for shopping with us!");
        thankYouLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        thankYouLabel.setForeground(TEXT_SECONDARY);
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(thankYouLabel);

        JPanel receiptCard = new JPanel(new BorderLayout());
        receiptCard.setBackground(CARD_BG);
        receiptCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 50, 50), 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel receiptTitle = new JLabel("Purchase Receipt");
        receiptTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        receiptTitle.setForeground(TEXT_PRIMARY);
        receiptTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        receiptArea.setEditable(false);
        receiptArea.setBackground(CARD_BG);
        receiptArea.setForeground(TEXT_PRIMARY);
        receiptArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 60, 60), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        receiptArea.setText(generateReceipt());

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        receiptCard.add(receiptTitle, BorderLayout.NORTH);
        receiptCard.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BACKGROUND);

        btnDownload = createModernButton("Download Receipt", SUCCESS);
        btnDownload.setPreferredSize(new Dimension(200, 50));
        btnDownload.addActionListener(e -> downloadReceipt());

        btnBackToShop = createModernButton("Back to Shop", PRIMARY);
        btnBackToShop.setPreferredSize(new Dimension(180, 50));
        btnBackToShop.addActionListener(e -> backToShop());

        btnExit = createModernButton("Exit", new Color(75, 85, 99));
        btnExit.setPreferredSize(new Dimension(130, 50));
        btnExit.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnDownload);
        buttonPanel.add(btnBackToShop);
        buttonPanel.add(btnExit);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(receiptCard, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void downloadReceipt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Receipt");
        String defaultFileName = String.format("Receipt_%s_%s.txt",
                orderId,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(receiptArea.getText());
                JOptionPane.showMessageDialog(this,
                        "Receipt saved successfully!\nLocation: " + fileToSave.getAbsolutePath(),
                        "Download Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving receipt: " + ex.getMessage(),
                        "Download Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String generateReceipt() {
        List<CartItem> cartItems = cartService.getCartItems(currentUser.getUserId());

        if (cartItems == null || cartItems.isEmpty()) {
            return "No items found in your cart.";
        }

        StringBuilder receipt = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        double tax = subtotal * 0.05;
        double shipping = subtotal > 1000 ? 0 : 50;
        double total = subtotal + tax + shipping;

        receipt.append("════════════════════════════════════════════════\n");
        receipt.append("              SHOPPING RECEIPT\n");
        receipt.append("════════════════════════════════════════════════\n\n");
        receipt.append(String.format("Order ID: #%s\n", orderId));
        receipt.append(String.format("Customer: %s\n", currentUser.getUsername()));
        receipt.append(String.format("Date: %s\n\n", df.format(now)));
        receipt.append("────────────────────────────────────────────────\n");
        receipt.append("ITEMS PURCHASED\n");
        receipt.append("────────────────────────────────────────────────\n");

        int count = 1;
        for (CartItem item : cartItems) {
            receipt.append(String.format("%d. %-30s\n", count++, item.getProductName()));
            receipt.append(String.format("   $%.2f x %d = $%.2f\n",
                    item.getPrice(), item.getQuantity(), item.getPrice() * item.getQuantity()));
        }

        receipt.append("────────────────────────────────────────────────\n");
        receipt.append("PAYMENT SUMMARY\n");
        receipt.append("────────────────────────────────────────────────\n");
        receipt.append(String.format("Subtotal:%35s$%.2f\n", "", subtotal));
        receipt.append(String.format("Tax (5%%):%35s$%.2f\n", "", tax));
        receipt.append(String.format("Shipping:%35s$%.2f\n", "", shipping));
        if (shipping == 0) receipt.append("     (Free shipping on orders over $1000)\n");
        receipt.append("════════════════════════════════════════════════\n");
        receipt.append(String.format("TOTAL PAID:%34s$%.2f\n", "", total));
        receipt.append("════════════════════════════════════════════════\n\n");
        receipt.append("Payment Method: Credit Card (****1234)\n");
        receipt.append("Status: APPROVED\n\n");
        receipt.append("Shipping Address:\n");
        receipt.append(shippingAddress + "\n\n");
        receipt.append("Expected Delivery: " + now.plusDays(5).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")) + "\n");
        receipt.append("────────────────────────────────────────────────\n");
        receipt.append("Thank you for your purchase!\n");
        receipt.append("════════════════════════════════════════════════\n");

        cartService.clearCart(currentUser.getUserId());
        return receipt.toString();
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { button.setBackground(bgColor.darker()); }
            @Override
            public void mouseExited(MouseEvent e) { button.setBackground(bgColor); }
        });
        return button;
    }

    private void backToShop() {
        ViewBrowse_Window viewBrowseWindow = new ViewBrowse_Window(currentUser);
        viewBrowseWindow.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Receipt_Window receiptWindow = new Receipt_Window(new User(), "123 Main Street\nCity, State 12345\nCountry");
            receiptWindow.setVisible(true);
        });
    }
}
