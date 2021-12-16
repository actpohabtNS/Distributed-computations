package task_b;

public class TaskBarbershop {
    public static void main(String[] args) {
        Barbershop barbershop = new Barbershop();
        Barber barber = new Barber(barbershop);

        new Thread(new Client(0, barbershop)).start();

        new Thread(barber).start();

        for (int i = 1; i < 7; i++) {
            new Thread(new Client(i, barbershop)).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 7; i < 10; i++) {
            new Thread(new Client(i, barbershop)).start();
        }
    }
}
