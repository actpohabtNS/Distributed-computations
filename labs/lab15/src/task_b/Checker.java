package task_b;

import java.util.Arrays;

public class Checker {
    private boolean shouldStop = false;
    private int counterOfThreads = 0;
    private final int numberOfThreads;
    private final int[] threadsInfo;
    private boolean allThreadsArrived = false;

    public Checker(int threadNum) {
        numberOfThreads = threadNum;
        threadsInfo = new int[threadNum];
    }

    public boolean shouldStop() {
        return shouldStop;
    }

    public void equalityCheck() {
        boolean isEqual = true;
        Arrays.sort(threadsInfo);

        for (int i = 1; i < threadsInfo.length - 2; i++) {
            if (threadsInfo[i] != threadsInfo[i + 1]) {
                isEqual = false;
                break;
            }
        }

        if (isEqual) {
            if (threadsInfo[0] == threadsInfo[1] || threadsInfo[threadsInfo.length - 1] == threadsInfo[1]) {
                shouldStop = true;
                System.out.println("'AB' amounts are equal. STOP!");
            }
        }
    }

    public synchronized void loadThreadInfo(int data) {
        threadsInfo[counterOfThreads] = data;
        counterOfThreads++;

        if (counterOfThreads == numberOfThreads) {
            notifyAll();
            allThreadsArrived = true;
        }

        while (!allThreadsArrived) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        counterOfThreads--;

        if (counterOfThreads == 0) {
            equalityCheck();
            allThreadsArrived = false;
        }
    }
}
