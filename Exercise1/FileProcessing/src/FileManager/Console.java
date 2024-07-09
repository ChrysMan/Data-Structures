package FileManager;

import java.io.IOException;
import counter.MultiCounter;

/*
 * A main class that creates all the files and searches for 
 * 20 existing keys in each file. 
 */

public class Console {

	public static void main(String[] args) throws IOException {
		Modes mode = new Modes();
		FileManager fm = new FileManager();
		FileManager fm2 = new FileManager();
		int MOcount;

		mode.InitializeAmodeFile();
		mode.InitializeBmodeFile();
		mode.InitializeCmodeFile();
		mode.InitializeDmodeFile();
		
		
		int[] keysToSearch = mode.getRandomExistingKeys();
		
		//Array search in mode A file.
		fm.OpenFile("caseA.bin");
		MOcount = 0;
		for (int i = 0; i < 20; i++) {
			MultiCounter.resetCounter(1);
			if (mode.searchArray(fm, keysToSearch[i]) == -1) {
				System.out.println("AProblem while searching for key.");
			}
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 20;
		System.out.println("-------------A organization file mode----------------");
		System.out.println("Average disc accesses while searching: "+MOcount);
		System.out.println("");
		fm.CloseFile();
		
		// Array search in mode B file.
		fm.OpenFile("caseBindexFile.bin");
		fm2.OpenFile("caseB.bin");
		MOcount = 0;
		for (int i = 0; i < 20; i++) {
			MultiCounter.resetCounter(2);
			int pos = mode.searchArray(fm, keysToSearch[i]);
			if (pos == -1) {
				System.out.println("BProblem while searching for key.");
			} else {
				byte[] ReadDataPage = new byte[128];
				fm2.ReadBlock(pos, ReadDataPage);

				if (MultiCounter.increaseCounter(2) && mode.searchKeyInPage(keysToSearch[i], ReadDataPage) == -1) {
					System.out.println("CProblem while searching for key.");
				}
			}
			MOcount += MultiCounter.getCount(2);
		}
		MOcount = MOcount / 20;
		fm.CloseFile();
		fm2.CloseFile();
		System.out.println("-------------B organization file mode----------------");
		System.out.println("Average disc accesses while searching: "+MOcount);
		System.out.println("");
		
		// Binary search in mode C file.
		fm.OpenFile("caseC.bin");
		MOcount = 0;
		for(int k = 0; k < 20; k++) {
			MultiCounter.resetCounter(1);
			int foundkey = mode.BinarySearchCmode(0, 2499, keysToSearch[k], 4, fm);
			if(foundkey == -1) {
				System.out.println("Problem while Searching for key!");
			}
			MOcount += MultiCounter.getCount(1);
		}
		MOcount = MOcount / 20;
		fm.CloseFile();
		System.out.println("-------------C organization file mode----------------");
		System.out.println("Average disc accesses while sorting: "+ MultiCounter.getCount(3));
		System.out.println("Average disc accesses while searching: "+MOcount);
		System.out.println("");
		
		
		// Binary search in mode D file.
		fm.OpenFile("caseDindexFile.bin");
		MOcount = 0;
		for(int i = 0; i < 20; i++) {
			MultiCounter.resetCounter(2);
			int foundkey = mode.BinarySearchDmode(0, 624, keysToSearch[i], 16, fm);
			if (foundkey == -1) {
				System.out.println("Problem while searching for key.");
			}
			MOcount += MultiCounter.getCount(2);
		}
		MOcount = MOcount / 20;
		fm.CloseFile();
		System.out.println("-------------D organization file mode----------------");
		System.out.println("Average disc accesses while sorting: "+ MultiCounter.getCount(4));
		System.out.println("Average disc accesses while searching: "+MOcount);
		System.out.println("");
		
	}

}