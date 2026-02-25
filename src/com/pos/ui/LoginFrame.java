package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame {

    private final Color bgColor = new Color(13, 71, 161); // Deep Blue to match Dashboard
    private final Color cardBg = Color.WHITE;
    private final Color primaryBlue = new Color(25, 118, 210); // Lighter blue for title/button
    private final Color inputBorder = new Color(220, 220, 220);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 32);
    private final Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

    public LoginFrame() {
        setTitle("Log In Now");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Modern look
        setSize(1000, 600);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Draw Large Circular Logo Area
                g2.setColor(Color.WHITE);
                int circleSize = 350;
                int x = 100;
                int y = (getHeight() - circleSize) / 2;
                g2.fill(new Ellipse2D.Double(x, y, circleSize, circleSize));
                g2.dispose();
            }
        };
        setContentPane(mainPanel);

        // Login Card
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cardBg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBounds(550, 80, 350, 440);
        mainPanel.add(card);

        // Card Content
        JLabel lblTitle = new JLabel("Log In Now", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(primaryBlue);
        lblTitle.setBounds(0, 40, 350, 40);
        card.add(lblTitle);

        // Email Field
        JTextField txtEmail = createStyledField("Enter your email");
        txtEmail.setBounds(30, 120, 290, 45);
        card.add(txtEmail);

        // Password Field
        JPasswordField txtPassword = createStyledPasswordField("Enter your password");
        txtPassword.setBounds(30, 190, 290, 45);
        card.add(txtPassword);

        // Eye Icon Placeholder (Optional implementation improvement later)

        // Remember Me
        JCheckBox chkRemember = new JCheckBox("Remember Me");
        chkRemember.setFont(labelFont);
        chkRemember.setOpaque(false);
        chkRemember.setBounds(30, 250, 150, 30);
        card.add(chkRemember);

        // Login Button
        JButton btnLogin = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(primaryBlue.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(primaryBlue.brighter());
                } else {
                    g2.setColor(primaryBlue);
                }
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setBounds(125, 300, 100, 100);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String password = String.valueOf(txtPassword.getPassword()).trim();

            if (email.isEmpty() || email.equals("Enter your email") || password.isEmpty()
                    || password.equals("Enter your password")) {
                JOptionPane.showMessageDialog(this, "Please enter both email and password.", "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Check Internet Connection
            if (!com.pos.service.NetworkService.isInternetAvailable()) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "No internet connection detected. Some features might not work correctly.\nDo you want to proceed anyway?",
                        "Connectivity Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (choice == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            try {
                String response = com.pos.service.AuthService.login(email, password);

                if (response.contains("\"success\":true") || response.contains("\"success\": true")) {
                    // Extract token
                    String token = "";
                    int tokenIndex = response.indexOf("\"access_token\":");
                    if (tokenIndex != -1) {
                        int start = response.indexOf("\"", tokenIndex + 15) + 1;
                        int end = response.indexOf("\"", start);
                        if (start > 0 && end > start) {
                            token = response.substring(start, end);
                        }
                    }
                    com.pos.service.SyncService.setAccessToken(token);
                    com.pos.service.SyncService.startSyncThread();

                    this.dispose();
                    new MainFrame().setVisible(true);
                } else {
                    String message = "Invalid email or password.";
                    if (response.contains("\"message\":")) {
                        // Very simple extraction of the message field
                        int msgIndex = response.indexOf("\"message\":");
                        int start = response.indexOf("\"", msgIndex + 10) + 1;
                        int end = response.indexOf("\"", start);
                        if (start > 0 && end > start) {
                            message = response.substring(start, end);
                        }
                    }
                    JOptionPane.showMessageDialog(this, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "API Connection error: " + ex.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(btnLogin);

        // Enable Enter Key to trigger login
        getRootPane().setDefaultButton(btnLogin);

        // Footer Text
        JLabel lblFooter = new JLabel("Powered by Silicon Radon Networks (Pvt) Ltd.", SwingConstants.CENTER);
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setBounds(550, 530, 350, 30);
        mainPanel.add(lblFooter);

        // Close Button (Top Right)
        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnClose.setForeground(Color.WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setBounds(950, 10, 40, 40);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> System.exit(0));
        mainPanel.add(btnClose);
    }

    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(inputFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(inputBorder, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(inputFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(inputBorder, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('\u2022');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        return field;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
