package src;

import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
    private static final long serialVersionUID = 1L;

    public Byte data;
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(Byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
