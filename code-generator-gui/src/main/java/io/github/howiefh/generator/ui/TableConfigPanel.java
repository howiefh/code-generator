package io.github.howiefh.generator.ui;

import io.github.howiefh.generator.common.QueryType;
import io.github.howiefh.generator.common.ShowType;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.service.TableMetaDataService;
import io.github.howiefh.generator.ui.handle.JListActionHandler;
import io.github.howiefh.generator.ui.model.TableCfgModel;
import io.github.howiefh.generator.ui.model.TypeCfgModel;
import io.github.howiefh.generator.ui.util.SimpleEntry;
import io.github.howiefh.generator.ui.util.WrapList;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.SwingBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author fenghao, Mon Jul 11 21:20:47 CST 2016
 * @version 1.0
 * @since 1.0
 */
public class TableConfigPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableConfigPanel.class);
    private static final long serialVersionUID = 1485156026951238430L;
    private JListActionHandler listActionHandler;
    private Runnable synchThread = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (tableCfgModel != null && StringUtils.isNotBlank(tableCfgModel.getName())) {
                            List<String> columns = TableMetaDataService.getInstance().findColumnList(tableCfgModel.getName());
                            setColumns(columns);
                        }
                    } catch (Exception e) {
                        LOGGER.warn("获取列名异常 ", e.getMessage());
                    }
                }
            };
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
    private JPanel panel7;
    private JComboBox typesComboBox;
    private JButton addTypeButton;
    private JButton ignoreTypesButton;
    private JScrollPane scrollPane5;
    private JList typesList;
    private JPanel panel5;
    private JLabel ignoreTypesLabel;
    private JScrollPane scrollPane6;
    private JList ignoreTypesList;
    private JPanel panel6;
    private JButton deleteIgnoreTypesButton;
    private List<java.lang.String> columns;
    private TableCfgModel tableCfgModel;
    private List<java.lang.String> queryTypes;
    private List<java.lang.String> showTypes;
    private BindingGroup bindingGroup;
    private BindingGroup enablementBindingGroup;
    // End of variables declaration  //GEN-END:variables

    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);

        nameLabel.setEnabled(enabled);
        nameTextField.setEditable(enabled);
        classNameLabel.setEnabled(enabled);
        classNameTextField.setEnabled(enabled);
        pksLabel.setEnabled(enabled);
        panel1.setEnabled(enabled);
        pkComboBox.setEnabled(enabled);
        addPkButton.setEnabled(enabled);
        scrollPane1.setEnabled(enabled);
        pksList.setEnabled(enabled);
        updatesLabel.setEnabled(enabled);
        panel2.setEnabled(enabled);
        updateComboBox.setEnabled(enabled);
        addUpdateColumnButton.setEnabled(enabled);
        scrollPane2.setEnabled(enabled);
        updatesList.setEnabled(enabled);
        queriesLabel.setEnabled(enabled);
        panel3.setEnabled(enabled);
        queriesComboBox.setEnabled(enabled);
        queryTypesComboBox.setEnabled(enabled);
        addQueryColumnButton.setEnabled(enabled);
        scrollPane3.setEnabled(enabled);
        queriesList.setEnabled(enabled);
        showTypesLabel.setEnabled(enabled);
        panel4.setEnabled(enabled);
        showTypeColumnsComboBox.setEnabled(enabled);
        showTypesComboBox.setEnabled(enabled);
        addShowTypeButton.setEnabled(enabled);
        scrollPane4.setEnabled(enabled);
        showTypesList.setEnabled(enabled);
        typesLabel.setEnabled(enabled);
        scrollPane5.setEnabled(enabled);
        typesList.setEnabled(enabled);
        panel5.setEnabled(enabled);
        addTypeButton.setEnabled(enabled);
        ignoreTypesLabel.setEnabled(enabled);
        scrollPane6.setEnabled(enabled);
        ignoreTypesList.setEnabled(enabled);
        panel6.setEnabled(enabled);
    }

    /**
     * @return typesComboBox
     */
    public JComboBox getTypesComboBox() {
        return typesComboBox;
    }

    /**
     * @param typesComboBox
     */
    public void setTypesComboBox(JComboBox typesComboBox) {
        this.typesComboBox = typesComboBox;
    }

    /**
     * @return queryTypes
     */
    public List<String> getQueryTypes() {
        return queryTypes;
    }

    /**
     * @param queryTypes
     */
    public void setQueryTypes(List<String> queryTypes) {
        List<String> oldValue = getQueryTypes();
        this.queryTypes = queryTypes;
        firePropertyChange("queryTypes", oldValue, queryTypes);
    }

    /**
     * @return showTypes
     */
    public List<String> getShowTypes() {
        return showTypes;
    }

    /**
     * @param showTypes
     */
    public void setShowTypes(List<String> showTypes) {
        List<String> oldValue = getShowTypes();
        this.showTypes = showTypes;
        firePropertyChange("showTypes", oldValue, showTypes);
    }

    /**
     * @return columns
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * @param columns
     */
    public void setColumns(List<String> columns) {
        List<String> oldValue = getColumns();
        this.columns = columns;
        firePropertyChange("columns", oldValue, columns);
    }

    /**
     * @return tableCfgModel
     */
    public TableCfgModel getTableCfgModel() {
        return tableCfgModel;
    }

    /**
     * @param tableCfgModel
     */
    public void setTableCfgModel(final TableCfgModel tableCfgModel) {
        TableCfgModel oldValue = getTableCfgModel();
        this.tableCfgModel = tableCfgModel;
        firePropertyChange("tableCfgModel", oldValue, tableCfgModel);

        if (tableCfgModel != null && tableCfgModel.getName() != null && (oldValue == null || oldValue != null && !tableCfgModel.getName().equals(oldValue.getName()))) {
            new Thread(synchThread).start();
        }
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
        queryTypes = ObservableCollections.observableList(QueryType.getQueryTypes());
        showTypes = ObservableCollections.observableList(ShowType.getShowTypes());
        initComponents();

        listActionHandler = new JListActionHandler();
    }

    private void addPk(ActionEvent e) {
        String text = (String) pkComboBox.getSelectedItem();
        listActionHandler.addItem(text, tableCfgModel, "pks", pksList);
    }

    private void deletePks(ActionEvent e) {
        listActionHandler.deleteItems(tableCfgModel, "pks", pksList);
    }

    private void addUpdateColumn(ActionEvent e) {
        String text = (String) updateComboBox.getSelectedItem();
        listActionHandler.addItem(text, tableCfgModel, "updates", updatesList);
    }

    private void deleteUpdateColumns(ActionEvent e) {
        listActionHandler.deleteItems(tableCfgModel, "updates", updatesList);
    }

    private void addQueryColumn(ActionEvent e) {
        String queryColumn = (String) queriesComboBox.getSelectedItem();
        String queryType = (String) queryTypesComboBox.getSelectedItem();
        listActionHandler.addItem(new SimpleEntry<String, String>(queryColumn, queryType) , tableCfgModel, "queries", queriesList);
    }

    private void deleteQueryColumns(ActionEvent e) {
        listActionHandler.deleteItems(tableCfgModel, "queries", queriesList);
    }

    private void addShowType(ActionEvent e) {
        String showTypeColumn = (String) showTypeColumnsComboBox.getSelectedItem();
        String showType = (String) showTypesComboBox.getSelectedItem();
        listActionHandler.addItem(new SimpleEntry<String, String>(showTypeColumn, showType) , tableCfgModel, "showTypes", showTypesList);
    }

    private void deleteShowTypes(ActionEvent e) {
        listActionHandler.deleteItems(tableCfgModel, "showTypes", showTypesList);
    }

    private void addType(ActionEvent e) {
        String name = (String) typesComboBox.getSelectedItem();
        TypeCfgModel typeCfgModel = new TypeCfgModel();
        typeCfgModel.setName(name);
        List<TypeCfgModel> types = new WrapList<TypeCfgModel>(tableCfgModel.getTypes());
        types.add(typeCfgModel);

        tableCfgModel.setTypes(types);

        // select new task in table and scroll row to visible area
        int row = types.size() - 1;
        typesList.setSelectedIndex(row);
        typesList.scrollRectToVisible(typesList.getCellBounds(row, row));
    }

    private void ignoreTypes(ActionEvent e) {
        listActionHandler.moveItems(tableCfgModel, tableCfgModel.getTypes(), "ignoreTypes", typesList, ignoreTypesList);
    }

    private void deleteIgnoreTypes(ActionEvent e) {
        listActionHandler.deleteItems(tableCfgModel, "ignoreTypes", ignoreTypesList);
    }

    private void focusOutNameTextField(FocusEvent e) {
        String oldValue = tableCfgModel.getName();
        String newValue = nameTextField.getText();
        if (newValue.equals(oldValue)) {
            return;
        }

        new Thread(synchThread).start();
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
        panel7 = new JPanel();
        typesComboBox = new JComboBox();
        addTypeButton = new JButton();
        ignoreTypesButton = new JButton();
        scrollPane5 = new JScrollPane();
        typesList = new JList();
        panel5 = new JPanel();
        ignoreTypesLabel = new JLabel();
        scrollPane6 = new JScrollPane();
        ignoreTypesList = new JList();
        panel6 = new JPanel();
        deleteIgnoreTypesButton = new JButton();
        tableCfgModel = new TableCfgModel();

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

        //---- nameTextField ----
        nameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                focusOutNameTextField(e);
            }
        });
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
            ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 0.0, 1.0E-4};
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
            ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 0.0, 1.0E-4};
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

        //======== panel7 ========
        {
            panel7.setLayout(new GridBagLayout());
            ((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            panel7.add(typesComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- addTypeButton ----
            addTypeButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addTypeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addType(e);
                }
            });
            panel7.add(addTypeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- ignoreTypesButton ----
            ignoreTypesButton.setIcon(new ImageIcon(getClass().getResource("/icons/exclude.png")));
            ignoreTypesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ignoreTypes(e);
                }
            });
            panel7.add(ignoreTypesButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane5 ========
            {
                scrollPane5.setViewportView(typesList);
            }
            panel7.add(scrollPane5, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel7, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel5 ========
        {
            panel5.setLayout(new GridBagLayout());
            ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
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
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("columns"), pkComboBox));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("columns"), updateComboBox));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("columns"), queriesComboBox));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("columns"), showTypeColumnsComboBox));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            queryTypes, queryTypesComboBox));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            showTypes, showTypesComboBox));
        bindingGroup.bind();
        enablementBindingGroup = new BindingGroup();
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            queriesList, ELProperty.create("${selectedElement != null}"),
            deleteQueryColumnsButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            typesList, ELProperty.create("${selectedElement != null}"),
            ignoreTypesButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            pksList, ELProperty.create("${selectedElement != null}"),
            deletePkButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            ignoreTypesList, ELProperty.create("${selectedElement != null}"),
            deleteIgnoreTypesButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            updatesList, ELProperty.create("${selectedElement != null}"),
            deleteUpdateColumnsButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            showTypesList, ELProperty.create("${selectedElement != null}"),
            deleteShowTypesButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, ELProperty.create("${tableCfgModel != null}"),
            this, BeanProperty.create("enabled")));
        enablementBindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }
}
