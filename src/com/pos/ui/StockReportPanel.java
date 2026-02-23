package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StockReportPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color darkBg = new Color(33, 37, 41);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 12);
    private final Font fontLabel = new Font("Segoe UI", Font.PLAIN, 11);

    public StockReportPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // --- Header ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Item Stock Count");
        lblBreadcrumb.setForeground(Color.GRAY);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnGenerate = new JButton("Generate Report");
        btnGenerate.setBackground(Color.BLACK);
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.setFont(fontTitle);
        btnGenerate.setFocusPainted(false);
        btnGenerate.setBorder(new EmptyBorder(8, 15, 8, 15));
        pnlHeader.add(btnGenerate, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content ---
        JPanel pnlContent = new JPanel(new BorderLayout(0, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 25, 25, 25));

        // 1. Filters Row
        JPanel pnlFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlFilters.setOpaque(false);

        pnlFilters.add(createFilterField("Category", new JComboBox<>(new String[]{"All Categories", "Dairy Products", "Vegetables"})));
        pnlFilters.add(createFilterField("Item Name", new JTextField("Enter item name")));
        pnlFilters.add(createFilterField("From Date", new JTextField("mm/dd/yyyy")));
        pnlFilters.add(createFilterField("To Date", new JTextField("mm/dd/yyyy")));

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBackground(primaryBlue);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setPreferredSize(new Dimension(80, 35));
        btnSubmit.setFocusPainted(false);

        JButton btnReset = new JButton("Reset");
        btnReset.setBackground(Color.BLACK);
        btnReset.setForeground(Color.WHITE);
        btnReset.setPreferredSize(new Dimension(80, 35));
        btnReset.setFocusPainted(false);

        pnlFilters.add(btnSubmit);
        pnlFilters.add(btnReset);

        pnlContent.add(pnlFilters, BorderLayout.NORTH);

        // 2. Data Table
        String[] columns = {
            "NO", "ITEM NAME", "CATEGORY", "STARTING STOCK", "PURCHASED STOCK", 
            "SOLD STOCK", "ENDING STOCK", "PURCHASE PRICE", "RETAIL PRICE", 
            "WHOLE SALE PRICE", "STOCK VALUE (RS.)", "PRICE PER ITEM (RS.)", 
            "SOLD STOCK VALUE (RS.)", "DATE"
        };
        
        Object[][] data = {
            {"1", "Sample Product 1", "Dairy Products", "20", "20", "2", "18", "250.00", "295.00", "290.00", "4,500.00", "250.00", "590.00", "2026-02-22"},
            {"2", "Anchor Cheese Slices 12S 200g", "Dairy Products", "100", "100", "0", "100", "1000.00", "1730.00", "1100.00", "100,000.00", "1,000.00", "0.00", "2026-02-23"},
            {"3", "Tomato", "Vegetables", "50", "50", "0", "50", "110.00", "105.00", "10000.00", "5,500.00", "110.00", "0.00", "2026-02-23"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(fontLabel);
        
        // Header styling
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setBackground(primaryBlue);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 10));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Right align numeric columns
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for(int i=3; i<columns.length-1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // 3. Footer Summary Row
        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel pnlFooter = new JPanel(new GridLayout(1, columns.length));
        pnlFooter.setPreferredSize(new Dimension(0, 40));
        pnlFooter.setBackground(new Color(235, 235, 235));
        
        // Manual summary layout (simplified for display)
        pnlFooter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        JLabel lblTotalLabel = new JLabel(" Total Value (Rs.)", SwingConstants.LEFT);
        lblTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.weightx = 0.7; // Cover first few columns
        pnlFooter.add(lblTotalLabel, gbc);

        JLabel lblStockValTotal = new JLabel("110,000.00  ", SwingConstants.RIGHT);
        lblStockValTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.weightx = 0.1;
        pnlFooter.add(lblStockValTotal, gbc);

        JLabel lblPriceTotal = new JLabel("1,360.00  ", SwingConstants.RIGHT);
        lblPriceTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.weightx = 0.1;
        pnlFooter.add(lblPriceTotal, gbc);

        JLabel lblSoldTotal = new JLabel("590.00  ", SwingConstants.RIGHT);
        lblSoldTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.weightx = 0.1;
        pnlFooter.add(lblSoldTotal, gbc);

        pnlTableContainer.add(pnlFooter, BorderLayout.SOUTH);

        pnlContent.add(pnlTableContainer, BorderLayout.CENTER);
        add(pnlContent, BorderLayout.CENTER);
    }

    private JPanel createFilterField(String label, JComponent field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(fontLabel);
        lbl.setForeground(Color.GRAY);
        
        field.setPreferredSize(new Dimension(field instanceof JTextField ? 150 : 180, 35));
        field.setBackground(Color.WHITE);
        
        if(field instanceof JTextField) {
            ((JTextField)field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
            ));
            ((JTextField)field).setForeground(Color.GRAY);
        }

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        return p;
    }
}
