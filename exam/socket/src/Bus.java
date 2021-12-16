import java.io.Serializable;
import java.util.Arrays;

public class Bus implements Serializable {
    public String driverName;
    public String model;
    public int route;
    public int age;
    public int mileage;

    public Bus(String driverName, String model, int route, int age, int mileage) {
        this.driverName = driverName;
        this.model = model;
        this.route = route;
        this.age = age;
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "driverName='" + driverName + '\'' +
                ", model='" + model + '\'' +
                ", route=" + route +
                ", age=" + age +
                ", mileage=" + mileage +
                '}';
    }
}
