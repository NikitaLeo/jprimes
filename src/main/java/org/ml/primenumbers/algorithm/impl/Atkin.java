package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 05.02.2017.
 */
public class Atkin extends BaseAlgorithm {

    private static final String NAME = "Atkini sõel";
    private byte[] sieve;

    private boolean toggle(int k) {
        if (k < sieve.length) {
            sieve[k] = sieve[k] == 1 ? (byte)0 : 1;
            return true;
        }
        return false;
    }

    @Override
    public int execute(long limit) {
        if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
        int N = (int)limit;

        List<Integer> primes = new ArrayList<>();
        sieve = new byte[N+1];


        primes.add(2);
        primes.add(3);

        for (int i = 0; i <= N; i++) {
            sieve[i] = 0;
        }
        int x, y, a, b, d;

        /*
         * Find all pairs (x,y) such that n mod 12 = 1 or 5 where n = 4x^2 + y^2
         */
        int max_x = (int)Math.floor( Math.sqrt(N) / 2 );
        for(x = 1; x <= max_x; x++){
            a = 4*x*x;
            d = 2;
            for(y = 1; true; y += d){
                b = a + y*y;
                if(b <= N) toggle(b); else break;
                if(x%3 == 0) d = d == 2 ? 4 : 2;
            }
        }

        /*
         * Find all pairs (x,y) such that n mod 12 = 7 where n = 3x^2 + y^2
         */
        max_x = (int) Math.floor( Math.sqrt(N / 3) );
        for(x = 1; x <= max_x; x += 2){
            a = 3*x*x;
            d = 4;
            for(y = 2; true; y += d){
                b = a + y*y;
                if(b <= N) toggle(b); else break;
                d = d == 2 ? 4 : 2;
            }
        }

        /*
         * Find all pairs (x,y) such that n mod 12 = 11 and x > y where n = 3x^2 - y^2
         */
        int max_y = (int) Math.floor( (Math.sqrt(8 * N + 12) - 6) / 4 );
        d = 2;
        for(y = 1; y <= max_y; y += d){
            a = -y*y;
            for(x = y + 1; true; x += 2){
                b = a + 3*x*x;
                if(b <= N) toggle(b); else break;
            }
            d = d == 2 ? 1 : 2;
        }

        /*
         * For all numbers n which can be divided by some square number set sieve[n] = 0
         */
        int max = (int) Math.floor(Math.sqrt(N));
        for(int i = 0; i <= max; i++){
            if(sieve[i] == 1){
                int s = i*i;
                d = 2;
                for(int j = s; j <= N; j += d*s){
                    sieve[j] = 0;
                    d = d == 2 ? 4 : 2;
                }
            }
        }

        for (int i = 0; i < sieve.length; i++) {
            if (sieve[i] == 1) {
                primes.add(i);
            }
        }

        return primes.size();

    }

    @Override
    public String getName() {
        return NAME;
    }
}
