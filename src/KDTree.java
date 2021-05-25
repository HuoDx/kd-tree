import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KDTree {
    KDTreeNode root;

    private KDTree(KDTreeNode root) {
        this.root = root;
    }
    private int probeDepth(KDTreeNode node) {
        if(node == null) return 0;
        int depth = probeDepth(node.leftChild);
        int rightDepth = probeDepth(node.rightChild);
        depth = rightDepth > depth ? rightDepth : depth;
        return depth + 1;
    }
    public int getDepth() {
        return probeDepth(root);
    }
    public static KDTree buildTree(NDVector<Double>[] data) {
        return new KDTree(build(Arrays.asList(data),0));
    }

    private static KDTreeNode build(List<NDVector<Double>> data, int dimensionFocus) {
        if(data.size() < 1) return null;
        data.sort( new Comparator<NDVector<Double>>() {
            @Override
            public int compare(NDVector<Double> o1, NDVector<Double> o2) {
                if(o1.data == null && o2.data == null) return 0;
                if(o1.data == null) return -1;
                if(o2.data == null) return 1;

                return (int)(o1.data[dimensionFocus] * 10 - o2.data[dimensionFocus] * 10);
            }
        });
        KDTreeNode node = new KDTreeNode(data.get(data.size() / 2), dimensionFocus);
        node.leftChild = build(data.subList(0, data.size() / 2), (dimensionFocus + 1) % node.vector.dimensions);
        node.rightChild = build(data.subList(data.size() / 2 + 1, data.size()), (dimensionFocus + 1) % node.vector.dimensions);
        return node;
    }

    public Pair<Double, NDVector<Double>> search (NDVector<Double> queryPosition) {
        return search(this.root, queryPosition);
    }
    /**
     * Searches the minimal distance under the provided node given the position of queryPosition;
     * @param node
     * @param queryPosition
     * @return
     */
    private Pair<Double, NDVector<Double>> search(KDTreeNode node, NDVector<Double> queryPosition) {
        if(node == null) return new Pair<Double, NDVector<Double>>(Double.MAX_VALUE, null);
        if(node.leftChild == null && node.rightChild == null) return new Pair<Double, NDVector<Double>>(node.vector.distanceTo(queryPosition), (NDVector<Double>) node.vector);

        double minimalDistance = Double.MAX_VALUE;
        double radius = Double.MAX_VALUE;
        boolean leftChildChosen = false;
        NDVector<Double> closerNode;

        if(queryPosition.data[node.dimensionFocus].doubleValue() > node.vector.data[node.dimensionFocus].doubleValue() && node.rightChild != null) {
            Pair<Double, NDVector<Double>> queryResult = search(node.rightChild, queryPosition);
            radius = queryResult.first;
            closerNode = queryResult.second;

        } else {
            Pair<Double, NDVector<Double>> queryResult = search(node.leftChild, queryPosition);
            radius = search(node.leftChild, queryPosition).first;
            closerNode = queryResult.second;

            leftChildChosen = true;
        }

        minimalDistance = radius;

        if( radius >= Math.abs(queryPosition.data[node.dimensionFocus].doubleValue() - node.vector.data[node.dimensionFocus].doubleValue())) {
            Pair<Double, NDVector<Double>> queryResult = search(leftChildChosen ? node.rightChild : node.leftChild, queryPosition);
            minimalDistance = queryResult.first;
            closerNode = queryResult.second;
            minimalDistance = minimalDistance > radius ? radius : minimalDistance;
        }

        double selfDistance = node.vector.distanceTo(queryPosition);

        return minimalDistance < selfDistance ? new Pair<>(minimalDistance, closerNode) : new Pair<>(selfDistance, (NDVector<Double>) node.vector);
    }
}
