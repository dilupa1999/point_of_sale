package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.*;

public class POSPanel extends JPanel {

    private final Font fontBold22 = new Font("Segoe UI", Font.BOLD, 22);
    private final Font fontBold20 = new Font("Segoe UI", Font.BOLD, 20);
    private final Font fontBold18 = new Font("Segoe UI", Font.BOLD, 18);
    private final Font fontBold16 = new Font("Segoe UI", Font.BOLD, 16);
    private final Color primaryGreen = new Color(0, 200, 83); // Vibrant Green for Enter
    private final Color categoryBlue = new Color(2, 119, 189); // Deep Blue for Categories
    private final Color keypadBg = new Color(230, 230, 235); // Light Gray for Keypad
    private final Color keypadFg = new Color(13, 71, 161); // Deep Blue for Keypad text
    private final Color activeBlue = new Color(13, 71, 161); // Active highlight blue
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontBold12 = new Font("Segoe UI", Font.BOLD, 12);
    private final Font fontPlain12 = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font fontPlain10 = new Font("Segoe UI", Font.PLAIN, 10);
    private final Font fontBold10 = new Font("Segoe UI", Font.BOLD, 10);

    private DefaultTableModel model;
    private JTextField txtInput;
    private JButton btnEnter;
    private JPanel pnlGrid;
    private JPanel pnlCategories;
    private JPanel pnlMain;

    public POSPanel() {
        setLayout(new BorderLayout());
        initComponents();
        setupResponsiveness();
    }

    private void initComponents() {
        setBackground(new Color(245, 245, 245));
        
        pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setOpaque(false);
        updateLayout(1200); // Default

        add(pnlMain, BorderLayout.CENTER);
    }

