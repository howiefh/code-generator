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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class TableConfigsPanel extends JPanel {
    private static final long serialVersionUID = 7954925520583477064L;
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


    /**
     * @return typeConfigPanel
     */
    public TypeConfigPanel getTypeConfigPanel() {
        return typeConfigPanel;
    }

    /**
     * @param typeConfigPanel
     */
    public void setTypeConfigPanel(TypeConfigPanel typeConfigPanel) {
        this.typeConfigPanel = typeConfigPanel;
    }

    /**
     * @return tableConfigPanel
     */
    public TableConfigPanel getTableConfigPanel() {
        return tableConfigPanel;
    }

    /**
     * @param tableConfigPanel
     */
    public void setTableConfigPanel(TableConfigPanel tableConfigPanel) {
        this.tableConfigPanel = tableConfigPanel;
    }

    public TableConfigsPanel() {
        config = Configuration.getConfig();
        tableCfgs = ObservableCollections.observableList(new ArrayList<TableCfgModel>());
        for (TableCfg tableCfg : config.getTables()) {
            TableCfgModel tableCfgModel = new TableCfgModel(tableCfg);
            tableCfgs.add(tableCfgModel);
        }

        initComponents();

        typeConfigPanel.setShowImplements(true);
    }

    private void addTableCfg(ActionEvent e) {
        TableCfgModel tableCfgModel = new TableCfgModel();
        tableCfgs.add(tableCfgModel);
        config.getTables().add(tableCfgModel.getEntry());

        // select new task in table and scroll row to visible area
        int row = tableCfgs.size() - 1;
        tableConfigTable.setRowSelectionInterval(row, row);
        tableConfigTable.scrollRectToVisible(tableConfigTable.getCellRect(row, 0, true));

        tableConfigPanel.requestFocusInWindow();
    }

    private void deleteTableCfgs(ActionEvent e) {
        int[] selectedRows = tableConfigTable.getSelectedRows();
        if (selectedRows.length == 0)
            return;

        // remove items
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            TableCfg tableCfg = tableCfgs.get(selectedRows[i]).getEntry();
            tableCfgs.remove(selectedRows[i]);

            config.getTypes().remove(tableCfg);
        }

        // select row
        if (tableConfigTable.getRowCount() > 0) {
            int newSel = Math.min(selectedRows[0], tableConfigTable.getRowCount() - 1);
            tableConfigTable.setRowSelectionInterval(newSel, newSel);
            tableConfigTable.scrollRectToVisible(tableConfigTable.getCellRect(newSel, 0, true));
        }
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTableCfg(e);
            }
        });
        add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- deleteBbutton ----
        deleteBbutton.setToolTipText("\u5220\u9664");
        deleteBbutton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        deleteBbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTableCfgs(e);
            }
        });
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
            tableConfigTable, ELProperty.create("${selectedElement != null}"),
            deleteBbutton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            tableConfigTable, BeanProperty.create("selectedElement"),
            tableConfigPanel, BeanProperty.create("tableCfgModel")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            tableConfigPanel, BeanProperty.create("typesList.selectedElement"),
            typeConfigPanel, BeanProperty.create("typeCfgModel")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            tableConfigPanel, BeanProperty.create("columns"),
            typeConfigPanel, BeanProperty.create("columns")));
        bindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }

}
