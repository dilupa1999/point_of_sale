package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class DashboardPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color secondaryBg = new Color(245, 247, 250);
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 18);
    private final Font fontValue = new Font("Segoe UI", Font.BOLD, 22);
    private final Font fontLabel = new Font("Segoe UI", Font.PLAIN, 12);

    private JPanel pnlSummary;
    private JPanel pnlStats;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
        setupResponsiveness();
    }

    private void initComponents() {
        // --- Top Header (Matching other panels) ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(primaryBlue);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new javax.swing.border.EmptyBorder(0, 20, 0, 20));

        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);
        // No back button for dashboard usually, but we can add POS button
        JButton btnPOS = createHeaderButton("POS", false);
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
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Afternoon!</span><br>Welcome Hypermart</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = createHeaderButton("\u23FB", true);
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content Container ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);

        // Header / Breadcrumbs inside content
        JPanel pnlContentHeader = new JPanel(new BorderLayout());
        pnlContentHeader.setBackground(Color.WHITE);
        pnlContentHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Dashboard");
        lblBreadcrumb.setForeground(breadcrumbGray);
        lblBreadcrumb.setFont(fontLabel);
        pnlContentHeader.add(lblBreadcrumb, BorderLayout.WEST);

        pnlMain.add(pnlContentHeader, BorderLayout.NORTH);

        // --- Scrollable Content ---
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // 1. Summary Cards (Top Row) - Now with dynamic columns
        pnlSummary = new JPanel(new GridLayout(0, 5, 15, 15));
        pnlSummary.setOpaque(false);

        pnlSummary.add(createSummaryCard("Total Sales Amount", "Rs. 590.00", "Cart"));
        pnlSummary.add(createSummaryCard("Total Sales Due", "Rs. 0.00", "Chart"));
        pnlSummary.add(createSummaryCard("Total Expenses Amount", "Rs. 0.00", "Money"));
        pnlSummary.add(createSummaryCard("Total Payment Received", "Rs. 590.00", "Chart"));
        pnlSummary.add(createSummaryCard("Today Total Sales", "Rs. 0.00", "Cart"));

        pnlContent.add(pnlSummary);
        pnlContent.add(Box.createVerticalStrut(20));

        // 2. Stats Section (Middle Row)
        pnlStats = new RoundedPanel(15);
        pnlStats.setLayout(new GridLayout(0, 4, 0, 15));
        pnlStats.setBackground(Color.WHITE);
        pnlStats.setBorder(new LineBorder(new Color(230, 230, 230), 1));

        pnlStats.add(createStatItem("Customers", "1", "User"));
        pnlStats.add(createStatItem("Suppliers", "2", "Truck"));
        pnlStats.add(createStatItem("Items", "1", "Box"));
        pnlStats.add(createStatItem("Sales Invoice", "1", "File"));

        pnlContent.add(pnlStats);
        pnlContent.add(Box.createVerticalStrut(30));

        // 3. Action Buttons Section (New)
        JPanel pnlActionsRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActionsRow.setOpaque(false);
        pnlActionsRow.add(createActionButton("Expiry Alert", new Color(67, 160, 71)));
        pnlActionsRow.add(createActionButton("Sales Chart", primaryBlue));
        pnlActionsRow.add(createActionButton("Stock Alert", new Color(120, 144, 156)));
        
        pnlContent.add(pnlActionsRow);
        pnlContent.add(Box.createVerticalStrut(30));

        // 4. Alerts Section (Bottom)
        JPanel pnlAlertsHeader = new JPanel(new BorderLayout());
        pnlAlertsHeader.setOpaque(false);
        JLabel lblAlertTitle = new JLabel("Items Expiring Within 5 Days");
        lblAlertTitle.setFont(fontTitle);
        pnlAlertsHeader.add(lblAlertTitle, BorderLayout.WEST);
        
        JLabel lblExpiryCount = new JLabel("1 Items expiring soon") {
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
        lblExpiryCount.setOpaque(false);
        lblExpiryCount.setBackground(new Color(255, 120, 120)); // Softer red
        lblExpiryCount.setForeground(Color.WHITE);
        lblExpiryCount.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblExpiryCount.setBorder(new EmptyBorder(5, 15, 5, 15));
        lblExpiryCount.setHorizontalAlignment(SwingConstants.CENTER);
        pnlAlertsHeader.add(lblExpiryCount, BorderLayout.EAST);

        pnlContent.add(pnlAlertsHeader);
        pnlContent.add(Box.createVerticalStrut(20));

        // Expiry Table Section
        String[] columns = {"#", "ITEM NAME", "STOCK QUANTITY", "EXPIRY DATE", "DAYS LEFT"};
        javax.swing.table.DefaultTableModel expiryModel = new javax.swing.table.DefaultTableModel(columns, 0);
        JTable tableExpiry = new JTable(expiryModel);
        
        tableExpiry.setRowHeight(45);
        tableExpiry.setShowGrid(false);
        tableExpiry.setIntercellSpacing(new Dimension(0, 0));
        
        // Red Header Styling
        javax.swing.table.JTableHeader header = tableExpiry.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(new Color(229, 57, 53)); // Primary Red
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return lbl;
            }
        });

        // Custom Cell Rendering
        tableExpiry.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 4) { // DAYS LEFT Column
                    JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
                    p.setBackground(new Color(255, 235, 238));
                    JLabel badge = new JLabel("TODAY");
                    badge.setOpaque(true);
                    badge.setBackground(new Color(229, 57, 53));
                    badge.setForeground(Color.WHITE);
                    badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    badge.setBorder(new EmptyBorder(2, 10, 2, 10));
                    p.add(badge);
                    p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 245)));
                    return p;
                }
                
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBackground(new Color(255, 235, 238)); // Light red background for expiring
                lbl.setForeground(new Color(183, 28, 28));
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 245)));
                return lbl;
            }
        });

        // Add Sample Data
        expiryModel.addRow(new Object[]{"1", "Kellogg's Chocos 127g", "5", "Feb 25, 2026", "TODAY"});

        JScrollPane scrollExpiry = new JScrollPane(tableExpiry);
        scrollExpiry.setPreferredSize(new Dimension(0, 150));
        scrollExpiry.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollExpiry.setBorder(new LineBorder(new Color(240, 240, 245)));
        scrollExpiry.getViewport().setBackground(Color.WHITE);
        
        pnlContent.add(scrollExpiry);
        pnlContent.add(Box.createVerticalStrut(20));

        JScrollPane scrollPane = new JScrollPane(pnlContent);
        scrollPane.setBorder(null);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlMain, BorderLayout.CENTER);
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
            btn.setPreferredSize(new Dimension(100, 45));
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(130, 40));
        return btn;
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                
                // Adjust Summary Cards
                int summaryCols = width > 1200 ? 5 : (width > 800 ? 3 : (width > 500 ? 2 : 1));
                ((GridLayout) pnlSummary.getLayout()).setColumns(summaryCols);
                pnlSummary.revalidate();

                // Adjust Stats items
                int statCols = width > 1000 ? 4 : (width > 600 ? 2 : 1);
                ((GridLayout) pnlStats.getLayout()).setColumns(statCols);
                pnlStats.revalidate();
            }
        });
    }

    private JPanel createSummaryCard(String label, String value, String iconType) {
        JPanel card = new RoundedPanel(12);
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(240, 248, 255)); // Light bluish/white bg
        card.setBorder(new EmptyBorder(10, 0, 10, 10));

        JPanel pnlIcon = new JPanel(new GridBagLayout());
        pnlIcon.setBackground(primaryBlue);
        pnlIcon.setPreferredSize(new Dimension(50, 0));
        JLabel lblIcon = new JLabel(new DashboardIcon(iconType, 24, 24, Color.WHITE));
        pnlIcon.add(lblIcon);

        JPanel pnlInfo = new JPanel(new GridLayout(2, 1));
        pnlInfo.setOpaque(false);
        pnlInfo.setBorder(new EmptyBorder(0, 10, 0, 0));
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(fontLabel);
        lblLabel.setForeground(Color.GRAY);
        JLabel lblVal = new JLabel(value);
        lblVal.setFont(fontValue);
        lblVal.setForeground(Color.DARK_GRAY);
        
        pnlInfo.add(lblLabel);
        pnlInfo.add(lblVal);

        card.add(pnlIcon, BorderLayout.WEST);
        card.add(pnlInfo, BorderLayout.CENTER);

        return card;
    }

    private JPanel createStatItem(String label, String value, String iconType) {
        JPanel item = new JPanel(new GridBagLayout());
        item.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        
        JLabel lblIcon = new JLabel(new DashboardIcon(iconType, 48, 48, new Color(173, 216, 230)));
        item.add(lblIcon, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(fontLabel);
        lblLabel.setForeground(Color.GRAY);
        item.add(lblLabel, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 0, 0);
        JLabel lblVal = new JLabel(value);
        lblVal.setFont(fontTitle);
        item.add(lblVal, gbc);
        
        return item;
    }

    // --- Helper Component: Rounded Panel ---
    private static class RoundedPanel extends JPanel {
        private int radius;
        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        }
    }

    // --- Helper: Dashboard Icons ---
    private static class DashboardIcon implements Icon {
        private String type;
        private int w, h;
        private Color color;

        public DashboardIcon(String type, int w, int h, Color color) {
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
                case "Cart":
                    g2.drawRect(cx-8, cy-8, 16, 12);
                    g2.drawOval(cx-6, cy+6, 4, 4);
                    g2.drawOval(cx+2, cy+6, 4, 4);
                    break;
                case "Chart":
                    g2.drawLine(cx-8, cy+8, cx+8, cy+8);
                    g2.drawLine(cx-8, cy+8, cx-8, cy-8);
                    g2.fillRect(cx-6, cy, 3, 8);
                    g2.fillRect(cx-1, cy-4, 3, 12);
                    g2.fillRect(cx+4, cy+2, 3, 6);
                    break;
                case "Money":
                    g2.drawRect(cx-10, cy-6, 20, 12);
                    g2.drawOval(cx-4, cy-4, 8, 8);
                    break;
                case "User":
                    g2.drawOval(cx-8, cy-12, 16, 16);
                    g2.drawArc(cx-12, cy+4, 24, 16, 0, 180);
                    break;
                case "Truck":
                    g2.drawRect(cx-12, cy-6, 16, 12);
                    g2.drawRect(cx+4, cy, 8, 6);
                    g2.drawOval(cx-8, cy+8, 4, 4);
                    g2.drawOval(cx+6, cy+8, 4, 4);
                    break;
                case "Box":
                    g2.drawRect(cx-8, cy-8, 16, 16);
                    g2.drawLine(cx-8, cy-8, cx+8, cy+8);
                    g2.drawLine(cx+8, cy-8, cx-8, cy+8);
                    break;
                case "File":
                    g2.drawRect(cx-8, cy-10, 16, 20);
                    g2.drawLine(cx-4, cy-4, cx+4, cy-4);
                    gbcLines(); // Placeholder
                    break;
            }
            g2.dispose();
        }
        
        private void gbcLines() {}

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
