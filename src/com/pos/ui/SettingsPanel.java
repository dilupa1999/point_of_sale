package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    public SettingsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // --- Header / Breadcrumbs ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Settings");
        lblBreadcrumb.setForeground(Color.GRAY);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 25, 25, 25));

        JPanel pnlCenter = new JPanel(new GridLayout(2, 3, 20, 20));
        pnlCenter.setOpaque(false);

        pnlCenter.add(createMenuCard("Company Settings", "Settings"));
        pnlCenter.add(createMenuCard("User Profile", "User"));
        pnlCenter.add(createMenuCard("Database Backup", "Database"));
        pnlCenter.add(createMenuCard("Tax Settings", "Tax"));
        pnlCenter.add(createMenuCard("Printer Config", "Printer"));
        pnlCenter.add(createMenuCard("System Logs", "Logs"));

        pnlContent.add(pnlCenter);

        add(new JScrollPane(pnlContent), BorderLayout.CENTER);
    }

    private JButton createMenuCard(String title, String iconType) {
        JButton btn = new JButton(new SettingsMenuIcon(iconType, 48, 48, Color.WHITE));
        btn.setText("<html><center>" + title + "</center></html>");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setPreferredSize(new Dimension(200, 210));
        btn.setBackground(primaryBlue);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontTitle);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(primaryBlue.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(primaryBlue); }
        });

        return btn;
    }

    private static class SettingsMenuIcon implements Icon {
        private String type; private int w, h; private Color color;
        public SettingsMenuIcon(String type, int w, int h, Color color) { this.type = type; this.w = w; this.h = h; this.color = color; }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.5f));
            g2.translate(x, y);
            int cx = w / 2; int cy = h / 2;
            switch(type) {
                case "Settings": g2.drawOval(cx-8, cy-8, 16, 16); break;
                case "User": g2.drawOval(cx-6, cy-10, 12, 12); g2.drawArc(cx-12, cy+2, 24, 12, 0, 180); break;
                default: g2.drawRect(cx-10, cy-10, 20, 20); break;
            }
            g2.dispose();
        }
        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
