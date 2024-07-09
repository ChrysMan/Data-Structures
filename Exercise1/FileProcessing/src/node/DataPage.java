package node;

import java.io.IOException;
/*
 * A Data page (sizes 128 bytes), with 4 nodes each containing a 4 bytes key and a 28 bytes information.
 */
public class DataPage {
	private static final int page_size = 128;
	private static final int rec_size = 32;
	private Node[] node;
	private int pos;
	
	public DataPage() {
		this.node = new Node[4];
	}
	
	public int getPos() {
		return this.pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public Node[] getNodes() {
		return node;
	}
	
	public void setNodes(Node[] node) {
		this.node = node;
	}
	
	public void setNode(Node node,int pos) {
		this.node[pos] = node;
	}
	
	/*
	 * Method that returns the key and the information of the nodes in a 128 sized byte array.
	 */
	public byte[] toByteArray() throws IOException {
		byte[] page = new byte[page_size];
		for (int i=0; i < 4; i++) {
			byte[] byteArray = new byte[rec_size];
			byteArray = getNodes()[i].toByteArray();
			System.arraycopy(byteArray, 0, page, i*rec_size, rec_size);
		}
		return page;		
	}
}
