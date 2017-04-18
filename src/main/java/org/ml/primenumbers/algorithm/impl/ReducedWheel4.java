package org.ml.primenumbers.algorithm.impl;

import java.util.List;

/**
 * Created by Nikita on 05.03.2017.
 */
public class ReducedWheel4 extends ReducedWheel {
    @Override
    public List<Integer> execute(long limit) {
        if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
        int N = (int)limit;

        return run(N, new int[]{2, 3, 5, 7});
    }

    @Override
    public String getName() {
        return "Reduced Wheel 2, 3, 5, 7";
    }
}
