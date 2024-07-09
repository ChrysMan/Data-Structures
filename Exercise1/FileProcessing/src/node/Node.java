package node;
import java.io.*;
import java.nio.ByteBuffer;
/*
 * A class implementing interface ComparableNode.
 * Implements the compareTo method of the Comparable interface that ComparableNode extends 
 * A Node is 32 bytes long and contains a 4 bytes key and a 28 bytes information.
 */
public class Node implements ComparableNode{
	private static final int rec_size = 32;
	private int key;
	private String info;
	
	public Node() {
	}
	
	public Node (int key, String info) {
		this.key = key;
		this.info = info;
	}
	
	public int getKey() {
		return key;
	}
	
	public int setKey(int key) {
		return this.key=key;
	}
	
	public String getInfo() {
		return info;
	}
	
	public String setInfo(String info) {
		return this.info = info;
	}
	
	public Node (byte[] byteArray) throws IOException{
		if(byteArray.length == rec_size) {
			ByteArrayInputStream bis= new ByteArrayInputStream(byteArray);
            DataInputStream din= new DataInputStream(bis);
			this.key = din.readInt();
			byte[] bb = new byte[28];
			din.read(bb);
			String info = new String(bb);
			this.info = info;
			din.close();
			bis.close();
		}
		else {
			System.out.println("Buffer must be 32 bytes.");
		}
	}
	
	public byte[] toByteArray() throws IOException {
		String Info = this.info;
		byte[] buffer = new byte[rec_size];
		byte[] INT = ByteBuffer.allocate(4).putInt(this.key).array();
		//byte[] string = new byte [28];
		byte[] string = Info.getBytes();
		System.arraycopy(INT, 0, buffer, 0, INT.length);
		System.arraycopy(string, 0, buffer, 4, string.length);
		return buffer;
		}

	@Override
	public int compareTo(ComparableNode otherNode) {
		if (this.getKey() == otherNode.getKey())
			return 0; // this == otherNode
		else if (this.getKey() > otherNode.getKey())
			return 1; // this > otherNode
		else
			return -1; // this < otherNode
	}
}
