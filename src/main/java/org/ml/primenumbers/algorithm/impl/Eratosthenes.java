package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Eratosthenes extends BaseAlgorithm {

	private static final String NAME = "Eratosthenese sõel";
	
	public int execute(long limit) {
		if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
		int N = (int)limit;
		int root = (int)Math.floor(Math.sqrt((double)N));
		List<Integer> primes = new ArrayList<>();
		byte[] sieve = new byte[N+1];

		for (int i = 2; i <= N; i++) sieve[i] = 1;

		for (int i = 2; i <= root; i++) {
			if (sieve[i] == 1) {
				for (int j = i*i; j <= N; j += i) {
					sieve[j] = 0;
				}
			}
		}
		for (int i = 0; i < sieve.length; i++){
			if (sieve[i] == 1){
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
/*
JS copy
function eratosthenes(N){
	var sieve = [];
	var primes = [];
	for(var i = 2; i <= N; i++) sieve[i] = 1;
	var root = Math.floor(Math.sqrt(N));
	for(var i = 2; i <= root; i++){
		if(sieve[i] == 1){
			for(var j = i*i; j <= N; j += i) sieve[j] = 0;
	    }
    }
	for(var i = 0; i < sieve.length; i++){
		if(sieve[i] == 1) primes.push(i);
	}
}
 */
