package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddCustomerPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color cancelRed = new Color(211, 47, 47);
    private final Color fieldBg = new Color(245, 245, 250);
    private final Font fontBold14 = new Font("Segoe UI", Font.BOLD, 14);

    public AddCustomerPanel(MainFrame mainFrame) {
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
        btnBack.addActionListener(e -> mainFrame.showPanel("Customer"));

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
        JLabel lblWelcome = new JLabel(
                "<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Morning!</span><br>Welcome POS System</div></html>");
        lblWelcome.setForeground(Color.WHITE);

        JButton btnPower = createHeaderButton("\u23FB", true);
        btnPower.setFont(new Font("Segoe UI", Font.BOLD, 20));

        pnlTopRight.add(lblWelcome);
        pnlTopRight.add(btnPower);
        pnlTopHeader.add(pnlTopRight, BorderLayout.EAST);

        add(pnlTopHeader, BorderLayout.NORTH);

        // --- Main Content ---
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(new EmptyBorder(10, 40, 40, 40));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Customers > Add New Customer");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlContent.add(lblBreadcrumb, BorderLayout.NORTH);

        JPanel pnlFormContainer = new JPanel(new BorderLayout());
        pnlFormContainer.setBackground(Color.WHITE);
        pnlFormContainer.setBorder(new LineBorder(new Color(235, 235, 240), 1));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Row 1: Customer Name (Full Width)
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        pnlForm.add(createLabel("Customer Name"), gbc);
        JTextField txtName = createTextField("");
        gbc.gridy = 1;
        pnlForm.add(txtName, gbc);

        // Row 2: Mobile Number
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        pnlForm.add(createLabel("Mobile Number"), gbc);
        JTextField txtMobile = createTextField("");
        gbc.gridy = 3;
        pnlForm.add(txtMobile, gbc);

        // Row 3: Email
        gbc.gridy = 4;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Email"), gbc);
        JTextField txtEmail = createTextField("");
        gbc.gridy = 5;
        pnlForm.add(txtEmail, gbc);

        // Row 4: Address Line 1 and City Name
        gbc.gridy = 6;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Address Line 1"), gbc);
        gbc.gridx = 1;
        pnlForm.add(createLabel("City Name"), gbc);

        JTextField txtAddress = createTextField("");
        JTextField txtCity = createTextField("");
        gbc.gridy = 7;
        gbc.gridx = 0;
        pnlForm.add(txtAddress, gbc);
        gbc.gridx = 1;
        pnlForm.add(txtCity, gbc);

        // Row 5: Due Amount
        gbc.gridy = 8;
        gbc.gridx = 0;
        pnlForm.add(createLabel("Due Amount"), gbc);
        JTextField txtDueAmount = createTextField("0.00");
        gbc.gridy = 9;
        pnlForm.add(txtDueAmount, gbc);

        // Row 6: Buttons
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 0, 10, 0);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButtons.setOpaque(false);

        JButton btnAdd = createActionButton("Add", actionBlue);
        JButton btnReset = createActionButton("Reset", tableHeaderBlue);
        JButton btnCancel = createActionButton("Cancel", cancelRed);

        JTextField[] fields = { txtName, txtMobile, txtEmail, txtAddress, txtCity, txtDueAmount };

        btnAdd.addActionListener(e -> saveCustomer(fields, mainFrame));
        btnReset.addActionListener(e -> {
            for (JTextField field : fields) {
                field.setText("");
            }
        });
        btnCancel.addActionListener(e -> mainFrame.showPanel("Customer"));

        // Allow Enter key to trigger the buttons when focused
        java.awt.event.KeyListener enterKeyListener = new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    ((JButton) e.getSource()).doClick();
                }
            }
        };
        btnAdd.addKeyListener(enterKeyListener);
        btnReset.addKeyListener(enterKeyListener);
        btnCancel.addKeyListener(enterKeyListener);

        // Also allow Enter in any text field to trigger Add
        for (JTextField field : fields) {
            field.addActionListener(e -> btnAdd.doClick());
        }

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnReset);
        pnlButtons.add(btnCancel);
        pnlForm.add(pnlButtons, gbc);

        pnlFormContainer.add(pnlForm, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(pnlFormContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlContent.add(scrollPane, BorderLayout.CENTER);

        add(pnlContent, BorderLayout.CENTER);
    }

    private void saveCustomer(JTextField[] fields, MainFrame mainFrame) {
        String name = fields[0].getText().trim();
        String mobile = fields[1].getText().trim();
        String email = fields[2].getText().trim();
        String address = fields[3].getText().trim();
        String city = fields[4].getText().trim();
        String dueAmountStr = fields[5].getText().trim();

        if (name.isEmpty() || mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer Name and Mobile Number are required.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double dueAmount = 0.0;
        try {
            if (!dueAmountStr.isEmpty()) {
                dueAmount = Double.parseDouble(dueAmountStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Due Amount format.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String customerCode = "CUST-" + System.currentTimeMillis();
        String posSystemId = com.pos.service.ConfigService.getPosSystemId();

        try {
            String sql = "INSERT INTO `customers` (`customer_name`, `contact_number`, `email`, `address_line_1`, `city_name`, `due_amount`, `customer_code`, `pos_system_id`, `is_synced`, `created_at`) VALUES ("
                    + "'" + name.replace("'", "''") + "', "
                    + "'" + mobile.replace("'", "''") + "', "
                    + (email.isEmpty() ? "NULL" : "'" + email.replace("'", "''") + "'") + ", "
                    + (address.isEmpty() ? "NULL" : "'" + address.replace("'", "''") + "'") + ", "
                    + (city.isEmpty() ? "NULL" : "'" + city.replace("'", "''") + "'") + ", "
                    + dueAmount + ", "
                    + "'" + customerCode + "', "
                    + "'" + posSystemId + "', "
                    + "0, CURRENT_TIMESTAMP)";

            model.MySQL.execute(sql);

            JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            for (JTextField field : fields) {
                field.setText("");
            }
            fields[5].setText("0.00");

            // Trigger background sync
            new Thread(() -> {
                try {
                    com.pos.service.SyncService.pushCustomers();
                } catch (Exception ex) {
                    System.err.println("Failed to sync new customer background: " + ex.getMessage());
                }
            }).start();

            // Redirect back to list and refresh
            CustomerListPanel clp = mainFrame.getCustomerListPanel();
            if (clp != null) {
                clp.loadCustomers("");
            }
            mainFrame.showPanel("CustomerList");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold14);
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private JTextField createTextField(String text) {
        JTextField txt = new JTextField(text);
        txt.setPreferredSize(new Dimension(0, 45));
        txt.setBackground(fieldBg);
        txt.setBorder(new LineBorder(new Color(230, 230, 235)));
        txt.setForeground(Color.GRAY);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return txt;
    }

    private JButton createActionButton(String text, Color bg) {
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setPreferredSize(new Dimension(120, 45));
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
