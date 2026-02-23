package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ReportsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color summaryBlue = new Color(63, 81, 181);
    private final Color primaryPurple = new Color(156, 39, 176);
    private final Color primaryRed = new Color(211, 47, 47);
    
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    private JPanel pnlGrid;

    public ReportsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
        setupResponsiveness();
    }

    private void initComponents() {
        // --- Header / Breadcrumbs ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Reports");
        lblBreadcrumb.setForeground(Color.GRAY);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 25, 25, 25));

        pnlGrid = new JPanel(new GridLayout(0, 4, 20, 20)); // Base 4 columns
        pnlGrid.setOpaque(false);

        // Row 1
        pnlGrid.add(createMenuCard("Sales Report", "SalesReport", primaryBlue));
        pnlGrid.add(createMenuCard("Item Stock Count", "StockCount", primaryBlue));
        pnlGrid.add(createMenuCard("Item Report", "ItemReport", primaryBlue));
        pnlGrid.add(createMenuCard("Expences Report", "ExpensesReport", primaryBlue));
        pnlGrid.add(createMenuCard("Stock Report", "StockReport", primaryBlue));
        pnlGrid.add(createMenuCard("Loyalty Point Income Report", "LoyaltyReport", primaryBlue));

        // Row 2
       
        pnlGrid.add(createMenuCard("Daily Summary Report", "SummaryReport", summaryBlue));
        pnlGrid.add(createMenuCard("Monthly Summary Report", "SummaryReport", primaryPurple));

        // Row 3
        pnlGrid.add(createMenuCard("Yearly Summary Report", "SummaryReport", primaryRed));

        pnlContent.add(pnlGrid);

        add(new JScrollPane(pnlContent), BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int cols = width > 1200 ? 5 : (width > 900 ? 4 : (width > 680 ? 3 : (width > 450 ? 2 : 1)));
                ((GridLayout) pnlGrid.getLayout()).setColumns(cols);
                pnlGrid.revalidate();
            }
        });
    }

    private JButton createMenuCard(String title, String iconType, Color bg) {
        JButton btn = new JButton(new ReportMenuIcon(iconType, 48, 48, Color.WHITE));
        btn.setText("<html><center>" + title + "</center></html>");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setPreferredSize(new Dimension(200, 230));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontTitle);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });

        return btn;
    }

    private static class ReportMenuIcon implements Icon {
        private String type; private int w, h; private Color color;
        public ReportMenuIcon(String type, int w, int h, Color color) { this.type = type; this.w = w; this.h = h; this.color = color; }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.5f));
            g2.translate(x, y);
            int cx = w / 2; int cy = h / 2;
            switch(type) {
                case "SalesReport": 
                case "SummaryReport":
                    g2.drawRect(cx-12, cy-15, 24, 30); 
                    g2.drawLine(cx-8, cy, cx+8, cy); 
                    g2.drawLine(cx-8, cy-5, cx+8, cy-5); 
                    g2.drawLine(cx-8, cy+5, cx+2, cy+5);
                    break;
                case "StockCount":
                    g2.drawRect(cx-12, cy-15, 24, 30);
                    g2.drawOval(cx-5, cy-8, 10, 10);
                    g2.drawLine(cx-5, cy+5, cx+5, cy+5);
                    break;
                case "ItemReport":
                case "StockReport":
                    g2.drawRect(cx-12, cy-15, 24, 30);
                    g2.drawLine(cx, cy-15, cx, cy-5);
                    g2.drawOval(cx-4, cy, 8, 8);
                    break;
                case "ExpensesReport":
                    g2.drawRect(cx-12, cy-15, 24, 30);
                    g2.drawLine(cx-8, cy, cx+8, cy);
                    g2.drawOval(cx+2, cy-8, 6, 6);
                    break;
                case "LoyaltyReport":
                    g2.drawRect(cx-12, cy-15, 24, 30);
                    g2.drawOval(cx-8, cy-5, 16, 16);
                    g2.drawLine(cx-4, cy, cx, cy+4);
                    g2.drawLine(cx, cy+4, cx+4, cy-4);
                    break;
                default:
                    g2.drawRect(cx-12, cy-15, 24, 30);
                    g2.drawLine(cx-6, cy-6, cx+6, cy+6);
                    g2.drawLine(cx+6, cy-6, cx-6, cy+6);
                    break;
            }
            g2.dispose();
        }
        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
