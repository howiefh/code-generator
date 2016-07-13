package io.github.howiefh.generator.ui;

import io.github.howiefh.generator.ui.model.TableCfgModel;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingbinding.SwingBindings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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
    private JPanel panel1;
    private JComboBox pkComboBox;
    private JButton addPkButton;
    private JButton deletePkButton;
    private JScrollPane scrollPane1;
    private JList pksList;
    private JLabel updatesLabel;
    private JPanel panel2;
    private JComboBox updateComboBox;
    private JButton addUpdateColumnButton;
    private JButton deleteUpdateColumnsButton;
    private JScrollPane scrollPane2;
    private JList updatesList;
    private JLabel queriesLabel;
    private JPanel panel3;
    private JComboBox queriesComboBox;
    private JComboBox queryTypesComboBox;
    private JButton addQueryColumnButton;
    private JButton deleteQueryColumnsButton;
    private JScrollPane scrollPane3;
    private JList queriesList;
    private JLabel showTypesLabel;
    private JPanel panel4;
    private JComboBox showTypeColumnsComboBox;
    private JComboBox showTypesComboBox;
    private JButton addShowTypeButton;
    private JButton deleteShowTypesButton;
    private JScrollPane scrollPane4;
    private JList showTypesList;
    private JLabel typesLabel;
    private JScrollPane scrollPane5;
    private JList typesList;
    private JPanel panel5;
    private JButton addTypeButton;
    private JButton ignoreTypesButton3;
    private JLabel ignoreTypesLabel;
    private JScrollPane scrollPane6;
    private JList ignoreTypesList;
    private JPanel panel6;
    private JButton deleteIgnoreTypesButton;
    private List<java.lang.String> columns;
    private TableCfgModel tableCfgModel;
    private JButton addTypeButton2;
    private BindingGroup bindingGroup;
    private BindingGroup enablementBindingGroup;
    // End of variables declaration  //GEN-END:variables


    /**
     * @return tableCfgModel
     */
    public TableCfgModel getTableCfgModel() {
        return tableCfgModel;
    }

    /**
     * @param tableCfgModel
     */
    public void setTableCfgModel(TableCfgModel tableCfgModel) {
        TableCfgModel oldValue = getTableCfgModel();
        this.tableCfgModel = tableCfgModel;
        firePropertyChange("tableCfgModel", oldValue, tableCfgModel);
    }

    /**
     * @return typesList
     */
    public JList getTypesList() {
        return typesList;
    }

    /**
     * @param typesList
     */
    public void setTypesList(JList typesList) {
        this.typesList = typesList;
    }

    public TableConfigPanel() {
        initComponents();
    }

    private void addPk(ActionEvent e) {
        // TODO add your code here
    }

    private void deletePks(ActionEvent e) {
        // TODO add your code here
    }

    private void addUpdateColumn(ActionEvent e) {
        // TODO add your code here
    }

    private void deleteUpdateColumns(ActionEvent e) {
        // TODO add your code here
    }

    private void addQueryColumn(ActionEvent e) {
        // TODO add your code here
    }

    private void deleteQueryColumns(ActionEvent e) {
        // TODO add your code here
    }

    private void addShowType(ActionEvent e) {
        // TODO add your code here
    }

    private void deleteShowTypes(ActionEvent e) {
        // TODO add your code here
    }

    private void addType(ActionEvent e) {
        // TODO add your code here
    }

    private void ignoreTypes(ActionEvent e) {
        // TODO add your code here
    }

    private void deleteIgnoreTypes(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        classNameLabel = new JLabel();
        classNameTextField = new JTextField();
        pksLabel = new JLabel();
        panel1 = new JPanel();
        pkComboBox = new JComboBox();
        addPkButton = new JButton();
        deletePkButton = new JButton();
        scrollPane1 = new JScrollPane();
        pksList = new JList();
        updatesLabel = new JLabel();
        panel2 = new JPanel();
        updateComboBox = new JComboBox();
        addUpdateColumnButton = new JButton();
        deleteUpdateColumnsButton = new JButton();
        scrollPane2 = new JScrollPane();
        updatesList = new JList();
        queriesLabel = new JLabel();
        panel3 = new JPanel();
        queriesComboBox = new JComboBox();
        queryTypesComboBox = new JComboBox();
        addQueryColumnButton = new JButton();
        deleteQueryColumnsButton = new JButton();
        scrollPane3 = new JScrollPane();
        queriesList = new JList();
        showTypesLabel = new JLabel();
        panel4 = new JPanel();
        showTypeColumnsComboBox = new JComboBox();
        showTypesComboBox = new JComboBox();
        addShowTypeButton = new JButton();
        deleteShowTypesButton = new JButton();
        scrollPane4 = new JScrollPane();
        showTypesList = new JList();
        typesLabel = new JLabel();
        scrollPane5 = new JScrollPane();
        typesList = new JList();
        panel5 = new JPanel();
        addTypeButton = new JButton();
        ignoreTypesButton3 = new JButton();
        ignoreTypesLabel = new JLabel();
        scrollPane6 = new JScrollPane();
        ignoreTypesList = new JList();
        panel6 = new JPanel();
        deleteIgnoreTypesButton = new JButton();
        tableCfgModel = new TableCfgModel();
        addTypeButton2 = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

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

        //======== panel1 ========
        {
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            panel1.add(pkComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- addPkButton ----
            addPkButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addPkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addPk(e);
                }
            });
            panel1.add(addPkButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- deletePkButton ----
            deletePkButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deletePkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deletePks(e);
                }
            });
            panel1.add(deletePkButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(pksList);
            }
            panel1.add(scrollPane1, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- updatesLabel ----
        updatesLabel.setText(bundle.getString("TableConfigPanel.updatesLabel.text"));
        add(updatesLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel2 ========
        {
            panel2.setLayout(new GridBagLayout());
            ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            panel2.add(updateComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- addUpdateColumnButton ----
            addUpdateColumnButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addUpdateColumnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addUpdateColumn(e);
                }
            });
            panel2.add(addUpdateColumnButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- deleteUpdateColumnsButton ----
            deleteUpdateColumnsButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deleteUpdateColumnsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteUpdateColumns(e);
                }
            });
            panel2.add(deleteUpdateColumnsButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(updatesList);
            }
            panel2.add(scrollPane2, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel2, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- queriesLabel ----
        queriesLabel.setText(bundle.getString("TableConfigPanel.queriesLabel.text"));
        add(queriesLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel3 ========
        {
            panel3.setLayout(new GridBagLayout());
            ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            panel3.add(queriesComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel3.add(queryTypesComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- addQueryColumnButton ----
            addQueryColumnButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addQueryColumnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addQueryColumn(e);
                }
            });
            panel3.add(addQueryColumnButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- deleteQueryColumnsButton ----
            deleteQueryColumnsButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deleteQueryColumnsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteQueryColumns(e);
                }
            });
            panel3.add(deleteQueryColumnsButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(queriesList);
            }
            panel3.add(scrollPane3, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel3, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- showTypesLabel ----
        showTypesLabel.setText(bundle.getString("TableConfigPanel.showTypesLabel.text"));
        add(showTypesLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel4 ========
        {
            panel4.setLayout(new GridBagLayout());
            ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            panel4.add(showTypeColumnsComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel4.add(showTypesComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- addShowTypeButton ----
            addShowTypeButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addShowTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addShowType(e);
                }
            });
            panel4.add(addShowTypeButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- deleteShowTypesButton ----
            deleteShowTypesButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deleteShowTypesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteShowTypes(e);
                }
            });
            panel4.add(deleteShowTypesButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane4 ========
            {
                scrollPane4.setViewportView(showTypesList);
            }
            panel4.add(scrollPane4, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel4, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- typesLabel ----
        typesLabel.setText(bundle.getString("TableConfigPanel.typesLabel.text"));
        add(typesLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane5 ========
        {
            scrollPane5.setViewportView(typesList);
        }
        add(scrollPane5, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel5 ========
        {
            panel5.setLayout(new GridBagLayout());
            ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- addTypeButton ----
            addTypeButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addType(e);
                }
            });
            panel5.add(addTypeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- ignoreTypesButton3 ----
            ignoreTypesButton3.setIcon(new ImageIcon(getClass().getResource("/icons/exclude.png")));
            ignoreTypesButton3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ignoreTypes(e);
                }
            });
            panel5.add(ignoreTypesButton3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(panel5, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- ignoreTypesLabel ----
        ignoreTypesLabel.setText(bundle.getString("TableConfigPanel.ignoreTypesLabel.text"));
        add(ignoreTypesLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane6 ========
        {
            scrollPane6.setViewportView(ignoreTypesList);
        }
        add(scrollPane6, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel6 ========
        {
            panel6.setLayout(new GridBagLayout());
            ((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- deleteIgnoreTypesButton ----
            deleteIgnoreTypesButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deleteIgnoreTypesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteIgnoreTypes(e);
                }
            });
            panel6.add(deleteIgnoreTypesButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(panel6, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- addTypeButton2 ----
        addTypeButton2.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("tableCfgModel.name"),
            nameTextField, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("tableCfgModel.className"),
            classNameTextField, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("tableCfgModel.pks"), pksList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("tableCfgModel.updates"), updatesList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("tableCfgModel.queries"), queriesList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("tableCfgModel.showTypes"), showTypesList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("tableCfgModel.types"), typesList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("tableCfgModel.ignoreTypes"), ignoreTypesList));
        bindingGroup.bind();
        enablementBindingGroup = new BindingGroup();
        enablementBindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }
}
