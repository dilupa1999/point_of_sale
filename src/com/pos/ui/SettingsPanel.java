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
        JLabel lblBreadcrumb = new JLabel("Main Panel > Settings");
        lblBreadcrumb.setForeground(breadcrumbGray);
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumb.add(lblBreadcrumb);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

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

        pnlMain.add(new JScrollPane(pnlContent), BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
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
