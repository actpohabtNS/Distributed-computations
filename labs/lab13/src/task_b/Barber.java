package task_b;

import java.time.Duration;
import java.time.Instant;

public class Barber implements Runnable {
    private final Barbershop barbershop;

    public Barber(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    public void startWorkDay() {
        System.out.println("[Barber] Starting working day!");

        while (true) {
            serveClient();
        }
    }

    public synchronized void serveClient() {
        Client client = barbershop.takeClient();
        Instant start = Instant.now();

        synchronized (client) {
            client.notify();
        }
        try {
            Thread.sleep(client.getTimeToCut());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("[Barber]: I have finished cutting Client #%d\n", client.getClientNum());
        System.out.printf("[Barber]: It took %d millis!\n\n", Duration.between(start, Instant.now()).getNano() / 1000000);
    }

    @Override
    public void run() {
        startWorkDay();
    }
}
