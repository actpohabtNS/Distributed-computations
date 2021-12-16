package task_c;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BusHeadquarters {
    private static final String KYIV = "Kyiv";
    private static final String LVIV = "Lviv";
    private static final String ODESSA = "Odessa";
    private static final String KHARKIV = "Kharkiv";

    public static void main(String... args) throws InterruptedException {
        BusScheduler schedule = new BusScheduler();
        Executor threadCreator = new Executor(new ReentrantReadWriteLock(false), schedule);
        threadCreator.addStop(ODESSA);
        threadCreator.addStop(KYIV);
        threadCreator.addStop(KHARKIV);
        threadCreator.addStop(LVIV);
        threadCreator.addRoute(KYIV, LVIV, 150);
        threadCreator.addRoute(ODESSA, LVIV, 250);
        threadCreator.addRoute(ODESSA, KHARKIV, 100);
        threadCreator.addRoute(KYIV, ODESSA, 180);
        threadCreator.changeRoutePrice(KYIV, ODESSA, 200);
        System.out.println("Price for trip " + ODESSA + " - " + KHARKIV + " = " +
                threadCreator.getRoutePrice(ODESSA, KHARKIV));
        System.out.println("Price for trip " + KHARKIV + " - " + ODESSA + " = " +
                threadCreator.getRoutePrice(KHARKIV, ODESSA));
        threadCreator.deleteRoute(ODESSA, KYIV);
        System.out.println("Price for trip " + ODESSA + " - " + KYIV + " = " +
                threadCreator.getRoutePrice(ODESSA, KYIV));
        threadCreator.deleteStop(LVIV);
        System.out.println("Price for trip " + ODESSA + " - " + LVIV + " = " +
                threadCreator.getRoutePrice(ODESSA, LVIV));
    }
}