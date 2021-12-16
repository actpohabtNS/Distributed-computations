package task_c;

import java.util.concurrent.locks.ReadWriteLock;

public class Executor implements Runnable {
    private final ReadWriteLock lock;
    private final BusScheduler scheduler;
    private Instructions instruction;
    private String firstCity;
    private String secondCity;
    private Integer price;

    public Executor(ReadWriteLock lock, BusScheduler schedule) {
        this.lock = lock;
        this.scheduler = schedule;
    }

    public void addStop(String city) throws InterruptedException {
        this.instruction = Instructions.ADD_STOP;
        this.firstCity = city;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
    }

    public void deleteStop(String city) throws InterruptedException {
        this.instruction = Instructions.REMOVE_STOP;
        this.firstCity = city;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
    }

    public void changeRoutePrice(String firstCity, String secondCity, int price) throws InterruptedException {
        this.instruction = Instructions.CHANGE_PRICE;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.price = price;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
    }

    public void addRoute(String firstCity, String secondCity, int price) throws InterruptedException {
        this.instruction = Instructions.ADD_ROUTE;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.price = price;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
    }

    public void deleteRoute(String firstCity, String secondCity) throws InterruptedException {
        this.instruction = Instructions.REMOVE_ROUTE;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
    }

    public Integer getRoutePrice(String firstCity, String secondCity) throws InterruptedException {
        this.instruction = Instructions.GET_ROUTE_PRICE;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
        return price;
    }

    private void addBusStopImpl() {
        lock.writeLock().lock();
        scheduler.addStop(firstCity);
        lock.writeLock().unlock();
    }

    private void deleteBusStopImpl() {
        lock.writeLock().lock();
        scheduler.deleteStop(firstCity);
        lock.writeLock().unlock();
    }

    private void changeRoutePriceImpl() {
        lock.writeLock().lock();
        scheduler.changeRoutePrice(firstCity, secondCity, price);
        lock.writeLock().unlock();
    }

    private void addRouteImpl() {
        lock.writeLock().lock();
        scheduler.addRoute(firstCity, secondCity, price);
        lock.writeLock().unlock();
    }

    private void deleteRouteImpl() {
        lock.writeLock().lock();
        scheduler.deleteRoute(firstCity, secondCity);
        lock.writeLock().unlock();
    }

    private void getRoutePriceImpl() {
        lock.writeLock().lock();
        price = scheduler.getRoutePrice(firstCity, secondCity);
        lock.writeLock().unlock();
    }

    @Override
    public void run() {
        switch (instruction) {
            case ADD_STOP: {
                addBusStopImpl();
                break;
            }
            case REMOVE_STOP: {
                deleteBusStopImpl();
                break;
            }
            case CHANGE_PRICE: {
                changeRoutePriceImpl();
                break;
            }
            case ADD_ROUTE: {
                addRouteImpl();
                break;
            }
            case REMOVE_ROUTE: {
                deleteRouteImpl();
                break;
            }
            case GET_ROUTE_PRICE: {
                getRoutePriceImpl();
                break;
            }
        }
    }
}
