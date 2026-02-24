package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddItemsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color headerBg = primaryBlue;
    private final Color fieldBg = new Color(245, 245, 250);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontPlain12 = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font fontBreadcrumb = new Font("Segoe UI", Font.PLAIN, 13);

    public AddItemsPanel(MainFrame mainFrame) {
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
        // In a real app, this would be an image
        pnlLogo.add(lblLogo);
        pnlTopHeader.add(pnlLogo, BorderLayout.CENTER);

        // Right section: Welcome message
        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Evening!</span><br>Welcome Pos System</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = new JButton("\u23FB"); // Power icon
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
        JLabel lblB = new JLabel("Main Panel > Items > Add Items");
        lblB.setFont(fontBreadcrumb);
        lblB.setForeground(Color.GRAY);
        pnlBreadcrumb.add(lblB);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

        // Form Area
        JPanel pnlFormScrollContainer = new JPanel(new BorderLayout());
        pnlFormScrollContainer.setOpaque(false);
        pnlFormScrollContainer.setBorder(new EmptyBorder(10, 20, 20, 20));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0
        addFormField(pnlForm, "Item Code (Optional)", new JTextField("Leave blank for auto-generate"), 0, 0, 1);
        addFormField(pnlForm, "Barcode", new JTextField("Barcode No"), 1, 0, 1);
        addFormField(pnlForm, "Item name", new JTextField("Your Item name"), 2, 0, 1);
        addFormField(pnlForm, "Singlish Name (For POS Search)", new JTextField("Enter Singlish name (e.g., Kos, Pol, Ric)"), 3, 0, 1);
        
        JComboBox<String> comboCat = new JComboBox<>(new String[]{"Select category", "Dairy Products", "Vegetables", "Bakery", "Drinks", "Fruits", "Snacks", "Beverages", "Spices", "Grains", "Frozen Foods", "Meat", "Pharmacy"});
        SearchableComboBox.install(comboCat);
        addFormField(pnlForm, "Category", comboCat, 4, 0, 1);

        // Row 1
        JComboBox<String> comboSupplier = new JComboBox<>(new String[]{"Your supplier name", "Global Distributors", "Fresh Farms", "National Trading", "Premium Goods", "Local Suppliers"});
        SearchableComboBox.install(comboSupplier);
        addFormField(pnlForm, "Supplier", comboSupplier, 0, 1, 1);
        
        JComboBox<String> comboUnit = new JComboBox<>(new String[]{"Select unit type", "Pieces", "Kg", "Gram", "Litre", "MilliLitre", "Packet", "Box", "Bottle", "Bundle"});
        SearchableComboBox.install(comboUnit);
        addFormField(pnlForm, "Unit Type", comboUnit, 1, 1, 1);
        addFormField(pnlForm, "Quantity", new JTextField("Enter Qty"), 2, 1, 1);
        addFormField(pnlForm, "Minimum Quantity", new JTextField("Enter Minimum Qty"), 3, 1, 1);
        addFormField(pnlForm, "Purchase Price", new JTextField("Enter purchase price"), 4, 1, 1);

        // Row 2
        addFormField(pnlForm, "Market Price", new JTextField("Enter market price"), 0, 2, 1);
        addFormField(pnlForm, "Wholesale Price", new JTextField("Enter wholesale price"), 1, 2, 1);
        addFormField(pnlForm, "Additional Fees % (Default: 0%)", new JTextField("0.00"), 2, 2, 1);
        
        // Retail Price with Toggle
        JPanel pnlRetail = new JPanel(new BorderLayout(5, 0));
        pnlRetail.setOpaque(false);
        JTextField txtRetail = new JTextField("Enter retail price");
        JCheckBox chkManual = new JCheckBox("Manual");
        chkManual.setOpaque(false);
        pnlRetail.add(txtRetail, BorderLayout.CENTER);
        pnlRetail.add(chkManual, BorderLayout.EAST);
        addFormField(pnlForm, "Retail Price", pnlRetail, 3, 2, 1);

        // Scale Item Toggle
        JPanel pnlScale = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlScale.setOpaque(false);
        JCheckBox chkScale = new JCheckBox("This is a scale item");
        chkScale.setOpaque(false);
        pnlScale.add(chkScale);
        addFormField(pnlForm, "Scale Item", pnlScale, 4, 2, 1);

        // Row 3
        addFormField(pnlForm, "POS Order No (For POS Ordering)", new JTextField("Enter POS order number"), 0, 3, 1);

        // Row 4: Description
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Fill remaining space
        gbc.fill = GridBagConstraints.BOTH;
        
        JPanel pnlDesc = new JPanel(new BorderLayout(0, 5));
        pnlDesc.setOpaque(false);
        JLabel lblDesc = new JLabel("Description");
        lblDesc.setFont(fontBold14);
        lblDesc.setForeground(new Color(50, 50, 50));
        JTextArea txtDesc = new JTextArea(5, 20);
        txtDesc.setBorder(new LineBorder(new Color(220, 220, 225)));
        txtDesc.setBackground(Color.WHITE);
        pnlDesc.add(lblDesc, BorderLayout.NORTH);
        pnlDesc.add(new JScrollPane(txtDesc), BorderLayout.CENTER);
        
        pnlForm.add(pnlDesc, gbc);

        pnlFormScrollContainer.add(pnlForm, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(pnlFormScrollContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
        
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void addFormField(JPanel panel, String labelStr, JComponent component, int x, int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setOpaque(false);

        JLabel label = new JLabel(labelStr);
        label.setFont(fontBold14);
        label.setForeground(new Color(50, 50, 50));

        if (component instanceof JTextField) {
            JTextField tf = (JTextField) component;
            tf.setPreferredSize(new Dimension(0, 40));
            tf.setBackground(fieldBg);
            tf.setBorder(new LineBorder(new Color(220, 220, 225)));
            tf.setForeground(Color.GRAY);
        } else if (component instanceof JComboBox) {
            component.setPreferredSize(new Dimension(0, 40));
            component.setBackground(fieldBg);
        }

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(component, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
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
