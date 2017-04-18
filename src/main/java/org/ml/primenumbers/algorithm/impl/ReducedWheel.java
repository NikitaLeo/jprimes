package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;
import org.ml.primenumbers.util.Wheel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Nikita on 05.03.2017.
 */
public abstract class ReducedWheel extends BaseAlgorithm {

    protected ArrayList<Integer> run(int limit, int[] basePrimes) {

        Wheel new_wheel = new Wheel(basePrimes);
        int primorial = new_wheel.getPrimorial();
        ArrayList<Integer>  spokes = new_wheel.getSpokes();
        ArrayList<Integer>  gaps = new_wheel.getGaps();
        int s = spokes.size();

        int N = limit + primorial - limit%primorial;
        int root = (int) Math.floor(Math.sqrt(N));
        int max = (root/primorial + 1) * s;
        int sieve_length = (N/primorial) * s;

        byte[] sieve = new byte[sieve_length];
        for (int i = 0; i < sieve_length; i++ ){
            sieve[i] = 1;
        }
        sieve[0] = 0;

        for (int i = 1; i < max; i++){
            if (sieve[i] == 1){

                int d = i%s;
                int p = primorial*(i/s) + spokes.get(d);
                int ps = p*s;

                int r = (p*p) % primorial;
                int q = (p*p) / primorial;
                int j;

                for (int count = 1; count <= s; count++) {
                    j = s*q + spokes.indexOf(r);
                    for (int k = j; k < sieve.length; k += ps) sieve[k] = 0;
                    int a = p * gaps.get(d) + r;
                    q += a / primorial;
                    r = a % primorial;
                    d++;
                    if(d == s) d = 0;
                }
            }
        }

        int d = 0, n = 1;

        ArrayList<Integer> primes = new ArrayList<>();
        for (int p : basePrimes) {
            primes.add(p);
        }
        for (byte i : sieve) {
            if (i == 1 && n <= limit) primes.add(n);
            n += gaps.get(d);
            d++;
            if (d == s) d = 0;
        }
        return primes;
    }

}
