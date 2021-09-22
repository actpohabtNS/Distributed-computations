package task_a;

public class Bee implements Runnable {
    public int beeNum;
    private final int HARVEST_TIME;
    private final Thread th;

    public Bee(int beeNum) {
        this.beeNum = beeNum;
        this.HARVEST_TIME = Random.getRandomInt(VinnieThePooh.MIN_HARV_TIME,
                                                VinnieThePooh.MAX_HARV_TIME);

        th = new Thread(this);
        th.start();
    }

    public void run() {
        while (true) {
            if (!VinnieThePooh.PoohIsAsleep) {
                continue;
            }

            try {
                Thread.sleep(HARVEST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!VinnieThePooh.PoohIsAsleep) {
                continue;
            }

            int honeyAmount = VinnieThePooh.bringHoney();

            if (honeyAmount >= VinnieThePooh.POT_SIZE) {
                VinnieThePooh.wakePooh(beeNum);
            }
        }
    }
}
