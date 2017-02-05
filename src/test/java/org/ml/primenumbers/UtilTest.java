package org.ml.primenumbers;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {

	//@Test
	public void testStamp() {
		String path = "scripts/result.js";
		File f = new File(path);
		
		if (f.exists()) {
			String stamped = PrimeNumbers.stampFile(f);
			System.out.println("Stamped name:" + stamped);
			assertEquals("result_", stamped.substring(0, 7));
		} else {
			System.out.println("File does not exist:" + f.getAbsolutePath());
		}
	}
	
}
