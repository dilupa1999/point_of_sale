package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ExportItemsPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color breadcrumbGray = new Color(100, 100, 100);
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontLabel = new Font("Segoe UI", Font.PLAIN, 12);

    public ExportItemsPanel(MainFrame mainFrame) {
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
        JLabel lblBreadcrumb = new JLabel("Main Panel > Items > Export Items");
        lblBreadcrumb.setForeground(breadcrumbGray);
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumb.add(lblBreadcrumb);
        pnlMain.add(pnlBreadcrumb, BorderLayout.NORTH);

        // Content Area 
        JPanel pnlContent = new JPanel(new BorderLayout(0, 20));
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // 1. Controls Section (Item Type Dropdown + Buttons)
        JPanel pnlControls = new JPanel(new BorderLayout());
        pnlControls.setOpaque(false);

        // Left Controls: Item Type
        JPanel pnlType = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        pnlType.setOpaque(false);
        pnlType.setLayout(new BoxLayout(pnlType, BoxLayout.Y_AXIS));
        
        JLabel lblType = new JLabel("Item Type");
        lblType.setFont(fontLabel);
        lblType.setForeground(Color.GRAY);
        
        String[] types = {"Scale Items", "Non-Scale Items", "All Items"};
        JComboBox<String> comboType = new JComboBox<>(types);
        comboType.setPreferredSize(new Dimension(150, 35));
        comboType.setBackground(Color.WHITE);
        
        pnlType.add(lblType);
        pnlType.add(Box.createVerticalStrut(5));
        pnlType.add(comboType);

        // Right Controls: Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        pnlButtons.setOpaque(false);

        JButton btnExport = new JButton("Export Selected");
        btnExport.setBackground(primaryBlue);
        btnExport.setForeground(Color.WHITE);
        btnExport.setFont(fontTitle);
        btnExport.setFocusPainted(false);
        btnExport.setPreferredSize(new Dimension(160, 40));
        btnExport.setIcon(new ExportIcon("Export", 18, 18, Color.WHITE));

        JButton btnSync = new JButton();
        btnSync.setBackground(new Color(60, 70, 90));
        btnSync.setPreferredSize(new Dimension(40, 40));
        btnSync.setIcon(new ExportIcon("Sync", 20, 20, Color.WHITE));
        btnSync.setFocusPainted(false);

        pnlButtons.add(btnExport);
        pnlButtons.add(btnSync);

        pnlControls.add(pnlType, BorderLayout.WEST);
        pnlControls.add(pnlButtons, BorderLayout.EAST);

        pnlContent.add(pnlControls, BorderLayout.NORTH);

        // 2. Table Section
        String[] columns = {"", "ITEM CODE", "ITEM NAME", "CATEGORY", "UNIT", "RETAIL PRICE", "TYPE - (SCALE GROUP NO IF ANY)", "MANAGE"};
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowGrid(false);
        table.setSelectionBackground(new Color(245, 245, 250));

        // Styling Table Header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(new Color(248, 249, 252));
                lbl.setForeground(new Color(140, 150, 170));
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(230, 235, 245)));
                return lbl;
            }
        });

        // Column widths
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        // Renderers & Sample Data
        setupTableRenderers(table);
        addSampleData(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        pnlContent.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlContent, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupTableRenderers(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 245)));
                if (isSelected) {
                    c.setBackground(new Color(245, 245, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
    }

    private void addSampleData(DefaultTableModel model) {
        model.addRow(new Object[]{false, "IT001", "Ambewela Milk 1L", "Dairy", "NOS", "450.00", "0", "Manage"});
        model.addRow(new Object[]{false, "IT002", "Munchee Super Cream Cracker", "Bakery", "NOS", "180.00", "0", "Manage"});
        model.addRow(new Object[]{false, "IT003", "Anchor Milk Powder 400g", "Dairy", "NOS", "1100.00", "0", "Manage"});
        model.addRow(new Object[]{false, "IT004", "Sunlight Soap 115g", "Beauty", "NOS", "120.00", "0", "Manage"});
        model.addRow(new Object[]{false, "IT005", "Vantastic Chocolate 50g", "Snacks", "NOS", "150.00", "0", "Manage"});
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
    private static class ExportIcon implements Icon {
        private final String type;
        private final int w, h;
        private final Color color;

        public ExportIcon(String type, int w, int h, Color color) {
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
                case "Export":
                    g2.drawRect(cx-8, cy-4, 16, 12);
                    g2.drawLine(cx, cy, cx, cy-8);
                    g2.drawLine(cx, cy-8, cx-3, cy-5);
                    g2.drawLine(cx, cy-8, cx+3, cy-5);
                    break;
                case "Sync":
                    g2.drawArc(4, 4, w-8, h-8, 45, 270);
                    g2.drawLine(w-6, cy, w-2, cy-4);
                    g2.drawLine(w-6, cy, w-10, cy-4);
                    break;
            }
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
