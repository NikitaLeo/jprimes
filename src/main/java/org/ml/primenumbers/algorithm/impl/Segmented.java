package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita on 04.03.2017.
 */
public class Segmented extends BaseAlgorithm {

    List<Integer> primes = new ArrayList<>();
    private int[] next;
    private byte[] sieve;

    @Override
    public List<Integer> execute(long limit) {
        if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
        primes.clear();
        int N = (int)limit;
        int segment = (int)Math.floor(Math.sqrt(N));
        sieve = new byte[segment+1];
        next = new int[segment+1];

        eratosthenes(segment);
        for(int n = segment; n < N; n += segment) {
            int start = n, end = n + segment;
            for(int i = 1; i <= segment ; i++) sieve[i] = 1;
            int root = (int) Math.floor(Math.sqrt(end));
            for(int i = 0; primes.get(i) <= root; i++){
                int p = primes.get(i), j;
                if (p*p <= start) j = next[i]; else j = p*p - start;
                for (; j <= segment; j += p ) sieve[j] = 0;
                next[i] = j - segment;
            }
            ArrayList<Integer> segmentPrimes = new ArrayList<>();
            for(int i = 1; i < sieve.length && start+i < N; i++) {
                if(sieve[i] == 1) segmentPrimes.add(start + i);
            }
            primes.addAll(segmentPrimes);
        }


        return primes;
    }

    private void eratosthenes(int N) {
        for (int i = 2; i <= N; i++) sieve[i] = 1;
        int root = (int) Math.floor(Math.sqrt(N));
        int next_index = 0;
        for (int i = 2; i <= root; i++) {
            if (sieve[i] == 1) {
                int j = i * i;
                for (; j <= N; j += i) sieve[j] = 0;
                next[next_index++] = j - N;
            }
        }
        for (int i = 2; i < sieve.length; i++) {
            if (sieve[i] == 1) {
                primes.add(i);
            }
        }
    }

    @Override
    public String getName() {
        return "Segmenteeritud sõel";
    }
}
