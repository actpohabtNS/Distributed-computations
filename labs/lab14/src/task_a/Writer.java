package task_a;

import java.io.*;

public class Writer implements Runnable{
    private Instructions instruction;
    private String name;
    private String number;
    private final Mutex mutex;
    private final File file;
    private volatile Boolean changed = null;

    public Writer(String file, Mutex mutex) {
        this.mutex = mutex;
        this.file = new File(file);
    }

    public boolean changeFile(Instructions instruction, String name, String number) throws InterruptedException {
        changed = null;
        this.instruction = instruction;
        this.name = name;
        this.number = number;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
        return changed;
    }

    @Override
    public void run() {
        try {
            mutex.writeLock();
            switch (instruction) {
                case ADD -> add(name, number);
                case REMOVE -> remove(name, number);
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            mutex.writeUnlock();
        }
    }

    private void remove(String name, String number) throws IOException {
        File tempFile = new File("~tempfile.txt");
        String toDelete = name + ":" + number;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currRecord;
        while ((currRecord = reader.readLine()) != null) {
            if (toDelete.equals(currRecord.trim())) {
                changed = true;
            } else {
                writer.write(currRecord + System.getProperty("line.separator"));
            }
        }

        writer.close();
        reader.close();

        if (changed == null)
            changed = false;

        if (!file.delete()) {
            changed = false;
            return;
        }

        if (!tempFile.renameTo(file))
            changed = false;
    }

    private void add(String name, String number) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(name + ":" + number + System.getProperty("line.separator"));
            changed = true;
        }
    }
}
