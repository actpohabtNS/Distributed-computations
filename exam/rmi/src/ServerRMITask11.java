import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMITask11 {
    public static void main(String[] args)
            throws RemoteException {
        ServerRMITask11Impl busParkImpl = new ServerRMITask11Impl();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("BusPark", busParkImpl);
        System.out.println("Server started!");
    }
}
