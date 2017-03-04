package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Nikita on 04.03.2017.
 */
public class Segmented extends BaseAlgorithm {

    List<Integer> primes = new ArrayList<>();
    private int[] next;
    private byte[] sieve;

    @Override
    public int execute(long limit) {
        if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
        primes.clear();
        int N = (int)limit;
        int segment = (int)Math.ceil(Math.sqrt(N));
        sieve = new byte[segment+1];
        next = new int[segment+1];

        long seg1 = 0, seg2 = 0, seg3 = 0, seg4 = 0;
        long time = 0;
        int LENGTH = sieve.length;

        eratosthenes(segment);
        int[] segmentPrimes = new int[segment+1];
        int primesCount = primes.size();

        for (int n = segment; n < N; n += segment) {

            // SEGMENT 1
            time = System.currentTimeMillis();

            int start = n, end = n + segment;
            for(int i = 1; i <= segment ; i++) sieve[i] = 1;
            int root = (int) Math.floor(Math.sqrt(end));

            seg1 += System.currentTimeMillis() - time;

            // SEGMENT 2
            time = System.currentTimeMillis();

            for (int i = 0; i < primes.size() && primes.get(i) <= root; i++){
                int p = primes.get(i), j;
                if (p*p <= start) j = next[i]; else j = p*p - start;
                for (; j <= segment; j += p ) sieve[j] = 0;
                next[i] = j - segment;
            }

            seg2 += System.currentTimeMillis() - time;

            // SEGMENT 3
            time = System.currentTimeMillis();

            for (int i = 1; i < LENGTH && start+i < N; i++) {
                if (sieve[i] == 1) primesCount += 1;
            }
            seg3 += System.currentTimeMillis() - time;
        }

        System.out.println("segment 1 :" + seg1);
        System.out.println("segment 2 :" + seg2);
        System.out.println("segment 3 :" + seg3);
        System.out.println("segment 4 :" + seg4);

        return primesCount;
    }

    /**
     * Finds primes in first segment
     * @param N
     */
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
