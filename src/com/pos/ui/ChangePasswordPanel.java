package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ChangePasswordPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Color cancelRed = new Color(211, 47, 47);
    private final Color saveGreen = new Color(100, 175, 80);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontPlain13 = new Font("Segoe UI", Font.PLAIN, 13);

    public ChangePasswordPanel(MainFrame mainFrame) {
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
        btnBack.addActionListener(e -> mainFrame.showPanel("Settings"));

        JButton btnMainPanel = createHeaderButton("Go to Main Panel", false);
        btnMainPanel.addActionListener(e -> mainFrame.showPanel("Dashboard"));

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnBack);
        pnlTopLeft.add(btnMainPanel);
        pnlTopLeft.add(btnPOS);
        pnlTopHeader.add(pnlTopLeft, BorderLayout.WEST);

        // Center Title
        JPanel pnlCenterTitle = new JPanel(new GridBagLayout());
        pnlCenterTitle.setOpaque(false);
        JLabel lblTopTitle = new JLabel("POS SYSTEM");
        lblTopTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTopTitle.setForeground(Color.WHITE);
        pnlCenterTitle.add(lblTopTitle);
        pnlTopHeader.add(pnlCenterTitle, BorderLayout.CENTER);

        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Evening!</span><br>Welcome Hypermart</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = createHeaderButton("\u23FB", true);
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Settings > Change Password");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlMain.add(lblBreadcrumb, BorderLayout.NORTH);

        JPanel pnlFormContainer = new JPanel(new BorderLayout());
        pnlFormContainer.setBackground(Color.WHITE);
        pnlFormContainer.setBorder(new LineBorder(new Color(230, 230, 235), 1));
        
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Current Password
        gbc.gridy = 0;
        pnlForm.add(createLabel("Current Password"), gbc);
        gbc.gridy = 1;
        pnlForm.add(createPasswordField("Enter current password"), gbc);

        // New Password
        gbc.gridy = 2;
        pnlForm.add(createLabel("New Password"), gbc);
        gbc.gridy = 3;
        pnlForm.add(createPasswordField("Enter new password"), gbc);

        // Confirm New Password
        gbc.gridy = 4;
        pnlForm.add(createLabel("Confirm New Password"), gbc);
        gbc.gridy = 5;
        pnlForm.add(createPasswordField("Enter new password again"), gbc);

        // Buttons
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 0, 0, 0);
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        pnlButtons.setOpaque(false);
        pnlButtons.add(createActionButton("Create", saveGreen));
        pnlButtons.add(createActionButton(">Cancel", cancelRed));
        pnlForm.add(pnlButtons, gbc);

        pnlFormContainer.add(pnlForm, BorderLayout.NORTH);
        
        pnlMain.add(pnlFormContainer, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold14);
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField txt = new JPasswordField();
        txt.setPreferredSize(new Dimension(0, 45));
        txt.setBackground(fieldBg);
        txt.setBorder(new LineBorder(new Color(230, 230, 235)));
        txt.setFont(fontPlain13);
        // Simplified placeholder logic for demonstration
        txt.setToolTipText(placeholder); 
        return txt;
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
        btn.setPreferredSize(new Dimension(140, 45));
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
        btn.setForeground(new Color(50, 50, 50));
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
