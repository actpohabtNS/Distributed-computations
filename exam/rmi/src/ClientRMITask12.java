import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientRMITask12 {
    public static void main(String[] args)
            throws NotBoundException, RemoteException,
            MalformedURLException {
        String url = "//localhost:123/BusPark";
        BusPark busPark = (BusPark) Naming.lookup(url);
        System.out.println("RMI object found");

        System.out.println("Busses with route number 13:");
        System.out.println(busPark.getWithRouteNum(13).toString());

        System.out.println("Busses older than 20 years:");
        System.out.println(busPark.getOlderThan(20).toString());

        System.out.println("Busses with mileage bigger than 200 000 km:");
        System.out.println(busPark.getWithMileageBigger(200000).toString());
    }
}
