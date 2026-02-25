package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ItemStockCountPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color resetBlack = new Color(33, 33, 33);
    private final Color fieldBg = new Color(245, 245, 250);

    private JTable table;
    private DefaultTableModel tableModel;

    public ItemStockCountPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(primaryBlue);
        pnlTopHeader.setPreferredSize(new Dimension(0, 70));
        pnlTopHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlTopLeft.setOpaque(false);

        JButton btnBack = createHeaderButton("<", true);
        btnBack.addActionListener(e -> mainFrame.showPanel("Reports"));

        JButton btnMainPanel = createHeaderButton("Go to Main Panel", false);
        btnMainPanel.addActionListener(e -> mainFrame.showPanel("Dashboard"));

        JButton btnPOS = createHeaderButton("POS", false);
        btnPOS.addActionListener(e -> mainFrame.showPanel("POS"));

        pnlTopLeft.add(btnBack);
        pnlTopLeft.add(btnMainPanel);
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
        pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Breadcrumbs and Top Actions
        JPanel pnlBreadcrumbRow = new JPanel(new BorderLayout());
        pnlBreadcrumbRow.setOpaque(false);
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Item Stock Count");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumbRow.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnGenerate = createMiniButton("Generate Report", resetBlack);
        pnlBreadcrumbRow.add(btnGenerate, BorderLayout.EAST);

        pnlMain.add(pnlBreadcrumbRow, BorderLayout.NORTH);

        // Filters Container
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);

        JPanel pnlFilters = new JPanel(new GridBagLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Row 1 Labels
        gbc.gridy = 0;
        pnlFilters.add(createFilterLabel("Category"), setGbc(gbc, 0));
        pnlFilters.add(createFilterLabel("Item Name"), setGbc(gbc, 1));
        pnlFilters.add(createFilterLabel("From Date"), setGbc(gbc, 2));
        pnlFilters.add(createFilterLabel("To Date"), setGbc(gbc, 3));
        
        // Row 1 Fields
        gbc.gridy = 1;
        JComboBox<String> comboCat = new JComboBox<>(new String[]{"All Categories", "Dairy Products", "Vegetables", "Bakery", "Drinks", "Fruits", "Snacks", "Beverages", "Spices", "Grains", "Frozen Foods", "Meat", "Pharmacy"});
        styleField(comboCat);
        SearchableComboBox.install(comboCat);
        pnlFilters.add(comboCat, setGbc(gbc, 0));
        
        JTextField txtItemName = new JTextField("Enter item name");
        styleField(txtItemName);
        pnlFilters.add(txtItemName, setGbc(gbc, 1));
        
        JTextField txtFrom = new JTextField("mm/dd/yyyy");
        styleField(txtFrom);
        pnlFilters.add(txtFrom, setGbc(gbc, 2));
        
        JTextField txtTo = new JTextField("mm/dd/yyyy");
        styleField(txtTo);
        pnlFilters.add(txtTo, setGbc(gbc, 3));

        // Buttons
        gbc.weightx = 0;
        gbc.gridx = 4;
        JButton btnSubmit = createMiniButton("Submit", actionBlue);
        btnSubmit.setPreferredSize(new Dimension(100, 45));
        pnlFilters.add(btnSubmit, gbc);

        gbc.gridx = 5;
        JButton btnReset = createMiniButton("Reset", tableHeaderBlue);
        btnReset.setPreferredSize(new Dimension(100, 45));
        pnlFilters.add(btnReset, gbc);

        pnlCenter.add(pnlFilters, BorderLayout.NORTH);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);
        
        String[] columns = {
            "NO", "ITEM NAME", "CATEGORY", "STARTING STOCK", "PURCHASED STOCK", 
            "SOLD STOCK", "ENDING STOCK", "PURCHASE PRICE", "RETAIL PRICE", 
            "WHOLE SALE PRICE", "STOCK VALUE (RS.)", "PRICE PER ITEM (RS.)", 
            "SOLD STOCK VALUE (RS.)", "DATE"
        };
        
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
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(tableHeaderBlue); // Blue header as per theme
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
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

        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        pnlTableSection.add(scrollPane, BorderLayout.CENTER);
        pnlCenter.add(pnlTableSection, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "Sample Product 1", "Dairy Products", "20", "20", "2", "18", "250.00", "295.00", "290.00", "4,500.00", "250.00", "590.00", "2026-02-22"});
        tableModel.addRow(new Object[]{"2", "Anchor Cheese Slices 12S 200g", "Dairy Products", "100", "100", "4", "96", "1000.00", "1730.00", "1100.00", "96,000.00", "1,000.00", "6,920.00", "2026-02-23"});
        tableModel.addRow(new Object[]{"3", "Tomato", "Vegetables", "50", "50", "1", "49", "110.00", "105.00", "10000.00", "5,390.00", "110.00", "105.00", "2026-02-23"});
        tableModel.addRow(new Object[]{"4", "Ambewela Flavoured Milk Uht Chocolate Tetra 1L", "Dairy Products", "100", "100", "4", "97", "350.00", "540.00", "490.00", "33,950.00", "350.00", "2,160.00", "2026-02-23"});
        tableModel.addRow(new Object[]{"5", "Ambewela Flavoured Milk Uht Vanilla Tetra 1L", "Dairy Products", "100", "100", "1", "99", "450.00", "540.00", "500.00", "44,550.00", "450.00", "540.00", "2026-02-23"});
        tableModel.addRow(new Object[]{"6", "Anchor Hot Choc 400g", "Dairy Products", "100", "100", "10", "90", "980.00", "1240.00", "1000.00", "88,200.00", "980.00", "12,400.00", "2026-02-23"});
    }

    private GridBagConstraints setGbc(GridBagConstraints gbc, int x) {
        gbc.gridx = x;
        return gbc;
    }

    private JLabel createFilterLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private void styleField(JComponent field) {
        field.setPreferredSize(new Dimension(0, 45));
        field.setBackground(fieldBg);
        field.setBorder(new LineBorder(new Color(230, 230, 235)));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        if (field instanceof JTextField) {
            ((JTextField) field).setForeground(Color.GRAY);
        }
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
}
