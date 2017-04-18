package org.ml.primenumbers.algorithm.impl;

import java.util.List;

/**
 * Created by Nikita on 05.03.2017.
 */
public class FullWheel4 extends FullWheel {
    @Override
    public List<Integer> execute(long limit) {
        if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
        int N = (int)limit;

        return eratosthenes_wheel(N, new int[]{2, 3, 5, 7});
    }

    @Override
    public String getName() {
        return "Full Wheel 2, 3, 5, 7";
    }
}
