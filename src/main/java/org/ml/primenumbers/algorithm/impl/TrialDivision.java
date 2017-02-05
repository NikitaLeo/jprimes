package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class TrialDivision extends BaseAlgorithm {

	
	@Override
	public List<Integer> execute(long count) {
		if (count > Integer.MAX_VALUE) throw new IllegalArgumentException();
		int k = (int)count;

		List<Integer> primes = new ArrayList<>();
		for (int n = 2; n <= k; n++) {
			boolean isPrime = true;
			int nroot = (int)Math.floor(Math.sqrt((double)n));

			for (int i = 0; i < primes.size() && primes.get(i) <= nroot; i++){
				if ( n % primes.get(i) == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				primes.add(n);
			}
			
		}
		/*
function trial_division(k){
	var prime_numbers = []
	for(var n=2; n<=k; n++){
		var prime = true
		var nroot = Math.sqrt(n)
		for(var i=0; prime_numbers[i] <= nroot; i++){
			if(n%prime_numbers[i]==0){
				prime = false
				break;
			}
		}
		if(prime==true){
			prime_numbers.push(n)
		}
	}
	return prime_numbers;
}


		 */
		return primes;
	}

	@Override
	public String getName() {

		return "Trial Division";
	}

}
