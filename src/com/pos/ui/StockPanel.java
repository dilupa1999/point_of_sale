package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class StockPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color headerBlue = new Color(30, 136, 229);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Color breadcrumbGray = new Color(100, 100, 100);

    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel pnlFilterBar;
    private JPanel pnlSearch;
    private JPanel pnlEntries;
    private JPanel pnlBreadcrumbRow;
    private JPanel pnlExportActions;

    public StockPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
        setupResponsiveness();
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(primaryBlue);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnPOS);
        pnlTopHeader.add(pnlTopLeft, BorderLayout.WEST);

        // Center Title
        JPanel pnlCenterTitle = new JPanel(new GridBagLayout());
        pnlCenterTitle.setOpaque(false);
        JLabel lblTopTitle = new JLabel("POS SYSTEM");
        lblTopTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTopTitle.setForeground(Color.WHITE);
        pnlCenterTitle.add(lblTopTitle);
        pnlTopHeader.add(pnlCenterTitle, BorderLayout.CENTER);

        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTopRight.setOpaque(false);
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Morning!</span><br>Welcome POS System</div></html>");
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
        pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Breadcrumbs and Top Actions
        pnlBreadcrumbRow = new JPanel(new BorderLayout());
        pnlBreadcrumbRow.setOpaque(false);
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Stock");
        lblBreadcrumb.setForeground(breadcrumbGray);
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumbRow.add(lblBreadcrumb, BorderLayout.WEST);

        pnlExportActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pnlExportActions.setOpaque(false);
        String[] exportLabels = {"Copy", "CSV", "Excel", "PDF", "Column Visibility"};
        for (String label : exportLabels) {
            JButton btn = createMiniButton(label, label.equals("Column Visibility") ? headerBlue : primaryBlue);
            pnlExportActions.add(btn);
        }
        pnlBreadcrumbRow.add(pnlExportActions, BorderLayout.EAST);

        pnlMain.add(pnlBreadcrumbRow, BorderLayout.NORTH);

        // Content Area
        JPanel pnlContent = new JPanel(new BorderLayout(0, 15));
        pnlContent.setBackground(Color.WHITE);

        // 1. Filter Bar
        pnlFilterBar = new JPanel(new BorderLayout());
        pnlFilterBar.setOpaque(false);
        pnlFilterBar.setBorder(new EmptyBorder(10, 0, 10, 0));

        pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setOpaque(false);

        JLabel lblCat = new JLabel("Category:  ");
        lblCat.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JComboBox<String> comboCat = new JComboBox<>(new String[]{"All Categories", "Dairy", "Bakery", "Drinks", "Fruits", "Snacks", "Beverages", "Spices", "Grains"});
        comboCat.setPreferredSize(new Dimension(180, 35));
        comboCat.setBackground(fieldBg);
        SearchableComboBox.install(comboCat);

        JLabel lblSearch = new JLabel("Search:  ");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField txtSearch = new JTextField("Enter item name or code");
        txtSearch.setPreferredSize(new Dimension(200, 35));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBackground(fieldBg);
        txtSearch.setBorder(new LineBorder(new Color(230, 230, 235)));

        JButton btnFilter = createMiniButton("Filter", primaryBlue);
        JButton btnClear = createMiniButton("Clear", Color.BLACK);

        pnlSearch.add(lblCat);
        pnlSearch.add(comboCat);
        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnFilter);
        pnlSearch.add(btnClear);

        pnlEntries = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlEntries.setOpaque(false);
        JLabel lblShow = new JLabel("Show");
        JTextField txtEntries = new JTextField("30");
        txtEntries.setPreferredSize(new Dimension(50, 35));
        txtEntries.setHorizontalAlignment(SwingConstants.CENTER);
        txtEntries.setBackground(fieldBg);
        txtEntries.setBorder(new LineBorder(new Color(230, 230, 235)));
        JLabel lblEntries = new JLabel("Entries");
        
        pnlEntries.add(lblShow);
        pnlEntries.add(txtEntries);
        pnlEntries.add(lblEntries);

        pnlFilterBar.add(pnlSearch, BorderLayout.WEST);
        pnlFilterBar.add(pnlEntries, BorderLayout.EAST);

        pnlContent.add(pnlFilterBar, BorderLayout.NORTH);

        // 2. Table Section
        String[] columns = {"#", "ITEM CODE", "ITEM NAME", "CATEGORY", "QUANTITY", "UNIT TYPE", "MANAGE"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(45);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(245, 245, 250));

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(headerBlue);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return lbl;
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
                return c;
            }
        });

        // Custom Renderer for "Manage" column
        table.getColumn("MANAGE").setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
                p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                JButton btn = new JButton("Add Stock");
                btn.setBackground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                btn.setPreferredSize(new Dimension(100, 30));
                btn.setBorder(new LineBorder(new Color(220, 220, 225)));
                p.add(btn);
                return p;
            }
        });

        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        pnlContent.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlContent, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "6472", "Sample Product 1", "Dairy Products", "18", "Pieces", ""});
        tableModel.addRow(new Object[]{"2", "2341", "Ambewela Milk 1L", "Dairy Products", "45", "Pieces", ""});
        tableModel.addRow(new Object[]{"3", "9082", "Anchor Powder 400g", "Dairy Products", "12", "Pieces", ""});
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                
                // Breadcrumb Row Responsiveness
                if (width < 1100) {
                    pnlBreadcrumbRow.remove(pnlExportActions);
                    pnlBreadcrumbRow.setLayout(new GridLayout(2, 1, 0, 10));
                    pnlBreadcrumbRow.add(pnlBreadcrumbRow.getComponent(0)); 
                    pnlBreadcrumbRow.add(pnlExportActions);
                    ((FlowLayout)pnlExportActions.getLayout()).setAlignment(FlowLayout.LEFT);
                } else {
                    pnlBreadcrumbRow.remove(pnlExportActions);
                    pnlBreadcrumbRow.setLayout(new BorderLayout());
                    pnlBreadcrumbRow.add(pnlExportActions, BorderLayout.EAST);
                    ((FlowLayout)pnlExportActions.getLayout()).setAlignment(FlowLayout.RIGHT);
                }

                // Filter Bar Responsiveness
                if (width < 950) {
                    pnlFilterBar.remove(pnlSearch);
                    pnlFilterBar.remove(pnlEntries);
                    pnlFilterBar.setLayout(new GridLayout(2, 1, 0, 10));
                    pnlFilterBar.add(pnlSearch);
                    pnlFilterBar.add(pnlEntries);
                    ((FlowLayout)pnlEntries.getLayout()).setAlignment(FlowLayout.LEFT);
                } else {
                    pnlFilterBar.remove(pnlSearch);
                    pnlFilterBar.remove(pnlEntries);
                    pnlFilterBar.setLayout(new BorderLayout());
                    pnlFilterBar.add(pnlSearch, BorderLayout.WEST);
                    pnlFilterBar.add(pnlEntries, BorderLayout.EAST);
                    ((FlowLayout)pnlEntries.getLayout()).setAlignment(FlowLayout.RIGHT);
                }
                
                revalidate();
                repaint();
            }
        });
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
        btn.setForeground(new Color(50, 50, 50));
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
        btn.setPreferredSize(new Dimension(text.length() > 10 ? 150 : 100, 35));
        return btn;
    }
}
