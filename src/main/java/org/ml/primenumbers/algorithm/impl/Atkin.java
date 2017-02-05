package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.Algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 05.02.2017.
 */
public class Atkin implements Algorithm {

    private static final String NAME = "Atkin2";
    int[] sieve;

    private void toggle(int k) {
        sieve[k] = sieve[k] == 1 ? 0 : 1;
    }

    @Override
    public List<Integer> execute(long count) {
        if (count > Integer.MAX_VALUE) throw new IllegalArgumentException();
        int n = (int)count;

        List<Integer> primes = new ArrayList<>();
        sieve = new int[n+1];

        /*
            Init primes
         */
        primes.add(2);
        primes.add(3);

        for (int i = 0; i <= n; i++) {
            sieve[i] = 0;
        }
        int x, y, k;

        int max = (int)Math.floor(Math.sqrt((double)n)/2);

        x = 1;
        while (x <= max) {
            int a;
            for (int i = 1; i <= 2; i++) {
                a = 4 * x * x;
                y = 1;
                while (true) {
                    k = a + y * y;
                    if (k <= n) toggle(k);
                    else break;
                    y += 2;
                }
                x++;
            }
            a = 4 * x * x;
            y = 1;
            while (true) {
                k = a + y * y;
                if (k <= n) toggle(k);
                else break;

                y += 4;

                k = a + y * y;
                if (k <= n) toggle(k);
                else break;

                y += 2;
            }
            x++;
        }


        max = (int) Math.floor(Math.sqrt(n / 3));
        x = 1;
        while (x <= max) {
            int a = 3 * x * x;
            y = 2;
            while (true) {
                k = a + y * y;
                if (k <= n) {
                    toggle(k);
                } else {
                    break;
                }
                y += 2;
                k = a + y * y;
                if (k <= n) {
                    toggle(k);
                } else {
                    break;
                }
                y += 4;
            }
            x += 2;
        }


        max = (int) Math.floor((Math.sqrt((double) (8 * n + 12)) - 6) / 4);
        y = 1;
        while (y <= max) {
            int a = -y * y;
            x = y + 1;
            while (true) {
                k = a + 3 * x * x;
                if (k <= n) {
                    toggle(k);
                } else {
                    break;
                }
                x += 2;
            }
            y++;
            a = -y * y;
            x = y + 1;
            while (true) {
                k = a + 3 * x * x;
                if (k <= n) {
                    toggle(k);
                } else {
                    break;
                }
                x += 2;
            }
            y += 2;
        }

        max = (int) Math.floor(Math.sqrt(n));
        int s, j, factor;
        for (int i = 0; i <= max; i++) {
            if (sieve[i] == 1) {
                s = i * i;
                j = s;
                factor = 2;
                for (j = s; j <= n; j += factor * s) {
                    sieve[j] = 0;
                    factor = factor == 2 ? 4 : 2;
                }
            }
        }
        for (int i = 0; i < sieve.length; i++) {
            if (sieve[i] == 1) {
                primes.add(i);
            }
        }

        return primes;

    }

    @Override
    public String getName() {
        return NAME;
    }
}
