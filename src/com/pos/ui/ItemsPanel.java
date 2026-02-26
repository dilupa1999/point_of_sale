package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ItemsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);

    private JPanel pnlGrid;

    public ItemsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
        setupResponsiveness();
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header (Blue) 
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(primaryBlue);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Left section: Back, Main Panel, POS
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

        // --- Content Area Wrapper ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);

        // Breadcrumbs
        JPanel pnlBreadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        pnlBreadcrumb.setOpaque(false);
        JLabel lblBreadcrumb = new JLabel("Main Panel > Items");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumb.add(lblBreadcrumb);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

        // Content Area 
        JPanel pnlContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        pnlGrid = new JPanel(new GridLayout(0, 4, 20, 20)); 
        pnlGrid.setOpaque(false);

        // Adding the Menu Cards
        JButton btnAddItems = createMenuCard("Add New Items", "AddItem");
        btnAddItems.addActionListener(e -> mainFrame.showPanel("AddItems"));
        pnlGrid.add(btnAddItems);
        
        JButton btnAddCategory = createMenuCard("Add New Category", "AddCategory");
        btnAddCategory.addActionListener(e -> mainFrame.showPanel("AddCategory"));
        pnlGrid.add(btnAddCategory);
        
        JButton btnItemsList = createMenuCard("Items List", "ItemList");
        btnItemsList.addActionListener(e -> mainFrame.showPanel("ItemsList"));
        pnlGrid.add(btnItemsList);
        
        JButton btnCategoryList = createMenuCard("Category List", "CategoryList");
        btnCategoryList.addActionListener(e -> mainFrame.showPanel("CategoryList"));
        pnlGrid.add(btnCategoryList);
        
        JButton btnImportItem = createMenuCard("Import Item", "Import");
        btnImportItem.addActionListener(e -> mainFrame.showPanel("ImportItems"));
        pnlGrid.add(btnImportItem);
        
        JButton btnBarcode = createMenuCard("Genarate QR/Barcode", "Barcode");
        btnBarcode.addActionListener(e -> mainFrame.showPanel("Barcode"));
        pnlGrid.add(btnBarcode);

        pnlContent.add(pnlGrid);

        pnlMain.add(new JScrollPane(pnlContent), BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int cols = width > 1200 ? 5 : (width > 900 ? 3 : (width > 600 ? 2 : 1));
                ((GridLayout) pnlGrid.getLayout()).setColumns(cols);
                pnlGrid.revalidate();
            }
        });
    }

    private JButton createMenuCard(String title, String iconType) {
        JButton btn = new JButton(new MenuIcon(iconType, 48, 48, Color.WHITE));
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

    // --- Custom Icon Renderer ---
    private static class MenuIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public MenuIcon(String type, int w, int h, Color color) {
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
                case "AddItem":
                    g2.drawRect(cx-10, cy-10, 16, 16);
                    g2.drawLine(cx+4, cy-4, cx+12, cy-4);
                    g2.drawLine(cx+8, cy-8, cx+8, cy);
                    break;
                case "AddCategory":
                    g2.drawOval(cx-10, cy-10, 20, 20);
                    g2.drawLine(cx-5, cy, cx+5, cy);
                    g2.drawLine(cx, cy-5, cx, cy+5);
                    break;
                case "ItemList":
                    g2.drawRect(cx-10, cy-12, 20, 24);
                    g2.drawLine(cx-6, cy-6, cx+6, cy-6);
                    g2.drawLine(cx-6, cy, cx+6, cy);
                    g2.drawLine(cx-6, cy+6, cx+6, cy+6);
                    break;
                case "CategoryList":
                    g2.drawLine(cx-10, cy-8, cx-10, cy+8);
                    g2.drawLine(cx-6, cy-8, cx+10, cy-8);
                    g2.drawLine(cx-6, cy, cx+10, cy);
                    g2.drawLine(cx-6, cy+8, cx+10, cy+8);
                    break;
                case "Import":
                    g2.drawRect(cx-10, cy-6, 20, 16);
                    g2.drawLine(cx, cy, cx, cy-12);
                    g2.drawLine(cx-4, cy-4, cx, cy);
                    g2.drawLine(cx+4, cy-4, cx, cy);
                    break;
                case "Barcode":
                    g2.drawRect(cx-12, cy-12, 24, 24);
                    g2.fillRect(cx-8, cy-8, 6, 6);
                    g2.fillRect(cx+2, cy-8, 6, 6);
                    g2.fillRect(cx-8, cy+2, 6, 6);
                    g2.fillRect(cx+2, cy+2, 2, 2);
                    break;
            }
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
