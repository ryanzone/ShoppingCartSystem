package GUI;

import model.User;
import service.CartService;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Purchase_Window extends JFrame {
    private JTextField txtCardNumber, txtCardName, txtExpiryDate, txtCVV;
    private JComboBox<String> cmbPaymentMethod;
    private JTextArea txtShippingAddress;
    private JLabel lblOrderTotal;
    private JButton btnConfirmPurchase, btnCancel;

    private User currentUser;
    private CartService cartService;

    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    private static final Color INPUT_BG = new Color(40, 40, 40);

    public Purchase_Window(User user) {
        this.currentUser = user;
        this.cartService = new CartService();

        setTitle("Checkout - Shopping Cart System");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        initComponents();
        displayTotal();
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));


        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Complete your purchase");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(subtitleLabel);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND);


        JPanel summaryCard = new JPanel();
        summaryCard.setLayout(new BoxLayout(summaryCard, BoxLayout.Y_AXIS));
        summaryCard.setBackground(CARD_BG);
        summaryCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 50, 50), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        summaryCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel summaryTitle = new JLabel("Order Summary");
        summaryTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        summaryTitle.setForeground(TEXT_PRIMARY);
        summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel summaryContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        summaryContent.setBackground(CARD_BG);
        summaryContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSummaryText = new JLabel("Total Amount:");
        lblSummaryText.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSummaryText.setForeground(TEXT_SECONDARY);

        lblOrderTotal = new JLabel("$1,359.96");
        lblOrderTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblOrderTotal.setForeground(SUCCESS);

        summaryContent.add(lblSummaryText);
        summaryContent.add(lblOrderTotal);

        summaryCard.add(summaryTitle);
        summaryCard.add(Box.createRigidArea(new Dimension(0, 10)));
        summaryCard.add(summaryContent);


        JPanel shippingCard = new JPanel();
        shippingCard.setLayout(new BoxLayout(shippingCard, BoxLayout.Y_AXIS));
        shippingCard.setBackground(CARD_BG);
        shippingCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 50, 50), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        shippingCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JLabel shippingTitle = new JLabel("Shipping Address");
        shippingTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        shippingTitle.setForeground(TEXT_PRIMARY);
        shippingTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblShipping = new JLabel("Enter your shipping address:");
        lblShipping.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblShipping.setForeground(TEXT_SECONDARY);
        lblShipping.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtShippingAddress = new JTextArea(4, 30);
        txtShippingAddress.setLineWrap(true);
        txtShippingAddress.setWrapStyleWord(true);
        txtShippingAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtShippingAddress.setBackground(INPUT_BG);
        txtShippingAddress.setForeground(TEXT_PRIMARY);
        txtShippingAddress.setCaretColor(TEXT_PRIMARY);
        txtShippingAddress.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(TEXT_SECONDARY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        txtShippingAddress.setText("123 Main Street\nCity, State 12345\nCountry");

        JScrollPane shippingScroll = new JScrollPane(txtShippingAddress);
        shippingScroll.setBorder(null);
        shippingScroll.getViewport().setBackground(INPUT_BG);
        shippingScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        shippingCard.add(shippingTitle);
        shippingCard.add(Box.createRigidArea(new Dimension(0, 10)));
        shippingCard.add(lblShipping);
        shippingCard.add(Box.createRigidArea(new Dimension(0, 8)));
        shippingCard.add(shippingScroll);


        JPanel paymentCard = new JPanel();
        paymentCard.setLayout(new BoxLayout(paymentCard, BoxLayout.Y_AXIS));
        paymentCard.setBackground(CARD_BG);
        paymentCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 50, 50), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        paymentCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));

        JLabel paymentTitle = new JLabel("Payment Information");
        paymentTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        paymentTitle.setForeground(TEXT_PRIMARY);
        paymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        paymentCard.add(paymentTitle);
        paymentCard.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel paymentContent = new JPanel(new GridBagLayout());
        paymentContent.setBackground(CARD_BG);
        paymentContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);


        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblMethod = new JLabel("Payment Method");
        lblMethod.setFont(labelFont);
        lblMethod.setForeground(TEXT_SECONDARY);
        paymentContent.add(lblMethod, gbc);

        gbc.gridx = 1;
        cmbPaymentMethod = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "PayPal"});
        cmbPaymentMethod.setPreferredSize(new Dimension(300, 40));
        cmbPaymentMethod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPaymentMethod.setBackground(INPUT_BG);
        cmbPaymentMethod.setForeground(TEXT_SECONDARY);
        paymentContent.add(cmbPaymentMethod, gbc);

        // Card Number
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblCardNum = new JLabel("Card Number");
        lblCardNum.setFont(labelFont);
        lblCardNum.setForeground(TEXT_SECONDARY);
        paymentContent.add(lblCardNum, gbc);

        gbc.gridx = 1;
        txtCardNumber = createStyledTextField();
        paymentContent.add(txtCardNumber, gbc);

        // Cardholder Name
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblCardName = new JLabel("Cardholder Name");
        lblCardName.setFont(labelFont);
        lblCardName.setForeground(TEXT_SECONDARY);
        paymentContent.add(lblCardName, gbc);

        gbc.gridx = 1;
        txtCardName = createStyledTextField();
        paymentContent.add(txtCardName, gbc);

        // Expiry Date
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblExpiry = new JLabel("Expiry Date (MM/YY)");
        lblExpiry.setFont(labelFont);
        lblExpiry.setForeground(TEXT_SECONDARY);
        paymentContent.add(lblExpiry, gbc);

        gbc.gridx = 1;
        txtExpiryDate = createStyledTextField();
        paymentContent.add(txtExpiryDate, gbc);

        // CVV
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblCVV = new JLabel("CVV");
        lblCVV.setFont(labelFont);
        lblCVV.setForeground(TEXT_SECONDARY);
        paymentContent.add(lblCVV, gbc);

        gbc.gridx = 1;
        txtCVV = createStyledTextField();
        paymentContent.add(txtCVV, gbc);

        paymentCard.add(paymentContent);

        // Add all cards to content
        contentPanel.add(summaryCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(shippingCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(paymentCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BACKGROUND);

        btnConfirmPurchase = createModernButton("Confirm Purchase", SUCCESS);
        btnConfirmPurchase.setPreferredSize(new Dimension(200, 50));
        btnConfirmPurchase.addActionListener(e -> confirmPurchase());

        btnCancel = createModernButton("Cancel", DANGER);
        btnCancel.setPreferredSize(new Dimension(140, 50));
        btnCancel.addActionListener(e -> cancel());

        buttonPanel.add(btnConfirmPurchase);
        buttonPanel.add(btnCancel);

        // Add all to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(TEXT_SECONDARY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
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

    private void confirmPurchase() {
        String cardNumber = txtCardNumber.getText();
        String cardName = txtCardName.getText();
        String expiryDate = txtExpiryDate.getText();
        String cvv = txtCVV.getText();
        String address = txtShippingAddress.getText();

        if (cardNumber.isEmpty() || cardName.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all payment information!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Processing payment...",
                "Please Wait",
                JOptionPane.INFORMATION_MESSAGE);

        Receipt_Window receiptWindow = new Receipt_Window(currentUser, address);
        receiptWindow.setVisible(true);
        dispose();
    }

    private void cancel() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel the purchase?",
                "Cancel",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Cart_Window cartWindow = new Cart_Window(currentUser);
            cartWindow.setVisible(true);
            dispose();
        }
    }
    private void displayTotal() {
        double total = cartService.getCartTotal(currentUser.getUserId());
        lblOrderTotal.setText(String.format("$%.2f", total));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Purchase_Window purchaseWindow = new Purchase_Window(new User());
            purchaseWindow.setVisible(true);
        });
    }
}



