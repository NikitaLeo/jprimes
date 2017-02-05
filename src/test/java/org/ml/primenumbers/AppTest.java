package org.ml.primenumbers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.ml.primenumbers.algorithm.Algorithm;
import org.ml.primenumbers.algorithm.impl.Eratosthenes;
import org.ml.primenumbers.algorithm.impl.TrialDivision;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	private static List<Integer> correct = Arrays.asList(2,3,5,7,11,13,17,19);
	private static int TEST_NUMBER = 20;
	
	public AppTest() {

	}
	
	@Test
	public void testEratosthenes() {
		testAlgorithm(new Eratosthenes());
		
	}
	
	@Test
	public void testTrialDivision() {
		testAlgorithm(new TrialDivision());
	}

	private void testAlgorithm(Algorithm a) {
		List<Integer> primes = a.execute(TEST_NUMBER);
		
		System.out.println(a.getName() + " =====> " + primes.toString());
		
		assertEquals(correct.size(), primes.size());
		
		for (int i = 0; i < correct.size(); i++) {

			assertEquals(correct.get(i), primes.get(i));
		}
	}
	

}
