package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Eratosthenes extends BaseAlgorithm {

	private static final String NAME = "Eratosthenes";
	
	public List<Integer> execute(long count) {
		if (count > Integer.MAX_VALUE) throw new IllegalArgumentException();
		int m = (int)count;
		int mroot = (int)Math.floor(Math.sqrt((double)m));
		
		List<Integer> primes = new ArrayList<>();
		int[] sieve = new int[m+1];
		// Initialise sieve
		for (int i = 2; i <= m; i++) sieve[i] = 1;

		for (int i = 2; i <= mroot; i++) {
			if (sieve[i] == 1) {
				for (int j = i*i; j <= m; j += i) {
					sieve[j] = 0;
				}
			}
		}
		for (int i = 0; i < sieve.length; i++){
			if (sieve[i] == 1){
				primes.add(i);
			} 
		}
		/* JS copy
	var sieve = [];
	var primes = [];
	for(var i=2; i<=m; i++){
		sieve[i] = 1;	
	}
	var mroot = Math.sqrt(m)
	for(var i=2; i<=mroot; i++){
		if(sieve[i]==1){
			for(var j=i*i; j<=m; j+=i){
				sieve[j] = 0;
			}
	    }
    }
	for(var i=0; i<sieve.length; i++){
		if(sieve[i]==1){
			primes.push(i)
		} 
	}
		 * 
		 */
		
		return primes;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
