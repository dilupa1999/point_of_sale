package poin_of_sale;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;

/**
 * @author SANDUN
 */
public class Poin_of_sale {

    public static void main(String[] args) {
        try {
            // Nimbus  FlatLaf modern theme  setup 
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            
            // Scroll Bar modern look  settings 
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.thumbInsets", new java.awt.Insets(2, 2, 2, 2));
            UIManager.put("ScrollBar.width", 12);
            UIManager.put("ScrollBar.showButtons", false);
            
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Poin_of_sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* LoginFrame  */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //  UI package LoginFrame open 
                new com.pos.ui.LoginFrame().setVisible(true);
            }
        });
    }
}