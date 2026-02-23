package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ExportItemsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontLabel = new Font("Segoe UI", Font.PLAIN, 12);

    public ExportItemsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // --- Header / Breadcrumbs ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Items > Export Items");
        lblBreadcrumb.setForeground(Color.GRAY);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new BorderLayout(0, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 25, 25, 25));

        // 1. Controls Section (Item Type Dropdown + Buttons)
        JPanel pnlControls = new JPanel(new BorderLayout());
        pnlControls.setOpaque(false);

        // Left Controls: Item Type
        JPanel pnlType = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        pnlType.setOpaque(false);
        pnlType.setLayout(new BoxLayout(pnlType, BoxLayout.Y_AXIS));
        
        JLabel lblType = new JLabel("Item Type");
        lblType.setFont(fontLabel);
        lblType.setForeground(Color.GRAY);
        
        String[] types = {"Scale Items", "Non-Scale Items", "All Items"};
        JComboBox<String> comboType = new JComboBox<>(types);
        comboType.setPreferredSize(new Dimension(150, 35));
        comboType.setBackground(Color.WHITE);
        
        pnlType.add(lblType);
        pnlType.add(Box.createVerticalStrut(5));
        pnlType.add(comboType);

        // Right Controls: Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        pnlButtons.setOpaque(false);

        JButton btnExport = new JButton("Export Selected");
        btnExport.setBackground(primaryBlue);
        btnExport.setForeground(Color.WHITE);
        btnExport.setFont(fontTitle);
        btnExport.setFocusPainted(false);
        btnExport.setPreferredSize(new Dimension(160, 40));
        btnExport.setIcon(new ExportIcon("Export", 18, 18, Color.WHITE));

        JButton btnSync = new JButton();
        btnSync.setBackground(new Color(60, 70, 90));
        btnSync.setPreferredSize(new Dimension(40, 40));
        btnSync.setIcon(new ExportIcon("Sync", 20, 20, Color.WHITE));
        btnSync.setFocusPainted(false);

        pnlButtons.add(btnExport);
        pnlButtons.add(btnSync);

        pnlControls.add(pnlType, BorderLayout.WEST);
        pnlControls.add(pnlButtons, BorderLayout.EAST);

        pnlContent.add(pnlControls, BorderLayout.NORTH);

        // 2. Table Section
        String[] columns = {"", "ITEM CODE", "ITEM NAME", "CATEGORY", "UNIT", "RETAIL PRICE", "TYPE - (SCALE GROUP NO IF ANY)", "MANAGE"};
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(45);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setFont(fontTitle);
        table.getTableHeader().setForeground(Color.GRAY);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Placeholder for "No items found"
        JLabel lblEmpty = new JLabel("No items found", SwingConstants.CENTER);
        lblEmpty.setForeground(Color.GRAY);
        lblEmpty.setFont(fontLabel);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        // Show empty message if no data
        if (model.getRowCount() == 0) {
            table.setFillsViewportHeight(true);
            JPanel pnlTableEmpty = new JPanel(new GridBagLayout());
            pnlTableEmpty.setBackground(Color.WHITE);
            pnlTableEmpty.add(lblEmpty);
            scrollPane.setViewportView(pnlTableEmpty);
        }

        pnlContent.add(scrollPane, BorderLayout.CENTER);

        add(pnlContent, BorderLayout.CENTER);
    }

    // --- Custom Icon Renderer ---
    private static class ExportIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public ExportIcon(String type, int w, int h, Color color) {
            this.type = type;
            this.w = w; this.h = h;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f));
            g2.translate(x, y);

            int cx = w / 2;
            int cy = h / 2;

            switch (type) {
                case "Export":
                    g2.drawRect(cx-8, cy-4, 16, 12);
                    g2.drawLine(cx, cy, cx, cy-8);
                    g2.drawLine(cx, cy-8, cx-3, cy-5);
                    g2.drawLine(cx, cy-8, cx+3, cy-5);
                    break;
                case "Sync":
                    g2.drawArc(4, 4, w-8, h-8, 45, 270);
                    g2.drawLine(w-6, cy, w-2, cy-4);
                    g2.drawLine(w-6, cy, w-10, cy-4);
                    break;
            }
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
