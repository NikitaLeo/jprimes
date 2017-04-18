package org.ml.primenumbers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.ml.primenumbers.algorithm.Algorithm;
import org.ml.primenumbers.algorithm.impl.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	//private static List<Integer> correct = Arrays.asList(2,3,5,7,11,13,17,19);
	private static final int TEST_NUMBER = 100000;
	private static final int MUST_FOUND = 9592;
	
	public AppTest() {

	}
	

	private void testAlgorithm(Algorithm a) {
		List<Integer> primes = a.execute(TEST_NUMBER);
		
		System.out.println("\n" + a.getName() + " =====> " + primes.size());

		//primes.stream().forEach(p -> System.out.print(p + ", "));

		assertEquals(MUST_FOUND, primes.size());
		/*
		assertEquals(correct.size(), primes.size());
		
		for (int i = 0; i < correct.size(); i++) {

			assertEquals(correct.get(i), primes.get(i));
		}
		*/
	}

	@Test
	public void testAll() {
		Class<?>[] all = {
				/*
				TrialDivision.class,
				Atkin.class,
				Eratosthenes.class,
				Segmented.class,*/
				FullWheel3.class,
				ReducedWheel3.class
		};

		for (Class<?> clazz : all) {
			try {
				Object obj = clazz.newInstance();
				if (obj instanceof Algorithm) {
					Algorithm a = (Algorithm) obj;
					testAlgorithm(a);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
