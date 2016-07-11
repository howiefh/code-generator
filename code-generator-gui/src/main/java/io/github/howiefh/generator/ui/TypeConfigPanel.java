package io.github.howiefh.generator.ui;

import com.google.common.io.Files;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.ImplementCfg;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.ui.handle.SelectFileHandler;
import io.github.howiefh.generator.ui.model.TypeCfgModel;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.SwingBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author fenghao, Sun Jul 10 14:39:21 CST 2016
 * @version 1.0
 * @since 1.0
 */
public class TypeConfigPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeConfigPanel.class);
    private SelectFileHandler selectFileHandler;
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel templateLabel;
    private JTextField templateTextField;
    private JButton templateButton;
    private JLabel targetLabel;
    private JTextField targetTextField;
    private JButton targetDirButton;
    private JLabel packageLabel;
    private JTextField packageTextField;
    private JLabel suffixLabel;
    private JTextField suffixTextField;
    private JLabel dependenciesLabel;
    private JScrollPane scrollPane1;
    private JList dependenciesList;
    private JPanel dependenciesBtnPanel;
    private JButton addDependenceButton;
    private JButton deleteDependenceButton;
    private JLabel implsLabel;
    private JScrollPane scrollPane2;
    private JList implsList;
    private JPanel implementBtnPanel;
    private JButton addImplementButton;
    private JButton ignoreImplementButton;
    private JLabel ignoreImplsLabel;
    private JScrollPane scrollPane3;
    private JList ignoreImplsList;
    private JPanel ignoreImplsButtonPanel;
    private JButton deleteIgnoreImplButton;
    private JPanel addDependenceDialog;
    private JLabel dependenceLabel;
    private JComboBox dependenciesComboBox;
    private JPanel addImplementDialog;
    private JLabel implementNameLabel;
    private JTextField implementNameTextField;
    private JLabel columnsLabel;
    private JPanel panel1;
    private JScrollPane scrollPane4;
    private JList columnsList;
    private JButton addColumnsButton;
    private JScrollPane scrollPane5;
    private JList implColumnsList;
    private JButton deleteColumnsButton;
    private TypeCfgModel typeCfgModel;
    private List<java.lang.String> types;
    private BindingGroup bindingGroup;
    private BindingGroup enablementBindingGroup;
    // End of variables declaration  //GEN-END:variables

    /**
     * @return typeCfgModel
     */
    public TypeCfgModel getTypeCfgModel() {
        return typeCfgModel;
    }

    /**
     * @param typeCfgModel
     */
    public void setTypeCfgModel(TypeCfgModel typeCfgModel) {
        TypeCfgModel oldValue = getTypeCfgModel();
        this.typeCfgModel = typeCfgModel;
        firePropertyChange("typeCfgModel", oldValue, typeCfgModel);
    }

    /**
     * @return types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * @param types
     */
    public void setTypes(List<String> types) {
        List<String> oldValue = getTypes();
        this.types = types;
        firePropertyChange("types", oldValue, types);
    }

    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);

        nameLabel.setEnabled(enabled);
        nameTextField.setEditable(enabled);
        templateLabel.setEnabled(enabled);
        templateTextField.setEditable(enabled);
        targetLabel.setEnabled(enabled);
        targetTextField.setEditable(enabled);
        packageLabel.setEnabled(enabled);
        packageTextField.setEditable(enabled);
        suffixLabel.setEnabled(enabled);
        suffixTextField.setEditable(enabled);
        dependenciesLabel.setEnabled(enabled);
        dependenciesList.setEnabled(enabled);
        dependenciesBtnPanel.setEnabled(enabled);
        implsLabel.setEnabled(enabled);
        implsList.setEnabled(enabled);
        ignoreImplsLabel.setEnabled(enabled);
        ignoreImplsList.setEnabled(enabled);

        templateButton.setEnabled(enabled);
        targetDirButton.setEnabled(enabled);
        addDependenceButton.setEnabled(enabled);
        addImplementButton.setEnabled(enabled);
    }

    public TypeConfigPanel() {
        initComponents();

        selectFileHandler = new SelectFileHandler(targetTextField);
    }

    private void addDependence(ActionEvent e) {
        String dep = showDependenciesDialog("Add dependence");
        if (StringUtils.isBlank(dep))
            return;

        List<String> dependencies = typeCfgModel.getDependencies();
        if (dependencies == null) {
            dependencies = new ArrayList<String>();
        }
        dependencies.add(dep);

        // add new dependence to dependencies list
        typeCfgModel.setDependencies(dependencies);

        // select new dependence in list and scroll row to visible area
        int row = typeCfgModel.getDependencies().size() - 1;
        dependenciesList.setSelectedIndex(row);
        dependenciesList.scrollRectToVisible(dependenciesList.getCellBounds(row, row));
    }

    private String showDependenciesDialog(String title) {
        JOptionPane optionPane = new JOptionPane(addDependenceDialog, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(null, title);
        dialog.setResizable(true);
        dialog.setVisible(true);

        if (!new Integer(JOptionPane.OK_OPTION).equals(optionPane.getValue()))
            return null;

        return (String) dependenciesComboBox.getSelectedItem();
    }

    private void deleteDependence(ActionEvent e) {
        int[] selectedRows = dependenciesList.getSelectedIndices();
        if (selectedRows.length == 0)
            return;

        List<String> dependencies = typeCfgModel.getDependencies();
        // remove items
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            dependencies.remove(selectedRows[i]);
        }
        typeCfgModel.setDependencies(dependencies);

        // select row
        if (dependencies.size() > 0) {
            int newSel = Math.min(selectedRows[0], dependencies.size() - 1);
            dependenciesList.setSelectedIndex(newSel);
            dependenciesList.scrollRectToVisible(dependenciesList.getCellBounds(newSel, newSel));
        }
    }

    private void selectTargetDir(ActionEvent e) {
        selectFileHandler.handleSelectFile();
    }

    private void editTemplate(ActionEvent e) {
        String templateDir = Configuration.getConfig().getTemplateDir();
        File template = new File(templateDir, templateTextField.getText());
        if (!template.getParentFile().exists()) {
            try {
                Files.createParentDirs(template);
                if (!template.exists()) {
                    Files.touch(template);
                    Desktop.getDesktop().open(template);
                }
            } catch (IOException e1) {
                LOGGER.warn("Open {} error.", template.getAbsolutePath());
            }
        }
    }

    private void addImplement(ActionEvent e) {
        // TODO
        showImplementsDialog("Add implements");
    }

    private String showImplementsDialog(String title) {
        JOptionPane optionPane = new JOptionPane(addImplementDialog, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(null, title);
        dialog.setResizable(true);
        dialog.setVisible(true);

        // TODO
        if (!new Integer(JOptionPane.OK_OPTION).equals(optionPane.getValue()))
            return null;

        return "";
    }
    private void ignoreImplement(ActionEvent e) {
        int[] selectedRows = implsList.getSelectedIndices();
        if (selectedRows.length == 0)
            return;

        List<ImplementCfg> implementCfgs = typeCfgModel.getImpls();
        List<String> ignoreImpls = typeCfgModel.getIgnoreImpls();
        // remove items
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            ImplementCfg implementCfg = implementCfgs.get(selectedRows[i]);
            ignoreImpls.add(implementCfg.getName());
        }
        typeCfgModel.setIgnoreImpls(ignoreImpls);

        // select row
        int row = selectedRows[selectedRows.length - 1];
        implsList.setSelectedIndex(row);
        implsList.scrollRectToVisible(implsList.getCellBounds(row, row));
    }

    private void deleteIgnoreImpl(ActionEvent e) {
        int[] selectedRows = ignoreImplsList.getSelectedIndices();
        if (selectedRows.length == 0)
            return;

        List<String> ignoreImpls = typeCfgModel.getIgnoreImpls();
        // remove items
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            ignoreImpls.remove(selectedRows[i]);
        }
        typeCfgModel.setIgnoreImpls(ignoreImpls);

        // select row
        if (ignoreImpls.size() > 0) {
            int newSel = Math.min(selectedRows[0], ignoreImpls.size() - 1);
            ignoreImplsList.setSelectedIndex(newSel);
            ignoreImplsList.scrollRectToVisible(ignoreImplsList.getCellBounds(newSel, newSel));
        }
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        templateLabel = new JLabel();
        templateTextField = new JTextField();
        templateButton = new JButton();
        targetLabel = new JLabel();
        targetTextField = new JTextField();
        targetDirButton = new JButton();
        packageLabel = new JLabel();
        packageTextField = new JTextField();
        suffixLabel = new JLabel();
        suffixTextField = new JTextField();
        dependenciesLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        dependenciesList = new JList();
        dependenciesBtnPanel = new JPanel();
        addDependenceButton = new JButton();
        deleteDependenceButton = new JButton();
        implsLabel = new JLabel();
        scrollPane2 = new JScrollPane();
        implsList = new JList();
        implementBtnPanel = new JPanel();
        addImplementButton = new JButton();
        ignoreImplementButton = new JButton();
        ignoreImplsLabel = new JLabel();
        scrollPane3 = new JScrollPane();
        ignoreImplsList = new JList();
        ignoreImplsButtonPanel = new JPanel();
        deleteIgnoreImplButton = new JButton();
        addDependenceDialog = new JPanel();
        dependenceLabel = new JLabel();
        dependenciesComboBox = new JComboBox();
        addImplementDialog = new JPanel();
        implementNameLabel = new JLabel();
        implementNameTextField = new JTextField();
        columnsLabel = new JLabel();
        panel1 = new JPanel();
        scrollPane4 = new JScrollPane();
        columnsList = new JList();
        addColumnsButton = new JButton();
        scrollPane5 = new JScrollPane();
        implColumnsList = new JList();
        deleteColumnsButton = new JButton();
        typeCfgModel = new TypeCfgModel();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- nameLabel ----
        nameLabel.setText(bundle.getString("TypeConfigPanel.nameLabel.text"));
        add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(nameTextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- templateLabel ----
        templateLabel.setText(bundle.getString("TypeConfigPanel.templateLabel.text"));
        add(templateLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(templateTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- templateButton ----
        templateButton.setToolTipText("\u6253\u5f00\u6a21\u677f\u6587\u4ef6");
        templateButton.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
        templateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTemplate(e);
            }
        });
        add(templateButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- targetLabel ----
        targetLabel.setText(bundle.getString("TypeConfigPanel.targetLabel.text"));
        add(targetLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(targetTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- targetDirButton ----
        targetDirButton.setText(bundle.getString("TypeConfigPanel.targetDirButton.text"));
        targetDirButton.setToolTipText("\u9009\u62e9\u76ee\u6807\u76ee\u5f55");
        targetDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTargetDir(e);
            }
        });
        add(targetDirButton, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- packageLabel ----
        packageLabel.setText(bundle.getString("TypeConfigPanel.packageLabel.text"));
        add(packageLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(packageTextField, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- suffixLabel ----
        suffixLabel.setText(bundle.getString("TypeConfigPanel.suffixLabel.text"));
        add(suffixLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(suffixTextField, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- dependenciesLabel ----
        dependenciesLabel.setText(bundle.getString("TypeConfigPanel.dependenciesLabel.text"));
        add(dependenciesLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(dependenciesList);
        }
        add(scrollPane1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== dependenciesBtnPanel ========
        {
            dependenciesBtnPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)dependenciesBtnPanel.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)dependenciesBtnPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)dependenciesBtnPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)dependenciesBtnPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- addDependenceButton ----
            addDependenceButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addDependenceButton.setToolTipText(bundle.getString("TypeConfigPanel.addDependenceButton.toolTipText"));
            addDependenceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addDependence(e);
                }
            });
            dependenciesBtnPanel.add(addDependenceButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- deleteDependenceButton ----
            deleteDependenceButton.setToolTipText(bundle.getString("TypeConfigPanel.deleteDependenceButton.toolTipText"));
            deleteDependenceButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deleteDependenceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteDependence(e);
                }
            });
            dependenciesBtnPanel.add(deleteDependenceButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(dependenciesBtnPanel, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- implsLabel ----
        implsLabel.setText(bundle.getString("TypeConfigPanel.implsLabel.text"));
        add(implsLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(implsList);
        }
        add(scrollPane2, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== implementBtnPanel ========
        {
            implementBtnPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)implementBtnPanel.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)implementBtnPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)implementBtnPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)implementBtnPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- addImplementButton ----
            addImplementButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
            addImplementButton.setToolTipText(bundle.getString("TypeConfigPanel.addImplementButton.toolTipText"));
            addImplementButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addImplement(e);
                }
            });
            implementBtnPanel.add(addImplementButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- ignoreImplementButton ----
            ignoreImplementButton.setToolTipText(bundle.getString("TypeConfigPanel.ignoreImplementButton.toolTipText"));
            ignoreImplementButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            ignoreImplementButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ignoreImplement(e);
                }
            });
            implementBtnPanel.add(ignoreImplementButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(implementBtnPanel, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- ignoreImplsLabel ----
        ignoreImplsLabel.setText(bundle.getString("TypeConfigPanel.ignoreImplsLabel.text"));
        add(ignoreImplsLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane3 ========
        {
            scrollPane3.setViewportView(ignoreImplsList);
        }
        add(scrollPane3, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== ignoreImplsButtonPanel ========
        {
            ignoreImplsButtonPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)ignoreImplsButtonPanel.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)ignoreImplsButtonPanel.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)ignoreImplsButtonPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)ignoreImplsButtonPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- deleteIgnoreImplButton ----
            deleteIgnoreImplButton.setToolTipText(bundle.getString("TypeConfigPanel.deleteIgnoreImplButton.toolTipText"));
            deleteIgnoreImplButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
            deleteIgnoreImplButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteIgnoreImpl(e);
                }
            });
            ignoreImplsButtonPanel.add(deleteIgnoreImplButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(ignoreImplsButtonPanel, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== addDependenceDialog ========
        {
            addDependenceDialog.setLayout(new GridBagLayout());
            ((GridBagLayout)addDependenceDialog.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)addDependenceDialog.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)addDependenceDialog.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)addDependenceDialog.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- dependenceLabel ----
            dependenceLabel.setText(bundle.getString("TypeConfigPanel.dependenceLabel.text"));
            addDependenceDialog.add(dependenceLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- dependenciesComboBox ----
            dependenciesComboBox.setEditable(true);
            addDependenceDialog.add(dependenciesComboBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        }

        //======== addImplementDialog ========
        {
            addImplementDialog.setLayout(new GridBagLayout());
            ((GridBagLayout)addImplementDialog.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
            ((GridBagLayout)addImplementDialog.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)addImplementDialog.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)addImplementDialog.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- implementNameLabel ----
            implementNameLabel.setText(bundle.getString("TypeConfigPanel.implementNameLabel.text"));
            addImplementDialog.add(implementNameLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            addImplementDialog.add(implementNameTextField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- columnsLabel ----
            columnsLabel.setText(bundle.getString("TypeConfigPanel.columnsLabel.text"));
            addImplementDialog.add(columnsLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //======== panel1 ========
            {
                panel1.setLayout(new GridBagLayout());
                ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

                //======== scrollPane4 ========
                {
                    scrollPane4.setViewportView(columnsList);
                }
                panel1.add(scrollPane4, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- addColumnsButton ----
                addColumnsButton.setIcon(new ImageIcon(getClass().getResource("/icons/arrow_right.png")));
                addColumnsButton.setToolTipText(bundle.getString("TypeConfigPanel.addColumnsButton.toolTipText"));
                panel1.add(addColumnsButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPane5 ========
                {
                    scrollPane5.setViewportView(implColumnsList);
                }
                panel1.add(scrollPane5, new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- deleteColumnsButton ----
                deleteColumnsButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
                deleteColumnsButton.setToolTipText(bundle.getString("TypeConfigPanel.deleteColumnsButton.toolTipText"));
                panel1.add(deleteColumnsButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
            }
            addImplementDialog.add(panel1, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("typeCfgModel.name"),
            nameTextField, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("typeCfgModel.template"),
            templateTextField, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("typeCfgModel.target"),
            targetTextField, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("typeCfgModel.pkg"),
            packageTextField, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("typeCfgModel.suffix"),
            suffixTextField, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("typeCfgModel.dependencies"), dependenciesList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("typeCfgModel.impls"), implsList));
        bindingGroup.addBinding(SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("typeCfgModel.ignoreImpls"), ignoreImplsList));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
            this, (BeanProperty) BeanProperty.create("types"), dependenciesComboBox));
        bindingGroup.bind();
        enablementBindingGroup = new BindingGroup();
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            ignoreImplsList, ELProperty.create("${selectedElements != null}"),
            deleteIgnoreImplButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            implsList, ELProperty.create("${selectedElements != null}"),
            ignoreImplementButton, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, ELProperty.create("${typeCfgModel != null}"),
            this, BeanProperty.create("enabled")));
        enablementBindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            dependenciesList, ELProperty.create("${selectedElements != null}"),
            deleteDependenceButton, BeanProperty.create("enabled")));
        enablementBindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }
}