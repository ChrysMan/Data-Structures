package BST;

import others.Generator;
import others.MultiCounter;
// Main class
public class Console {

	public static void main(String[] args) {
		int i = 0;
		int MOcount;
		int[] keysToInsert = new int[100000];
		int[] keysToFind = new int[100];
		int[] keyRange = new int[100];
		
		Generator gen = new Generator();
		keysToInsert = gen.GenerateRandomInts(1, 1000001, 100000);
		keysToFind = gen.GenerateRandomInts(1, 1000001, 100);
		keyRange = gen.GenerateRandomInts(1, 100001, 100);

		bst BST = new bst(100000);
		ThreadedBST TBST = new ThreadedBST(100000);
		SortedArray sarray = new SortedArray(100000, keysToInsert);

		// Initialization of the bst
		MOcount = 0;
		for (int y = 0; y < 100000; y++) {
			MultiCounter.resetCounter(1);
			BST.insert(keysToInsert[y]);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100000;
		System.out.println("------------A) Binary Search Tree---------------");
		System.out.println("Average number of comparisons in insertion: " + MOcount);

		// Searching for 100 random keys in bst
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			BST.find(keysToFind[i]);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while searching for key: " + MOcount);

		// Printing key ranges in bst
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			BST.printRange(keyRange[i], keyRange[i] + 100);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 100): " + MOcount);

		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			BST.printRange(keyRange[i], keyRange[i] + 1000);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 1000): " + MOcount);
		
		// Initialization of the Threaded bst
		MOcount = 0;
		for (int y = 0; y < 100000; y++) {
			MultiCounter.resetCounter(1);
			TBST.insert(keysToInsert[y]);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100000;
		System.out.println("\n------------B) Threaded Binary Search Tree------");
		System.out.println("Average number of comparisons in insertion: " + MOcount);

		// Searching for 100 random keys in Threaded bst
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			TBST.find(keysToFind[i]);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while searching for key: " + MOcount);

		// Printing key ranges in Threaded bst
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			TBST.printRange(keyRange[i], keyRange[i] + 100);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 100): " + MOcount);

		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			TBST.printRange(keyRange[i], keyRange[i] + 1000);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 1000): " + MOcount);
		
		// Searching for 100 random keys in sorted array with binary search
		MOcount = 0;
		for(i = 0; i<keysToFind.length; i++) {
			MultiCounter.resetCounter(1);
			int mid = sarray.binSearch(0, 99999, keysToFind[i]);
			/*if(keysToFind[i] == sarray.getSortedarray(mid)) {
			System.out.println("key "+ keysToFind[i]+" found:"+sarray.getSortedarray(mid));
			}
			else 
				System.out.println("Key "+keysToFind[i]+"not found");*/
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("\n------------C) Sorted Array---------------------");
		System.out.println("Average number of comparisons while searching for key: " + MOcount);
		
		// Printing key ranges in sorted array
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			sarray.printRange(0, 99999, keyRange[i],keyRange[i] + 100);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 100): " + MOcount);
	
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			sarray.printRange(0, 99999, keyRange[i],keyRange[i] + 1000);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 1000): " + MOcount);
	
		MOcount = 0;
		for (i = 0; i < 100; i++) {
			MultiCounter.resetCounter(1);
			sarray.printRange(0, 99999, keyRange[i],keyRange[i] + 2000);
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 100;
		System.out.println("Average number of comparisons while printing keys in range(K = 2000): " + MOcount);
	}

}
