package BST;

import others.MultiCounter;
// Binary search tree class
public class bst {
	private int data[][];
	private int root;
	private int avail;

	//data[0]=key, data[1]=left, data[2]=right, null=-1
	public bst(int N) {
		this.data = new int[N][3];
		avail = 0;
		root = -1;
		for (int i = 0; i < N; i++) {					
			data[i][2] = i + 1;
			if (i == (N - 1)) {
				data[i][2] = -1;
			}
		}
	}

	public void insert(int key) {
		inserthelp(root, key);
	}

	private int inserthelp(int rt, int key) {
		//Insert root
		if (MultiCounter.increaseCounter(1) && rt == -1) {
			root = avail;
			data[avail][0] = key;
			int availtmp = data[avail][2];
			data[avail][1] = -1;
			data[avail][2] = -1;
			avail = availtmp;

			return key;
		}
		int it = data[rt][0];
		
		//Moving on left subtree
		if (MultiCounter.increaseCounter(1) && it > key) {
			//Insert left child
			if (MultiCounter.increaseCounter(1) && data[rt][1] == -1) {
				data[rt][1] = avail;									
				data[avail][0] = key;
				int availtmp = data[avail][2];
				data[avail][1] = -1;
				data[avail][2] = -1;
				avail = availtmp;
				return key;
			}
			inserthelp(data[rt][1], key);
			
		//Moving on right subtree
		} else {
			//Insert right child
			if (MultiCounter.increaseCounter(1) && data[rt][2] == -1) {
				data[rt][2] = avail;
				data[avail][0] = key;
				int availtmp = data[avail][2];
				data[avail][1] = -1;
				data[avail][2] = -1;
				avail = availtmp;
				return key;
			}
			inserthelp(data[rt][2], key);
		}
		return rt;
	}

	public int find(int key) {
		return findhelp(root, key);
	}

	private int findhelp(int rt, int key) {
		 // bst is empty
		if (MultiCounter.increaseCounter(1) && rt == -1)      
			return -1;
		int it = data[rt][0];
		// Move to left subtree
		if (MultiCounter.increaseCounter(1) && it > key)
			return findhelp(data[rt][1], key);
		// Key found
		else if (MultiCounter.increaseCounter(1) && it == key)
			return it;
		//Move to right subtree
		else {
			return findhelp(data[rt][2], key);
		}
	}

	public void printRange(int low, int high) {
		// System.out.print("\nPrint keys between " + low + " and " + high +": ");
		printRangehelp(root, low, high);
		// System.out.println();
	}

	private void printRangehelp(int root, int low, int high) {
		// bst is empty
		if (MultiCounter.increaseCounter(1) && root == -1)		
			return;
		int it = data[root][0];
		// Move to left subtree
		if (MultiCounter.increaseCounter(1) && high < it)
			printRangehelp(data[root][1], low, high);
		// Move to right subtree
		else if (MultiCounter.increaseCounter(1) && low > it)
			printRangehelp(data[root][2], low, high);
		// Visit both subtrees to print
		else {
			printRangehelp(data[root][1], low, high);
			// System.out.print(" "+it);
			printRangehelp(data[root][2], low, high);
		}
	}

	public void inorder() {
		inorderhelp(root);
		System.out.println();
	}

	private void inorderhelp(int subroot) {
		if (subroot == -1) {
			System.out.println();
			return; // Empty, do nothing
		}
		inorderhelp(data[subroot][1]);
		visit(subroot); // Perform desired action
		inorderhelp(data[subroot][2]);
	}

	private void visit(int rt) {
		System.out.print(data[rt][0] + " ");
	}

}
