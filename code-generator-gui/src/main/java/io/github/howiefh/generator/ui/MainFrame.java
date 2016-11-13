package io.github.howiefh.generator.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import io.github.howiefh.generator.TemplateCodeGenerator;
import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.strategy.GeneratorStrategy;
import io.github.howiefh.generator.strategy.MergeGeneratorStrategy;
import io.github.howiefh.generator.strategy.OverrideGeneratorStrategy;
import io.github.howiefh.generator.vcs.Gits;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingbinding.SwingBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * @author fenghao
 * @version 1.0
 * @since 1.0
 */
public class MainFrame extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);
    private static final long serialVersionUID = 8386739357191883218L;
    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JToolBar toolBar;
    private JButton saveConfigButton;
    private JButton generatorButton;
    private JCheckBox overrideCheckBox;
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
    private BindingGroup bindingGroup;
    // End of variables declaration  //GEN-END:variables
    private boolean override;

    /**
     * @return override
     */
    public boolean isOverride() {
        return override;
    }

    /**
     * @param override
     */
    public void setOverride(boolean override) {
        boolean oldValue = isOverride();
        this.override = override;
        firePropertyChange("override", oldValue, override);
    }

    public MainFrame() {
        setPreferredSize(new Dimension(976, 678));
        initComponents();
        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                typeConfigsPanel.getTypes(), tableConfigsPanel.getTypeConfigPanel().getDependenciesComboBox()));
        bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                typeConfigsPanel.getTypes(), tableConfigsPanel.getTableConfigPanel().getTypesComboBox()));
        bindingGroup.bind();
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

    private void generateCode(ActionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GeneratorStrategy strategy;
                    if (override) {
                        strategy = new OverrideGeneratorStrategy();
                    } else {
                        strategy = new MergeGeneratorStrategy(Gits.DEFAULT_REPO_PATH);
                    }
                    strategy.generate();
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Argument error. {}", e.getMessage());
                } catch (ConfigInitException e) {
                    LOGGER.error("Config init error. {}", e.getMessage());
                } catch (GeneratorException e) {
                    LOGGER.error("Generate error. {}", e.getMessage());
                }
            }
        }).start();
    }

    private void saveConfig(ActionEvent e) {
        Config config = Configuration.getConfig();
        File configFile = new File(TemplateCodeGenerator.DEFAULT_CONFIG);
        LOGGER.info("#saveConfig config:{}, configFile:{}", config, configFile.getAbsolutePath());

        try {
            JSON.writeJSONString(Files.newWriter(configFile, Charsets.UTF_8), config, SerializerFeature.PrettyFormat);
        } catch (FileNotFoundException e1) {
            LOGGER.warn("#saveConfig config:{}, configFile:{}, e:{}", config, configFile.getAbsolutePath(), e1);
        }
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("lang.language");
        toolBar = new JToolBar();
        saveConfigButton = new JButton();
        generatorButton = new JButton();
        overrideCheckBox = new JCheckBox();
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

            //---- saveConfigButton ----
            saveConfigButton.setText(bundle.getString("Generator.MainFrame.saveConfigButton.text"));
            saveConfigButton.setToolTipText(bundle.getString("Generator.MainFrame.saveConfigButton.toolTipText"));
            saveConfigButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveConfig(e);
                }
            });
            toolBar.add(saveConfigButton);

            //---- generatorButton ----
            generatorButton.setText(bundle.getString("Generator.MainFrame.generatorButton.text"));
            generatorButton.setToolTipText(bundle.getString("Generator.MainFrame.generatorButton.toolTipText"));
            generatorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    generateCode(e);
                }
            });
            toolBar.add(generatorButton);

            //---- overrideCheckBox ----
            overrideCheckBox.setText(bundle.getString("Generator.MainFrame.overrideCheckBox.text"));
            toolBar.add(overrideCheckBox);

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

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("override"),
            overrideCheckBox, BeanProperty.create("selected")));
        bindingGroup.bind();
        // End of component initialization  //GEN-END:initComponents
    }
}
