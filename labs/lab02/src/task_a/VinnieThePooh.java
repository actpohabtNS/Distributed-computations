package task_a;

import java.util.Arrays;

public class VinnieThePooh {
    private static int[][] forest;
    private static final int forestSize = 5000;

    private static final int hiveSize = 5;
    private static boolean PoohFound = false;
    private static int lastSearchedRow = 0;

    public static void main(String[] args) {
        initForest(forestSize);
        hidePooh();
        startPursue();
    }

    public static void initForest(int size) {
        forest = new int[size][size];

        for (int i = 0; i < size; i++) {
            int[] row = new int[size];
            Arrays.fill(row, 0);
            forest[i] = row;
        }
    }

    public static void hidePooh() {
        int row = _getRandomInt(0, forestSize - 1);
        int col = _getRandomInt(0, forestSize - 1);

        forest[row][col] = 1;
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
            lastSearchedRow++;
        }

        for (int col = 0; col < forestSize - 1; col++) {
            if (forest[row][col] == 1) {
                PoohFound = true;
                System.out.printf("Pooh has been found at %d row, %d col (by hive %d) \n", row, col, hiveNum);
                return;
            }
        }
    }

    private static int _getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
