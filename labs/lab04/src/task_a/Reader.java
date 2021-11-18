package task_a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements Runnable{
    private Instructions instruction;
    private String searchingKey;
    private final String fileName;
    private final Mutex mutex;
    private String result = null;

    public Reader(String fileName, Mutex mutex) {
        this.fileName = fileName;
        this.mutex = mutex;
    }

    public void run() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            mutex.readLock();
            String line;
            while ((line = reader.readLine()) != null) {
                int sepIdx = line.indexOf(':');
                String name = line.substring(0, sepIdx);
                String number = line.substring(sepIdx + 1);
                if (lookUp(name, number)) {
                    mutex.readUnlock();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mutex.readUnlock();
        }
    }

    public String search(Instructions instruction, String searchingKey) throws InterruptedException {
        result = null;
        this.instruction = instruction;
        this.searchingKey = searchingKey;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
        return result;
    }

    private boolean lookUp(String name, String number) {
        switch (instruction) {
            case FIND_NAME_BY_NUMBER -> {
                if (number.equals(searchingKey)) {
                    result = name;
                    return true;
                }
            }
            case FIND_NUMBER_BY_NAME -> {
                if (name.equals(searchingKey)) {
                    result = number;
                    return true;
                }
            }
        }
        return false;
    }
}