package task_a;

public class Bee implements Runnable {
    public int hiveNum;

    public Bee(int hiveNum) {
        this.hiveNum = hiveNum;
        new Thread(this).start();
    }

    public void run() {
        while (!VinnieThePooh.PoohFound) {
            int line = VinnieThePooh.getForestLine();

            for (int col = 0; col < VinnieThePooh.forestSize - 1; col++) {
                if (VinnieThePooh.forest[line][col]) {
                    VinnieThePooh.PoohFound = true;
                    System.out.printf("Pooh has been found at %,d row, %,d col (by hive %,d) \n", line, col, hiveNum);
                    VinnieThePooh.executePooh();
                    return;
                }
            }
        }
    }
}
