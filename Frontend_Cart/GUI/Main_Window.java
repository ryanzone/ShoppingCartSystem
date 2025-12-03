package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Main_Window extends JFrame {
    private JButton btnLogin;
    private JButton btnSignup;
    private JButton btnExit;

    // Modern color palette (from ModernUI)
    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(30, 30, 30);
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);

    public Main_Window() {
        setTitle("Shopping Cart System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));




        JLabel titleLabel = new JLabel("Welcome to");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel shopLabel = new JLabel("Shopping Cart System");
        shopLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        shopLabel.setForeground(TEXT_PRIMARY);
        shopLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Your one-stop shop for everything");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        btnLogin = createModernButton("Login", PRIMARY);
        btnSignup = createModernButton("Sign Up", SUCCESS);
        btnExit = createModernButton("Exit", DANGER);


        btnLogin.addActionListener(e -> openLoginWindow());
        btnSignup.addActionListener(e -> openSignupWindow());
        btnExit.addActionListener(e -> System.exit(0));


        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(shopLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(btnLogin);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnSignup);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnExit);

        add(mainPanel, BorderLayout.CENTER);
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
        button.setMaximumSize(new Dimension(400, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);


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

    private void openLoginWindow() {
        Login_Window loginWindow = new Login_Window();
        loginWindow.setVisible(true);
        dispose();
    }

    private void openSignupWindow() {
        Signup_Window signupWindow = new Signup_Window();
        signupWindow.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Main_Window mainWindow = new Main_Window();
            mainWindow.setVisible(true);
        });
    }
}