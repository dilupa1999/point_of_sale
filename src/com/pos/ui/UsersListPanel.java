package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UsersListPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color tableHeaderGreen = new Color(100, 175, 80);
    private final Color deactivateRed = new Color(211, 47, 47);
    private final Color headerBg = primaryBlue;

    private JTable table;
    private DefaultTableModel tableModel;

    public UsersListPanel(MainFrame mainFrame) {
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
        btnBack.addActionListener(e -> mainFrame.showPanel("Users"));

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

        // Breadcrumbs & Export Buttons
        JPanel pnlTopActions = new JPanel(new BorderLayout());
        pnlTopActions.setOpaque(false);
        pnlTopActions.setBorder(new EmptyBorder(10, 40, 0, 40));

        JLabel lblBreadcrumb = new JLabel("Main Panel > Users > Users List");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JPanel pnlExport = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlExport.setOpaque(false);
        pnlExport.add(createActionButton("Copy", tableHeaderGreen));
        pnlExport.add(createActionButton("CSV", tableHeaderGreen));
        pnlExport.add(createActionButton("Excel", tableHeaderGreen));
        pnlExport.add(createActionButton("PDF", tableHeaderGreen));
        pnlExport.add(createActionButton("Column Visibility", tableHeaderGreen));
        pnlTopActions.add(pnlExport, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Container with padding
        JPanel pnlContainer = new JPanel(new BorderLayout());
        pnlContainer.setBackground(Color.WHITE);
        pnlContainer.setBorder(new EmptyBorder(0, 40, 40, 40));

        // Filters Row
        JPanel pnlFilters = new JPanel(new BorderLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(25, 0, 15, 0));

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Search"));
        JTextField txtSearch = new JTextField("Enter User name", 25);
        txtSearch.setPreferredSize(new Dimension(0, 35));
        txtSearch.setForeground(Color.GRAY);
        pnlSearch.add(txtSearch);
        pnlSearch.add(createActionButton("Search", tableHeaderGreen));
        pnlFilters.add(pnlSearch, BorderLayout.WEST);

        JPanel pnlEntries = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlEntries.setOpaque(false);
        JTextField txtEntries = new JTextField("30", 5);
        txtEntries.setPreferredSize(new Dimension(0, 35));
        txtEntries.setHorizontalAlignment(SwingConstants.CENTER);
        pnlEntries.add(txtEntries);
        pnlEntries.add(new JLabel("Entries"));
        pnlFilters.add(pnlEntries, BorderLayout.EAST);

        pnlContainer.add(pnlFilters, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"#", "NAME", "EMAIL", "ROLE", "NUMBER", "GENDER", "STATUS", "MANAGE"};
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
                c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                c.setForeground(Color.BLACK);
                ((JComponent)c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
                return c;
            }
        });

        table.getColumnModel().getColumn(7).setCellRenderer(new ManageRenderer());
        
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlContainer.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlContainer, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "aaaaa", "admin@gmail.com", "admin", "0761234567", "male", "Active", ""});
        tableModel.addRow(new Object[]{"2", "Nimal", "nimal@gmail.com", "Super Admin", "0764534234", "male", "Active", ""});
        tableModel.addRow(new Object[]{"3", "superadmin", "superadmin@gmail.com", "admin", "0712626063", "male", "Active", ""});
        tableModel.addRow(new Object[]{"4", "Saduni", "sanduni@gmail.com", "manager", "0714442235", "female", "Active", ""});
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
        if (text.length() > 10) {
            btn.setPreferredSize(new Dimension(150, 35));
        } else {
            btn.setPreferredSize(new Dimension(70, 35));
        }
        return btn;
    }

    class ManageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 8));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JButton btnEdit = styleManageButton("Edit", Color.WHITE, Color.GRAY);
            JButton btnDeactivate = styleManageButton("Deactivate", vibrantGreen, Color.WHITE);
            
            p.add(btnEdit);
            p.add(btnDeactivate);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
        
        private JButton styleManageButton(String text, Color bg, Color fg) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            btn.setBackground(bg);
            btn.setForeground(fg);
            btn.setFocusPainted(false);
            if (bg == Color.WHITE) {
                btn.setBorder(new LineBorder(new Color(200, 200, 205)));
            } else {
                btn.setBorder(null);
            }
            btn.setOpaque(true);
            btn.setPreferredSize(new Dimension(text.equals("Edit") ? 45 : 85, 30));
            return btn;
        }
    }
}
