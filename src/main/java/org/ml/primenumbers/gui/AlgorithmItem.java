package org.ml.primenumbers.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.ml.primenumbers.algorithm.Algorithm;

/**
 * Created by Andrei on 28.11.2016.
 */
public class AlgorithmItem {
    private SimpleBooleanProperty selected = new SimpleBooleanProperty();
    private Algorithm algorithm;

    public AlgorithmItem(Algorithm algorithm) {
        this.algorithm = algorithm;
        selected.set(true);
    }

    public final BooleanProperty onProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public String toString() {
        return algorithm.getName();
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
