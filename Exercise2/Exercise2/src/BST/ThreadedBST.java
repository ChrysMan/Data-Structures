package BST;

import others.MultiCounter;
//Threaded Binary search tree class
public class ThreadedBST {
	private int data[][];
	private int root;
	private int avail;

	/* data[0]=key, data[1]=left, data[2]=leftThread, data[3]=right/avail,
	/  data[4]=rigthThread
	/  null = -1, False = -2, True = -3
	*/
	public ThreadedBST(int N) {
		this.data = new int[N][5];
		avail = 0;
		root = 0;
		for (int i = 0; i < N; i++) {
			data[i][0] = -1;
			data[i][1] = -1;
			data[i][2] = -2; 						// leftThread = False
			data[i][4] = -2; 						// rightThread = False
			data[i][3] = i + 1;
			if (i == (N - 1)) {
				data[i][3] = -1;
			}
		}
	}

	public void insert(int key) {
		inserthelp(root, key);
	}

	private int inserthelp(int rt, int key) {
		int ptr = rt;
		int par = rt;
		while (MultiCounter.increaseCounter(1) && ptr != -1) {
			par = ptr; // update parent pointer

			// Moving on left subtree
			if (MultiCounter.increaseCounter(1) && key < data[ptr][0]) {
				if (MultiCounter.increaseCounter(1) && data[ptr][2] == -3) {
					ptr = data[ptr][1];
				} else
					break;
			}

			// Moving on right subtree.
			else {
				if (MultiCounter.increaseCounter(1) && data[ptr][4] == -3) {
					ptr = data[ptr][3];
				} else
					break;
			}
		}

		// Insert root
		if (MultiCounter.increaseCounter(1) && data[root][0] == -1) {
			data[avail][0] = key;
			int availtmp = data[avail][3];
			data[avail][3] = -1;
			avail = availtmp;
		}
		// Insert left child
		else if (MultiCounter.increaseCounter(1) && key < data[par][0]) {
			data[avail][0] = key;
			int availtmp = data[avail][3];

			data[avail][3] = par;							 // Avail's right points to parent
			data[avail][1] = data[par][1];					 // Avail's left points to parent's previous left thread
			data[par][1] = avail;							 // Parent's left points to avail
			data[par][2] = -3;								 // Parent's leftThread = True

			avail = availtmp;
		//Insert right child
		} else {
			data[avail][0] = key;
			int availtmp = data[avail][3];

			data[avail][1] = par;							 // Avail's left points to parent
			data[avail][3] = data[par][3]; 					 // Avail's right points to parent's previous right thread
			data[par][3] = avail;                            // Parent's right points to avail
			data[par][4] = -3; 								 // Parent's rightThread = True

			avail = availtmp;
		}

		return key;
	}

	public int find(int key) {
		return findhelp(root, key);
	}

	private int findhelp(int rt, int key) {
		int ptr = rt;
		while (MultiCounter.increaseCounter(1) && ptr != -1) {
			int it = data[ptr][0];
			
			if (MultiCounter.increaseCounter(1) && it > key) {	
				// If there is no left child stop
				if (MultiCounter.increaseCounter(1) && data[ptr][2] == -2) {
					return ptr;
				}
				// Else move to left subtree
				ptr = data[ptr][1];
				// Key found
			} else if (MultiCounter.increaseCounter(1) && it == key)
				return ptr;
			//Move to right subtree
			else {
				// If there is no right  child stop
				if (MultiCounter.increaseCounter(1) && data[ptr][4] == -2) {
					return ptr;
				}
				// Else move to right subtree
				ptr = data[ptr][3];
			}
		}
		return -1;
	}

	private int inorderSuccessor(int ptr) {
		if (MultiCounter.increaseCounter(1) && data[ptr][4] == -2)			// If right thread has no child
			return data[ptr][3];											// return right thread
		ptr = data[ptr][3];													// update ptr
																			
		while (MultiCounter.increaseCounter(1) && data[ptr][2] == -3)		// If left thread has child 
			ptr = data[ptr][1];												// update ptr pointer with left child
		return ptr;
	}

	public void printRange(int low, int high) {
		int ptr = find(low);
		
		while ((MultiCounter.increaseCounter(1) && ptr != -1 ) && (MultiCounter.increaseCounter(1) && data[ptr][0] <= high)) {
			while (MultiCounter.increaseCounter(1) && data[ptr][0] < low)    // ptr can be smaller than low so we increase until it becomes greater
				ptr = data[ptr][3];
			// System.out.print(" "+data[ptr][0]);
			ptr = inorderSuccessor(ptr);
		}
	}

}
