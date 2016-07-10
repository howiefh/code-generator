package io.github.howiefh.generator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class TableConfigsPanel extends JPanel {
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane;
    private JTable typeConfigTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteBbutton;
    // End of variables declaration  //GEN-END:variables

    public TableConfigsPanel() {
        initComponents();
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        scrollPane = new JScrollPane();
        typeConfigTable = new JTable();
        addButton = new JButton();
        editButton = new JButton();
        deleteBbutton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //======== scrollPane ========
        {
            scrollPane.setViewportView(typeConfigTable);
        }
        add(scrollPane, new GridBagConstraints(0, 0, 1, 5, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- addButton ----
        addButton.setText(bundle.getString("TableConfigsPanel.addButton.text"));
        add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- editButton ----
        editButton.setText(bundle.getString("TableConfigsPanel.editButton.text"));
        add(editButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- deleteBbutton ----
        deleteBbutton.setText(bundle.getString("TableConfigsPanel.deleteBbutton.text"));
        add(deleteBbutton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // End of component initialization  //GEN-END:initComponents
    }

}
