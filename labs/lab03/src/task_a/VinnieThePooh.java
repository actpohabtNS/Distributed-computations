package task_a;

import java.time.Duration;
import java.time.Instant;

public class VinnieThePooh {
    private static final int HIVE_SIZE = 5;
    private static int honeyAmount = 0;
    public static final int POT_SIZE = 50;
    public static final int MIN_HARV_TIME = 50;
    public static final int MAX_HARV_TIME = 500;
    public volatile static boolean PoohIsAsleep = true;

    private static Instant start;

    public static void main(String[] args) {
        start = Instant.now();
        startHarvest();
    }

    public static void startHarvest() {
        for (int i = 0; i < HIVE_SIZE && honeyAmount != POT_SIZE; i++) {
            new Bee(i);
        }
    }

    public static synchronized int bringHoney() {
        return ++honeyAmount;
    }

    public static void wakePooh(int beeNum) {
        PoohIsAsleep = false;
        Instant end = Instant.now();

        System.out.printf("[Bee #%d]: %,d honey collected in %,d nanos\n", beeNum, honeyAmount, Duration.between(start, end).getNano());
        System.out.println("[Pooh]: Eating honey...\n");

        honeyAmount = 0;
        lullPooh();
    }

    public static void lullPooh() {
        PoohIsAsleep = true;
        start = Instant.now();
    }
}
