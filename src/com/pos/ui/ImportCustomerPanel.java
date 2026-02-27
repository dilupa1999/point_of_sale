package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ImportCustomerPanel extends JPanel {

    private final Color primaryBlue = new Color(13, 71, 161);
    private final Color actionBlue = new Color(25, 118, 210);
    private final Color tableHeaderBlue = new Color(21, 101, 192);
    private final Color cancelRed = new Color(211, 47, 47);

    private JTable table;
    private DefaultTableModel tableModel;

    public ImportCustomerPanel(MainFrame mainFrame) {
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
        JLabel lblWelcome = new JLabel("<html><div style='text-align: right;'><span style='font-size: 16px; font-weight: bold;'>Good Morning!</span><br>Welcome POS System</div></html>");
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
        pnlMain.setBorder(new EmptyBorder(15, 25, 15, 25));

        // Breadcrumbs
        JLabel lblBreadcrumb = new JLabel("Main Panel > Customers > Import Customer");
        lblBreadcrumb.setForeground(new Color(100, 100, 100));
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlMain.add(lblBreadcrumb, BorderLayout.NORTH);

        // Center Content
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Upload Area
        JPanel pnlUploadContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pnlUploadContainer.setOpaque(false);

        JPanel pnlDropZone = new JPanel(new BorderLayout());
        pnlDropZone.setPreferredSize(new Dimension(80, 80));
        pnlDropZone.setBackground(Color.WHITE);
        pnlDropZone.setBorder(BorderFactory.createDashedBorder(new Color(200, 200, 205), 3, 3));
        
        JLabel lblUploadIcon = new JLabel("\u21EA"); // Upload arrow icon
        lblUploadIcon.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblUploadIcon.setForeground(Color.GRAY);
        lblUploadIcon.setHorizontalAlignment(SwingConstants.CENTER);
        pnlDropZone.add(lblUploadIcon, BorderLayout.CENTER);
        
        JPanel pnlUploadDetails = new JPanel(new GridBagLayout());
        pnlUploadDetails.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lblFileFormat = new JLabel("File must be in CSV format");
        lblFileFormat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlUploadDetails.add(lblFileFormat, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        JPanel pnlUploadBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlUploadBtns.setOpaque(false);
        pnlUploadBtns.add(createActionButton("Import", actionBlue, 100));
        pnlUploadBtns.add(createActionButton("Cancel", cancelRed, 100));
        pnlUploadDetails.add(pnlUploadBtns, gbc);

        pnlUploadContainer.add(pnlDropZone);
        pnlUploadContainer.add(pnlUploadDetails);
        pnlCenter.add(pnlUploadContainer, BorderLayout.NORTH);

        // Table List and Add Data Buttons
        JPanel pnlTableActions = new JPanel(new BorderLayout());
        pnlTableActions.setBackground(Color.WHITE);
        pnlTableActions.setBorder(new EmptyBorder(30, 0, 10, 0));

        JButton btnList = createActionButton("List", Color.WHITE, 80);
        btnList.setForeground(Color.DARK_GRAY);
        btnList.setBorder(new LineBorder(new Color(230, 230, 235)));
        pnlTableActions.add(btnList, BorderLayout.WEST);

        JButton btnAddDatabase = createActionButton("Add Data On Database", tableHeaderBlue, 220);
        pnlTableActions.add(btnAddDatabase, BorderLayout.EAST);

        // Table
        JPanel pnlTableSection = new JPanel(new BorderLayout());
        pnlTableSection.setBackground(Color.WHITE);

        String[] columns = {"#", "CUSTOMER CODE", "CUSTOMER NAME", "MOBILE NUMBER", "ADDRESS LINE 1", "CITY NAME", "EMAIL", "DUE AMOUNT", "USER ID", "CITY ID", "STATUS ID"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(tableHeaderBlue);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return lbl;
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 235)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel pnlTableContainer = new JPanel(new BorderLayout());
        pnlTableContainer.setBackground(Color.WHITE);
        pnlTableContainer.add(pnlTableActions, BorderLayout.NORTH);
        pnlTableContainer.add(scrollPane, BorderLayout.CENTER);

        pnlCenter.add(pnlTableContainer, BorderLayout.CENTER);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setPreferredSize(new Dimension(width, 40));
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        return btn;
    }
}
