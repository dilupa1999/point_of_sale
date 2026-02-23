package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddCategoryPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color btnResetGray = new Color(80, 80, 85);
    private final Color headerBg = primaryBlue;
    private final Color fieldBg = new Color(245, 245, 250);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontBreadcrumb = new Font("Segoe UI", Font.PLAIN, 13);

    public AddCategoryPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header (Blue) ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(headerBg);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Left section: Back, Main Panel, POS
        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);

        JButton btnBack = createHeaderButton("<", true);
        btnBack.addActionListener(e -> mainFrame.showPanel("Items"));

        JButton btnMainPanel = createHeaderButton("Go to Main Panel", false);
        btnMainPanel.addActionListener(e -> mainFrame.showPanel("Dashboard"));

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnBack);
        pnlTopLeft.add(btnMainPanel);
        pnlTopLeft.add(btnPOS);
        pnlTopHeader.add(pnlTopLeft, BorderLayout.WEST);

        // Center section: Logo
        JPanel pnlLogo = new JPanel(new GridBagLayout());
        pnlLogo.setOpaque(false);
        JLabel lblLogo = new JLabel("POS SYSTEM");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlLogo.add(lblLogo);
        pnlTopHeader.add(pnlLogo, BorderLayout.CENTER);

        // Right section: Welcome message
        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Evening!</span><br>Welcome Pos System</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = new JButton("\u23FB");
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnPower.setForeground(Color.BLACK);
        btnPower.setBackground(Color.WHITE);
        btnPower.setPreferredSize(new Dimension(40, 40));
        btnPower.setFocusPainted(false);
        btnPower.setBorder(null);

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);

        // Breadcrumbs
        JPanel pnlBreadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pnlBreadcrumb.setOpaque(false);
        JLabel lblB = new JLabel("Main Panel > Items > Add Category");
        lblB.setFont(fontBreadcrumb);
        lblB.setForeground(Color.GRAY);
        pnlBreadcrumb.add(lblB);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

        // Form Area
        JPanel pnlFormContainer = new JPanel(new BorderLayout());
        pnlFormContainer.setOpaque(false);
        pnlFormContainer.setBorder(new EmptyBorder(10, 40, 40, 40));

        JPanel pnlCard = new JPanel(new GridBagLayout());
        pnlCard.setBackground(Color.WHITE);
        pnlCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230,230,235), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Category Field
        gbc.gridy = 0;
        JLabel lblCategory = new JLabel("Category");
        lblCategory.setFont(fontBold14);
        pnlCard.add(lblCategory, gbc);

        gbc.gridy = 1;
        JTextField txtCategory = new JTextField("Your Category name");
        txtCategory.setPreferredSize(new Dimension(0, 45));
        txtCategory.setBackground(fieldBg);
        txtCategory.setBorder(new LineBorder(new Color(220, 220, 225)));
        txtCategory.setForeground(Color.GRAY);
        pnlCard.add(txtCategory, gbc);

        // Description Field
        gbc.gridy = 2;
        gbc.insets = new Insets(25, 0, 0, 0);
        JLabel lblDesc = new JLabel("Your description");
        lblDesc.setFont(fontBold14);
        pnlCard.add(lblDesc, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        JTextArea txtDesc = new JTextArea("Enter description", 8, 20);
        txtDesc.setBorder(new LineBorder(new Color(220, 220, 225)));
        txtDesc.setForeground(Color.GRAY);
        pnlCard.add(new JScrollPane(txtDesc), gbc);

        // Buttons Section
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlButtons.setOpaque(false);
        
        JButton btnSave = createActionButton("Save", vibrantGreen);
        JButton btnReset = createActionButton("Reset", btnResetGray);
        
        pnlButtons.add(btnSave);
        pnlButtons.add(btnReset);

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        pnlCard.add(pnlButtons, gbc);

        pnlFormContainer.add(pnlCard, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(pnlFormContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
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
        btn.setPreferredSize(new Dimension(100, 45));
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
        btn.setForeground(headerBg);
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
