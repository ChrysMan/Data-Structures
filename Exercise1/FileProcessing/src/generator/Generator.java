package generator;

/*
 * A class that generates a random string and an array of random integers.
 */
public class Generator {
	private static final int START_INT = 1;
	private static final int END_INT = 1000001;
	private static final int NO_OF_ELEMENTS = 10000;
	
	public String GenerateRandomStrings(int length) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
	
	public int[] GenerateRandomInts() {
		java.util.Random randomGenerator = new java.util.Random();
		int[] randomInts = randomGenerator.ints(START_INT, END_INT).distinct().limit(NO_OF_ELEMENTS).toArray();
		return randomInts;
	}
	
}
