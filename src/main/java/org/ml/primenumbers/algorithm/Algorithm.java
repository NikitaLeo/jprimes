package org.ml.primenumbers.algorithm;

import java.util.List;

public interface Algorithm {
	List<Integer> execute(long count);
	String getName();
}
