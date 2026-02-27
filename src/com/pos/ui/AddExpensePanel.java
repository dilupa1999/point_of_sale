package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddExpensePanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color cancelRed = new Color(211, 47, 47);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);

    public AddExpensePanel(MainFrame mainFrame) {
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
        btnBack.addActionListener(e -> mainFrame.showPanel("Expenses"));

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
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Morning!</span><br>Welcome Hypermart</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = createHeaderButton("\u23FB", true);
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Expenses > Add New Expense");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlContent.add(lblBreadcrumb, BorderLayout.NORTH);

        JPanel pnlFormContainer = new JPanel(new BorderLayout());
        pnlFormContainer.setBackground(Color.WHITE);
        pnlFormContainer.setBorder(new LineBorder(new Color(235, 235, 240), 1));
        
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Row 1: Date and Expense Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Date"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createLabel("Expense Title"), gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 0;
        pnlForm.add(createTextField("Select date"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createTextField("Enter expense title"), gbc);

        // Row 2: Expense Category (Full Width)
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        pnlForm.add(createLabel("Expense Category"), gbc);
        gbc.gridy = 3;
        JComboBox<String> comboCategory = new JComboBox<>(new String[]{"Select expense category", "Electricity", "Water", "Rent", "Salaries", "Marketing", "Utility", "Maintenance", "Repair", "Taxes", "Insurance"});
        SearchableComboBox.install(comboCategory);
        comboCategory.setPreferredSize(new Dimension(0, 45));
        comboCategory.setBackground(fieldBg);
        comboCategory.setBorder(new LineBorder(new Color(230, 230, 235)));
        comboCategory.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlForm.add(comboCategory, gbc);

        // Row 3: Amount (Half Width)
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        pnlForm.add(createLabel("Amount"), gbc);
        gbc.gridy = 5;
        pnlForm.add(createTextField("Enter amount"), gbc);

        // Row 4: Details (Full Width)
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        pnlForm.add(createLabel("Details"), gbc);
        gbc.gridy = 7;
        JTextArea txtDetails = new JTextArea("Enter Details");
        txtDetails.setPreferredSize(new Dimension(0, 120));
        txtDetails.setBackground(fieldBg);
        txtDetails.setBorder(new LineBorder(new Color(230, 230, 235)));
        txtDetails.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        pnlForm.add(new JScrollPane(txtDetails), gbc);

        // Row 5: Buttons
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 0, 10, 0);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButtons.setOpaque(false);
        pnlButtons.add(createActionButton("Add", actionBlue)); // Using blue theme
        pnlButtons.add(createActionButton("Reset", tableHeaderBlue));
        pnlButtons.add(createActionButton("Cancel", cancelRed));
        pnlForm.add(pnlButtons, gbc);

        pnlFormContainer.add(pnlForm, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(pnlFormContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlContent.add(scrollPane, BorderLayout.CENTER);

        add(pnlContent, BorderLayout.CENTER);
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
        txt.setForeground(Color.GRAY);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
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
        btn.setPreferredSize(new Dimension(120, 45));
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
