package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DailySummaryReportPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionGreen = new Color(139, 195, 74);
    private final Color resetBlack = new Color(33, 33, 33);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Color tableHeaderGreen = new Color(100, 175, 80);

    public DailySummaryReportPanel(MainFrame mainFrame) {
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
        
        JLabel lblBreadcrumb = new JLabel("Main Panel > Reports > Daily Summary Report");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlTopActions.add(lblBreadcrumb, BorderLayout.WEST);

        JButton btnGenerate = createMiniButton("Generate Report", resetBlack);
        pnlTopActions.add(btnGenerate, BorderLayout.EAST);

        pnlMain.add(pnlTopActions, BorderLayout.NORTH);

        // Center Content Container
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBackground(Color.WHITE);

        // 1. Filters
        JPanel pnlFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.add(createFilterPanel("Date", new JTextField("02/24/2026"), 180));
        pnlFilters.add(createFilterPanel("Category", new JComboBox<>(new String[]{"All Categories"}), 180));
        pnlFilters.add(createFilterPanel("Item", new JTextField("All Items"), 250));
        
        JButton btnFilter = createMiniButton("Filter", actionGreen);
        btnFilter.setPreferredSize(new Dimension(80, 40));
        pnlFilters.add(btnFilter);

        JButton btnReset = createMiniButton("Reset", resetBlack);
        btnReset.setPreferredSize(new Dimension(80, 40));
        pnlFilters.add(btnReset);
        pnlCenter.add(pnlFilters);

        // 2. Summary Cards
        JPanel pnlCardsRows = new JPanel();
        pnlCardsRows.setLayout(new BoxLayout(pnlCardsRows, BoxLayout.Y_AXIS));
        pnlCardsRows.setOpaque(false);

        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlRow1.setOpaque(false);
        pnlRow1.add(createSummaryCard("Total Sales", "Rs. 0.00", "0 transactions", new Color(225, 245, 254)));
        pnlRow1.add(createSummaryCard("Total Returns", "Rs. 0.00", "0 items returned", new Color(255, 235, 238)));
        pnlRow1.add(createSummaryCard("Total Expenses", "Rs. 0.00", "0 expense records", new Color(255, 243, 224)));
        pnlRow1.add(createSummaryCard("Net Sales", "Rs. 0.00", "After returns deduction", new Color(232, 245, 233)));
        pnlCardsRows.add(pnlRow1);

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlRow2.setOpaque(false);
        pnlRow2.add(createSummaryCard("Total Cost", "Rs. 0.00", "", new Color(227, 242, 253)));
        pnlRow2.add(createSummaryCard("Revenue Before Discount", "Rs. 0.00", "", new Color(255, 248, 225)));
        pnlRow2.add(createSummaryCard("Total Discounts", "Rs. 0.00", "0.00%", new Color(252, 228, 236)));
        pnlRow2.add(createSummaryCard("Gross Revenue", "Rs. 0.00", "Before returns", new Color(224, 242, 241)));
        pnlRow2.add(createSummaryCard("Net Profit", "Rs. 0.00", "After returns", new Color(255, 253, 231)));
        pnlRow2.add(createSummaryCard("Net Margin", "0.00%", "Net profit margin", new Color(243, 229, 245)));
        pnlCardsRows.add(pnlRow2);

        pnlCenter.add(pnlCardsRows);

        // 3. Table Header & Empty State
        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.setBackground(Color.WHITE);
        pnlTableContainer.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel pnlTableHeader = new JPanel(new GridLayout(1, 0));
        pnlTableHeader.setBackground(tableHeaderGreen);
        pnlTableHeader.setPreferredSize(new Dimension(0, 45));
        String[] cols = {"NO", "ITEM NAME", "CATEGORY", "UNIT", "QTY SOLD", "QTY RETURNED", "NET QTY", "TOTAL COST", "GROSS REVENUE", "TOTAL DISCOUNT", "RETURNS", "NET REVENUE", "NET PROFIT", "PROFIT %"};
        for (String col : cols) {
            JLabel lbl = new JLabel(col, SwingConstants.CENTER);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
            pnlTableHeader.add(lbl);
        }
        pnlTableContainer.add(pnlTableHeader, BorderLayout.NORTH);

        JLabel lblEmpty = new JLabel("No sales data found for 2026-02-24", SwingConstants.CENTER);
        lblEmpty.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblEmpty.setForeground(Color.GRAY);
        lblEmpty.setBorder(new EmptyBorder(50, 0, 50, 0));
        pnlTableContainer.add(lblEmpty, BorderLayout.CENTER);

        pnlCenter.add(pnlTableContainer);

        pnlMain.add(new JScrollPane(pnlCenter), BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
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
        field.setBackground(Color.WHITE);
        field.setBorder(new LineBorder(new Color(230, 230, 235)));
        
        if (field instanceof JTextField) {
            ((JTextField)field).setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        return p;
    }

    private JPanel createSummaryCard(String title, String value, String subtext, Color bg) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(170, 100));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(new Color(60, 60, 60));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValue.setForeground(new Color(30, 30, 30));

        JLabel lblSub = new JLabel(subtext);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(110, 110, 110));

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(5));
        card.add(lblValue);
        card.add(Box.createVerticalStrut(5));
        card.add(lblSub);

        return card;
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
