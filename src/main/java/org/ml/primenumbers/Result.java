package org.ml.primenumbers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Result {
	private long count;
	private int primeCount;
	private int i = 0, j = 0;
	private int numTests, repeat;
	private long[][] series;

	public Result(long count, int numTests, int repeat) {
		this.count = count;
		this.numTests = numTests;
		this.repeat = repeat;
		series = new long[numTests][repeat];
	}
	
	public void setTime(int alg, int round, long result) {
		if (alg >= numTests || round >= repeat) return;
		series[alg][round] = result;
	}

	public long average(int testNumber) {
		double avg = 0;
		for (int i = 0; i < repeat; i++) {
			avg += series[testNumber][i] / repeat;
		}
		return Math.round(avg);
	}

	public long averageMsec(int testNumber) {
		long nano = average(testNumber);
		return Math.round(nano / 1000000);
	}

	public long getCount() {
		return count;
	}

	public int getNumTests() {
		return numTests;
	}

	public int getPrimeCount() {
		return primeCount;
	}

	public void setPrimeCount(int primeCount) {
		this.primeCount = primeCount;
	}
}
