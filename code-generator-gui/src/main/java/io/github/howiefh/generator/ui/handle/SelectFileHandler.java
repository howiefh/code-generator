package io.github.howiefh.generator.ui.handle;

import javax.swing.*;

/**
 * @author fenghao, 2016/7/11
 * @version 1.0
 * @since 1.0
 */
public class SelectFileHandler {
    private JComponent component;

    public SelectFileHandler(JComponent component) {
        this.component = component;
    }

    public void handleSelectFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (component instanceof JTextField) {
                final JTextField textField = (JTextField)component;
                textField.setText(fileChooser.getSelectedFile().toString());
            }
        }
    }
}
