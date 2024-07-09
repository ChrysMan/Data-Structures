package FileManager;

import java.io.*;
import java.io.IOException;

public class FileManager {
	private static final int page_size = 128;
	private String filename;
	private int numOfBlocks;
	private int currentPos;
	private RandomAccessFile MyFile;
	
	
	public FileManager() {
	}
	
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	public void setNumOfBlocks(int numOfBlocks) {
		this.numOfBlocks = numOfBlocks;
	}

	public int getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(int currentPos) {
		this.currentPos = currentPos;
	}

	public String getFilename() {
		return filename;
	}
	
	public RandomAccessFile getMyFile() {
		return MyFile;
	}
	
	public int CreateFile(String name){
		try {
			this.filename = name;
			this.numOfBlocks = 0;
			this.currentPos = 1;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bos);
			out.writeInt(this.numOfBlocks); 								// Write numOfBlocks=0 (4 bytes) in the beginning of the page

			byte[] src = filename.getBytes(); 								// Encoding the filename into a sequence of bytes
			byte dst[] = new byte[50];										// Storing the result into a new byte array
			System.arraycopy(src, 0, dst, 0, src.length); 					// Copy contents of string filename in a fixed size array
			out.write(dst, 0, 50); 											// Write fixed array string (50 bytes)
			out.close();

			byte[] buffer = bos.toByteArray(); 								// Creates a newly allocated byte array
			byte[] WriteDataPage = new byte[page_size]; 					// Copy serialized data to a DataPage
			System.arraycopy(buffer, 0, WriteDataPage, 0, buffer.length);   // Copy buffer data to DataPage of page_size
			bos.close();

			// Creation of the file
			this.MyFile = new RandomAccessFile(filename, "rw");
			MyFile.seek(0);
			MyFile.write(WriteDataPage);

		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int OpenFile(String name) {
		this.filename = name;
		try {
			this.MyFile = new RandomAccessFile(filename, "rw");

			byte[] ReadDataPage = new byte[page_size];
			MyFile.seek(0);
			MyFile.read(ReadDataPage);
			ByteArrayInputStream bis = new ByteArrayInputStream(ReadDataPage);
			DataInputStream din = new DataInputStream(bis);
			this.numOfBlocks = din.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File not found");
			return 0;
		}
		return numOfBlocks;
	}

	public int ReadBlock(int PagePos, byte[] buffer) {
		try {
			if (buffer.length == 128) {
				int position = (PagePos - 1) * 128;

				MyFile.seek(position);
				MyFile.read(buffer);

				this.currentPos = PagePos;
			} else {
				System.out.println("Buffer must be 128 bytes long.");
				return 0;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int ReadNextBlock(byte[] buffer) {
		try {
			if (buffer.length == 128) {
				int position = this.currentPos * 128;

				MyFile.seek(position);
				MyFile.read(buffer);

				this.currentPos += 1;
			} else {
				System.out.println("Buffer must be 128 bytes long.");
				return 0;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int ReadPreviousBlock(byte[] buffer) {
		try {
			if (buffer.length == 128) {
				int position = (this.currentPos - 1) * 128;

				MyFile.seek(position);
				MyFile.read(buffer);
			} else {
				System.out.println("Buffer must be 128 bytes long.");
				return 0;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int WriteBlock(int PagePos, byte[] buffer) {
		try {
			if (buffer.length == 128) {
				if (PagePos > this.numOfBlocks) {
					this.numOfBlocks += 1;
					MyFile.seek(0);
					MyFile.writeInt(numOfBlocks);
				}
				byte[] WriteDataPage = new byte[page_size]; // Copy serialized data to a DataPage
				System.arraycopy(buffer, 0, WriteDataPage, 0, buffer.length); // Copy buffer data to DataPage of
																				// page_size

				int position = (PagePos - 1) * 128;
				MyFile.seek(position);
				MyFile.write(WriteDataPage);

				this.currentPos = PagePos;
			} else {
				System.out.println("Buffer must be 128 bytes long.");
				return 0;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int WriteNextBlock(byte[] buffer) {
		try {
			if (buffer.length == 128) {
				if (this.currentPos > this.numOfBlocks) {
					this.numOfBlocks += 1;
					MyFile.seek(0);
					MyFile.writeInt(numOfBlocks);
				}
				byte[] WriteDataPage = new byte[page_size]; // Copy serialized data to a DataPage
				System.arraycopy(buffer, 0, WriteDataPage, 0, buffer.length); // Copy buffer data to DataPage of
																				// page_size

				int position = this.currentPos * 128;
				MyFile.seek(position);
				MyFile.write(WriteDataPage);

				this.currentPos += 1;
			} else {
				System.out.println("Buffer must be 128 bytes long.");
				return 0;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int AppendBlock(byte[] buffer) {
		try {
			if (buffer.length == 128) {
				this.currentPos = this.numOfBlocks + 1;

				this.numOfBlocks += 1;
				MyFile.seek(0);
				MyFile.writeInt(numOfBlocks);

				byte[] WriteDataPage = new byte[page_size]; 					// Copy serialized data to a DataPage
				System.arraycopy(buffer, 0, WriteDataPage, 0, buffer.length);	// Copy buffer data to DataPage of
																				// page_size

				int position = this.currentPos * 128;
				MyFile.seek(position);
				MyFile.write(WriteDataPage);

				this.currentPos += 1;
			} else {
				System.out.println("Buffer must be 128 bytes long.");
				return 0;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int DeleteBlock(int PagePos) {
		try {
			int lastBlockPos = (this.numOfBlocks) * 128;
			int pagePos = (PagePos - 1) * 128;

			this.numOfBlocks -= 1;
			MyFile.seek(0);
			MyFile.writeInt(numOfBlocks);

			MyFile.seek(lastBlockPos);
			byte[] buffer = new byte[page_size];
			MyFile.read(buffer);

			MyFile.seek(pagePos);
			MyFile.write(buffer);

			this.currentPos = PagePos + 1;
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int CloseFile() {
		try {
			MyFile.seek(0);
			MyFile.writeInt(numOfBlocks);
			MyFile.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}
}
	
