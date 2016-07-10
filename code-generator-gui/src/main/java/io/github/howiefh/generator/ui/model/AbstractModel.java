package io.github.howiefh.generator.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * Abstract base class for model objects with support for bound properties.
 * Bound properties fire property change events when changed.
 * The Beans Binding library invokes {@link #addPropertyChangeListener} and
 * {@link #removePropertyChangeListener} and listens to property changes.
 */
public class AbstractModel implements ObservableModel, Serializable {

    private static final long serialVersionUID = -383058655678905296L;
    protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
}
