package org.ml.primenumbers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

import org.ml.primenumbers.algorithm.Algorithm;
import org.ml.primenumbers.gui.FxApp;
import javafx.application.*;
import org.ml.primenumbers.util.Notifier;

public class PrimeNumbers implements Runnable {
	
	private long interval;
	private long maximum;
	private int repeat;
	private List<Result> results;
	private List<Algorithm> algorithms;
	private DoubleConsumer progress = null;
	private Notifier onFinish = null;
	private boolean stopAsap = false;
	private boolean done = false;
	
    public PrimeNumbers(long interval, long maximum, int repeat) {
		super();
		this.interval = interval;
		this.maximum = maximum;
		this.repeat = repeat;
		algorithms = new ArrayList<>();
	}

	public void addAlgorithm(Algorithm a) {
    	algorithms.add(a);
    }

    public void interrupt() {
    	stopAsap = true;
	}

    public void run() {
    	done = false;
    	stopAsap = false;
    	results = new ArrayList<>();
    	int numIterations = (int) ((maximum / interval) * repeat * algorithms.size());
    	int curIteration = 1;
    	long count = (int) interval;
    	for (; count <= maximum; count += interval) {
    		Result result = new Result(count, algorithms.size(), repeat);
    		//System.out.println(count);
			for (int round = 0; round < repeat; round++) {
        		long diff;
        		int a = 0;
				for (Algorithm algorithm : algorithms) {
        			long start = System.nanoTime();
        			List<Integer> primes = algorithm.execute(count);
        			long after = System.nanoTime();
        			diff = (after - start);
					notifyProgress(curIteration, numIterations);
        			curIteration += 1;
        			result.setTime(a, round, diff);
        			result.setPrimeCount(primes.size());
        			if (stopAsap) {
        				return;
					}
					a++;
        		}

        	}
    		
    		results.add(result);
    	}
    	done = true;
    	//onFinish.call();
    }

    private void notifyProgress(int i, int outOf) {
    	if (progress != null) {
    		progress.accept((double)i / (double)outOf);
		}
	}

	public boolean isDone() {
		return done;
	}

	public void setProgress(DoubleConsumer p) {
    	this.progress = p;
	}

    public void setOnFinish(Notifier onFinish) {
        this.onFinish = onFinish;
    }

    public String exportToJS() {
    	StringBuffer w = new StringBuffer();
    	w.append("var result = [\n");
		
    	// Columns
    	w.append("[\"Limit\",");
		List<String> names = algorithms.stream().map(a -> a.getName()).collect(Collectors.toList());
		w.append(Utils.join(names, ","));
		
		w.append("]");
		
		// Results
    	for (Result r : results) {
    		w.append(",");
    		w.append("\n[" + r.getCount() + "");
    		for (int a = 0; a < r.getNumTests(); a++) {
    			w.append("," + r.averageMsec(a));
    		}
    		w.append("]");
    	}
    	w.append("]");
    	return w.toString();
    }

	public void printResults() {
		StringBuffer w = new StringBuffer();

		// Results
		for (Result r : results) {
			w.append("Limit = ");
			w.append(r.getCount());
			w.append(", Found = ").append(r.getPrimeCount());
			for (int a = 0; a < r.getNumTests(); a++) {
				w.append(", Time = " + r.averageMsec(a) + " msec");
			}
		}
		System.out.println(w.toString());
	}

	public static void main( String[] args ){
		
		String usage = "Usage:\n"
				+ "app <interval> <max> <repeat> <output> <algorithms...>";
		
		if (args.length > 0 && args[0].equals("-gui")) {
			
			Application.launch(FxApp.class, args);
			return;
		}
		
		if (args.length < 4) {
			System.out.println("Number of parameters: " + args.length);
			System.out.println(usage);
			System.exit(1);
		}
		int arg1 = Integer.valueOf(args[0]);
		int arg2 = Integer.valueOf(args[1]);
		int arg3 = Integer.valueOf(args[2]);

		PrimeNumbers app = new PrimeNumbers(arg1, arg2, arg3);
		
		//ClassLoader loader = app.getClass().getClassLoader();
		for (int i = 4; i < args.length; i++) {
			String className = args[i];
			try {
				//Class<?> clazz = loader.loadClass(className);
				Class<?> clazz = Class.forName("org.ml.primenumbers.algorithm.impl." + className);
				Object obj = clazz.newInstance();
				if (obj instanceof Algorithm) {
					app.addAlgorithm((Algorithm)obj);
				} else {
					System.err.println("Class " + className + " is not instance of Algorithm");
				}
			} catch (Exception e) {
				System.err.println("Cannot load class " + className);
				e.printStackTrace(System.out);
			}
		}
		
		app.run();
		app.printResults();
		
		try {
			if (args.length > 4) {
				String outputFileName = args[4];
				saveResults(outputFileName, app);
			}

		} catch (IOException e) {
			System.err.println("Failed to output results");		
		}
		

    }

    public static void saveResults(String outputFileName, PrimeNumbers app) throws IOException {
        // Rename existing file
        File file = new File(outputFileName);
        if (file.exists() && file.isFile()) {
            file.renameTo(new File(""));
        }

        Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
        fileWriter.write(app.exportToJS());
        fileWriter.flush();
        fileWriter.close();
    }


	public static String stampFile(File old) {
		String name = old.getName();
		String base = "", tail = "";
		int pos = name.lastIndexOf('.');
		if (pos > 0) {
			base = name.substring(0, pos);
			tail = name.substring(pos);
		} else {
			base = name;
		}
		long lastModified = old.lastModified();
		
		SimpleDateFormat stampFormatter = new SimpleDateFormat("_yyyyMMdd_HHmmss");
		String stamp = stampFormatter.format(new Date(lastModified));
		
		return base + stamp + tail;
	}
}
