package task_a;

import java.security.SecureRandom;

public class Database {
    private static final String fileName = "src/task_a/database.txt";

    public static void main(String... args) {
        Mutex mutex = new Mutex();
        Reader reader = new Reader(fileName, mutex);
        Writer writer = new Writer(fileName, mutex);

        String number = "050 123 45 67";
        String name = "Nikita Sazonov";

        SecureRandom random = new SecureRandom();
        try {
            System.out.println("Status of adding operation: " +
                    writer.changeFile(Instructions.ADD, "Name" + random.nextInt(100),
                            "1" + (random.nextInt(100) + 1000)));
            System.out.println("Status of adding operation: " +
                    writer.changeFile(Instructions.ADD, name, number));
            System.out.println("Name with number 26352673: " +
                    reader.search(Instructions.FIND_NAME_BY_NUMBER, "26352673"));
            System.out.println("Number of " + name + ": " +
                    reader.search(Instructions.FIND_NUMBER_BY_NAME, name));
            System.out.println("Status of removing operation: " +
                    writer.changeFile(Instructions.REMOVE, name,
                            number));
            System.out.println("Name of " + number + ": " +
                    reader.search(Instructions.FIND_NAME_BY_NUMBER, number));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
