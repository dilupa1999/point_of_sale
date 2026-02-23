package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ImportItemsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color tableHeaderGreen = new Color(100, 175, 80);
    private final Color outOfStockRed = new Color(211, 47, 47);
    private final Color templateBlue = new Color(33, 150, 243);
    private final Color infoBg = new Color(240, 248, 255);
    private final Color headerBg = primaryBlue;

    public ImportItemsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(headerBg);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

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
        pnlMain.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Items > Import Items");
        lblBreadcrumb.setForeground(Color.GRAY);
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlMain.add(lblBreadcrumb, BorderLayout.NORTH);

        JPanel pnlBody = new JPanel(new GridBagLayout());
        pnlBody.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // --- Import Row ---
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 0, 0, 0);
        JPanel pnlImportRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlImportRow.setOpaque(false);

        // File Icon/Box
        JPanel pnlFileIcon = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                float[] dash = {5f, 5f};
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f));
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        pnlFileIcon.setPreferredSize(new Dimension(80, 80));
        pnlFileIcon.setOpaque(false);
        JLabel lblUpload = new JLabel("\u21E7"); // Cloud/Upload arrow symbol
        lblUpload.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblUpload.setForeground(Color.GRAY);
        pnlFileIcon.add(lblUpload);
        pnlImportRow.add(pnlFileIcon);

        // Status & Template
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlStatus.setOpaque(false);
        pnlStatus.add(new JLabel("File must be in CSV format"));
        
        JButton btnTemplate = createActionButton("Download CSV Template", templateBlue);
        btnTemplate.setPreferredSize(new Dimension(180, 35));
        pnlStatus.add(btnTemplate);
        pnlImportRow.add(pnlStatus);

        pnlBody.add(pnlImportRow, gbc);

        // --- Action Buttons (Import/Cancel) ---
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 100, 0, 0); // Align after the icon
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlActions.setOpaque(false);
        pnlActions.add(createActionButton("Import", tableHeaderGreen));
        pnlActions.add(createActionButton("Cancel", outOfStockRed));
        pnlBody.add(pnlActions, gbc);

        // --- Guidelines Box ---
        gbc.gridy = 2;
        gbc.insets = new Insets(40, 0, 0, 0);
        JPanel pnlGuidelines = new JPanel(new BorderLayout());
        pnlGuidelines.setBackground(infoBg);
        pnlGuidelines.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 240), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        StringBuilder sb = new StringBuilder("<html><div style='color: " + toHex(primaryBlue) + ";'>");
        sb.append("<b style='font-size: 13px;'>CSV Format Guidelines:</b><br><br>");
        sb.append("\u2022 <b>Item Code:</b> Unique identifier (optional, will auto-generate if empty)<br>");
        sb.append("\u2022 <b>Item Name:</b> Required field<br>");
        sb.append("\u2022 <b>Scale Item:</b> Use 1 for true, 0 for false<br>");
        sb.append("\u2022 <b>Supplier ID, Category ID, Unit Type ID:</b> Must exist in respective tables<br>");
        sb.append("\u2022 <b>Prices:</b> Use decimal format (e.g., 10.50). Market Price is optional<br>");
        sb.append("\u2022 <b>Additional Fees Percentage:</b> Enter as percentage (e.g., 2.5 for 2.5%)<br>");
        sb.append("\u2022 <b>Status ID:</b> 1 for Active, 2 for Inactive");
        sb.append("</div></html>");

        JLabel lblGuidelines = new JLabel(sb.toString());
        pnlGuidelines.add(lblGuidelines, BorderLayout.CENTER);
        pnlBody.add(pnlGuidelines, gbc);

        // --- Bottom "Add Data On Database" Button ---
        gbc.gridy = 3;
        gbc.insets = new Insets(40, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        JButton btnAddDb = createActionButton("Add Data On Database", tableHeaderGreen);
        btnAddDb.setPreferredSize(new Dimension(180, 45));
        pnlBody.add(btnAddDb, gbc);

        pnlMain.add(pnlBody, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
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

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(100, 35));
        return btn;
    }
}
