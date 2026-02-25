package com.pos.ui;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Utility to make JComboBox searchable with a modern look and feel.
 */
public class SearchableComboBox {

    /**
     * Installs searchable functionality on a JComboBox.
     * @param comboBox The JComboBox to enhance.
     */
    public static void install(JComboBox<?> comboBox) {
        SearchableComboBoxUtil.install(comboBox);
    }
}
