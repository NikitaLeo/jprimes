package org.ml.primenumbers.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nikita on 05.03.2017.
 */
public class Wheel {
    private int primorial = 1;
    private ArrayList<Integer> spokes = new ArrayList<>();
    private ArrayList<Integer> gaps = new ArrayList<>();

    public Wheel(int[] basePrimes) {

        //arr is an array of primes we use in wheel
        //calculate primorial
        primorial = 1;
        for (int i = 0; i < basePrimes.length; i++) primorial *= basePrimes[i];
        //find spokes
        int[] sieve = new int[primorial];
        Arrays.fill(sieve, 1);

        for (int i = 0; i < basePrimes.length; i++){
            int prime = basePrimes[i];
            for(int j = prime; j < primorial; j += prime) sieve[j] = 0;
        }

        for (int i = 1; i < sieve.length; i++){
            if (sieve[i] == 1) spokes.add(i);
        }

        //calculate gaps
        int s = 1;
        for(int i = 2; i < primorial; i++){
            if(sieve[i] == 1){
                gaps.add(i - s);
                s = i;
            }
        }
        gaps.add(2);

    }

    public int getPrimorial() {
        return primorial;
    }

    public void setPrimorial(int primorial) {
        this.primorial = primorial;
    }

    public ArrayList<Integer> getSpokes() {
        return spokes;
    }

    public void setSpokes(ArrayList<Integer> spokes) {
        this.spokes = spokes;
    }

    public ArrayList<Integer> getGaps() {
        return gaps;
    }

    public void setGaps(ArrayList<Integer> gaps) {
        this.gaps = gaps;
    }
}
