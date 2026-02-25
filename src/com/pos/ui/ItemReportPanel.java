package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ItemReportPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color resetBlack = new Color(33, 33, 33);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Color tableHeaderBlue = new Color(21, 101, 192);

    private JTable table;
    private DefaultTableModel tableModel;

    public ItemReportPanel(MainFrame mainFrame) {
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

        // Breadcrumbs & Generate Report Button
        JPanel pnlTopActions = new JPanel(new BorderLayout());
        pnlTopActions.setOpaque(false);
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Reports > Item Report");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnGenerate = createMiniButton("Generate Report", resetBlack);
        pnlTopActions.add(btnGenerate, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Filters Container
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);

        JPanel pnlFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        pnlFilters.setBackground(Color.WHITE);

        pnlFilters.add(createFilterPanel("Item Name", new JTextField("Enter item name"), 200));
        
        JComboBox<String> comboCat = new JComboBox<>(new String[]{"All Categories", "Dairy Products", "Vegetables", "Bakery", "Drinks", "Fruits", "Snacks", "Beverages", "Spices", "Grains", "Frozen Foods", "Meat", "Pharmacy"});
        SearchableComboBox.install(comboCat);
        pnlFilters.add(createFilterPanel("Category", comboCat, 180));
        
        pnlFilters.add(createFilterPanel("Status", new JComboBox<>(new String[]{"All Status", "In Stock", "Out Of Stock"}), 150));

        JButton btnFilter = createMiniButton("Filter", actionBlue);
        btnFilter.setPreferredSize(new Dimension(100, 40));
        pnlFilters.add(btnFilter);

        JButton btnReset = createMiniButton("Reset", resetBlack);
        btnReset.setPreferredSize(new Dimension(100, 40));
        pnlFilters.add(btnReset);

        pnlCenter.add(pnlFilters, BorderLayout.NORTH);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);
        
        String[] columns = {"NO", "ITEM CODE", "ITEM NAME", "QTY", "UNIT TYPE", "STATUS"};
        
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

        // Styling Table Header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
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
                
                if (column == 5) { // Status column
                    String val = String.valueOf(value);
                    if ("In Stock".equals(val)) {
                        setForeground(new Color(46, 125, 50));
                    } else {
                        setForeground(Color.RED);
                    }
                } else {
                    setForeground(Color.BLACK);
                }
                
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
        tableModel.addRow(new Object[]{"1", "3539", "Kellogg's Chocos 250g", "100", "g", "In Stock"});
        tableModel.addRow(new Object[]{"2", "6711", "Kellogg's Chocos 125g", "103", "g", "In Stock"});
        tableModel.addRow(new Object[]{"3", "4128", "Eggs Bulk Large", "100", "Pieces", "In Stock"});
        tableModel.addRow(new Object[]{"4", "0021", "Cic Besto Eggs Omega 3", "47", "Pieces", "In Stock"});
        tableModel.addRow(new Object[]{"5", "ITEM001", "Ambewela Non Fat Milk - 1L", "100", "Pieces", "In Stock"});
    }

    private JPanel createFilterPanel(String label, JComponent field, int width) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(50, 50, 50));
        
        field.setPreferredSize(new Dimension(width, 40));
        field.setMaximumSize(new Dimension(width, 40));
        field.setBackground(fieldBg);
        field.setBorder(new LineBorder(new Color(230, 230, 235)));
        
        if (field instanceof JTextField) {
            ((JTextField)field).setFont(new Font("Segoe UI", Font.PLAIN, 13));
            ((JTextField)field).setForeground(Color.GRAY);
        }

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        return p;
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
