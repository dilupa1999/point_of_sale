package com.pos.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Main Frame for the Retail POS System containing the Modern Sidebar and Content Area
 */
public class ModernMainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContent;
    private Sidebar sidebar;
    private JLabel lblHeaderTitle;

    public ModernMainFrame() {
        // Basic Frame Setup
        setTitle("Retail POS System - Modern Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new Dimension(800, 600)); // Responsive minimum
        setLayout(new BorderLayout());

        initComponents();
        setupResponsiveListener();
    }

    private void initComponents() {
        // ... existing code ...
        sidebar = new Sidebar(this);
        add(sidebar, BorderLayout.WEST);

        // Right side wrapper (Header + Content)
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(new Color(245, 246, 250));

        // --- Top Header Bar ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setPreferredSize(new Dimension(0, 60)); // Slimmer header
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));
        
        lblHeaderTitle = new JLabel("  Dashboard");
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeaderTitle.setForeground(new Color(45, 50, 70));
        pnlHeader.add(lblHeaderTitle, BorderLayout.WEST);

        // Admin Profile/Search Section
        JPanel pnlRightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlRightHeader.setOpaque(false);
        
        JTextField txtSearch = new JTextField(15);
        txtSearch.setText(" Search...");
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setPreferredSize(new Dimension(200, 32));
        
        JLabel lblAdmin = new JLabel("System Admin");
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        pnlRightHeader.add(txtSearch);
        pnlRightHeader.add(lblAdmin);
        pnlHeader.add(pnlRightHeader, BorderLayout.EAST);

        pnlRight.add(pnlHeader, BorderLayout.NORTH);

        // --- Main Content Area (CardLayout) ---
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setOpaque(false);

        // Add Pages
        String[] pages = {
            "Dashboard", "Items", "Export", "Stock", "Sales", "Due", "POS", 
            "Users", "Customer", "Suppliers", "Expenses", "Reports", "Setting", "StockReport"
        };

        for (String page : pages) {
            mainContent.add(createPage(page), page);
        }

        pnlRight.add(mainContent, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.CENTER);
    }

    private void setupResponsiveListener() {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            private int lastWidth = -1;
            
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                if (width == lastWidth) return;
                lastWidth = width;

                // Threshold 1: Compact Sidebar
                boolean compact = (width < 1000);
                sidebar.setCompactMode(compact);

                // Threshold 2: Font Scaling
                float scaleFactor = 1.0f;
                if (width < 800) scaleFactor = 0.85f;
                else if (width < 1200) scaleFactor = 0.92f;
                
                updateComponentFonts(ModernMainFrame.this, scaleFactor);
                
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Recursively updates fonts of all components based on a scale factor
     */
    private void updateComponentFonts(Container container, float scale) {
        for (Component c : container.getComponents()) {
            Font f = c.getFont();
            if (f != null && !(c instanceof Sidebar)) { // Don't scale sidebar fonts manually as they are handled internally
                // We use a base font size and apply scale
                // For simplicity here, we just check if it's been scaled already or keep track of base
                // In a production app, we'd store the original font in a ClientProperty
                Object originalFont = ((JComponent)c).getClientProperty("originalFont");
                if (originalFont == null) {
                    ((JComponent)c).putClientProperty("originalFont", f);
                    originalFont = f;
                }
                
                Font base = (Font) originalFont;
                c.setFont(base.deriveFont(base.getSize2D() * scale));
            }
            if (c instanceof Container) {
                updateComponentFonts((Container) c, scale);
            }
        }
    }

    public void showPanel(String name) {
        cardLayout.show(mainContent, name);
        lblHeaderTitle.setText("  " + name);
    }

    private JPanel createPage(String title) {
        // We use GridBagLayout here as requested for proportional scaling
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 235), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        JLabel lbl = new JLabel("Welcome to " + title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        card.add(lbl, BorderLayout.NORTH);
        
        JLabel lblDesc = new JLabel("<html>This section is now responsive. Try resizing the window to see the sidebar collapse and fonts scale!</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(new Color(110, 110, 120));
        card.add(lblDesc, BorderLayout.CENTER);
        
        wrapper.add(card, gbc);
        return wrapper;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new ModernMainFrame().setVisible(true));
    }
}
