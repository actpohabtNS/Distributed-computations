package task_c;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BusScheduler {
    private final List<Stop> busStops;

    public BusScheduler() {
        busStops = new ArrayList<>();
    }

    public void addStop(String city) {
        busStops.add(new Stop(city));
        System.out.println("Added stop: " + city);
    }

    public void deleteStop(String city) {
        Stop cityStopToDelete = getCityStopByName(city);
        if (cityStopToDelete == null) {
            return;
        } else {
            Set<Stop> stopsConnectedToDeleted = new HashSet<>(
                    cityStopToDelete.getNeighbors().keySet());
            for (Stop cityStop : stopsConnectedToDeleted) {
                cityStop.removeConnection(cityStopToDelete);
            }
            busStops.remove(cityStopToDelete);
        }
        System.out.println("Deleted stop: " + city);
    }

    public void changeRoutePrice(String firstCity, String secondCity, int price) {
        Stop firstCityStopToChange = getCityStopByName(firstCity);
        Stop secondCityStopToChange = getCityStopByName(secondCity);
        if (firstCityStopToChange != null && secondCity != null
                && firstCityStopToChange.isConnected(secondCityStopToChange)) {
            firstCityStopToChange.connect(secondCityStopToChange, price);
            System.out.println("Changed price for route from " + firstCity + " to " + secondCity
                    + " for " + price);
        }
    }

    public void addRoute(String firstCity, String secondCity, int price) {
        Stop firstCityStop = getCityStopByName(firstCity);
        Stop secondCityStop = getCityStopByName(secondCity);
        if (firstCityStop != null && secondCity != null) {
            firstCityStop.connect(secondCityStop, price);
            System.out.println("Added route from " + firstCity + " to " + secondCity
                    + " with price " + price);
        }
    }

    public void deleteRoute(String firstCity, String secondCity) {
        Stop firstCityStopToChange = getCityStopByName(firstCity);
        Stop secondCityStopToChange = getCityStopByName(secondCity);
        if (firstCityStopToChange != null && secondCity != null
                && firstCityStopToChange.isConnected(secondCityStopToChange)) {
            firstCityStopToChange.removeConnection(secondCityStopToChange);
            System.out.println("Deleted route from " + firstCity + " to " + secondCity);
        }
    }

    public Integer getRoutePrice(String firstCity, String secondCity) {
        Stop firstCityStopToChange = getCityStopByName(firstCity);
        Stop secondCityStopToChange = getCityStopByName(secondCity);

        return firstCityStopToChange.getPrice(secondCityStopToChange);
    }

    private Stop getCityStopByName(String cityName) {
        Stop cityStop = new Stop(cityName);
        if (busStops.contains(cityStop)) {
            return busStops.get(busStops.indexOf(cityStop));
        }
        return null;
    }
}
