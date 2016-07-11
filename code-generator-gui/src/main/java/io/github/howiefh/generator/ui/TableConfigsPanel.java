package io.github.howiefh.generator.ui;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.ui.model.TableCfgModel;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class TableConfigsPanel extends JPanel {
    private Config config;
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane;
    private JTable tableConfigTable;
    private JButton addButton;
    private JButton deleteBbutton;
    private JPanel tableAndTypeConfigPanel;
    private TableConfigPanel tableConfigPanel;
    private TypeConfigPanel typeConfigPanel;
    private List<io.github.howiefh.generator.ui.model.TableCfgModel> tableCfgs;
    private BindingGroup bindingGroup;
    // End of variables declaration  //GEN-END:variables

    public TableConfigsPanel() {
        config = Configuration.getConfig();
        tableCfgs = ObservableCollections.observableList(new ArrayList<TableCfgModel>());
        for (TableCfg tableCfg : config.getTables()) {
            TableCfgModel tableCfgModel = new TableCfgModel();
            tableCfgModel.setTableCfg(tableCfg);
            tableCfgs.add(tableCfgModel);
        }

        initComponents();
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        scrollPane = new JScrollPane();
        tableConfigTable = new JTable();
        addButton = new JButton();
        deleteBbutton = new JButton();
        tableAndTypeConfigPanel = new JPanel();
        tableConfigPanel = new TableConfigPanel();
        typeConfigPanel = new TypeConfigPanel();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0, 1.0E-4};

        //======== scrollPane ========
        {
            scrollPane.setViewportView(tableConfigTable);
        }
        add(scrollPane, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- addButton ----
        addButton.setToolTipText(bundle.getString("TableConfigPanel.addButton.toolTipText"));
        addButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
        add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- deleteBbutton ----
        deleteBbutton.setToolTipText("\u5220\u9664");
        deleteBbutton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        add(deleteBbutton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== tableAndTypeConfigPanel ========
        {
            tableAndTypeConfigPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)tableAndTypeConfigPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)tableAndTypeConfigPanel.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)tableAndTypeConfigPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
            ((GridBagLayout)tableAndTypeConfigPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //---- tableConfigPanel ----
            tableConfigPanel.setBorder(new TitledBorder(bundle.getString("TableConfigPanel.tableConfigPanel.border")));
            tableAndTypeConfigPanel.add(tableConfigPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- typeConfigPanel ----
            typeConfigPanel.setBorder(new TitledBorder(bundle.getString("TableConfigPanel.typeConfigPanel.border")));
            tableAndTypeConfigPanel.add(typeConfigPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(tableAndTypeConfigPanel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                tableCfgs, tableConfigTable);
            binding.addColumnBinding(BeanProperty.create("name"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_4"))
                .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("className"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_2"))
                .setColumnClass(String.class);
            binding.addColumnBinding(BeanProperty.create("pks"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_5"))
                .setColumnClass(List.class);
            binding.addColumnBinding(BeanProperty.create("updates"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_10"))
                .setColumnClass(List.class);
            binding.addColumnBinding(BeanProperty.create("queries"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_6"))
                .setColumnClass(List.class);
            binding.addColumnBinding(BeanProperty.create("showTypes"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_7"))
                .setColumnClass(List.class);
            binding.addColumnBinding(BeanProperty.create("types"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_9"))
                .setColumnClass(List.class);
            binding.addColumnBinding(BeanProperty.create("ignoreTypes"))
                .setColumnName(bundle.getString("TableConfigPanel.tableConfigTable.columnName_3"))
                .setColumnClass(List.class);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            tableConfigTable, ELProperty.create("${selectedElements != null}"),
            deleteBbutton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            tableConfigTable, BeanProperty.create("selectedElement"),
            tableConfigPanel, BeanProperty.create("tableCfgModel")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            tableConfigPanel, BeanProperty.create("typesList.selectedElement"),
            typeConfigPanel, BeanProperty.create("typeCfgModel")));
        bindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }

}
