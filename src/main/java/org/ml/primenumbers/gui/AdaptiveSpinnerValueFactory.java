package org.ml.primenumbers.gui;

import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.IntegerStringConverter;

/**
 * Created by Andrei on 30.11.2016.
 */
public class AdaptiveSpinnerValueFactory extends SpinnerValueFactory<Integer> {

    private int min;
    private int max;

    public AdaptiveSpinnerValueFactory(int min, int max, int initial) {
        setMin(min);
        setMax(max);

        setConverter(new IntegerStringConverter());

        valueProperty().addListener((o, oldValue, newValue) -> {
            if (newValue < getMin()) {
                setValue(getMin());
            } else if (newValue > getMax()) {
                setValue(getMax());
            }
        });

        setValue(initial >= min && initial <= max ? initial : min);
    }

    private int getMin() {
        return min;
    }

    private void setMin(int min) {
        this.min = min;
    }

    private int getMax() {
        return max;
    }

    private void setMax(int max) {
        this.max = max;
    }

    @Override
    public void decrement(int steps) {
        int value = getValue();
        int stepAmount = 1;
        while (value > 10 && value % 10 == 0) {
            value /= 10;
            stepAmount *= 10;
        }

        final int newValue = getValue() - steps * stepAmount;
        setValue(newValue >= min ? newValue : min);
    }

    @Override
    public void increment(int steps) {
        int value = getValue();
        int stepAmount = 1;
        while (value >= 10 && value % 10 == 0) {
            value /= 10;
            stepAmount *= 10;
        }
        final int newValue = getValue() + steps * stepAmount;
        setValue(newValue <= max ? newValue : max);
    }

}
