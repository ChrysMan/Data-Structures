package others;

/*
 * A class that generates a random string and an array of random integers.
 */
public class Generator {
	
	public Generator() {
	}
	
	public int[] GenerateRandomInts(int start, int end, int numOfElements) {
		java.util.Random randomGenerator = new java.util.Random();
		int[] randomInts = randomGenerator.ints(start, end).distinct().limit(numOfElements).toArray();
		return randomInts;
	}
	
	
}
