public class KDTreeNode {
    public KDTreeNode leftChild, rightChild;
    public NDVector vector;
    public int dimensionFocus;
    public KDTreeNode(NDVector vector, int dimensionFocus) {
        this.vector = vector;
        this.dimensionFocus = dimensionFocus;
    }

    @Override
    public String toString() {
        return "KDTreeNode{" +
                "vector=" + vector +
                '}';
    }
}
