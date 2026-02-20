package poin_of_sale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.*;

public class POS_UI extends JFrame {

    // Fonts - Closely matching the clean look
    private final Font fontBold22 = new Font("Segoe UI", Font.BOLD, 22);
    private final Font fontBold20 = new Font("Segoe UI", Font.BOLD, 20);
    private final Font fontBold18 = new Font("Segoe UI", Font.BOLD, 18);
    private final Font fontBold16 = new Font("Segoe UI", Font.BOLD, 16);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontBold12 = new Font("Segoe UI", Font.BOLD, 12);
    private final Font fontPlain12 = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font fontPlain10 = new Font("Segoe UI", Font.PLAIN, 10);
    private final Font fontBold10 = new Font("Segoe UI", Font.BOLD, 10);
    private final Font fontEmoji32 = new Font("Segoe UI Emoji", Font.PLAIN, 32);

    // Logic Components
    private DefaultTableModel model;
    private JTextField txtInput;
    private JButton btnEnter; // Declared as field to avoid scoping issues
    
    public POS_UI() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initComponents() {
        setTitle("Retail Supermarket POS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Colors ---
        Color darkBlue = new Color(10, 50, 90);
        Color lightBlue = new Color(20, 100, 160);
        Color greenColor = new Color(40, 180, 80);
        Color lightGray = new Color(245, 245, 245);

        // --- Header ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel pnlHeaderLeft = new JPanel(new GridLayout(2, 1));
        pnlHeaderLeft.setOpaque(false);
        JLabel lblTitle = new JLabel("RETAIL SUPERMARKET");
        lblTitle.setFont(fontBold20);
        JLabel lblDate = new JLabel(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date()));
        lblDate.setFont(fontPlain12);
        lblDate.setForeground(Color.GRAY);
        pnlHeaderLeft.add(lblTitle);
        pnlHeaderLeft.add(lblDate);

        JPanel pnlHeaderRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlHeaderRight.setOpaque(false);
        
        JButton btnWalkIn = createFlatButton("Walk In-Take Away", darkBlue, Color.WHITE);
        btnWalkIn.setPreferredSize(new Dimension(160, 40));
        
        JPanel pnlPrinter = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlPrinter.setOpaque(false);
        JLabel lblPrinter = new JLabel("Printer");
        lblPrinter.setFont(fontBold14);
        JLabel lblOn = new JLabel(" ON ");
        lblOn.setOpaque(true);
        lblOn.setBackground(greenColor);
        lblOn.setForeground(Color.WHITE);
        lblOn.setFont(fontBold12);
        lblOn.setBorder(new EmptyBorder(5, 10, 5, 10)); // Rounded effectively by label
        pnlPrinter.add(lblPrinter);
        pnlPrinter.add(lblOn);

        JLabel lblUser = new JLabel(new VectorIcon("User", 40, 40, darkBlue));

        pnlHeaderRight.add(btnWalkIn);
        pnlHeaderRight.add(pnlPrinter);
        pnlHeaderRight.add(lblUser);

        pnlHeader.add(pnlHeaderLeft, BorderLayout.WEST);
        pnlHeader.add(pnlHeaderRight, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(lightGray);
        GridBagConstraints gbc = new GridBagConstraints();

        // -- Left Pane (Item List + Keypad) --
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 5);
        pnlMain.add(createLeftPane(), gbc);

        // -- Right Pane (Products) --
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        gbc.insets = new Insets(10, 5, 10, 10);
        pnlMain.add(createRightPane(), gbc);

        add(pnlMain, BorderLayout.CENTER);
    }

    private JPanel createLeftPane() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        btnEnter = new JButton("PAY"); // Initialize the field

        // 1. Sidebar (Icons)
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(Color.WHITE);
        pnlSidebar.setPreferredSize(new Dimension(90, 0)); // Slightly wider for better icon spacing
        
        // Define sidebar items: Text, IconType
        String[][] items = {
            {"Discount", "Discount"},
            {"Stock", "Stock"},
            {"Customer", "Customer"},
            {"Search(F2)", "Search"},
            {"Receipt", "Receipt"},
            {"Void", "Void"},
            {"Price Check", "PriceTag"},
            {"Print", "Print"}
        };

