package org.ml.primenumbers;

import java.util.List;

public class Utils {

	public static <T> String join(List<T> list) {
		return join(list, ",");
	}
	public static <T> String join(List<T> list, String j) {
		StringBuffer b = new StringBuffer();
		for (Object obj : list) {
			if (b.length() > 0) b.append(j);
			if (obj != null) b.append("\"").append(obj.toString()).append("\"");
		}
		
		return b.toString();
	}
}
