package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ItemsListPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color btnResetGray = new Color(80, 80, 85);
    private final Color tableHeaderGreen = new Color(100, 175, 80);
    private final Color outOfStockRed = new Color(211, 47, 47);
    private final Color headerBg = primaryBlue;

    private JTable table;
    private DefaultTableModel tableModel;

    public ItemsListPanel(MainFrame mainFrame) {
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
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnPower.setForeground(Color.BLACK);
        btnPower.setBackground(Color.WHITE);
        btnPower.setPreferredSize(new Dimension(40, 40));
        btnPower.setFocusPainted(false);
        btnPower.setBorder(null);

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);

        // Breadcrumbs & Column Visibility
        JPanel pnlTopActions = new JPanel(new BorderLayout());
        pnlTopActions.setOpaque(false);
        pnlTopActions.setBorder(new EmptyBorder(10, 20, 0, 20));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Items > Items List");
        lblBreadcrumb.setForeground(new Color(100, 100, 100)); // Darker gray for better visibility
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnColVisibility = createActionButton("Column Visibility", tableHeaderGreen);
        btnColVisibility.setPreferredSize(new Dimension(150, 35));
        pnlTopActions.add(btnColVisibility, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Search & Filter Header
        JPanel pnlFilters = new JPanel(new BorderLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Left Filters (Search)
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Search"));
        JTextField txtSearch = new JTextField(15);
        txtSearch.setPreferredSize(new Dimension(0, 35));
        pnlSearch.add(txtSearch);
        pnlSearch.add(createActionButton("Search", vibrantGreen));
        pnlSearch.add(createActionButton("Reset", tableHeaderGreen));
        pnlFilters.add(pnlSearch, BorderLayout.WEST);

        // Right Filters (Sort)
        JPanel pnlSort = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlSort.setOpaque(false);
        pnlSort.add(new JLabel("Sort by:"));
        pnlSort.add(new JComboBox<>(new String[]{"Item Code", "Item Name", "Price"}));
        pnlSort.add(new JComboBox<>(new String[]{"Ascending", "Descending"}));
        pnlFilters.add(pnlSort, BorderLayout.EAST);

        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.setBackground(Color.WHITE);
        pnlTableContainer.setBorder(new EmptyBorder(0, 40, 40, 40)); // Added space around the table body
        pnlTableContainer.add(pnlFilters, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"ITEM CODE", "ITEM IMAGE", "ITEM NAME - (SINGLISH NAME IF ANY)", "QTY", "UNIT TYPE", "STATUS", "MANAGE"};
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
                lbl.setBackground(tableHeaderGreen);
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
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
                return c;
            }
        });

        // Special Renderer for Manage and Status columns would go here
        setupTableRenderers();

        // Sample Data
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlTableContainer.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlTableContainer, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupTableRenderers() {
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusRenderer()); // Status
        table.getColumnModel().getColumn(6).setCellRenderer(new ManageRenderer()); // Manage
        
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(200); // More space for buttons
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"3539", "", "Kellogg's Chocos 250g - (siriyal)", "100", "g", "In Stock", ""});
        tableModel.addRow(new Object[]{"6711", "", "Kellogg's Chocos 127g - (siriyal)", "103", "g", "In Stock", ""});
        tableModel.addRow(new Object[]{"4128", "", "Eggs Bulk Large - (Biththra)", "100", "Pieces", "In Stock", ""});
    }

    private void setupTable() {
        // Implementation for custom renderers and listeners
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
        btn.setPreferredSize(new Dimension(80, 35));
        return btn;
    }

    // --- Custom Renderers ---
    
    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JLabel lbl = new JLabel("In Stock");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(vibrantGreen);
            lbl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(vibrantGreen, 1, true),
                new EmptyBorder(4, 12, 4, 12)
            ));
            
            p.add(lbl);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }

    class ManageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JButton btnEdit = new JButton("Edit");
            styleManageButton(btnEdit, Color.WHITE, Color.GRAY);
            btnEdit.setPreferredSize(new Dimension(60, 35));
            
            JButton btnStock = new JButton("Out Of stock");
            styleManageButton(btnStock, outOfStockRed, Color.WHITE);
            btnStock.setPreferredSize(new Dimension(110, 35));
            
            p.add(btnEdit);
            p.add(btnStock);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
        
        private void styleManageButton(JButton btn, Color bg, Color fg) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setBackground(bg);
            btn.setForeground(fg);
            btn.setFocusPainted(false);
            if(bg == Color.WHITE) {
                btn.setBorder(new LineBorder(new Color(230, 230, 235)));
            } else {
                btn.setBorder(null);
                btn.setOpaque(true);
            }
        }
    }
}
