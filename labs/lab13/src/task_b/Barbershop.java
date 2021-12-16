package task_b;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Barbershop {
    private final BlockingQueue<Client> queue = new LinkedBlockingQueue<>();

    public Barbershop() {}

    public void addClient(Client client) {
        queue.add(client);
    }

    public Client takeClient() {
        Client client = null;
        try {
            client = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return client;
    }
}
