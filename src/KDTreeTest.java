import java.util.ArrayList;
import java.util.Random;

public class KDTreeTest {
    public KDTreeTest() {
        final int SCALE = 1<<16, DIMENSIONS = 9;
        final int TRIALS = 128;
        boolean passed = true;
        System.out.println("Testing...");

        NDVector[] data = buildRandomData(SCALE, DIMENSIONS);
        KDTree tree = KDTree.buildTree(data);
        System.out.printf("Tree depth: %d\n",tree.getDepth());
        double avgTime = 0;
        for(int x = 0; x < TRIALS; x++) {
            NDVector probe = randomProbe(DIMENSIONS);
            long st = System.nanoTime();
            Pair<Double, NDVector> sr = tree.search(probe);
            long et = System.nanoTime();
            if(sr.first - minDistance(data, probe) > 1e-8) {
                passed = false;
                break;
            }
            avgTime += (et-st)/1e6;
            //System.out.printf("Case Done: %f ms.; %s\n",, sr.second);
        }
        System.out.println("Test done; " + (passed ? "PASSED." : "NOT PASSED.") + " Avg. time is "+avgTime/TRIALS+" ms." );
    }
    NDVector randomProbe(int dimensions) {
        Random r = new Random();
        double[] d = new double[dimensions];
        for(int i = 0;i < dimensions; i++) {
            d[i] = r.nextDouble() * 1e3 - 5e2;
        }
        return new NDVector(d);
    }
    NDVector[] buildRandomData(int scale, int dimensions) {
        Random r = new Random();

        NDVector[] result = new NDVector[scale];
        for(int i = 0;i < scale; i++) {
            double[] values = new double[dimensions];
            for(int j = 0; j < dimensions; j++) values[j] = r.nextDouble() * 1e3 - 5e2;

            result[i] = new NDVector(values);
        }
        return result;
    }

    double minDistance(NDVector[] data, NDVector probe) {
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
