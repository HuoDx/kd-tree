import java.util.ArrayList;
import java.util.Random;

public class KDTreeTest {
    public KDTreeTest() {
        final int SCALE = 1<<16, DIMENSIONS = 9;
        final int TRIALS = 128;
        boolean passed = true;
        System.out.println("Testing...");

        NDVector<Double>[] data = buildRandomData(SCALE, DIMENSIONS);
        KDTree tree = KDTree.buildTree(data);
        System.out.printf("Tree depth: %d\n",tree.getDepth());
        for(int x = 0; x < TRIALS; x++) {
            NDVector<Double> probe = randomProbe(DIMENSIONS);
            long st = System.nanoTime();
            Pair<Double, NDVector<Double>> sr = tree.search(probe);
            long et = System.nanoTime();
            if(sr.first - minDistance(data, probe) > 1e-8) {
                passed = false;
                break;
            }
            System.out.printf("Case Done: %f ms.; %s\n",(et-st)/1e6, sr.second);
        }
        System.out.println("Test done; " + (passed ? "PASSED." : "NOT PASSED.") );
    }
    NDVector<Double> randomProbe(int dimensions) {
        Random r = new Random();
        Double[] d = new Double[dimensions];
        for(int i = 0;i < dimensions; i++) {
            d[i] = r.nextDouble() * 1e3 - 5e2;
        }
        return new NDVector<Double>(d);
    }
    NDVector<Double>[] buildRandomData(int scale, int dimensions) {
        Random r = new Random();

        NDVector<Double>[] result = new NDVector[scale];
        for(int i = 0;i < scale; i++) {
            Double[] values = new Double[dimensions];
            for(int j = 0; j < dimensions; j++) values[j] = r.nextDouble() * 1e3 - 5e2;

            result[i] = new NDVector<Double>(values);
        }
        return result;
    }

    double minDistance(NDVector<Double>[] data, NDVector<Double> probe) {
        double result = Double.MAX_VALUE;
        for(int i = 0;i < data.length; i++) {
            result = data[i].distanceTo(probe) < result ? data[i].distanceTo(probe) : result;
        }
        return result;
    }
    public static void main(String[] args) {
        new KDTreeTest();
    }
}
