import java.lang.Double;
public class NDVector<T extends Number> {
    T[] data;
    int dimensions;
    public NDVector(T[] data) {
        this.dimensions = data.length;
        this.data = data;
    }

    public double distanceTo(NDVector other) throws IllegalArgumentException{
        if(other.dimensions != this.dimensions) throw new IllegalArgumentException("dimensions mismatch.");
        double ans = 0;
        for(int i = 0; i < dimensions; i++) {
            ans += Math.pow(this.data[i].doubleValue() -  other.data[i].doubleValue(), 2);
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
