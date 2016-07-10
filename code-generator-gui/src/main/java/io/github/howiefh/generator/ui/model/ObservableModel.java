package io.github.howiefh.generator.ui.model;

import java.beans.PropertyChangeListener;

/**
 * @author fenghao, 2016/7/10
 * @version 1.0
 * @since 1.0
 */
public interface ObservableModel {
    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
