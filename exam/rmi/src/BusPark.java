import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface BusPark extends Remote {
    ArrayList<Bus> getWithRouteNum(int route) throws RemoteException;
    ArrayList<Bus> getOlderThan(int age) throws RemoteException;
    ArrayList<Bus> getWithMileageBigger(int mileage) throws RemoteException;
}
