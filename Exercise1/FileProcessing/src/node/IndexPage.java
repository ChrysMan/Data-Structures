package node;
/*
 * An Index page (sizes 128 bytes), with 16 index nodes each containing
 * a 4 byte key and a 4 bytes position of key's page in the file.
 */
public class IndexPage {
	private static final int page_size = 128;
	private static final int rec_size = 8;
	private IndexNode[] node;
	private int pos;
	
	public IndexPage() {
		this.node = new IndexNode[16];
	}

	public IndexNode[] getNodes() {
		return node;
	}

	public void setNodes(IndexNode[] node) {
		this.node = node;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public void setNode(IndexNode node,int pos) {
		this.node[pos] = node;
	}
	
	/*
	 * Method that returns the data of the index nodes in a 128 sized byte array.
	 */
	public byte[] toByteArray() {
		byte[] page = new byte[page_size];
		for (int i=0; i < 16; i++) {
			byte[] byteArray = new byte[rec_size];
			byteArray = getNodes()[i].toByteArray();
			System.arraycopy(byteArray, 0, page, i*rec_size, rec_size);
		}
		return page;
	}
}
