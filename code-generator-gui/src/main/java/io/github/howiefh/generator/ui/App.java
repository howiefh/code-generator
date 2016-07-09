package io.github.howiefh.generator.ui;

import com.alee.laf.WebLookAndFeel;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * @author fenghao, 2016/7/9
 * @version 1.0
 * @since 1.0
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        //Set the look and feel.
        try {
            Configuration.init("config.json");

            UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
            if (SystemUtils.IS_OS_WINDOWS) {
                //Windows下设置全局字体，否则weblaf会乱码,其他可供选择的字体Helvetica,Arial,sans-serif
                initGlobalFont(new Font("Helvetica", 0, 12));
            }
        } catch (ClassNotFoundException e){
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.error(e.getMessage());
        } catch (ConfigInitException e) {
            LOGGER.error(e.getMessage());
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }


    private static void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
                .hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
