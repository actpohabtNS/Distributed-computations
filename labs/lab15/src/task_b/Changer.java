package task_b;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Changer implements Runnable {
    private final Random random = new Random();
    private String currentString;
    private final CyclicBarrier barrier;
    private final Checker checker;
    private boolean shouldStop = false;
    private int abCount;
    private final int threadIndex;

    public Changer(String str, CyclicBarrier barrier, Checker checker, int index){
        this.currentString = str;
        this.barrier = barrier;
        this.checker = checker;
        this.abCount = countAB(str);
        this.threadIndex = index;
    }

    @Override
    public void run(){
        while(!shouldStop) {
            int randIndex = random.nextInt(currentString.length());

            switch (currentString.charAt(randIndex)) {
                case 'A': {
                    currentString = currentString.substring(0, randIndex) + 'C' + currentString.substring(randIndex + 1);
                    abCount--;
                    break;
                }
                case 'B': {
                    currentString = currentString.substring(0, randIndex) + 'D' + currentString.substring(randIndex + 1);
                    abCount--;
                    break;
                }
                case 'C': {
                    currentString = currentString.substring(0, randIndex) + 'A' + currentString.substring(randIndex + 1);
                    abCount++;
                    break;
                }
                case 'D': {
                    currentString = currentString.substring(0, randIndex) + 'B' + currentString.substring(randIndex + 1);
                    abCount++;
                    break;
                }
            }

            checker.loadThreadInfo(abCount);
            System.out.println("Thread #" + this.threadIndex + " " + currentString + " " + abCount);

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            if(this.threadIndex == 1) {
                System.out.println();
            }

            shouldStop = checker.shouldStop();
        }
    }

    private int countAB(String str) {
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'A' || str.charAt(i) == 'B'){
                count++;
            }
        }
        return count;
    }
}
