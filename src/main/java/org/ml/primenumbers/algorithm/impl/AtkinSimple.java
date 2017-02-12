package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.Algorithm;
import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 05.02.2017.
 */
public class AtkinSimple extends BaseAlgorithm {

    private byte[] sieve;

    private void toggle(int k) {
        sieve[k] = sieve[k] == 1 ? (byte)0 : (byte)1;
    }
    
    @Override
    public List<Integer> execute(long count) {

        int n = (int) count;

        List<Integer> primes = new ArrayList<>();
        sieve = new byte[n + 1];

        primes.add(2);
        primes.add(3);

        for (int i = 0; i <= n; i++) {
            sieve[i] = 0;
        }
        int max = (int) Math.floor(Math.sqrt(n));

        for (int x = 1; x <= max; x++) {
            for (int y = 1; y <= max; y++) {
                int k = 4 * x * x + y * y;
                if (k < n && (k % 12 == 1 || k % 12 == 5)) {
                    toggle(k);
                }
                k = 3 * x * x + y * y;
                if (k < n && k % 12 == 7) {
                    toggle(k);
                }
                k = 3 * x * x - y * y;
                if (x > y && k < n && k % 12 == 11) {
                    toggle(k);
                }
            }
        }
        for (int i = 5; i < max; i++) {
            if (sieve[i] == 1) {
                for (int j = i * i; j < n; j += i * i) {
                    sieve[j] = 0;
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
        return "Atkin";
    }
}
