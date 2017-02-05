package org.ml.primenumbers.algorithm;

/**
 * Created by Andrei on 28.11.2016.
 */
public abstract class BaseAlgorithm implements Algorithm {
    @Override
    public String toString() {
        return getName();
    }
}
