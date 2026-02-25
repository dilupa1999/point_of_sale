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
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color btnResetGray = new Color(80, 80, 85);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color outOfStockRed = new Color(211, 47, 47);
    private final Color headerBg = primaryBlue;

    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel pnlSearch;
    private JPanel pnlSort;
    private JPanel pnlFiltersRow;

    public ItemsListPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
        setupResponsiveness();
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
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Morning!</span><br>Welcome Hypermart</div></html>");
        lblWelcome.setForeground(Color.WHITE);
        
        JButton btnPower = createHeaderButton("\u23FB", true);
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));

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
        pnlTopActions.setBorder(new EmptyBorder(10, 25, 0, 25));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Items > Items List");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnColVisibility = createMiniButton("Column Visibility", tableHeaderBlue);
        pnlTopActions.add(btnColVisibility, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Search & Filter Header
        pnlFiltersRow = new JPanel(new BorderLayout());
        pnlFiltersRow.setBackground(Color.WHITE);
        pnlFiltersRow.setBorder(new EmptyBorder(15, 25, 15, 25));

        // Row 1: Search (Left) and Sort (Right)
        pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Search"));
        JTextField txtSearch = new JTextField("Enter item name");
        txtSearch.setPreferredSize(new Dimension(300, 40));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(new LineBorder(new Color(230, 230, 235)));
        pnlSearch.add(txtSearch);
        pnlSearch.add(createMiniButton("Search", actionBlue));
        pnlSearch.add(createMiniButton("Reset", tableHeaderBlue));
        pnlFiltersRow.add(pnlSearch, BorderLayout.WEST);

        pnlSort = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlSort.setOpaque(false);
        pnlSort.add(new JLabel("Sort by:"));
        JComboBox<String> comboSort = new JComboBox<>(new String[]{"Item Code", "Item Name", "Price"});
        styleDropdown(comboSort);
        pnlSort.add(comboSort);
        
        JComboBox<String> comboOrder = new JComboBox<>(new String[]{"Ascending", "Descending"});
        styleDropdown(comboOrder);
        pnlSort.add(comboOrder);
        pnlFiltersRow.add(pnlSort, BorderLayout.EAST);

        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.setBackground(Color.WHITE);
        pnlTableContainer.setBorder(new EmptyBorder(0, 25, 25, 25));
        pnlTableContainer.add(pnlFiltersRow, BorderLayout.NORTH);

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
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(tableHeaderBlue);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
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

        setupTableRenderers();
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlTableContainer.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlTableContainer, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                if (width < 900) {
                    pnlFiltersRow.remove(pnlSearch);
                    pnlFiltersRow.remove(pnlSort);
                    pnlFiltersRow.setLayout(new GridLayout(2, 1, 0, 10));
                    pnlFiltersRow.add(pnlSearch);
                    pnlFiltersRow.add(pnlSort);
                    ((FlowLayout)pnlSort.getLayout()).setAlignment(FlowLayout.LEFT);
                } else {
                    pnlFiltersRow.remove(pnlSearch);
                    pnlFiltersRow.remove(pnlSort);
                    pnlFiltersRow.setLayout(new BorderLayout());
                    pnlFiltersRow.add(pnlSearch, BorderLayout.WEST);
                    pnlFiltersRow.add(pnlSort, BorderLayout.EAST);
                    ((FlowLayout)pnlSort.getLayout()).setAlignment(FlowLayout.RIGHT);
                }
                pnlFiltersRow.revalidate();
                pnlFiltersRow.repaint();
            }
        });
    }

    private void styleDropdown(JComboBox<String> combo) {
        combo.setPreferredSize(new Dimension(150, 40));
        combo.setBackground(Color.WHITE);
        combo.setBorder(new LineBorder(new Color(230, 230, 235)));
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"3539", "", "Kellogg's Chocos 250g - (siriyal)", "100", "g", "In Stock", ""});
        tableModel.addRow(new Object[]{"6711", "", "Kellogg's Chocos 127g - (siriyal)", "103", "g", "In Stock", ""});
        tableModel.addRow(new Object[]{"4128", "", "Eggs Bulk Large - (Biththra)", "100", "Pieces", "In Stock", ""});
        tableModel.addRow(new Object[]{"0021", "", "Cic Besto Eggs Omega 3 Standard 10S - (Biththra)", "47", "Pieces", "In Stock", ""});
        tableModel.addRow(new Object[]{"ITEM001", "", "Ambewela Non Fat Milk - 1l - (Sample Item Alt Name)", "100", "Pieces", "In Stock", ""});
        tableModel.addRow(new Object[]{"7688", "", "Anchor Hot Choc 400g - (kiri)", "90", "g", "In Stock", ""});
        tableModel.addRow(new Object[]{"8888", "", "Ambewela Flavoured Milk Uht Vanilla Tetra 1L - (kiri)", "99", "L", "In Stock", ""});
        tableModel.addRow(new Object[]{"0511", "", "Ambewela Flavoured Milk Uht Chocolate Tetra 1L - (kiri)", "97", "L", "In Stock", ""});
        tableModel.addRow(new Object[]{"2693", "", "Tomato - (Thakkali)", "49", "Kg", "In Stock", ""});
    }

    private void setupTableRenderers() {
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(6).setCellRenderer(new ManageRenderer());
        
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(200);
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

    private JButton createMiniButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
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
        btn.setPreferredSize(new Dimension(text.length() > 10 ? 150 : 100, 40));
        return btn;
    }


    // --- Custom Renderers ---
    
    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JLabel lbl = new JLabel("In Stock");
            lbl.setForeground(actionBlue);
            lbl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(actionBlue, 1, true),
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
