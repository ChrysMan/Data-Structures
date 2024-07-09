package FileManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import counter.MultiCounter;
import node.Node;
import generator.Generator;
import node.DataPage;
import node.IndexNode;
import node.IndexPage;

/*
 * A class that initializes all the files and contains the methods for all the searches.
 */

public class Modes {
	private static final int page_size = 128;
	private static final int totalPages = 2500; // 10000 keys / 4 records = 2500 pages
	private static final int IndexFilePages = 625;
	private int[] keys;
	private Node[] nodes;
	private IndexNode[] indexNodes;
	

	private DataPage dataPage;
	private IndexPage indexPage;
	private Generator gn;
	private FileManager fm;

	public Modes() {
		gn = new Generator();
		keys = gn.GenerateRandomInts();
		fm = new FileManager();
	}

	public int[] getKeys() {
		return keys;
	}

	/*
	 * Method that creates the caseA.bin file.
	 */
	public void InitializeAmodeFile() throws IOException {
		byte[] Page;
		int i = 1;
		int j = 0;
		String filename = "caseA.bin";
		fm.CreateFile(filename);
		// fm.setNumOfBlocks(totalPages);
		fm.OpenFile(filename);

		while (i < totalPages) {
			Page = new byte[page_size];
			nodes = new Node[4];
			for (int k = 0; k < 4; k++) {
				nodes[k] = new Node(keys[j], gn.GenerateRandomStrings(28));
				j++;
			}
			dataPage = new DataPage();
			dataPage.setNodes(nodes);
			Page = dataPage.toByteArray();
			dataPage.setPos(i);
			fm.WriteBlock(dataPage.getPos(), Page);
			i++;
		}
		fm.CloseFile();
	}

	/*
	 * Method that creates the caseB.bin file and caseBindexFile.bin that contains
	 * the keys and the positions of the keys in the caseB.bin file.
	 */
	public void InitializeBmodeFile() throws IOException {
		FileManager fm2 = new FileManager();
		byte[] Page;
		byte[] keyPage;
		int i = 1;
		int x = 1;
		int j = 0;
		int y = 0;
		String filename = "caseB.bin";
		String Indexfilename = "caseBindexFile.bin";

		fm.CreateFile(filename);
		fm.setNumOfBlocks(totalPages);
		fm.OpenFile(filename);

		fm2.CreateFile(Indexfilename);
		fm2.setNumOfBlocks(IndexFilePages);
		fm2.OpenFile(Indexfilename);

		indexNodes = new IndexNode[16];
		while (i < totalPages) {
			Page = new byte[page_size];
			nodes = new Node[4];

			for (int k = 0; k < 4; k++) {
				nodes[k] = new Node(keys[j], gn.GenerateRandomStrings(28));
				indexNodes[y] = new IndexNode(keys[j], i);
				y++;
				j++;
			}
			if (i % 4 == 0) {
				keyPage = new byte[page_size];
				indexPage = new IndexPage();
				indexPage.setNodes(indexNodes);
				keyPage = indexPage.toByteArray();
				indexPage.setPos(x);
				fm2.WriteBlock(indexPage.getPos(), keyPage);
				indexNodes = new IndexNode[16];
				y = 0;
				x++;
			}
			dataPage = new DataPage();
			dataPage.setNodes(nodes);
			Page = dataPage.toByteArray();
			dataPage.setPos(i);
			fm.WriteBlock(dataPage.getPos(), Page);
			i++;
		}
		fm.CloseFile();
		fm2.CloseFile();
	}
	
	/*
	 * Method that creates 10.000 random keys.
	 */
	public int[] getRandomExistingKeys() {
		Random random = new Random();
		int[] existingkeys = new int[20];
		for (int i = 0; i < 20; i++) {
			existingkeys[i] = keys[random.nextInt(keys.length)];
		}
		return existingkeys;
	}

	/*
	 * Method that is used to search for key in the file caseA.bin and
	 * caseBindexFile.bin
	 */
	public int searchArray(FileManager filemanager, int key) throws IOException {
		byte[] ReadDataPage = new byte[128];
		int pos;
		for (int p = 1; p <= filemanager.getNumOfBlocks(); p++) {
			filemanager.setCurrentPos(p);
			filemanager.ReadNextBlock(ReadDataPage);
			if(filemanager.getFilename() == "caseA.bin") {
				MultiCounter.increaseCounter(1);
			}
			else MultiCounter.increaseCounter(2);

			ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
			DataInputStream din = new DataInputStream(bis);
			if (filemanager.getFilename() == "caseBindexFile.bin") {
				for (int b = 0; b < 32; b++) {
					if (key == din.readInt()) {
						pos = din.readInt();
						return pos;
					}
				}
			} else if (filemanager.getFilename() == "caseA.bin") {
				for (int b = 0; b < 4; b++) {
					byte[] bb = new byte[28];
					int Int = din.readInt();
					din.read(bb);
					if (key == Int) {
						pos = filemanager.getCurrentPos();
						return pos;
					}
				}
			}
			din.close();
			bis.close();
		}
		return -1;
	}