    private void updateLayout(int width) {
        if (pnlMain == null) return;
        pnlMain.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        
        if (width > 900) {
            // Desktop Layout (Side-by-side)
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.weightx = 0.4; gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(10, 10, 10, 5);
            pnlMain.add(createLeftPane(), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            gbc.insets = new Insets(10, 5, 10, 10);
            pnlMain.add(createRightPane(), gbc);
        } else {
            // Mobile/Tablet Layout (Stacked)
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.weightx = 1.0; gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(10, 10, 5, 10);
            pnlMain.add(createLeftPane(), gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(5, 10, 10, 10);
            pnlMain.add(createRightPane(), gbc);
        }
        pnlMain.revalidate();
        pnlMain.repaint();
    }

    private void setupResponsiveness() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                updateLayout(width);
                
                // Adjust grids in right pane
                if (pnlGrid != null && pnlCategories != null) {
                    int cols = width > 1200 ? 6 : (width > 900 ? 4 : (width > 600 ? 3 : 2));
                    ((GridLayout) pnlGrid.getLayout()).setColumns(cols);
                    ((GridLayout) pnlCategories.getLayout()).setColumns(cols);
                    pnlGrid.revalidate();
                    pnlCategories.revalidate();
                }
            }
        });
    }

    private JPanel createLeftPane() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        btnEnter = new JButton("Enter");
        
        // Table Section
        String[] columns = {"Item Name", "Quantity", "U/Price", "Dis%", "Total"};
        Object[][] data = {
            {"Croissant", "1", "$2.99", "0", "$2.99"},
            {"Strawberry Jam", "2", "$5.00", "0", "$10.00"},
            {"Vodafone Top-up", "1", "$5.00", "0", "$5.00"},
            {"Wheat Braed", "2", "$2.00", "0", "$4.00"}
        };
        model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(fontPlain12);
        table.getTableHeader().setBackground(new Color(210, 215, 220));
        table.getTableHeader().setFont(fontBold12);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Section
        JPanel pnlBottom = new JPanel(new BorderLayout(5, 5));
        pnlBottom.setOpaque(false);
        pnlBottom.setPreferredSize(new Dimension(0, 420));

        // PLU Input
        JPanel pnlInput = new JPanel(new BorderLayout());
        pnlInput.setBackground(new Color(110, 110, 115));
        JLabel lblQty = new JLabel(" 1x    PLU ", SwingConstants.LEFT);
        lblQty.setForeground(Color.WHITE);
        lblQty.setFont(fontBold14);
        lblQty.setPreferredSize(new Dimension(110, 0));
        
        txtInput = new JTextField("");
        txtInput.setFont(fontBold18);
        txtInput.setBorder(new EmptyBorder(0,10,0,10));
        txtInput.setForeground(Color.BLACK);
        
        pnlInput.add(lblQty, BorderLayout.WEST);
        pnlInput.add(txtInput, BorderLayout.CENTER);
        pnlInput.setPreferredSize(new Dimension(0, 45));
        pnlBottom.add(pnlInput, BorderLayout.NORTH);

        // Keypad
        JPanel pnlKeypadContainer = new JPanel(new BorderLayout(3, 3));
        pnlKeypadContainer.setOpaque(false);

        JPanel pnlNumPad = new JPanel(new GridLayout(4, 3, 3, 3));
        pnlNumPad.setOpaque(false);
        String[] keys = {"7","8","9","4","5","6","1","2","3",".","0","*"};
        for(String key : keys) {
            JButton b = new JButton(key);
            b.setFont(fontBold20);
            b.setBackground(keypadBg);
            b.setForeground(keypadFg);
            b.setBorder(new LineBorder(new Color(210, 210, 215)));
            b.setFocusPainted(false);
            b.addActionListener(e -> txtInput.setText(txtInput.getText() + key));
            pnlNumPad.add(b);
        }

        JPanel pnlRightPad = new JPanel(new GridLayout(4, 2, 3, 3));
        pnlRightPad.setOpaque(false);
        pnlRightPad.setPreferredSize(new Dimension(150, 0));
        String[] rKeys = {"DEL", "+", "-", "Back", "Hold", "Recall", "Void", "Last Rec"};
        for(String rk : rKeys) {
            JButton b = new JButton(rk);
            b.setFont(fontBold12);
            b.setBackground(Color.WHITE);
            b.setForeground(keypadFg);
            b.setBorder(new LineBorder(new Color(210, 210, 215)));
            b.setFocusPainted(false);
            pnlRightPad.add(b);
        }

        pnlKeypadContainer.add(pnlNumPad, BorderLayout.CENTER);
        pnlKeypadContainer.add(pnlRightPad, BorderLayout.EAST);
        pnlBottom.add(pnlKeypadContainer, BorderLayout.CENTER);

        // Enter Button
        btnEnter.setBackground(primaryGreen);
        btnEnter.setForeground(Color.WHITE);
        btnEnter.setFont(fontBold22);
        btnEnter.setPreferredSize(new Dimension(0, 65));
        btnEnter.setBorder(null);
        btnEnter.setFocusPainted(false);
        pnlBottom.add(btnEnter, BorderLayout.SOUTH);

        panel.add(pnlBottom, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createRightPane() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        // Products Grid (Upper half)
        pnlGrid = new JPanel(new GridLayout(0, 6, 8, 8));
        pnlGrid.setOpaque(false);
        for(int i=0; i<18; i++) {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(Color.WHITE);
            p.setBorder(new LineBorder(new Color(220, 220, 225)));
            JLabel img = new JLabel("Image", SwingConstants.CENTER); // Placeholder
            img.setPreferredSize(new Dimension(0, 80));
            img.setOpaque(true);
            img.setBackground(new Color(240, 240, 245));
            p.add(img, BorderLayout.NORTH);
            JLabel name = new JLabel(" Item " + (i+1), SwingConstants.CENTER);
            name.setFont(fontBold10);
            p.add(name, BorderLayout.CENTER);
            pnlGrid.add(p);
        }
        
        // Category Buttons (Lower half)
        pnlCategories = new JPanel(new GridLayout(0, 6, 8, 8));
        pnlCategories.setOpaque(false);
        pnlCategories.setPreferredSize(new Dimension(0, 250));
        String[] cats = {"Snacks", "Dairy", "Coffee", "Health", "Bakery", "Beauty", 
                         "Stationary", "Gift Cards", "Favorites", "Fruits", "Spices", "Noodles",
                         "Jam", "Meat", "Beverages", "Sauce", "Frozen", "Top-up"};
        for(String cat : cats) {
            JButton btn = new JButton("<html><center>"+cat+"</center></html>");
            btn.setFont(fontBold12);
            btn.setForeground(Color.WHITE);
            if(cat.equals("Favorites")) {
                btn.setBackground(new Color(80, 80, 85));
            } else {
                btn.setBackground(categoryBlue);
            }
            btn.setBorder(null);
            btn.setFocusPainted(false);
            pnlCategories.add(btn);
        }

        JPanel pnlRightContent = new JPanel(new BorderLayout(10, 10));
        pnlRightContent.setOpaque(false);
        pnlRightContent.add(pnlGrid, BorderLayout.CENTER);
        pnlRightContent.add(pnlCategories, BorderLayout.SOUTH);

        panel.add(pnlRightContent, BorderLayout.CENTER);
        return panel;
    }
}
