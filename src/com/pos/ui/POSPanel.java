package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class POSPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color activeBlue = new Color(1, 87, 155);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color sidebarBg = new Color(245, 245, 250);
    private final Color tableHeaderBg = new Color(220, 225, 230);
    
    private final Font fontBold22 = new Font("Segoe UI", Font.BOLD, 22);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontPlain12 = new Font("Segoe UI", Font.PLAIN, 12);

    private JTextField txtInput;
    private DefaultTableModel tableModel;

    public POSPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        // --- Top Header ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setPreferredSize(new Dimension(0, 60));
        pnlHeader.setBorder(new EmptyBorder(5, 20, 5, 20));

        JLabel lblTitle = new JLabel("RETAIL SUPERMARKET");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        JPanel pnlHeaderActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 8));
        pnlHeaderActions.setOpaque(false);
        
        JButton btnMode = new JButton("Walk In-Take Away");
        btnMode.setBackground(activeBlue);
        btnMode.setForeground(Color.WHITE);
        btnMode.setPreferredSize(new Dimension(160, 35));
        btnMode.setFocusPainted(false);
        
        JLabel lblPrinter = new JLabel("Printer ON");
        lblPrinter.setForeground(vibrantGreen);
        
        pnlHeaderActions.add(btnMode);
        pnlHeaderActions.add(lblPrinter);
        pnlHeaderActions.add(new JLabel("\uD83D\uDC64")); // User icon
        
        pnlHeader.add(pnlHeaderActions, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // --- Main Content Area ---
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setOpaque(false);

        // 1. Left Sidebar Icons
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(sidebarBg);
        pnlSidebar.setPreferredSize(new Dimension(100, 0));
        pnlSidebar.setBorder(new LineBorder(new Color(230, 230, 235)));

        pnlSidebar.add(createSidebarBtn("Discount", "\uD83C\uDFF7"));
        pnlSidebar.add(createSidebarBtn("Stock", "\uD83D\uDCE6"));
        pnlSidebar.add(createSidebarBtn("Customer", "\uD83D\uDC64", true)); // Active
        pnlSidebar.add(createSidebarBtn("Search", "\uD83D\uDD0D"));
        pnlSidebar.add(createSidebarBtn("Journal", "\uD83D\uDCD3"));
        pnlSidebar.add(createSidebarBtn("Void", "\uD83D\uDDD1"));
        pnlSidebar.add(createSidebarBtn("Price", "\uD83D\uDCA2"));
        pnlSidebar.add(createSidebarBtn("Print", "\uD83D\uDDA8"));

        pnlContent.add(pnlSidebar, BorderLayout.WEST);

        // 2. Center Panel (Receipt + Keypad)
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Receipt Table
        String[] cols = {"Item Name", "Quantity", "U/Price", "Dis%", "Total"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setBackground(tableHeaderBg);
        table.getTableHeader().setFont(fontBold14);
        
        tableModel.addRow(new Object[]{"Croissant", "1", "$2.99", "0", "$2.99"});
        tableModel.addRow(new Object[]{"Strawberry Jam", "2", "$5.00", "0", "$10.00"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230,230,235)));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        // Bottom Keypad Area
        JPanel pnlBottom = new JPanel(new BorderLayout(5, 5));
        pnlBottom.setOpaque(false);
        pnlBottom.setPreferredSize(new Dimension(0, 350));

        // Input Area
        JPanel pnlInput = new JPanel(new BorderLayout());
        pnlInput.setBackground(new Color(80, 80, 85));
        JLabel lblPLU = new JLabel(" 1x    PLU ", SwingConstants.LEFT);
        lblPLU.setForeground(Color.WHITE);
        lblPLU.setFont(fontBold14);
        txtInput = new JTextField();
        txtInput.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtInput.setBorder(null);
        pnlInput.add(lblPLU, BorderLayout.WEST);
        pnlInput.add(txtInput, BorderLayout.CENTER);
        pnlInput.setPreferredSize(new Dimension(0, 45));
        pnlBottom.add(pnlInput, BorderLayout.NORTH);

        // Numbers & Enter
        JPanel pnlNumbers = new JPanel(new BorderLayout(3, 3));
        pnlNumbers.setOpaque(false);

        JPanel pnlNumGrid = new JPanel(new GridLayout(4, 3, 3, 3));
        pnlNumGrid.setOpaque(false);
        String[] keys = {"7","8","9","4","5","6","1","2","3",".","0","*"};
        for(String key : keys) {
            JButton b = createKeypadBtn(key);
            b.addActionListener(e -> txtInput.setText(txtInput.getText() + key));
            pnlNumGrid.add(b);
        }
        
        JPanel pnlRightKeys = new JPanel(new GridLayout(4, 2, 3, 3));
        pnlRightKeys.setOpaque(false);
        String[] rKeys = {"\u232B", "Hold", "Recall", "Void", "Last"};
        for(String rk : rKeys) pnlRightKeys.add(createKeypadBtn(rk));

        pnlNumbers.add(pnlNumGrid, BorderLayout.CENTER);
        pnlNumbers.add(pnlRightKeys, BorderLayout.EAST);
        
        JButton btnEnter = new JButton("Enter");
        btnEnter.setBackground(vibrantGreen);
        btnEnter.setForeground(Color.WHITE);
        btnEnter.setFont(fontBold22);
        btnEnter.setPreferredSize(new Dimension(0, 60));
        btnEnter.setFocusPainted(false);
        btnEnter.setBorder(null);

        pnlBottom.add(pnlNumbers, BorderLayout.CENTER);
        pnlBottom.add(btnEnter, BorderLayout.SOUTH);

        pnlCenter.add(pnlBottom, BorderLayout.SOUTH);
        pnlContent.add(pnlCenter, BorderLayout.CENTER);

        // 3. Right Product/Category Panel
        JPanel pnlRight = new JPanel(new BorderLayout(10, 10));
        pnlRight.setOpaque(false);
        pnlRight.setPreferredSize(new Dimension(500, 0));
        pnlRight.setBorder(new EmptyBorder(10, 0, 10, 10));

        // Search Bar
        JTextField txtProdSearch = new JTextField(" \uD83D\uDD0D Search products...");
        txtProdSearch.setPreferredSize(new Dimension(0, 40));
        txtProdSearch.setForeground(Color.GRAY);
        pnlRight.add(txtProdSearch, BorderLayout.NORTH);

        // Product Grid
        JPanel pnlProdGrid = new JPanel(new GridLayout(0, 4, 8, 8));
        pnlProdGrid.setOpaque(false);
        for(int i=0; i<8; i++) {
            pnlProdGrid.add(createProductCard("Item " + i, "$1.50"));
        }
        
        // Categories at bottom
        JPanel pnlCats = new JPanel(new GridLayout(0, 3, 8, 8));
        pnlCats.setOpaque(false);
        pnlCats.setPreferredSize(new Dimension(0, 200));
        String[] cats = {"Snacks", "Dairy", "Beverages", "Health", "Bakery", "Frozen"};
        for(String cat : cats) {
            JButton b = new JButton(cat);
            b.setBackground(activeBlue);
            b.setForeground(Color.WHITE);
            b.setFont(fontBold14);
            b.setFocusPainted(false);
            b.setBorder(null);
            pnlCats.add(b);
        }

        JPanel pnlProdContainer = new JPanel(new BorderLayout(10, 10));
        pnlProdContainer.setOpaque(false);
        pnlProdContainer.add(pnlProdGrid, BorderLayout.CENTER);
        pnlProdContainer.add(pnlCats, BorderLayout.SOUTH);
        
        pnlRight.add(pnlProdContainer, BorderLayout.CENTER);
        pnlContent.add(pnlRight, BorderLayout.EAST);

        add(pnlContent, BorderLayout.CENTER);
    }

    private JButton createSidebarBtn(String text, String icon, boolean active) {
        JButton btn = new JButton("<html><center>" + icon + "<br><span style='font-size:8px;'>" + text + "</span></center></html>");
        btn.setPreferredSize(new Dimension(100, 80));
        btn.setBackground(active ? activeBlue : Color.WHITE);
        btn.setForeground(active ? Color.WHITE : Color.DARK_GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(230, 230, 235)));
        return btn;
    }

    private JButton createSidebarBtn(String text, String icon) {
        return createSidebarBtn(text, icon, false);
    }

    private JButton createKeypadBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(fontBold14);
        b.setBackground(Color.WHITE);
        b.setBorder(new LineBorder(new Color(230, 230, 235)));
        b.setFocusPainted(false);
        return b;
    }

    private JPanel createProductCard(String name, String price) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new LineBorder(new Color(230, 230, 235)));
        JLabel lblImg = new JLabel("IMG", SwingConstants.CENTER);
        lblImg.setPreferredSize(new Dimension(0, 70));
        lblImg.setOpaque(true);
        lblImg.setBackground(new Color(245, 245, 250));
        p.add(lblImg, BorderLayout.NORTH);
        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 10));
        p.add(lblName, BorderLayout.CENTER);
        JLabel lblPrice = new JLabel(price, SwingConstants.CENTER);
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblPrice.setForeground(activeBlue);
        p.add(lblPrice, BorderLayout.SOUTH);
        return p;
    }
}
