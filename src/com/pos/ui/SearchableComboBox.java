package com.pos.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SearchableComboBox {

    public static void install(JComboBox<String> comboBox) {
        new SearchableComboInstance(comboBox);
    }

    private static class SearchableComboInstance {
        private final JComboBox<String> comboBox;
        private final List<String> originalItems;
        private boolean isFiltering = false;

        public SearchableComboInstance(JComboBox<String> comboBox) {
            this.comboBox = comboBox;
            this.originalItems = new ArrayList<>();
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                originalItems.add(comboBox.getItemAt(i));
            }

            comboBox.setEditable(true);
            JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();

            textField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { filter(); }
                @Override public void removeUpdate(DocumentEvent e) { filter(); }
                @Override public void changedUpdate(DocumentEvent e) { filter(); }

                private void filter() {
                    if (isFiltering) return;
                    String text = textField.getText();
                    
                    EventQueue.invokeLater(() -> {
                        isFiltering = true;
                        comboBox.removeAllItems();
                        for (String item : originalItems) {
                            if (item.toLowerCase().contains(text.toLowerCase())) {
                                comboBox.addItem(item);
                            }
                        }
                        textField.setText(text);
                        if (comboBox.getItemCount() > 0) {
                            comboBox.setPopupVisible(true);
                        }
                        isFiltering = false;
                    });
                }
            });

            // Handle selection
            comboBox.addActionListener(e -> {
                if (!isFiltering && comboBox.getSelectedItem() != null) {
                    textField.setText(comboBox.getSelectedItem().toString());
                }
            });
            
            // Custom styling to make it look "premium"
            textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            comboBox.setBackground(Color.WHITE);
        }
    }
}
