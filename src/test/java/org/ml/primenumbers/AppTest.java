package org.ml.primenumbers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.ml.primenumbers.algorithm.Algorithm;
import org.ml.primenumbers.algorithm.impl.Atkin;
import org.ml.primenumbers.algorithm.impl.Eratosthenes;
import org.ml.primenumbers.algorithm.impl.Segmented;
import org.ml.primenumbers.algorithm.impl.TrialDivision;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	//private static List<Integer> correct = Arrays.asList(2,3,5,7,11,13,17,19);
	private static final int TEST_NUMBER = 100000;
	private static final int MUST_FOUND = 9592;
	
	public AppTest() {

	}
	
	@Test
	public void testEratosthenes() {
		testAlgorithm(new Eratosthenes());
		
	}

	@Test
	public void testSegmented() {
		testAlgorithm(new Segmented());
	}

	@Test
	public void testTrialDivision() {
		testAlgorithm(new TrialDivision());
	}


	@Test
	public void testAtkins() {
		testAlgorithm(new Atkin());
	}

	private void testAlgorithm(Algorithm a) {
		int primes = a.execute(TEST_NUMBER);
		
		System.out.println("\n" + a.getName() + " =====> " + primes);

		//primes.stream().forEach(p -> System.out.print(p + ", "));

		assertEquals(MUST_FOUND, primes);
		/*
		assertEquals(correct.size(), primes.size());
		
		for (int i = 0; i < correct.size(); i++) {

			assertEquals(correct.get(i), primes.get(i));
		}
		*/
	}
	

}