        for (String[] item : items) {
            String txt = item[0];
            String type = item[1];
            
            boolean isActive = txt.equals("Customer");
            Color fg = isActive ? Color.WHITE : Color.GRAY;
            Color bg = isActive ? new Color(10, 50, 90) : Color.WHITE;
            
            JButton btn = new JButton(new VectorIcon(type, 32, 28, fg));
            btn.setText("<html><center>" + txt + "</center></html>");
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setPreferredSize(new Dimension(80, 75));
            btn.setMaximumSize(new Dimension(80, 75));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(bg);
            btn.setForeground(fg);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 10)); // Small font for labels
            btn.setBorder(new LineBorder(new Color(240,240,240), 1));
            
            btn.setBorder(new LineBorder(new Color(240,240,240), 1));
            
            if (type.equals("Receipt")) {
                btn.addActionListener(e -> showReceiptDialog());
            }
            
            pnlSidebar.add(Box.createVerticalStrut(5));
            pnlSidebar.add(btn);
        }
        
        // Add glue to push bottom buttons down
        pnlSidebar.add(Box.createVerticalGlue());
        
        // Bottom Navigation arrows (< >)
        // Bottom Navigation arrows ( < > )
        JPanel pnlSideNav = new JPanel(new GridLayout(1, 2, 8, 0)); 
        pnlSideNav.setBackground(Color.WHITE);
        pnlSideNav.setBorder(new EmptyBorder(5, 8, 5, 8)); // Balanced padding
        pnlSideNav.setMaximumSize(new Dimension(90, 50)); // Approx 40px height for buttons
        
        // Smaller Icons for Navigation
        JButton btnPrev = new JButton(new VectorIcon("ChevronLeft", 10, 16, new Color(10, 50, 90)));
        btnPrev.setBackground(new Color(235, 235, 235));
        btnPrev.setBorder(null);
        btnPrev.setFocusPainted(false);
        
        JButton btnNext = new JButton(new VectorIcon("ChevronRight", 10, 16, new Color(10, 50, 90)));
        btnNext.setBackground(new Color(235, 235, 235));
        btnNext.setBorder(null);
        btnNext.setFocusPainted(false);
        
        pnlSideNav.add(btnPrev);
        pnlSideNav.add(btnNext);
        pnlSidebar.add(pnlSideNav);
        pnlSidebar.add(Box.createVerticalStrut(8));

        panel.add(pnlSidebar, BorderLayout.WEST);

        // 2. Center of Left Pane (Table + Keypad)
        JPanel pnlCenterLeft = new JPanel(new BorderLayout(5, 5));
        pnlCenterLeft.setOpaque(false);

        // Table
        // Table
        String[] columns = {"Item Name", "Quantity", "U/Price", "Dis%", "Total"};
        Object[][] data = {
            {"Croissant", "1", "$2.99", "0", "$2.99"},
            {"Strawberry Jam", "2", "$5.00", "0", "$10.00"},
            {"Vodafone Top-up", "1", "$5.00", "0", "$5.00"},
            {"Wheat Braed", "2", "$2.00", "0", "$4.00"}
        };
        model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(fontPlain12);
        table.getTableHeader().setBackground(new Color(230, 230, 230));
        table.getTableHeader().setFont(fontBold12);
        JScrollPane scrollPane = new JScrollPane(table);
        pnlCenterLeft.add(scrollPane, BorderLayout.CENTER);

        // Bottom Section (Input + Keypad)
        JPanel pnlBottom = new JPanel(new BorderLayout(5, 5));
        pnlBottom.setOpaque(false);
        pnlBottom.setPreferredSize(new Dimension(0, 380)); // Slightly taller

        // Input
        JPanel pnlInput = new JPanel(new BorderLayout());
        pnlInput.setBackground(new Color(100, 100, 100)); // Dark gray input bg
        JLabel lblQty = new JLabel(" 1x ", SwingConstants.CENTER);
        lblQty.setForeground(Color.WHITE);
        lblQty.setFont(fontBold16);
        lblQty.setPreferredSize(new Dimension(60, 0));
        
        txtInput = new JTextField("");
        txtInput.setFont(fontBold16);
        txtInput.setBorder(null);
        txtInput.setForeground(Color.BLACK);
        
        pnlInput.add(lblQty, BorderLayout.WEST);
        pnlInput.add(txtInput, BorderLayout.CENTER);
        pnlInput.setPreferredSize(new Dimension(0, 45));
        
        // Add Enter Key Listener to Input field
        txtInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnEnter.doClick(); // Fire the Enter button logic
                }
            }
        });
        
        pnlBottom.add(pnlInput, BorderLayout.NORTH);

        // Keypad + Actions Container
        JPanel pnlKeypadContainer = new JPanel(new GridBagLayout());
        pnlKeypadContainer.setOpaque(false);
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.fill = GridBagConstraints.BOTH;
        gbcK.insets = new Insets(2,2,2,2);

        // Numpad (4x4 including Backspace, +, -)
        // 7 8 9 Backspace
        // 4 5 6 +
        // 1 2 3 -
        // . 0 * Back
        
        String[][] numLayout = {
            {"7", "8", "9", "⌫"},
            {"4", "5", "6", "+"},
            {"1", "2", "3", "-"},
            {".", "0", "*", "Back"}
        };
        
        ActionListener keypadListener = e -> {
            String cmd = e.getActionCommand();
            if (cmd.equals("⌫")) { // Backspace icon button text was cleared, so this checks original? No, button text is empty.
                // We need to handle this differently or set action command.
                // Handled below by checking source or setting action command.
            } else if (cmd.equals("Back")) { // Clear
                 txtInput.setText("");
            } else if (cmd.equals("Entering")) {
                // Enter logic
            } else {
               txtInput.setText(txtInput.getText() + cmd);
            }
        };

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                String txt = numLayout[r][c];
                JButton b = new JButton(txt);
                b.setFont(fontBold20);
                b.setBackground(new Color(235, 235, 235));
                b.setFocusPainted(false);
                
                if(txt.equals("⌫")) {
                    b.setText("");
                    b.setIcon(new VectorIcon("Backspace", 24, 24, Color.BLACK));
                    b.setActionCommand("BACKSPACE");
                    b.addActionListener(e -> {
                        String t = txtInput.getText();
                        if (t.length() > 0) txtInput.setText(t.substring(0, t.length()-1));
                    });
                } else if (txt.equals("Back")) {
                     b.addActionListener(e -> txtInput.setText(""));
                } else {
                     b.addActionListener(e -> txtInput.setText(txtInput.getText() + txt));
                }
                
                gbcK.gridx = c;
                gbcK.gridy = r;
                gbcK.weightx = 1.0;
                gbcK.weighty = 1.0;
                pnlKeypadContainer.add(b, gbcK);
            }
        }
        
        // Right Actions (2x2)
        // Hold, Recall
        // Void, Last Receipt
        String[][] actItems = {
            {"Hold(F6)", "Hold"},
            {"Recall", "Recall"},
            {"Void", "Void"},
            {"Last Receipt", "Receipt"}
        };
        
        for (int i=0; i<4; i++) {
            String txt = actItems[i][0];
            String type = actItems[i][1];
            
            JButton b = new JButton("<html><center>" + txt + "</center></html>");
            b.setIcon(new VectorIcon(type, 20, 20, Color.DARK_GRAY));
            b.setVerticalTextPosition(SwingConstants.BOTTOM);
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            b.setBackground(Color.WHITE);
            b.setFont(fontPlain12);
            b.setFocusPainted(false);
            
            if (type.equals("Receipt")) {
                b.addActionListener(e -> showReceiptDialog());
            }

            // Calc grid pos (right of numpad)
            // r: i/2, c: 4 + (i%2)
            gbcK.gridx = 4 + (i % 2);
            gbcK.gridy = (i / 2) * 2; // Span 2 rows each? No, just 2 rows total in space of 4
            gbcK.gridheight = 2; // Make these tall
            gbcK.weightx = 1.0;
            pnlKeypadContainer.add(b, gbcK);
        }

        pnlBottom.add(pnlKeypadContainer, BorderLayout.CENTER);

        // Big PAY Button
        btnEnter.setText("PAY"); // Use pre-declared button
        btnEnter.setBackground(new Color(40, 180, 80));
        btnEnter.setForeground(Color.WHITE);
        btnEnter.setFont(fontBold22);
        btnEnter.setPreferredSize(new Dimension(0, 60));
        btnEnter.setFocusPainted(false);
        btnEnter.addActionListener(e -> {
            try {
                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No items in the list!", "Empty Transaction", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                double cash = 0.0;
                String input = txtInput.getText().trim();
                if (!input.isEmpty()) {
                    try {
                        cash = Double.parseDouble(input);
                    } catch (NumberFormatException nfe) {
                        // If not a number, proceed with 0 cash (just show total)
                    }
                }
                
                showReceiptDialog(cash);
                txtInput.setText(""); // Clear input after payment logic
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating receipt: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlBottom.add(btnEnter, BorderLayout.SOUTH);

        pnlCenterLeft.add(pnlBottom, BorderLayout.SOUTH);

        panel.add(pnlCenterLeft, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRightPane() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        // 1. Navigation / Search Header
        JPanel pnlHeaderContainer = new JPanel(new BorderLayout(0, 5));
        pnlHeaderContainer.setOpaque(false);
        pnlHeaderContainer.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Row 1: Breadcrumbs (Home >)
        JPanel pnlBreadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlBreadcrumb.setOpaque(false);
        
        JButton btnBreadHome = new JButton(new VectorIcon("Home", 18, 18, new Color(10, 50, 90)));
        btnBreadHome.setBorder(null);
        btnBreadHome.setContentAreaFilled(false);
        btnBreadHome.setFocusPainted(false);
        
        JLabel lblChevron = new JLabel(">");
        lblChevron.setFont(fontBold14);
        lblChevron.setForeground(Color.GRAY);
        
        pnlBreadcrumb.add(btnBreadHome);
        pnlBreadcrumb.add(lblChevron);
        
        pnlHeaderContainer.add(pnlBreadcrumb, BorderLayout.NORTH);

        // Row 2: Controls (< Search >)
        JPanel pnlControls = new JPanel(new BorderLayout(10, 0));
        pnlControls.setOpaque(false);
        pnlControls.setPreferredSize(new Dimension(0, 45));

        JButton btnBack = new JButton(new VectorIcon("ChevronLeft", 16, 24, new Color(10, 50, 90)));
        btnBack.setPreferredSize(new Dimension(45, 45));
        btnBack.setBackground(new Color(230, 230, 230));
        btnBack.setBorder(null);
        btnBack.setFocusPainted(false);

        JButton btnNext = new JButton(new VectorIcon("ChevronRight", 16, 24, new Color(10, 50, 90)));
        btnNext.setPreferredSize(new Dimension(45, 45));
        btnNext.setBackground(new Color(230, 230, 230));
        btnNext.setBorder(null);
        btnNext.setFocusPainted(false);
        
        // Custom Search Field Panel
        JPanel pnlSearch = new JPanel(new BorderLayout());
        pnlSearch.setBackground(new Color(230, 230, 230));
        pnlSearch.setBorder(new EmptyBorder(5, 10, 5, 10)); // Padding inside
        
        JLabel lblSearchIcon = new JLabel(new VectorIcon("Search", 16, 16, Color.GRAY));
        lblSearchIcon.setBorder(new EmptyBorder(0, 0, 0, 10)); // Gap after icon
        
        JTextField txtSearch = new JTextField("Search products...");
        txtSearch.setBackground(new Color(230, 230, 230));
        txtSearch.setBorder(null);
        txtSearch.setFont(fontPlain12);
        txtSearch.setForeground(Color.GRAY);
        
        pnlSearch.add(lblSearchIcon, BorderLayout.WEST);
        pnlSearch.add(txtSearch, BorderLayout.CENTER);

        pnlControls.add(btnBack, BorderLayout.WEST);
        pnlControls.add(pnlSearch, BorderLayout.CENTER);
        pnlControls.add(btnNext, BorderLayout.EAST);

        pnlHeaderContainer.add(pnlControls, BorderLayout.CENTER);

        panel.add(pnlHeaderContainer, BorderLayout.NORTH);

        // 2. Product Grid
        JPanel pnlGrid = new JPanel(new GridLayout(4, 5, 5, 5)); // 4 rows, 5 cols
        pnlGrid.setOpaque(false);

        // Add some dummy products (Top 2 rows)
        String[] products = {"Croissant", "Choco Cookie", "French Bread", "Wheat Bread", "White Bread", "Eclair", 
                             "Vodafone", "Talk Home", "Lebara", "Lycamobile", "Tesco Mobile", "GiffGaff"};
        
        for (String p : products) {
            pnlGrid.add(createProductCard(p, "$2.00"));
        }
        
        // Add categories (Bottom 2 rows - roughly)
        String[] categories = {"Snacks", "Dairy", "Coffee & Tea", "Health", "Bakery", "Beauty", 
                               "Stationery", "Gift Cards", "Favorites", "Fruits", "Spices", "Noodles"};
                               
        for (String c : categories) {
             pnlGrid.add(createCategoryCard(c));
        }

        JScrollPane scrollGrid = new JScrollPane(pnlGrid);
        scrollGrid.setBorder(null);
        scrollGrid.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollGrid, BorderLayout.CENTER);

        // 3. Pagination
        JPanel pnlPage = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlPage.setOpaque(false);
        
        // Home Button
        JButton btnFullHome = new JButton(new VectorIcon("Home", 20, 20, Color.DARK_GRAY));
        btnFullHome.setPreferredSize(new Dimension(45, 45));
        btnFullHome.setBackground(new Color(230, 230, 230));
        btnFullHome.setBorder(null);
        btnFullHome.setFocusPainted(false);
        pnlPage.add(btnFullHome);
        
        // Pages 1-7
        for (int i = 1; i <= 7; i++) {
            JButton b = new JButton(String.valueOf(i));
            b.setPreferredSize(new Dimension(45, 45));
            b.setFocusPainted(false);
            b.setBorder(null);
            if (i == 1) {
                b.setBackground(new Color(10, 50, 90)); // Dark Blue
                b.setForeground(Color.WHITE);
            } else {
                b.setBackground(new Color(230, 230, 230)); // Light Gray
                b.setForeground(Color.DARK_GRAY);
            }
            b.setFont(fontBold14);
            pnlPage.add(b);
        }
        
        // Next Button
        JButton btnFullNext = new JButton(">");
        btnFullNext.setPreferredSize(new Dimension(45, 45));
        btnFullNext.setBackground(new Color(230, 230, 230));
        btnFullNext.setForeground(Color.DARK_GRAY);
        btnFullNext.setFont(fontBold16);
        btnFullNext.setBorder(null);
        btnFullNext.setFocusPainted(false);
        pnlPage.add(btnFullNext);
        
        panel.add(pnlPage, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createProductCard(String name, String price) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        
        JLabel lblImg = new JLabel("", SwingConstants.CENTER);
        
        ImageIcon icon = null;
        try {
            java.io.File imgFile = new java.io.File("images/" + name + ".png");
            if (!imgFile.exists()) {
                imgFile = new java.io.File("images/" + name + ".jpg");
            }
            if (imgFile.exists()) {
                ImageIcon fullIcon = new ImageIcon(imgFile.getPath());
                Image img = fullIcon.getImage();
                Image newImg = img.getScaledInstance(100, 80, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
            }
        } catch (Exception e) {}
        
        if (icon != null) {
            lblImg.setIcon(icon);
        } else {
            // Generate placeholder
            int w = 100;
            int h = 80;
            java.awt.image.BufferedImage bufImg = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int hash = name.hashCode();
            int r = (hash & 0xFF0000) >> 16;
            int g = (hash & 0x00FF00) >> 8;
            int b = (hash & 0x0000FF);
            r = (r + 255) / 2; g = (g + 255) / 2; b = (b + 255) / 2;
            
            g2.setColor(new Color(r, g, b));
            g2.fillRect(0, 0, w, h);
            
            g2.setColor(new Color(50, 50, 50, 100));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 40));
            FontMetrics fm = g2.getFontMetrics();
            String letter = name.substring(0, 1).toUpperCase();
            g2.drawString(letter, (w - fm.stringWidth(letter)) / 2, ((h - fm.getHeight()) / 2) + fm.getAscent());
            g2.dispose();
            lblImg.setIcon(new ImageIcon(bufImg));
        }
        
        JPanel pnlInfo = new JPanel(new BorderLayout());
        pnlInfo.setBackground(new Color(50, 50, 50));
        JLabel lblName = new JLabel(" " + name);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(fontPlain10);
        JLabel lblPrice = new JLabel(price + " ");
        lblPrice.setForeground(Color.WHITE);
        lblPrice.setFont(fontBold10);
        
        pnlInfo.add(lblName, BorderLayout.CENTER);
        pnlInfo.add(lblPrice, BorderLayout.EAST);
        
        p.add(lblImg, BorderLayout.CENTER);
        p.add(pnlInfo, BorderLayout.SOUTH);

        // Click Logic
        p.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addProductToTable(name, price);
            }
             public void mouseEntered(java.awt.event.MouseEvent evt) {
                p.setBorder(new LineBorder(new Color(10, 50, 90), 2));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                p.setBorder(new LineBorder(new Color(220, 220, 220), 1));
            }
        });
        
        return p;
    }

    private void addProductToTable(String name, String priceStr) {
        String cleanPrice = priceStr.replace("$", "").trim();
        double price = 0.0;
        try {
            price = Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {}

        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String tblName = (String) model.getValueAt(i, 0);
            if (tblName.equals(name)) {
                String sQty = (String) model.getValueAt(i, 1);
                int qty = Integer.parseInt(sQty) + 1;
                model.setValueAt(String.valueOf(qty), i, 1);
                
                double newTotal = qty * price;
                model.setValueAt("$" + String.format("%.2f", newTotal), i, 4);
                found = true;
                break;
            }
        }
        
        if (!found) {
            model.addRow(new Object[]{name, "1", priceStr, "0", "$" + String.format("%.2f", price)});
        }
    }

    private JButton createCategoryCard(String name) {
        JButton b = new JButton("<html><center>" + name + "</center></html>");
        b.setBackground(new Color(20, 100, 160));
        b.setForeground(Color.WHITE);
        b.setFont(fontBold12);
        b.setFocusPainted(false);
        return b;
    }

    private JButton createFlatButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(fontBold14);
        return btn;
    }

    // --- Vector Icon Class ---
    private static class VectorIcon implements Icon {
        private final String type;
        private final int width;
        private final int height;
        private final Color color;

        public VectorIcon(String type, int width, int height, Color color) {
            this.type = type;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f));
            g2.translate(x, y);

            int cx = width / 2;
            int cy = height / 2;
            int s = Math.min(width, height);

            switch (type) {
                case "User": // Header user
                case "Customer": // Sidebar customer
                    g2.drawOval(cx - 6, 2, 12, 12); // Head
                    g2.drawArc(cx - 10, 16, 20, 20, 0, 180); // Body (upside down arc?) No, standard arc 0 to 180 is bottom half... 
                    // Actually arc(x,y,w,h, start, extent). 0 is 3 oclock. 180 is 9 oclock.
                    // To draw shoulders:
                    g2.drawArc(cx - 10, 16, 20, 14, 0, 180); // Actually wait, 0-180 is top half.
                    // Let's manually draw a curve for shoulders.
                    g2.draw(new Arc2D.Float(cx - 10, 14, 20, 20, 0, 180, Arc2D.OPEN)); // Flipped?
                    // Let's keep it simple: Circle + Arc
                    g2.fillOval(cx-5, 4, 10, 10);
                    g2.fillArc(cx-10, 16, 20, 14, 0, 180); 
                    break;
                    
                case "Discount": // % icon
                    Font f = new Font("Segoe UI", Font.BOLD, s-8);
                    g2.setFont(f);
                    // Draw a couple of circles and a line
                    g2.drawOval(4, 4, 6, 6);
                    g2.drawOval(width-10, height-10, 6, 6);
                    g2.drawLine(width-6, 6, 6, height-6);
                    break;
                    
                case "Stock": // Box
                    g2.drawRect(4, 8, width-8, height-12);
                    g2.drawLine(4, 8, cx, 2);
                    g2.drawLine(width-4, 8, cx, 2);
                    g2.drawLine(cx, 2, cx, height/2);
                    break;
                    
                case "Search": // Magnifying glass
                    // Optimized for 16x16
                    int d = 9;  // Diameter
                    int p = 3;  // Padding
                    
                    g2.setStroke(new BasicStroke(1.8f));
                    g2.drawOval(p, p, d, d);
                    
                    // Handle
                    g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(p + d - 1, p + d - 1, width - 3, height - 3);
                    break;
                    
                case "Receipt": // Paper with lines
                    g2.drawRect(6, 4, width-12, height-8);
                    g2.drawLine(10, 10, width-10, 10);
                    g2.drawLine(10, 16, width-10, 16);
                    g2.drawLine(10, 22, width-14, 22);
                    break;
                    
                case "Void": 
                     // Trash / X
                     g2.drawRect(8, 6, width-16, height-10);
                     g2.drawLine(10, 10, width-10, height-8);
                     g2.drawLine(width-10, 10, 10, height-8);
                     break;

                case "Backspace":// Pentagon shape <|X|
                     Polygon bh = new Polygon();
                     bh.addPoint(2, cy);           // Tip
                     bh.addPoint(8, 4);            // Top Shoulder
                     bh.addPoint(width-2, 4);      // Top Right
                     bh.addPoint(width-2, height-4);// Bottom Right
                     bh.addPoint(8, height-4);     // Bottom Shoulder
                     
                     g2.setStroke(new BasicStroke(2f));
                     g2.drawPolygon(bh);
                     
                     // Draw X inside
                     int lx = 11; 
                     int rx = width - 5;
                     int ty = 7;
                     int by = height - 7;
                     
                     g2.drawLine(lx, ty, rx, by);
                     g2.drawLine(rx, ty, lx, by);
                     break;
                    
                case "PriceTag": // Tag shape
                    Polygon tag = new Polygon();
                    tag.addPoint(width-4, cy);
                    tag.addPoint(width-10, 4);
                    tag.addPoint(4, 4);
                    tag.addPoint(4, height-4);
                    tag.addPoint(width-10, height-4);
                    g2.drawPolygon(tag);
                    g2.drawOval(width-12, cy-2, 4, 4);
                    break;
                    
                case "Print": // Printer
                    g2.drawRect(6, 10, width-12, height-16); // Body
                    g2.drawRect(10, 4, width-20, 6); // Paper
                    g2.drawLine(4, 16, width-4, 16); // Slot
                    break;
                    
                case "Hold": // ||
                    g2.fillRect(cx-6, 6, 4, height-12);
                    g2.fillRect(cx+2, 6, 4, height-12);
                    break;
                    
                case "Recall": // Circular arrow
                    g2.drawArc(4, 4, width-8, height-8, 45, 270);
                    // Arrowhead
                    g2.drawLine(cx+4, 4, cx+10, 4);
                    break;
                case "Home": // House
                    int h2 = height / 2;
                    // Roof
                    int[] xP = {cx, 3, width-3}; 
                    int[] yP = {2, h2, h2};
                    g2.fillPolygon(xP, yP, 3); 
                    
                    // Base (Wider now)
                    g2.fillRect(5, h2, width-10, h2-2); 
                    
                    // Door
                    g2.setColor(Color.WHITE);
                    g2.fillRect(cx-2, height-7, 4, 5);
                    break;
                    
                case "ChevronLeft":
                    // Matches current 2f stroke
                    g2.drawLine(width - 3, 3, 3, cy);
                    g2.drawLine(3, cy, width - 3, height - 3);
                    break;
                    
                case "ChevronRight":
                    // Matches current 2f stroke
                    g2.drawLine(3, 3, width - 3, cy);
                    g2.drawLine(width - 3, cy, 3, height - 3);
                    break;
            }

            g2.dispose();
        }
        
        private int getWidth(){ return width; }
        private int getHeight(){ return height; }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    private double calculateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object val = model.getValueAt(i, 4);
            if (val != null) {
                try {
                    total += Double.parseDouble(val.toString().replace("$", "").trim());
                } catch (Exception e) {}
            }
        }
        return total;
    }

    private void showReceiptDialog() {
        showReceiptDialog(0.0);
    }

    private void showReceiptDialog(double cashReceived) {
        try {
            JDialog dialog = new JDialog(this, "Transaction Receipt", true);
            dialog.setLayout(new BorderLayout());
            dialog.setSize(550, 800);
            dialog.setLocationRelativeTo(this);
            dialog.getContentPane().setBackground(Color.WHITE);

            JPanel pnlContent = new JPanel();
            pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
            pnlContent.setBackground(Color.WHITE);
            pnlContent.setBorder(new EmptyBorder(30, 40, 30, 40));

            // 1. Header Section
            JLabel lblStore = new JLabel("RETAIL SUPERMARKET");
            lblStore.setFont(fontBold22);
            lblStore.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblAddr = new JLabel("123 Business Road, City Center");
            lblAddr.setFont(fontPlain12);
            lblAddr.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblDate = new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            lblDate.setFont(fontPlain12);
            lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);

            pnlContent.add(lblStore);
            pnlContent.add(Box.createVerticalStrut(5));
            pnlContent.add(lblAddr);
            pnlContent.add(lblDate);
            pnlContent.add(Box.createVerticalStrut(25));
            
            // 2. Item Grid Section
            StringBuilder sb = new StringBuilder();
            sb.append("--------------------------------------------------------\n");
            sb.append("#   ITEM                    QTY      PRICE      AMOUNT\n");
            sb.append("--------------------------------------------------------\n");

            StringBuilder promoSb = new StringBuilder();
            double totalSavingsValue = 0;
            double netTotalValue = 0;

            for (int i = 0; i < model.getRowCount(); i++) {
                Object oName = model.getValueAt(i, 0);
                Object oQty = model.getValueAt(i, 1);
                Object oUPrice = model.getValueAt(i, 2);
                Object oDis = model.getValueAt(i, 3);
                
                if (oName == null) continue;
                
                String name = oName.toString();
                String qtyStr = oQty == null ? "0" : oQty.toString();
                String uPriceStr = oUPrice == null ? "0" : oUPrice.toString();
                String disStr = oDis == null ? "0" : oDis.toString();
                
                try {
                    int qty = Integer.parseInt(qtyStr.trim());
                    double unitPrice = Double.parseDouble(uPriceStr.replace("$", "").trim());
                    double disPercent = Double.parseDouble(disStr.replace("%", "").trim());
                    
                    double gross = qty * unitPrice;
                    double savings = gross * (disPercent / 100.0);
                    double itemNet = gross - savings;
                    
                    totalSavingsValue += savings;
                    netTotalValue += itemNet;

                    // Line 1: Index and Name
                    sb.append((i + 1)).append(") ").append(name.toUpperCase()).append("\n");
                    
                    // Line 2: Details
                    String idStr = "    ID-" + (1000 + i);
                    sb.append(String.format("%-24s %-8d %10.2f %10.2f\n\n", 
                                           idStr, qty, unitPrice, itemNet));

                    // Capture Promotion info
                    if (savings > 0.01) {
                        String disType = disPercent > 0 ? String.format("%.2f%% Dis", disPercent) : "Value Dis";
                        promoSb.append(String.format("%-5d %-12s %-18s %10.2f\n", 
                                        qty, "ID-"+(1000+i), disType, savings));
                    }
                    
                } catch (Exception ex) { }
            }
            sb.append("--------------------------------------------------------\n");

            // --- Promotions Summary Section (Matches Photo) ---
            if (totalSavingsValue > 0.01) {
                sb.append(String.format("Total promotion(s) savings %30.2f\n\n", totalSavingsValue));
                sb.append("Special Promotions\n");
                sb.append(promoSb.toString());
                sb.append("--------------------------------------------------------\n");
            }

            JTextArea areaItems = new JTextArea(sb.toString());
            areaItems.setFont(new Font("Consolas", Font.PLAIN, 13));
            areaItems.setEditable(false);
            areaItems.setBackground(Color.WHITE);
            pnlContent.add(areaItems);

            // 3. Totals Section
            if (totalSavingsValue > 0.01) {
                JPanel pSavings = new JPanel(new BorderLayout());
                pSavings.setOpaque(false);
                pSavings.setMaximumSize(new Dimension(800, 25));
                JLabel lSavingsT = new JLabel("PROMOTION SAVINGS");
                lSavingsT.setFont(fontBold14);
                lSavingsT.setForeground(new Color(40, 180, 80));
                JLabel lSavingsV = new JLabel("-" + String.format("%.2f", totalSavingsValue));
                lSavingsV.setFont(fontBold14);
                lSavingsV.setForeground(new Color(40, 180, 80));
                pSavings.add(lSavingsT, BorderLayout.WEST);
                pSavings.add(lSavingsV, BorderLayout.EAST);
                pnlContent.add(pSavings);
                pnlContent.add(Box.createVerticalStrut(5));
            }

            JPanel pTotal = new JPanel(new BorderLayout());
            pTotal.setOpaque(false);
            pTotal.setMaximumSize(new Dimension(800, 45));
            JLabel lTotalT = new JLabel("TOTAL AMOUNT");
            lTotalT.setFont(fontBold18);
            JLabel lTotalV = new JLabel("$" + String.format("%.2f", netTotalValue));
            lTotalV.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lTotalV.setForeground(new Color(10, 50, 90));
            pTotal.add(lTotalT, BorderLayout.WEST);
            pTotal.add(lTotalV, BorderLayout.EAST);
            pnlContent.add(pTotal);

            // 4. Payment Section (New)
            if (cashReceived > 0.01) {
                pnlContent.add(Box.createVerticalStrut(15));
                
                JPanel pCash = new JPanel(new BorderLayout());
                pCash.setOpaque(false);
                pCash.setMaximumSize(new Dimension(800, 25));
                JLabel lCashT = new JLabel("CASH RECEIVED");
                lCashT.setFont(fontBold14);
                JLabel lCashV = new JLabel("$" + String.format("%.2f", cashReceived));
                lCashV.setFont(fontBold14);
                pCash.add(lCashT, BorderLayout.WEST);
                pCash.add(lCashV, BorderLayout.EAST);
                pnlContent.add(pCash);
                
                JPanel pChange = new JPanel(new BorderLayout());
                pChange.setOpaque(false);
                pChange.setMaximumSize(new Dimension(800, 25));
                JLabel lChangeT = new JLabel("CHANGE");
                lChangeT.setFont(fontBold16);
                double changeAmount = cashReceived - netTotalValue;
                JLabel lChangeV = new JLabel("$" + String.format("%.2f", Math.max(0, changeAmount)));
                lChangeV.setFont(fontBold18); // Defined previously
                lChangeV.setForeground(new Color(200, 40, 40)); // Clear red for change
                pChange.add(lChangeT, BorderLayout.WEST);
                pChange.add(lChangeV, BorderLayout.EAST);
                pnlContent.add(pChange);
            }

            pnlContent.add(Box.createVerticalStrut(40));
            
            JLabel lblFooter = new JLabel("THANK YOU FOR SHOPPING!");
            lblFooter.setFont(fontBold14);
            lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlContent.add(lblFooter);

            JScrollPane scroll = new JScrollPane(pnlContent);
            scroll.setBorder(null);
            dialog.add(scroll, BorderLayout.CENTER);

            JButton btnClose = new JButton("Confirm & Clear Table");
            btnClose.setBackground(new Color(10, 50, 90));
            btnClose.setForeground(Color.WHITE);
            btnClose.setFont(fontBold16);
            btnClose.setPreferredSize(new Dimension(0, 60));
            btnClose.setFocusPainted(false);
            btnClose.addActionListener(e -> {
                dialog.dispose();
                model.setRowCount(0);
            });
            dialog.add(btnClose, BorderLayout.SOUTH);

            dialog.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Internal Error: " + ex.getMessage());
        }
    }
}
