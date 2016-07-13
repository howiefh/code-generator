package io.github.howiefh.generator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class MainFrame extends JFrame {
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JToolBar toolBar;
    private JButton configButton;
    private JButton generatorButton;
    private JButton aboutButton;
    private JSplitPane splitPane;
    private JPanel projectPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton copyButton;
    private JButton saveButton;
    private JScrollPane projectScrollPane;
    private JTree projectTree;
    private JPanel cardPanel;
    private JTabbedPane configTabbedPane;
    private BaseConfigPanel baseConfigPanel;
    private JScrollPane typeScrollPane;
    private TypeConfigsPanel typeConfigsPanel;
    private JScrollPane tableScrollPane;
    private TableConfigsPanel tableConfigsPanel;
    private JPanel generatorPanel;
    // End of variables declaration  //GEN-END:variables

    public MainFrame() {
        setPreferredSize(new Dimension(976, 678));
        initComponents();
    }

    private void effectsOnClosing(WindowEvent e) {
        setMinimumSize(new Dimension(0, 0));
        // 人的感光系统能够区分多达每秒48次闪光，设置更新时间10毫秒,windows下20毫秒有些明显抖动，调成10毫秒
        int milliSecond = 10;
        // 需要渐进的缩小的次数
        int count = 450 / milliSecond;
        int height = getHeight();
        int heightPerMinus = height / count;
        int width = getWidth();
        int x = getLocation().x;
        int y = getLocation().y;
        if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            setExtendedState(JFrame.NORMAL);
            setBounds(x, y, width, height);
        }
        for (int i = 0; i < count; i++) {
            height -= heightPerMinus;
            y += heightPerMinus/2;
            setBounds(x, y, width, height);
            try {
                Thread.sleep(milliSecond);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        if (!UIManager.getLookAndFeel().getSupportsWindowDecorations()) {
            dispose();
            setUndecorated(true);
            setVisible(true);
        }
        setBounds(x, y+height/2, width, 0);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.exit(0);
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        toolBar = new JToolBar();
        configButton = new JButton();
        generatorButton = new JButton();
        aboutButton = new JButton();
        splitPane = new JSplitPane();
        projectPanel = new JPanel();
        addButton = new JButton();
        deleteButton = new JButton();
        copyButton = new JButton();
        saveButton = new JButton();
        projectScrollPane = new JScrollPane();
        projectTree = new JTree();
        cardPanel = new JPanel();
        configTabbedPane = new JTabbedPane();
        baseConfigPanel = new BaseConfigPanel();
        typeScrollPane = new JScrollPane();
        typeConfigsPanel = new TypeConfigsPanel();
        tableScrollPane = new JScrollPane();
        tableConfigsPanel = new TableConfigsPanel();
        generatorPanel = new JPanel();

        //======== this ========
        setMinimumSize(new Dimension(976, 678));
        setTitle(bundle.getString("Generator.MainFrame.title"));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("frame");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                effectsOnClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== toolBar ========
        {

            //---- configButton ----
            configButton.setText(bundle.getString("Generator.MainFrame.configButton.text"));
            configButton.setToolTipText(bundle.getString("Generator.MainFrame.configButton.toolTipText"));
            toolBar.add(configButton);

            //---- generatorButton ----
            generatorButton.setText(bundle.getString("Generator.MainFrame.generatorButton.text"));
            generatorButton.setToolTipText(bundle.getString("Generator.MainFrame.generatorButton.toolTipText"));
            toolBar.add(generatorButton);

            //---- aboutButton ----
            aboutButton.setText(bundle.getString("Generator.MainFrame.aboutButton.text"));
            aboutButton.setToolTipText(bundle.getString("Generator.MainFrame.aboutButton.toolTipText"));
            toolBar.add(aboutButton);
        }
        contentPane.add(toolBar, BorderLayout.NORTH);

        //======== splitPane ========
        {
            splitPane.setDividerLocation(200);
            splitPane.setOneTouchExpandable(true);

            //======== projectPanel ========
            {
                projectPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)projectPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
                ((GridBagLayout)projectPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)projectPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)projectPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                //---- addButton ----
                addButton.setToolTipText(bundle.getString("Generator.MainFrame.addButton.toolTipText"));
                addButton.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
                addButton.setPreferredSize(new Dimension(42, 28));
                projectPanel.add(addButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- deleteButton ----
                deleteButton.setToolTipText(bundle.getString("Generator.MainFrame.deleteButton.toolTipText"));
                deleteButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
                deleteButton.setPreferredSize(new Dimension(42, 28));
                projectPanel.add(deleteButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- copyButton ----
                copyButton.setToolTipText(bundle.getString("Generator.MainFrame.copyButton.toolTipText"));
                copyButton.setIcon(new ImageIcon(getClass().getResource("/icons/copy.png")));
                copyButton.setPreferredSize(new Dimension(42, 28));
                copyButton.setMaximumSize(new Dimension(42, 28));
                copyButton.setMinimumSize(new Dimension(42, 28));
                projectPanel.add(copyButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- saveButton ----
                saveButton.setToolTipText(bundle.getString("Generator.MainFrame.saveButton.toolTipText"));
                saveButton.setIcon(new ImageIcon(getClass().getResource("/icons/save.png")));
                saveButton.setPreferredSize(new Dimension(42, 28));
                saveButton.setMaximumSize(new Dimension(42, 28));
                saveButton.setMinimumSize(new Dimension(42, 28));
                projectPanel.add(saveButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== projectScrollPane ========
                {
                    projectScrollPane.setViewportView(projectTree);
                }
                projectPanel.add(projectScrollPane, new GridBagConstraints(0, 1, 5, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            splitPane.setLeftComponent(projectPanel);

            //======== cardPanel ========
            {
                cardPanel.setLayout(new CardLayout());

                //======== configTabbedPane ========
                {
                    configTabbedPane.addTab(bundle.getString("Generator.MainFrame.baseConfigPanel.tab.title"), baseConfigPanel);

                    //======== typeScrollPane ========
                    {
                        typeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        typeScrollPane.setViewportView(typeConfigsPanel);
                    }
                    configTabbedPane.addTab(bundle.getString("Generator.MainFrame.typeConfigsPanel.tab.title"), typeScrollPane);

                    //======== tableScrollPane ========
                    {
                        tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        tableScrollPane.setViewportView(tableConfigsPanel);
                    }
                    configTabbedPane.addTab(bundle.getString("Generator.MainFrame.tableConfigsPanel.tab.title"), tableScrollPane);
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
