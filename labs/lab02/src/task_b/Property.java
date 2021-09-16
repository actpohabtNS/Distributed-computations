package task_b;

public class Property {
    private int value;
    private static final int min = 10;
    private static final int max = 1000;

    public Property() {
        value = _getRandomInt(min, max);
    }

    public Property(int value) {
        this.value = value;
    }

    private static int _getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int getValue() {
        return value;
    }
}
