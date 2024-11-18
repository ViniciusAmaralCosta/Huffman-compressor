package src;

import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable, Cloneable {
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

    @Override
    public HuffmanNode clone() {
        try {
            HuffmanNode cloned = (HuffmanNode) super.clone();
            // Clona os filhos manualmente se não forem nulos
            if (this.left != null) cloned.left = this.left.clone();
            if (this.right != null) cloned.right = this.right.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
