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

public class ExpensesReportPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color fieldBg = new Color(245, 245, 250);

    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel pnlFilters;
    private JPanel pnlFiltersWrapper;

    public ExpensesReportPanel(MainFrame mainFrame) {
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

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Breadcrumbs and Top Actions
        JPanel pnlTopActions = new JPanel(new BorderLayout());
        pnlTopActions.setOpaque(false);
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Reports > Expenses Report");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnGenerate = createMiniButton("Generate Report", new Color(33, 33, 33));
        pnlTopActions.add(btnGenerate, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Filters Section
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);

        pnlFiltersWrapper = new JPanel(new BorderLayout());
        pnlFiltersWrapper.setBackground(Color.WHITE);

        pnlFilters = new JPanel(new GridBagLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(20, 0, 20, 0));

        pnlFiltersWrapper.add(pnlFilters, BorderLayout.CENTER);
        pnlCenter.add(pnlFiltersWrapper, BorderLayout.NORTH);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);
        
        String[] columns = {"ID", "EXPENSE TITLE", "EXPENSE CATEGORY", "AMOUNT (RS)", "CREATED AT", "DESCRIPTION"};
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
        tableModel.addRow(new Object[]{"1", "Digital Marketing", "Marketing and Advertising", "20000.00", "2026-02-23", "Social media ads"});
        tableModel.addRow(new Object[]{"2", "Store Rent", "Utility", "50000.00", "2026-02-24", "Monthly rent"});
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                pnlFilters.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;

                int cols = width > 1000 ? 4 : (width > 600 ? 2 : 1);
                
            JComboBox<String> comboCat = new JComboBox<>(new String[]{"All Categories", "Marketing", "Utility", "Salary", "Rent", "Insurance", "Repair", "Taxes", "Maintenance"});
            SearchableComboBox.install(comboCat);
            JPanel f1 = createFilterPanel("Expense Category", comboCat, 0);
                JPanel f2 = createFilterPanel("Expense Title", new JTextField("Enter expense title"), 0);
                JPanel f3 = createFilterPanel("From Date", new JTextField("mm/dd/yyyy"), 0);
                JPanel f4 = createFilterPanel("To Date", new JTextField("mm/dd/yyyy"), 0);

                JPanel[] panels = {f1, f2, f3, f4};
                for (int i = 0; i < panels.length; i++) {
                    gbc.gridx = i % cols;
                    gbc.gridy = i / cols;
                    pnlFilters.add(panels[i], gbc);
                }

                // Filter Button
                gbc.gridy = (panels.length + cols - 1) / cols + 1;
                gbc.gridx = 0;
                gbc.gridwidth = cols;
                JButton btnFilter = createMiniButton("Filter", primaryBlue);
                btnFilter.setPreferredSize(new Dimension(0, 45));
                pnlFilters.add(btnFilter, gbc);

                pnlFilters.revalidate();
                pnlFilters.repaint();
            }
        });
    }

    private JPanel createFilterPanel(String label, JComponent field, int width) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(50, 50, 50));
        
        if(width > 0) {
            field.setPreferredSize(new Dimension(width, 40));
            field.setMaximumSize(new Dimension(width, 40));
        } else {
            field.setPreferredSize(new Dimension(0, 40));
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }
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
        btn.setPreferredSize(new Dimension(150, 40));
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
