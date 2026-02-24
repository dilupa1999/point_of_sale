package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SalesPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    public SalesPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Header / Breadcrumbs ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Sales");
        lblBreadcrumb.setForeground(breadcrumbGray);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new GridBagLayout());
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        JPanel pnlCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlCards.setOpaque(false);

        // Adding the Menu Cards
        JButton btnSalesList = createMenuCard("Sales Items List", "SalesList");
        btnSalesList.addActionListener(e -> mainFrame.showPanel("SalesList"));
        pnlCards.add(btnSalesList);

        JButton btnReturnsList = createMenuCard("Returns List View", "ReturnsList");
        btnReturnsList.addActionListener(e -> mainFrame.showPanel("ReturnsList"));
        pnlCards.add(btnReturnsList);

        pnlContent.add(pnlCards);

        add(new JScrollPane(pnlContent), BorderLayout.CENTER);
    }

    private JButton createMenuCard(String title, String iconType) {
        JButton btn = new JButton(new SalesMenuIcon(iconType, 64, 64, Color.WHITE));
        btn.setText("<html><center>" + title + "</center></html>");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        btn.setPreferredSize(new Dimension(220, 250));
        btn.setBackground(primaryBlue);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontTitle);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 51), 1),
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

    // --- Custom Icon Renderer ---
    private static class SalesMenuIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public SalesMenuIcon(String type, int w, int h, Color color) {
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
                case "SalesList":
                    g2.drawRect(cx-15, cy-20, 30, 40);
                    g2.drawLine(cx-10, cy-10, cx+10, cy-10);
                    g2.drawLine(cx-10, cy, cx+10, cy);
                    g2.drawLine(cx-10, cy+10, cx+5, cy+10);
                    g2.drawOval(cx+5, cy+5, 10, 10);
                    g2.drawLine(cx+10, cy+10, cx+15, cy+15);
                    break;
                case "ReturnsList":
                    g2.drawRect(cx-15, cy-20, 30, 40);
                    g2.drawOval(cx-8, cy-8, 16, 16);
                    g2.drawLine(cx+4, cy+4, cx+12, cy+12);
                    g2.drawLine(cx-10, cy-15, cx+10, cy-15);
                    break;
            }
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
