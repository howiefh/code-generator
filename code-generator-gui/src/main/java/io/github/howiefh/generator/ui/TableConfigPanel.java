package io.github.howiefh.generator.ui;

import org.jdesktop.beansbinding.BindingGroup;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author fenghao, Mon Jul 11 21:20:47 CST 2016
 * @version 1.0
 * @since 1.0
 */
public class TableConfigPanel extends JPanel {
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel classNameLabel;
    private JTextField classNameTextField;
    private JLabel pksLabel;
    private JScrollPane scrollPane4;
    private JList pksList;
    private JLabel updatesLabel;
    private JScrollPane scrollPane5;
    private JList updatesList;
    private JLabel queriesLabel;
    private JScrollPane scrollPane6;
    private JList queriesList;
    private JLabel showTypesLabel;
    private JScrollPane scrollPane1;
    private JList showTypesList;
    private JLabel typesLabel;
    private JScrollPane scrollPane2;
    private JList typesList;
    private JLabel ignoreTypesLabel;
    private JScrollPane scrollPane3;
    private JList ignoreTypesList;
    private BindingGroup bindingGroup;
    private BindingGroup enablementBindingGroup;
    // End of variables declaration  //GEN-END:variables

    public TableConfigPanel() {
        initComponents();
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        classNameLabel = new JLabel();
        classNameTextField = new JTextField();
        pksLabel = new JLabel();
        scrollPane4 = new JScrollPane();
        pksList = new JList();
        updatesLabel = new JLabel();
        scrollPane5 = new JScrollPane();
        updatesList = new JList();
        queriesLabel = new JLabel();
        scrollPane6 = new JScrollPane();
        queriesList = new JList();
        showTypesLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        showTypesList = new JList();
        typesLabel = new JLabel();
        scrollPane2 = new JScrollPane();
        typesList = new JList();
        ignoreTypesLabel = new JLabel();
        scrollPane3 = new JScrollPane();
        ignoreTypesList = new JList();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- nameLabel ----
        nameLabel.setText(bundle.getString("TableConfigPanel.nameLabel.text"));
        add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(nameTextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- classNameLabel ----
        classNameLabel.setText(bundle.getString("TableConfigPanel.classNameLabel.text"));
        add(classNameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(classNameTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- pksLabel ----
        pksLabel.setText(bundle.getString("TableConfigPanel.pksLabel.text"));
        add(pksLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane4 ========
        {
            scrollPane4.setViewportView(pksList);
        }
        add(scrollPane4, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- updatesLabel ----
        updatesLabel.setText(bundle.getString("TableConfigPanel.updatesLabel.text"));
        add(updatesLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane5 ========
        {
            scrollPane5.setViewportView(updatesList);
        }
        add(scrollPane5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- queriesLabel ----
        queriesLabel.setText(bundle.getString("TableConfigPanel.queriesLabel.text"));
        add(queriesLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane6 ========
        {
            scrollPane6.setViewportView(queriesList);
        }
        add(scrollPane6, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- showTypesLabel ----
        showTypesLabel.setText(bundle.getString("TableConfigPanel.showTypesLabel.text"));
        add(showTypesLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(showTypesList);
        }
        add(scrollPane1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- typesLabel ----
        typesLabel.setText(bundle.getString("TableConfigPanel.typesLabel.text"));
        add(typesLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(typesList);
        }
        add(scrollPane2, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- ignoreTypesLabel ----
        ignoreTypesLabel.setText(bundle.getString("TableConfigPanel.ignoreTypesLabel.text"));
        add(ignoreTypesLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane3 ========
        {
            scrollPane3.setViewportView(ignoreTypesList);
        }
        add(scrollPane3, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.bind();
        enablementBindingGroup = new BindingGroup();
        enablementBindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }
}
