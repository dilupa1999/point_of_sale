package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ExpensesPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    private JPanel pnlGrid;

    public ExpensesPanel() {
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

        JLabel lblBreadcrumb = new JLabel("Main Panel > Expenses");
        lblBreadcrumb.setForeground(breadcrumbGray);
        pnlHeader.add(lblBreadcrumb, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Content Area ---
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Grid Area
        pnlGrid = new JPanel(new GridLayout(0, 4, 20, 20));
        pnlGrid.setOpaque(false);

        // Adding the Menu Cards
        pnlGrid.add(createMenuCard("Add New Expense", "AddMoney"));
        pnlGrid.add(createMenuCard("Expenses List", "MoneyList"));
        pnlGrid.add(createMenuCard("Expense Categories", "Group"));
        pnlGrid.add(createMenuCard("Import Expenses", "Import"));

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
        JButton btn = new JButton(new ExpenseMenuIcon(iconType, 48, 48, Color.WHITE));
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

    private static class ExpenseMenuIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public ExpenseMenuIcon(String type, int w, int h, Color color) {
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
                case "AddMoney":
                    g2.drawRect(cx-12, cy-8, 24, 16);
                    g2.drawOval(cx-4, cy-4, 8, 8);
                    g2.drawLine(cx-15, cy-12, cx-7, cy-12);
                    g2.drawLine(cx-11, cy-16, cx-11, cy-8);
                    break;
                case "MoneyList":
                    g2.drawRect(cx-12, cy-8, 24, 16);
                    g2.drawOval(cx-4, cy-4, 8, 8);
                    g2.drawLine(cx-12, cy-12, cx+12, cy-12);
                    break;
                case "Group":
                    g2.drawRect(cx-10, cy-10, 20, 20);
                    g2.drawLine(cx-10, cy, cx+10, cy);
                    g2.drawLine(cx, cy-10, cx, cy+10);
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
