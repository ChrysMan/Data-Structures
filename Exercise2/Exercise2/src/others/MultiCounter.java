package others;

public class MultiCounter {
	private static int[] counters = new int[10];

	public static void resetCounter(int counterIndex) {
		if (counterIndex-1 < counters.length)
			counters[counterIndex-1] = 0;
	}

	public static int getCount(int counterIndex) {
		if (counterIndex-1 < counters.length)
			return counters[counterIndex-1];
		return -1;
	}

	public static boolean increaseCounter(int counterIndex) {
		if (counterIndex-1 < counters.length)
			counters[counterIndex-1]++;
		return true;
	}

	public static boolean increaseCounter(int counterIndex, int step) {
		if (counterIndex-1 < counters.length)
			counters[counterIndex] = counters[counterIndex-1] + step;
		return true;
	}
}
