package io.github.howiefh.generator.ui;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.ui.model.TypeCfgModel;
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
public class TypeConfigsPanel extends JPanel {
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane;
    private JTable typeConfigTable;
    private JButton addButton;
    private JButton deleteButton;
    private TypeConfigPanel typeConfigFormPanel;
    private List<io.github.howiefh.generator.ui.model.TypeCfgModel> typeCfgs;
    private BindingGroup bindingGroup;
    // End of variables declaration  //GEN-END:variables

    private Config config;

    public TypeConfigsPanel() {
        List<String> types = ObservableCollections.observableList(new ArrayList<String>());

        config = Configuration.getConfig();
        typeCfgs = ObservableCollections.observableList(new ArrayList<TypeCfgModel>());
        for (TypeCfg typeCfg : config.getTypes()) {
            types.add(typeCfg.getName());

            TypeCfgModel typeCfgModel = new TypeCfgModel(typeCfg);
            typeCfgs.add(typeCfgModel);
        }

        initComponents();

        typeConfigFormPanel.setShowImplements(false);
        typeConfigFormPanel.setTypes(types);
    }

    /**
     * @return typeCfgs
     */
    public List<TypeCfgModel> getTypeCfgs() {
        return typeCfgs;
    }

    /**
     * @param typeCfgs
     */
    public void setTypeCfgs(List<TypeCfgModel> typeCfgs) {
        List<TypeCfgModel> oldTypeCfgModels = getTypeCfgs();
        this.typeCfgs = typeCfgs;
        firePropertyChange("typeCfgs", oldTypeCfgModels, typeCfgs);
    }

    private void newTypeConfig(ActionEvent e) {
        TypeCfgModel typeCfgModel = new TypeCfgModel();
        typeCfgs.add(typeCfgModel);
        config.getTypes().add(typeCfgModel.getEntry());

        // select new task in table and scroll row to visible area
        int row = typeCfgs.size() - 1;
        typeConfigTable.setRowSelectionInterval(row, row);
        typeConfigTable.scrollRectToVisible(typeConfigTable.getCellRect(row, 0, true));

        typeConfigFormPanel.requestFocusInWindow();
    }

    private void deleteTypeConfig(ActionEvent e) {
        int[] selectedRows = typeConfigTable.getSelectedRows();
        if (selectedRows.length == 0)
            return;

        // remove items
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            TypeCfg typeCfg = typeCfgs.get(selectedRows[i]).getEntry();
            typeCfgs.remove(selectedRows[i]);

            config.getTypes().remove(typeCfg);

            List<String> types = typeConfigFormPanel.getTypes();
            types.remove(typeCfg.getName());
            typeConfigFormPanel.setTypes(types);
        }

        // select row
        if (typeConfigTable.getRowCount() > 0) {
            int newSel = Math.min(selectedRows[0], typeConfigTable.getRowCount() - 1);
            typeConfigTable.setRowSelectionInterval(newSel, newSel);
            typeConfigTable.scrollRectToVisible(typeConfigTable.getCellRect(newSel, 0, true));
        }
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        scrollPane = new JScrollPane();
        typeConfigTable = new JTable();
        addButton = new JButton();
        deleteButton = new JButton();
        typeConfigFormPanel = new TypeConfigPanel();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0, 1.0E-4};

        //======== scrollPane ========
        {

            //---- typeConfigTable ----
            typeConfigTable.setPreferredScrollableViewportSize(new Dimension(450, 300));
            scrollPane.setViewportView(typeConfigTable);
        }
        add(scrollPane, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- addButton ----
        addButton.setToolTipText(bundle.getString("TypeConfigPanel.addButton.toolTipText"));
        addButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTypeConfig(e);
            }
        });
        add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- deleteButton ----
        deleteButton.setToolTipText(bundle.getString("TypeConfigPanel.deleteButton.toolTipText"));
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTypeConfig(e);
            }
        });
        add(deleteButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- typeConfigFormPanel ----
        typeConfigFormPanel.setBorder(new TitledBorder(bundle.getString("TypeConfigPanel.typeConfigFormPanel.border")));
        add(typeConfigFormPanel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        {
            JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
                this, (BeanProperty) BeanProperty.create("typeCfgs"), typeConfigTable);
            binding.addColumnBinding(BeanProperty.create("name"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_5"))
                .setColumnClass(String.class)
                .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("template"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_9"))
                .setColumnClass(String.class)
                .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("target"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_8"))
                .setColumnClass(String.class)
                .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("pkg"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_6"))
                .setColumnClass(String.class)
                .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("suffix"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_7"))
                .setColumnClass(String.class)
                .setEditable(false);
            binding.addColumnBinding(BeanProperty.create("dependencies"))
                .setColumnName(bundle.getString("TypeConfigPanel.typeConfigTable.columnName_2"))
                .setColumnClass(List.class)
                .setEditable(false);
            bindingGroup.addBinding(binding);
        }
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            typeConfigTable, ELProperty.create("${selectedElement != null}"),
            deleteButton, BeanProperty.create("enabled")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            typeConfigTable, BeanProperty.create("selectedElement"),
            typeConfigFormPanel, BeanProperty.create("typeCfgModel")));
        bindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }

}