	/*
	 * Method that is used to search for key in the file caseB in the given page
	 * position
	 */
	public int searchKeyInPage(int key,byte[] ReadDataPage) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
		DataInputStream din = new DataInputStream(bis);
		for (int b = 0; b < 4; b++) {
			int Int = din.readInt();
			byte[] bb = new byte[28];
			din.read(bb);
			if (Int == key) {
				return key;
			}
		}
		din.close();
		bis.close();
		return -1;
	}

	/*
	 * Method that takes the information from a data page and converts them into 4
	 * nodes
	 */
	public Node[] FromPageToNodes(byte[] DataPage) throws IOException {
		Node[] nodes = new Node[4];
		int key;
		ByteArrayInputStream bis = new ByteArrayInputStream(DataPage);
		DataInputStream din = new DataInputStream(bis);
		for (int b = 0; b < 4; b++) {

			key = din.readInt();
			byte[] bb = new byte[28];
			din.read(bb);
			String info = new String(bb);
			nodes[b] = new Node(key, info);
		}
		din.close();
		bis.close();
		return nodes;
	}

	/*
	 * Method that takes the information from a data page and converts them into 16
	 * indexNodes
	 */
	public IndexNode[] FromPageToIndexNodes(byte[] DataPage) throws IOException {
		IndexNode[] indexNodes = new IndexNode[16];
		int key;
		int pos;
		ByteArrayInputStream bis = new ByteArrayInputStream(DataPage);
		DataInputStream din = new DataInputStream(bis);
		for (int b = 0; b < 16; b++) {
			key = din.readInt();
			pos = din.readInt();
			indexNodes[b] = new IndexNode(key, pos);
		}
		din.close();
		bis.close();
		return indexNodes;
	}

	/*
	 * Method that sorts the information of caseA.bin file into new file caseC.bin. 
	 * All the nodes from file caseA.bin are red into a new array of 10000 nodes, 
	 * sorted and then written into the new file caseC.bin 
	 */
	public void InitializeCmodeFile() throws IOException {
		byte[] Page = new byte[128];
		int i = 1;
		int j = 0;
		int num = 0;
		FileManager fm2 = new FileManager();
		String srcfilename = "caseA.bin";
		String dstfilename = "caseC.bin";
		fm.CreateFile(dstfilename);
		fm.OpenFile(dstfilename);
		fm2.OpenFile(srcfilename);

		Node[] sortedNodes = new Node[10000];
		for (int k = 0; k < 2500; k++) {
			fm2.ReadNextBlock(Page);
			MultiCounter.increaseCounter(3);
			Node[] nodes4 = new Node[4];
			nodes4 = FromPageToNodes(Page);
			for (int h = 0; h < 4; h++) {
				sortedNodes[j] = nodes4[h];
				j++;
			}
		}
		Arrays.sort(sortedNodes);
		for (int r = 0; r < 2500; r++) {
			dataPage = new DataPage();
			for (int s = 0; s < 4; s++) {
				dataPage.setNode(sortedNodes[num + s], s);
			}
			Page = dataPage.toByteArray();
			dataPage.setPos(i);
			fm.WriteBlock(dataPage.getPos(), Page);
			MultiCounter.increaseCounter(3);
			num = num + 4;
			i++;
		}
		fm.CloseFile();
		fm2.CloseFile();
	}

	/*
	 *  Method that sorts the information of caseBindexFile.bin file into new file caseDindexFile.bin.
	 */
	public void InitializeDmodeFile() throws IOException {
		byte[] Page = new byte[128];
		int i = 1;
		int j = 0;
		int s;
		int num = 0;
		String dstIndexfilename = "caseDindexFile.bin";
		String srcIndexfilename = "caseBindexFile.bin";

		FileManager fm2 = new FileManager();
		fm.CreateFile(dstIndexfilename);
		fm.OpenFile(dstIndexfilename);
		fm2.OpenFile(srcIndexfilename);

		IndexNode[] sortedIndexNodes = new IndexNode[10000];
		for (int k = 0; k < 625; k++) {
			fm2.ReadNextBlock(Page);
			MultiCounter.increaseCounter(4);
			IndexNode[] nodes16 = new IndexNode[16];
			nodes16 = FromPageToIndexNodes(Page);
			for (int h = 0; h < 16; h++) {
				sortedIndexNodes[j] = nodes16[h];
				j++;
			}
		}
		Arrays.sort(sortedIndexNodes);
		for (int r = 0; r < 625; r++) {
			indexPage = new IndexPage();
			for (s = 0; s < 16; s++) {
				indexPage.setNode(sortedIndexNodes[num + s], s);
			}
			Page = indexPage.toByteArray();
			indexPage.setPos(i);
			fm.WriteBlock(indexPage.getPos(), Page);
			MultiCounter.increaseCounter(4);
			num = num + 16;
			i++;
		}
		fm.CloseFile();
		fm2.CloseFile();
	}
	/*
	 * Method that gets the first key from a data page.	
	 */
	public int getFirstKey(byte[] ReadDataPage) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
		DataInputStream din = new DataInputStream(bis);
		int firstkey = din.readInt();
		din.close();
		bis.close();
		return firstkey;
	}
	/*
	 * Method that gets the last key from a data page.	
	 */
	public int getLastKey(byte[] ReadDataPage, int num) throws IOException {
		int lastkey = 0;
		ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
		DataInputStream din = new DataInputStream(bis);
		if(num == 16) {
			for(int i = 0; i < num; i++) {
				lastkey = din.readInt();
				din.readInt();
			}
			din.close();
			bis.close();
			return lastkey;
		}
		else {
			for(int i = 0; i < num; i++) {
				lastkey = din.readInt();
				byte[] string = new byte[28]; 
				din.read(string);
			}
			din.close();
			bis.close();
			return lastkey;
		}
	}
	/*
	 * Method that searches for a key in given data page.	
	 */
	public int searchIndexBlock(int key, byte[] ReadDataPage) throws IOException {
		int pos;
		ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
		DataInputStream din = new DataInputStream(bis);
		for (int b = 0; b < 32; b++) {
			if (key == din.readInt()) {
				pos = din.readInt();
				return pos;
			}
		}
		din.close();
		bis.close();
		return -1;
	}
	/*
	 * Method that is binary searching key in file caseC.bin
	 */
	public int BinarySearchCmode(int leftIndex, int rightIndex, int key, int num, FileManager fm) throws IOException {
		byte[] ReadDataPage = new byte[128];
		if (rightIndex >= leftIndex) {
			int mid = leftIndex + (rightIndex - leftIndex) / 2;
			fm.ReadBlock(mid, ReadDataPage);
			MultiCounter.increaseCounter(1);
		
			if(searchKeyInPage(key, ReadDataPage) == key )
				return key;
			
			if(getFirstKey(ReadDataPage) > key)
				return BinarySearchCmode(leftIndex, mid-1, key, num, fm);	
			
			if(getLastKey(ReadDataPage, num) < key)
				return BinarySearchCmode(mid+1, rightIndex, key, num, fm);
		}
			return -1;
	}
	
	/*
	 * Method that is binary searching a given key in file caseDindexFile.bin and then in file caseB.bin
	 */
	public int BinarySearchDmode(int leftIndex, int rightIndex, int key, int num, FileManager fm) throws IOException {
		byte[] ReadDataPage = new byte[128];
		if (rightIndex >= leftIndex) {
			int mid = leftIndex + (rightIndex - leftIndex) / 2;
			fm.ReadBlock(mid, ReadDataPage);
			MultiCounter.increaseCounter(2);
			
			int pos = searchIndexBlock(key, ReadDataPage);
			if(pos != -1) {
				FileManager fm2 = new FileManager();
				fm2.OpenFile("caseB.bin");
				fm2.ReadBlock(pos, ReadDataPage);
				if(searchKeyInPage(key, ReadDataPage) == key )
					return key;
				fm2.CloseFile();
			}
			
			if(getFirstKey(ReadDataPage) > key) 
				return BinarySearchDmode(leftIndex, mid-1, key, num, fm);	
			
			
			if(getLastKey(ReadDataPage, num) < key) 
				return BinarySearchDmode(mid+1, rightIndex, key, num, fm);
			
		}
			return -1;
	}
}
