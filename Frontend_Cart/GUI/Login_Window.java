package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import service.UserService;
import service.CartService;
import model.User;

public class Login_Window extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnBack;
    private JButton btnDeleteAccount;
    private JCheckBox chkRememberMe;

    // Service Layer
    private UserService userService;
    private CartService cartService;

    // Colors
    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color WARNING = new Color(245, 158, 11);

    // Fonts
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_TEXT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_CHECK = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_LINK = new Font("Segoe UI", Font.PLAIN, 11);

    public Login_Window() {
        setTitle("Login - Shopping Cart System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(BACKGROUND);

        // Initialize services
        userService = new UserService();
        cartService = new CartService();

        initComponents();
    }

    private void initComponents() {
        // Card Panel (centered)
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY, 2, true),
                BorderFactory.createEmptyBorder(20, 30, 30, 30)
        ));
        cardPanel.setPreferredSize(new Dimension(380, 400));

        // Title Label
        JLabel titleLabel = new JLabel("Login to Your Account", SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(TEXT_PRIMARY);
        lblUsername.setFont(FONT_LABEL);
        formPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        txtUsername.setFont(FONT_TEXT);
        txtUsername.setBackground(BACKGROUND);
        txtUsername.setForeground(TEXT_PRIMARY);
        txtUsername.setCaretColor(TEXT_PRIMARY);
        txtUsername.setBorder(BorderFactory.createLineBorder(TEXT_SECONDARY, 1, true));
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(TEXT_PRIMARY);
        lblPassword.setFont(FONT_LABEL);
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(FONT_TEXT);
        txtPassword.setBackground(BACKGROUND);
        txtPassword.setForeground(TEXT_PRIMARY);
        txtPassword.setCaretColor(TEXT_PRIMARY);
        txtPassword.setBorder(BorderFactory.createLineBorder(TEXT_SECONDARY, 1, true));
        formPanel.add(txtPassword, gbc);

        // Remember Me
        gbc.gridx = 1;
        gbc.gridy = 2;
        chkRememberMe = new JCheckBox("Remember Me");
        chkRememberMe.setFont(FONT_CHECK);
        chkRememberMe.setForeground(TEXT_SECONDARY);
        chkRememberMe.setBackground(CARD_BG);
        formPanel.add(chkRememberMe, gbc);

        cardPanel.add(formPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(CARD_BG);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);

        btnLogin = new JButton("Login");
        btnLogin.setFont(FONT_BUTTON);
        btnLogin.setBackground(SUCCESS);
        btnLogin.setForeground(TEXT_PRIMARY);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(true);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> handleLogin());
        addHoverEffect(btnLogin, SUCCESS.darker(), SUCCESS);

        btnBack = new JButton("Back");
        btnBack.setFont(FONT_BUTTON);
        btnBack.setBackground(DANGER);
        btnBack.setForeground(TEXT_PRIMARY);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setOpaque(true);
        btnBack.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> backToMain());
        addHoverEffect(btnBack, DANGER.darker(), DANGER);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnBack);

        // Delete Account Link
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deletePanel.setBackground(CARD_BG);

        btnDeleteAccount = new JButton("<html><u>Delete Account</u></html>");
        btnDeleteAccount.setFont(FONT_LINK);
        btnDeleteAccount.setForeground(WARNING);
        btnDeleteAccount.setBackground(CARD_BG);
        btnDeleteAccount.setBorderPainted(false);
        btnDeleteAccount.setContentAreaFilled(false);
        btnDeleteAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeleteAccount.setFocusPainted(false);
        btnDeleteAccount.addActionListener(e -> handleDeleteAccount());

        deletePanel.add(btnDeleteAccount);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(deletePanel);

        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add card to frame
        add(cardPanel);
    }

    // Button hover effect
    private void addHoverEffect(JButton button, Color hoverColor, Color normalColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    /**
     * Handle login with database authentication
     */
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Call Service Layer for authentication
        User user = userService.login(username, password);

        if (user != null) {
            // Login successful
            JOptionPane.showMessageDialog(this,
                    "Welcome back, " + user.getUsername() + "!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            // Pass user to ViewBrowse_Window
            ViewBrowse_Window viewBrowseWindow = new ViewBrowse_Window(user);
            viewBrowseWindow.setVisible(true);
            dispose();
        } else {
            // Login failed
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!\nPlease try again.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            // Clear password field
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    /**
     * Handle account deletion
     */
    private void handleDeleteAccount() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your username and password to delete your account!",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Authenticate user first
        User user = userService.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            return;
        }

        // Final confirmation
        String[] options = {"Cancel", "Delete Account"};
        int choice = JOptionPane.showOptionDialog(this,
                "⚠️ WARNING: This action is PERMANENT and IRREVERSIBLE!\n\n" +
                        "You are about to delete your account: " + username + "\n\n" +
                        "All your data will be permanently deleted:\n" +
                        "• Account information\n" +
                        "• Shopping cart items\n" +
                        "• Purchase history\n\n" +
                        "Are you absolutely sure?",
                "Delete Account - Final Warning",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 1) {
            // Delete cart items first
            cartService.clearCart(user.getUserId());

            // Delete user account
            boolean success = userService.deleteUser(user.getUserId());

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Your account has been permanently deleted.\nGoodbye!",
                        "Account Deleted",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                txtUsername.setText("");
                txtPassword.setText("");
                txtUsername.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to delete account. Please try again later.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void backToMain() {
        Main_Window mainWindow = new Main_Window();
        mainWindow.setVisible(true);
        dispose();
    }

    // For testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Login_Window().setVisible(true);
        });
    }
}