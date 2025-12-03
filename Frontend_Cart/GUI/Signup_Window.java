package GUI;

import java.awt.*;
import javax.swing.*;
import service.UserService;
import model.User;

public class Signup_Window extends JFrame {
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtPhone;
    private JButton btnSignup;
    private JButton btnBack;

    // Service Layer
    private UserService userService;

    // Colors (matching Main_Window)
    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);

    // Fonts
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_TEXT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    public Signup_Window() {
        setTitle("Sign Up - Shopping Cart System");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);
        setLocationRelativeTo(null);

        // Initialize service
        userService = new UserService();

        initComponents();
    }

    private void initComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Join us today");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(subtitleLabel);

        // Form Card Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        int row = 0;
        // Username
        addLabelAndField(formPanel, gbc, row++, "Username:", txtUsername = createTextField());
        // Email
        addLabelAndField(formPanel, gbc, row++, "Email:", txtEmail = createTextField());
        // Password
        addLabelAndField(formPanel, gbc, row++, "Password:", txtPassword = createPasswordField());
        // Confirm Password
        addLabelAndField(formPanel, gbc, row++, "Confirm Password:", txtConfirmPassword = createPasswordField());
        // Phone
        addLabelAndField(formPanel, gbc, row++, "Phone:", txtPhone = createTextField());

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BACKGROUND);

        // Signup Button
        btnSignup = new JButton("Sign Up");
        btnSignup.setFont(FONT_BUTTON);
        btnSignup.setPreferredSize(new Dimension(150, 45));
        btnSignup.setBackground(SUCCESS);
        btnSignup.setForeground(TEXT_PRIMARY);
        btnSignup.setFocusPainted(false);
        btnSignup.setBorderPainted(false);
        btnSignup.setOpaque(true);
        btnSignup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(btnSignup, SUCCESS.darker(), SUCCESS);
        btnSignup.addActionListener(e -> handleSignup());

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setFont(FONT_BUTTON);
        btnBack.setPreferredSize(new Dimension(120, 45));
        btnBack.setBackground(DANGER);
        btnBack.setForeground(TEXT_PRIMARY);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setOpaque(true);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(btnBack, DANGER.darker(), DANGER);
        btnBack.addActionListener(e -> backToMain());

        buttonPanel.add(btnSignup);
        buttonPanel.add(btnBack);

        // Add panels
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_PRIMARY);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        field.setPreferredSize(new Dimension(300, 38));
        panel.add(field, gbc);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_TEXT);
        field.setBackground(new Color(40, 40, 40));
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_SECONDARY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FONT_TEXT);
        field.setBackground(new Color(40, 40, 40));
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_SECONDARY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

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
     * Handle signup with database integration
     */
    private void handleSignup() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String phone = txtPhone.getText().trim();

        // Validate all fields filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtConfirmPassword.setText("");
            txtPassword.requestFocus();
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 6 characters long!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create user object
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);

        // Call Service Layer to register user
        boolean success = userService.signup(user);

        if (success) {
            // Signup successful
            JOptionPane.showMessageDialog(this,
                    "Account created successfully!\nWelcome, " + username + "!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Auto-login the user after signup
            User loggedInUser = userService.login(username, password);

            if (loggedInUser != null) {
                ViewBrowse_Window viewBrowseWindow = new ViewBrowse_Window(loggedInUser);
                viewBrowseWindow.setVisible(true);
                dispose();
            }
        } else {
            // Signup failed (username or email already exists)
            JOptionPane.showMessageDialog(this,
                    "Username or email already exists!\nPlease try different credentials.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            // Clear password fields
            txtPassword.setText("");
            txtConfirmPassword.setText("");
        }
    }

    private void backToMain() {
        Main_Window mainWindow = new Main_Window();
        mainWindow.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Signup_Window().setVisible(true);
        });
    }
}