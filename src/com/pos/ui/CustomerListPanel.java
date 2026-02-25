package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CustomerListPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color filterBlue = new Color(13, 71, 161);
    private final Color tableHeaderBlue = new Color(100, 175, 80).darker(); // I'll use a blue-ish version or keep it consistent with others
    private final Color themeBlue = new Color(13, 71, 161);
    private final Color actionGreen = new Color(139, 195, 74);
    private final Color disableRed = new Color(211, 47, 47);

    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel pnlBreadcrumbRow;
    private JPanel pnlExportActions;
    private JPanel pnlFilterBar;
    private JPanel pnlSearch;
    private JPanel pnlShowEntries;

    public CustomerListPanel(MainFrame mainFrame) {
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
        btnBack.addActionListener(e -> mainFrame.showPanel("Customer"));

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
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Customers > Customers List");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlBreadcrumbRow.add(lblBreadcrumb, BorderLayout.WEST);

        pnlExportActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pnlExportActions.setOpaque(false);
        pnlExportActions.add(createMiniButton("Copy", primaryBlue));
        pnlExportActions.add(createMiniButton("CSV", primaryBlue));
        pnlExportActions.add(createMiniButton("Excel", primaryBlue));
        pnlExportActions.add(createMiniButton("PDF", primaryBlue));
        pnlExportActions.add(createMiniButton("Column Visibility", primaryBlue));
        pnlBreadcrumbRow.add(pnlExportActions, BorderLayout.EAST);

        pnlMain.add(pnlBreadcrumbRow, BorderLayout.NORTH);

        // Search and Show Entries
        pnlFilterBar = new JPanel(new BorderLayout());
        pnlFilterBar.setBackground(Color.WHITE);
        pnlFilterBar.setBorder(new EmptyBorder(20, 0, 20, 0));

        pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Search"));
        JTextField txtSearch = new JTextField("Enter customer name");
        txtSearch.setPreferredSize(new Dimension(300, 40));
        txtSearch.setBorder(new LineBorder(new Color(230, 230, 235)));
        pnlSearch.add(txtSearch);
        pnlSearch.add(createMiniButton("Search", primaryBlue));
        pnlFilterBar.add(pnlSearch, BorderLayout.WEST);

        pnlShowEntries = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlShowEntries.setOpaque(false);
        pnlShowEntries.add(new JLabel("Show"));
        pnlShowEntries.add(new JTextField("30", 3));
        pnlShowEntries.add(new JLabel("Entries"));
        pnlFilterBar.add(pnlShowEntries, BorderLayout.EAST);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);
        
        String[] columns = {"#", "CUSTOMER CODE", "CUSTOMER NAME", "MOBILE NUMBER", "ADDRESS", "EMAIL", "MANAGE"};
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
                lbl.setBackground(new Color(30, 136, 229)); // Theme Blue
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

        table.getColumnModel().getColumn(6).setCellRenderer(new ManageRenderer());
        table.getColumnModel().getColumn(6).setPreferredWidth(170);
        table.getColumnModel().getColumn(6).setMinWidth(160);
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        pnlTableSection.add(scrollPane, BorderLayout.CENTER);
        
        // Assemble Center
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.add(pnlFilterBar, BorderLayout.NORTH);
        pnlCenter.add(pnlTableSection, BorderLayout.CENTER);
        
        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                
                // Responsiveness for Breadcrumb row (Export buttons)
                if (width < 1100) {
                    pnlBreadcrumbRow.remove(pnlExportActions);
                    pnlBreadcrumbRow.setLayout(new GridLayout(2, 1, 0, 10));
                    pnlBreadcrumbRow.add(new JLabel("Main Panel > Customers > Customers List"));
                    pnlBreadcrumbRow.getComponent(0).setForeground(new Color(100, 100, 100));
                    pnlBreadcrumbRow.add(pnlExportActions);
                    ((FlowLayout)pnlExportActions.getLayout()).setAlignment(FlowLayout.LEFT);
                } else {
                    pnlBreadcrumbRow.removeAll();
                    pnlBreadcrumbRow.setLayout(new BorderLayout());
                    JLabel lblB = new JLabel("Main Panel > Customers > Customers List");
                    lblB.setForeground(new Color(100, 100, 100));
                    pnlBreadcrumbRow.add(lblB, BorderLayout.WEST);
                    pnlBreadcrumbRow.add(pnlExportActions, BorderLayout.EAST);
                    ((FlowLayout)pnlExportActions.getLayout()).setAlignment(FlowLayout.RIGHT);
                }

                // Responsiveness for Filter bar (Search & Show Entries)
                if (width < 900) {
                    pnlFilterBar.remove(pnlSearch);
                    pnlFilterBar.remove(pnlShowEntries);
                    pnlFilterBar.setLayout(new GridLayout(2, 1, 0, 10));
                    pnlFilterBar.add(pnlSearch);
                    pnlFilterBar.add(pnlShowEntries);
                    ((FlowLayout)pnlShowEntries.getLayout()).setAlignment(FlowLayout.LEFT);
                } else {
                    pnlFilterBar.remove(pnlSearch);
                    pnlFilterBar.remove(pnlShowEntries);
                    pnlFilterBar.setLayout(new BorderLayout());
                    pnlFilterBar.add(pnlSearch, BorderLayout.WEST);
                    pnlFilterBar.add(pnlShowEntries, BorderLayout.EAST);
                    ((FlowLayout)pnlShowEntries.getLayout()).setAlignment(FlowLayout.RIGHT);
                }
                
                revalidate();
                repaint();
            }
        });
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "1", "Customer", "0786835563", "Elpitiya", "Customer@gmail.com", ""});
        tableModel.addRow(new Object[]{"2", "2", "Shanthi", "0761234567", "27 Dam Street, 12", "shanthi@gmail.com", ""});
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
        btn.setPreferredSize(new Dimension(text.length() > 10 ? 140 : 70, 35));
        return btn;
    }

    class ManageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new GridBagLayout());
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

            JButton btnEdit = new JButton("Edit");
            styleManageButton(btnEdit, Color.WHITE, Color.DARK_GRAY);
            
            JButton btnDisable = new JButton("Disable");
            styleManageButton(btnDisable, disableRed, Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 5, 0, 5);
            p.add(btnEdit, gbc);
            p.add(btnDisable, gbc);
            
            return p;
        }

        private void styleManageButton(JButton btn, Color bg, Color fg) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btn.setBackground(bg);
            btn.setForeground(fg);
            btn.setPreferredSize(new Dimension(60, 30));
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            if (bg == Color.WHITE) {
                btn.setBorder(new LineBorder(new Color(230, 230, 235)));
            } else {
                btn.setBorder(null);
            }
        }
    }
}
