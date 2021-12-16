import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerRMITask11Impl extends UnicastRemoteObject
        implements BusPark {

    private final ArrayList<Bus> busses;

    public ServerRMITask11Impl() throws RemoteException {
        this.busses = new ArrayList<>(Arrays.asList(
                new Bus("Nikita", "Toyota", 13, 10, 300000),
                new Bus("Vasya", "Porshe", 11, 2, 20000),
                new Bus("Stepan", "PAZ", 155, 55, 400000),
                new Bus("Petya", "MAZ", 56, 60, 1000000),
                new Bus("Lera", "VAZ", 13, 40, 350000),
                new Bus("Vasylysa", "ZAZ", 33, 52, 200000)
        ));
    }

    @Override
    public ArrayList<Bus> getWithRouteNum(int route) throws RemoteException {
        ArrayList<Bus> res = new ArrayList<>();
        for (Bus bus : busses) {
            if (bus.route == route) {
                res.add(bus);
            }
        }
        return res;
    }

    @Override
    public ArrayList<Bus> getOlderThan(int age) throws RemoteException {
        ArrayList<Bus> res = new ArrayList<>();
        for (Bus bus : busses) {
            if (bus.age >= age) {
                res.add(bus);
            }
        }
        return res;
    }

    @Override
    public ArrayList<Bus> getWithMileageBigger(int mileage) throws RemoteException {
        ArrayList<Bus> res = new ArrayList<>();
        for (Bus bus : busses) {
            if (bus.mileage >= mileage) {
                res.add(bus);
            }
        }
        return res;
    }
}
