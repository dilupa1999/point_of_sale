package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SiteSettingsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color headerBlue = new Color(30, 136, 229);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Color cancelRed = new Color(211, 47, 47);
    private final Color saveGreen = new Color(100, 175, 80);
    private final Color resetBlack = new Color(33, 33, 33);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontPlain13 = new Font("Segoe UI", Font.PLAIN, 13);

    public SiteSettingsPanel(MainFrame mainFrame) {
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

        // Center Title/Logo
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
        JLabel lblBreadcrumb = new JLabel("Main Panel > Settings > Site Settings");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlMain.add(lblBreadcrumb, BorderLayout.NORTH);

        JPanel pnlFormContainer = new JPanel(new BorderLayout());
        pnlFormContainer.setBackground(Color.WHITE);
        pnlFormContainer.setBorder(new LineBorder(new Color(230, 230, 235), 1));
        
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form Fields
        gbc.gridy = 0;
        pnlForm.add(createLabel("Site Name"), gbc);
        gbc.gridy = 1;
        pnlForm.add(createTextField("Hypermart"), gbc);

        gbc.gridy = 2;
        pnlForm.add(createLabel("Sidebar One Name"), gbc);
        gbc.gridy = 3;
        pnlForm.add(createTextField("Hypermart"), gbc);

        gbc.gridy = 4;
        pnlForm.add(createLabel("Sidebar Two Name"), gbc);
        gbc.gridy = 5;
        pnlForm.add(createTextField("Hypermart"), gbc);

        gbc.gridy = 6;
        pnlForm.add(createLabel("Contact Number"), gbc);
        gbc.gridy = 7;
        pnlForm.add(createTextField("0786835520"), gbc);

        gbc.gridy = 8;
        pnlForm.add(createLabel("Company Logo"), gbc);
        gbc.gridy = 9;
        JPanel pnlFileUpload = new JPanel(new BorderLayout(10, 0));
        pnlFileUpload.setOpaque(false);
        JButton btnChooseFile = new JButton("Choose File");
        btnChooseFile.setBackground(resetBlack);
        btnChooseFile.setForeground(Color.WHITE);
        btnChooseFile.setFont(fontBold14);
        btnChooseFile.setFocusPainted(false);
        btnChooseFile.setPreferredSize(new Dimension(120, 35));
        JLabel lblFileName = new JLabel("No file chosen");
        lblFileName.setFont(fontPlain13);
        pnlFileUpload.add(btnChooseFile, BorderLayout.WEST);
        pnlFileUpload.add(lblFileName, BorderLayout.CENTER);
        pnlFileUpload.setBorder(new LineBorder(new Color(230, 230, 235)));
        pnlFileUpload.setPreferredSize(new Dimension(0, 45));
        pnlForm.add(pnlFileUpload, gbc);

        // Logo Preview Placeholder
        gbc.gridy = 10;
        gbc.insets = new Insets(20, 0, 20, 0);
        JLabel lblLogoPreview = new JLabel("HYPERMART"); // Using text as placeholder for the logo image
        lblLogoPreview.setFont(new Font("Impact", Font.ITALIC, 24));
        lblLogoPreview.setForeground(primaryBlue);
        pnlForm.add(lblLogoPreview, gbc);

        // Buttons
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButtons.setOpaque(false);
        pnlButtons.add(createActionButton("Save", saveGreen));
        pnlButtons.add(createActionButton("Reset System", resetBlack));
        pnlButtons.add(createActionButton("Cancel", cancelRed));
        pnlForm.add(pnlButtons, gbc);

        pnlFormContainer.add(pnlForm, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(pnlFormContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold14);
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private JTextField createTextField(String text) {
        JTextField txt = new JTextField(text);
        txt.setPreferredSize(new Dimension(0, 45));
        txt.setBackground(fieldBg);
        txt.setBorder(new LineBorder(new Color(230, 230, 235)));
        txt.setFont(fontPlain13);
        txt.setForeground(new Color(50, 50, 50));
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
