package node;

/*
 * A node in a data structure with int keys
 */
public interface ComparableNode extends Comparable<ComparableNode> {
	// Returns the key of this node
	public int getKey();
}
