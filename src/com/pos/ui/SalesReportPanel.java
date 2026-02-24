package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SalesReportPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color filterBlue = new Color(13, 71, 161);
    private final Color clearGray = new Color(120, 144, 156);
    private final Color tableHeaderBlue = new Color(30, 136, 229);
    private final Color paidBadgeBg = new Color(225, 245, 254);
    private final Color paidBadgeFg = new Color(1, 87, 155);
    private final Color dueBadgeBg = new Color(224, 242, 241);
    private final Color dueBadgeFg = new Color(0, 121, 107);
    private final Color actionBlue = new Color(3, 169, 244);
    private final Color actionGreen = new Color(67, 160, 71);

    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel pnlFilters;
    private JPanel pnlMainContent;

    public SalesReportPanel(MainFrame mainFrame) {
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
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Reports > Sales Report");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnGenerate = createMiniButton("Generate Report", new Color(33, 33, 33));
        pnlTopActions.add(btnGenerate, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Filters Container
        JPanel pnlFiltersWrapper = new JPanel(new BorderLayout());
        pnlFiltersWrapper.setBackground(Color.WHITE);
        
        pnlFilters = new JPanel(new GridBagLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Filter Rows
        gbc.gridy = 0;
        pnlFilters.add(createFilterLabel("Category"), setGbc(gbc, 0));
        pnlFilters.add(createFilterLabel("Sales Code"), setGbc(gbc, 1));
        pnlFilters.add(createFilterLabel("Customer"), setGbc(gbc, 2));
        pnlFilters.add(createFilterLabel("Customer Contact"), setGbc(gbc, 3));
        pnlFilters.add(createFilterLabel("From Date"), setGbc(gbc, 4));
        pnlFilters.add(createFilterLabel("To Date"), setGbc(gbc, 5));

        gbc.gridy = 1;
        pnlFilters.add(styleCombo(new JComboBox<>(new String[]{"All Categories"})), setGbc(gbc, 0));
        pnlFilters.add(styleField(new JTextField("Enter sales code")), setGbc(gbc, 1));
        pnlFilters.add(styleField(new JTextField("Enter customer name")), setGbc(gbc, 2));
        pnlFilters.add(styleField(new JTextField("Enter contact number")), setGbc(gbc, 3));
        pnlFilters.add(styleField(new JTextField("mm/dd/yyyy")), setGbc(gbc, 4));
        pnlFilters.add(styleField(new JTextField("mm/dd/yyyy")), setGbc(gbc, 5));

        // Filter Buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(15, 5, 5, 5);
        pnlFilters.add(createActionButton("Filter", filterBlue), gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 3;
        pnlFilters.add(createActionButton("Clear Filters", clearGray), gbc);

        pnlFiltersWrapper.add(pnlFilters, BorderLayout.CENTER);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);
        
        String[] columns = {"ID", "CODE", "CUSTOMER NAME", "CONTACT", "TOTAL (RS)", "RECIEVED AMOUNT (RS)", "PAID AMOUNT (RS)", "STATUS", "DUE AMOUNT (RS)", "DISCOUNT (RS)", "CREATED AT", "ACTION"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(80);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(245, 245, 250));

        // Styling Table Header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
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
        
        pnlTableSection.add(scrollPane, BorderLayout.CENTER);
        
        // Final Assembly
        pnlMainContent = new JPanel(new BorderLayout());
        pnlMainContent.setBackground(Color.WHITE);
        pnlMainContent.add(pnlFiltersWrapper, BorderLayout.NORTH);
        pnlMainContent.add(pnlTableSection, BorderLayout.CENTER);
        
        pnlMain.add(pnlMainContent, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                pnlFilters.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;

                int cols = width > 1200 ? 6 : (width > 800 ? 3 : 2);
                
                String[] labels = {"Category", "Sales Code", "Customer", "Customer Contact", "From Date", "To Date"};
            JComboBox<String> comboCat = new JComboBox<>(new String[]{"All Categories", "Dairy", "Bakery", "Drinks", "Fruits", "Snacks", "Beverages", "Spices", "Grains", "Frozen Foods", "Meat", "Pharmacy"});
            SearchableComboBox.install(comboCat);
            
            JComponent[] fields = {
                styleCombo(comboCat),
                styleField(new JTextField("Enter sales code")),
                styleField(new JTextField("Enter customer name")),
                styleField(new JTextField("Enter contact number")),
                styleField(new JTextField("mm/dd/yyyy")),
                styleField(new JTextField("mm/dd/yyyy"))
            };

                for (int i = 0; i < labels.length; i++) {
                    gbc.gridx = i % cols;
                    gbc.gridy = (i / cols) * 2;
                    pnlFilters.add(createFilterLabel(labels[i]), gbc);
                    
                    gbc.gridy = (i / cols) * 2 + 1;
                    pnlFilters.add(fields[i], gbc);
                }

                // Buttons row
                gbc.gridy = (labels.length / cols + 1) * 2;
                gbc.gridx = 0;
                gbc.gridwidth = cols / 2;
                if (gbc.gridwidth == 0) gbc.gridwidth = 1;
                pnlFilters.add(createActionButton("Filter", filterBlue), gbc);

                gbc.gridx = gbc.gridwidth;
                gbc.gridwidth = cols - gbc.gridwidth;
                pnlFilters.add(createActionButton("Clear Filters", clearGray), gbc);

                pnlFilters.revalidate();
                pnlFilters.repaint();
            }
        });
    }

    private GridBagConstraints setGbc(GridBagConstraints gbc, int x) {
        gbc.gridx = x;
        return gbc;
    }

    private JLabel createFilterLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return lbl;
    }

    private JTextField styleField(JTextField tf) {
        tf.setPreferredSize(new Dimension(0, 40));
        tf.setBackground(new Color(245, 245, 250));
        tf.setBorder(new LineBorder(new Color(220, 220, 225)));
        tf.setForeground(Color.GRAY);
        return tf;
    }

    private JComboBox styleCombo(JComboBox cb) {
        cb.setPreferredSize(new Dimension(0, 40));
        cb.setBackground(new Color(245, 245, 250));
        return cb;
    }

    private void setupTableRenderers() {
        table.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(11).setCellRenderer(new ActionRenderer());
        
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(11).setPreferredWidth(150);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"25", "SALE-699C4707B14E8", "Customer", "0786835563", "1240.00", "10000.00", "1240.00", "PAID", "0", "0.00", "2026-02-23 17:54:39", ""});
        tableModel.addRow(new Object[]{"24", "SALE-699C46EB5516F", "Customer", "0786835563", "3430.00", "1000.00", "1000.00", "DUE", "2430", "0.00", "2026-02-23 17:54:11", ""});
        tableModel.addRow(new Object[]{"23", "SALE-699C39F1D2D74", "Customer", "0786835563", "1510.50", "1000.00", "1200.00", "DUE", "310.5", "79.50", "2026-02-23 16:58:49", ""});
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
        btn.setPreferredSize(new Dimension(150, 40));
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(200, 45));
        return btn;
    }

    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String status = (String) value;
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            JLabel lbl = new JLabel(status);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setBorder(new EmptyBorder(5, 15, 5, 15));

            if ("PAID".equals(status)) {
                lbl.setBackground(paidBadgeBg);
                lbl.setForeground(paidBadgeFg);
            } else {
                lbl.setBackground(dueBadgeBg);
                lbl.setForeground(dueBadgeFg);
            }

            p.add(lbl);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }

    class ActionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            String status = (String) table.getValueAt(row, 7);

            if ("DUE".equals(status)) {
                JButton btnPay = new JButton("Pay");
                styleActionButton(btnPay, actionGreen);
                p.add(btnPay);
            }

            JButton btnMore = new JButton("MORE");
            styleActionButton(btnMore, actionBlue);
            p.add(btnMore);

            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }

        private void styleActionButton(JButton btn, Color bg) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(65, 35));
            btn.setFocusPainted(false);
            btn.setBorder(null);
            btn.setOpaque(true);
        }
    }
}
