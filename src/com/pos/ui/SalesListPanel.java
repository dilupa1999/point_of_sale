package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.pos.util.ReceiptPrinter;
import model.MySQL;
import com.toedter.calendar.JDateChooser;

public class SalesListPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color filterBlue = new Color(13, 71, 161);
    private final Color clearGray = new Color(120, 144, 156);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color secondaryBlue = new Color(21, 101, 192);
    private final Color dueBadgeBg = new Color(232, 245, 233); // Keeping a slight green for due might be okay, but user
                                                               // said blue theme.
    // Changing DUE badge to blue/gray for theme consistency
    private final Color dueBadgeBgBlue = new Color(235, 241, 250);
    private final Color dueBadgeFgBlue = new Color(25, 118, 210);

    private final Color topHeaderBlue = new Color(13, 71, 161);

    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel pnlFilters;
    private JPanel pnlFiltersWrapper;

    // Filter Components
    private JComboBox<String> comboCategory;
    private JTextField txtSalesCode;
    private JTextField txtCustomer;
    private JTextField txtContact;
    private JDateChooser dateFrom;
    private JDateChooser dateTo;

    public SalesListPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents(mainFrame);
        setupResponsiveness();

        // Auto-load data when panel is shown
        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                loadData();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });
    }

    private void initComponents(MainFrame mainFrame) {
        // --- Top Header ---
        JPanel pnlTopHeader = new JPanel(new BorderLayout());
        pnlTopHeader.setBackground(topHeaderBlue);
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

        JButton btnRefresh = createHeaderButton("Refresh", false);
        btnRefresh.addActionListener(e -> loadData());

        pnlTopLeft.add(btnBack);
        pnlTopLeft.add(btnMainPanel);
        pnlTopLeft.add(btnPOS);
        pnlTopLeft.add(btnRefresh);
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
        JLabel lblWelcome = new JLabel(
                "<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Morning!</span><br>Welcome Hypermart</div></html>");
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

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Sales > Sales List");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlMain.add(lblBreadcrumb, BorderLayout.NORTH);

        // Filters Container
        pnlFiltersWrapper = new JPanel(new BorderLayout());
        pnlFiltersWrapper.setBackground(Color.WHITE);

        pnlFilters = new JPanel(new GridBagLayout());
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(new EmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Filter Rows
        gbc.gridy = 0;
        pnlFilters.add(createFilterLabel("Category"), setGbc(gbc, 0));
        pnlFilters.add(createFilterLabel("Sales Code"), setGbc(gbc, 1));
        pnlFilters.add(createFilterLabel("Customer"), setGbc(gbc, 2));
        pnlFilters.add(createFilterLabel("Customer Contact"), setGbc(gbc, 3));
        pnlFilters.add(createFilterLabel("From Date"), setGbc(gbc, 4));
        pnlFilters.add(createFilterLabel("To Date"), setGbc(gbc, 5));

        gbc.gridy = 1;
        comboCategory = new JComboBox<>();
        comboCategory.addItem("All Categories");
        loadCategories(); // Helper to populate this

        txtSalesCode = new JTextField();
        txtCustomer = new JTextField();
        txtContact = new JTextField();

        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("yyyy-MM-dd");

        dateTo = new JDateChooser();
        dateTo.setDateFormatString("yyyy-MM-dd");

        pnlFilters.add(comboCategory, setGbc(gbc, 0));
        pnlFilters.add(txtSalesCode, setGbc(gbc, 1));
        pnlFilters.add(txtCustomer, setGbc(gbc, 2));
        pnlFilters.add(txtContact, setGbc(gbc, 3));
        pnlFilters.add(dateFrom, setGbc(gbc, 4));
        pnlFilters.add(dateTo, setGbc(gbc, 5));

        // Filter Buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(15, 5, 5, 5);
        JButton btnFilter = createActionButton("Filter", filterBlue);
        btnFilter.addActionListener(e -> applyFilters());
        pnlFilters.add(btnFilter, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 3;
        JButton btnClear = createActionButton("Clear Filters", clearGray);
        btnClear.addActionListener(e -> clearFilters());
        pnlFilters.add(btnClear, gbc);

        pnlFiltersWrapper.add(pnlFilters, BorderLayout.CENTER);

        // Table Section
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);

        String[] columns = { "ID", "CODE", "CUSTOMER NAME", "CONTACT", "TOTAL (RS)", "RECIEVED AMOUNT (RS)",
                "PAID AMOUNT (RS)", "STATUS", "DUE AMOUNT (RS)", "DISCOUNT (RS)", "CREATED AT", "SYNC", "ACTION" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(80);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 255));
        table.setSelectionForeground(new Color(30, 30, 30));

        // Styling Table Header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                lbl.setBackground(tableHeaderBlue);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return lbl;
            }
        });

        // Custom Cell Rendering for plain text columns
        DefaultTableCellRenderer plainRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

                if (isSelected) {
                    setForeground(new Color(30, 30, 30));
                    setBackground(new Color(232, 240, 255));
                } else {
                    setForeground(new Color(50, 50, 50));
                    setBackground(Color.WHITE);
                }
                return this;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 7 && i != 11 && i != 12) {
                table.getColumnModel().getColumn(i).setCellRenderer(plainRenderer);
            }
        }

        setupTableRenderers();
        loadData();

        // Handle button clicks in the Action column
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();

                if (row < table.getRowCount() && row >= 0 && column == 12) { // 12 is Action column
                    Object value = table.getValueAt(row, 7); // 7 is Status
                    int cellWidth = table.getColumnModel().getColumn(column).getWidth();
                    int xInCell = e.getX() - table.getCellRect(row, column, false).x;

                    if ("DUE".equals(value)) {
                        if (xInCell < cellWidth / 2) { // "Pay" is on the left
                            handlePayment(row);
                        } else { // "MORE" is on the right
                            showSaleDetails(row);
                        }
                    } else {
                        // For PAID or other, "MORE" is the only button or covers more area
                        // In ActionRenderer, btnMore is always added.
                        showSaleDetails(row);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        pnlTableSection.add(scrollPane, BorderLayout.CENTER);

        // Final Assembly
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.add(pnlFiltersWrapper, BorderLayout.NORTH);
        pnlCenter.add(pnlTableSection, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void setupResponsiveness() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                pnlFilters.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;

                int cols = width > 1200 ? 6 : (width > 800 ? 3 : 2);

                String[] labels = { "Category", "Sales Code", "Customer", "Customer Contact", "From Date", "To Date" };

                JComponent[] fields = {
                        comboCategory,
                        txtSalesCode,
                        txtCustomer,
                        txtContact,
                        dateFrom,
                        dateTo
                };

                for (int i = 0; i < labels.length; i++) {
                    gbc.gridx = i % cols;
                    gbc.gridy = (i / cols) * 2;
                    pnlFilters.add(createFilterLabel(labels[i]), gbc);

                    gbc.gridy = (i / cols) * 2 + 1;
                    pnlFilters.add(fields[i], gbc);
                }

                // Buttons row
                gbc.gridy = (labels.length / cols + 1) * 2;
                gbc.gridx = 0;
                gbc.gridwidth = cols / 2;
                if (gbc.gridwidth == 0)
                    gbc.gridwidth = 1;
                JButton btnFilter = createActionButton("Filter", filterBlue);
                btnFilter.addActionListener(evt -> applyFilters());
                pnlFilters.add(btnFilter, gbc);

                gbc.gridx = gbc.gridwidth;
                gbc.gridwidth = cols - gbc.gridwidth;
                JButton btnClear = createActionButton("Clear Filters", clearGray);
                btnClear.addActionListener(evt -> clearFilters());
                pnlFilters.add(btnClear, gbc);

                pnlFilters.revalidate();
                pnlFilters.repaint();
            }
        });
    }

    private GridBagConstraints setGbc(GridBagConstraints gbc, int x) {
        gbc.gridx = x;
        return gbc;
    }

    private JLabel createFilterLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return lbl;
    }

    private void loadCategories() {
        try {
            java.sql.ResultSet rs = MySQL.execute("SELECT categories FROM item_categories ORDER BY categories ASC");
            while (rs.next()) {
                comboCategory.addItem(rs.getString("categories"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyFilters() {
        loadData();
    }

    private void clearFilters() {
        comboCategory.setSelectedIndex(0);
        txtSalesCode.setText("");
        txtCustomer.setText("");
        txtContact.setText("");
        dateFrom.setDate(null);
        dateTo.setDate(null);
        loadData();
    }

    private void setupTableRenderers() {
        table.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(11).setCellRenderer(new SyncStatusRenderer());
        table.getColumnModel().getColumn(12).setCellRenderer(new ActionRenderer());

        // Adjust column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(11).setPreferredWidth(80);
        table.getColumnModel().getColumn(12).setPreferredWidth(150);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT s.id, s.sales_code, c.customer_name, c.contact_number, " +
                            "p.grand_total, p.received_amount, p.paid_amount, p.payment_status, p.due_amount, p.discount, s.created_at, s.is_synced "
                            +
                            "FROM `sales` s " +
                            "LEFT JOIN `customers` c ON s.customers_id = c.id " +
                            "LEFT JOIN `payments` p ON s.id = p.sales_id " +
                            "WHERE 1=1 ");

            // Apply Filters
            if (comboCategory != null && comboCategory.getSelectedIndex() > 0) {
                String cat = comboCategory.getSelectedItem().toString();
                sql.append("AND EXISTS (SELECT 1 FROM `sales_items` si ")
                        .append("JOIN `items` i ON si.items_id = i.id ")
                        .append("JOIN `item_categories` ic ON i.item_categories_id = ic.id ")
                        .append("WHERE si.sales_id = s.id AND ic.categories = '").append(cat).append("') ");
            }

            if (txtSalesCode != null && !txtSalesCode.getText().trim().isEmpty()
                    && !txtSalesCode.getText().equals("Enter sales code")) {
                sql.append("AND s.sales_code LIKE '%").append(txtSalesCode.getText().trim().replace("'", "\\'"))
                        .append("%' ");
            }

            if (txtCustomer != null && !txtCustomer.getText().trim().isEmpty()
                    && !txtCustomer.getText().equals("Enter customer name")) {
                sql.append("AND c.customer_name LIKE '%").append(txtCustomer.getText().trim().replace("'", "\\'"))
                        .append("%' ");
            }

            if (txtContact != null && !txtContact.getText().trim().isEmpty()
                    && !txtContact.getText().equals("Enter contact number")) {
                sql.append("AND c.contact_number LIKE '%").append(txtContact.getText().trim().replace("'", "\\'"))
                        .append("%' ");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (dateFrom != null && dateFrom.getDate() != null) {
                sql.append("AND DATE(s.created_at) >= '").append(sdf.format(dateFrom.getDate())).append("' ");
            }

            if (dateTo != null && dateTo.getDate() != null) {
                sql.append("AND DATE(s.created_at) <= '").append(sdf.format(dateTo.getDate())).append("' ");
            }

            sql.append("ORDER BY s.id DESC");

            java.sql.ResultSet rs = model.MySQL.execute(sql.toString());
            while (rs.next()) {
                double dueAmount = rs.getDouble("due_amount");
                String displayStatus = (dueAmount > 0.01) ? "DUE" : "PAID";

                tableModel.addRow(new Object[] {
                        rs.getString("id"),
                        rs.getString("sales_code"),
                        rs.getString("customer_name") == null ? "Guest" : rs.getString("customer_name"),
                        rs.getString("contact_number") == null ? "-" : rs.getString("contact_number"),
                        String.format("%.2f", rs.getDouble("grand_total")),
                        String.format("%.2f", rs.getDouble("received_amount")),
                        String.format("%.2f", rs.getDouble("paid_amount")),
                        displayStatus,
                        String.format("%.2f", dueAmount),
                        String.format("%.2f", rs.getDouble("discount")),
                        rs.getString("created_at"),
                        rs.getInt("is_synced") == 1 ? "SYNCED" : "PENDING",
                        ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePayment(int row) {
        String saleId = (String) table.getValueAt(row, 0);
        String saleCode = (String) table.getValueAt(row, 1);
        String dueAmountStr = (String) table.getValueAt(row, 8);
        double dueAmount = Double.parseDouble(dueAmountStr);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Make a Payment", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(new EmptyBorder(20, 30, 20, 30));
        pnlForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 5, 0);

        JTextField txtSaleId = new JTextField(saleId);
        txtSaleId.setEditable(false);
        txtSaleId.setPreferredSize(new Dimension(0, 40));

        JTextField txtDue = new JTextField(String.format("%.2f", dueAmount));
        txtDue.setEditable(false);
        txtDue.setPreferredSize(new Dimension(0, 40));

        JComboBox<String> comboType = new JComboBox<>(new String[] { "CASH", "CARD" });
        comboType.setPreferredSize(new Dimension(0, 40));

        JTextField txtAmount = new JTextField();
        txtAmount.setPreferredSize(new Dimension(0, 40));
        txtAmount.setToolTipText("Enter amount paid");

        int r = 0;
        addPaymentLabel(pnlForm, "Sales ID", gbc, r++);
        pnlForm.add(txtSaleId, gbc);

        addPaymentLabel(pnlForm, "Current Due Amount", gbc, r++);
        pnlForm.add(txtDue, gbc);

        addPaymentLabel(pnlForm, "Payment Type", gbc, r++);
        pnlForm.add(comboType, gbc);

        addPaymentLabel(pnlForm, "Amount Paid", gbc, r++);
        pnlForm.add(txtAmount, gbc);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlButtons.setBackground(Color.WHITE);

        JButton btnSubmit = new JButton("Submit") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(66, 133, 244));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setPreferredSize(new Dimension(120, 45));
        btnSubmit.setContentAreaFilled(false);
        btnSubmit.setBorder(null);

        JButton btnCancel = new JButton("Cancel") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(234, 67, 53));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(120, 45));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setBorder(null);

        btnCancel.addActionListener(e -> dialog.dispose());

        // Make Enter key trigger the submit button
        dialog.getRootPane().setDefaultButton(btnSubmit);
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnSubmit.doClick();
                }
            }
        });

        btnSubmit.addActionListener(e -> {
            String input = txtAmount.getText();
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amountPaid = Double.parseDouble(input);
                if (amountPaid <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Amount must be greater than zero.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amountPaid > dueAmount) {
                    JOptionPane.showMessageDialog(dialog, "Amount exceeds current due.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update Local Database
                double newDue = dueAmount - amountPaid;
                String status = (newDue <= 0.01) ? "PAID" : "DUE";

                MySQL.execute("UPDATE `payments` SET `paid_amount` = `paid_amount` + " + amountPaid +
                        ", `due_amount` = " + String.format("%.2f", newDue) +
                        ", `payment_status` = '" + status + "' WHERE `sales_id` = " + saleId);

                // Log to history table
                MySQL.execute("INSERT INTO `sales_due_payments` (sale_id, amount_paid, payment_type, created_at) " +
                        "VALUES (" + saleId + ", " + amountPaid + ", '" + comboType.getSelectedItem().toString()
                        + "', NOW())");

                MySQL.execute("UPDATE `sales` SET `is_synced` = 0 WHERE `id` = " + saleId);

                // Update Customer table if it's not a guest
                String customerName = (String) table.getValueAt(row, 2);
                if (!"Guest".equalsIgnoreCase(customerName)) {
                    java.sql.ResultSet rs = MySQL.execute("SELECT customers_id FROM sales WHERE id = " + saleId);
                    if (rs.next()) {
                        int customerId = rs.getInt("customers_id");
                        MySQL.execute("UPDATE `customers` SET `due_amount` = `due_amount` - " + amountPaid +
                                ", `is_synced` = 0 WHERE `id` = " + customerId);
                    }
                }

                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData();

                // Printer Integration
                try {
                    Map<String, Object> printData = new HashMap<>();
                    printData.put("type", "PAYMENT");
                    printData.put("shop_name", "Hypermart");
                    printData.put("address", "Kandy");
                    printData.put("phone", "0712345678 / 0771234567");
                    printData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    printData.put("time", new SimpleDateFormat("hh:mm a").format(new Date()));
                    printData.put("customer", customerName != null ? customerName : "Guest");
                    printData.put("ptype", comboType.getSelectedItem().toString());
                    printData.put("user", "SUPERADMIN");

                    // Get Customer ID for receipt
                    int finalCustomerId = 0;
                    if (!"Guest".equalsIgnoreCase(customerName)) {
                        java.sql.ResultSet rsId = MySQL.execute("SELECT customers_id FROM sales WHERE id = " + saleId);
                        if (rsId.next()) {
                            finalCustomerId = rsId.getInt("customers_id");
                        }
                    }
                    printData.put("customerId", finalCustomerId != 0 ? String.valueOf(finalCustomerId) : "-");
                    printData.put("due_amount", String.format("%.2f", dueAmount));
                    printData.put("amount_paid", String.format("%.2f", amountPaid));
                    printData.put("remaining_balance", String.format("%.2f", newDue));

                    ReceiptPrinter.printReceipt(printData);
                } catch (Exception printEx) {
                    System.err.println("Printing error: " + printEx.getMessage());
                }

                // Trigger Sync in background
                new Thread(() -> {
                    try {
                        com.pos.service.SyncService.pushSales();
                        com.pos.service.SyncService.pushCustomers();
                        SwingUtilities.invokeLater(() -> loadData());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Payment error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        pnlButtons.add(btnSubmit);
        pnlButtons.add(btnCancel);

        dialog.add(pnlForm, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showSaleDetails(int row) {
        String saleId = (String) table.getValueAt(row, 0);
        String saleCode = (String) table.getValueAt(row, 1);
        String customerName = (String) table.getValueAt(row, 2);
        String contact = (String) table.getValueAt(row, 3);
        String totalPaid = (String) table.getValueAt(row, 6);
        String status = (String) table.getValueAt(row, 7);
        String remainingDue = (String) table.getValueAt(row, 8);
        String originalTotal = (String) table.getValueAt(row, 4);
        String createdAt = (String) table.getValueAt(row, 10);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Payment Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(900, 750);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(245, 247, 250));

        // Top Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Payment Details");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(55, 71, 79));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        JButton btnPrint = new JButton("Print Receipt") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(66, 133, 244));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setPreferredSize(new Dimension(140, 45));
        btnPrint.setContentAreaFilled(false);
        btnPrint.setBorder(null);
        btnPrint.addActionListener(e -> {
            // Trigger reprint logic
            try {
                java.sql.ResultSet rs = MySQL.execute("SELECT * FROM payments WHERE sales_id = " + saleId);
                if (rs.next()) {
                    Map<String, Object> printData = new HashMap<>();
                    printData.put("type", "PAYMENT");
                    printData.put("shop_name", "Hypermart");
                    printData.put("address", "Kandy");
                    printData.put("phone", "0712345678 / 0771234567");
                    printData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    printData.put("time", new SimpleDateFormat("hh:mm a").format(new Date()));
                    printData.put("customer", customerName);
                    printData.put("ptype", rs.getString("payment_type"));
                    printData.put("user", "SUPERADMIN");
                    printData.put("customerId", "-"); // Could fetch actual ID if needed
                    printData.put("due_amount", originalTotal);
                    printData.put("amount_paid", totalPaid);
                    printData.put("remaining_balance", remainingDue);

                    ReceiptPrinter.printReceipt(printData);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pnlHeader.add(btnPrint, BorderLayout.EAST);

        // Content Panel (Scrollable)
        JPanel pnlContent = new JPanel(new GridBagLayout());
        pnlContent.setBackground(new Color(245, 247, 250));
        pnlContent.setBorder(new EmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Sections: Sale Information & Payment Summary
        JPanel pnlTopInfo = new JPanel(new GridLayout(1, 2, 25, 0));
        pnlTopInfo.setOpaque(false);

        pnlTopInfo.add(createDetailCard("Sale Information", new String[][] {
                { "Sale Code", saleCode },
                { "Sale ID", saleId },
                { "Customer", customerName },
                { "Contact", contact },
                { "Date", createdAt }
        }));

        pnlTopInfo.add(createDetailCard("Payment Summary", new String[][] {
                { "Original Amount", originalTotal },
                { "Total Paid", totalPaid },
                { "Remaining Due", remainingDue },
                { "Status", status }
        }));

        gbc.gridy = 0;
        pnlContent.add(pnlTopInfo, gbc);

        // Purchased Items Section
        gbc.gridy = 1;
        pnlContent.add(createTableSection("Purchased Items", new String[] { "ITEM", "QUANTITY", "SOLD PRICE", "TOTAL" },
                getSaleItemsData(saleId)), gbc);

        // Due Payment History Section
        gbc.gridy = 2;
        pnlContent.add(createTableSection("Due Payment History", new String[] { "DATE", "AMOUNT", "TYPE" },
                getPaymentHistoryData(saleId)), gbc);

        JScrollPane scroll = new JScrollPane(pnlContent);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        dialog.add(pnlHeader, BorderLayout.NORTH);
        dialog.add(scroll, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JPanel createDetailCard(String title, String[][] details) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 235), 1),
                new EmptyBorder(15, 20, 15, 20)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(100, 100, 100));
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        card.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlGrid = new JPanel(new GridBagLayout());
        pnlGrid.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        for (int i = 0; i < details.length; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.insets = new Insets(0, 0, 5, 0);
            JLabel lblKey = new JLabel(details[i][0] + ": ");
            lblKey.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblKey.setForeground(new Color(80, 80, 80));
            pnlGrid.add(lblKey, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, 20, 5, 0); // Spacing between key and value
            JLabel lblVal = new JLabel(details[i][1]);
            lblVal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblVal.setForeground(new Color(50, 50, 50));

            if ("Status".equals(details[i][0])) {
                lblVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lblVal.setForeground(
                        "PAID".equalsIgnoreCase(details[i][1]) ? new Color(76, 175, 80) : new Color(255, 152, 0));
            }
            pnlGrid.add(lblVal, gbc);
        }

        card.add(pnlGrid, BorderLayout.CENTER);
        return card;
    }

    private JPanel createTableSection(String title, String[] headers, Object[][] data) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(80, 80, 80));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        section.add(lblTitle, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(data, headers);
        JTable sectionTable = new JTable(model);
        sectionTable.setRowHeight(40);
        sectionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sectionTable.setEnabled(false);
        sectionTable.setBackground(Color.WHITE);
        sectionTable.setShowGrid(false);

        JTableHeader header = sectionTable.getTableHeader();
        header.setBackground(new Color(250, 250, 252));
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setForeground(new Color(120, 120, 120));
        header.setPreferredSize(new Dimension(0, 35));

        JScrollPane scroll = new JScrollPane(sectionTable);
        scroll.setPreferredSize(new Dimension(0, 180));
        scroll.setBorder(new LineBorder(new Color(230, 230, 235), 1));
        section.add(scroll, BorderLayout.CENTER);

        return section;
    }

    private Object[][] getSaleItemsData(String saleId) {
        try {
            java.sql.ResultSet rs = MySQL.execute("SELECT items.item_name, sales_items.quantity, sales_items.price " +
                    "FROM sales_items JOIN items ON sales_items.items_id = items.id " +
                    "WHERE sales_items.sales_id = " + saleId);
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                double qty = rs.getDouble("quantity");
                double price = rs.getDouble("price");
                rows.add(new Object[] {
                        rs.getString("item_name"),
                        qty,
                        String.format("%.2f", price),
                        String.format("%.2f", qty * price)
                });
            }
            return rows.toArray(new Object[0][]);
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    private Object[][] getPaymentHistoryData(String saleId) {
        try {
            java.sql.ResultSet rs = MySQL
                    .execute("SELECT amount_paid, payment_type, created_at FROM sales_due_payments " +
                            "WHERE sale_id = " + saleId + " ORDER BY created_at DESC");
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[] {
                        rs.getString("created_at"),
                        String.format("%.2f", rs.getDouble("amount_paid")),
                        rs.getString("payment_type")
                });
            }
            return rows.toArray(new Object[0][]);
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    private void addPaymentLabel(JPanel panel, String text, GridBagConstraints gbc, int row) {
        gbc.gridy = row * 2;
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(100, 100, 100));
        panel.add(lbl, gbc);
        gbc.gridy = row * 2 + 1;
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(200, 45));
        return btn;
    }

    // --- Custom Renderers ---

    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            String status = (String) value;
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            JLabel lbl = new JLabel(status);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setBorder(new EmptyBorder(5, 15, 5, 15));

            if ("PAID".equals(status)) {
                lbl.setBackground(new Color(225, 245, 254));
                lbl.setForeground(new Color(1, 87, 155));
            } else {
                lbl.setBackground(dueBadgeBgBlue);
                lbl.setForeground(dueBadgeFgBlue);
            }

            p.add(lbl);

            // Ensure contrast on selection
            if (isSelected) {
                p.setBackground(table.getSelectionBackground());
            } else {
                p.setBackground(Color.WHITE);
            }
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }

    class SyncStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            String status = (String) value;
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            JLabel lbl = new JLabel(status);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lbl.setOpaque(true);
            lbl.setBorder(new EmptyBorder(4, 10, 4, 10));

            if ("SYNCED".equals(status)) {
                lbl.setBackground(new Color(232, 245, 233));
                lbl.setForeground(new Color(46, 125, 50));
            } else {
                lbl.setBackground(new Color(255, 243, 224));
                lbl.setForeground(new Color(230, 81, 0));
            }

            p.add(lbl);

            // Ensure contrast on selection
            if (isSelected) {
                p.setBackground(table.getSelectionBackground());
            } else {
                p.setBackground(Color.WHITE);
            }

            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
            return p;
        }
    }

    class ActionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
            p.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

            String status = (String) table.getValueAt(row, 7);

            if ("DUE".equals(status)) {
                JButton btnPay = new JButton("Pay");
                styleActionButton(btnPay, actionBlue);
                p.add(btnPay);
            }

            JButton btnMore = new JButton("MORE");
            styleActionButton(btnMore, secondaryBlue);
            p.add(btnMore);

            btnMore.addActionListener(e -> {
                // Future implementation for More details
            });

            p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

            // Ensure contrast on selection
            if (isSelected) {
                p.setBackground(table.getSelectionBackground());
            } else {
                p.setBackground(Color.WHITE);
            }
            return p;
        }

        private void styleActionButton(JButton btn, Color bg) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(65, 35));
            btn.setFocusPainted(false);
            btn.setBorder(null);
            btn.setOpaque(true);
        }
    }
}
