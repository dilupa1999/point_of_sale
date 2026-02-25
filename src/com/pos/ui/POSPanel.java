package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class POSPanel extends JPanel {

    private final Color headerGreen = new Color(27, 94, 32);
    private final Color buttonGreen = new Color(46, 125, 50);
    private final Color lightGreen = new Color(102, 187, 106);
    private final Color dangerRed = new Color(211, 47, 47);
    private final Color tableHeaderGray = new Color(224, 224, 224);
    private final Color borderGray = new Color(200, 200, 200);
    private final Color fieldBg = new Color(245, 245, 250);

    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 18);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontPlain14 = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font fontBold12 = new Font("Segoe UI", Font.BOLD, 12);

    private DefaultTableModel tableModel;
    private JLabel lblTime;

    public POSPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));
        initComponents();
        setupResponsiveness();
        startTimer();
    }

    private void initComponents() {
        // --- Split Pane Layout ---
        setLayout(new BorderLayout(5, 5));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel pnlMain = new JPanel(new BorderLayout(5, 0));
        pnlMain.setOpaque(false);

        // --- Left Panel (Billing Area) ---
        JPanel pnlLeft = new JPanel(new BorderLayout(0, 5));
        pnlLeft.setOpaque(true);
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBorder(new LineBorder(borderGray));
        pnlLeft.setPreferredSize(new Dimension(850, 0));

        // 1. Billing Header
        JPanel pnlBillingHeader = new JPanel(new BorderLayout());
        pnlBillingHeader.setBackground(headerGreen);
        pnlBillingHeader.setPreferredSize(new Dimension(0, 60));
        pnlBillingHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pnlTitleGroup = new JPanel(new GridLayout(2, 1));
        pnlTitleGroup.setOpaque(false);

        JLabel lblBillingTitle = new JLabel("Billing system");
        lblBillingTitle.setForeground(Color.WHITE);
        lblBillingTitle.setFont(fontTitle);

        lblTime = new JLabel("Loading date/time...");
        lblTime.setForeground(new Color(200, 230, 200));
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        pnlTitleGroup.add(lblBillingTitle);
        pnlTitleGroup.add(lblTime);
        pnlBillingHeader.add(pnlTitleGroup, BorderLayout.WEST);

        JPanel pnlHeaderBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlHeaderBtns.setOpaque(false);
        pnlHeaderBtns.add(createHeaderBtn("View Hold List", Color.WHITE, Color.BLACK));
        pnlHeaderBtns.add(createHeaderBtn("Retail", buttonGreen, Color.WHITE));
        pnlHeaderBtns.add(createHeaderBtn("Wholesale", Color.WHITE, Color.BLACK));
        pnlBillingHeader.add(pnlHeaderBtns, BorderLayout.EAST);

        pnlLeft.add(pnlBillingHeader, BorderLayout.NORTH);

        // 2. Billing Content
        JPanel pnlBillingContent = new JPanel(new BorderLayout(0, 5));
        pnlBillingContent.setOpaque(false);
        pnlBillingContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Control Row
        JPanel pnlControls = new JPanel(new GridBagLayout());
        pnlControls.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 5);

        // Customer Selection
        gbc.weightx = 0.3;
        JPanel pnlCustomer = createInputWrapper("Customer - 0786835563");
        pnlControls.add(pnlCustomer, gbc);

        // User Icon Button
        gbc.weightx = 0.05;
        JButton btnUser = new JButton("\uD83D\uDC64");
        btnUser.setBackground(headerGreen);
        btnUser.setForeground(Color.WHITE);
        btnUser.setPreferredSize(new Dimension(50, 45));
        btnUser.setFocusPainted(false);
        btnUser.setBorder(null);
        pnlControls.add(btnUser, gbc);

        // Barcode Input
        gbc.weightx = 0.4;
        JPanel pnlBarcode = createInputWrapper("Enter a valid barcode");
        pnlControls.add(pnlBarcode, gbc);

        // Qty Selector
        gbc.weightx = 0.2;
        JPanel pnlQty = new JPanel(new BorderLayout());
        pnlQty.setBorder(new LineBorder(borderGray));
        pnlQty.setBackground(Color.WHITE);

        JButton btnMinus = createQtyBtn("-");
        JButton btnPlus = createQtyBtn("+");
        JLabel lblQtyVal = new JLabel("1", SwingConstants.CENTER);
        lblQtyVal.setFont(fontBold14);

        pnlQty.add(btnMinus, BorderLayout.WEST);
        pnlQty.add(lblQtyVal, BorderLayout.CENTER);
        pnlQty.add(btnPlus, BorderLayout.EAST);
        pnlControls.add(pnlQty, gbc);

        pnlBillingContent.add(pnlControls, BorderLayout.NORTH);

        // Table Area
        String[] columns = { "ITEM NAME", "QTY", "PRICE", "DISCOUNT (%)", "DISCOUNT (RS)", "SUBTOTAL" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setFont(fontPlain14);

        JTableHeader header = table.getTableHeader();
        header.setBackground(tableHeaderGray);
        header.setFont(fontBold12);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(borderGray));
        scroll.getViewport().setBackground(Color.WHITE);
        pnlBillingContent.add(scroll, BorderLayout.CENTER);

        // Summary Row
        JPanel pnlSummary = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        pnlSummary.setOpaque(true);
        pnlSummary.setBackground(new Color(245, 245, 245));
        pnlSummary.setBorder(new LineBorder(borderGray));

        pnlSummary.add(createSummaryLabel("Total Items: 0"));
        pnlSummary.add(createSummaryLabel("Total Quantity: 0"));
        pnlSummary.add(createSummaryLabel("Total Amount: Rs. 0.00"));
        pnlSummary.add(createSummaryLabel("Grand Total: Rs. 0.00"));

        pnlBillingContent.add(pnlSummary, BorderLayout.SOUTH);

        pnlLeft.add(pnlBillingContent, BorderLayout.CENTER);

        // 3. Billing Footer
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setPreferredSize(new Dimension(0, 130));
        pnlFooter.setBorder(new EmptyBorder(10, 20, 10, 20));
        pnlFooter.setOpaque(false);

        JPanel pnlDiscount = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        pnlDiscount.setOpaque(false);
        pnlDiscount.add(new JLabel("Discount"));
        JTextField txtDiscount = new JTextField("0.00", 15);
        txtDiscount.setPreferredSize(new Dimension(0, 40));
        txtDiscount.setBorder(new LineBorder(borderGray));
        txtDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlDiscount.add(txtDiscount);
        pnlFooter.add(pnlDiscount, BorderLayout.WEST);

        JPanel pnlActionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlActionBtns.setOpaque(false);

        JButton btnPay = createActionBtn("\uD83D\uDCBB Pay All (Enter)", headerGreen, 180);
        JButton btnHold = createActionBtn("\u26A0 Hold All", lightGreen, 160);
        JButton btnCancel = createActionBtn("\u2298 Cancel", dangerRed, 160);

        JPanel pnlBtnHelper = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlBtnHelper.setOpaque(false);
        pnlBtnHelper.add(btnHold);
        pnlBtnHelper.add(btnCancel);

        pnlActionBtns.add(btnPay);
        pnlActionBtns.add(pnlBtnHelper);
        pnlFooter.add(pnlActionBtns, BorderLayout.CENTER);

        pnlLeft.add(pnlFooter, BorderLayout.SOUTH);
        pnlMain.add(pnlLeft, BorderLayout.WEST);

        // --- Right Panel (Search Area) ---
        JPanel pnlRightFinal = new JPanel(new BorderLayout(0, 10));
        pnlRightFinal.setOpaque(true);
        pnlRightFinal.setBackground(Color.WHITE);
        pnlRightFinal.setBorder(new LineBorder(borderGray));

        JPanel pnlSearchBack = new JPanel(new BorderLayout());
        pnlSearchBack.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlSearchBack.setOpaque(false);

        JPanel pnlSearchBar = new JPanel(new BorderLayout(5, 0));
        pnlSearchBar.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderGray),
                new EmptyBorder(5, 10, 5, 10)));
        pnlSearchBar.setBackground(Color.WHITE);

        JTextField txtSearch = new JTextField("Search for items...");
        txtSearch.setBorder(null);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setFont(fontPlain14);
        pnlSearchBar.add(txtSearch, BorderLayout.CENTER);

        JPanel pnlSearchIcons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlSearchIcons.setOpaque(false);
        pnlSearchIcons.add(new JLabel("\uD83D\uDD0D")); // Search icon
        pnlSearchIcons.add(new JLabel("X")); // Close icon
        pnlSearchBar.add(pnlSearchIcons, BorderLayout.EAST);

        pnlSearchBack.add(pnlSearchBar, BorderLayout.NORTH);
        pnlRightFinal.add(pnlSearchBack, BorderLayout.NORTH);

        // Placeholder for results
        JPanel pnlResults = new JPanel();
        pnlResults.setBackground(Color.WHITE);
        pnlRightFinal.add(pnlResults, BorderLayout.CENTER);

        pnlMain.add(pnlRightFinal, BorderLayout.CENTER);
        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            lblTime.setText(sdf.format(new Date()));
        });
        timer.start();
    }

    // --- Helper Methods for Redesign ---

    private JButton createHeaderBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(fontBold12);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(borderGray));
        btn.setPreferredSize(new Dimension(120, 35));
        return btn;
    }

    private JPanel createInputWrapper(String placeholder) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(fieldBg);
        p.setBorder(new LineBorder(borderGray));
        p.setPreferredSize(new Dimension(0, 45));

        JTextField txt = new JTextField(placeholder);
        txt.setBorder(new EmptyBorder(0, 10, 0, 10));
        txt.setOpaque(false);
        txt.setForeground(Color.GRAY);
        txt.setFont(fontPlain14);
        p.add(txt, BorderLayout.CENTER);
        return p;
    }

    private JButton createQtyBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(fontBold14);
        b.setBackground(new Color(245, 245, 250));
        b.setFocusPainted(false);
        b.setBorder(null);
        b.setPreferredSize(new Dimension(45, 45));
        return b;
    }

    private JLabel createSummaryLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold14);
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private JButton createActionBtn(String text, Color bg, int width) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontBold14);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setPreferredSize(new Dimension(width, 45));
        if (text.contains("Pay All")) {
            btn.setPreferredSize(new Dimension(width, 95));
        }
        return btn;
    }
}
