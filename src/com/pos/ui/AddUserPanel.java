package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddUserPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color tableHeaderGreen = new Color(100, 175, 80);
    private final Color cancelRed = new Color(211, 47, 47);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);

    public AddUserPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(primaryBlue);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);

        JButton btnBack = createHeaderButton("<", true);
        btnBack.addActionListener(e -> mainFrame.showPanel("Users"));

        JButton btnMainPanel = createHeaderButton("Go to Main Panel", false);
        btnMainPanel.addActionListener(e -> mainFrame.showPanel("Dashboard"));

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnBack);
        pnlTopLeft.add(btnMainPanel);
        pnlTopLeft.add(btnPOS);
        pnlTopHeader.add(pnlTopLeft, BorderLayout.WEST);

        JPanel pnlLogo = new JPanel(new GridBagLayout());
        pnlLogo.setOpaque(false);
        JLabel lblLogo = new JLabel("POS SYSTEM");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlLogo.add(lblLogo);
        pnlTopHeader.add(pnlLogo, BorderLayout.CENTER);

        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Evening!</span><br>Welcome Pos System</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = new JButton("\u23FB");
        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Users > Add New User");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlContent.add(lblBreadcrumb, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        // Row 1: Name and Mobile Number
        gbc.gridy = 0;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Name"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createLabel("Mobile Number"), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        pnlForm.add(createTextField("Enter name"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createTextField("Enter mobile number"), gbc);

        // Row 2: Gender and Email
        gbc.gridy = 2;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Gender"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createLabel("User Email"), gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        pnlForm.add(createComboBox(new String[]{"Select gender", "Male", "Female", "Other"}), gbc);
        gbc.gridx = 1;
        pnlForm.add(createTextField("superadmin@gmail.com"), gbc);

        // Row 3: Password and Confirm Password
        gbc.gridy = 4;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Password"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createLabel("Confirm Password"), gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        pnlForm.add(createPasswordField(), gbc);
        gbc.gridx = 1;
        pnlForm.add(createTextField("Confirm password"), gbc);

        // Row 4: Role
        gbc.gridy = 6;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Role"), gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        pnlForm.add(createComboBox(new String[]{"Select Role", "Admin", "User", "Cashier"}), gbc);

        // Row 5: Buttons
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButtons.setOpaque(false);
        pnlButtons.add(createActionButton("Add", vibrantGreen));
        pnlButtons.add(createActionButton("Reset", tableHeaderGreen));
        pnlButtons.add(createActionButton("Cancel", cancelRed));
        pnlForm.add(pnlButtons, gbc);

        pnlContent.add(pnlForm, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(pnlContent);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold14);
        return lbl;
    }

    private JTextField createTextField(String text) {
        JTextField txt = new JTextField(text);
        txt.setPreferredSize(new Dimension(0, 40));
        txt.setBackground(fieldBg);
        txt.setBorder(new LineBorder(new Color(220, 220, 225)));
        txt.setForeground(Color.GRAY);
        return txt;
    }

    private JPasswordField createPasswordField() {
        JPasswordField pass = new JPasswordField("*********");
        pass.setPreferredSize(new Dimension(0, 40));
        pass.setBackground(fieldBg);
        pass.setBorder(new LineBorder(new Color(220, 220, 225)));
        pass.setForeground(Color.GRAY);
        return pass;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setPreferredSize(new Dimension(0, 40));
        combo.setBackground(Color.WHITE);
        combo.setBorder(new LineBorder(new Color(220, 220, 225)));
        return combo;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setPreferredSize(new Dimension(100, 40));
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        return btn;
    }

    private JButton createHeaderButton(String text, boolean isCircle) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, isCircle ? 18 : 13));
        btn.setForeground(primaryBlue);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        if (isCircle) {
            btn.setPreferredSize(new Dimension(50, 45));
        } else {
            btn.setPreferredSize(new Dimension(130, 45));
        }
        return btn;
    }
}
