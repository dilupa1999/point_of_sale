package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SuppliersPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    private JPanel pnlGrid;

    public SuppliersPanel(MainFrame mainFrame) {
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
        JLabel lblBreadcrumb = new JLabel("Main Panel > Suppliers");
        lblBreadcrumb.setForeground(breadcrumbGray);
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumb.add(lblBreadcrumb);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

        // Content Area 
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Grid Area
        pnlGrid = new JPanel(new GridLayout(0, 4, 20, 20));
        pnlGrid.setOpaque(false);

        // Adding the Menu Cards
        JButton btnAddSupplier = createMenuCard("Add New Supplier", "AddTruck");
        btnAddSupplier.addActionListener(e -> mainFrame.showPanel("AddSupplier"));
        pnlGrid.add(btnAddSupplier);

        JButton btnSuppliersList = createMenuCard("Suppliers List", "TruckList");
        btnSuppliersList.addActionListener(e -> mainFrame.showPanel("SuppliersList"));
        pnlGrid.add(btnSuppliersList);
        JButton btnImportSuppliers = createMenuCard("Import Suppliers", "Import");
        btnImportSuppliers.addActionListener(e -> mainFrame.showPanel("ImportSuppliers"));
        pnlGrid.add(btnImportSuppliers);

        pnlContent.add(pnlGrid);

        pnlMain.add(new JScrollPane(pnlContent), BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int cols = width > 1100 ? 5 : (width > 900 ? 3 : (width > 600 ? 2 : 1));
                ((GridLayout) pnlGrid.getLayout()).setColumns(cols);
                pnlGrid.revalidate();
            }
        });
    }

    private JButton createMenuCard(String title, String iconType) {
        JButton btn = new JButton(new SupplierMenuIcon(iconType, 48, 48, Color.WHITE));
        btn.setText("<html><center>" + title + "</center></html>");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        btn.setPreferredSize(new Dimension(200, 230));
        btn.setBackground(primaryBlue);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontTitle);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(primaryBlue.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(primaryBlue);
            }
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

    private static class SupplierMenuIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public SupplierMenuIcon(String type, int w, int h, Color color) {
            this.type = type;
            this.w = w; this.h = h;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.5f));
            g2.translate(x, y);

            int cx = w / 2;
            int cy = h / 2;

            switch (type) {
                case "AddTruck":
                    g2.drawRect(cx-12, cy-6, 16, 12);
                    g2.drawRect(cx+4, cy, 8, 6);
                    g2.drawOval(cx-8, cy+4, 4, 4);
                    g2.drawOval(cx+6, cy+4, 4, 4);
                    g2.drawLine(cx-15, cy-12, cx-7, cy-12);
                    g2.drawLine(cx-11, cy-16, cx-11, cy-8);
                    break;
                case "TruckList":
                    g2.drawRect(cx-12, cy-6, 16, 12);
                    g2.drawRect(cx+4, cy, 8, 6);
                    g2.drawOval(cx-8, cy+4, 4, 4);
                    g2.drawOval(cx+6, cy+4, 4, 4);
                    g2.drawLine(cx-12, cy-10, cx+12, cy-10);
                    break;
                case "Import":
                    g2.drawRect(cx-10, cy-6, 20, 16);
                    g2.drawLine(cx, cy, cx, cy-12);
                    g2.drawLine(cx-4, cy-4, cx, cy);
                    g2.drawLine(cx+4, cy-4, cx, cy);
                    break;
            }
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
