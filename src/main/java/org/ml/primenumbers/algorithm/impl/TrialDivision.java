package org.ml.primenumbers.algorithm.impl;

import org.ml.primenumbers.algorithm.BaseAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class TrialDivision extends BaseAlgorithm {

	
	@Override
	public List<Integer> execute(long limit) {
		if (limit > Integer.MAX_VALUE) throw new IllegalArgumentException();
		int N = (int)limit;


		List<Integer> primes = new ArrayList<>();
		for (int n = 2; n <= N; n++) {
			boolean isPrime = true;
			int root = (int)Math.floor(Math.sqrt((double)n));

			for (int i = 0; i < primes.size() && primes.get(i) <= root; i++){
				if ( n % primes.get(i) == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				primes.add(n);
			}
		}
		return primes;
	}

	@Override
	public String getName() {
		return "Trial Division";
	}

}
/*
JS copy
function trial_division(N){
	var primes = [];
	for(var n = 2; n <= N; n++){
		var isPrime = true;
		var root = Math.floor(Math.sqrt(n));
		for(var i = 0; primes[i] <= root; i++){
			if(n%primes[i] == 0){
				isPrime = false;
				break;
			}
		}
		if(isPrime){
			primes.push(n)
		}
	}
}
 */
