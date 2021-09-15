package task_b;

public class PropertyTheft {
    private static final int propAmount = 100;
    private static Property[] props;

    public static void main(String[] args) {
        init();

        for (int i = 0; i < propAmount; i++) {
            System.out.printf("Prop %d with value: %d \n", i, props[i].getValue());
        }
    }

    public static void init() {
        props = new Property[propAmount];
        for (int i = 0; i < propAmount; i++) {
            props[i] = new Property();
        }
    }
}
