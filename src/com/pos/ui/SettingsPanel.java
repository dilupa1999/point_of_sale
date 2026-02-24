package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color breadcrumbGray = new Color(100, 100, 100);

    public SettingsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Header / Breadcrumbs ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Settings");
        lblBreadcrumb.setForeground(breadcrumbGray);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 100));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        JPanel pnlCenter = new JPanel(new GridLayout(1, 3, 20, 20));
        pnlCenter.setOpaque(false);
        
        JButton btnSite = createMenuCard("Site Settings", "Site", primaryBlue);
        btnSite.addActionListener(e -> mainFrame.showPanel("SiteSettings"));
        
        JButton btnPass = createMenuCard("Change Password", "Password", primaryBlue);
        btnPass.addActionListener(e -> mainFrame.showPanel("ChangePassword"));
        
        JButton btnChangeSite = createMenuCard("Change Site", "ChangeSite", primaryBlue);
        btnChangeSite.addActionListener(e -> mainFrame.showPanel("ChangeSite"));

        pnlCenter.add(btnSite);
        pnlCenter.add(btnPass);
        pnlCenter.add(btnChangeSite);

        pnlContent.add(pnlCenter);

        add(new JScrollPane(pnlContent), BorderLayout.CENTER);
    }

    private JButton createMenuCard(String title, String iconType, Color bgColor) {
        JButton btn = new JButton(new SettingsMenuIcon(iconType, 48, 48, Color.WHITE));
        btn.setText("<html><center>" + title + "</center></html>");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setPreferredSize(new Dimension(210, 220));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 10, 20, 10));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
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
                case "Settings": 
                    g2.drawOval(cx-12, cy-12, 24, 24); 
                    g2.drawOval(cx-4, cy-4, 8, 8); 
                    break;
                case "User": 
                    g2.drawOval(cx-8, cy-14, 16, 16); 
                    g2.drawArc(cx-16, cy+2, 32, 16, 0, 180); 
                    break;
                case "Database":
                    g2.drawOval(cx-14, cy-14, 28, 12);
                    g2.drawLine(cx-14, cy-8, cx-14, cy+8);
                    g2.drawLine(cx+14, cy-8, cx+14, cy+8);
                    g2.drawArc(cx-14, cy+2, 28, 12, 180, 180);
                    break;
                case "Site":
                    g2.drawRect(cx-15, cy-12, 30, 24);
                    g2.drawLine(cx-15, cy-4, cx+15, cy-4);
                    g2.drawOval(cx-4, cy+2, 8, 8);
                    break;
                case "Password":
                    g2.drawRect(cx-12, cy-4, 24, 18);
                    g2.drawArc(cx-8, cy-14, 16, 20, 0, 180);
                    g2.drawLine(cx, cy+2, cx, cy+8);
                    break;
                case "ChangeSite":
                    g2.drawOval(cx-12, cy-12, 24, 24);
                    g2.drawArc(cx-16, cy-16, 32, 32, 45, 270);
                    g2.drawLine(cx+8, cy-12, cx+16, cy-16);
                    g2.drawLine(cx+8, cy-12, cx+12, cy-4);
                    break;
                default: 
                    g2.drawRoundRect(cx-14, cy-14, 28, 28, 5, 5); 
                    break;
            }
            g2.dispose();
        }
        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
