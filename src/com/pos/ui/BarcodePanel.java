package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class BarcodePanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color vibrantBlue = new Color(33, 150, 243);
    private final Color outOfStockRed = new Color(211, 47, 47);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color headerBg = primaryBlue;

    private JTable table;
    private DefaultTableModel tableModel;

    public BarcodePanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(headerBg);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);

        JButton btnBack = createHeaderButton("<", true);
        btnBack.addActionListener(e -> mainFrame.showPanel("Items"));

        JButton btnMainPanel = createHeaderButton("Go to Main Panel", false);
        btnMainPanel.addActionListener(e -> mainFrame.showPanel("Dashboard"));

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnBack);
        pnlTopLeft.add(btnMainPanel);
        pnlTopLeft.add(btnPOS);
        pnlTopHeader.add(pnlTopLeft, BorderLayout.WEST);

        JPanel pnlLogo = new JPanel(new GridBagLayout());
        pnlLogo.setOpaque(false);
        JLabel lblLogo = new JLabel("POS SYSTEM");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlLogo.add(lblLogo);
        pnlTopHeader.add(pnlLogo, BorderLayout.CENTER);

        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Evening!</span><br>Welcome Pos System</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = new JButton("\u23FB");
        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);

        // Breadcrumbs & Visibility
        JPanel pnlTopActions = new JPanel(new BorderLayout());
        pnlTopActions.setOpaque(false);
        pnlTopActions.setBorder(new EmptyBorder(10, 20, 0, 20));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Items > QR/Barcode");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnColVisibility = createActionButton("Column Visibility", tableHeaderBlue);
        btnColVisibility.setPreferredSize(new Dimension(150, 35));
        pnlTopActions.add(btnColVisibility, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Filters Container
        JPanel pnlContainer = new JPanel(new BorderLayout());
        pnlContainer.setBackground(Color.WHITE);
        pnlContainer.setBorder(new EmptyBorder(0, 40, 40, 40));

        // Search & Filter Header
        JPanel pnlFilters = new JPanel(new BorderLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(15, 0, 15, 0));

        // Left: Search
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Search"));
        JTextField txtSearch = new JTextField("Enter item name", 15);
        txtSearch.setPreferredSize(new Dimension(0, 35));
        txtSearch.setForeground(Color.GRAY);
        pnlSearch.add(txtSearch);
        pnlSearch.add(createActionButton("Search", tableHeaderBlue));
        pnlSearch.add(createActionButton("Reset", Color.BLACK));
        pnlFilters.add(pnlSearch, BorderLayout.WEST);

        // Right: Entries
        JPanel pnlEntries = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlEntries.setOpaque(false);
        JTextField txtEntries = new JTextField("10", 5);
        txtEntries.setPreferredSize(new Dimension(0, 35));
        txtEntries.setHorizontalAlignment(SwingConstants.CENTER);
        pnlEntries.add(txtEntries);
        pnlEntries.add(new JLabel("Entries"));
        pnlFilters.add(pnlEntries, BorderLayout.EAST);

        pnlContainer.add(pnlFilters, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"#", "ITEM IMAGE", "ITEM NAME", "ITEM CODE", "QTY", "CATEGORY", "SUBCATEGORY", "STATUS", "MANAGE"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(60);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(245, 245, 250));

        // Styling Table Header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(tableHeaderBlue);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return lbl;
            }
        });

        // Custom Cell Rendering
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                c.setForeground(Color.BLACK);
                ((JComponent)c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
                return c;
            }
        });

        setupTableRenderers();
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlContainer.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlContainer, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupTableRenderers() {
        table.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new ManageRenderer());
        
        // Preferred Widths
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(8).setPreferredWidth(300);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "", "Sample Product 1", "6472", "18", "Dairy Products", "N/A", "Out Of Stock", ""});
        tableModel.addRow(new Object[]{"2", "", "Anchor Cheese Slices 12S 200g", "ITEM002", "96", "Dairy Products", "N/A", "In Stock", ""});
        tableModel.addRow(new Object[]{"3", "", "Tomato", "2693", "49", "Vegetables", "N/A", "In Stock", ""});
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

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(100, 35));
        return btn;
    }

    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            String status = value.toString();
            Color statusColor = status.equals("In Stock") ? actionBlue : outOfStockRed;
            
            JLabel lbl = new JLabel(status);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setForeground(statusColor);
            lbl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(statusColor, 1, true),
                new EmptyBorder(2, 10, 2, 10)
            ));
            
            p.add(lbl);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }

    class ManageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JButton btnQr = styleManageButton("QR Code", actionBlue);
            JButton btnBar = styleManageButton("Barcode", actionBlue);
            JButton btnPrint = styleManageButton("Print Item Card", vibrantBlue);
            
            p.add(btnQr);
            p.add(btnBar);
            p.add(btnPrint);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
        
        private JButton styleManageButton(String text, Color bg) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(null);
            btn.setOpaque(true);
            if (text.contains("Print")) {
                btn.setPreferredSize(new Dimension(110, 32));
            } else {
                btn.setPreferredSize(new Dimension(70, 32));
            }
            return btn;
        }
    }
}
