package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CategoryListPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color headerBg = primaryBlue;

    private JTable table;
    private DefaultTableModel tableModel;

    public CategoryListPanel(MainFrame mainFrame) {
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

        // Breadcrumbs & Visibility
        JPanel pnlTopActions = new JPanel(new BorderLayout());
        pnlTopActions.setOpaque(false);
        pnlTopActions.setBorder(new EmptyBorder(10, 20, 0, 20));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Item > Category List");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnColVisibility = createActionButton("Column Visibility", tableHeaderBlue);
        btnColVisibility.setPreferredSize(new Dimension(150, 35));
        pnlTopActions.add(btnColVisibility, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Search & Entries Filters
        JPanel pnlFilters = new JPanel(new BorderLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Left: Search
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Search"));
        JTextField txtSearch = new JTextField("Enter Category name", 15);
        txtSearch.setPreferredSize(new Dimension(0, 35));
        txtSearch.setForeground(Color.GRAY);
        pnlSearch.add(txtSearch);
        pnlSearch.add(createActionButton("Search", actionBlue));
        pnlFilters.add(pnlSearch, BorderLayout.WEST);

        // Center: Entries
        JPanel pnlEntries = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlEntries.setOpaque(false);
        JTextField txtEntries = new JTextField("30", 5);
        txtEntries.setPreferredSize(new Dimension(0, 35));
        txtEntries.setHorizontalAlignment(SwingConstants.CENTER);
        pnlEntries.add(txtEntries);
        pnlEntries.add(new JLabel("Entries"));
        pnlFilters.add(pnlEntries, BorderLayout.CENTER);

        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.setBackground(Color.WHITE);
        pnlTableContainer.setBorder(new EmptyBorder(0, 40, 40, 40)); // Added space around the body
        pnlTableContainer.add(pnlFilters, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"#", "CATEGORY", "DESCRIPTION", "MANAGE"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(50);
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
                if (column == 0) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                    ((JLabel)c).setBorder(new EmptyBorder(0, 10, 0, 10));
                }
                c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                c.setForeground(Color.BLACK);
                ((JComponent)c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
                return c;
            }
        });

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
        table.getColumnModel().getColumn(3).setCellRenderer(new ManageRenderer()); // Manage
        
        // Set column widths
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "Dairy Products", "Milk, cheese, butter, yogurt, and other fresh dairy items.", ""});
        tableModel.addRow(new Object[]{"2", "Bakery & Eggs", "Bread, pastries, eggs, and other bakery essentials.", ""});
        tableModel.addRow(new Object[]{"3", "Dry Goods & Pantry", "Rice, sugar, flour, spices, and other non-perishable pantry staples.", ""});
        tableModel.addRow(new Object[]{"4", "Cooking Essentials", "Oil, sauces, seasoning mixes, and cooking ingredients.", ""});
        tableModel.addRow(new Object[]{"5", "Canned & Packaged Goods", "Canned meats, canned fish, ready-to-eat meals, and pantry items.", ""});
        tableModel.addRow(new Object[]{"6", "Household Supplies", "Tissue, cleaning supplies, detergents, and other home essentials.", ""});
        tableModel.addRow(new Object[]{"7", "Vegetables", "aa", ""});
        tableModel.addRow(new Object[]{"8", "Fruits", "bb", ""});
        tableModel.addRow(new Object[]{"9", "Rice", "cc", ""});
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

    class ManageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JButton btnEdit = new JButton("Edit");
            btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnEdit.setBackground(Color.WHITE);
            btnEdit.setForeground(Color.GRAY);
            btnEdit.setFocusPainted(false);
            btnEdit.setBorder(new LineBorder(new Color(230, 230, 235)));
            btnEdit.setPreferredSize(new Dimension(60, 32));
            
            p.add(btnEdit);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }
}
