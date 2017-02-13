package org.ml.primenumbers;

import java.util.ArrayList;
import java.util.List;

public class Result {
	long count;
	List<Integer> measures;
	
	public Result(long count) {
		this.count = count;
		measures = new ArrayList<>();
	}
	
	public void addTime(int msec) {
		measures.add(msec);
	}
}
