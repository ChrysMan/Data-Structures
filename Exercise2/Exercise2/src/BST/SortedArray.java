package BST;

import java.util.Arrays;

import others.MultiCounter;
// Sorted array class
public class SortedArray {
	private int[] sortedarray;
	
	public SortedArray(int n, int[] keys) {
		sortedarray = new int[n];
		for(int i = 0; i<n; i++) {
			sortedarray[i] = keys[i];
		}
		Arrays.sort(sortedarray);
	}
	
	public int getSortedarray(int i) {
		return sortedarray[i];
	}

	// Finding the address of the key with binary search
	public int binSearch(int leftEdge, int rightEdge, int key) {
		int mid = (leftEdge + rightEdge) / 2;
		while(MultiCounter.increaseCounter(1) && leftEdge <= rightEdge) {
			if(MultiCounter.increaseCounter(1) && sortedarray[mid] < key)
				leftEdge = mid +1;
			else if (MultiCounter.increaseCounter(1) && sortedarray[mid] == key)
				return mid;
			else
				rightEdge = mid -1;
			mid = (leftEdge + rightEdge) / 2;
		}
		return mid;
	}

	
	public int findlow(int leftEdge, int rightEdge, int low) {
		int num = binSearch(leftEdge, rightEdge, low);							// returns the nearest address of the low (always sortedarray[num] < low) if low doesn't exist. 
		if(MultiCounter.increaseCounter(1) && num != low) {
			while(MultiCounter.increaseCounter(1) && sortedarray[num] < low) {
				num++;
			}
			return num;
		}
		return num;
	}
	
	public void printRange(int leftEdge, int rightEdge, int low, int high) {
		int begin = findlow(leftEdge, rightEdge, low);
		//System.out.print("\nPrint keys in range "+low+" - "+high+": ");
		while(MultiCounter.increaseCounter(1) && sortedarray[begin] <= high) {
			//System.out.print(sortedarray[begin]+ " ");
			begin++;
		}
	}
}
