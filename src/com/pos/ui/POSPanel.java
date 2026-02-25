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
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color sidebarBg = new Color(255, 255, 255);
    private final Color lightGrayBg = new Color(245, 245, 250);
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
    private JPanel pnlHeader;
    private JButton btnMode;

    public POSPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));
        initComponents();
        setupResponsiveness();
        startTimer();
    }

    private void initComponents() {
        // --- Top Header ---
        pnlHeader = new JPanel(new BorderLayout());
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
        
        btnMode = createHeaderActionBtn("Walk In-Take Away", darkBlue);
        
        JPanel pnlPrinter = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlPrinter.setOpaque(false);
        pnlPrinter.add(new JLabel("Printer"));
        JLabel lblOn = new JLabel(" ON ");
        lblOn.setOpaque(true);
        lblOn.setBackground(actionBlue);
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
        tableHeader.setBackground(tableHeaderBlue);
        tableHeader.setForeground(Color.WHITE);
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
        btnEnter.setBackground(actionBlue);
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

        double gross = 0;
        double disc = 55.00; // Sample static discount for demo
        
        StringBuilder itemsHtml = new StringBuilder();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String name = (String) tableModel.getValueAt(i, 0);
            String qty = String.valueOf(tableModel.getValueAt(i, 1));
            String price = String.valueOf(tableModel.getValueAt(i, 2)).replace("$", "");
            String total = String.valueOf(tableModel.getValueAt(i, 4)).replace("$", "");
            
            try { gross += Double.parseDouble(total); } catch(Exception e) {}

            itemsHtml.append("<tr>")
                     .append("<td align='left' style='padding-top:8px;'>").append(i + 1).append("</td>")
                     .append("<td align='left' colspan='4' style='padding-top:8px;'>").append(name.toUpperCase()).append("</td>")
                     .append("</tr>")
                     .append("<tr>")
                     .append("<td></td><td></td>")
                     .append("<td align='right'>").append(price).append("</td>")
                     .append("<td align='right'>").append(qty).append(".0</td>")
                     .append("<td align='right'>").append(total).append("</td>")
                     .append("</tr>");
        }

        double net = Math.max(0, gross - disc);
        String dateStr = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date());

        String html = "<html><body style='font-family:Segoe UI; padding:10px;'>" +
                "<div style='text-align:center; width:280px;'>" +
                "<h1 style='color:#0D47A1; margin:0;'>POS BILL</h1>" +
                "<p style='margin:5px 0; font-size:11px;'>No.248, Pallegama , Embilpitiya.<br>" +
                "0772 062 970 / Store Code: SREP</p>" +
                "<p style='font-size:10px; margin:5px 0;'>" + dateStr + " C:66001 R:26661</p>" +
                "</div>" +
                "<hr style='border: 0; border-top: 1px dashed #ccc; width:280px; text-align:left;'>" +
                "<table width='280' style='font-size:11px; border-collapse:collapse;'>" +
                "<tr>" +
                "<th align='left' width='10%'>Ln</th>" +
                "<th align='left' width='40%'>Item</th>" +
                "<th align='right' width='18%'>Price</th>" +
                "<th align='right' width='12%'>Qty</th>" +
                "<th align='right' width='20%'>Amount</th>" +
                "</tr>" +
                "<tr><td colspan='5' style='border-top:1px solid #000;'></td></tr>" +
                itemsHtml.toString() +
                "</table>" +
                "<hr style='border: 0; border-top: 1px solid #000; width:280px; text-align:left;'>" +
                "<table width='280' style='font-size:12px; font-weight:bold;'>" +
                "<tr><td>Gross Amount</td><td align='right'>" + String.format("%.2f", gross) + "</td></tr>" +
                "<tr><td>Promotion Discount</td><td align='right'>" + String.format("%.2f", disc) + "</td></tr>" +
                "<tr><td style='font-size:14px;'>Net Amount</td><td align='right' style='font-size:14px;'>" + String.format("%.2f", net) + "</td></tr>" +
                "<tr><td>Cash</td><td align='right'>" + String.format("%.2f", gross) + "</td></tr>" +
                "</table>" +
                "<hr style='border: 0; border-top: 1px dashed #ccc; width:280px; text-align:left;'>" +
                "<div style='text-align:center; font-size:10px; margin-top:10px; width:280px;'>" +
                "THANK YOU - COME AGAIN!" +
                "</div>" +
                "</body></html>";

        JEditorPane area = new JEditorPane("text/html", html);
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        
        JScrollPane sp = new JScrollPane(area);
        int dialogH = Math.min(550, (int)(getHeight() * 0.85));
        sp.setPreferredSize(new Dimension(380, dialogH));
        sp.setBorder(new LineBorder(new Color(220, 220, 220)));

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
                
                // Threshold-based font scaling
                float scale = 1.0f;
                if (width < 850) scale = 0.75f;
                else if (width < 1100) scale = 0.85f;
                else if (width < 1300) scale = 0.95f;
                
                updateFontsRecursive(POSPanel.this, scale);

                // Adjust Header height and padding based on window height
                if (height < 700) {
                    pnlHeader.setPreferredSize(new Dimension(0, 60));
                    pnlHeader.setBorder(new EmptyBorder(2, 10, 2, 10));
                    btnMode.setPreferredSize(new Dimension(130, 35));
                } else {
                    pnlHeader.setPreferredSize(new Dimension(0, 80));
                    pnlHeader.setBorder(new EmptyBorder(5, 20, 5, 20));
                    btnMode.setPreferredSize(new Dimension(180, 40));
                }

                // Adjust Right Panel Proportional Width
                int rightW;
                if (width < 900) {
                    rightW = (int)(width * 0.55); // More space for products on smaller widths
                } else {
                    rightW = (int)(width * 0.45);
                }
                // Cap width for useability
                if (rightW < 320) rightW = 320;
                if (rightW > 700) rightW = 700;
                pnlRight.setPreferredSize(new Dimension(rightW, 0));

                // Adjust Keypad Height - scale with window height
                int keypadH = (int)(height * 0.42);
                if (keypadH < 260) keypadH = 260; 
                if (keypadH > 450) keypadH = 450;
                pnlKeypadArea.setPreferredSize(new Dimension(0, keypadH));

                // Grid Columns - optimize for available width in pnlRight
                int rWidth = pnlRight.getPreferredSize().width;
                ((GridLayout) pnlProdGrid.getLayout()).setColumns(Math.max(2, rWidth / 90));
                ((GridLayout) pnlCatGrid.getLayout()).setColumns(Math.max(2, rWidth / 95));

                revalidate();
                repaint();
            }
        });
    }

    private void updateFontsRecursive(Container container, float scale) {
        for (Component c : container.getComponents()) {
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                Font current = jc.getFont();
                if (current != null) {
                    Font original = (Font) jc.getClientProperty("originalFont");
                    if (original == null) {
                        jc.putClientProperty("originalFont", current);
                        original = current;
                    }
                    float newSize = original.getSize2D() * scale;
                    if (newSize < 9.0f) newSize = 9.0f; // Minimum readable size
                    jc.setFont(original.deriveFont(newSize));
                }
            }
            if (c instanceof Container) {
                updateFontsRecursive((Container) c, scale);
            }
        }
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            lblTime.setText(sdf.format(new Date()));
        });
        timer.start();
    }
}