package task_a;

public class Random {
    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
