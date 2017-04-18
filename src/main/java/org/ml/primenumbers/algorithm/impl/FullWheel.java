package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;
import org.ml.primenumbers.util.Wheel;

import java.util.ArrayList;

/**
 * Created by Nikita on 05.03.2017.
 */
public abstract class FullWheel extends BaseAlgorithm {

    protected ArrayList<Integer> eratosthenes_wheel(int N, int[] basePrimes){

        Wheel new_wheel = new Wheel(basePrimes);
        int p = new_wheel.getPrimorial();
        ArrayList<Integer> spokes = new_wheel.getSpokes();
        ArrayList<Integer> gaps = new_wheel.getGaps();

        byte[] sieve = new byte[N+1];
        ArrayList<Integer> primes = new ArrayList<>();
        int root = (int) Math.floor(Math.sqrt(N));

        for (int i = 0; i <= N; i++) sieve[i] = 0;

        for (int anArr : basePrimes) {
            sieve[anArr] = 1;
        }

        int d = 0;
        int l = gaps.size();
        for (int n = 1 + gaps.get(0); n <= N; n += gaps.get(d)) {
            sieve[n] = 1;
            d++;
            if (d == l) d = 0;
        }

        for (int i = 1 + gaps.get(0); i <= root; i++){
            if(sieve[i] == 1){
                d = spokes.indexOf(i%p) - 1;
                for (int j = i*i; j <= N; j += gaps.get(d)*i){
                    sieve[j] = 0;
                    d++;
                    if(d == l) d = 0;
                }
            }
        }

        for (int i = 0; i < sieve.length; i++){
            if (sieve[i] == 1) primes.add(i);
        }
        return primes;
    }
}
