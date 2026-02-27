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
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Color primaryRed = new Color(211, 47, 47);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    private JPanel pnlGrid;

    public ReportsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
        setupResponsiveness();
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header (Blue) ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(primaryBlue);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Left section: POS
        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnPOS);
        pnlTopHeader.add(pnlTopLeft, BorderLayout.WEST);

        // Center section: Title
        JPanel pnlLogo = new JPanel(new GridBagLayout());
        pnlLogo.setOpaque(false);
        JLabel lblLogo = new JLabel("POS SYSTEM");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnlLogo.add(lblLogo);
        pnlTopHeader.add(pnlLogo, BorderLayout.CENTER);

        // Right section: Welcome message
        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Evening!</span><br>Welcome Pos System</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = createHeaderButton("\u23FB", true);
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 22));

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Content Wrapper ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);

        // Breadcrumbs
        JPanel pnlBreadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        pnlBreadcrumb.setOpaque(false);
        JLabel lblBreadcrumb = new JLabel("Main Panel > Reports");
        lblBreadcrumb.setForeground(breadcrumbGray);
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumb.add(lblBreadcrumb);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        pnlGrid = new JPanel(new GridLayout(0, 5, 20, 20)); // Match screenshot 5 columns
        pnlGrid.setOpaque(false);

        // Row 1 (5 cards as per screenshot)
        pnlGrid.add(createMenuCard("Sales Report", "SalesReport", primaryBlue, e -> mainFrame.showPanel("SalesReport")));
        pnlGrid.add(createMenuCard("Item Stock Count", "StockCount", primaryBlue, e -> mainFrame.showPanel("ItemStockCount")));
        pnlGrid.add(createMenuCard("Item Report", "ItemReport", primaryBlue, e -> mainFrame.showPanel("ItemReport")));
        pnlGrid.add(createMenuCard("Expences Report", "ExpensesReport", primaryBlue, e -> mainFrame.showPanel("ExpensesReport")));
        pnlGrid.add(createMenuCard("Stock Report", "StockReport", primaryBlue, e -> mainFrame.showPanel("StockReport")));

        // Row 2 (4 cards as per screenshot)
        pnlGrid.add(createMenuCard("Loyalty Point Income Report", "LoyaltyReport", primaryBlue, e -> mainFrame.showPanel("LoyaltyPointReport")));
        pnlGrid.add(createMenuCard("Daily Summary Report", "SummaryReport", summaryBlue, e -> mainFrame.showPanel("DailySummaryReport")));
        pnlGrid.add(createMenuCard("Monthly Summary Report", "SummaryReport", primaryPurple, e -> mainFrame.showPanel("MonthlySummaryReport")));
        pnlGrid.add(createMenuCard("Yearly Summary Report", "SummaryReport", primaryRed, e -> mainFrame.showPanel("YearlySummaryReport")));

        pnlContent.add(pnlGrid);

        pnlMain.add(new JScrollPane(pnlContent), BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
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

    private JButton createMenuCard(String title, String iconType, Color bg, java.awt.event.ActionListener al) {
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
        
        if (al != null) btn.addActionListener(al);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });

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
