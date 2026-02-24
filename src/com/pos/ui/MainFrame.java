package com.pos.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContent;
    private JButton activeButton; 
    private JPanel pnlSidebar; 
    private final Color sidebarBg = new Color(245, 245, 250); // Light sidebar
    private final Color activeBlue = new Color(13, 71, 161); // Deep Blue active highlight
    private final Color hoverBg = new Color(235, 237, 245);
    private final Color footerGreen = new Color(0, 100, 30); // Professional dark green
    private final Font fontSidebar = new Font("Segoe UI", Font.BOLD, 12);

    public MainFrame() {
        setTitle("Retail Supermarket POS - Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- Sidebar (Scrollable) ---
        JPanel pnlSidebarContainer = new JPanel(new BorderLayout());
        pnlSidebarContainer.setBackground(sidebarBg);
        pnlSidebarContainer.setPreferredSize(new Dimension(115, 0));
        pnlSidebarContainer.setBorder(new LineBorder(new Color(230, 230, 235), 1));

        pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(sidebarBg);

        String[] menuItems = {
            "Dashboard", "Items", "Export", "Stock", "Sales", "Due", "POS", 
            "Users", "Customer", "Suppliers", "Expenses", "Reports", "Settings", "StockReport"
        };

        for (String item : menuItems) {
            JButton btn = createSidebarButton(item, item, sidebarBg, Color.GRAY);
            pnlSidebar.add(btn);
            
            if (item.equals("Dashboard")) {
                setActiveButton(btn);
            }
        }

        JScrollPane scrollSidebar = new JScrollPane(pnlSidebar);
        scrollSidebar.setBorder(null);
        scrollSidebar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSidebar.getVerticalScrollBar().setUnitIncrement(16);
        pnlSidebarContainer.add(scrollSidebar, BorderLayout.CENTER);

        // Sidebar Navigation Helper (Bottom Arrows)
        JPanel pnlNav = new JPanel(new GridLayout(1, 2));
        pnlNav.setPreferredSize(new Dimension(0, 40));
        JButton btnUp = new JButton("<"); btnUp.setBackground(sidebarBg);
        JButton btnDown = new JButton(">"); btnDown.setBackground(sidebarBg);
        pnlNav.add(btnUp); pnlNav.add(btnDown);
        pnlSidebarContainer.add(pnlNav, BorderLayout.SOUTH);

        add(pnlSidebarContainer, BorderLayout.WEST);

        // --- Main Content Area (CardLayout) ---
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(Color.WHITE);

        // Add Panels
        mainContent.add(new DashboardPanel(), "Dashboard");
        mainContent.add(new POSPanel(), "POS");
        mainContent.add(new ItemsPanel(this), "Items");
        mainContent.add(new ExportItemsPanel(), "Export");
        mainContent.add(new StockPanel(this), "Stock");
        mainContent.add(new SalesPanel(this), "Sales");
        mainContent.add(new DueAmountPanel(), "Due");
        mainContent.add(new UsersPanel(this), "Users");
        mainContent.add(new CustomerPanel(this), "Customer");
        mainContent.add(new SuppliersPanel(this), "Suppliers");
        mainContent.add(new ExpensesPanel(this), "Expenses");
        mainContent.add(new ReportsPanel(this), "Reports");
        mainContent.add(new SettingsPanel(this), "Settings");
        mainContent.add(new StockReportPanel(this), "StockReport"); 
        mainContent.add(new AddItemsPanel(this), "AddItems");
        mainContent.add(new AddCategoryPanel(this), "AddCategory");
        mainContent.add(new ItemsListPanel(this), "ItemsList");
        mainContent.add(new CategoryListPanel(this), "CategoryList");
        mainContent.add(new ImportItemsPanel(this), "ImportItems");
        mainContent.add(new BarcodePanel(this), "Barcode");
        mainContent.add(new AddUserPanel(this), "AddUser");
        mainContent.add(new AddRolePanel(this), "AddRole");
        mainContent.add(new AddPermissionPanel(this), "AddPermission");
        mainContent.add(new UsersListPanel(this), "UsersList");
        mainContent.add(new RolesListPanel(this), "RolesList");
        mainContent.add(new SalesListPanel(this), "SalesList");
        mainContent.add(new ReturnsListPanel(this), "ReturnsList");
        mainContent.add(new AddCustomerPanel(this), "AddCustomer");
        mainContent.add(new CustomerListPanel(this), "CustomerList");
        mainContent.add(new ImportCustomerPanel(this), "ImportCustomer");
        mainContent.add(new AddSupplierPanel(this), "AddSupplier");
        mainContent.add(new SuppliersListPanel(this), "SuppliersList");
        mainContent.add(new ImportSuppliersPanel(this), "ImportSuppliers");
        mainContent.add(new AddExpensePanel(this), "AddExpense");
        mainContent.add(new AddExpenseCategoryPanel(this), "ExpenseCategories");
        mainContent.add(new ExpensesListPanel(this), "ExpensesList");
        mainContent.add(new ItemStockCountPanel(this), "ItemStockCount");
        mainContent.add(new ExpenseCategoryListPanel(this), "ExpenseCategoryList");
        mainContent.add(new PermissionListPanel(this), "PermissionList");
        mainContent.add(new LoyaltyPointReportPanel(this), "LoyaltyPointReport");
        mainContent.add(new DailySummaryReportPanel(this), "DailySummaryReport");
        mainContent.add(new MonthlySummaryReportPanel(this), "MonthlySummaryReport");
        mainContent.add(new YearlySummaryReportPanel(this), "YearlySummaryReport");
        mainContent.add(new ItemReportPanel(this), "ItemReport");
        mainContent.add(new SalesReportPanel(this), "SalesReport");
        mainContent.add(new ExpensesReportPanel(this), "ExpensesReport");
        mainContent.add(new SiteSettingsPanel(this), "SiteSettings");
        mainContent.add(new ChangePasswordPanel(this), "ChangePassword");
        mainContent.add(new ChangeSitePanel(this), "ChangeSite");
        
        add(mainContent, BorderLayout.CENTER);

        // --- Footer ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBackground(footerGreen);
        pnlFooter.setPreferredSize(new Dimension(0, 35));
        JLabel lblFooterText = new JLabel("2026 \u00a9 All Rights Reserved | Designed and Developed by Silicon Radon Networks (Pvt) Ltd");
        lblFooterText.setForeground(Color.WHITE);
        lblFooterText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        pnlFooter.add(lblFooterText);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    public void showPanel(String name) {
        cardLayout.show(mainContent, name);
    }

    private JButton createSidebarButton(String text, String iconType, Color bg, Color fg) {
        JButton btn = new JButton(new SidebarIcon(iconType, 24, 24, fg));
        btn.setText("<html><center>" + text + "</center></html>");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMaximumSize(new Dimension(115, 85));
        btn.setPreferredSize(new Dimension(115, 85));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(230, 230, 235)));
        btn.setFont(fontSidebar);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn != activeButton) btn.setBackground(hoverBg);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn != activeButton) btn.setBackground(sidebarBg);
            }
        });

        btn.addActionListener(e -> {
            setActiveButton(btn);
            cardLayout.show(mainContent, text);
        });

        return btn;
    }

    private void setActiveButton(JButton btn) {
        if (activeButton != null) {
            activeButton.setBackground(sidebarBg);
            activeButton.setForeground(Color.GRAY);
            activeButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(230, 230, 235)));
            ((SidebarIcon)activeButton.getIcon()).setColor(Color.GRAY);
        }
        activeButton = btn;
        btn.setBackground(Color.WHITE);
        btn.setForeground(activeBlue);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 1, 0, activeBlue),
            BorderFactory.createEmptyBorder(0, -5, 0, 0)
        ));
        ((SidebarIcon)activeButton.getIcon()).setColor(activeBlue);
        activeButton.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // --- Sidebar Icon Renderer ---
    private static class SidebarIcon implements Icon {
        private String type;
        private int w, h;
        private Color color;

        public SidebarIcon(String type, int w, int h, Color color) {
            this.type = type;
            this.w = w; this.h = h;
            this.color = color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.0f));
            g2.translate(x, y);

            int cx = w / 2;
            int cy = h / 2;

            switch (type) {
                case "Dashboard":
                    g2.fillRect(cx-6, cy-6, 5, 5);
                    g2.fillRect(cx+1, cy-6, 5, 5);
                    g2.fillRect(cx-6, cy+1, 5, 5);
                    g2.fillRect(cx+1, cy+1, 5, 5);
                    break;
                case "Items":
                    g2.drawRect(cx-7, cy-7, 6, 6);
                    g2.drawRect(cx+1, cy-7, 6, 6);
                    g2.drawRect(cx-7, cy+1, 6, 6);
                    g2.drawRect(cx+1, cy+1, 6, 6);
                    break;
                case "Export":
                    g2.drawRect(cx-6, cy-8, 12, 16);
                    g2.drawLine(cx, cy, cx+8, cy);
                    g2.drawLine(cx+4, cy-4, cx+8, cy);
                    g2.drawLine(cx+4, cy+4, cx+8, cy);
                    break;
                case "Stock":
                case "StockReport":
                    g2.drawRect(cx-8, cy-4, 16, 12);
                    g2.drawLine(cx-8, cy-4, cx, cy-8);
                    g2.drawLine(cx+8, cy-4, cx, cy-8);
                    break;
                case "Sales":
                    g2.drawRect(cx-7, cy-8, 14, 16);
                    g2.drawLine(cx-4, cy-2, cx+4, cy-2);
                    g2.drawLine(cx-4, cy+2, cx+4, cy+2);
                    break;
                case "Due":
                    g2.drawOval(cx-7, cy-7, 14, 14);
                    g2.drawLine(cx, cy-4, cx, cy+4);
                    g2.drawLine(cx-4, cy, cx+4, cy);
                    break;
                case "POS":
                    g2.drawRect(cx-8, cy-6, 16, 12);
                    g2.drawRect(cx-10, cy-10, 20, 20);
                    break;
                case "Users":
                    g2.drawOval(cx-5, cy-8, 10, 10);
                    g2.drawArc(cx-8, cy, 16, 10, 0, 180);
                    break;
                case "Customer":
                    g2.drawOval(cx-5, cy-8, 10, 10);
                    g2.drawArc(cx-10, cy, 20, 12, 0, 180);
                    break;
                case "Suppliers":
                    g2.drawRect(cx-8, cy-4, 16, 10);
                    g2.drawOval(cx-6, cy+6, 4, 4);
                    g2.drawOval(cx+2, cy+6, 4, 4);
                    break;
                case "Expenses":
                    g2.drawRect(cx-8, cy-5, 16, 10);
                    g2.drawOval(cx-3, cy-3, 6, 6);
                    break;
                case "Reports":
                    g2.drawLine(cx-8, cy+8, cx+8, cy+8);
                    g2.drawRect(cx-6, cy-2, 3, 10);
                    g2.drawRect(cx-1, cy-6, 3, 14);
                    g2.drawRect(cx+4, cy+2, 3, 6);
                    break;
                case "Settings":
                    g2.drawOval(cx-7, cy-7, 14, 14);
                    g2.drawOval(cx-2, cy-2, 4, 4);
                    for(int i=0; i<8; i++) {
                        double a = i * Math.PI / 4;
                        g2.drawLine((int)(cx+5*Math.cos(a)), (int)(cy+5*Math.sin(a)), 
                                     (int)(cx+8*Math.cos(a)), (int)(cy+8*Math.sin(a)));
                    }
                    break;
                default:
                    g2.drawOval(cx-5, cy-5, 10, 10);
                    break;
            }
            g2.dispose();
        }

        @Override public int getIconWidth() { return w; }
        @Override public int getIconHeight() { return h; }
    }
}
