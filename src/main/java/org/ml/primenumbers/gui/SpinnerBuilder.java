package org.ml.primenumbers.gui;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;

/**
 * Created by Andrei on 28.11.2016.
 */
public class SpinnerBuilder {

    private Spinner spinner;
    private Label label = null;
    public SpinnerBuilder() {
        spinner = new Spinner();
    }

    public SpinnerBuilder withLabel(String label) {
        if (this.label == null) new Label(label);
        else this.label.setText(label);
        return this;
    }

    public SpinnerBuilder withId(String id) {
        spinner.setId(id);
        return this;
    }

    public Spinner appendTo(Pane pane) {
        if (label != null) pane.getChildren().add(label);
        pane.getChildren().add(spinner);
        return spinner;
    }
}
