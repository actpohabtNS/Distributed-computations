package module1;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class java10Task {

    public static void main(String[] args) {
        new Thread(new EngageBroker()).start();
        new Thread(new EngageBroker()).start();
        new Thread(new EngageBroker()).start();
        new Thread(new EngageBroker()).start();
        new Thread(new EngageBroker()).start();
    }
}

class StockExchange {

    private static final StockExchange INSTANCE = new StockExchange();
    private final AtomicInteger index = new AtomicInteger(5000);
    private final int INDEX_RATE = 10;
    private final int INDEX_STOP = 100;
    private final AtomicInteger availableStocks = new AtomicInteger(500);
    private boolean isWorking = true;

    public StockExchange() {}

    public boolean sell(final int amount) {
        if (amount < 1) {
            return false;
        }
        availableStocks.set(availableStocks.get() + amount);
        updateIndex(amount);
        checkStop();
        return true;
    }

    public boolean buy(final int amount) {
        if (amount < 1 || amount > availableStocks.get()) {
            return false;
        }
        availableStocks.set(availableStocks.get() - amount);
        updateIndex(-amount);
        checkStop();
        return true;
    }

    public int getAvailableStocks() {
        return availableStocks.get();
    }

    public int getIndex() {
        return index.get();
    }

    public boolean isWorking() {
        return isWorking;
    }

    private void updateIndex(int amount) {
        index.set(index.get() + amount * INDEX_RATE);
    }

    private void checkStop() {
        if(index.get() < INDEX_STOP)
            isWorking = false;
    }

    public static StockExchange getInstance() {
        return INSTANCE;
    }
}

class Broker {

    private final StockExchange stocks;
    private final int brokerID;
    private static int allBrokers;
    private int currentStocks;

    public Broker(final int stocksOwned) {
        stocks = StockExchange.getInstance();
        currentStocks = stocksOwned;
        brokerID = ++allBrokers;
    }

    public boolean requestBuy(final int amount) {
        if (amount < 1 || !stocks.buy(amount)) {
            return false;
        }
        currentStocks += amount;
        return true;
    }

    public boolean requestSell(final int amount) {
        if (amount < 1 || amount > currentStocks
                || !stocks.sell(amount)) {
            return false;
        }
        currentStocks -= amount;
        return true;
    }

    public boolean shouldWork() {
        return stocks.isWorking();
    }

    public void printBrokerInfo() {
        System.out.println(this);
        System.out.format("StockExchange (index %d) stocks available: %d%n", stocks.getIndex(),
                stocks.getAvailableStocks());
    }

    public String toString() {
        return String.format("Broker %d : %d stocks available.", brokerID,
                currentStocks);
    }

    public int brokerID() {
        return brokerID;
    }
}

class EngageBroker implements Runnable {

    private static final Random RAND = new Random();
    private final Broker broker;

    public EngageBroker() {
        broker = new Broker(RAND.nextInt(250));
    }

    private void buy() {
        broker.printBrokerInfo();
        final int amount = RAND.nextInt(500) + 1;
        final boolean success = broker.requestBuy(amount);
        if (success) {
            System.out.format("Bought %d stocks.%n", amount);
        } else {
            System.out.format("Failed to buy %d stocks.%n", amount);
        }
        broker.printBrokerInfo();
        System.out.println("********************");
    }

    private void sell() {
        broker.printBrokerInfo();
        final int amount = RAND.nextInt(500) + 1;
        final boolean success = broker.requestSell(amount);
        if (success) {
            System.out.format("Sold %d stocks.%n", amount);
        } else {
            System.out.format("Failed to sell %d stocks.%n", amount);
        }
        broker.printBrokerInfo();
        System.out.println("********************");
    }

    private void sleep() {
        try {
            Thread.sleep(RAND.nextInt(2000));
        } catch (InterruptedException ignored) {
        }
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            sleep();
            if (!broker.shouldWork()) {
                System.out.format("Broker %d : stopping (Stocks closed).%n", broker.brokerID());
                return;
            }
            buy();
            sleep();

            if (!broker.shouldWork()) {
                System.out.format("Broker %d : stopping (Stocks closed).%n", broker.brokerID());
                return;
            }
            sell();
        }

        System.out.format("Broker %d : returning home wealthy.%n", broker.brokerID());
    }
}
