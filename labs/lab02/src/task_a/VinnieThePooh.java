package task_a;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class VinnieThePooh {
    private static boolean[][] forest;
    private static final int forestSize = 5000;

    private static final int hiveSize = 5;
    private volatile static boolean PoohFound = false;
    private static int lastSearchedRow = 0;

    private static Instant start;
    private static Instant end;

    public static void main(String[] args) {
        initForest(forestSize);
        start = Instant.now();
        hidePooh();
        startPursue();
    }

    public static void initForest(int size) {
        forest = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            boolean[] row = new boolean[size];
            Arrays.fill(row, false);
            forest[i] = row;
        }
    }

    public static void hidePooh() {
        int row = _getRandomInt(0, forestSize - 1);
        int col = _getRandomInt(0, forestSize - 1);

        forest[row][col] = true;
    }

    public static void startPursue() {
        for (int i = 0; i < hiveSize - 1 || PoohFound; i++) {
            int finalI = i;
            Thread th = new Thread(() -> {
               while (!PoohFound) {
                   _checkRow(lastSearchedRow, finalI);
               }
            });

            th.start();
        }
    }

    private static void _checkRow(int row, int hiveNum) {
        synchronized (VinnieThePooh.class) {
            lastSearchedRow = lastSearchedRow + 1;
        }

        for (int col = 0; col < forestSize - 1; col++) {
            if (forest[row][col]) {
                PoohFound = true;
                end = Instant.now();
                System.out.printf("Pooh has been found at %,d row, %,d col (by hive %,d) \n", row, col, hiveNum);
                System.out.printf("Time elapsed: %,d micros", Duration.between(start, end).toNanos() / 1000);
                return;
            }
        }
    }

    private static int _getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
