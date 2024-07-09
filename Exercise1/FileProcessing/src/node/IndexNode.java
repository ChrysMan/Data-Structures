package node;

import java.nio.ByteBuffer;
/*
 * A class implementing interface ComparableNode.
 * Implements the compareTo method of the Comparable interface that ComparableNode extends 
 * An IndexNode is 8 bytes long and contains a 4 byte key and a 4byte position
 * of key's page in the file.
 */
public class IndexNode implements ComparableNode{
	private int key;
	private int pos;
	
	public IndexNode() {
	}
	
	public IndexNode(int key, int pos) {
		this.key = key;
		this.pos = pos;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public byte[] toByteArray() {
		byte[] buffer = new byte[8];
		byte[] Key = ByteBuffer.allocate(4).putInt(this.key).array();
		byte[] Pos = ByteBuffer.allocate(4).putInt(this.pos).array();
		System.arraycopy(Key, 0, buffer, 0, Key.length);
		System.arraycopy(Pos, 0, buffer, 4, Pos.length);
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
