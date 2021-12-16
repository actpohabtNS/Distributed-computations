package task_b;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PropertyTheft {
    private static final int PROP_AMOUNT = 100000;
    private static int revenue = 0;
    private static BlockingQueue<Property> warehouse = new LinkedBlockingQueue<>();
    private static BlockingQueue<Property> car = new LinkedBlockingQueue<>();
    private static BlockingQueue<Property> truck = new LinkedBlockingQueue<>();
    private static Property poisonPill = new Property(-1);

    private static Instant start;
    private static Instant end;

    public static void main(String[] args) {
        init();
        Ivanov();
        Petrov();
        Nechyporchyk();
    }

    public static void init() {
        for (int i = 0; i < PROP_AMOUNT; i++) {
            warehouse.add(new Property());
        }

        warehouse.add(poisonPill);
        start = Instant.now();
    }

    private static void Ivanov() {
        Thread Ivanov = new Thread(PropertyTheft::steal);
        Ivanov.start();
    }

    private static void Petrov() {
        Thread Petrov = new Thread(PropertyTheft::carry);
        Petrov.start();
    }

    private static void Nechyporchyk() {
        Thread Nechyporchyk = new Thread(PropertyTheft::count);
        Nechyporchyk.start();
    }

    private static void steal() {
        while (true) {
            try {
                Property prop = warehouse.take();
                car.put(prop);

                if (prop == poisonPill) {
                    break;
                }
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void carry() {
        while (true) {
            try {
                Property prop = car.take();
                truck.put(prop);

                if (prop == poisonPill) {
                    break;
                }
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void count() {
        int counted = 0;
        while (true) {
            try {
                Property prop = truck.take();

                if (prop == poisonPill) {
                    break;
                }

                revenue += prop.getValue();
                counted++;
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
        }

        end = Instant.now();
        System.out.printf("We have stolen %,d properties worth %,d $!\n", counted, revenue);
        System.out.printf("Time elapsed: %,d micros", Duration.between(start, end).toNanos() / 1000);
    }
}
