package io.github.howiefh.generator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class MainFrame extends JFrame {
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JToolBar toolBar;
    private JButton addButton;
    private JButton copyButton;
    private JButton deleteButton;
    private JButton configButton;
    private JButton generatorButton;
    private JButton aboutButton;
    private JSplitPane splitPane;
    private JScrollPane projectScrollPane;
    private JTree projectTree;
    private JPanel cardPanel;
    private JTabbedPane configTabbedPane;
    private BaseConfigPanel baseCofigPanel;
    private TypeConfigPanel typeConfigPanel;
    private TableConfigPanel tableConfigPanel;
    private JPanel generatorPanel;
    // End of variables declaration  //GEN-END:variables

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        toolBar = new JToolBar();
        addButton = new JButton();
        copyButton = new JButton();
        deleteButton = new JButton();
        configButton = new JButton();
        generatorButton = new JButton();
        aboutButton = new JButton();
        splitPane = new JSplitPane();
        projectScrollPane = new JScrollPane();
        projectTree = new JTree();
        cardPanel = new JPanel();
        configTabbedPane = new JTabbedPane();
        baseCofigPanel = new BaseConfigPanel();
        typeConfigPanel = new TypeConfigPanel();
        tableConfigPanel = new TableConfigPanel();
        generatorPanel = new JPanel();

        //======== this ========
        setMinimumSize(new Dimension(976, 678));
        setTitle(bundle.getString("Generator.MainFrame.title"));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== toolBar ========
        {

            //---- addButton ----
            addButton.setText(bundle.getString("Generator.MainFrame.addButton.text"));
            toolBar.add(addButton);

            //---- copyButton ----
            copyButton.setText(bundle.getString("Generator.MainFrame.copyButton.text"));
            toolBar.add(copyButton);

            //---- deleteButton ----
            deleteButton.setText(bundle.getString("Generator.MainFrame.deleteButton.text"));
            toolBar.add(deleteButton);
            toolBar.addSeparator();

            //---- configButton ----
            configButton.setText(bundle.getString("Generator.MainFrame.configButton.text"));
            toolBar.add(configButton);

            //---- generatorButton ----
            generatorButton.setText(bundle.getString("Generator.MainFrame.generatorButton.text"));
            toolBar.add(generatorButton);

            //---- aboutButton ----
            aboutButton.setText(bundle.getString("Generator.MainFrame.aboutButton.text"));
            toolBar.add(aboutButton);
        }
        contentPane.add(toolBar, BorderLayout.NORTH);

        //======== splitPane ========
        {
            splitPane.setDividerLocation(200);
            splitPane.setOneTouchExpandable(true);

            //======== projectScrollPane ========
            {
                projectScrollPane.setViewportView(projectTree);
            }
            splitPane.setLeftComponent(projectScrollPane);

            //======== cardPanel ========
            {
                cardPanel.setLayout(new CardLayout());

                //======== configTabbedPane ========
                {
                    configTabbedPane.addTab(bundle.getString("Generator.MainFrame.baseCofigPanel.tab.title"), baseCofigPanel);
                    configTabbedPane.addTab(bundle.getString("Generator.MainFrame.typeConfigPanel.tab.title"), typeConfigPanel);
                    configTabbedPane.addTab(bundle.getString("Generator.MainFrame.tableConfigPanel.tab.title"), tableConfigPanel);
                }
                cardPanel.add(configTabbedPane, "card1");

                //======== generatorPanel ========
                {
                    generatorPanel.setLayout(new BorderLayout());
                }
                cardPanel.add(generatorPanel, "card2");
            }
            splitPane.setRightComponent(cardPanel);
        }
        contentPane.add(splitPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // End of component initialization  //GEN-END:initComponents
    }

}
