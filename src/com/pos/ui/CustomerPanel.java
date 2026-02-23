package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CustomerPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    private JPanel pnlGrid;

    public CustomerPanel() {
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

        JLabel lblBreadcrumb = new JLabel("Main Panel > Customer");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Grid Area
        pnlGrid = new JPanel(new GridLayout(0, 2, 20, 20));
        pnlGrid.setOpaque(false);

        // Adding the Menu Cards
        pnlGrid.add(createMenuCard("Add New Customer", "AddUser"));
        pnlGrid.add(createMenuCard("Customer List", "UsersList"));
        pnlGrid.add(createMenuCard("Customer Groups", "AddRole"));
        pnlGrid.add(createMenuCard("Import Customers", "Import"));

        pnlContent.add(pnlGrid);

        add(new JScrollPane(pnlContent), BorderLayout.CENTER);
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
        JButton btn = new JButton(new CustomerMenuIcon(iconType, 48, 48, Color.WHITE));
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
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(primaryBlue.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(primaryBlue); }
        });

        return btn;
    }

    // --- Custom Icon Renderer ---
    private static class CustomerMenuIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public CustomerMenuIcon(String type, int w, int h, Color color) {
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
                case "AddUser":
                    g2.drawOval(cx-10, cy-15, 20, 20);
                    g2.drawArc(cx-15, cy+5, 30, 15, 0, 180);
                    g2.drawLine(cx+10, cy-10, cx+18, cy-10);
                    g2.drawLine(cx+14, cy-14, cx+14, cy-6);
                    break;
                case "UsersList":
                    g2.drawRect(cx-12, cy-15, 10, 10);
                    g2.drawRect(cx-12, cy, 10, 10);
                    g2.drawLine(cx+2, cy-15, cx+15, cy-15);
                    g2.drawLine(cx+2, cy-10, cx+15, cy-10);
                    g2.drawLine(cx+2, cy, cx+15, cy);
                    g2.drawLine(cx+2, cy+5, cx+15, cy+5);
                    break;
                case "AddRole":
                    g2.drawOval(cx-12, cy-8, 10, 10);
                    g2.drawOval(cx+2, cy-8, 10, 10);
                    g2.drawArc(cx-15, cy, 30, 15, 0, 180);
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
