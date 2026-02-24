package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class POSPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color darkBlue = new Color(0, 51, 102);
    private final Color activeBlueColor = new Color(1, 87, 155);
    private final Color vibrantGreen = new Color(0, 200, 83);
    private final Color sidebarBg = new Color(255, 255, 255);
    private final Color lightGrayBg = new Color(245, 245, 250);
    private final Color tableHeaderBg = new Color(220, 225, 230);
    private final Color categoryBlue = new Color(21, 101, 192);
    
    private final Font fontTitle = new Font("Segoe UI", Font.BOLD, 22);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontBold12 = new Font("Segoe UI", Font.BOLD, 12);
    private final Font fontPlain12 = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font fontSmall = new Font("Segoe UI", Font.PLAIN, 10);

    private JTextField txtInput;
    private DefaultTableModel tableModel;
    private JLabel lblTime;

    private JPanel pnlRight;
    private JPanel pnlProdGrid;
    private JPanel pnlCatGrid;
    private JScrollPane scrollCart;
    private JPanel pnlKeypadArea;

    public POSPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));
        initComponents();
        setupResponsiveness();
        startTimer();
    }

    private void initComponents() {
        // --- Top Header ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setPreferredSize(new Dimension(0, 80));
        pnlHeader.setBorder(new EmptyBorder(5, 20, 5, 20));

        JPanel pnlTitleArea = new JPanel(new GridLayout(2, 1));
        pnlTitleArea.setOpaque(false);
        JLabel lblTitle = new JLabel("RETAIL SUPERMARKET");
        lblTitle.setFont(fontTitle);
        lblTime = new JLabel("05/02/2024 10:45:34 AM"); // Sample date, timer updates it
        lblTime.setForeground(Color.GRAY);
        pnlTitleArea.add(lblTitle);
        pnlTitleArea.add(lblTime);
        pnlHeader.add(pnlTitleArea, BorderLayout.WEST);

        JPanel pnlHeaderActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlHeaderActions.setOpaque(false);
        
        JButton btnMode = createHeaderActionBtn("Walk In-Take Away", darkBlue);
        
        JPanel pnlPrinter = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlPrinter.setOpaque(false);
        pnlPrinter.add(new JLabel("Printer"));
        JLabel lblOn = new JLabel(" ON ");
        lblOn.setOpaque(true);
        lblOn.setBackground(vibrantGreen);
        lblOn.setForeground(Color.WHITE);
        lblOn.setFont(fontBold12);
        lblOn.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        pnlPrinter.add(lblOn);
        
        JLabel lblUserIcon = new JLabel("\uD83D\uDC64"); // Profile icon placeholder
        lblUserIcon.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        lblUserIcon.setForeground(primaryBlue);

        pnlHeaderActions.add(btnMode);
        pnlHeaderActions.add(pnlPrinter);
        pnlHeaderActions.add(lblUserIcon);
        
        pnlHeader.add(pnlHeaderActions, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // --- Main Body ---
        JPanel pnlBody = new JPanel(new BorderLayout());
        pnlBody.setOpaque(false);


        // Center Content (Transaction + Numeric Pad)
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Transaction Table
        String[] columns = {"Item Name", "Quantity", "U/Price", "Dis%", "Total"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(tableHeaderBg);
        tableHeader.setFont(fontBold12);
        tableHeader.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=1; i<5; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        addSampleCartData();

        scrollCart = new JScrollPane(table);
        scrollCart.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollCart.getViewport().setBackground(Color.WHITE);
        pnlCenter.add(scrollCart, BorderLayout.CENTER);

        // Keypad Section
        pnlKeypadArea = new JPanel(new BorderLayout(5, 5));
        pnlKeypadArea.setOpaque(false);
        pnlKeypadArea.setPreferredSize(new Dimension(0, 380));

        // PLU Input
        JPanel pnlPLU = new JPanel(new BorderLayout());
        pnlPLU.setBackground(new Color(100, 100, 105));
        pnlPLU.setPreferredSize(new Dimension(0, 45));
        JLabel lblPLUTag = new JLabel(" 1x    PLU ", SwingConstants.LEFT);
        lblPLUTag.setForeground(Color.WHITE);
        lblPLUTag.setFont(fontBold14);
        txtInput = new JTextField();
        txtInput.setFont(new Font("Segoe UI", Font.BOLD, 22));
        txtInput.setBorder(null);
        pnlPLU.add(lblPLUTag, BorderLayout.WEST);
        pnlPLU.add(txtInput, BorderLayout.CENTER);
        pnlKeypadArea.add(pnlPLU, BorderLayout.NORTH);

        // Grid of Numbers and Controls
        JPanel pnlKeysSub = new JPanel(new BorderLayout(5, 5));
        pnlKeysSub.setOpaque(false);

        JPanel pnlNumGrid = new JPanel(new GridLayout(4, 4, 3, 3));
        pnlNumGrid.setOpaque(false);
        String[] numKeys = {"7", "8", "9", "\u232B", "4", "5", "6", "+", "1", "2", "3", "-", ".", "0", "*", "Back"};
        for(String sk : numKeys) {
            JButton b = createKeypadBtn(sk);
            if(sk.length() == 1 && Character.isDigit(sk.charAt(0))) {
                b.addActionListener(e -> txtInput.setText(txtInput.getText() + sk));
            }
            pnlNumGrid.add(b);
        }
        pnlKeysSub.add(pnlNumGrid, BorderLayout.CENTER);

        JPanel pnlSpecialControls = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlSpecialControls.setOpaque(false);
        pnlSpecialControls.setPreferredSize(new Dimension(160, 0));
        pnlSpecialControls.add(createControlBtn("Hold(F6)", "\u23F8"));
        pnlSpecialControls.add(createControlBtn("Recall", "\u21BA"));
        pnlSpecialControls.add(createControlBtn("Void", "\uD83D\uDDD1"));
        pnlSpecialControls.add(createControlBtn("Last Receipt", "\uD83D\uDDA8"));
        pnlKeysSub.add(pnlSpecialControls, BorderLayout.EAST);

        pnlKeypadArea.add(pnlKeysSub, BorderLayout.CENTER);

        JButton btnEnter = new JButton("Enter");
        btnEnter.setBackground(vibrantGreen);
        btnEnter.setForeground(Color.WHITE);
        btnEnter.setFont(new Font("Segoe UI", Font.BOLD, 26));
        btnEnter.setPreferredSize(new Dimension(0, 70));
        btnEnter.setFocusPainted(false);
        btnEnter.setBorder(null);
        btnEnter.addActionListener(e -> generateReceipt());
        pnlKeypadArea.add(btnEnter, BorderLayout.SOUTH);

        pnlCenter.add(pnlKeypadArea, BorderLayout.SOUTH);
        pnlBody.add(pnlCenter, BorderLayout.CENTER);

        // Right Panel (Product Grid + Categories)
        pnlRight = new JPanel(new BorderLayout(10, 10));
        pnlRight.setOpaque(false);
        pnlRight.setPreferredSize(new Dimension(550, 0));
        pnlRight.setBorder(new EmptyBorder(5, 0, 5, 5));

        // Path / Search Area
        JPanel pnlRightTop = new JPanel(new BorderLayout());
        pnlRightTop.setOpaque(false);
        
        JPanel pnlPath = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlPath.setOpaque(false);
        pnlPath.add(new JLabel("\u2302")); // Home icon
        pnlPath.add(new JLabel(">"));
        pnlRightTop.add(pnlPath, BorderLayout.NORTH);

        JPanel pnlSearchArea = new JPanel(new BorderLayout(5, 0));
        pnlSearchArea.setOpaque(false);
        JButton btnPrev = createNavBtn("<");
        btnPrev.setPreferredSize(new Dimension(45, 45));
        JButton btnNext = createNavBtn(">");
        btnNext.setPreferredSize(new Dimension(45, 45));
        JTextField txtSearch = new JTextField(" \uD83D\uDD0D Search products...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setFont(fontPlain12);
        txtSearch.setBorder(new LineBorder(new Color(220, 220, 225)));
        
        pnlSearchArea.add(btnPrev, BorderLayout.WEST);
        pnlSearchArea.add(txtSearch, BorderLayout.CENTER);
        pnlSearchArea.add(btnNext, BorderLayout.EAST);
        pnlRightTop.add(pnlSearchArea, BorderLayout.SOUTH);

        pnlRight.add(pnlRightTop, BorderLayout.NORTH);

        // Middle: Product Grid
        JPanel pnlGrids = new JPanel(new BorderLayout(10, 10));
        pnlGrids.setOpaque(false);

        pnlProdGrid = new JPanel(new GridLayout(0, 6, 8, 8));
        pnlProdGrid.setOpaque(false);
        addSampleProducts(pnlProdGrid);
        pnlGrids.add(pnlProdGrid, BorderLayout.NORTH);

        // Categories Grid
        pnlCatGrid = new JPanel(new GridLayout(0, 6, 5, 5));
        pnlCatGrid.setOpaque(false);
        String[] categories = {
            "Snacks", "Dairy Products", "Coffee & Tea Powder", "Health Products", "Bakery Items", "Beauty Products",
            "Stationery", "Gift Cards", "Favorites", "Fruits", "Spices", "Noodles",
            "Jam", "Meat", "Beverages", "Sauce", "Frozen Foods", "Top-up phone credit"
        };
        for(String cat : categories) {
            JButton b = new JButton("<html><center>" + cat + "</center></html>");
            b.setBackground(cat.equals("Favorites") ? Color.DARK_GRAY : categoryBlue);
            b.setForeground(Color.WHITE);
            b.setFont(fontSmall);
            b.setFocusPainted(false);
            b.setBorder(null);
            pnlCatGrid.add(b);
        }
        pnlGrids.add(pnlCatGrid, BorderLayout.CENTER);

        pnlRight.add(pnlGrids, BorderLayout.CENTER);

        // Bottom Pagination
        JPanel pnlPagination = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlPagination.setOpaque(false);
        pnlPagination.add(createPageBtn("\u2302"));
        for(int i=1; i<=7; i++) pnlPagination.add(createPageBtn(String.valueOf(i)));
        pnlPagination.add(createPageBtn(">"));
        pnlRight.add(pnlPagination, BorderLayout.SOUTH);

        pnlBody.add(pnlRight, BorderLayout.EAST);

        add(pnlBody, BorderLayout.CENTER);
    }

    private JButton createHeaderActionBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontBold14);
        btn.setPreferredSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBorder(null);
        return btn;
    }

    private JButton createSidebarAction(String text, String icon, boolean active) {
        JButton btn = new JButton("<html><center><span style='font-size:20px;'>" + icon + "</span><br><br>" + text + "</center></html>");
        btn.setMaximumSize(new Dimension(120, 90));
        btn.setPreferredSize(new Dimension(120, 90));
        btn.setBackground(active ? activeBlueColor : Color.WHITE);
        btn.setForeground(active ? Color.WHITE : Color.DARK_GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(235, 235, 240)));
        btn.setFont(fontSmall);
        return btn;
    }

    private JButton createSidebarAction(String text, String icon) {
        return createSidebarAction(text, icon, false);
    }

    private JButton createNavBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(245, 245, 250));
        b.setForeground(primaryBlue);
        b.setFont(fontBold14);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(new Color(230, 230, 235)));
        return b;
    }

    private JButton createKeypadBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(fontBold14);
        b.setBackground(Color.WHITE);
        b.setBorder(new LineBorder(new Color(230, 230, 235)));
        b.setFocusPainted(false);
        return b;
    }

    private JButton createControlBtn(String text, String icon) {
        JButton b = new JButton("<html><center><span style='font-size:18px;'>" + icon + "</span><br>" + text + "</center></html>");
        b.setBackground(Color.WHITE);
        b.setForeground(Color.GRAY);
        b.setFont(fontSmall);
        b.setBorder(new LineBorder(new Color(230, 230, 235)));
        b.setFocusPainted(false);
        return b;
    }

    private JButton createPageBtn(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(40, 40));
        b.setBackground(text.equals("1") ? activeBlueColor : Color.WHITE);
        b.setForeground(text.equals("1") ? Color.WHITE : Color.GRAY);
        b.setFont(fontBold12);
        b.setBorder(new LineBorder(new Color(230, 230, 235)));
        b.setFocusPainted(false);
        return b;
    }

    private void addSampleCartData() {
        tableModel.addRow(new Object[]{"Croissant", "1", "$2.99", "0", "$2.99"});
        tableModel.addRow(new Object[]{"Strawberry Jam", "2", "$5.00", "0", "$10.00"});
        tableModel.addRow(new Object[]{"Vodafone Top-up", "1", "$5.00", "0", "$5.00"});
        tableModel.addRow(new Object[]{"Wheat Braed", "2", "$2.00", "0", "$4.00"});
    }

    private void addSampleProducts(JPanel grid) {
        String[] names = {"Croissant", "Choco Chip Cookie", "French Bread", "Wheat Bread", "White Bread", "Eclair"};
        String[] prices = {"$1.50", "$1.50", "$2.50", "$2.00", "$2.00", "$2.00"};
        for(int i=0; i<18; i++) {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(Color.WHITE);
            p.setBorder(new LineBorder(new Color(235, 235, 240)));
            
            JLabel lblImg = new JLabel("IMG", SwingConstants.CENTER);
            lblImg.setOpaque(true);
            lblImg.setBackground(new Color(248, 249, 250));
            lblImg.setPreferredSize(new Dimension(80, 60));
            p.add(lblImg, BorderLayout.CENTER);
            
            JPanel pnlInfo = new JPanel(new GridLayout(2, 1));
            pnlInfo.setBackground(new Color(50, 50, 50));
            JLabel lblN = new JLabel(names[i % names.length], SwingConstants.CENTER);
            lblN.setForeground(Color.WHITE);
            lblN.setFont(fontSmall);
            JLabel lblP = new JLabel(prices[i % prices.length], SwingConstants.CENTER);
            lblP.setForeground(Color.LIGHT_GRAY);
            lblP.setFont(fontSmall);
            pnlInfo.add(lblN);
            pnlInfo.add(lblP);
            p.add(pnlInfo, BorderLayout.SOUTH);
            
            grid.add(p);
        }
    }

    private void generateReceipt() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "The cart is empty!", "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("--------------------------------------------\n");
        receipt.append("            RETAIL SUPERMARKET            \n");
        receipt.append("--------------------------------------------\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        receipt.append("Date: ").append(sdf.format(new Date())).append("\n");
        receipt.append("--------------------------------------------\n");
        receipt.append(String.format("%-18s %3s %5s %9s\n", "Item", "Qty", "Disc", "Total"));
        receipt.append("--------------------------------------------\n");

        double grandTotal = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String item = (String) tableModel.getValueAt(i, 0);
            String qty = String.valueOf(tableModel.getValueAt(i, 1));
            String discount = String.valueOf(tableModel.getValueAt(i, 3));
            String totalStr = (String) tableModel.getValueAt(i, 4);
            
            receipt.append(String.format("%-18s %3s %5s %9s\n", 
                item.length() > 18 ? item.substring(0, 15) + "..." : item, 
                qty, discount + "%", totalStr));
            
            try {
                grandTotal += Double.parseDouble(totalStr.replace("$", ""));
            } catch (Exception ex) {}
        }

        receipt.append("--------------------------------------------\n");
        receipt.append(String.format("GRAND TOTAL:                  $%.2f\n", grandTotal));
        receipt.append("--------------------------------------------\n");
        receipt.append("            THANK YOU - COME AGAIN!           \n");
        receipt.append("--------------------------------------------\n");

        JTextArea area = new JTextArea(receipt.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        area.setBackground(new Color(255, 255, 248));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(380, 500));
        sp.setBorder(new LineBorder(new Color(220, 220, 220)));

        // Custom Dialog with Print Button
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sales Receipt", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(sp, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(Color.WHITE);
        
        JButton btnPrint = new JButton("Print Receipt");
        styleDialogButton(btnPrint, primaryBlue);
        
        JButton btnClose = new JButton("Close");
        styleDialogButton(btnClose, Color.GRAY);

        pnlButtons.add(btnPrint);
        pnlButtons.add(btnClose);
        dialog.add(pnlButtons, BorderLayout.SOUTH);

        btnClose.addActionListener(e -> dialog.dispose());
        btnPrint.addActionListener(e -> {
            try {
                area.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Printing failed: " + ex.getMessage());
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        // Clear cart after completing transaction (once dialog is closed)
        tableModel.setRowCount(0);
        txtInput.setText("");
    }

    private void styleDialogButton(JButton btn, Color bg) {
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontBold12);
        btn.setFocusPainted(false);
        btn.setBorder(null);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                
                // Adjust Right Panel Width based on aspect ratio
                double aspectRatio = (double) width / height;
                
                if (width > 1600) {
                    pnlRight.setPreferredSize(new Dimension(800, 0));
                } else if (width > 1200) {
                    pnlRight.setPreferredSize(new Dimension(600, 0));
                } else if (aspectRatio < 1.4) { // Square or near-square (like 15x15 inch terminals)
                    pnlRight.setPreferredSize(new Dimension((int)(width * 0.45), 0));
                } else if (width > 900) {
                    pnlRight.setPreferredSize(new Dimension(450, 0));
                } else {
                    pnlRight.setPreferredSize(new Dimension(width / 2, 0));
                }

                // Adjust Keypad Height based on total height
                int keypadHeight = (int)(height * 0.42);
                if (keypadHeight < 300) keypadHeight = 300;
                if (keypadHeight > 450) keypadHeight = 450;
                pnlKeypadArea.setPreferredSize(new Dimension(0, keypadHeight));

                // Adjust Product Grid Columns
                int rightWidth = pnlRight.getPreferredSize().width;
                int prodCols = Math.max(2, rightWidth / 95);
                ((GridLayout) pnlProdGrid.getLayout()).setColumns(prodCols);

                // Adjust Category Grid Columns
                int catCols = Math.max(2, rightWidth / 110);
                ((GridLayout) pnlCatGrid.getLayout()).setColumns(catCols);

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
}
