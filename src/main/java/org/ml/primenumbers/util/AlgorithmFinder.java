package org.ml.primenumbers.util;

import org.ml.primenumbers.algorithm.Algorithm;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Andrei on 28.11.2016.
 */
public class AlgorithmFinder implements Runnable {

    private final List<Algorithm> algorithms = new ArrayList<>();
    private final Notifier callback;

    public AlgorithmFinder(Notifier callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage("org.ml.primenumbers"))
                .setScanners(new SubTypesScanner())
                .filterInputsBy(new FilterBuilder().includePackage("org.ml.primenumbers"))
        );
        Set<Class<? extends Algorithm>> classes = reflections.getSubTypesOf(Algorithm.class);
        for (Class<? extends Algorithm> clazz : classes) {
            try {
                Algorithm obj = clazz.newInstance();
                algorithms.add(obj);
            } catch (Exception e) {
                // skip

            }
        }

        callback.call();
    }

    public List<Algorithm> getList() {
        return algorithms;
    }
}
