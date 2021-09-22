package task_b;

public class Client implements Runnable {
    private final int TIME_TO_CUT;
    private final int CLIENT_NUM;
    private static final int MIN_TIME = 500;
    private static final int MAX_TIME = 1500;
    private Barbershop barbershop;

    public Client(int clientNum, Barbershop barbershop) {
        this.TIME_TO_CUT = Random.getRandomInt(MIN_TIME, MAX_TIME);
        this.CLIENT_NUM = clientNum;
        this.barbershop = barbershop;
    }

    public synchronized void goToBarbershop(Barbershop barbershop) {
        barbershop.addClient(this);
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("[Client #%d]: I am being cut :)\n", CLIENT_NUM);
    }

    public int getTimeToCut() {
        return this.TIME_TO_CUT;
    }

    public int getClientNum() {
        return this.CLIENT_NUM;
    }

    @Override
    public void run() {
        goToBarbershop(barbershop);
    }
}
