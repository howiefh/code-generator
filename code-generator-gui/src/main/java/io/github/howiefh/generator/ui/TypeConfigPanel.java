package io.github.howiefh.generator.ui;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class TypeConfigPanel extends JPanel {
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane;
    private JTable typeConfigTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteBbutton;
    private JPanel typeConfigFormPanel;
    private ObservableList<io.github.howiefh.generator.common.config.TypeCfg> typeCfgs;
    private BindingGroup bindingGroup;
    // End of variables declaration  //GEN-END:variables

    public TypeConfigPanel() {
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
        typeConfigFormPanel = new JPanel();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0, 1.0E-4};

        //======== scrollPane ========
        {
            scrollPane.setViewportView(typeConfigTable);
        }
        add(scrollPane, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- addButton ----
        addButton.setText(bundle.getString("TypeConfigPanel.addButton.text"));
        add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- editButton ----
        editButton.setText(bundle.getString("TypeConfigPanel.editButton.text"));
        add(editButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- deleteBbutton ----
        deleteBbutton.setText(bundle.getString("TypeConfigPanel.deleteBbutton.text"));
        add(deleteBbutton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== typeConfigFormPanel ========
        {
            typeConfigFormPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)typeConfigFormPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)typeConfigFormPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)typeConfigFormPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)typeConfigFormPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
        }
        add(typeConfigFormPanel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                typeCfgs, typeConfigTable);
            binding.addColumnBinding(BeanProperty.create("attributes"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName.1"))
                .setColumnClass(Map.class);
            binding.addColumnBinding(BeanProperty.create("dependencies"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_2"))
                .setColumnClass(Set.class);
            binding.addColumnBinding(BeanProperty.create("ignoreImpls"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_3"))
                .setColumnClass(Set.class);
            binding.addColumnBinding(BeanProperty.create("impls"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_4"))
                .setColumnClass(Set.class);
            binding.addColumnBinding(BeanProperty.create("name"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_5"))
                .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("pkg"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_6"))
                .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("suffix"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_7"))
                .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("target"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_8"))
                .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("template"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_9"))
                .setColumnClass(String.class);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }

}
