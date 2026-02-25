package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Modern Sidebar Navigation Panel for POS System
 * Features: Dark theme, Vector icons, Hover effects, Active highlight, Collapsible mode
 */
public class Sidebar extends JPanel {

    // --- Modern Dark Palette ---
    private static final Color COLOR_BG = new Color(18, 18, 24);        // Deep Dark Background
    private static final Color COLOR_HOVER = new Color(30, 31, 42);     // Subtle hover state
    private static final Color COLOR_ACTIVE = new Color(52, 104, 255);  // Vibrant modern blue
    private static final Color COLOR_TEXT = new Color(150, 155, 175);   // Muted grey-blue text
    private static final Color COLOR_TEXT_ACTIVE = Color.WHITE;         // Crisp white text

    private final int expandedWidth = 220;
    private final int collapsedWidth = 70;
    private boolean isExpanded = true;

    private List<NavButton> navButtons = new ArrayList<>();
    private NavButton activeButton = null;
    private ModernMainFrame mainFrame;

    public Sidebar(ModernMainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);
        setPreferredSize(new Dimension(expandedWidth, 0));

        initComponents();
    }

    private void initComponents() {
        // --- Header Section ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setPreferredSize(new Dimension(0, 100));
        pnlHeader.setBorder(new EmptyBorder(30, 20, 10, 20));

        JLabel lblLogo = new JLabel("RETAIL POS");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel lblSub = new JLabel("Management System");
        lblSub.setForeground(COLOR_TEXT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        
        JPanel pnlLogoWrap = new JPanel(new GridLayout(2, 1, 0, -5));
        pnlLogoWrap.setOpaque(false);
        pnlLogoWrap.add(lblLogo);
        pnlLogoWrap.add(lblSub);
        
        pnlHeader.add(pnlLogoWrap, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        // --- Buttons Section ---
        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
        pnlButtons.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        String[] menuItems = {
            "Dashboard", "Items", "Export", "Stock", "Sales", "Due", "POS", 
            "Users", "Customer", "Suppliers", "Expenses", "Reports", "Setting", "StockReport"
        };

        for (String item : menuItems) {
            NavButton btn = new NavButton(item);
            btn.addActionListener(e -> {
                setActive(btn);
                mainFrame.showPanel(item);
            });
            pnlButtons.add(btn);
            pnlButtons.add(Box.createVerticalStrut(8));
            navButtons.add(btn);
        }

        JScrollPane scrollPane = new JScrollPane(pnlButtons);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Toggle Section ---
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setOpaque(false);
        pnlFooter.setPreferredSize(new Dimension(0, 70));
        pnlFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton btnToggle = new JButton("COLLAPSE");
        btnToggle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnToggle.setForeground(COLOR_TEXT);
        btnToggle.setContentAreaFilled(false);
        btnToggle.setBorderPainted(false);
        btnToggle.setFocusPainted(false);
        btnToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnToggle.addActionListener(e -> {
            isExpanded = !isExpanded;
            if (isExpanded) {
                setPreferredSize(new Dimension(expandedWidth, 0));
                btnToggle.setText("COLLAPSE");
                pnlLogoWrap.setVisible(true);
                for (NavButton btn : navButtons) btn.setExpanded(true);
            } else {
                setPreferredSize(new Dimension(collapsedWidth, 0));
                btnToggle.setText(">>");
                pnlLogoWrap.setVisible(false);
                for (NavButton btn : navButtons) btn.setExpanded(false);
            }
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        pnlFooter.add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setForeground(new Color(40, 42, 54));
        }}, BorderLayout.NORTH);
        pnlFooter.add(btnToggle, BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);
        
        if (!navButtons.isEmpty()) {
            setActive(navButtons.get(0));
        }
    }

    public void setCompactMode(boolean compact) {
        if (isExpanded != !compact) {
            isExpanded = !compact;
            if (isExpanded) {
                setPreferredSize(new Dimension(expandedWidth, 0));
                // We need to find the logo wrap and toggle button to update them
                // This is a bit hacky without fields, but we can update button text
            } else {
                setPreferredSize(new Dimension(collapsedWidth, 0));
            }
            for (NavButton btn : navButtons) btn.setExpanded(isExpanded);
            revalidate();
            repaint();
        }
    }

    private void setActive(NavButton btn) {
        if (activeButton != null) {
            activeButton.setActive(false);
        }
        activeButton = btn;
        activeButton.setActive(true);
    }

    /**
     * Custom Button with Vector Icons rendered via Graphics2D
     */
    private class NavButton extends JButton {
        private String type;
        private boolean isActive = false;
        private boolean isHovered = false;
        private boolean isExpanded = true;

        public NavButton(String type) {
            this.type = type;
            
            setAlignmentX(Component.CENTER_ALIGNMENT);
            setMaximumSize(new Dimension(200, 44));
            setMinimumSize(new Dimension(50, 44));
            setPreferredSize(new Dimension(200, 44));
            
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            });
        }

        public void setActive(boolean active) { this.isActive = active; repaint(); }

        public void setExpanded(boolean expanded) {
            this.isExpanded = expanded;
            if (isExpanded) {
                setPreferredSize(new Dimension(200, 44));
                setMaximumSize(new Dimension(200, 44));
            } else {
                setPreferredSize(new Dimension(50, 44));
                setMaximumSize(new Dimension(50, 44));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background Shape
            if (isActive) {
                g2.setColor(COLOR_ACTIVE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            } else if (isHovered) {
                g2.setColor(COLOR_HOVER);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }

            // Icon Color & Drawing
            g2.setColor(isActive ? COLOR_TEXT_ACTIVE : (isHovered ? Color.WHITE : COLOR_TEXT));
            int iconSize = 18;
            int iconX = isExpanded ? 15 : (getWidth() - iconSize) / 2;
            int iconY = (getHeight() - iconSize) / 2;
            
            drawVectorIcon(g2, type, iconX, iconY, iconSize);

            // Label Text
            if (isExpanded) {
                g2.setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 13));
                // Handle naming consistency (reporte -> Reports)
                String label = type.equals("reporte") ? "Reports" : type;
                g2.drawString(label, 50, (getHeight() / 2) + 5);
            }

            g2.dispose();
        }

        private void drawVectorIcon(Graphics2D g2, String type, int x, int y, int size) {
            g2.setStroke(new BasicStroke(1.8f));
            int mid = size / 2;
            
            switch (type) {
                case "Dashboard":
                    g2.drawRect(x, y, 7, 7);
                    g2.drawRect(x + 10, y, 7, 7);
                    g2.drawRect(x, y + 10, 7, 7);
                    g2.fillRect(x + 10, y + 10, 8, 8); // One solid for accent
                    break;
                case "Items":
                    g2.drawRect(x, y, size, size);
                    g2.drawLine(x, y + 6, x + size, y + 6);
                    g2.drawLine(x, y + 12, x + size, y + 12);
                    break;
                case "Export":
                    g2.drawRect(x, y + 6, size, size - 6);
                    g2.drawLine(x + mid, y, x + mid, y + 10);
                    g2.drawLine(x + mid - 4, y + 4, x + mid, y);
                    g2.drawLine(x + mid + 4, y + 4, x + mid, y);
                    break;
                case "Stock":
                case "StockReport":
                    // Isometric Box Icon
                    g2.drawRect(x + 2, y + 4, size - 4, size - 4);
                    g2.drawLine(x + 2, y + 4, x + mid, y);
                    g2.drawLine(x + size - 2, y + 4, x + mid, y);
                    break;
                case "Sales":
                    g2.drawOval(x, y, size, size);
                    g2.drawLine(x + mid, y + 4, x + mid, y + size - 4);
                    g2.drawLine(x + mid - 3, y + 7, x + mid + 3, y + 11);
                    break;
                case "Due":
                    g2.drawOval(x, y, size, size);
                    g2.setFont(new Font("Arial", Font.BOLD, 12));
                    g2.drawString("!", x + mid - 2, y + size - 4);
                    break;
                case "POS":
                    g2.drawRect(x, y, size, size);
                    g2.drawRect(x + 3, y + 3, size - 6, size - 6);
                    g2.fillRect(x + 7, y + 7, 4, 4);
                    break;
                case "Users":
                case "Customer":
                    g2.drawOval(x + 4, y, 10, 10);
                    g2.drawArc(x, y + 11, size, 10, 0, 180);
                    break;
                case "Suppliers":
                    g2.drawRect(x, y + 4, size, 10);
                    g2.drawOval(x + 2, y + 14, 4, 4);
                    g2.drawOval(x + size - 6, y + 14, 4, 4);
                    break;
                case "Expenses":
                    g2.drawRect(x, y + 2, size, 14);
                    g2.drawLine(x, y + 8, x + size, y + 8);
                    g2.drawOval(x + mid - 3, y + 4, 6, 2);
                    break;
                case "Reports":
                    g2.drawLine(x, y + size, x + size, y + size);
                    g2.fillRect(x, y + 10, 4, 8);
                    g2.fillRect(x + 7, y + 4, 4, 14);
                    g2.fillRect(x + 14, y + 12, 4, 6);
                    break;
                case "Setting":
                    g2.drawOval(x + 4, y + 4, 10, 10);
                    for(int i=0; i<8; i++) {
                        double a = Math.toRadians(i * 45);
                        g2.drawLine((int)(x+mid+5*Math.cos(a)), (int)(y+mid+5*Math.sin(a)), 
                                     (int)(x+mid+8*Math.cos(a)), (int)(y+mid+8*Math.sin(a)));
                    }
                    break;
                default:
                    g2.drawRect(x, y, size, size);
                    break;
            }
        }
    }
}
