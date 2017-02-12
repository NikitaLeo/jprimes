package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.Algorithm;
import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 05.02.2017.
 */
public class Atkin extends BaseAlgorithm {

    private static final String NAME = "Atkin2";
    private int[] sieve;

    private boolean toggle(int k) {
        if (k < sieve.length) {
            sieve[k] = sieve[k] == 1 ? 0 : 1;
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> execute(long count) {
        if (count > Integer.MAX_VALUE) throw new IllegalArgumentException();
        int n = (int)count;

        List<Integer> primes = new ArrayList<>();
        sieve = new int[n+1];


        primes.add(2);
        primes.add(3);

        for (int i = 0; i <= n; i++) {
            sieve[i] = 0;
        }
        int x, y, k;

        int max_x = (int)Math.floor( Math.sqrt(n) / 2 );
        int max_y = (int)Math.floor( Math.sqrt(n) );
        System.out.println("max_y = " + max_y);
        int max = 0;
        /**
         *
         * Find all pairs x, y where
         * 4x*x + y*y = N
         * and N mod 12 = 1 or 5
         *
         */
        x = 1;
        while (x <= max_x) {
            int a = 4 * x * x;

            if (x % 3 == 0) {
                for (y = 1; toggle(a + y * y); y += 6) ;
                for (y = 5; toggle(a + y * y); y += 6) ;

            } else {
                for (y = 1; toggle(a + y * y); y += 2) ;
            }
            x++;
        }


        max_x = (int) Math.floor( Math.sqrt(n / 3) );

        /**
         * Find all pairs (x,y) such that n mod 12 = 7 where n = 3x^2 + y^2
         */
        for (x = 1; x <= max_x; x += 2) {
            int a = 3 * x * x;

            for (y = 2; toggle(a + y * y); y += 6) ;
            for (y = 4; toggle(a + y * y); y += 6) ;

        }

        /**
         * Find all pairs (x,y) such that n mod 12 = 11 and x > y where n = 3x^2 - y^2
         */
        max_x = (int) Math.floor( (Math.sqrt(8 * n + 12) - 2) / 4 );
        for (x = 1; x <= max_x; x++){
            int a = 3 * x * x;
            if (x % 2 == 0) {
                for (y = 1; y < x ; y += 6) toggle(a - y * y);
                for (y = 5; y < x ; y += 6) toggle(a - y * y);

            } else {
                for (y = 2; y < x ; y += 6) toggle(a - y * y);
                for (y = 4; y < x ; y += 6) toggle(a - y * y);
            }
        }


        /**
         * For all numbers n which can be divided by some square number set sieve[n] = 0
         */
        max_x = (int) Math.floor(Math.sqrt(n));
        int s, j, factor;
        for (int i = 0; i <= max_x; i++) {
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
