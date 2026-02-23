package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class StockPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Color secondaryGray = new Color(108, 117, 125);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontLabel = new Font("Segoe UI", Font.PLAIN, 12);

    public StockPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // --- Header / Breadcrumbs ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Stock");
        lblBreadcrumb.setForeground(breadcrumbGray);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        // Export Buttons (Top Right)
        JPanel pnlExport = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlExport.setOpaque(false);
        String[] exportLabels = {"Copy", "CSV", "Excel", "PDF", "Column Visibility"};
        for (String label : exportLabels) {
            JButton btn = new JButton(label);
            btn.setBackground(label.equals("Column Visibility") ? new Color(60, 100, 150) : primaryBlue);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            btn.setFocusPainted(false);
            btn.setBorder(new EmptyBorder(5, 12, 5, 12));
            pnlExport.add(btn);
        }
        pnlHeader.add(pnlExport, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new BorderLayout(0, 15));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // 1. Controls Row (Filter + Search)
        JPanel pnlControls = new JPanel(new BorderLayout());
        pnlControls.setOpaque(false);

        // Left Controls: Category + Search
        JPanel pnlFilterLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlFilterLeft.setOpaque(false);

        // Category Group
        JPanel grpCat = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        grpCat.setOpaque(false);
        grpCat.setLayout(new BoxLayout(grpCat, BoxLayout.X_AXIS));
        JLabel lblCat = new JLabel("Category:  ");
        JComboBox<String> comboCat = new JComboBox<>(new String[]{"All Categories", "Dairy", "Bakery", "Drinks"});
        comboCat.setPreferredSize(new Dimension(140, 32));
        grpCat.add(lblCat);
        grpCat.add(comboCat);

        // Search Group
        JPanel grpSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        grpSearch.setOpaque(false);
        grpSearch.setLayout(new BoxLayout(grpSearch, BoxLayout.X_AXIS));
        JLabel lblSearch = new JLabel("Search:  ");
        JTextField txtSearch = new JTextField("Enter item name or code");
        txtSearch.setPreferredSize(new Dimension(180, 32));
        txtSearch.setForeground(Color.LIGHT_GRAY);
        grpSearch.add(lblSearch);
        grpSearch.add(txtSearch);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBackground(primaryBlue);
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);
        btnFilter.setPreferredSize(new Dimension(70, 32));

        JButton btnClear = new JButton("Clear");
        btnClear.setBackground(Color.BLACK);
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new Dimension(70, 32));

        pnlFilterLeft.add(grpCat);
        pnlFilterLeft.add(grpSearch);
        pnlFilterLeft.add(btnFilter);
        pnlFilterLeft.add(btnClear);

        // Right Controls: Show Entries
        JPanel pnlEntries = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlEntries.setOpaque(false);
        JLabel lblShow = new JLabel("Show");
        JTextField txtEntries = new JTextField("30");
        txtEntries.setPreferredSize(new Dimension(50, 32));
        txtEntries.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lblEntries = new JLabel("Entries");
        pnlEntries.add(lblShow);
        pnlEntries.add(txtEntries);
        pnlEntries.add(lblEntries);

        pnlControls.add(pnlFilterLeft, BorderLayout.WEST);
        pnlControls.add(pnlEntries, BorderLayout.EAST);

        pnlContent.add(pnlControls, BorderLayout.NORTH);

        // 2. Data Table
        String[] columns = {"#", "ITEM CODE", "ITEM NAME", "CATEGORY", "QUANTITY", "UNIT TYPE", "MANAGE"};
        Object[][] data = {
            {"1", "6472", "Sample Product 1", "Dairy Products", "18", "Pieces", "Add Stock"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.getTableHeader().setBackground(primaryBlue);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(fontTitle);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(fontLabel);

        // Custom Renderer for "Manage" column button
        table.getColumn("MANAGE").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton btn = new JButton(value.toString());
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                return btn;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        pnlContent.add(scrollPane, BorderLayout.CENTER);

        add(pnlContent, BorderLayout.CENTER);
    }
}
