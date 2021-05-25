import java.lang.Double;
public class NDVector {
    double[] data;
    int dimensions;
    public NDVector(double[] data) {
        this.dimensions = data.length;
        this.data = data;
    }

    public double distanceTo(NDVector other) throws IllegalArgumentException{
        if(other.dimensions != this.dimensions) throw new IllegalArgumentException("dimensions mismatch.");
        double ans = 0;
        for(int i = 0; i < dimensions; i++) {
            ans += (this.data[i] -  other.data[i]) * (this.data[i] -  other.data[i]);
        }
        return ans;

    }
    @Override
    public String toString() {
        String result = "<";
        result += data[0];
        for(int i = 1;i < dimensions; i++) result += ", " + data[i];
        result += ">";
        return result;
    }
}
