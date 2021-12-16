import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMITask12 {
    public static void main(String[] args)
            throws RemoteException {
        ServerRMITask12Impl busParkImpl = new ServerRMITask12Impl();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("BusPark", busParkImpl);
        System.out.println("Server started!");
    }
}
