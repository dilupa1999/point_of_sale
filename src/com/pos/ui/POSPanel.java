package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.pos.service.POSService;
import java.util.*;
import com.pos.util.ReceiptPrinter;
import com.pos.service.ConfigService;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class POSPanel extends JPanel {

    private final Color headerBlue = new Color(25, 118, 210);
    private final Color buttonBlue = new Color(43, 107, 224);
    private final Color lightBlue = new Color(100, 181, 246);
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

    // --- State Variables ---
    private boolean isRetailMode = true;
    private com.pos.service.POSService.Customer selectedCustomer = null;

    // --- Component References ---
    private JTextField txtCustomerSearch;
    private JTextField txtBarcode;
    private JTextField txtRightSearch;
    private JTextField txtDiscount;
    private JButton btnRetail;
    private JButton btnWholesale;
    private JButton btnViewHold;
    private JButton btnMinus;
    private JButton btnPlus;
    private JTextField txtQty;
    private JButton btnSync;
    private JLabel lblSyncStatus;
    private JPanel pnlResults;
    private JLabel lblTotalItems, lblTotalQty, lblTotalAmount, lblGrandTotal;
    private JButton btnPendingSync;

    // --- Dropdown for Customer Search ---
    private boolean isCustomerSelecting = false;
    private String lastCustomerSearchQuery = "";
    private javax.swing.Timer customerSearchTimer;
    private JPopupMenu customerPopup;
    private JList<String> customerList;
    private DefaultListModel<String> customerListModel;
    private List<POSService.Customer> currentSearchCustomers;

    public POSPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));
        initComponents();
        setupResponsiveness();
        startTimer();
        setupCustomerSearchDropdown();
        setupPlaceholder(txtCustomerSearch, "Search Customer...");
        setupPlaceholder(txtBarcode, "Enter a valid barcode");
        setupPlaceholder(txtRightSearch, "Search for items...");
        updateSyncStatusCount();
        com.pos.service.SeedData.seed(); // Temporary seed for testing
        performItemSearch(""); // Initial load
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
        pnlBillingHeader.setBackground(headerBlue);
        pnlBillingHeader.setPreferredSize(new Dimension(0, 60));
        pnlBillingHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel pnlTitleGroup = new JPanel(new GridLayout(2, 1));
        pnlTitleGroup.setOpaque(false);

        JLabel lblBillingTitle = new JLabel("Billing system");
        lblBillingTitle.setForeground(Color.WHITE);
        lblBillingTitle.setFont(fontTitle);

        lblTime = new JLabel("Loading date/time...");
        lblTime.setForeground(new Color(220, 230, 250));
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        pnlTitleGroup.add(lblBillingTitle);
        pnlTitleGroup.add(lblTime);
        pnlBillingHeader.add(pnlTitleGroup, BorderLayout.WEST);

        JPanel pnlHeaderBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlHeaderBtns.setOpaque(false);

        lblSyncStatus = new JLabel("Pending Sync: 0");
        lblSyncStatus.setFont(fontBold12);
        lblSyncStatus.setForeground(new Color(130, 130, 130));

        btnRetail = createHeaderBtn("Retail", buttonBlue, Color.WHITE);
        btnWholesale = createHeaderBtn("Wholesale", Color.WHITE, Color.BLACK);
        btnSync = createHeaderBtn("Sync Online", new Color(46, 125, 50), Color.WHITE);

        btnRetail.addActionListener(e -> setPOSMode(true));
        btnWholesale.addActionListener(e -> setPOSMode(false));

        btnViewHold = createHeaderBtn("View Hold List", Color.WHITE, Color.BLACK);
        btnViewHold.addActionListener(e -> showHoldListDialog());
        btnSync.addActionListener(e -> handleManualSync());
        updateHoldCount();
        updateSyncStatusCount();

        com.pos.service.SyncService.setOnSyncCompleteListener(() -> {
            updateSyncStatusCount();
        });

        btnPendingSync = new JButton("View");
        btnPendingSync.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btnPendingSync.setPreferredSize(new Dimension(60, 25));
        btnPendingSync.setBackground(Color.WHITE);
        btnPendingSync.addActionListener(e -> showPendingSyncDialog());

        pnlHeaderBtns.add(lblSyncStatus);
        pnlHeaderBtns.add(btnPendingSync);
        pnlHeaderBtns.add(btnSync);
        pnlHeaderBtns.add(btnViewHold);
        pnlHeaderBtns.add(btnRetail);
        pnlHeaderBtns.add(btnWholesale);
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
        txtCustomerSearch = new JTextField("Search Customer...");
        JPanel pnlCustomer = createInputWrapper(txtCustomerSearch);
        pnlControls.add(pnlCustomer, gbc);

        // User Icon Button
        gbc.weightx = 0.05;
        JButton btnUser = new JButton("\uD83D\uDC64");
        btnUser.setBackground(headerBlue);
        btnUser.setForeground(Color.WHITE);
        btnUser.setPreferredSize(new Dimension(50, 45));
        btnUser.setFocusPainted(false);
        btnUser.setBorder(null);
        btnUser.addActionListener(e -> showAddCustomerDialog());
        pnlControls.add(btnUser, gbc);

        // Barcode Input
        gbc.weightx = 0.4;
        txtBarcode = new JTextField("Enter a valid barcode");
        txtBarcode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleBarcodeScan();
                }
            }
        });
        JPanel pnlBarcode = createInputWrapper(txtBarcode);
        pnlControls.add(pnlBarcode, gbc);

        // Qty Selector
        gbc.weightx = 0.2;
        JPanel pnlQty = new JPanel(new BorderLayout());
        pnlQty.setBorder(new LineBorder(borderGray));
        pnlQty.setBackground(Color.WHITE);

        btnMinus = createQtyBtn("-");
        btnPlus = createQtyBtn("+");
        txtQty = new JTextField("1");
        txtQty.setHorizontalAlignment(SwingConstants.CENTER);
        txtQty.setFont(fontBold14);
        txtQty.setBorder(null);

        btnPlus.addActionListener(e -> {
            try {
                int q = Integer.parseInt(txtQty.getText().trim());
                txtQty.setText(String.valueOf(q + 1));
            } catch (Exception ex) {
                txtQty.setText("1");
            }
        });
        btnMinus.addActionListener(e -> {
            try {
                int q = Integer.parseInt(txtQty.getText().trim());
                if (q > 1)
                    txtQty.setText(String.valueOf(q - 1));
            } catch (Exception ex) {
                txtQty.setText("1");
            }
        });

        txtQty.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleBarcodeScan();
                }
            }
        });

        pnlQty.add(btnMinus, BorderLayout.WEST);
        pnlQty.add(txtQty, BorderLayout.CENTER);
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

        lblTotalItems = createSummaryLabel("Total Items: 0");
        lblTotalQty = createSummaryLabel("Total Quantity: 0");
        lblTotalAmount = createSummaryLabel("Total Amount: Rs. 0.00");
        lblGrandTotal = createSummaryLabel("Grand Total: Rs. 0.00");

        pnlSummary.add(lblTotalItems);
        pnlSummary.add(lblTotalQty);
        pnlSummary.add(lblTotalAmount);
        pnlSummary.add(lblGrandTotal);

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
        txtDiscount = new JTextField("0.00", 15);
        txtDiscount.setPreferredSize(new Dimension(0, 40));
        txtDiscount.setBorder(new LineBorder(borderGray));
        txtDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        txtDiscount.setEditable(false);
        pnlDiscount.add(txtDiscount);
        pnlFooter.add(pnlDiscount, BorderLayout.WEST);

        JPanel pnlActionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlActionBtns.setOpaque(false);

        JButton btnPay = createActionBtn("\uD83D\uDCBB Pay All (Enter)", headerBlue, 180);
        JButton btnHold = createActionBtn("\u26A0 Hold All", lightBlue, 160);
        JButton btnCancel = createActionBtn("\u2298 Cancel", dangerRed, 160);

        btnPay.addActionListener(e -> showPaymentDialog());

        // Ensure the button can be triggered by pressing Enter when focused
        btnPay.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnPay.doClick();
                }
            }
        });

        // Add a global shortcut so pressing Enter anywhere without another input
        // consuming it triggers Pay All
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "payAllGlobal");
        this.getActionMap().put("payAllGlobal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtCustomerSearch.hasFocus() || txtRightSearch.hasFocus())
                    return;
                btnPay.doClick();
            }
        });

        btnCancel.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Clear current bill?", "Cancel Bill",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                tableModel.setRowCount(0);
                updateSummary();
            }
        });
        btnHold.addActionListener(e -> handleHoldSale());

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

        txtRightSearch = new JTextField("Search for items...");
        txtRightSearch.setBorder(null);
        txtRightSearch.setOpaque(false);
        txtRightSearch.setForeground(Color.GRAY);
        txtRightSearch.setFont(fontPlain14);
        pnlSearchBar.add(txtRightSearch, BorderLayout.CENTER);

        JPanel pnlSearchIcons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlSearchIcons.setOpaque(false);
        pnlSearchIcons.add(new JLabel("\uD83D\uDD0D")); // Search icon
        pnlSearchIcons.add(new JLabel("X")); // Close icon
        pnlSearchBar.add(pnlSearchIcons, BorderLayout.EAST);

        pnlSearchBack.add(pnlSearchBar, BorderLayout.NORTH);
        pnlRightFinal.add(pnlSearchBack, BorderLayout.NORTH);

        // Result results with ScrollPane
        pnlResults = new JPanel(new GridLayout(0, 3, 5, 5));
        pnlResults.setBackground(Color.WHITE);
        JScrollPane scrollResults = new JScrollPane(pnlResults);
        scrollResults.setBorder(null);
        scrollResults.getVerticalScrollBar().setUnitIncrement(16);
        pnlRightFinal.add(scrollResults, BorderLayout.CENTER);

        pnlMain.add(pnlRightFinal, BorderLayout.CENTER);
        // Search Listeners
        txtRightSearch.addActionListener(e -> performItemSearch(txtRightSearch.getText().trim()));

        add(pnlMain);
    }

    private void setupCustomerSearchDropdown() {
        customerPopup = new JPopupMenu();
        customerPopup.setFocusable(false);
        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        customerList.setFont(fontPlain14);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerList.setFocusable(false);

        customerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectCustomerFromList();
                }
            }
        });

        customerSearchTimer = new javax.swing.Timer(300, e -> {
            if (isCustomerSelecting)
                return;
            String query = txtCustomerSearch.getText().trim();
            if (!query.equals(lastCustomerSearchQuery)) {
                performCustomerSearch(query);
                lastCustomerSearchQuery = query;
            }
        });
        customerSearchTimer.setRepeats(false);

        txtCustomerSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                customerSearchTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                customerSearchTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                customerSearchTimer.restart();
            }
        });

        // Open dropdown on click
        txtCustomerSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!customerPopup.isVisible()) {
                    performCustomerSearch(txtCustomerSearch.getText().trim());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(customerList);
        scroll.setPreferredSize(new Dimension(240, 150));
        scroll.setFocusable(false);
        scroll.getVerticalScrollBar().setFocusable(false);
        customerPopup.add(scroll);

        txtCustomerSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!customerPopup.isVisible() || customerListModel.isEmpty())
                    return;

                int index = customerList.getSelectedIndex();
                int size = customerListModel.getSize();

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    index = (index + 1) % size;
                    customerList.setSelectedIndex(index);
                    customerList.ensureIndexIsVisible(index);
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    index = (index - 1 + size) % size;
                    customerList.setSelectedIndex(index);
                    customerList.ensureIndexIsVisible(index);
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (index != -1) {
                        selectCustomerFromList();
                        e.consume();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    customerPopup.setVisible(false);
                }
            }
        });
    }

    private void selectCustomerFromList() {
        int index = customerList.getSelectedIndex();
        if (index != -1 && currentSearchCustomers != null && index < currentSearchCustomers.size()) {
            selectCustomer(currentSearchCustomers.get(index));
            customerPopup.setVisible(false);
        }
    }

    private void performCustomerSearch(String query) {
        currentSearchCustomers = POSService.searchCustomers(query);

        SwingUtilities.invokeLater(() -> {
            customerListModel.clear();
            if (currentSearchCustomers.isEmpty()) {
                customerPopup.setVisible(false);
                return;
            }

            for (POSService.Customer c : currentSearchCustomers) {
                customerListModel.addElement(c.name + " (" + c.mobile + ")");
            }

            if (!customerPopup.isVisible() && txtCustomerSearch.isShowing()) {
                customerPopup.show(txtCustomerSearch, 0, txtCustomerSearch.getHeight());
            }
        });
    }

    private void selectCustomer(POSService.Customer customer) {
        isCustomerSelecting = true;
        this.selectedCustomer = customer;
        String val = customer.name + " (" + customer.mobile + ")";
        lastCustomerSearchQuery = val; // Synchronize last query
        txtCustomerSearch.setText(val);
        txtCustomerSearch.setForeground(headerBlue);
        isCustomerSelecting = false;
    }

    private void addItemToTable(POSService.Item item) {
        int qty = 1;
        try {
            qty = Integer.parseInt(txtQty.getText().trim());
        } catch (Exception e) {
        }
        double price = isRetailMode ? item.retailPrice : item.wholesalePrice;
        double subtotal = price * qty;

        // Check if item already exists in table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(item.name)) {
                int oldQty = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                int newQty = oldQty + qty;
                tableModel.setValueAt(String.valueOf(newQty), i, 1);
                tableModel.setValueAt(String.format("%.2f", price * newQty), i, 5);
                updateSummary();
                return;
            }
        }

        tableModel.addRow(new Object[] { item.name, String.valueOf(qty), String.format("%.2f", price), "0", "0.00",
                String.format("%.2f", subtotal) });
        updateSummary();
    }

    private void updateSummary() {
        int totalItems = tableModel.getRowCount();
        int totalQty = 0;
        double totalAmount = 0;
        double totalDiscount = 0;

        for (int i = 0; i < totalItems; i++) {
            totalQty += Integer.parseInt(tableModel.getValueAt(i, 1).toString());
            totalAmount += Double.parseDouble(tableModel.getValueAt(i, 5).toString());
            totalDiscount += Double.parseDouble(tableModel.getValueAt(i, 4).toString());
        }

        lblTotalItems.setText("Total Items: " + totalItems);
        lblTotalQty.setText("Total Quantity: " + totalQty);
        lblTotalAmount.setText(String.format("Total Amount: Rs. %.2f", totalAmount));
        lblGrandTotal.setText(String.format("Grand Total: Rs. %.2f", totalAmount - totalDiscount));
        txtDiscount.setText(String.format("%.2f", totalDiscount));
    }

    private void performItemSearch(String query) {
        pnlResults.removeAll();
        List<POSService.Item> items = POSService.searchItems(query);
        for (POSService.Item item : items) {
            JButton btn = new JButton("<html><center>" + item.name + "<br>Rs."
                    + (isRetailMode ? item.retailPrice : item.wholesalePrice) + "</center></html>");
            btn.setFont(fontBold12);
            btn.setBackground(Color.WHITE);
            btn.setBorder(new LineBorder(borderGray));
            btn.addActionListener(e -> addItemToTable(item));
            pnlResults.add(btn);
        }
        pnlResults.revalidate();
        pnlResults.repaint();
    }

    private void handleBarcodeScan() {
        String barcode = txtBarcode.getText().trim();
        if (barcode.isEmpty() || barcode.equals("Enter a valid barcode")) {
            showPaymentDialog();
            return;
        }

        POSService.Item item = POSService.findItemByBarcode(barcode);
        if (item != null) {
            addItemToTable(item);
            txtBarcode.setText("");
            // Reset qty to 1 after scan as per typical POS behavior
            txtQty.setText("1");
            txtBarcode.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Barcode: " + barcode, "Error", JOptionPane.ERROR_MESSAGE);
            txtBarcode.selectAll();
            txtBarcode.requestFocus();
        }
    }

    private void handleManualSync() {
        if (!com.pos.service.NetworkService.isInternetAvailable()) {
            JOptionPane.showMessageDialog(this, "Internet connection not available!", "Sync Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        btnSync.setEnabled(false);
        btnSync.setText("Syncing...");

        new Thread(() -> {
            try {
                com.pos.service.SyncService.pushCustomers(); // Call to push customers
                int syncedCount = com.pos.service.SyncService.pushSales(); // Existing call, renamed variable
                SwingUtilities.invokeLater(() -> {
                    btnSync.setEnabled(true);
                    btnSync.setText("\uD83D\uDCE4 Sync Online"); // New text with emoji
                    updateSyncStatusCount();
                    if (syncedCount >= 0) { // New condition for success/failure
                        JOptionPane.showMessageDialog(this, "Synchronization complete!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Synchronization failed. Please check internet connection.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    btnSync.setEnabled(true);
                    btnSync.setText("\uD83D\uDCE4 Sync Online"); // New text with emoji
                    JOptionPane.showMessageDialog(this, "Sync failed: " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void updateSyncStatusCount() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::updateSyncStatusCount);
            return;
        }
        try {
            java.sql.ResultSet rs = model.MySQL.execute("SELECT COUNT(*) FROM `sales` WHERE `is_synced` = 0");
            if (rs.next()) {
                int count = rs.getInt(1);
                lblSyncStatus.setText("Pending Sync: " + count);
                lblSyncStatus.setForeground(count > 0 ? new Color(198, 40, 40) : new Color(46, 125, 50));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPaymentDialog() {
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(this, "Please select a customer first!", "Customer Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No items in bill!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Payment", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);

        double subTotal = Double.parseDouble(lblTotalAmount.getText().replace("Total Amount: Rs. ", ""));
        double initialDiscount = Double.parseDouble(txtDiscount.getText());
        final double[] grandTotal = { subTotal - initialDiscount };

        JPanel pnlMain = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlMain.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnlMain.setBackground(Color.WHITE);

        // --- Left Column: Payment Details ---
        JPanel pnlLeftCol = new JPanel(new GridBagLayout());
        pnlLeftCol.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        JTextField txtReceived = new JTextField();
        txtReceived.setFont(fontTitle);
        JTextField txtPaying = new JTextField(String.format("%.2f", grandTotal[0]));
        txtPaying.setEditable(false);
        JTextField txtPoints = new JTextField("0");
        JTextField txtAddPoints = new JTextField("0");
        JTextField txtCommission = new JTextField("0.00");
        JTextField txtChange = new JTextField("0.00");
        txtChange.setEditable(false);
        txtChange.setForeground(dangerRed);
        txtChange.setFont(fontTitle);

        JComboBox<String> comboType = new JComboBox<>(
                new String[] { "CASH", "CARD", "CHEQUE", "CREDIT", "BANKTRANSFER" });

        int row = 0;
        addLabelValue(pnlLeftCol, "Received Amount", txtReceived, gbc, row++);
        addLabelValue(pnlLeftCol, "Paying Amount", txtPaying, gbc, row++);
        addLabelValue(pnlLeftCol, "Payment in points", txtPoints, gbc, row++);
        addLabelValue(pnlLeftCol, "Change Return", txtChange, gbc, row++);
        addLabelValue(pnlLeftCol, "Add Points", txtAddPoints, gbc, row++);
        addLabelValue(pnlLeftCol, "Commission", txtCommission, gbc, row++);
        addLabelValue(pnlLeftCol, "Payment Type", comboType, gbc, row++);

        JLabel lblDue = new JLabel("Due Amount: 0.00");
        lblDue.setForeground(dangerRed);
        lblDue.setFont(fontBold14);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        pnlLeftCol.add(lblDue, gbc);

        // --- Right Column: Summary & Discounts ---
        JPanel pnlRightCol = new JPanel(new GridBagLayout());
        pnlRightCol.setBackground(new Color(250, 250, 250));
        pnlRightCol.setBorder(new LineBorder(borderGray));
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.insets = new Insets(8, 15, 8, 15);
        gbcR.weightx = 1.0;

        JLabel lblTotalP = new JLabel("TOTAL PRODUCTS: " + tableModel.getRowCount());
        JLabel lblTotalA = new JLabel("TOTAL AMOUNT: Rs. " + String.format("%.2f", subTotal));
        JLabel lblDiscTill = new JLabel("DISCOUNT (TILL NOW): Rs. " + String.format("%.2f", initialDiscount));

        JTextField txtOrderDiscPerc = new JTextField("0");
        JTextField txtOrderDiscRs = new JTextField("0");
        JLabel lblGrand = new JLabel("GRAND TOTAL: Rs. " + String.format("%.2f", grandTotal[0]));
        lblGrand.setFont(fontTitle);
        lblGrand.setForeground(headerBlue);

        JTextArea txtNotes = new JTextArea(3, 20);
        txtNotes.setBorder(new LineBorder(borderGray));

        int r = 0;
        gbcR.gridy = r++;
        pnlRightCol.add(lblTotalP, gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(lblTotalA, gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(lblDiscTill, gbcR);

        gbcR.gridy = r++;
        pnlRightCol.add(new JLabel("ORDER DISCOUNT (%)"), gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(txtOrderDiscPerc, gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(new JLabel("ORDER DISCOUNT (Rs.)"), gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(txtOrderDiscRs, gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(lblGrand, gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(new JLabel("NOTES"), gbcR);
        gbcR.gridy = r++;
        pnlRightCol.add(new JScrollPane(txtNotes), gbcR);

        // Quick buttons for %
        JPanel pnlQuick = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlQuick.setOpaque(false);
        String[] percents = { "5%", "10%", "15%", "20%", "25%", "Clear" };
        for (String p : percents) {
            JButton b = new JButton(p);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            b.setMargin(new Insets(2, 5, 2, 5));
            b.addActionListener(e -> {
                if (p.equals("Clear"))
                    txtOrderDiscPerc.setText("0");
                else
                    txtOrderDiscPerc.setText(p.replace("%", ""));
                updateGrandTotal(txtOrderDiscPerc, txtOrderDiscRs, subTotal, initialDiscount, lblGrand, txtPaying,
                        grandTotal);
            });
            pnlQuick.add(b);
        }
        gbcR.gridy = r++;
        pnlRightCol.add(pnlQuick, gbcR);

        // --- Logic for updates ---
        txtOrderDiscPerc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateGrandTotal(txtOrderDiscPerc, txtOrderDiscRs, subTotal, initialDiscount, lblGrand, txtPaying,
                        grandTotal);
            }
        });
        txtOrderDiscRs.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtOrderDiscPerc.setText("0");
                updateGrandTotal(txtOrderDiscPerc, txtOrderDiscRs, subTotal, initialDiscount, lblGrand, txtPaying,
                        grandTotal);
            }
        });
        txtReceived.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    double rec = Double.parseDouble(txtReceived.getText());
                    double pay = grandTotal[0];
                    if (rec >= pay) {
                        txtChange.setText(String.format("%.2f", rec - pay));
                        lblDue.setText("Due Amount: 0.00");
                    } else {
                        txtChange.setText("0.00");
                        lblDue.setText(String.format("Due Amount: %.2f", pay - rec));
                    }
                } catch (Exception ex) {
                    txtChange.setText("0.00");
                    lblDue.setText(String.format("Due Amount: %.2f", grandTotal[0]));
                }
            }
        });

        pnlMain.add(pnlLeftCol);
        pnlMain.add(pnlRightCol);
        dialog.add(pnlMain, BorderLayout.CENTER);

        // --- Footer Buttons ---
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlSouth.setBackground(Color.WHITE);
        JButton btnPay = createActionBtn("Pay", headerBlue, 150);
        JButton btnCancel = createActionBtn("Cancel", dangerRed, 150);

        // Make Enter key anywhere trigger the Payment form's main button
        dialog.getRootPane().setDefaultButton(btnPay);

        // Let it also respond when specifically focused
        btnPay.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnPay.doClick();
                }
            }
        });

        btnPay.addActionListener(e -> {
            try {
                double totalPaid = Double.parseDouble(txtPaying.getText());
                double received = txtReceived.getText().isEmpty() ? 0 : Double.parseDouble(txtReceived.getText());
                double change = Double.parseDouble(txtChange.getText());
                double discountRs = Double.parseDouble(txtOrderDiscRs.getText()) + initialDiscount;
                String type = comboType.getSelectedItem().toString();
                String notes = txtNotes.getText();

                double comm = 0;
                try {
                    comm = Double.parseDouble(txtCommission.getText().trim());
                } catch (Exception ex) {
                }
                processPaymentExtended(totalPaid, received, change, discountRs, type, notes, comm);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid amounts!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());
        pnlSouth.add(btnPay);
        pnlSouth.add(btnCancel);
        dialog.add(pnlSouth, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void addLabelValue(JPanel pnl, String label, JComponent comp, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        pnl.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        pnl.add(comp, gbc);
    }

    private void updateGrandTotal(JTextField perc, JTextField rs, double sub, double initDisc, JLabel lblG,
            JTextField txtP, double[] grand) {
        try {
            double p = Double.parseDouble(perc.getText());
            double r = Double.parseDouble(rs.getText());
            double totalBase = sub - initDisc;
            double discAmount = r;
            if (p > 0) {
                discAmount = totalBase * (p / 100.0);
                rs.setText(String.format("%.2f", discAmount));
            }
            grand[0] = totalBase - discAmount;
            lblG.setText("GRAND TOTAL: Rs. " + String.format("%.2f", grand[0]));
            txtP.setText(String.format("%.2f", grand[0]));
        } catch (Exception ex) {
        }
    }

    private void processPaymentExtended(double grandTotal, double received, double change, double totalDiscount,
            String type, String notes, double commission) {
        if (tableModel.getRowCount() == 0)
            return;

        try {
            int customerId = (selectedCustomer != null) ? selectedCustomer.id : 0;
            String salesCode = "SALE-" + System.currentTimeMillis();

            // Ensure schema consistency
            try {
                model.MySQL.execute("ALTER TABLE `sales` ADD COLUMN `notes` TEXT");
            } catch (Exception ex) {
            }
            try {
                model.MySQL.execute("ALTER TABLE `sales` ADD COLUMN `commission` DECIMAL(14,2) DEFAULT 0.00");
            } catch (Exception ex) {
            }
            try {
                model.MySQL.execute("ALTER TABLE `sales` ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
                model.MySQL
                        .execute("ALTER TABLE `payments` ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
            } catch (Exception ex) {
            }

            // Insert into sales table
            String sql = "INSERT INTO `sales` (`sales_code`, `customers_id`, `users_id`, `pos_system_id`, `warranty_period`, `warranty_card_no`, `notes`, `commission`, `is_synced`, `created_at`) VALUES ("
                    + "'" + salesCode + "', " + (customerId == 0 ? "NULL" : customerId) + ", 1, '"
                    + ConfigService.getPosSystemId() + "', '', '', '" + notes.replace("'", "''") + "', " + commission
                    + ", 0, CURRENT_TIMESTAMP)";
            model.MySQL.execute(sql);

            java.sql.ResultSet rs = model.MySQL.execute("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                int saleId = rs.getInt(1);
                double subTotal = Double.parseDouble(lblTotalAmount.getText().replace("Total Amount: Rs. ", ""));
                double due = Math.max(0, grandTotal - received);

                String paymentSql = "INSERT INTO `payments` (`sales_id`, `users_id`, `sub_total`, `grand_total`, `paid_amount`, `due_amount`, `discount`, `received_amount`, `change_return_amount`, `payment_type`, `payment_status`, `created_at`) VALUES ("
                        + saleId + ", 1, " + subTotal + ", " + grandTotal + ", " + (received - change) + ", " + due
                        + ", " + totalDiscount + ", " + received + ", " + change + ", '" + type + "', '"
                        + (due > 0 ? "DUE" : "PAID") + "', CURRENT_TIMESTAMP)";
                model.MySQL.execute(paymentSql);

                // Update Customer due_amount if applicable
                if (customerId != 0 && due > 0) {
                    model.MySQL.execute("UPDATE `customers` SET `due_amount` = `due_amount` + " + due
                            + ", `is_synced` = 0 WHERE `id` = " + customerId);
                }

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String itemName = tableModel.getValueAt(i, 0).toString();
                    int qty = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                    double price = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
                    java.sql.ResultSet rsItem = model.MySQL.execute(
                            "SELECT id FROM items WHERE item_name = '" + itemName.replace("'", "''") + "' LIMIT 1");
                    if (rsItem.next()) {
                        int itemId = rsItem.getInt("id");
                        model.MySQL.execute(
                                "INSERT INTO `sales_items` (`sales_id`, `items_id`, `quantity`, `price`, `discount`, `discount_type`) VALUES ("
                                        + saleId + ", " + itemId + ", " + qty + ", " + price + ", 0, 'FIXED')");

                        // Immediately deduct stock locally
                        model.MySQL.execute("UPDATE `items` SET `quantity` = IF(`quantity` >= " + qty
                                + ", `quantity` - " + qty + ", 0) WHERE `id` = " + itemId);
                    }
                }

                // Prepare and trigger printing
                Map<String, Object> printData = new HashMap<>();
                printData.put("shop_name", "Hypermart");
                printData.put("address", "Kandy");
                printData.put("phone", "0712345678 / 0771234567");
                printData.put("sales_code", salesCode);
                printData.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
                printData.put("time", new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
                printData.put("customer", selectedCustomer != null ? selectedCustomer.name : "CUSTOMER");
                printData.put("ptype", type);
                printData.put("user", "SUPERADMIN");
                printData.put("stype", isRetailMode ? "RETAIL" : "WHOLESALE");

                List<Map<String, Object>> printItems = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("ln", i + 1);
                    item.put("item", tableModel.getValueAt(i, 0).toString());
                    item.put("qty", tableModel.getValueAt(i, 1).toString());
                    item.put("unit", "Pcs");
                    item.put("market_price", "0.00");
                    item.put("our_price", tableModel.getValueAt(i, 2).toString());
                    item.put("amount", tableModel.getValueAt(i, 3).toString());
                    printItems.add(item);
                }
                printData.put("items", printItems);

                printData.put("total", String.format("%.2f", subTotal));
                printData.put("net_total", String.format("%.2f", grandTotal));
                printData.put("received", String.format("%.2f", received));
                printData.put("paid", String.format("%.2f", received - change));
                printData.put("change", String.format("%.2f", change));
                printData.put("due", String.format("%.2f", due));
                printData.put("count", String.valueOf(tableModel.getRowCount()));

                ReceiptPrinter.printReceipt(printData);
            }
            JOptionPane.showMessageDialog(this, "Sale completed successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            tableModel.setRowCount(0);
            updateSummary();
            updateSyncStatusCount();
            selectedCustomer = null;
            updateHoldCount();
            txtCustomerSearch.setText("Search Customer...");
            txtCustomerSearch.setForeground(Color.GRAY);

            // Trigger instant sale sync in background
            new Thread(() -> {
                try {
                    com.pos.service.SyncService.pushSales();
                    SwingUtilities.invokeLater(() -> updateSyncStatusCount());
                } catch (Exception exSync) {
                    System.err.println("Sale background sync error: " + exSync.getMessage());
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupResponsiveness() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    private void startTimer() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            if (lblTime != null)
                lblTime.setText(sdf.format(new java.util.Date()));
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

    private void setPOSMode(boolean retail) {
        this.isRetailMode = retail;
        btnRetail.setBackground(retail ? buttonBlue : Color.WHITE);
        btnRetail.setForeground(retail ? Color.WHITE : Color.BLACK);
        btnWholesale.setBackground(!retail ? buttonBlue : Color.WHITE);
        btnWholesale.setForeground(!retail ? Color.WHITE : Color.BLACK);

        // Refresh item grid to show correct prices
        String query = txtRightSearch.getText().trim();
        if (query.equals("Search for items...")) {
            query = ""; // Show all items if search box is still empty
        }
        performItemSearch(query);
    }

    private JPanel createInputWrapper(JTextField txt) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(fieldBg);
        p.setBorder(new LineBorder(borderGray));
        p.setPreferredSize(new Dimension(0, 45));

        txt.setBorder(new EmptyBorder(0, 10, 0, 10));
        txt.setOpaque(false);
        txt.setForeground(Color.GRAY);
        txt.setFont(fontPlain14);
        p.add(txt, BorderLayout.CENTER);
        return p;
    }

    private JButton createQtyBtn(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(50, 45));
        btn.setFont(fontBold14);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFocusable(false); // keep focus in text fields
        btn.setBorder(null);

        btn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleBarcodeScan();
                }
            }
        });

        return btn;
    }

    private JLabel createSummaryLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold14);
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private void setupPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void updateHoldCount() {
        try {
            java.sql.ResultSet rs = model.MySQL.execute("SELECT COUNT(*) FROM payments WHERE payment_status = 'HOLD'");
            if (rs.next()) {
                int count = rs.getInt(1);
                btnViewHold.setText("View Hold List (" + count + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleHoldSale() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No items to hold!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String refName = JOptionPane.showInputDialog(this, "Enter a name for this bill:", "Hold Bill",
                JOptionPane.QUESTION_MESSAGE);
        if (refName == null || refName.trim().isEmpty()) {
            return;
        }

        try {
            // Ensure hold_ref_name column exists
            try {
                model.MySQL
                        .execute("ALTER TABLE `sales` ADD COLUMN `hold_ref_name` VARCHAR(255) AFTER `pos_system_id`");
            } catch (Exception ex) {
            }
            try {
                model.MySQL.execute("ALTER TABLE `sales` ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
                model.MySQL
                        .execute("ALTER TABLE `payments` ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
            } catch (Exception ex) {
            }

            double total = Double.parseDouble(lblTotalAmount.getText().replace("Total Amount: Rs. ", ""));
            int customerId = (selectedCustomer != null) ? selectedCustomer.id : 0;
            String salesCode = "HOLD-" + System.currentTimeMillis();

            String sql = "INSERT INTO `sales` (`sales_code`, `customers_id`, `users_id`, `pos_system_id`, `hold_ref_name`, `warranty_period`, `warranty_card_no`, `is_synced`, `created_at`) VALUES ("
                    + "'" + salesCode + "', " + (customerId == 0 ? "NULL" : customerId) + ", 1, '"
                    + ConfigService.getPosSystemId() + "', '" + refName.replace("'", "''")
                    + "', '', '', 0, CURRENT_TIMESTAMP)";
            model.MySQL.execute(sql);

            java.sql.ResultSet rs = model.MySQL.execute("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                int saleId = rs.getInt(1);
                String paymentSql = "INSERT INTO `payments` (`sales_id`, `users_id`, `sub_total`, `grand_total`, `paid_amount`, `due_amount`, `discount`, `received_amount`, `change_return_amount`, `payment_status`, `created_at`) VALUES ("
                        + saleId + ", 1, " + total + ", " + total + ", 0, " + total
                        + ", 0, 0, 0, 'HOLD', CURRENT_TIMESTAMP)";
                model.MySQL.execute(paymentSql);

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String itemName = tableModel.getValueAt(i, 0).toString();
                    int qty = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                    double price = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
                    java.sql.ResultSet rsItem = model.MySQL.execute(
                            "SELECT id FROM items WHERE item_name = '" + itemName.replace("'", "''") + "' LIMIT 1");
                    if (rsItem.next()) {
                        int itemId = rsItem.getInt("id");
                        model.MySQL.execute(
                                "INSERT INTO `sales_items` (`sales_id`, `items_id`, `quantity`, `price`, `discount`, `discount_type`) VALUES ("
                                        + saleId + ", " + itemId + ", " + qty + ", " + price + ", 0, 'FIXED')");
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Sale put on HOLD!", "Success", JOptionPane.INFORMATION_MESSAGE);
            tableModel.setRowCount(0);
            updateSummary();
            updateHoldCount();
            updateSyncStatusCount();
            selectedCustomer = null;
            txtCustomerSearch.setText("Search Customer...");
            txtCustomerSearch.setForeground(Color.GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showHoldListDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Hold List", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(this);

        String[] headers = { "ID", "Date", "Bill Name", "Amount", "Actions" };
        DefaultTableModel holdModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        JTable holdTable = new JTable(holdModel);
        holdTable.setRowHeight(40);

        // Set column widths
        holdTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        holdTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        holdTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        holdTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        holdTable.getColumnModel().getColumn(4).setPreferredWidth(200);

        try {
            // Ensure schema consistency
            try {
                model.MySQL
                        .execute("ALTER TABLE `sales` ADD COLUMN `hold_ref_name` VARCHAR(255) AFTER `pos_system_id`");
            } catch (Exception ex) {
            }
            try {
                model.MySQL.execute("ALTER TABLE `sales` ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
                model.MySQL
                        .execute("ALTER TABLE `payments` ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
                model.MySQL.execute(
                        "UPDATE `sales` SET `created_at` = CURRENT_TIMESTAMP WHERE `created_at` IS NULL OR `created_at` = '0000-00-00 00:00:00'");
                model.MySQL.execute(
                        "UPDATE `payments` SET `created_at` = CURRENT_TIMESTAMP WHERE `created_at` IS NULL OR `created_at` = '0000-00-00 00:00:00'");
            } catch (Exception ex) {
            }

            java.sql.ResultSet rs = model.MySQL.execute(
                    "SELECT s.id, COALESCE(p.created_at, s.created_at) as created_at, s.hold_ref_name, p.grand_total FROM sales s JOIN payments p ON s.id = p.sales_id WHERE p.payment_status = 'HOLD' ORDER BY created_at DESC");
            while (rs.next()) {
                String dateStr = rs.getString("created_at");
                if (dateStr == null || dateStr.isEmpty()) {
                    dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
                }
                holdModel.addRow(new Object[] {
                        rs.getInt("id"),
                        dateStr,
                        rs.getString("hold_ref_name"),
                        String.format("%.2f", rs.getDouble("grand_total")),
                        ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Custom renderer/editor for Add/Delete buttons
        holdTable.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
                p.setOpaque(true);
                if (isSelected) {
                    p.setBackground(table.getSelectionBackground());
                } else {
                    p.setBackground(table.getBackground());
                }
                JButton btnAdd = new JButton("Add Bill");
                JButton btnDel = new JButton("Delete");
                btnAdd.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                btnDel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                btnAdd.setPreferredSize(new Dimension(80, 25));
                btnDel.setPreferredSize(new Dimension(80, 25));
                btnAdd.setBackground(headerBlue);
                btnAdd.setForeground(Color.WHITE);
                btnDel.setBackground(dangerRed);
                btnDel.setForeground(Color.WHITE);
                p.add(btnAdd);
                p.add(btnDel);
                return p;
            }
        });

        holdTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = holdTable.rowAtPoint(e.getPoint());
                int col = holdTable.columnAtPoint(e.getPoint());
                if (col == 4) {
                    int saleId = (int) holdModel.getValueAt(row, 0);
                    // Check which button was clicked based on position
                    Rectangle rect = holdTable.getCellRect(row, col, true);
                    double clickX = e.getX() - rect.getX();
                    if (clickX < rect.getWidth() / 2) {
                        resumeSale(saleId);
                        dialog.dispose();
                    } else {
                        if (JOptionPane.showConfirmDialog(dialog, "Delete this hold record?", "Confirm",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            deleteHoldSale(saleId);
                            holdModel.removeRow(row);
                            updateHoldCount();
                        }
                    }
                }
            }
        });

        dialog.add(new JScrollPane(holdTable), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void deleteHoldSale(int saleId) {
        try {
            model.MySQL.execute("DELETE FROM payments WHERE sales_id = " + saleId);
            model.MySQL.execute("DELETE FROM sales_items WHERE sales_id = " + saleId);
            model.MySQL.execute("DELETE FROM sales WHERE id = " + saleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resumeSale(int saleId) {
        try {
            tableModel.setRowCount(0);
            java.sql.ResultSet rs = model.MySQL.execute(
                    "SELECT i.item_name, si.quantity, si.price FROM sales_items si JOIN items i ON si.items_id = i.id WHERE si.sales_id = "
                            + saleId);
            while (rs.next()) {
                double subtotal = rs.getDouble("price") * rs.getInt("quantity");
                tableModel.addRow(new Object[] { rs.getString("item_name"), rs.getString("quantity"),
                        String.format("%.2f", rs.getDouble("price")), "0", "0.00", String.format("%.2f", subtotal) });
            }
            // Delete the hold records so they don't stay in list
            model.MySQL.execute("DELETE FROM payments WHERE sales_id = " + saleId);
            model.MySQL.execute("DELETE FROM sales_items WHERE sales_id = " + saleId);
            model.MySQL.execute("DELETE FROM sales WHERE id = " + saleId);
            updateSummary();
            updateHoldCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPendingSyncDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Pending Sync Sales", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        String[] headers = { "ID", "Sales Code", "Customer ID", "Amount" };
        DefaultTableModel modelSync = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableSync = new JTable(modelSync);
        tableSync.setRowHeight(30);

        try {
            java.sql.ResultSet rs = model.MySQL.execute(
                    "SELECT s.id, s.sales_code, s.customers_id, p.grand_total FROM sales s JOIN payments p ON s.id = p.sales_id WHERE s.is_synced = 0");
            while (rs.next()) {
                modelSync.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("sales_code"),
                        rs.getInt("customers_id"),
                        String.format("%.2f", rs.getDouble("grand_total"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.add(new JScrollPane(tableSync), BorderLayout.CENTER);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dialog.dispose());
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSouth.add(btnClose);
        dialog.add(pnlSouth, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showAddCustomerDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Customer", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(this);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnlForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField txtName = new JTextField();
        JTextField txtMobile = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtCity = new JTextField();
        JTextArea txtAddress = new JTextArea(3, 20);
        txtAddress.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JTextField txtDue = new JTextField("0.00");

        int row = 0;
        addLabelValue(pnlForm, "Customer Name:", txtName, gbc, row++);
        addLabelValue(pnlForm, "Mobile Number:", txtMobile, gbc, row++);
        addLabelValue(pnlForm, "Email:", txtEmail, gbc, row++);
        addLabelValue(pnlForm, "City:", txtCity, gbc, row++);
        addLabelValue(pnlForm, "Address Line:", new JScrollPane(txtAddress), gbc, row++);
        addLabelValue(pnlForm, "Due Amount:", txtDue, gbc, row++);

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtns.setBackground(new Color(245, 245, 245));
        pnlBtns.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnSave = new JButton("Save");
        btnSave.setBackground(headerBlue);
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(100, 35));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(100, 35));

        btnSave.addActionListener(e -> {
            String name = txtName.getText().trim();
            String mobile = txtMobile.getText().trim();
            String emailStr = txtEmail.getText().trim();
            String cityStr = txtCity.getText().trim();
            String addressStr = txtAddress.getText().trim();
            String dueStr = txtDue.getText().trim();

            if (name.isEmpty() || mobile.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name and Mobile are required!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Final safety check for columns
                String[] columns = { "email", "city_name", "address_line_1", "due_amount", "is_synced", "customer_code",
                        "created_by_user_id", "pos_system_id" };
                for (String col : columns) {
                    try {
                        String columnType = "VARCHAR(255)";
                        if (col.equals("address_line_1"))
                            columnType = "TEXT";
                        else if (col.equals("is_synced"))
                            columnType = "TINYINT(1) DEFAULT 0";
                        else if (col.equals("created_by_user_id"))
                            columnType = "INT";
                        else if (col.equals("due_amount"))
                            columnType = "DOUBLE DEFAULT 0";
                        else if (col.equals("customer_code"))
                            columnType = "VARCHAR(255) UNIQUE";
                        else if (col.equals("pos_system_id"))
                            columnType = "VARCHAR(255)";

                        model.MySQL.execute("ALTER TABLE `customers` ADD COLUMN `" + col + "` " + columnType);
                    } catch (Exception ex) {
                        // Ignore if already exists
                    }
                }

                String customerCode = "SOU-" + System.currentTimeMillis();
                String posId = com.pos.service.ConfigService.getPosSystemId();
                int userId = com.pos.service.SyncService.getCurrentUserId();

                System.out.println("Saving customer with User ID: " + userId + " and POS ID: " + posId);

                String sql = "INSERT INTO `customers` (`customer_name`, `contact_number`, `email`, `city_name`, `address_line_1`, `due_amount`, `is_synced`, `customer_code`, `created_by_user_id`, `pos_system_id`) VALUES ("
                        + "'" + name.replace("'", "''") + "', "
                        + "'" + mobile.replace("'", "''") + "', "
                        + (emailStr.isEmpty() ? "NULL" : "'" + emailStr.replace("'", "''") + "'") + ", "
                        + (cityStr.isEmpty() ? "NULL" : "'" + cityStr.replace("'", "''") + "'") + ", "
                        + (addressStr.isEmpty() ? "NULL" : "'" + addressStr.replace("'", "''") + "'") + ", "
                        + (dueStr.isEmpty() ? "0.0" : dueStr) + ", 0, '" + customerCode + "', " + userId + ", '" + posId
                        + "')";
                model.MySQL.execute(sql);

                // Instant sync if connected
                new Thread(() -> {
                    try {
                        if (com.pos.service.NetworkService.isInternetAvailable()) {
                            com.pos.service.SyncService.pushCustomers();
                        }
                    } catch (Exception exSync) {
                        exSync.printStackTrace();
                    }
                }).start();

                JOptionPane.showMessageDialog(dialog, "Customer saved successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error saving customer: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        pnlBtns.add(btnSave);
        pnlBtns.add(btnCancel);

        dialog.add(pnlForm, BorderLayout.CENTER);
        dialog.add(pnlBtns, BorderLayout.SOUTH);
        dialog.setVisible(true);
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
