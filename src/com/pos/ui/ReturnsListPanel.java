package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ReturnsListPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color filterBlue = new Color(13, 71, 161);
    private final Color clearGray = new Color(120, 144, 156);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color processedBadgeBg = new Color(225, 245, 254);
    private final Color processedBadgeFg = new Color(1, 87, 155);

    private JTable table;
    private DefaultTableModel tableModel;

    public ReturnsListPanel(MainFrame mainFrame) {
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
        btnBack.addActionListener(e -> mainFrame.showPanel("Sales"));

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
        JPanel pnlContentScroll = new JPanel(new BorderLayout());
        pnlContentScroll.setBackground(Color.WHITE);
        
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Sales > Sale Return/Refund");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlMain.add(lblBreadcrumb, BorderLayout.NORTH);

        // Search Section
        JPanel pnlSearchSection = new JPanel(new BorderLayout());
        pnlSearchSection.setBackground(Color.WHITE);
        pnlSearchSection.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlInput.setOpaque(false);
        
        JTextField txtSearch = new JTextField("Enter sale code to search for refund");
        txtSearch.setPreferredSize(new Dimension(300, 45));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(new LineBorder(new Color(220, 220, 225)));
        
        pnlInput.add(txtSearch);
        pnlInput.add(createActionButton("Search Sale", filterBlue, 150));
        pnlInput.add(createActionButton("Clear", clearGray, 100));
        
        pnlSearchSection.add(pnlInput, BorderLayout.NORTH);
        
        pnlMain.add(pnlSearchSection, BorderLayout.CENTER);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);
        
        JLabel lblTableTitle = new JLabel("Return History");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTableTitle.setBorder(new EmptyBorder(10, 0, 15, 0));
        pnlTableSection.add(lblTableTitle, BorderLayout.NORTH);
        
        String[] columns = {"SALES CODE", "DATE", "TIME", "ITEM NAME", "PRICE(ONCE)", "QUANTITY", "PRICE(TOTAL)", "STATUS"};
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

        table.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        pnlTableSection.add(scrollPane, BorderLayout.CENTER);
        
        // Total label
        JLabel lblTotal = new JLabel("Total: Rs. 490");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBorder(new EmptyBorder(15, 0, 0, 10));
        pnlTableSection.add(lblTotal, BorderLayout.SOUTH);

        // Assembly
        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.setBackground(Color.WHITE);
        pnlTableContainer.add(pnlSearchSection, BorderLayout.NORTH);
        pnlTableContainer.add(pnlTableSection, BorderLayout.CENTER);
        
        pnlMain.add(pnlTableContainer, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"SALE-699C39F1D2D74", "", "", "", "", "", "", ""}); // Group header style?
        tableModel.addRow(new Object[]{"SALE-699C39F1D2D74", "2026-02-23", "16:58:49", "Ambewela Flavoured Milk Uht Chocolate Tetra 1L", "Rs. 490.00", "1", "Rs. 490", "Processed"});
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

    private JButton createActionButton(String text, Color bg, int width) {
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
        btn.setPreferredSize(new Dimension(width, 45));
        return btn;
    }

    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String status = (String) value;
            if (status == null || status.isEmpty()) return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            JLabel lbl = new JLabel(status);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lbl.setOpaque(true);
            lbl.setBorder(new EmptyBorder(3, 10, 3, 10));
            lbl.setBackground(processedBadgeBg);
            lbl.setForeground(processedBadgeFg);

            p.add(lbl);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }
}
